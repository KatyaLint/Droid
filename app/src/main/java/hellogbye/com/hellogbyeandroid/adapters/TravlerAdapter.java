package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.checkout.TravlersFragment;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class TravlerAdapter extends RecyclerView.Adapter<TravlerAdapter.ViewHolder> {
    private ArrayList<UserDataVO> mArrayList;
    private OnItemClickListener  mItemClickListner;
    private TravlersFragment travelFragment;
    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public FontTextView mTravlerName;
        public FontTextView mTravlerText;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mTravlerName = (FontTextView)itemLayoutView.findViewById(R.id.travler_name);
            mTravlerText = (FontTextView)itemLayoutView.findViewById(R.id.travler_text);

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
    public TravlerAdapter(ArrayList<UserDataVO> myDataset, Context context, TravlersFragment fragment) {
        mArrayList = myDataset;
        mContext = context;
        travelFragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TravlerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travler_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        UserDataVO user = mArrayList.get(position);
        holder.mTravlerName.setText(user.getFirstname());
        if(HGBUtility.isUserDataValid(user)){
            holder.mTravlerText.setText(user.getPhone()+"\n\n"+user.getAddress()+"\n"+user.getCity()+","+user.getState()+","+user.getCountry());
            holder.mTravlerText.setTextColor(mContext.getResources().getColor(R.color.hgb_blue));

        }else{

            holder.mTravlerText.setText("~ More information required ~");
            holder.mTravlerText.setTextColor(mContext.getResources().getColor(R.color.red_button_color));
            travelFragment.deselectNext();
        }
     //   holder.mTravlerText.setText("$"+String.valueOf((int) nodesVO.getmMinimumAmount()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }




}