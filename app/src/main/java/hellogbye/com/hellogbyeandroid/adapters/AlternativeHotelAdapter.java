//package hellogbye.com.hellogbyeandroid.adapters;
//
//import android.content.Context;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.AppCompatImageView;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import java.util.ArrayList;
//
//import hellogbye.com.hellogbyeandroid.R;
//import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
//import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
//import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
//import hellogbye.com.hellogbyeandroid.views.FontTextView;
//
///**
// * Created by arisprung on 10/27/15.
// */
//public class AlternativeHotelAdapter extends RecyclerView.Adapter<AlternativeHotelAdapter.ViewHolder> {
//    private ArrayList<NodesVO> mArrayList;
//    private OnLinearLayoutClickListener  onLinearLayoutClickListner;
//    private OnSelectItemClickListener  onSelectItemClick;
//    private NodesVO mCurrentNode;
//    private NodesVO mMyNode;
//    private Context mContext;
//
//
//
//    public  class ViewHolder extends RecyclerView.ViewHolder {
//        // each data item is just a string in this case
//        public FontTextView mHotelName;
//        public FontTextView mHotelPrice;
//        public FontTextView mSelectTextView;
//        public ImageView mStart1ImageView;
//        public ImageView mStart2ImageView;
//        public ImageView mStart3ImageView;
//        public ImageView mStart4ImageView;
//        public ImageView mStart5ImageView;
//        private View mTopView;
//        private LinearLayout mLinearLayout;
//
//        public ViewHolder(View itemLayoutView) {
//            super(itemLayoutView);
//            mHotelName = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_name);
//            mHotelPrice = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_price);
//            mStart1ImageView = (ImageView) itemLayoutView.findViewById(R.id.star1);
//            mStart2ImageView = (ImageView) itemLayoutView.findViewById(R.id.star2);
//            mStart3ImageView = (ImageView) itemLayoutView.findViewById(R.id.star3);
//            mStart4ImageView = (ImageView) itemLayoutView.findViewById(R.id.star4);
//            mStart5ImageView = (ImageView) itemLayoutView.findViewById(R.id.star5);
//            mSelectTextView = (FontTextView)itemLayoutView.findViewById(R.id.select);
//            mLinearLayout = (LinearLayout) itemLayoutView.findViewById(R.id.alternate_hotel_item_left_ll);
//            mTopView = itemLayoutView.findViewById(R.id.top_selected_view);
//
//            mLinearLayout.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onLinearLayoutClickListner != null){
//                        onLinearLayoutClickListner.onItemClick(view,getAdapterPosition());
//                    }
//                }
//            });
//
//            mSelectTextView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onSelectItemClick != null){
//                        onSelectItemClick.onSelectItemClick(view,getAdapterPosition());
//                    }
//                }
//            });
//
//
//        }
//
//    }
//
//
//    public interface OnLinearLayoutClickListener {
//        void onItemClick(View view , int position);
//    }
//
//    public interface OnSelectItemClickListener {
//        void onSelectItemClick(View view , int position);
//    }
//
//
//
//    public void SetOnItemClickListener(final OnLinearLayoutClickListener mItemClickListener) {
//        onLinearLayoutClickListner = mItemClickListener;
//    }
//
//    public void SetOnSelectClickListener(final OnSelectItemClickListener mItemClickListener) {
//        onSelectItemClick = mItemClickListener;
//    }
//
//
//
//    // Provide a suitable constructor (depends on the kind of dataset)
//    public AlternativeHotelAdapter(ArrayList<NodesVO> myDataset,NodesVO currentNode,Context context) {
//        mArrayList = myDataset;
//        mCurrentNode = currentNode;
//        mMyNode = mCurrentNode;
//        mContext = context;
//    }
//
//    // Create new views (invoked by the layout manager)
//    @Override
//    public AlternativeHotelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
//                                                                 int viewType) {
//        // create a new view
//        View v = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.alternative_hotel_item, parent, false);
//
//        ViewHolder vh = new ViewHolder(v);
//        return vh;
//    }
//
//
//
//    // Replace the contents of a view (invoked by the layout manager)
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        NodesVO nodesVO = mArrayList.get(position);
//        holder.mHotelName.setText(position+1+"."+nodesVO.getmHotelName());
//        holder.mHotelPrice.setText("$"+String.valueOf((int) nodesVO.getmMinimumAmount()));
//
//
//
//        if(mCurrentNode.getmHotelCode().equals(nodesVO.getmHotelCode())){
//
//            holder.mSelectTextView.setText("MY HOTEL");
//            holder.mSelectTextView.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_EE3A3C));
//            holder.mSelectTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check, 0, 0, 0);
//            holder.mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.COLOR_EE3A3C));
//
//
//        }else{
//            holder.mSelectTextView.setText("SELECTED");
//            holder.mSelectTextView.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
//            holder.mSelectTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            holder.mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
//        }
//
//        if(mMyNode.getmHotelCode().equals(nodesVO.getmHotelCode())){
//            holder.mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.COLOR_EE3A3C));
//            holder.mHotelName.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_003D4C));
//            holder.mHotelPrice.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_003D4C));
//            setStarRating(true,holder,nodesVO.getmStarRating());
//        }else{
//            holder.mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
//            holder.mHotelName.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
//            holder.mHotelPrice.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
//            setStarRating(false,holder,nodesVO.getmStarRating());
//        }
//
//    }
//
//    // Return the size of your dataset (invoked by the layout manager)
//    @Override
//    public int getItemCount() {
//        return mArrayList.size();
//    }
//
//
//
//
//    private void setStarRating(boolean isSelected,ViewHolder holder, float star) {
//
//        if ("0.5".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_half,R.drawable.star_blue_out,
//                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
//            }else{
//                starHolder(holder,R.drawable.gray_star_half,R.drawable.gray_star_out,
//                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
//            }
//
//
//        } else if ("1.0".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_full,R.drawable.star_blue_out,
//                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
//            }else{
//                starHolder(holder,R.drawable.gray_star_full,R.drawable.gray_star_out,
//                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
//            }
//
//
//        } else if ("1.5".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_full,R.drawable.star_blue_half,
//                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
//            }else{
//                starHolder(holder,R.drawable.gray_star_full,R.drawable.gray_star_half,
//                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
//            }
//
//
//        } else if ("2.0".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_full,R.drawable.star_blue_full,
//                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
//            }else{
//                starHolder(holder,R.drawable.gray_star_full,R.drawable.gray_star_full,
//                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
//            }
//
//        } else if ("2.5".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_full,R.drawable.star_blue_full,
//                        R.drawable.star_blue_half, R.drawable.star_blue_out,R.drawable.star_blue_out);
//            }else{
//                starHolder(holder,R.drawable.gray_star_full,R.drawable.gray_star_full,
//                        R.drawable.gray_star_half, R.drawable.gray_star_out,R.drawable.gray_star_out);
//            }
//
//        } else if ("3.0".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_full,R.drawable.star_blue_full,
//                        R.drawable.star_blue_full, R.drawable.star_blue_out,R.drawable.star_blue_out);
//            }else{
//                starHolder(holder,R.drawable.gray_star_full,R.drawable.gray_star_full,
//                        R.drawable.gray_star_full, R.drawable.gray_star_out,R.drawable.gray_star_out);
//            }
//
//        } else if ("3.5".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_full,R.drawable.star_blue_full,
//                        R.drawable.star_blue_full, R.drawable.star_blue_half,R.drawable.star_blue_out);
//            }else{
//                starHolder(holder,R.drawable.gray_star_full,R.drawable.gray_star_full,
//                        R.drawable.gray_star_full, R.drawable.gray_star_half,R.drawable.gray_star_out);
//            }
//
//
//        } else if ("4.0".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_full,R.drawable.star_blue_full,
//                        R.drawable.star_blue_full, R.drawable.star_blue_full,R.drawable.star_blue_out);
//            }else{
//                starHolder(holder,R.drawable.gray_star_full,R.drawable.gray_star_full,
//                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_out);
//            }
//
//
//        } else if ("4.5".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_full,R.drawable.star_blue_full,
//                        R.drawable.star_blue_full, R.drawable.star_blue_full,R.drawable.star_blue_half);
//            }else{
//                starHolder(holder,R.drawable.gray_star_full,R.drawable.gray_star_full,
//                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_half);
//            }
//
//
//        } else if ("5.0".equals(String.valueOf(star))) {
//            if(isSelected){
//                starHolder(holder,R.drawable.star_blue_full,R.drawable.star_blue_full,
//                        R.drawable.star_blue_full, R.drawable.star_blue_full,R.drawable.star_blue_full);
//            }else{
//                starHolder(holder,R.drawable.gray_star_full,R.drawable.gray_star_full,
//                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_full);
//            }
//
//
//        }
//    }
//
//    private void starHolder(ViewHolder holder,int firstStar, int secondStar, int thirdStar, int fourStar, int fiveStar){
//        holder.mStart1ImageView.setBackgroundResource(firstStar);
//        holder.mStart2ImageView.setBackgroundResource(secondStar);
//        holder.mStart3ImageView.setBackgroundResource(thirdStar);
//        holder.mStart4ImageView.setBackgroundResource(fourStar);
//        holder.mStart5ImageView.setBackgroundResource(fiveStar);
//    }
//
//    public void setmMyNode(NodesVO mMyNode) {
//        this.mMyNode = mMyNode;
//    }
//}