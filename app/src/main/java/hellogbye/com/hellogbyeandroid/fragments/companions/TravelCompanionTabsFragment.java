package hellogbye.com.hellogbyeandroid.fragments.companions;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

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
    private ViewPagerAdapter adapter;
    private CompanionsTravelers companionsTravelers;


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

        viewPager = (CustomViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        setupViewPager();

        tabLayout = (TabLayout)rootView. findViewById(R.id.tabslayout);
        tabLayout.setupWithViewPager(viewPager);
        tabsClickListener();
        return rootView;
    }


    private void tabsClickListener(){
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewPager.setCurrentItem(tab.getPosition());
            switch (tab.getPosition()) {
                case 0:
                    companionsTravelers.isPendingTabs(false);
                    break;
                case 1:
                    companionsTravelers.isPendingTabs(true);
                    break;
            }
        }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {
        }
        @Override
        public void onTabReselected(TabLayout.Tab tab) {
        }
    });

    }


    private void setupViewPager() {
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        companionsTravelers =  new CompanionsTravelers();
        adapter.addFrag(new CompanionsTravelers(),getContext().getString(R.string.companion_travel));
        adapter.addFrag(new CompanionsPendingFragment(),getContext().getString(R.string.companion_pending));

        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


        public void removeFragments(){

            for (Fragment fragment: mFragmentList){
                FragmentManager manager = ((Fragment) fragment).getFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove((Fragment) fragment);
                trans.commit();
            }

            mFragmentList.clear();
            mFragmentTitleList.clear();
        }
    }



    @Override
    public void onDestroyView() {
        adapter.removeFragments();
        viewPager.removeAllViewsInLayout();
        tabLayout.removeAllViews();
        adapter = null;

        super.onDestroyView();
    }
}
