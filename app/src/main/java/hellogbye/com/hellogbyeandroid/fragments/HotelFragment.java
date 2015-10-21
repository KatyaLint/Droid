package hellogbye.com.hellogbyeandroid.fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.ImageGalleryActivity;
import hellogbye.com.hellogbyeandroid.models.vo.flights.AllImagesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 9/30/15.
 */
public class HotelFragment extends HGBAbtsractFragment {

    private MapFragment fragment;
    private GoogleMap mMap;
    private TableLayout mTableLayout;
    ArrayList<String> mImageList;
    private SlidingUpPanelLayout mSlidingPanels;
    private static final String TAG = "HotelFragment";
    private boolean mStartedSliding;
    private ScrollView mScrollView;
    private FontTextView mHotelNameFontTextView;
    private FontTextView mHotelPriceFontTextView;
    private FontTextView mHotelDaysFontTextView;
    private FontTextView mHotelAddressFontTextView;

    private FontTextView mHotelRoomNameFontTextView;
    private FontTextView mHotelGuestNumberFontTextView;
    private FontTextView mHotelCheckInFontTextView;
    private FontTextView mHotelCheckOutFontTextView;
    private FontTextView mAlertnativeHotelFontTextView;
    private FontTextView mHotelNameFullFontTextView;
    private FontTextView mHotelContactFontTextView;
    private FontTextView mHotelAmnitiesFontTextView;
    private FontTextView mHotelNearbyAttrictionsFontTextView;
    private UserTravelVO mTravelDetails;
    PassengersVO passengersVO;
    CellsVO cellsVO;
    NodesVO nodesVO;
    private ArrayList<String> mListForGallery;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hotel_main_layout, container, false);
        mTableLayout = (TableLayout) rootView.findViewById(R.id.tableLayout1);
        mSlidingPanels = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
        mSlidingPanels.setCoveredFadeColor(Color.TRANSPARENT);
        mSlidingPanels.setParalaxOffset(100);
        mScrollView = (ScrollView) rootView.findViewById(R.id.detail_scroll_view);

        mHotelNameFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_name);
        mHotelPriceFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_price);
        mHotelDaysFontTextView = (FontTextView) rootView.findViewById(R.id.days);
        mHotelAddressFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_address);
        mHotelRoomNameFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_room_name);
        mHotelGuestNumberFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_guest_number);
        mHotelCheckInFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_dates_checkin_name);
        mHotelCheckOutFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_dates_checkout_name);
        mAlertnativeHotelFontTextView = (FontTextView) rootView.findViewById(R.id.show_alternative_hotel);
        mHotelNameFullFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_full_address_name);
        mHotelContactFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_contact_name);
        mHotelAmnitiesFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_amnities_name);
        mHotelNearbyAttrictionsFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_nearby_attrictaions_name);

        mTravelDetails = getActivityInterface().getTravelOrder();
        passengersVO = mTravelDetails.getPassengerses().get(0);
        cellsVO = passengersVO.getmCells().get(0);
        nodesVO = cellsVO.getmNodes().get(1);

        init();


//        Animation  mAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
//                0.0f, Animation.RELATIVE_TO_SELF, -5.0f);
//        mAnimation.setDuration(5000);
//        mAnimation.setFillAfter(true);
//        mScrollView.setAnimation(mAnimation);

//        mSlidingPanels.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
//            @Override
//            public void onPanelSlide(View panel, float slideOffset) {
//                Log.i(TAG, "onPanelSlide, offset " + slideOffset);
////                Log.i(TAG, "main height=" + mSlidingPanels.findViewById(R.id.main).getHeight());
////                if(!mStartedSliding){
////                    mStartedSliding = true;
////                    mSlidingPanels.setPanelHeight(200);
////                }
//
//            }
//
//            @Override
//            public void onPanelCollapsed(View panel) {
//                Log.i(TAG, "onPanelCollapsed");
//  //              Log.i(TAG, "main height=" + mSlidingPanels.findViewById(R.id.main).getHeight());
//            }
//
//            @Override
//            public void onPanelExpanded(View panel) {
//                Log.i(TAG, "onPanelExpanded");
//      ///          Log.i(TAG, "main height=" + mSlidingPanels.findViewById(R.id.main).getHeight());
//            }
//
//            @Override
//            public void onPanelAnchored(View panel) {
//                Log.i(TAG, "onPanelAnchored");
//      //          Log.i(TAG, "main height=" + mSlidingPanels.findViewById(R.id.main).getHeight());
//            }
//
//            @Override
//            public void onPanelHidden(View panel) {
//
//            }
//        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();

        fragment = ((MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map));//(SupportMapFragment) fm.findFragmentById(R.id.map);
        if (fragment == null) {
            fragment = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap = fragment.getMap();

        if (mMap != null) {
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.setMyLocationEnabled(true);

            LatLng currentHotelLatLon = new LatLng(nodesVO.getmLatitude(), nodesVO.getmLongitude());
            Marker currentHotel = mMap.addMarker(new MarkerOptions()
                    .position(currentHotelLatLon)

                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_selected_hotel_blue)));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentHotelLatLon)      // Sets the center of the map to Mountain View
                    .zoom(10)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            Log.d("DEBUG", "map is null");
        }
    }

    private void buildTable(int cols) {
        // outer for loop
        for (int i = 1; i <= 2; i++) {
            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for (int j = 1; j <= cols; j++) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ImageView image = (ImageView) inflater.inflate(R.layout.hotel_gallery_imageview, null);

                //  tv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                if (mImageList.size() > 0) {
                    String strValue = mImageList.remove(0);
                    HGBUtility.loadHotelImage(getActivity().getApplicationContext(), strValue, image);
                    row.addView(image);
                }
            }
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), ImageGalleryActivity.class);

                    intent.putStringArrayListExtra("images", mListForGallery);
                    startActivity(intent);
                }
            });
            mTableLayout.addView(row);
        }
    }

    private void init() {

        mListForGallery = new ArrayList<>();
        mHotelNameFontTextView.setText(nodesVO.getmHotelName());
        mHotelPriceFontTextView.setText(String.valueOf(nodesVO.getmMinimumAmount()));
        //  mHotelDaysFontTextView = (FontTextView)rootView.findViewById(R.id.days);
        mHotelAddressFontTextView.setText(nodesVO.getmAddress1());
        mHotelRoomNameFontTextView.setText(nodesVO.getmDefaultRoomGuid());
        // mHotelGuestNumberFontTextView.setText(mTravelDetails.getPassengerses().get(1).getmCells().get(0).getmNodes().get(0).getm());
        mHotelCheckInFontTextView.setText(nodesVO.getmCheckIn());
        mHotelCheckOutFontTextView.setText(nodesVO.getmCheckOut());

        mHotelNameFullFontTextView.setText(nodesVO.getmHotelName());
        mHotelAmnitiesFontTextView.setText(Html.fromHtml(nodesVO.getmAmenities()));
        mHotelContactFontTextView.setText(nodesVO.getmPhone());
        mHotelNearbyAttrictionsFontTextView.setText(Html.fromHtml(nodesVO.getmShortDescription()));
        mImageList = new ArrayList<>();
        for (AllImagesVO image : nodesVO.getAllImagesVOs()) {
            mImageList.add(image.getmImage());
            mListForGallery.add(image.getmImage());
        }
        buildTable(mImageList.size() / 2);//

    }

}
