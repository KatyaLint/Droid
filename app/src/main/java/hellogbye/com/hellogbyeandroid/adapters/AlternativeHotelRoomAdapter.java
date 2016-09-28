package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.creditcardadapters.CreditCardAdapter;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


public class AlternativeHotelRoomAdapter extends PagerAdapter {

    private ArrayList<RoomsVO> mArrayList;
    private  ImageLoader imageLoader;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private CustomAlternativeHotelAdapter.OnLinearLayoutClickListener onLinearLayoutClickListner;




    public AlternativeHotelRoomAdapter(ArrayList<RoomsVO> myDataset, Context context) {
        mArrayList = myDataset;

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


    @Override public float getPageWidth(int position) { return(0.8f); }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

         ImageView mRoomImageView;
         FontTextView mRoomPriceFontTextView;
         FontTextView mRoomTypeFontTextView;
         ImageView mRoomSelectedImageView;

        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemLayoutView = layoutInflater.inflate(R.layout.alt_room_item_layout, container,
                false);

        mRoomImageView = (ImageView) itemLayoutView.findViewById(R.id.room_image);
        mRoomTypeFontTextView = (FontTextView) itemLayoutView.findViewById(R.id.room_type);
        mRoomPriceFontTextView= (FontTextView) itemLayoutView.findViewById(R.id.room_price);
        mRoomSelectedImageView = (ImageView)itemLayoutView.findViewById(R.id.room_selected);
        RoomsVO room = mArrayList.get(position);
        if(imageLoader == null){
            imageLoader = ImageLoader.getInstance();
        }

        if(room.getmImages().size()> 0 ){
            imageLoader.displayImage(room.getmImages().get(0), mRoomImageView);
        }
        else{
            mRoomImageView.setBackgroundResource(R.drawable.room_placeholder);
        }



        mRoomPriceFontTextView.setText(""+room.getmCost());
        mRoomTypeFontTextView.setText("" + room.getmRoomType());

        if(room.ismIsAlternative()){
            mRoomSelectedImageView.setVisibility(View.GONE);
        }else{
            mRoomSelectedImageView.setVisibility(View.VISIBLE);
        }

        mRoomImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onLinearLayoutClickListner != null){
                    onLinearLayoutClickListner.onItemClick(view,position);
                }
            }
        });
        container.addView(itemLayoutView);
        return itemLayoutView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((CardView) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }



    public interface OnLinearLayoutClickListener {
        void onItemClick(View view , int position);
    }



    public void SetOnItemClickListener(final CustomAlternativeHotelAdapter.OnLinearLayoutClickListener mItemClickListener) {
        onLinearLayoutClickListner = mItemClickListener;
    }



}
