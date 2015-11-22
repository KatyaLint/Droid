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
public class TabsFragmentSettings extends HGBAbtsractFragment {



    public static Fragment newInstance(int position) {
        Fragment fragment = new TabsFragmentSettings();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

//    http://cnc.hellogbye.com/cnc/rest/TravelPreference/Profiles/3a3be8e4-57b6-4b48-98f7-2624701b20af/Preferences/HTL/Attributes/6/Values


    private boolean mIsFlightViewShown = true;
    View flight_tab_view;
    View hotel_tab_view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.settings_tabs_ex_layout, container, false);

        flight_tab_view = (View) rootView.findViewById(R.id.setting_flight_include);
        hotel_tab_view = (View) rootView.findViewById(R.id.setting_hotel_include);

        List<SettingsAttributeParamVO> accountSettings = getActivityInterface().getAccountSettingsAttribute();
        ArrayList<SettingsAttributesVO> accountFlightSettings = null;
        ArrayList<SettingsAttributesVO> accountHotelSettings;

        for (SettingsAttributeParamVO accountSetting : accountSettings) {
            if (accountSetting.getmName().equalsIgnoreCase(NodeTypeEnum.FLIGHT.toString())) {
                accountFlightSettings = accountSetting.getAttributesVOs();
            } else if (accountSetting.getmName().equalsIgnoreCase(NodeTypeEnum.HOTEL.toString())) {

                accountHotelSettings = accountSetting.getAttributesVOs();

            }
        }




//        final DynamicListView mDynamicListView = (DynamicListView) rootView.findViewById(R.id.settings_flight_recyclerView);
//        mDynamicListView.enableDragAndDrop();
//        mDynamicListView.setOnItemLongClickListener(
//                new AdapterView.OnItemLongClickListener() {
//                    @Override
//                    public boolean onItemLongClick(final AdapterView<?> parent, final View view,
//                                                   final int position, final long id) {
//                        mDynamicListView.startDragging(position);
//                        return true;
//                    }
//                }
//        );

//            PreferenceSettingsListAdapter mTabsAdapter = new PreferenceSettingsListAdapter(getActivity(), accountFlightSettings);
//            mDynamicListView.setAdapter(mTabsAdapter);


        final RecyclerView flightRecyclerView = (RecyclerView) rootView.findViewById(R.id.settings_flight_recyclerView);
        flightRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // 5. set item animator to DefaultAnimator
        flightRecyclerView.setItemAnimator(new DefaultItemAnimator());
            // 2. set layoutManger
            flightRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            // 3. create an adapter

        PreferenceSettingsFlightTabsAdapter mTabsAdapter = new PreferenceSettingsFlightTabsAdapter(accountFlightSettings);
        flightRecyclerView.setAdapter(mTabsAdapter);



//        http://cnc.hellogbye.com/cnc/rest/TravelPreference/Preferences/FLT/Attributes/1/Values



        mTabsAdapter.SetOnItemClickListener(new PreferenceSettingsFlightTabsAdapter.OnItemClickListener() {

                @Override
                public void onItemClick(String guid) {
                    if(mIsFlightViewShown) {
                        //TODO change HTL and FLT
                        System.out.println("Kate in fight CB guid =" + guid);
                        getSettingsAttributes(guid, "FLT");
                    }else{
                        if(mIsFlightViewShown) {
                            System.out.println("Kate in fight CB guid =" + guid);
                            getSettingsAttributes(guid, "HTL");
                        }
                    }
                    //    getSettingsAttributes(String clickedAttributeID, String type, guid);
                    //go to server
                }
            });


            //     final FontTextView text = (FontTextView) rootView.findViewById(R.id.setting_content_text);

            FontTextView flightTab = (FontTextView) rootView.findViewById(R.id.settings_flight_tab);
            flightTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //   text.setText("flight");
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



    private void getSettingsAttributes(String clickedAttributeID, String type) {

        ConnectionManager.getInstance(getActivity()).getUserSettingAttributesForAttributeID(clickedAttributeID,type, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    List<SettingsAttributesVO> acountSettingsAttributes = (List<SettingsAttributesVO>) data; //gson.fromJson((String) data, listType);
                    getActivityInterface().setAccountSettingsAttributeSpecific(acountSettingsAttributes);
                    //getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_TAB_SETTINGS.getNavNumber());
                }
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });
    }


    private void showCorrectView(){
        if(mIsFlightViewShown){
            hotel_tab_view.setVisibility(View.GONE);
            flight_tab_view.setVisibility(View.VISIBLE);
            flight_tab_view.setSelected(true);
            hotel_tab_view.setSelected(false);
            //show flight
        }else{
            hotel_tab_view.setVisibility(View.VISIBLE);
            flight_tab_view.setVisibility(View.GONE);
            flight_tab_view.setSelected(false);
            hotel_tab_view.setSelected(true);
            //show hotel
        }


    }

}
