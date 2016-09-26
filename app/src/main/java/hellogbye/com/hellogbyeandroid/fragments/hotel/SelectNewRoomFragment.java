package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.Amenities;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.ExpandableHeightGridView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static com.google.android.gms.analytics.internal.zzy.v;

/**
 * Created by arisprung on 9/25/16.
 */

public class SelectNewRoomFragment extends HGBAbstractFragment {


    private static final int NUMBER_OF_SHORT_AMNITIES = 8;
    private ExpandableHeightGridView mGridViewLong;
    private ExpandableHeightGridView mGridViewShort;
    private ImageView mMainImageView;
    private FontTextView mPrice;
    private FontTextView mType;
    private FontTextView mShowMore;



    public static Fragment newInstance(int position) {
        Fragment fragment = new SelectNewRoomFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_room_layout, container, false);
        mGridViewLong = (ExpandableHeightGridView) rootView.findViewById(R.id.room_grid_long);
        mGridViewShort = (ExpandableHeightGridView) rootView.findViewById(R.id.room_grid_short);
        mShowMore = (FontTextView) rootView.findViewById(R.id.show_more);

        mGridViewShort.setExpanded(true);
        mGridViewLong.setExpanded(true);
        mMainImageView = (ImageView) rootView.findViewById(R.id.main_image_room);
        mPrice = (FontTextView) rootView.findViewById(R.id.room_price);
        mType = (FontTextView) rootView.findViewById(R.id.room_type);


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String strValue = getArguments().getString("alternative_rooms", "");
        Type roomType = new TypeToken<RoomsVO>() {
        }.getType();

        Gson gson = new Gson();
        RoomsVO room = gson.fromJson(strValue, roomType);
        mType.setText(room.getmRoomType());
        mPrice.setText(room.getmCost()+" USD/NIGHT");
        ImageLoader  imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(room.getmImages().get(0), mMainImageView);
        CustomDetailAmenitiesGridAdapter adapterLong = new CustomDetailAmenitiesGridAdapter(getActivity(),getShortAdapter(room.getmAmenities()));
        CustomDetailAmenitiesGridAdapter adapterShort = new CustomDetailAmenitiesGridAdapter(getActivity(),room.getmAmenities() );
        mGridViewLong.setAdapter(adapterLong);
        mGridViewShort.setAdapter(adapterShort);


        mShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FontTextView tv = (FontTextView) view;
                String str = tv.getText().toString();
                if(str.equals(R.string.show_more)){
                    showLongGrid();
                }else{
                    showShortGrid();
                }
            }
        });
    }

    private ArrayList<Amenities> getShortAdapter(ArrayList<Amenities> amenities) {

        ArrayList<Amenities> list = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_SHORT_AMNITIES; i++) {
            list.add(amenities.get(i));
        }

        return list;
    }

    private void showShortGrid(){
        mShowMore.setText(R.string.show_more);
        mGridViewLong.setVisibility(View.GONE);
        mGridViewShort.setVisibility(View.VISIBLE);

    }

    private void showLongGrid(){

        mShowMore.setText(R.string.show_less);
        mGridViewLong.setVisibility(View.VISIBLE);
        mGridViewShort.setVisibility(View.GONE);
    }
}
