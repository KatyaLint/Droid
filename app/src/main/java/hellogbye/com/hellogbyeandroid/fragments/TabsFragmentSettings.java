package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/8/15.
 */
public class TabsFragmentSettings extends HGBAbtsractFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.settings_tabs_ex_layout, container, false);

        final FontTextView text = (FontTextView) rootView.findViewById(R.id.setting_content_text);


        FontTextView flightTab = (FontTextView)rootView.findViewById(R.id.settings_flight_tab);
        flightTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("flight");
//                SettingsFlightFragment fragemnt = new SettingsFlightFragment();
//                fragemnt.setFlightData(rootView);
//                HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragemnt, false);
            }
        });


        FontTextView hotelTab = (FontTextView)rootView.findViewById(R.id.setting_hotel_tab);
        hotelTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text.setText("hotel");

//                SettingsHotelFragment fragemnt = new SettingsHotelFragment();
//                fragemnt.setFlightData(rootView);
//                HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragemnt, false);
            }
        });

        return rootView;
    }
}
