package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.AllImagesVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


public class HotelImageAdapter extends PagerAdapter {

    private ArrayList<AllImagesVO> mArrayList;
    private ImageLoader imageLoader;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private HotelImageAdapter.OnLinearLayoutClickListener onLinearLayoutClickListner;




    public HotelImageAdapter(ArrayList<AllImagesVO> myDataset, Context context) {
        mArrayList = myDataset;

        mContext = context;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }



    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

         ImageView mHotelImage;
         FontTextView mHotelImageCount;


        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemLayoutView = layoutInflater.inflate(R.layout.hotel_image_item  , container,
                false);

        mHotelImage = (ImageView) itemLayoutView.findViewById(R.id.hotel_image);
        mHotelImageCount = (FontTextView) itemLayoutView.findViewById(R.id.gallery_number_images);

        AllImagesVO image = mArrayList.get(position);
        if(imageLoader == null){
            imageLoader = ImageLoader.getInstance();
        }
        imageLoader.displayImage(image.getmImage(), mHotelImage);
        mHotelImageCount.setText(position+1+"/"+mArrayList.size());


        mHotelImage.setOnClickListener(new View.OnClickListener() {
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
        ((ViewPager) container).removeView((RelativeLayout) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }



    public interface OnLinearLayoutClickListener {
        void onItemClick(View view, int position);
    }



    public void SetOnItemClickListener(final HotelImageAdapter.OnLinearLayoutClickListener mItemClickListener) {
        onLinearLayoutClickListner = mItemClickListener;
    }



}
