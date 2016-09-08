package hellogbye.com.hellogbyeandroid.fragments.preferences;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;

import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsDragListAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 11/8/15.
 */
public class PreferencesTabsFragmentSettings extends HGBAbstractFragment {
    private DynamicListView mFlightDynamicListView;
    private DynamicListView mHotelDynamicListView;
    private boolean mIsFlightViewShown = true;
    private View flight_tab_view;
    private View hotel_tab_view;
    private String strJson;
    private FontTextView hotelTab;
    private FontTextView flightTab;
    private FontTextView settings_hotel_text;
    private FontTextView settings_title_flight_text;
    private FontTextView settings_flight_text;
    private FontTextView settings_title_hotel_text;

    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferencesTabsFragmentSettings();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();

        if (args != null) {
            strJson = args.getString(HGBConstants.BUNDLE_SETTINGS_ATT_ID);
            String titleName =  args.getString(HGBConstants.BUNDLE_SETTINGS_TITLE_NAME);
            FontTextView titleBar = ((MainActivityBottomTabs) getActivity()).getTitleBar();
            titleBar.setText(titleName);
        }

        final View rootView = inflater.inflate(R.layout.settings_tabs_ex_layout, container, false);

        flight_tab_view = (View) rootView.findViewById(R.id.setting_flight_include);
        hotel_tab_view = (View) rootView.findViewById(R.id.setting_hotel_include);


        //TODO sort by rank :)
        List<SettingsAttributeParamVO> accountSettings = getActivityInterface().getAccountSettingsAttribute();
        ArrayList<SettingsAttributesVO> accountFlightSettings = null;
        ArrayList<SettingsAttributesVO> accountHotelSettings = null;

        for (SettingsAttributeParamVO accountSetting : accountSettings) {
            if (accountSetting.getmName().equalsIgnoreCase(NodeTypeEnum.FLIGHT.toString())) {
                accountFlightSettings = accountSetting.getAttributesVOs();
            } else if (accountSetting.getmName().equalsIgnoreCase(NodeTypeEnum.HOTEL.toString())) {
                accountHotelSettings = accountSetting.getAttributesVOs();

            }
        }

        initializeFlightRecycle(flight_tab_view, accountFlightSettings);
        initializeHotelRecycle(hotel_tab_view, accountHotelSettings);


        flightTab = (FontTextView) rootView.findViewById(R.id.settings_flight_tab);
        flightTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsFlightViewShown = true;
                showCorrectView();
            }
        });


        hotelTab = (FontTextView) rootView.findViewById(R.id.setting_hotel_tab);
        hotelTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsFlightViewShown = false;
                showCorrectView();

            }
        });




        initializeFontText(flight_tab_view, hotel_tab_view);
        showCorrectView();
        return rootView;
    }


    private void initializeFontText(View flightView, View hotelView){
         settings_title_flight_text = (FontTextView)flightView.findViewById(R.id.settings_item_title);
         settings_flight_text = (FontTextView)flightView.findViewById(R.id.settings_item_text);
         settings_title_hotel_text = (FontTextView)hotelView.findViewById(R.id.settings_item_title);
         settings_hotel_text = (FontTextView)hotelView.findViewById(R.id.settings_item_text);
    }

    private void addFlightPreferenceText(){
        settings_title_flight_text.setText(getActivity().getResources().getText(R.string.preferences_flight_preferences));
        settings_flight_text.setText(getActivity().getResources().getText(R.string.preferences_flight_prefer));
    }


    private void addHotelPreferenceText(){
        settings_title_hotel_text.setText(getActivity().getResources().getText(R.string.preferences_hotel_preferences));
        settings_hotel_text.setText(getActivity().getResources().getText(R.string.preferences_hotel_prefer));
    }

    private void initializeFlightRecycle(View rootView,  List<SettingsAttributesVO> accountFlightSettings) {

        mFlightDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_drag_list);
        mFlightDynamicListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FontTextView settings_flight_title = (FontTextView) view.findViewById(R.id.setting_preferences_title_drag);
                String clickedItemID = settings_flight_title.getTag().toString();
                switchBetweenOptions(clickedItemID);
            }
        });

        PreferencesSettingsDragListAdapter mTabsAdapter = new PreferencesSettingsDragListAdapter(getActivity(),accountFlightSettings);
        mFlightDynamicListView.setAdapter(mTabsAdapter);
        mFlightDynamicListView.enableDragAndDrop();
        mFlightDynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {
                        mFlightDynamicListView.startDragging(position);
                        return true;
                    }
                }
        );

    }


    private void initializeHotelRecycle(View rootView, ArrayList<SettingsAttributesVO> accountFlightSettings) {
        mHotelDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_hotel_drag_list);
        mHotelDynamicListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                FontTextView settings_flight_title = (FontTextView) view.findViewById(R.id.setting_preferences_title_drag);
                String clickedItemID = settings_flight_title.getTag().toString();
                switchBetweenOptions(clickedItemID);
            }
        });
        PreferencesSettingsDragListAdapter mTabsAdapter = new PreferencesSettingsDragListAdapter(getActivity(),accountFlightSettings);
        mHotelDynamicListView.setAdapter(mTabsAdapter);
        mHotelDynamicListView.enableDragAndDrop();
        mHotelDynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
                                                   final int position, final long id) {
                        mHotelDynamicListView.startDragging(position);
                        return true;
                    }
                }
        );

    }

    private void switchBetweenOptions(String guid) {
        List<SettingsAttributesVO> accountAttributes = null;
        boolean goToNewFragment = false;
        String type = null;
        switch (guid) {
            case "1":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightCarrierAttributes();
                break;
            case "2":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightAircraftAttributes();
                break;
            case "3":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightCabinClassAttributes();
                break;
            case "9":
                type = "HTL";
                accountAttributes = getActivityInterface().getAccountSettingsHotelChainAttributes();
                break;
            case "5":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightStopAttributes();
                break;
            case "6":
                type = "HTL";
                accountAttributes = getActivityInterface().getAccountSettingsHotelBedTypeAttributes();
                break;
            case "7":
                type = "HTL";
                accountAttributes = getActivityInterface().getAccountSettingsHotelStarAttributes();
                break;
            case "8":
                type = "HTL";
                accountAttributes = getActivityInterface().getAccountSettingsHotelSmokingClassAttributes();
                break;
        }


        setSettingGuidSelected(guid);
        if (accountAttributes != null) {
            goToNewFragment = true;
        } else {
            getSettingsAttributes(guid, type);
        }

        if(goToNewFragment){
            gotToSelectedFragment(guid,type);
        }

    }


    private void gotToSelectedFragment(String guid, String type){
        args.putString(HGBConstants.BUNDLE_SETTINGS_ATT_ID, strJson);
        args.putString(HGBConstants.BUNDLE_SETTINGS_TYPE, type);
        switch (guid){
            case "1":
            case "2":
            case "9":
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_SEARCH_LIST_SETTINGS.getNavNumber(), args);
                break;
            case "5":
            case "8":
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_CHECK_AS_RADIO_SETTINGS.getNavNumber(), args);
                break;
            case "7":
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_CHECK_LIST_SETTINGS.getNavNumber(), args);
                break;
            case "3":
            case "6":
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCES_DRAG_LIST_SETTINGS.getNavNumber(), args);
                break;


        }
    }


    Bundle args = new Bundle();
    private void getSettingsAttributes(String clickedAttributeID, final String type) {

        ConnectionManager.getInstance(getActivity()).getUserSettingAttributesForAttributeID(clickedAttributeID, type, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    List<SettingsAttributesVO> acountSettingsAttributes = (List<SettingsAttributesVO>) data; //gson.fromJson((String) data, listType);
                    String settingsGuid = getSettingGuidSelected();

                    switch (settingsGuid) {
                        case "1":
                            getActivityInterface().setAccountSettingsFlightCarrierAttributes(acountSettingsAttributes);
                            break;
                        case "2":
                            getActivityInterface().setAccountSettingsFlightAircraftAttributes(acountSettingsAttributes);
                            break;
                        case "3":
                            getActivityInterface().setAccountSettingsFlightCabinClassAttributes(acountSettingsAttributes);
                            break;
                        case "5":
                            getActivityInterface().setAccountSettingsFlightStopAttributes(acountSettingsAttributes);
                            break;
                        case "6":
                            getActivityInterface().setAccountSettingsHotelBedTypeAttributes(acountSettingsAttributes);
                            break;
                        case "7":
                            getActivityInterface().setAccountSettingsHotelStarAttributes(acountSettingsAttributes);
                            break;
                        case "8":
                            getActivityInterface().setAccountSettingsHotelSmokingAttributes(acountSettingsAttributes);
                            break;
                        case "9":
                            getActivityInterface().setAccountSettingsHotelChainAttributes(acountSettingsAttributes);
                            break;
                    }
                    gotToSelectedFragment(settingsGuid,type);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    private void showCorrectView() {
        if (mIsFlightViewShown) {
            hotel_tab_view.setVisibility(View.GONE);
            flight_tab_view.setVisibility(View.VISIBLE);

            hotelTab.setTextColor(getActivity().getResources().getColor(R.color.COLOR_7FA5B4));
            flightTab.setTextColor(getActivity().getResources().getColor(R.color.COLOR_WHITE));
            flight_tab_view.setSelected(true);
            hotel_tab_view.setSelected(false);
            addFlightPreferenceText();


            //show flight
        } else {
            hotel_tab_view.setVisibility(View.VISIBLE);
            flight_tab_view.setVisibility(View.GONE);
            hotelTab.setTextColor(getActivity().getResources().getColor(R.color.COLOR_WHITE));
            flightTab.setTextColor(getActivity().getResources().getColor(R.color.COLOR_7FA5B4));
            flight_tab_view.setSelected(false);
            hotel_tab_view.setSelected(true);
            addHotelPreferenceText();
            //show hotel
        }


    }

}
