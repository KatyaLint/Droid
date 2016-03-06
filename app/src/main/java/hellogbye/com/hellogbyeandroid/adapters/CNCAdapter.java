package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class CNCAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CNCItem> mArrayList;
    OnItemClickListener mItemClickListner;
    private Context mContext;

    public static final int HGB_ITEM = 2;
    public static final int HGB_ERROR_ITEM = 1;
    public static final int ME_ITEM = 0;
    public static final int WAITING_ITEM = 3;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CNCAdapter(Context context, ArrayList<CNCItem> myDataset) {
        mArrayList = myDataset;
        mContext = context;
    }

    class ViewHolderMe extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public FontTextView itemME;


        public ViewHolderMe(View itemLayoutView) {
            super(itemLayoutView);
            itemME = (FontTextView) itemLayoutView.findViewById(R.id.cnc_input);

            itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListner != null) {
                mItemClickListner.onItemClick(v, getPosition());
            }
        }

    }

    class ViewHolderHGB extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public FontTextView itemHGB;


        public ViewHolderHGB(View itemLayoutView) {
            super(itemLayoutView);
            itemHGB = (FontTextView) itemLayoutView.findViewById(R.id.cnc_hgb_input);
            itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListner != null) {
                mItemClickListner.onItemClick(v, getPosition());
            }
        }

    }

    class ViewHolderWaiting extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public DotsTextView itemWaiting;


        public ViewHolderWaiting(View itemLayoutView) {
            super(itemLayoutView);
            itemWaiting = (DotsTextView) itemLayoutView.findViewById(R.id.dots);
            //itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListner != null) {
                mItemClickListner.onItemClick(v, getPosition());
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ME_ITEM:
                // create a new view
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cnc_item, parent, false);
                ViewHolderMe vh = new ViewHolderMe(view);
                return vh;

            case HGB_ITEM:
                View hgbview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cnc_hgb_item, parent, false);
                ViewHolderHGB vh2 = new ViewHolderHGB(hgbview);
                return vh2;

            case WAITING_ITEM:
                View waitview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cnc_waiting_item, parent, false);
                ViewHolderWaiting vhwait = new ViewHolderWaiting(waitview);
                return vhwait;

        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderHGB hgbholder = (ViewHolderHGB) holder;
        String strMessage = mArrayList.get(position).getText();

        switch (holder.getItemViewType()) {

            case ME_ITEM:
                ViewHolderMe meholder = (ViewHolderMe) holder;
                meholder.itemME.setText(strMessage);
                break;
            case HGB_ERROR_ITEM:

                hgbholder.itemHGB.setBackgroundResource(R.drawable.hgb_red_cnc_backround);
                hgbholder.itemHGB.setTextColor(mContext.getResources().getColor(R.color.black));
                hgbholder.itemHGB.setPadding(30,30,30,30);
            break;
            case HGB_ITEM:

                if (mContext.getResources().getString(R.string.iteinerary_created).equals(strMessage)
                   ||mContext.getResources().getString(R.string.grid_has_been_updated).equals(strMessage)) {
                    hgbholder.itemHGB.setBackgroundResource(R.drawable.hgb_red_cnc_backround);
                    hgbholder.itemHGB.setTextColor(mContext.getResources().getColor(R.color.black));
                    hgbholder.itemHGB.setPadding(30,30,30,30);
                }
                else {
                    hgbholder.itemHGB.setBackgroundResource(R.drawable.hgb_cnc_backround);
                    hgbholder.itemHGB.setTextColor(mContext.getResources().getColor(R.color.cnc_hgb_text));
                    hgbholder.itemHGB.setPadding(30,30,30,30);
                }
                hgbholder.itemHGB.setText(strMessage);
                break;
            case WAITING_ITEM:
                ViewHolderWaiting hgbwaiting = (ViewHolderWaiting) holder;
                //TODO magic
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {

        return mArrayList.get(position).getType();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mItemClickListner = mItemClickListener;
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (null != mArrayList ? mArrayList.size() : 0);
    }


}