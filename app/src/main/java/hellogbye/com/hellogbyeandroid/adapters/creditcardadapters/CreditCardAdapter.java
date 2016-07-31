package hellogbye.com.hellogbyeandroid.adapters.creditcardadapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder> {
    private ArrayList<CreditCardItem> mArrayList;
    OnItemClickListener  mItemClickListner;


    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public ImageView mCardImage;
        public FontTextView mCardNumberText;
       // public ImageView mNext;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mCardImage= (ImageView)itemLayoutView.findViewById(R.id.cc_image);
            mCardNumberText = (FontTextView)itemLayoutView.findViewById(R.id.cc_number);
          //  mNext = (ImageView)itemLayoutView.findViewById(R.id.cc_image_next);
            itemLayoutView.setOnClickListener(this);

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

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mItemClickListner = mItemClickListener;
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public CreditCardAdapter(ArrayList<CreditCardItem> myDataset, Context context) {
        mArrayList = myDataset;
        mContext = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public CreditCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.credit_card_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        CreditCardItem cc = mArrayList.get(position);
        holder.mCardNumberText.setText(cc.getLast4());

        if(cc.getCardtypeid().equals("1")){
            //holder.mCardImage.setBackgroundResource(R.drawable.visa);
        }else if (cc.getCardtypeid().equals("2")){
           // holder.mCardImage.setBackgroundResource(R.drawable.master_card);
        }else if (cc.getCardtypeid().equals("3")){
            holder.mCardImage.setBackgroundResource(R.drawable.master_card);
        }else if (cc.getCardtypeid().equals("4")){
            holder.mCardImage.setBackgroundResource(R.drawable.visa);
        }else {
            holder.mCardImage.setBackgroundResource(R.drawable.visa);
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


}