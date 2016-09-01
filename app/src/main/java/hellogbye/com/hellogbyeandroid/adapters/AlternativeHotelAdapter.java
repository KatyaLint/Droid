package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class AlternativeHotelAdapter extends RecyclerView.Adapter<AlternativeHotelAdapter.ViewHolder> {
    private ArrayList<NodesVO> mArrayList;
    private OnLinearLayoutClickListener  onLinearLayoutClickListner;
    private OnSelectItemClickListener  onSelectItemClick;
    private NodesVO mCurrentNode;
    private NodesVO mMyNode;
    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder


    public  class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public FontTextView mHotelName;
        public FontTextView mHotelPrice;
        public FontTextView mSelectTextView;
        public ImageView mStart1ImageView;
        public ImageView mStart2ImageView;
        public ImageView mStart3ImageView;
        public ImageView mStart4ImageView;
        public ImageView mStart5ImageView;
        private View mTopView;
        private LinearLayout mLinearLayout;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            mHotelName = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_name);
            mHotelPrice = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_price);
            mStart1ImageView = (ImageView) itemLayoutView.findViewById(R.id.star1);
            mStart2ImageView = (ImageView) itemLayoutView.findViewById(R.id.star2);
            mStart3ImageView = (ImageView) itemLayoutView.findViewById(R.id.star3);
            mStart4ImageView = (ImageView) itemLayoutView.findViewById(R.id.star4);
            mStart5ImageView = (ImageView) itemLayoutView.findViewById(R.id.star5);
            mSelectTextView = (FontTextView)itemLayoutView.findViewById(R.id.select);
            mLinearLayout = (LinearLayout) itemLayoutView.findViewById(R.id.alternate_hotel_item_left_ll);
            mTopView = itemLayoutView.findViewById(R.id.top_selected_view);

            mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onLinearLayoutClickListner != null){
                        onLinearLayoutClickListner.onItemClick(view,getAdapterPosition());
                    }
                }
            });

            mSelectTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onSelectItemClick != null){
                        onSelectItemClick.onSelectItemClick(view,getAdapterPosition());
                    }
                }
            });


        }

    }


    public interface OnLinearLayoutClickListener {
        void onItemClick(View view , int position);
    }

    public interface OnSelectItemClickListener {
        void onSelectItemClick(View view , int position);
    }



    public void SetOnItemClickListener(final OnLinearLayoutClickListener mItemClickListener) {
        onLinearLayoutClickListner = mItemClickListener;
    }

    public void SetOnSelectClickListener(final OnSelectItemClickListener mItemClickListener) {
        onSelectItemClick = mItemClickListener;
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public AlternativeHotelAdapter(ArrayList<NodesVO> myDataset,NodesVO currentNode,Context context) {
        mArrayList = myDataset;
        mCurrentNode = currentNode;
        mMyNode = mCurrentNode;
        mContext = context;
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
        holder.mHotelName.setText(position+1+"."+nodesVO.getmHotelName());
        holder.mHotelPrice.setText("$"+String.valueOf((int) nodesVO.getmMinimumAmount()));



        if(mCurrentNode.getmHotelCode().equals(nodesVO.getmHotelCode())){

            holder.mSelectTextView.setText("MY HOTEL");
            holder.mSelectTextView.setTextColor(ContextCompat.getColor(mContext,R.color.red_button_color));
            holder.mSelectTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check, 0, 0, 0);
            holder.mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red_button_color));


        }else{
            holder.mSelectTextView.setText("SELECTED");
            holder.mSelectTextView.setTextColor(ContextCompat.getColor(mContext,R.color.dark_grey));
            holder.mSelectTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            holder.mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.warm_grey));
        }

        if(mMyNode.getmHotelCode().equals(nodesVO.getmHotelCode())){
            holder.mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red_button_color));
            holder.mHotelName.setTextColor(ContextCompat.getColor(mContext,R.color.blue_my_trip));
            holder.mHotelPrice.setTextColor(ContextCompat.getColor(mContext,R.color.blue_my_trip));
            setStarRating(true,holder,nodesVO.getmStarRating());
        }else{
            holder.mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.warm_grey));
            holder.mHotelName.setTextColor(ContextCompat.getColor(mContext,R.color.warm_grey));
            holder.mHotelPrice.setTextColor(ContextCompat.getColor(mContext,R.color.warm_grey));
            setStarRating(false,holder,nodesVO.getmStarRating());
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mArrayList.size();
    }




    private void setStarRating(boolean isSelected,ViewHolder holder, float star) {

        if ("0.5".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.half_star,R.drawable.empty_star,
                    R.drawable.empty_star, R.drawable.empty_star,R.drawable.empty_star);

        } else if ("1.0".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.full_star,R.drawable.empty_star,
                    R.drawable.empty_star, R.drawable.empty_star,R.drawable.empty_star);

        } else if ("1.5".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.full_star,R.drawable.half_star,
                    R.drawable.empty_star, R.drawable.empty_star,R.drawable.empty_star);

        } else if ("2.0".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.empty_star, R.drawable.empty_star,R.drawable.empty_star);
        } else if ("2.5".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.half_star, R.drawable.empty_star,R.drawable.empty_star);
        } else if ("3.0".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.empty_star,R.drawable.empty_star);
        } else if ("3.5".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.half_star,R.drawable.empty_star);

        } else if ("4.0".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.full_star,R.drawable.empty_star);

        } else if ("4.5".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.full_star,R.drawable.half_star);

        } else if ("5.0".equals(String.valueOf(star))) {
            starHolder(isSelected,holder,R.drawable.full_star,R.drawable.full_star,
                    R.drawable.full_star, R.drawable.full_star,R.drawable.full_star);

        }
    }

    private void starHolder(boolean isSelected,ViewHolder holder,int firstStar, int secondStar, int thirdStar, int fourStar, int fiveStar){
        holder.mStart1ImageView.setBackgroundResource(firstStar);
        holder.mStart2ImageView.setBackgroundResource(secondStar);
        holder.mStart3ImageView.setBackgroundResource(thirdStar);
        holder.mStart4ImageView.setBackgroundResource(fourStar);
        holder.mStart5ImageView.setBackgroundResource(fiveStar);

        if(isSelected){
            holder.mStart1ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.blue_my_trip));
            holder.mStart2ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.blue_my_trip));
            holder.mStart3ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.blue_my_trip));
            holder.mStart4ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.blue_my_trip));
            holder.mStart5ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.blue_my_trip));
        }else{
            holder.mStart1ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.warm_grey));
            holder.mStart2ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.warm_grey));
            holder.mStart3ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.warm_grey));
            holder.mStart4ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.warm_grey));
            holder.mStart5ImageView.setColorFilter(ContextCompat.getColor(mContext,R.color.warm_grey));
        }


    }

    public void setmMyNode(NodesVO mMyNode) {
        this.mMyNode = mMyNode;
    }
}