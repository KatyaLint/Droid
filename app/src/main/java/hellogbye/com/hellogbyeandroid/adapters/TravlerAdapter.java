package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class TravlerAdapter extends RecyclerView.Adapter<TravlerAdapter.ViewHolder> {


    private Fragment travelFragment;

    private ArrayList<UserDataVO> mArrayList;
    private OnItemClickListener  mItemClickListner;


    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public FontTextView childNametext;
        public FontTextView childDOB;
        public FontTextView childPhone;
        public FontTextView childAddress;
        public FontTextView childEmail;



        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            childNametext = (FontTextView) itemLayoutView.findViewById(R.id.travler_name_entry);
            childDOB = (FontTextView) itemLayoutView.findViewById(R.id.travler_dob_entry);
            childPhone = (FontTextView) itemLayoutView.findViewById(R.id.travler_phone_entry);
            childAddress = (FontTextView) itemLayoutView.findViewById(R.id.travler_address_entry);
            childEmail = (FontTextView) itemLayoutView.findViewById(R.id.travler_email_entry);

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

    public TravlerAdapter(ArrayList<UserDataVO> myDataset,Context context) {

        mArrayList = myDataset;
        mContext = context;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public TravlerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.travler_items_layout, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        UserDataVO child = mArrayList.get(position);


        holder.childNametext.setText(child.getFirstname() + " " + child.getLastname());
        holder.childDOB.setText(HGBUtilityDate.parseDateToddMMyyyyForPayment(child.getDob()));
        holder.childPhone.setText(child.getPhone());
        holder.childAddress.setText(child.getAddress() + "\n" + child.getCity() + "," + child.getState() + "\n" + child.getPostalcode());
        holder.childEmail.setText(child.getEmailaddress());
//        if(HGBUtility.isUserDataValid(user)){
//            holder.mTravlerText.setText(user.getPhone()+"\n\n"+user.getAddress()+"\n"+user.getCity()+","+user.getState()+","+user.getCountry());
//            holder.mTravlerText.setTextColor(mContext.getResources().getColor(R.color.hgb_blue));
//
//        }else{
//
//            holder.mTravlerText.setText("~ More information required ~");
//            holder.mTravlerText.setTextColor(mContext.getResources().getColor(R.color.red_button_color));

        }
     //   holder.mTravlerText.setText("$"+String.valueOf((int) nodesVO.getmMinimumAmount()));



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }




}