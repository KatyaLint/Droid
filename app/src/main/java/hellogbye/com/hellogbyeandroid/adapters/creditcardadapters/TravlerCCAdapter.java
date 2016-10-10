package hellogbye.com.hellogbyeandroid.adapters.creditcardadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class TravlerCCAdapter extends RecyclerView.Adapter<TravlerCCAdapter.ViewHolder> {



    private ArrayList<CreditCardItem> mArrayList;
    private onCheckCVV  mOnCheck;
    private OnItemClickListener  mItemClickListner;
    private HashMap<String, Double> CCMap;


    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        //  private FontTextView mProceedDisable;
        private FontTextView mCardNumberText;
        private FontTextView mNameText;
        private FontTextView mTotalText;
        private ImageView mCCImage ;
        private ImageView mCVVImage;
        private FontEditTextView mCVVEditText;



        public ViewHolder(View view) {
            super(view);
            mCardNumberText = (FontTextView) view.findViewById(R.id.cc_text);
            mNameText = (FontTextView) view.findViewById(R.id.summary_passenger_name);
            mTotalText = (FontTextView) view.findViewById(R.id.summary_passenger_total);
            mCCImage = (ImageView) view.findViewById(R.id.cc_image);
            mCVVImage = (ImageView) view.findViewById(R.id.ccv_image);
            mCVVEditText = (FontEditTextView) view.findViewById(R.id.ccv_edittext);


        }


        @Override
        public void onClick(View v) {
            if (mItemClickListner != null){
                mItemClickListner.onItemClick(v,getPosition());
            }
        }
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public interface onCheckCVV {
        public void onCheck();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mItemClickListner = mItemClickListener;
    }

    public void setOnCheckCVV(final onCheckCVV oncheck) {
        mOnCheck = oncheck;
    }



    // Provide a suitable constructor (depends on the kind of dataset)

    public TravlerCCAdapter(HashMap<String, Double> map, ArrayList<CreditCardItem> myDataset, Context context) {
        CCMap =map;
        mArrayList = myDataset;
        mContext = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public TravlerCCAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_summary_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        CreditCardItem item = mArrayList.get(position);

        holder.mCardNumberText.setText(item.getLast4());
        holder.mNameText.setText(item.getBuyerfirstname());

        if(CCMap.containsKey(item.getToken())){

            Double d = CCMap.get(item.getToken());

            holder.mTotalText.setText("$" + String.format("%.2f",d ));
        }

        if(item.getCvv()!= null){
            holder.mCVVEditText.setText(item.getCvv());
        }

        HGBUtility.setCCIcon(holder.mCCImage,item.getCardtypeid());

        holder.mCVVEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()==3){
                    holder.mCVVImage.setVisibility(View.GONE);
                }else{
                    holder.mCVVImage.setVisibility(View.VISIBLE);
                }
                mOnCheck.onCheck();

            }

            @Override
            public void afterTextChanged(Editable editable) {



            }
        });


        }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }




}