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
import android.widget.AdapterView;

import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;

import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsFlightTabsAdapter;
import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsListAdapter;
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

    private DynamicListView mDynamicListView;
    private RecyclerView flightRecyclerView;
    private RecyclerView hotelRecyclerView;
    private boolean mIsFlightViewShown = true;
    private View flight_tab_view;
    private View hotel_tab_view;

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
        //  settings_drag_drop = (View) rootView.findViewById(R.id.settings_drag_drop);

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
            public void onItemClick(String guid) {
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
            public void onItemClick(String guid) {
                switchBetweenOptions(guid);
            }
        });
    }


    private void switchBetweenOptions(String guid) {
        List<SettingsAttributesVO> accountAttributes = null;
        switch (guid) {
            case "1":

                accountAttributes = getActivityInterface().getAccountSettingsFlightCarrierAttributes();
                setSettingGuidSelected(guid);
                if (accountAttributes != null) {
                    getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_SPECIFIC_LIST_SETTINGS.getNavNumber());
                } else {
                    getSettingsAttributes(guid, "FLT");
                }
                break;
            case "5":

                accountAttributes = getActivityInterface().getAccountSettingsFlightStopAttributes();
                setSettingGuidSelected(guid);
                if (accountAttributes != null) {
                    getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_SPECIFIC_LIST_SETTINGS.getNavNumber());
                } else {
                    getSettingsAttributes(guid, "FLT");
                }
                break;
        }


    }


    private void getSettingsAttributes(String clickedAttributeID, String type) {

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
                    }

                    getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_SPECIFIC_LIST_SETTINGS.getNavNumber());
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
