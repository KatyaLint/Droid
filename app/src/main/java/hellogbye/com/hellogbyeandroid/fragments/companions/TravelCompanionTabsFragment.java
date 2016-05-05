package hellogbye.com.hellogbyeandroid.fragments.companions;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.CustomViewPager;


/**
 * Created by nyawka on 4/20/16.
 */
public class TravelCompanionTabsFragment extends HGBAbstractFragment {

    private CustomViewPager viewPager;
    private TabLayout tabLayout;
    private PagerAdapterCostume adapterPager;


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

        View rootView = inflater.inflate(R.layout.companion_search_tabs, container, false);


        tabLayout = (TabLayout)rootView. findViewById(R.id.tabslayout);
        tabLayout.addTab(tabLayout.newTab().setText(getContext().getString(R.string.companion_travel)));
        tabLayout.addTab(tabLayout.newTab().setText(getContext().getString(R.string.companion_pending)));

        viewPager = (CustomViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);

        setupViewPager();

        return rootView;
    }



    private void setupViewPager() {

        adapterPager = new PagerAdapterCostume(getActivity().getSupportFragmentManager(), tabLayout.getTabCount()) ;



        viewPager.setAdapter(adapterPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabSelected(tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


    private void tabSelected(int position){

        switch (position) {
            case 0:
                ((CompanionsTabsViewClass)adapterPager.getItem(position)).isPendingTabs(false);
                break;
            case 1:
                ((CompanionsTabsViewClass)adapterPager.getItem(position)).isPendingTabs(true);
                break;
        }
    }

}
