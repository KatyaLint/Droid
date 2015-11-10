package hellogbye.com.hellogbyeandroid.fragments;

import android.app.ActionBar;

import android.os.Bundle;


import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;


import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;

/**
 * Created by nyawka on 11/5/15.
 */
public class PreferencesAttributeFragment extends HGBAbtsractFragment {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private SettingsFlightFragment flightFragment;
    private SettingsHotelFragment hotelFragment;
    ActionBar.Tab flightTab, hotelTab;
    private FragmentTabHost mTabHost;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        View rootView = inflater.inflate(R.layout.settings_tabs_layout, container, false);


        View rootView = inflater.inflate(R.layout.settings_tabs_layout,container, false);

        FrameLayout tab = (FrameLayout)rootView.findViewById(R.id.tab_1);
        tab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("Kate clickeeedd!!!!!!");
            }
        });
        return rootView;










    }


}



