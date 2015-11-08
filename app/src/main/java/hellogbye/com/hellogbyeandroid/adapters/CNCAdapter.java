package hellogbye.com.hellogbyeandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class CNCAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<CNCItem> mArrayList;
    OnItemClickListener mItemClickListner;

    public static final int HGB_ITEM = 2;
    public static final int ME_ITEM = 0;

    // Provide a suitable constructor (depends on the kind of dataset)
    public CNCAdapter(ArrayList<CNCItem> myDataset) {
        mArrayList = myDataset;
    }

    class ViewHolderMe extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public FontTextView item;


        public ViewHolderMe(View itemLayoutView) {
            super(itemLayoutView);
            item = (FontTextView) itemLayoutView.findViewById(R.id.cnc_input);

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
        public FontTextView item;


        public ViewHolderHGB(View itemLayoutView) {
            super(itemLayoutView);
            item = (FontTextView) itemLayoutView.findViewById(R.id.cnc_hgb_input);
            itemLayoutView.setOnClickListener(this);

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

        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {

            case 0:
                ViewHolderMe meholder = (ViewHolderMe) holder;
                meholder.item.setText(mArrayList.get(position).getText());
                break;

            case 2:
                ViewHolderHGB hgbholder = (ViewHolderHGB) holder;
                hgbholder.item.setText(mArrayList.get(position).getText());
                break;
        }
    }


    @Override
    public int getItemViewType(int position) {

        return mArrayList.get(position).getType();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mItemClickListner = mItemClickListener;
    }





    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }


}