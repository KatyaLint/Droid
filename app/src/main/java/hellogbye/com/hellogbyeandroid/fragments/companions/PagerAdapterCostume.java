/*
package hellogbye.com.hellogbyeandroid.fragments.companions;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

*/
/**
 * Created by nyawka on 5/5/16.
 *//*

public class PagerAdapterCostume extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private CompanionsTravelers companionsTravelers;
    private CompanionsPendingFragment companionsPendingFragment;

    public PagerAdapterCostume(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if(companionsTravelers == null) {
                    companionsTravelers = new CompanionsTravelers();
                }
                return companionsTravelers;
            case 1:
                if(companionsPendingFragment == null) {
                    companionsPendingFragment = new CompanionsPendingFragment();
                }
                return companionsPendingFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }


}*/
