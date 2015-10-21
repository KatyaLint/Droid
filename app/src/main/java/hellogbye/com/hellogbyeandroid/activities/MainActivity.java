
package hellogbye.com.hellogbyeandroid.activities;



import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;

import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.HGBMainInterface;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.NavListAdapter;
import hellogbye.com.hellogbyeandroid.fragments.AccountSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.ContentFragment;
import hellogbye.com.hellogbyeandroid.fragments.HelpFeedbackFragment;
import hellogbye.com.hellogbyeandroid.fragments.HistoryFragment;
import hellogbye.com.hellogbyeandroid.fragments.HomeFragment;
import hellogbye.com.hellogbyeandroid.fragments.ItineraryFragment;
import hellogbye.com.hellogbyeandroid.fragments.MyTripsFragment;
import hellogbye.com.hellogbyeandroid.fragments.PrefrenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.TravelCompanionsFragment;
import hellogbye.com.hellogbyeandroid.models.NavItem;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;

public class MainActivity extends AppCompatActivity implements NavListAdapter.OnItemClickListener, HGBMainInterface {

    private CharSequence mTitle;
    //private String[] mNavTitles;
//    private RoundedImageView mProfileImage;
    private NavListAdapter mAdapter;
    private ArrayList<NavItem> mNavItemsList;
    private CostumeToolBar mToolbar;


    private UserTravelVO mUserTravelOrder;
    private List<AlternativeFlightsVO> alternativeFlights;



    private NavigationView navigationView;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_layout);


        //   mTitle = mDrawerTitle = getTitle();
        // mNavTitles = getResources().getStringArray(R.array.nav_draw_array);
//        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer_rv);
//        mNavDrawerLinearLayout = (LinearLayout) findViewById(R.id.drawer);
//        mProfileImage = (RoundedImageView) findViewById(R.id.nav_profile_image);

        // set a custom shadow that overlays the main content when the drawer opens
        // mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // improve performance by indicating the list if fixed size.
//        mDrawerList.setHasFixedSize(true);
//        mDrawerList.setLayoutManager(new LinearLayoutManager(this));

        // set up the drawer's list view with items and click listener
        loadNavItems();
        mAdapter = new NavListAdapter(mNavItemsList, this, getApplicationContext());

//        mDrawerList.setAdapter(mAdapter);

        mToolbar = (CostumeToolBar)findViewById(R.id.toolbar_costume);
        initToolBar();


//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);




        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                }
                else{
                    menuItem.setChecked(true);
                }
                selectItem(menuItem.getOrder());
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                return true;
            }


        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }





    private void initToolBar() {

       // setSupportActionBar(mToolbar);
        //DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


//        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
//                this, mDrawerLayout, mToolbar,
//                R.string.drawer_open,  /* "open drawer" description for accessibility */
//                R.string.drawer_close
//        );
//        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //kate
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//
        //----------------

<<<<<<< HEAD

     //   mDrawerToggle.syncState();


    //    HGBUtility.loadImage(getApplicationContext(), "http://a.abcnews.com/images/Technology/HT_ari_sprung_jef_140715_16x9_992.jpg", mProfileImage);
=======
        HGBUtility.loadHotelImage(getApplicationContext(), "http://a.abcnews.com/images/Technology/HT_ari_sprung_jef_140715_16x9_992.jpg", mProfileImage);
>>>>>>> origin/master
        selectItem(ToolBarNavEnum.HOME.getNavNumber());

    }

    private void loadNavItems() {
        mNavItemsList = new ArrayList<>();
        mNavItemsList.add(new NavItem(ToolBarNavEnum.HOME, true));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.ITINARERY, false));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.HISTORY, false));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.TRIPS, false));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.COMPANIONS, false));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.PREFERENCE, false));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.ACCOUNT, false));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.HELP, false));


        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        Menu menu = navigationView.getMenu();
        for(NavItem navItemMenu : mNavItemsList){
            menu.add(Menu.NONE,Menu.NONE,navItemMenu.getIndex(),navItemMenu.getName());
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mNavDrawerLinearLayout);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
//        if (mDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        // Handle action buttons
//        switch (item.getItemId()) {
////            case R.id.action_websearch:
////                // create intent to perform web search for this planet
////                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
////                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
////                // catch event that there's no activity to handle intent
////                if (intent.resolveActivity(getPackageManager()) != null) {
////                    startActivity(intent);
////                } else {
////                    Toast.makeText(this, "app not avail", Toast.LENGTH_LONG).show();
////                }
////                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }


        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    /* The click listener for RecyclerView in the navigation drawer */
    @Override
    public void onClick(View view, int position) {
        //selectItem(position);
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);
        int navPosition = position;//navBar.getNavNumber();
        switch (navBar) {
            case HOME:
                fragment = HomeFragment.newInstance(navPosition);
                break;
            case HISTORY:
                fragment = HistoryFragment.newInstance(navPosition);
                break;
            case TRIPS:
                fragment = MyTripsFragment.newInstance(navPosition);
                break;
            case COMPANIONS:
                fragment = TravelCompanionsFragment.newInstance(navPosition);
                break;
            case PREFERENCE:
                fragment = PrefrenceSettingsFragment.newInstance(navPosition);
                break;
            case ACCOUNT:
                fragment = AccountSettingsFragment.newInstance(navPosition);
                break;
            case HELP:
                fragment = HelpFeedbackFragment.newInstance(navPosition);
                break;
            case ITINARERY:
                fragment = ItineraryFragment.newInstance(navPosition);
                break;

        }


    HGBUtility.goToNextFragmentIsAddToBackStack(this,fragment,true);

        mToolbar.initToolBarItems();
        mToolbar.updateToolBarView(position);
//        mDrawerLayout.closeDrawer(mNavDrawerLinearLayout);
    }

    @Override
    public void onBackPressed() {
            if(!HGBUtility.clearBackStackAndGoToNextFragment(this))
            {
               // super.onBackPressed();
            }
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
//        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
//        mDrawerToggle.onConfigurationChanged(newConfig);
    }















    public CostumeToolBar getmToolbar() {
        return mToolbar;
    }

    public void setmToolbar(CostumeToolBar mToolbar) {
        this.mToolbar = mToolbar;
    }




    @Override
    public void openVoiceToTextControl() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now...");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            Log.v("Speech", "Could not find any Speech Recognition Actions");
        }
    }

    @Override
    public void setTravelOrder(UserTravelVO travelorder) {
        mUserTravelOrder = travelorder;

    }

    @Override
    public void setAlternativeFlights(List<AlternativeFlightsVO> alternativeFlightsVO) {
        this.alternativeFlights = alternativeFlightsVO;
    }



    @Override
    public UserTravelVO getTravelOrder() {
        return mUserTravelOrder;
    }

    @Override
    public List<AlternativeFlightsVO> getAlternativeFlights() {
        return alternativeFlights;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            HomeFragment fragment = (HomeFragment) getFragmentManager().findFragmentByTag(HomeFragment.class.toString());
            if (fragment != null) {
                fragment.handleClick(matches.get(0));
            }
        }
    }



}
