/*
package hellogbye.com.hellogbyeandroid.fragments.mytrips;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionsPendingFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionsTravelers;

*/
/**
 * Created by nyawka on 5/8/16.
 *//*

public class MyTripsPagerAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private TabFavoritesView tabFavoritesView;
    private TabHistoryView tabHistoryView;
    private TabUpcomingTripsView tabUpcomingTripsView;

    private ArrayList<Fragment> fragments = new ArrayList<>();
private FragmentManager fm;
int baseID = 1000;
    public MyTripsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        System.out.println("Kate MyTripsPagerAdapter");
        this.mNumOfTabs = NumOfTabs;
        this.fm= fm;
*/
/*        tabUpcomingTripsView = new TabUpcomingTripsView();
        tabHistoryView = new TabHistoryView();
        tabFavoritesView = new TabFavoritesView();
        fragments.add(tabUpcomingTripsView);
        fragments.add(tabHistoryView);
        fragments.add(tabFavoritesView);*//*

    }

    //this is called when notifyDataSetChanged() is called
    @Override
    public int getItemPosition(Object object) {
        // refresh all fragments when data set changed
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("Kate destroyed");
        super.destroyItem(container, position, object);
        FragmentManager manager = ((Fragment) object).getFragmentManager();
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove((Fragment) object);
        trans.commit();
    }





    @Override
    public Fragment getItem(int position) {
        System.out.println("Kate MyTripsPagerAdapter getItem =" + position);
        Fragment fragment;
        switch (position) {
            case 0:
                if(tabUpcomingTripsView == null) {
                    tabUpcomingTripsView = (TabUpcomingTripsView) TabUpcomingTripsView.newInstance(position + baseID);
                    fragments.add(tabUpcomingTripsView);
                }
                return tabUpcomingTripsView;
            case 1:
                if(tabHistoryView == null) {
                    tabHistoryView = (TabHistoryView) TabHistoryView.newInstance(position + baseID);
                    fragments.add(tabHistoryView);

                }

                return tabHistoryView;
            case 2:
                if(tabFavoritesView == null) {
                    tabFavoritesView = (TabFavoritesView) TabFavoritesView.newInstance(position + baseID);
                    fragments.add(tabFavoritesView);
                }
                return tabFavoritesView;

            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return mNumOfTabs;
    }




    public void onDestroyFragments(FragmentManager supportFragmentManager) {

    */
/*    for(Fragment fragment : fragments){
            fragment = supportFragmentManager.findFragmentById(fragment.getId());
            if(fragment != null){
                supportFragmentManager.beginTransaction().remove(fragment).commit();
            }
        }*//*


        for(int i=0; i<fragments.size(); i++) {
            supportFragmentManager.beginTransaction().remove(fragments.get(i)).commit();
        }
        fragments.clear();


    }
    */
/* Set current adapter to null *//*


}*/
