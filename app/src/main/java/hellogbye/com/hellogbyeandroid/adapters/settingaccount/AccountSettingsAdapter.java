package hellogbye.com.hellogbyeandroid.adapters.settingaccount;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 1/14/16.
 */


public class AccountSettingsAdapter extends RecyclerView.Adapter<AccountSettingsAdapter.ViewHolder> {
    private  String[] mArrayList;
    private OnItemClickListener  mItemClickListner;

    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public FontTextView account_settings_name;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            account_settings_name = (FontTextView)itemLayoutView.findViewById(R.id.account_settings_name);

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
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mItemClickListner = mItemClickListener;
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public AccountSettingsAdapter( String[] myDataset, Context context) {
        mArrayList = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AccountSettingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.account_settings_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        String strData = mArrayList[position];
        holder.account_settings_name.setText(strData);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.length;
    }




}