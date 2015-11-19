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
import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
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
                {
                    accountHotelSettings = accountSetting.getAttributesVOs();
                }
            }
        }

         RecyclerView flightRecyclerView = (RecyclerView) rootView.findViewById(R.id.settings_flight_recyclerView);
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
                    System.out.println("Kate in fight CB guid =" + guid);
//                Fragment fragment = new AlternativeFlightFragment();
//                ((AlternativeFlightFragment)fragment).selectedItemGuidNumber(guid);
//                getActivityInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber());
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
//                SettingsFlightFragment fragemnt = new SettingsFlightFragment();
//                fragemnt.setFlightData(rootView);
//                HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragemnt, false);
                }
            });


            FontTextView hotelTab = (FontTextView) rootView.findViewById(R.id.setting_hotel_tab);
            hotelTab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mIsFlightViewShown = false;
                    showCorrectView();
                    //  text.setText("hotel");

//                SettingsHotelFragment fragemnt = new SettingsHotelFragment();
//                fragemnt.setFlightData(rootView);
//                HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragemnt, false);
                }
            });

            return rootView;
        }



    private void showCorrectView(){
        if(mIsFlightViewShown){
            hotel_tab_view.setVisibility(View.GONE);
            flight_tab_view.setVisibility(View.VISIBLE);
            //show flight
        }else{
            hotel_tab_view.setVisibility(View.VISIBLE);
            flight_tab_view.setVisibility(View.GONE);
            //show hotel
        }


    }

}
