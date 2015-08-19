
package hellogbye.com.hellogbyeandroid.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.NavListAdapter;
import hellogbye.com.hellogbyeandroid.fragments.AccountSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.HelpFeedbackFragment;
import hellogbye.com.hellogbyeandroid.fragments.HistoryFragment;
import hellogbye.com.hellogbyeandroid.fragments.HomeFragment;
import hellogbye.com.hellogbyeandroid.fragments.MyTripsFragment;
import hellogbye.com.hellogbyeandroid.fragments.PrefrenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.TravelCompanionsFragment;
import hellogbye.com.hellogbyeandroid.models.NavItem;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;

public class MainActivity extends ActionBarActivity implements NavListAdapter.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private LinearLayout mNavDrawerLinearLayout;
    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mNavTitles;
    private RoundedImageView mProfileImage;
    private NavListAdapter mAdapter;
    private ArrayList<NavItem> mNavItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_layout);

        mTitle = mDrawerTitle = getTitle();
        mNavTitles = getResources().getStringArray(R.array.nav_draw_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer_rv);
        mNavDrawerLinearLayout = (LinearLayout) findViewById(R.id.drawer);
        mProfileImage = (RoundedImageView)findViewById(R.id.nav_profile_image);

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // improve performance by indicating the list if fixed size.
        mDrawerList.setHasFixedSize(true);
        mDrawerList.setLayoutManager(new LinearLayoutManager(this));

        // set up the drawer's list view with items and click listener
        loadNavItems();
        mAdapter = new NavListAdapter(mNavItemsList, this,getApplicationContext());

        mDrawerList.setAdapter(mAdapter);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
                this,  mDrawerLayout, mToolbar,
                R.string.drawer_open,  /* "open drawer" description for accessibility */
               R.string.drawer_close
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        HGBUtility.loadImage(getApplicationContext(), "http://a.abcnews.com/images/Technology/HT_ari_sprung_jef_140715_16x9_992.jpg", mProfileImage);
        selectItem(0);
    }

    private void loadNavItems() {
        mNavItemsList = new ArrayList<>();
        mNavItemsList.add(new NavItem(mNavTitles[0],true));
        mNavItemsList.add(new NavItem(mNavTitles[1],false));
        mNavItemsList.add(new NavItem(mNavTitles[2],false));
        mNavItemsList.add(new NavItem(mNavTitles[3],false));
        mNavItemsList.add(new NavItem(mNavTitles[4],false));
        mNavItemsList.add(new NavItem(mNavTitles[5],false));
        mNavItemsList.add(new NavItem(mNavTitles[6],false));

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//      //  getMenuInflater().inflate(R.menu.navigation_drawer, menu);
//        return true;
//    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mNavDrawerLinearLayout);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch (item.getItemId()) {
//            case R.id.action_websearch:
//                // create intent to perform web search for this planet
//                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
//                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
//                // catch event that there's no activity to handle intent
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, "app not avail", Toast.LENGTH_LONG).show();
//                }
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listener for RecyclerView in the navigation drawer */
    @Override
    public void onClick(View view, int position) {
        selectItem(position);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;


        switch (position) {
            case 0:
                fragment = HomeFragment.newInstance(position);
                break;
            case 1:
                fragment = HistoryFragment.newInstance(position);
                break;
            case 2:
                fragment = MyTripsFragment.newInstance(position);
                break;
            case 3:
                fragment = TravelCompanionsFragment.newInstance(position);
                break;
            case 4:
                fragment = PrefrenceSettingsFragment.newInstance(position);
                break;
            case 5:
                fragment = AccountSettingsFragment.newInstance(position);
                break;
            case 6:
                fragment = HelpFeedbackFragment.newInstance(position);
                break;

        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        // update selected item title, then close the drawer
        setTitle(mNavTitles[position]);
        mDrawerLayout.closeDrawer(mNavDrawerLinearLayout);



    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
