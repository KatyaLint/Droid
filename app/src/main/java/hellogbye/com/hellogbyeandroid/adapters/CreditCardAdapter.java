package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.PaymentSummaryItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder> {
    private ArrayList<PaymentSummaryItem> mArrayList;
    OnItemClickListener  mItemClickListner;

    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;

    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public FontTextView mCardNumberText;
        public FontTextView mNameText;
        public FontTextView mTotalText;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mCardNumberText = (FontTextView)itemLayoutView.findViewById(R.id.summary_passenger_cc);
            mNameText = (FontTextView)itemLayoutView.findViewById(R.id.summary_passenger_name);
            mTotalText = (FontTextView)itemLayoutView.findViewById(R.id.summary_passenger_total);

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
    public CreditCardAdapter(ArrayList<PaymentSummaryItem> myDataset, Context context) {
        mArrayList = myDataset;
        mContext = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public CreditCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_summary_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        PaymentSummaryItem cc = mArrayList.get(position);
        holder.mCardNumberText.setText(cc.getLast4());
        holder.mNameText.setText(cc.getName());
        holder.mTotalText.setText("$"+cc.getTotal());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }

    public  int getLastCheckedPos() {
        return lastCheckedPos;
    }

    public static void setLastCheckedPos(int lastCheckedPos) {
        CreditCardAdapter.lastCheckedPos = lastCheckedPos;
    }
}