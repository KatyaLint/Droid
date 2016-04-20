package hellogbye.com.hellogbyeandroid.fragments.companions;



import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by nyawka on 4/20/16.
 */
public class TravelCompanionTabsFragment extends HGBAbstractFragment {

    public static Fragment newInstance(int position) {
        Fragment fragment = new TravelCompanionTabsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // getCompanions();

        View rootView = inflater.inflate(R.layout.companion_search_tabs, container, false);
        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);


        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }



}
