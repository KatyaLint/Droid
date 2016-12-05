package hellogbye.com.hellogbyeandroid.adapters.flights;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.models.vo.flights.FairclassPreferencesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


public class AlternativeFlightFareClassAdapter extends PagerAdapter {

  //  private LegsVO mDataset;
    private ImageLoader imageLoader;
    private Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<FairclassPreferencesVO> dataset;
    private AlternativeFlightFragment.IFareClassClickListener onClickFareClass;


    public AlternativeFlightFareClassAdapter(Context context) {
      //  this.mDataset = dataset;
        mContext = context;
    }

    public AlternativeFlightFareClassAdapter(ArrayList<FairclassPreferencesVO> dropDownOptions, Context mContext) {
        this.dataset = dropDownOptions;
        this.mContext = mContext;
    }

    public void setDataset(ArrayList<FairclassPreferencesVO> dataset){
        this.dataset = dataset;
    }


    @Override
    public int getItemPosition(Object object){
        return PagerAdapter.POSITION_NONE;
    }


    @Override public float getPageWidth(int position) { return(0.6f); }

    @Override
    public int getCount() {
        return this.dataset.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

         final ImageView flight_details_fareclass_image;
         FontTextView flight_details_fareclass_type;
         FontTextView flight_details_fareclass_price;
         ImageView flight_details_fareclass_selected;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemLayoutView = layoutInflater.inflate(R.layout.flight_details_gallery, container, false);
    /*    if(!dataset.isEmpty())
        {
            itemLayoutView.setVisibility(View.VISIBLE);
        }*/

        FairclassPreferencesVO fareClassPreference = this.dataset.get(position);
        flight_details_fareclass_image = (ImageView) itemLayoutView.findViewById(R.id.flight_details_fareclass_image);
        flight_details_fareclass_type = (FontTextView) itemLayoutView.findViewById(R.id.flight_details_fareclass_type);
        flight_details_fareclass_price= (FontTextView) itemLayoutView.findViewById(R.id.flight_details_fareclass_price);
        flight_details_fareclass_selected = (ImageView)itemLayoutView.findViewById(R.id.flight_details_fareclass_selected);


        flight_details_fareclass_price.setText("" + fareClassPreference.getCost());

        String fareClassType = fareClassPreference.getFareclass();
        if(fareClassType.equals("Economy")){
            flight_details_fareclass_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.economy_class));
        }else{
            flight_details_fareclass_image.setImageDrawable(mContext.getResources().getDrawable(R.drawable.business_class));
        }


        flight_details_fareclass_type.setText(fareClassPreference.getFarepreference());


        if(fareClassPreference.isalternative()){
            flight_details_fareclass_selected.setVisibility(View.GONE);
        }else{
            flight_details_fareclass_selected.setVisibility(View.VISIBLE);
        }
        flight_details_fareclass_image.setTag(position);
        flight_details_fareclass_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String position = flight_details_fareclass_image.getTag().toString();
                onClickFareClass.onFareClassClicked(position);
            }
        });
/*
        RoomsVO room = mArrayList.get(position);
        if(imageLoader == null){
            imageLoader = ImageLoader.getInstance();
        }

        if(room.getmImages().size()> 0 ){
            imageLoader.displayImage(room.getmImages().get(0), flight_details_fareclass_image);
        }
        else{
            flight_details_fareclass_image.setBackgroundResource(R.drawable.room_placeholder);
        }
*/



/*        mRoomPriceFontTextView.setText(""+room.getmCost());
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
        });*/
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

    public void setOnClickFareClass(AlternativeFlightFragment.IFareClassClickListener onClickFareClass) {
        this.onClickFareClass = onClickFareClass;
    }


}
