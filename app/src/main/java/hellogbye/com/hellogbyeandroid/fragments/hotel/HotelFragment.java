package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.ImageGalleryActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.hotel.AlternativeHotelRoomAdapter;
import hellogbye.com.hellogbyeandroid.adapters.hotel.HotelImageAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.AllImagesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.RoomsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.WrapContentViewPager;


/**
 * Created by arisprung on 9/30/15.
 */
public class HotelFragment extends HGBAbstractFragment {


    private WrapContentViewPager mViewPager;
    private ViewPager mHotelViewPager;
    private AlternativeHotelRoomAdapter mHotelRoomAdapter;
    private HotelImageAdapter mHotelImageAdapter;
    private FontTextView mHotelNameFontTextView;
    private FontTextView mHotelPriceFontTextView;
    private FontTextView mHotelDaysFontTextView;
    private FontTextView mAlertnativeHotelFontTextView;
    private FontTextView mRoomName;
    private FontTextView mChckInDate;
    private FontTextView mChckOutDate;
    private ArrayList<String> mListForGallery;
    private ArrayList<NodesVO> mNodeArrayList;
    private PassengersVO passengersVO;
    private NodesVO currentSelectedNode;
    private ImageView mConfirmBadge;

    public final float PANEL_HIGHT = 0.4f;
    private Activity activity;

    public static boolean IS_MAIN_BACK_ALLOWED = true;

    private FragmentTabHost mTabHost;
    private static final String TAB_1_TAG = "DETAILS";
    private static final String TAB_2_TAG = "GALLERY";
    private static final String TAB_3_TAG = "Hotel Policies";

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
        mNodeArrayList = new ArrayList<>();

        initRootView(rootView);
        initHotelTabs(rootView);

        initCurrentHotel();
        loadAlternativeHotels();
        loadRoomsList();


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


        HGBUtility.checkPermissions(getActivity());

        return rootView;
    }


    private void loadAlternativeHotels() {

        ConnectionManager.getInstance(activity).getAlternateHotelsWithHotel(getActivityInterface().getTravelOrder().getmSolutionID(),
                passengersVO.getmPaxguid(),
                currentSelectedNode.getmCheckIn(), currentSelectedNode.getmCheckOut(), new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        CellsVO cellsVO = (CellsVO) data;
                        mNodeArrayList.clear();
                        mNodeArrayList.addAll(cellsVO.getmNodes());
                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });


    }


    private void initCurrentHotel() {
        passengersVO = getTravellerWitGuid(getActivityInterface().getTravelOrder());
        currentSelectedNode = getLegWithGuid(getActivityInterface().getTravelOrder());
        initHotel(currentSelectedNode);
    }


    private View initRootView(View rootView) {

        mRoomName = (FontTextView) rootView.findViewById(R.id.room_name);
        mChckInDate = (FontTextView) rootView.findViewById(R.id.checkin_date);
        mChckOutDate = (FontTextView) rootView.findViewById(R.id.checkout_date);
        mHotelNameFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_name);
        mHotelPriceFontTextView = (FontTextView) rootView.findViewById(R.id.hotel_price);
        mHotelDaysFontTextView = (FontTextView) rootView.findViewById(R.id.days);
        mAlertnativeHotelFontTextView = (FontTextView) rootView.findViewById(R.id.show_alternative_hotel);

        mViewPager = (WrapContentViewPager) rootView.findViewById(R.id.rooms_view_pager);
        mHotelViewPager = (ViewPager) rootView.findViewById(R.id.hotel_image_view_pager);


        return rootView;
    }


    private void initHotel(NodesVO node) {

        mListForGallery = new ArrayList<>();
        mHotelNameFontTextView.setText(node.getmHotelName());

        UserTravelMainVO userOrder = getActivityInterface().getTravelOrder();
        Map<String, NodesVO> items = userOrder.getItems();
        ArrayList<PassengersVO> passangers = userOrder.getPassengerses();
        for (PassengersVO passenger: passangers){
            ArrayList<String> ItineraryItems = passenger.getmItineraryItems();
            for (String itineraryItem :ItineraryItems){
                NodesVO node1 = items.get(itineraryItem);
                if(node.getmPaxguid().equalsIgnoreCase(node1.getmPaxguid())){
                    mRoomName.setText(passenger.getmName());
                    break;
                }
            }
        }

        mChckInDate.setText(HGBUtilityDate.parseDateToddMMyyyyMyTrip(node.getmCheckIn()));
        mChckOutDate.setText(HGBUtilityDate.parseDateToddMMyyyyMyTrip(node.getmCheckOut()));
        long diff = HGBUtilityDate.dayDifference(node.getmCheckIn(), node.getmCheckOut());
        double iCharge = node.getmMinimumAmount() / diff;
        String result = String.format("%.2f", iCharge);
        mHotelPriceFontTextView.setText("$" + node.getmMinimumAmount());
        mHotelDaysFontTextView.setText(diff + " Nights");
        initHotelImages(node.getAllImagesVOs());
    }

    private void initAlternativeRoomList(final ArrayList<RoomsVO> roomlist) {

        mHotelRoomAdapter = new AlternativeHotelRoomAdapter(initRoomList(roomlist), getActivity().getApplicationContext());
        mViewPager.setAdapter(mHotelRoomAdapter);

        mHotelRoomAdapter.SetOnItemClickListener(new AlternativeHotelRoomAdapter.OnLinearLayoutClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle args = new Bundle();
                Gson gson = new Gson();
                String json = gson.toJson(roomlist.get(position));
                args.putString("alternative_rooms", json);
                args.putString("paxid", currentSelectedNode.getmPaxguid());
                getFlowInterface().goToFragment(ToolBarNavEnum.SELECT_ROOM_FRAGMENT.getNavNumber(), args);
            }
        });
        mViewPager.setCurrentItem(0,true);
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                mViewPager.reMeasureCurrentPage(mViewPager.getCurrentItem());
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    private ArrayList<RoomsVO> initRoomList(ArrayList<RoomsVO> roomlist){


        ArrayList<RoomsVO> newList = new ArrayList<>(roomlist);
        for (int i = 0; i <newList.size() ; i++) {

            if(!newList.get(i).ismIsAlternative()){
                RoomsVO room = newList.get(i);
                newList.remove(i);
                newList.add(0,room);
                return newList;
            }


        }
        return newList;

    }

    private void initHotelImages(ArrayList<AllImagesVO> allImagesVOs) {

        mHotelImageAdapter = new HotelImageAdapter(allImagesVOs, getActivity().getApplicationContext());
        mHotelViewPager.setAdapter(mHotelImageAdapter);
        mHotelImageAdapter.SetOnItemClickListener(new HotelImageAdapter.OnLinearLayoutClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Gson gson = new Gson();
                String json = gson.toJson(getLegWithGuid(getActivityInterface().getTravelOrder()).getAllImagesVOs());
                Intent intent = new Intent(getActivity().getApplicationContext(),ImageGalleryActivity.class);
                intent.putExtra("images",json);
                startActivity(intent);

            }
        });
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

    private void initHotelTabs(View rootview) {


//        Bundle detailBundle = new Bundle();
//        detailBundle.putString("hotel_desc", currentSelectedNode.getmShortDescription());
        mTabHost = (FragmentTabHost) rootview.findViewById(android.R.id.tabhost);

        mTabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
//        mTabHost.addTab(mTabHost.newTabSpec(TAB_2_TAG).setIndicator(TAB_2_TAG),
//                GalleryHotelFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec(TAB_1_TAG).setIndicator(TAB_1_TAG),
                DetailsHotelFragment.class, null);

        mTabHost.addTab(mTabHost.newTabSpec(TAB_3_TAG).setIndicator(TAB_3_TAG),
                PolicyHotelFragment.class, null);

        mTabHost.setCurrentTab(0);
        mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {

                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
                Typeface textFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + "dinnextltpro_medium.otf");
                for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.COLOR_999999));
                    tv.setTypeface(textFont);
                    tv.setTransformationMethod(null);

                }

                TextView tv = (TextView) mTabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.COLOR_565656));
                tv.setTypeface(textFont);
                tv.setTransformationMethod(null);

            }
        });
    }


}
