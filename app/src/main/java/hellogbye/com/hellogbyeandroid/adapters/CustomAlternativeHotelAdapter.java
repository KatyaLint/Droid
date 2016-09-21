package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by arisprung on 9/21/16.
 */

public class CustomAlternativeHotelAdapter extends PagerAdapter {

    private ArrayList<NodesVO> mArrayList;
    private NodesVO mCurrentNode;
    private NodesVO mMyNode;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private OnLinearLayoutClickListener onLinearLayoutClickListner;
    private OnSelectItemClickListener onSelectItemClick;

  

    public CustomAlternativeHotelAdapter(ArrayList<NodesVO> myDataset, NodesVO currentNode, Context context) {
        mArrayList = myDataset;
        mCurrentNode = currentNode;
        mMyNode = mCurrentNode;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }


    @Override public float getPageWidth(int position) { return(0.45f); }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        // Declare Variables
         FontTextView mHotelName;
         FontTextView mHotelPrice;
         FontTextView mSelectTextView;


         View mTopView;
         LinearLayout mLinearLayout;
        NodesVO nodesVO = mArrayList.get(position);
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemLayoutView = layoutInflater.inflate(R.layout.alternative_hotel_item, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        mHotelName = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_name);
        mHotelPrice = (FontTextView)itemLayoutView.findViewById(R.id.alt_hotel_price);

        mSelectTextView = (FontTextView)itemLayoutView.findViewById(R.id.select);
        mLinearLayout = (LinearLayout) itemLayoutView.findViewById(R.id.alternate_hotel_item_left_ll);
        mTopView = itemLayoutView.findViewById(R.id.top_selected_view);

        mHotelName.setText(position+1+"."+nodesVO.getmHotelName());
        mHotelPrice.setText("$"+String.valueOf((int) nodesVO.getmMinimumAmount()));

        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLinearLayoutClickListner != null){
                    onLinearLayoutClickListner.onItemClick(view,position);
                }
            }
        });

        mSelectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSelectItemClick != null){
                    onSelectItemClick.onSelectItemClick(view,position);
                }
            }
        });



        if(mCurrentNode.getmHotelCode().equals(nodesVO.getmHotelCode())){

            mSelectTextView.setText("MY HOTEL");
            mSelectTextView.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_EE3A3C));
            mSelectTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check, 0, 0, 0);
            mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.COLOR_EE3A3C));


        }else{
            mSelectTextView.setText("SELECTED");
            mSelectTextView.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
            mSelectTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
        }

        if(mMyNode.getmHotelCode().equals(nodesVO.getmHotelCode())){
            mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.COLOR_EE3A3C));
            mHotelName.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_003D4C));
            mHotelPrice.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_003D4C));
            setStarRating(true,itemLayoutView,nodesVO.getmStarRating());
        }else{
            mTopView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
            mHotelName.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
            mHotelPrice.setTextColor(ContextCompat.getColor(mContext,R.color.COLOR_565656));
            setStarRating(false,itemLayoutView,nodesVO.getmStarRating());
        }

        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemLayoutView);

        return itemLayoutView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    private void setStarRating(boolean isSelected, View view, float star) {

        if ("0.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_half,R.drawable.star_blue_out,
                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_half,R.drawable.gray_star_out,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }


        } else if ("1.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_out,
                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_out,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }


        } else if ("1.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_half,
                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_half,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }


        } else if ("2.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_out, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }

        } else if ("2.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_half, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_half, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }

        } else if ("3.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_out,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }

        } else if ("3.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_half,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_half,R.drawable.gray_star_out);
            }


        } else if ("4.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_full,R.drawable.star_blue_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_out);
            }


        } else if ("4.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_full,R.drawable.star_blue_half);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_half);
            }


        } else if ("5.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.star_blue_full,R.drawable.star_blue_full,
                        R.drawable.star_blue_full, R.drawable.star_blue_full,R.drawable.star_blue_full);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_full);
            }


        }
    }

    private void starHolder(View view, int firstStar, int secondStar, int thirdStar, int fourStar, int fiveStar){
        view.findViewById(R.id.star1).setBackgroundResource(firstStar);
        view.findViewById(R.id.star2).setBackgroundResource(secondStar);
        view.findViewById(R.id.star3).setBackgroundResource(thirdStar);
        view.findViewById(R.id.star4).setBackgroundResource(fourStar);
        view.findViewById(R.id.star5).setBackgroundResource(fiveStar);
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
    public void setmMyNode(NodesVO mMyNode) {
        this.mMyNode = mMyNode;
    }

}
