package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;

import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsFlightTabsAdapter;
import hellogbye.com.hellogbyeandroid.adapters.PreferencesSettingsHotelTabsAdapter;
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by nyawka on 11/8/15.
 */
public class PreferencesTabsFragmentSettings extends HGBAbtsractFragment {


    private RecyclerView flightRecyclerView;
    private RecyclerView hotelRecyclerView;
    private boolean mIsFlightViewShown = true;
    private View flight_tab_view;
    private View hotel_tab_view;
    private String strJson;

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

        initializeFlightRecycle(rootView, accountFlightSettings);
        initializeHotelRecycle(rootView, accountHotelSettings);

        FontTextView flightTab = (FontTextView) rootView.findViewById(R.id.settings_flight_tab);
        flightTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsFlightViewShown = true;
                showCorrectView();
            }
        });


        FontTextView hotelTab = (FontTextView) rootView.findViewById(R.id.setting_hotel_tab);
        hotelTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsFlightViewShown = false;
                showCorrectView();

            }
        });


        Bundle args = getArguments();
        if (args != null) {
            strJson = args.getString("setting_att_id");
        }

        return rootView;
    }

    private void initializeFlightRecycle(View rootView, ArrayList<SettingsAttributesVO> accountFlightSettings) {
        flightRecyclerView = (RecyclerView) rootView.findViewById(R.id.settings_flight_recyclerView);
        flightRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // 5. set item animator to DefaultAnimator
        flightRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        flightRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter

        PreferenceSettingsFlightTabsAdapter mTabsAdapter = new PreferenceSettingsFlightTabsAdapter(accountFlightSettings);
        flightRecyclerView.setAdapter(mTabsAdapter);


        mTabsAdapter.SetOnItemClickListener(new PreferenceSettingsFlightTabsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String guid, String position) {
                switchBetweenOptions(guid);
            }
        });
    }


    private void initializeHotelRecycle(View rootView, ArrayList<SettingsAttributesVO> accountFlightSettings) {
        hotelRecyclerView = (RecyclerView) rootView.findViewById(R.id.settings_hotel_recyclerView_list);
        hotelRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // 5. set item animator to DefaultAnimator
        hotelRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 2. set layoutManger
        hotelRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // 3. create an adapter

        PreferencesSettingsHotelTabsAdapter mTabsAdapter = new PreferencesSettingsHotelTabsAdapter(accountFlightSettings);
        hotelRecyclerView.setAdapter(mTabsAdapter);


        mTabsAdapter.SetOnItemClickListener(new PreferencesSettingsHotelTabsAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(String guid, String position) {
                switchBetweenOptions(guid);
            }
        });
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
            case "3":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightCabinClassAttributes();
                break;
            case "5":
                type = "FLT";
                accountAttributes = getActivityInterface().getAccountSettingsFlightStopAttributes();
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
        args.putString("setting_att_id", strJson);
        args.putString("setting_type", type);
        switch (guid){
            case "1":
                getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_SEARCH_LIST_SETTINGS.getNavNumber(), args);
                break;
            case "5":
            case "8":
                getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_CHECK_LIST_SETTINGS.getNavNumber(), args);
                break;
            case "3":
                getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_DRAG_LIST_SETTINGS.getNavNumber(), args);
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
                        case "5":
                            getActivityInterface().setAccountSettingsFlightStopAttributes(acountSettingsAttributes);
                            break;
                        case "1":
                            getActivityInterface().setAccountSettingsFlightCarrierAttributes(acountSettingsAttributes);
                            break;
                        case "3":
                            getActivityInterface().setAccountSettingsFlightCabinClassAttributes(acountSettingsAttributes);
                            break;
                        case "8":
                            getActivityInterface().setAccountSettingsHotelSmokingAttributes(acountSettingsAttributes);
                            break;
                    }
                    gotToSelectedFragment(settingsGuid,type);


                  //  getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_SEARCH_LIST_SETTINGS.getNavNumber(), args);
                }
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });
    }


    private void showCorrectView() {
        if (mIsFlightViewShown) {
            hotel_tab_view.setVisibility(View.GONE);
            flight_tab_view.setVisibility(View.VISIBLE);
            flight_tab_view.setSelected(true);
            hotel_tab_view.setSelected(false);

            //show flight
        } else {
            hotel_tab_view.setVisibility(View.VISIBLE);
            flight_tab_view.setVisibility(View.GONE);
            flight_tab_view.setSelected(false);
            hotel_tab_view.setSelected(true);
            //show hotel
        }


    }

}
