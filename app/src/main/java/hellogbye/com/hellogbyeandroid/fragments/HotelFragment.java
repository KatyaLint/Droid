package hellogbye.com.hellogbyeandroid.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.AlternativeHotelRoomAdapter;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by arisprung on 9/30/15.
 */
public class HotelFragment extends HGBAbstractFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private SupportMapFragment fragment;
    private GoogleMap mMap;
    //  private SlidingUpPanelLayout mSlidingPanels;
    private RecyclerView mRecyclerView;
    private AlternativeHotelRoomAdapter mHotelRoomAdapter;
    private FontTextView mHotelNameFontTextView;
    private FontTextView mHotelPriceFontTextView;
    private FontTextView mHotelDaysFontTextView;
    private LinearLayoutManager mLayoutManager;
    private FontTextView mAlertnativeHotelFontTextView;
    private ImageView mStart1ImageView;
    private ImageView mStart2ImageView;
    private ImageView mStart3ImageView;
    private ImageView mStart4ImageView;
    private ImageView mStart5ImageView;
    private FontTextView mRoomName;
    private FontTextView mChckInDate;
    private FontTextView mChckOutDate;

    //  private NodesVO nodesVO;
    private ArrayList<String> mListForGallery;
    private ArrayList<NodesVO> mNodeArrayList;
    private PassengersVO passengersVO;
    private NodesVO currentSelectedNode;
    private ImageView mConfirmBadge;
    private FontTextView mSelectHotel;
    private boolean isChangeHotelSelected = false;

    public final float PANEL_HIGHT = 0.4f;
    private Activity activity;

    public static boolean IS_MAIN_BACK_ALLOWED = true;

    public static Fragment newInstance(int position) {
        Fragment fragment = new HotelFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = getActivity();
        View rootView = inflater.inflate(R.layout.hotel_main_layout, container, false);
        //  mSlidingPanels = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);
        mNodeArrayList = new ArrayList<>();

        initSliderPanel();

        initRootView(rootView);

        initCurrentHotel();


        mAlertnativeHotelFontTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                Gson gson = new Gson();
                String json = gson.toJson(mNodeArrayList);
                args.putString("alternative_hotels", json);
                getFlowInterface().goToFragment(ToolBarNavEnum.SELECT_HOTEL_FRAGMENT.getNavNumber(), args);
            }
        });

        ((MainActivityBottomTabs) activity).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {

                if (getActivity() != null && getActivity().getFragmentManager() != null) {
                    IS_MAIN_BACK_ALLOWED = true;
                }

            }
        });

        mSelectHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendServerNewHotelOrder();
            }
        });

        HGBUtility.checkPermissions(getActivity());

        return rootView;
    }

    private void initializeMap() {

        fragment.getMapAsync(this);
    }


    private void sendServerNewHotelOrder() {

        ConnectionManager.getInstance(activity).putAlternateHotel(getActivityInterface().getTravelOrder().getmSolutionID(),
                passengersVO.getmPaxguid(),
                currentSelectedNode.getmCheckIn(), currentSelectedNode.getmCheckOut(), currentSelectedNode.getmGuid(),
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

                        getFlowInterface().callRefreshItinerary(ToolBarNavEnum.HOTEL.getNavNumber());
                        selectedItemGuidNumber(currentSelectedNode.getmGuid());
                        getFlowInterface().setSelectedHotelNode(null);
                        loadMap();

                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();

        fragment = (SupportMapFragment) fm.findFragmentById(R.id.map); //((SupportMapFragment) activity.getFragmentManager().findFragmentById(R.id.map));//(SupportMapFragment) fm.findFragmentById(R.id.map);

        if (fragment == null) {
            fragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
            fragment.getMapAsync(this);
        }
    }

    boolean permissionDenied = true;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == HGBConstants.MY_PERMISSION_ACCESS_COARSE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                permissionDenied = false;
            } else {
                permissionDenied = true;
            }
            // Permission was denied. Display an error message.
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initializeMap();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadMap();
    }

    private void loadMap() {
        //SET UP MAP

        if (!permissionDenied) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.setOnMarkerClickListener(this);


        //GET ALL HOTEL NODES AND SET CURRENT ONE
        ConnectionManager.getInstance(activity).getAlternateHotelsWithHotel(getActivityInterface().getTravelOrder().getmSolutionID(),
                passengersVO.getmPaxguid(),
                currentSelectedNode.getmCheckIn(), currentSelectedNode.getmCheckOut(), new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        CellsVO cellsVO = (CellsVO) data;
                        mNodeArrayList.clear();

                        for (NodesVO node : cellsVO.getmNodes()) {
                            if (currentSelectedNode.getmHotelCode().equals(node.getmHotelCode())) {
                                if (getFlowInterface().getSelectedHotelNode() != null) {
                                    currentSelectedNode = getFlowInterface().getSelectedHotelNode();
                                    mSelectHotel.setVisibility(View.VISIBLE);

                                } else {

                                    currentSelectedNode = getLegWithGuid(getActivityInterface().getTravelOrder());//cellsVO.getmNodes().get(1);
                                    mSelectHotel.setVisibility(View.GONE);
                                }

                                initHotel(currentSelectedNode);
                                setCurrentHotel();
                            } else {
                                mNodeArrayList.add(node);

                            }
                        }


                        //      mSlidingPanels.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                        //    mSlidingPanels.setVisibility(View.VISIBLE);
                    }
                });
    }


    private void initCurrentHotel() {
        passengersVO = getTravellerWitGuid(getActivityInterface().getTravelOrder());//cellsVO.getmNodes().get(1);
        currentSelectedNode = getLegWithGuid(getActivityInterface().getTravelOrder());//cellsVO.getmNodes().get(1);
    }


    private View initRootView(View rootView) {

        //    mScrollView = (ScrollView) rootView.findViewById(R.id.detail_scroll_view);
        mRoomName = (FontTextView) rootView.findViewById(R.id.room_name);
        mChckInDate = (FontTextView) rootView.findViewById(R.id.checkin_date);
        mChckOutDate = (FontTextView) rootView.findViewById(R.id.checkout_date);
        mHotelNameFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_name);
        mHotelPriceFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_price);
        mHotelDaysFontTextView = (FontTextView) rootView.findViewById(R.id.days);
        mAlertnativeHotelFontTextView = (FontTextView) rootView.findViewById(R.id.show_alternative_hotel);


        mSelectHotel = (FontTextView) rootView.findViewById(R.id.select_hotel);


        mStart1ImageView = (ImageView) rootView.findViewById(R.id.star1);
        mStart2ImageView = (ImageView) rootView.findViewById(R.id.star2);
        mStart3ImageView = (ImageView) rootView.findViewById(R.id.star3);
        mStart4ImageView = (ImageView) rootView.findViewById(R.id.star4);
        mStart5ImageView = (ImageView) rootView.findViewById(R.id.star5);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rooms_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(mLayoutManager);


        return rootView;
    }

    private void initSliderPanel() {
//        mSlidingPanels.setCoveredFadeColor(Color.TRANSPARENT);
//        mSlidingPanels.setAnchorPoint(PANEL_HIGHT);
//        mSlidingPanels.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);
    }

    private void initHotel(NodesVO node) {

        mListForGallery = new ArrayList<>();
        mHotelNameFontTextView.setText(node.getmHotelName());
        mRoomName.setText(node.getUserName());
        mChckInDate.setText(HGBUtilityDate.parseDateToddMMyyyyMyTrip(node.getmCheckIn()));
        mChckOutDate.setText(HGBUtilityDate.parseDateToddMMyyyyMyTrip(node.getmCheckOut()));
        long diff = HGBUtilityDate.dayDifference(node.getmCheckIn(), node.getmCheckOut());
        double iCharge = node.getmMinimumAmount() / diff;
        String result = String.format("%.2f", iCharge);
        mHotelPriceFontTextView.setText("$" + node.getmMinimumAmount());
        mHotelDaysFontTextView.setText(diff + " Nights");
        loadRoomsList();


    }

    private void initAlternativeRoomList(ArrayList<RoomsVO> roomlist) {

        mHotelRoomAdapter = new AlternativeHotelRoomAdapter(roomlist);
        mRecyclerView.setAdapter(mHotelRoomAdapter);


    }


    private Marker setCurrentHotel() {
        mMap.clear();
        LatLng currentHotelLatLon = new LatLng(currentSelectedNode.getmLatitude(), currentSelectedNode.getmLongitude());
        Marker currentHotel = mMap.addMarker(new MarkerOptions()
                .position(currentHotelLatLon)
                .icon(BitmapDescriptorFactory.fromBitmap(HGBUtility.getMyHotelMarkerBitmap(currentSelectedNode.getmStarRating(), currentSelectedNode.getmMinimumAmount(), getActivity()))));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentHotelLatLon)      // Sets the center of the map to Mountain View
                .zoom(11)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return currentHotel;

    }

    private void loadRoomsList() {

        //GET ALL HOTEL NODES AND SET CURRENT ONE
        ConnectionManager.getInstance(activity).getAlternateHotelRoomsWithHotel(getActivityInterface().getTravelOrder().getmSolutionID(),
                passengersVO.getmPaxguid(),
                currentSelectedNode.getmCheckIn(), currentSelectedNode.getmCheckOut(), currentSelectedNode.getmHotelCode(), new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        NodesVO node = (NodesVO) data;
                        if (node.getRoomsVOs().size() > 0) {
                            initAlternativeRoomList(node.getRoomsVOs());
                        }


                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });

    }


}
