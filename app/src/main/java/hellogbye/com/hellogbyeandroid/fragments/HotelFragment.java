package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.ImageGalleryActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.AlternativeHotelAdapter;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.AllImagesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by arisprung on 9/30/15.
 */
public class HotelFragment extends HGBAbtsractFragment implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private MapFragment fragment;
    private GoogleMap mMap;
    private TableLayout mTableLayout;
    private ArrayList<String> mImageList;
    private SlidingUpPanelLayout mSlidingPanels;
    private static final String TAG = "HotelFragment";

    private ScrollView mScrollView;
    private FontTextView mHotelNameFontTextView;
    private FontTextView mHotelPriceFontTextView;
    private FontTextView mHotelDaysFontTextView;
    private FontTextView mHotelAddressFontTextView;
    private RelativeLayout mPullDOwnRelativeLayout;

    private FontTextView mHotelRoomNameFontTextView;
    private FontTextView mHotelGuestNumberFontTextView;
    private FontTextView mHotelCheckInFontTextView;
    private FontTextView mHotelCheckOutFontTextView;
    private FontTextView mAlertnativeHotelFontTextView;
    private FontTextView mHotelNameFullFontTextView;
    private FontTextView mHotelContactFontTextView;
    private FontTextView mHotelAmnitiesFontTextView;
    private FontTextView mHotelNearbyAttrictionsFontTextView;
    //  private UserTravelVO mTravelDetails;
    private ImageView mStart1ImageView;
    private ImageView mStart2ImageView;
    private ImageView mStart3ImageView;
    private ImageView mStart4ImageView;
    private ImageView mStart5ImageView;
    private LinearLayout mMyHotelLinearLayout;
    private ImageView mMyHotelImage;
    private FontTextView mMyHotelText;
    private FontTextView mSelectHotelText;

    private AlternativeHotelAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;

    //    PassengersVO passengersVO;
//    CellsVO cellsVO;
    private NodesVO nodesVO;
    private ArrayList<String> mListForGallery;
    private HashMap<Marker, NodesVO> nodeMarkerMap;
    private PassengersVO passengersVO;
    private NodesVO currentSelectedNode;

    private float PANEL_HIGHT = 0.4f;
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
        mTableLayout = (TableLayout) rootView.findViewById(R.id.tableLayout1);
        mSlidingPanels = (SlidingUpPanelLayout) rootView.findViewById(R.id.sliding_layout);

        nodeMarkerMap = new HashMap<Marker, NodesVO>();
        mAdapter = new AlternativeHotelAdapter(new ArrayList<NodesVO>(nodeMarkerMap.values()));


        initSliderPanel();

        initRootView(rootView);

        initCurrentHotel();

        mMyHotelLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMyHotelImage.getVisibility() != View.VISIBLE) {
                    sendServerNewHotelOrder(currentSelectedNode);

                }
            }
        });

        mAlertnativeHotelFontTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlternativeHotels();
            }
        });

        ((MainActivity) activity).setOnBackPressedListener(new OnBackPressedListener() {
            public void doBack() {

                if (mRecyclerView.getVisibility() != View.VISIBLE) {
                    if (getActivity() != null && getActivity().getFragmentManager() != null) {
                        IS_MAIN_BACK_ALLOWED = true;
                        //getFragmentManager().popBackStack();
//                        FragmentManager fm = getActivity().getFragmentManager();
//                        fm.popBackStack(HotelFragment.class.toString(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    }
                } else {
                    IS_MAIN_BACK_ALLOWED = false;
                    hideAlternativeHotels();
                }

            }
        });

        getFlowInterface().getToolBar().updateToolBarView(ToolBarNavEnum.HOTEL.getNavNumber());
        return rootView;
    }

    private void sendServerNewHotelOrder(NodesVO cnode) {



        ConnectionManager.getInstance(activity).putAlternateHotel(getActivityInterface().getTravelOrder().getmSolutionID(),
                passengersVO.getmPaxguid(),
                nodesVO.getmCheckIn(), nodesVO.getmCheckOut(), cnode.getmGuid(),
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        getFlowInterface().callRefreshItinerary(ToolBarNavEnum.HOTEL.getNavNumber());
                        //GET ALL HOTEL NODES AND SET CURRENT ONE
                        nodesVO = currentSelectedNode;
                        //TODO set hotel locall and call server
                        loadMap();

                        hideAlternativeHotels();

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getFragmentManager(), (String) data);
                    }
                });
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();

        fragment = ((MapFragment) activity.getFragmentManager().findFragmentById(R.id.map));//(SupportMapFragment) fm.findFragmentById(R.id.map);

        if (fragment == null) {
            fragment = MapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, fragment).commit();
            fragment.getMapAsync(this);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mMap = fragment.getMap();

        if (mMap != null) {

            //SET UP MAP
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(false);
            mMap.setMyLocationEnabled(true);
            mMap.setOnMarkerClickListener(this);
        } else {
            Log.d("DEBUG", "map is null");
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        NodesVO node = nodeMarkerMap.get(marker);
        if (node != null) {
            initHotel(node, marker);
            currentSelectedNode = node;
        } else {
            setCurrentHotel();
            initHotel(nodesVO, null);
        }

        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        loadMap();
    }

    private void loadMap() {

        //GET ALL HOTEL NODES AND SET CURRENT ONE
        ConnectionManager.getInstance(activity).getAlternateHotelsWithHotel(getActivityInterface().getTravelOrder().getmSolutionID(),
              passengersVO.getmPaxguid(),
                nodesVO.getmCheckIn(), nodesVO.getmCheckOut(), new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        CellsVO cellsVO = (CellsVO) data;
                        setCurrentHotel();
                        if (nodeMarkerMap.size() > 0) {
                            nodeMarkerMap.clear();
                            mMap.clear();
                        }

                        setCurrentHotel();


                        for (NodesVO node : cellsVO.getmNodes()) {
                            if (nodesVO.getmHotelCode().equals(node.getmHotelCode())) {
                                initHotel(node, null);
                                //  nodeMarkerMap.put(marker, node);
                            } else {


                                Marker mark = mMap.addMarker(new MarkerOptions()
                                        .position(new LatLng(Double.valueOf(node.getmLatitude()), Double.valueOf(node.getmLongitude())))
                                        .icon(BitmapDescriptorFactory.fromBitmap(HGBUtility.getMarkerBitmap(activity, String.valueOf((int) node.getmMinimumAmount()), R.drawable.other_location_blue))));
                                nodeMarkerMap.put(mark, node);

                            }
                        }

                        // specify an adapter (see also next example)

                        mAdapter = new AlternativeHotelAdapter(new ArrayList<NodesVO>(nodeMarkerMap.values()));
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.SetOnItemClickListener(new AlternativeHotelAdapter.OnItemClickListener() {

                            @Override
                            public void onItemClick(View v, int position) {

                                NodesVO node = (new ArrayList<NodesVO>(nodeMarkerMap.values())).get(position);
                                Marker marker = (new ArrayList<Marker>(nodeMarkerMap.keySet())).get(position);
                                currentSelectedNode = node;
                                initHotel(node, marker);
                                hideAlternativeHotels();

                                //sendServerNewHotelOrder(node);
                            }
                        });
                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getFragmentManager(), (String) data);
                    }
                });
    }


    private void buildHotelGalleryTable(int cols) {
        // outer for loop
        int iIndex = 0;
        int count = mTableLayout.getChildCount();
        if (mTableLayout.getChildCount() > 0) {
            mTableLayout.removeAllViews();
        }


        for (int i = 1; i <= 2; i++) {
            TableRow row = new TableRow(activity);
            row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
            for (int j = 1; j <= cols; j++) {
                LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ImageView image = (ImageView) inflater.inflate(R.layout.hotel_gallery_imageview, null);

                //  tv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                if (mImageList.size() > 0) {
                    String strValue = mImageList.remove(0);
                    HGBUtility.loadHotelImage(activity.getApplicationContext(), strValue, image);
                    image.setTag(iIndex++);
                    row.addView(image);
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(activity.getApplicationContext(), ImageGalleryActivity.class);
                            intent.putStringArrayListExtra("images", mListForGallery);
                            intent.putExtra("image_index", (Integer) v.getTag());
                            startActivity(intent);
                        }
                    });
                }
            }

            mTableLayout.addView(row);
        }
    }

    private void initCurrentHotel() {

        //   mTravelDetails = getActivityInterface().getTravelOrder();
//        passengersVO = mTravelDetails.getPassengerses().get(0);
//        cellsVO = getCellWitGuid(getActivityInterface().getTravelOrder());  //passengersVO.getmCells().get(0);
        passengersVO = getTravellerWitGuid(getActivityInterface().getTravelOrder());//cellsVO.getmNodes().get(1);
        nodesVO = getLegWithGuid(getActivityInterface().getTravelOrder());//cellsVO.getmNodes().get(1);
        currentSelectedNode = nodesVO;
    }


    private View initRootView(View rootView) {

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
        mMyHotelLinearLayout = (LinearLayout) rootView.findViewById(R.id.my_hotel_ll);
        mMyHotelImage = (ImageView) rootView.findViewById(R.id.image_my_hotel);
        mMyHotelText = (FontTextView) rootView.findViewById(R.id.text_my_hotel);
        mSelectHotelText = (FontTextView) rootView.findViewById(R.id.text_select_hotel);
        mPullDOwnRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.pull_down);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.alt_hotel_recycler_view);


        mStart1ImageView = (ImageView) rootView.findViewById(R.id.star1);
        mStart2ImageView = (ImageView) rootView.findViewById(R.id.star2);
        mStart3ImageView = (ImageView) rootView.findViewById(R.id.star3);
        mStart4ImageView = (ImageView) rootView.findViewById(R.id.star4);
        mStart5ImageView = (ImageView) rootView.findViewById(R.id.star5);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(activity);
        mRecyclerView.setLayoutManager(mLayoutManager);


        return rootView;
    }

    private void initSliderPanel() {
        mSlidingPanels.setCoveredFadeColor(Color.TRANSPARENT);
        mSlidingPanels.setAnchorPoint(PANEL_HIGHT);
        mSlidingPanels.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

        mSlidingPanels.setPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                mPullDOwnRelativeLayout.setVisibility(View.GONE);
            }

            @Override
            public void onPanelCollapsed(View panel) {
            }

            @Override
            public void onPanelExpanded(View panel) {
            }

            @Override
            public void onPanelAnchored(View panel) {
                //          Log.i(TAG, "main height=" + mSlidingPanels.findViewById(R.id.main).getHeight());
            }

            @Override
            public void onPanelHidden(View panel) {

            }
        });


    }

    private void initHotel(NodesVO node, Marker marker) {

        mListForGallery = new ArrayList<>();
        mHotelNameFontTextView.setText(node.getmHotelName());
        mHotelPriceFontTextView.setText("$" + String.valueOf((int) node.getmMinimumAmount()));
        mHotelDaysFontTextView.setText(HGBUtility.getDateDiffString(node.getmCheckIn(), node.getmCheckOut()));
        mHotelAddressFontTextView.setText(node.getmAddress1());
        //TODO we need to check if size is more then 1 show option to change
        mHotelRoomNameFontTextView.setText(node.getRoomsVOs().get(0).getmRoomType());
        // mHotelGuestNumberFontTextView.setText(mTravelDetails.getPassengerses().get(1).getmCells().get(0).getmNodes().get(0).getm());
//        mHotelCheckInFontTextView.setText(nodesVO.getmCheckIn());
//        mHotelCheckOutFontTextView.setText(nodesVO.getmCheckOut());
        mHotelCheckInFontTextView.setText(HGBUtility.parseDateToddMMyyyy(node.getmCheckIn()));
        mHotelCheckOutFontTextView.setText(HGBUtility.parseDateToddMMyyyy(node.getmCheckOut()));

        if (nodesVO.getmHotelCode().equals(node.getmHotelCode())) {
            mMyHotelText.setVisibility(View.VISIBLE);
            mMyHotelImage.setVisibility(View.VISIBLE);
            mSelectHotelText.setVisibility(View.GONE);

        } else {
            mSelectHotelText.setVisibility(View.VISIBLE);
            mMyHotelText.setVisibility(View.GONE);
            mMyHotelImage.setVisibility(View.GONE);
        }

        mHotelNameFullFontTextView.setText(node.getmAddress1() + "," + node.getmCityName() + "\n" + node.getmPostalCode() + "," + node.getmStateProvince() + "," + node.getmCountry());
        mHotelAmnitiesFontTextView.setText(Html.fromHtml(node.getmAmenities()));
        mHotelContactFontTextView.setText(node.getmPhone());
        mHotelNearbyAttrictionsFontTextView.setText(Html.fromHtml(node.getmShortDescription()));
        setStarRating(node.getmStarRating());
        mImageList = new ArrayList<>();
        for (AllImagesVO image : node.getAllImagesVOs()) {
            mImageList.add(image.getmImage());
            mListForGallery.add(image.getmImage());
        }
        buildHotelGalleryTable(mImageList.size() / 2);//

        if (marker != null) {

            for (Marker marker1 : nodeMarkerMap.keySet()) {

                if (!marker.equals(marker1)) {
                    marker1.setIcon(BitmapDescriptorFactory.fromBitmap(HGBUtility.getMarkerBitmap(getActivity(), String.valueOf((int) nodeMarkerMap.get(marker1).getmMinimumAmount()), R.drawable.other_location_blue)));

                } else {
                    marker1.setIcon(BitmapDescriptorFactory.fromBitmap(HGBUtility.getMarkerBitmap(getActivity(), String.valueOf((int) nodeMarkerMap.get(marker1).getmMinimumAmount()), R.drawable.other_location_red)));
                }

            }

        }
    }

    private void setStarRating(float star) {

        if ("0.5".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.half_star);
            mStart2ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart3ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

        } else if ("1.0".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.full_star);
            mStart2ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart3ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

        } else if ("1.5".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.full_star);
            mStart2ImageView.setBackgroundResource(R.drawable.half_star);
            mStart3ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

        } else if ("2.0".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.full_star);
            mStart2ImageView.setBackgroundResource(R.drawable.full_star);
            mStart3ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

        } else if ("2.5".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.full_star);
            mStart2ImageView.setBackgroundResource(R.drawable.full_star);
            mStart3ImageView.setBackgroundResource(R.drawable.half_star);
            mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

        } else if ("3.0".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.full_star);
            mStart2ImageView.setBackgroundResource(R.drawable.full_star);
            mStart3ImageView.setBackgroundResource(R.drawable.full_star);
            mStart4ImageView.setBackgroundResource(R.drawable.empty_star);
            mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

        } else if ("3.5".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.full_star);
            mStart2ImageView.setBackgroundResource(R.drawable.full_star);
            mStart3ImageView.setBackgroundResource(R.drawable.full_star);
            mStart4ImageView.setBackgroundResource(R.drawable.half_star);
            mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

        } else if ("4.0".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.full_star);
            mStart2ImageView.setBackgroundResource(R.drawable.full_star);
            mStart3ImageView.setBackgroundResource(R.drawable.full_star);
            mStart4ImageView.setBackgroundResource(R.drawable.full_star);
            mStart5ImageView.setBackgroundResource(R.drawable.empty_star);

        } else if ("4.5".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.full_star);
            mStart2ImageView.setBackgroundResource(R.drawable.full_star);
            mStart3ImageView.setBackgroundResource(R.drawable.full_star);
            mStart4ImageView.setBackgroundResource(R.drawable.full_star);
            mStart5ImageView.setBackgroundResource(R.drawable.half_star);

        } else if ("5.0".equals(String.valueOf(star))) {
            mStart1ImageView.setBackgroundResource(R.drawable.full_star);
            mStart2ImageView.setBackgroundResource(R.drawable.full_star);
            mStart3ImageView.setBackgroundResource(R.drawable.full_star);
            mStart4ImageView.setBackgroundResource(R.drawable.full_star);
            mStart5ImageView.setBackgroundResource(R.drawable.full_star);

        }


    }


    private Marker setCurrentHotel() {
        LatLng currentHotelLatLon = new LatLng(nodesVO.getmLatitude(), nodesVO.getmLongitude());
        Marker currentHotel = mMap.addMarker(new MarkerOptions()
                .position(currentHotelLatLon)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_selected_hotel_blue)));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(currentHotelLatLon)      // Sets the center of the map to Mountain View
                .zoom(11)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        return currentHotel;

    }

    private void showAlternativeHotels() {
        mRecyclerView.clearAnimation();
        mRecyclerView.setVisibility(View.VISIBLE);

        Animation anim = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_in_up);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSlidingPanels.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRecyclerView.setAnimation(anim);
        anim.start();

    }

    private void hideAlternativeHotels() {
        mRecyclerView.clearAnimation();
        if (mRecyclerView.getVisibility() != View.VISIBLE) {
            return;
        }

        Animation animDown = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.slide_out_down);
        animDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mSlidingPanels.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRecyclerView.setVisibility(View.GONE);
                mSlidingPanels.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRecyclerView.setAnimation(animDown);
        animDown.start();

    }


}
