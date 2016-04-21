package hellogbye.com.hellogbyeandroid.fragments.companions;




import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.companion.CompanionAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;

/**
 * Created by nyawka on 4/20/16.
 */
public class TravelCompanionTabsFragment extends HGBAbstractFragment {


    private int[] tabIcons = {
            R.drawable.ic_delete,
            R.drawable.abc_ic_clear_mtrl_alpha,
            R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha
    };
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static String TRAVEL_COMPANIONS = "Travel Companions";
    private static String PENDING_COMPANIONS = "Pending Companions";
    private ArrayList<CompanionVO> companionsVO;


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

        companionsVO = getActivityInterface().getCompanions();
        View rootView = inflater.inflate(R.layout.companion_search_tabs, container, false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)rootView. findViewById(R.id.tabslayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();




        return rootView;
    }



    private void setupTabIcons() {
        tabLayout.getTabAt(0).setText(TRAVEL_COMPANIONS);
        tabLayout.getTabAt(1).setText(PENDING_COMPANIONS);

//        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);

    }



    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new CompanionsTravelers(), "ONE");
        adapter.addFrag(new CompanionsPendingFragment(), "TWO");

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
    }


}
