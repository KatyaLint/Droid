package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.RefreshComplete;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.Amenities;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.ExpandableHeightGridView;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 9/25/16.
 */

public class SelectNewRoomFragment extends HGBAbstractFragment {


    private static  int NUMBER_OF_SHORT_AMNITIES = 8;
    private ExpandableHeightGridView mGridViewLong;
    private ExpandableHeightGridView mGridViewShort;
    private ImageView mMainImageView;
    private FontTextView mPrice;
    private FontTextView mType;
    private FontTextView mShowMore;
    private FontTextView mCapaity;
    private FontTextView mPolicy;
    private FontButtonView mSelectRoom;
    private RoomsVO mSelectedRoom;
    private String  mPax;



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
        mCapaity = (FontTextView) rootView.findViewById(R.id.capacity);
        mPolicy = (FontTextView) rootView.findViewById(R.id.policy);
        mSelectRoom= (FontButtonView) rootView.findViewById(R.id.select_room);
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
        mSelectedRoom = gson.fromJson(strValue, roomType);
        mPax = getArguments().getString("paxid", "");
        mType.setText(mSelectedRoom.getmRoomType());
        mPrice.setText(mSelectedRoom.getmCost()+ " USD/NIGHT");

        mCapaity.setText(mSelectedRoom.getMaxroomoccupancy()+" people capacity");
        mPolicy.setText(mSelectedRoom.getmCancellationPolicy());
        ImageLoader  imageLoader = ImageLoader.getInstance();
        if(mSelectedRoom.getmImages().size()>0){
            imageLoader.displayImage(mSelectedRoom.getmImages().get(0), mMainImageView);
        }
        CustomDetailAmenitiesGridAdapter adapterLong = new CustomDetailAmenitiesGridAdapter(getActivity(),mSelectedRoom.getmAmenities());
        CustomDetailAmenitiesGridAdapter adapterShort = new CustomDetailAmenitiesGridAdapter(getActivity(),getShortAdapter(mSelectedRoom.getmAmenities()) );
        mGridViewLong.setAdapter(adapterLong);
        mGridViewShort.setAdapter(adapterShort);
        setNumberOfPhotos();


        mShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FontTextView tv = (FontTextView) view;
                String str = tv.getText().toString();
                String str1 = getActivity().getResources().getString(R.string.show_more);
                if(str.contains(str1)){
                    showLongGrid();
                }else{
                    showShortGrid();
                }

            }
        });

        mSelectRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNewRoomToServer();
            }
        });
    }

    private void sendNewRoomToServer() {

        ConnectionManager.getInstance(getActivity()).putAlternateHotelRoom(getActivityInterface().getTravelOrder().getmSolutionID(),
                mPax,
                mSelectedRoom.getmGuid(), new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        getFlowInterface().callRefreshItineraryWithCallback(ToolBarNavEnum.HOTEL.getNavNumber(), new RefreshComplete() {
                            @Override
                            public void onRefreshSuccess(Object data) {
                                //selectedItemGuidNumber(nodesVO.getmGuid());
                                getActivity().onBackPressed();
                            }

                            @Override
                            public void onRefreshError(Object data) {

                            }
                        }, getActivityInterface().getTravelOrder().getmSolutionID());

                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });

    }

    private ArrayList<Amenities> getShortAdapter(ArrayList<Amenities> amenities) {
        ArrayList<Amenities> list = new ArrayList<>();
        if(NUMBER_OF_SHORT_AMNITIES >= amenities.size()){
            NUMBER_OF_SHORT_AMNITIES = amenities.size();
        }
        for (int i = 0; i < NUMBER_OF_SHORT_AMNITIES; i++) {
            list.add(amenities.get(i));
        }
        return list;
    }

    private void showShortGrid(){
        setNumberOfPhotos();
        mGridViewLong.setVisibility(View.GONE);
        mGridViewShort.setVisibility(View.VISIBLE);

    }

    private void setNumberOfPhotos() {
        int iNumber =mSelectedRoom.getmAmenities().size()-NUMBER_OF_SHORT_AMNITIES;
        mShowMore.setText("Show more "+"("+iNumber+")");
    }

    private void showLongGrid(){

        mShowMore.setText(R.string.show_less);
        mGridViewLong.setVisibility(View.VISIBLE);
        mGridViewShort.setVisibility(View.GONE);
    }
}
