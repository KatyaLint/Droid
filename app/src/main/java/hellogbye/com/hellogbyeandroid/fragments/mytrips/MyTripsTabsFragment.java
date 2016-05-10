package hellogbye.com.hellogbyeandroid.fragments.mytrips;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.CustomViewPager;

/**
 * Created by nyawka on 5/8/16.
 */
public class MyTripsTabsFragment extends HGBAbstractFragment {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private MyTripsPagerAdapter adapterPager;

    public static Fragment newInstance(int position) {
        Fragment fragment = new MyTripsTabsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("Kate onCreateView getActivity = " + getActivity());
        System.out.println("Kate onCreateView getContext = " + getContext());
        View rootView = inflater.inflate(R.layout.my_trips_main_tabs_layout, container, false);


        tabLayout = (TabLayout)rootView.findViewById(R.id.my_trips_tabs_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getContext().getString(R.string.my_trips_upcoming)));
        tabLayout.addTab(tabLayout.newTab().setText(getContext().getString(R.string.my_trips_history)));
        tabLayout.addTab(tabLayout.newTab().setText(getContext().getString(R.string.my_trips_favorites)));

        viewPager = (CustomViewPager) rootView.findViewById(R.id.my_trips_view_pager);
        viewPager.setPagingEnabled(false);


        setupViewPager();


        return rootView;
    }


    private void setupViewPager() {
        adapterPager = new MyTripsPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount()) ;

        viewPager.setAdapter(adapterPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                int position = tab.getPosition();


          //      Fragment fragment =  ((TabViewMainFragment)adapterPager.getItem(position));


                ((TabViewMainFragment)adapterPager.getItem(position)).updateTabsView(position, getActivity());
           //     tabSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



    @Override
    public void onDestroyView() {
        System.out.println("Kate onDestroy");
        super.onDestroyView();
        adapterPager.onDestroyFragments(getActivity().getSupportFragmentManager());

        adapterPager = null;

        viewPager.removeAllViews();
        viewPager = null;

        tabLayout.removeAllViews();
        tabLayout.removeAllTabs();
        tabLayout = null;

    }
}
