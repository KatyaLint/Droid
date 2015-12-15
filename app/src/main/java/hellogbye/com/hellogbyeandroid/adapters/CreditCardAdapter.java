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
import hellogbye.com.hellogbyeandroid.models.CreditCardItem;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder> {
    private ArrayList<CreditCardItem> mArrayList;
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
        public ImageView mCardImage;
        public RadioButton mCardRadioButton;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mCardNumberText = (FontTextView)itemLayoutView.findViewById(R.id.cc_number);
            mCardImage = (ImageView)itemLayoutView.findViewById(R.id.cc_image);
            mCardRadioButton = (RadioButton)itemLayoutView.findViewById(R.id.cc_radio_button);

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



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element




        CreditCardItem cc = mArrayList.get(position);
        holder.mCardNumberText.setText(cc.getLast4());
        holder.mCardRadioButton.setChecked(cc.isSelected());
        holder.mCardRadioButton.setTag(new Integer(position));

        if(position == 0 && mArrayList.get(0).isSelected() && holder.mCardRadioButton.isChecked())
        {
            lastChecked = holder.mCardRadioButton;
            lastCheckedPos = 0;
        }

        holder.mCardRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RadioButton cb = (RadioButton)buttonView;
                int clickedPos = ((Integer)cb.getTag()).intValue();

                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        mArrayList.get(lastCheckedPos).setSelected(false);

                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                mArrayList.get(clickedPos).setSelected(cb.isSelected());

            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }




}