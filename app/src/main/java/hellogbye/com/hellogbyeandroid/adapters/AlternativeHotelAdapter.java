package hellogbye.com.hellogbyeandroid.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class AlternativeHotelAdapter extends RecyclerView.Adapter<AlternativeHotelAdapter.ViewHolder> {
    private ArrayList<NodesVO> mArrayList;
    OnItemClickListener  mItemClickListner;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public FontTextView mHotelName;
        public FontTextView mHotelPrice;
        public FontTextView mHotelNights;
        public ImageView mStart1ImageView;
        public ImageView mStart2ImageView;
        public ImageView mStart3ImageView;
        public ImageView mStart4ImageView;
        public ImageView mStart5ImageView;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mHotelName = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_name);
            mHotelPrice = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_price);
            mHotelNights = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_nights);
            mStart1ImageView = (ImageView) itemLayoutView.findViewById(R.id.star1);
            mStart2ImageView = (ImageView) itemLayoutView.findViewById(R.id.star2);
            mStart3ImageView = (ImageView) itemLayoutView.findViewById(R.id.star3);
            mStart4ImageView = (ImageView) itemLayoutView.findViewById(R.id.star4);
            mStart5ImageView = (ImageView) itemLayoutView.findViewById(R.id.star5);
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
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mItemClickListner = mItemClickListener;
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public AlternativeHotelAdapter(ArrayList<NodesVO> myDataset) {
        mArrayList = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlternativeHotelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alternative_hotel_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        NodesVO nodesVO = mArrayList.get(position);
        holder.mHotelName.setText(nodesVO.getmHotelName());
        holder.mHotelPrice.setText("$"+String.valueOf((int) nodesVO.getmMinimumAmount()));
        holder.mHotelNights.setText(HGBUtility.getDateDiffString(nodesVO.getmCheckIn(), nodesVO.getmCheckOut()));
        setStarRating(holder,nodesVO.getmStarRating());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }




    private void setStarRating(ViewHolder holder, float star) {

        if ("0.5".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.half_star,R.drawable.empty_star,
                    R.drawable.empty_star, R.drawable.empty_star,R.drawable.empty_star);

        } else if ("1.0".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.full_star,R.drawable.empty_star,
                    R.drawable.empty_star, R.drawable.empty_star,R.drawable.empty_star);

        } else if ("1.5".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.full_star,R.drawable.half_star,
                    R.drawable.empty_star, R.drawable.empty_star,R.drawable.empty_star);

        } else if ("2.0".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.empty_star, R.drawable.empty_star,R.drawable.empty_star);
        } else if ("2.5".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.half_star, R.drawable.empty_star,R.drawable.empty_star);

        } else if ("3.0".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.empty_star,R.drawable.empty_star);
        } else if ("3.5".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.half_star,R.drawable.empty_star);

        } else if ("4.0".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.full_star,R.drawable.empty_star);

        } else if ("4.5".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.full_star,R.drawable.half_star);

        } else if ("5.0".equals(String.valueOf(star))) {
            starHolder(holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.full_star,R.drawable.full_star);

        }
    }

    private void starHolder(ViewHolder holder,int firstStar, int secondStar, int thirdStar, int fourStar, int fiveStar){
        holder.mStart1ImageView.setBackgroundResource(firstStar);
        holder.mStart2ImageView.setBackgroundResource(secondStar);
        holder.mStart3ImageView.setBackgroundResource(thirdStar);
        holder.mStart4ImageView.setBackgroundResource(fourStar);
        holder.mStart5ImageView.setBackgroundResource(fiveStar);
    }

}