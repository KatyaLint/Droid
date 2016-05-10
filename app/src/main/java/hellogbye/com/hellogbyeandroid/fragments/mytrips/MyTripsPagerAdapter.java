package hellogbye.com.hellogbyeandroid.fragments.mytrips;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionsPendingFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionsTravelers;

/**
 * Created by nyawka on 5/8/16.
 */
public class MyTripsPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private TabFavoritesView tabFavoritesView;
    private TabHistoryView tabHistoryView;
    private TabUpcomingTripsView tabUpcomingTripsView;

    private ArrayList<Fragment> fragments = new ArrayList<>();
private FragmentManager fm;
    public MyTripsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        System.out.println("Kate MyTripsPagerAdapter");
        this.mNumOfTabs = NumOfTabs;
        this.fm= fm;
/*        tabUpcomingTripsView = new TabUpcomingTripsView();
        tabHistoryView = new TabHistoryView();
        tabFavoritesView = new TabFavoritesView();
        fragments.add(tabUpcomingTripsView);
        fragments.add(tabHistoryView);
        fragments.add(tabFavoritesView);*/
    }

    @Override
    public Fragment getItem(int position) {
        System.out.println("Kate MyTripsPagerAdapter getItem =" + position);

        switch (position) {
            case 0:
                if(tabUpcomingTripsView == null) {
                    tabUpcomingTripsView = new TabUpcomingTripsView();
                    fragments.add(tabUpcomingTripsView);
                }
                return tabUpcomingTripsView;
            case 1:
                if(tabHistoryView == null) {
                    tabHistoryView = new TabHistoryView();
                    fragments.add(tabHistoryView);
                }

                return tabHistoryView;
            case 2:
                if(tabFavoritesView == null) {
                    tabFavoritesView = new TabFavoritesView();
                    fragments.add(tabFavoritesView);
                }
                return tabFavoritesView;

            default:
                tabUpcomingTripsView = new TabUpcomingTripsView();
                return tabUpcomingTripsView;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }




    public void onDestroyFragments(FragmentManager supportFragmentManager) {

        for(Fragment fragment : fragments){
            fragment = supportFragmentManager.findFragmentById(fragment.getId());
            if(fragment != null){
                supportFragmentManager.beginTransaction().remove(fragment).commit();
            }
        }

    }
}