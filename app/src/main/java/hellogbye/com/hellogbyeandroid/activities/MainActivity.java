
package hellogbye.com.hellogbyeandroid.activities;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.HGBMainInterface;
import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.NavListAdapter;
import hellogbye.com.hellogbyeandroid.fragments.AccountSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.fragments.AlternativeFlightsDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.CNCFragment;
import hellogbye.com.hellogbyeandroid.fragments.HelpFeedbackFragment;
import hellogbye.com.hellogbyeandroid.fragments.HistoryFragment;
import hellogbye.com.hellogbyeandroid.fragments.HomeFragment;
import hellogbye.com.hellogbyeandroid.fragments.HotelFragment;
import hellogbye.com.hellogbyeandroid.fragments.ItineraryFragment;
import hellogbye.com.hellogbyeandroid.fragments.MyTripsFragment;
import hellogbye.com.hellogbyeandroid.fragments.PaymentDetailsFragemnt;
import hellogbye.com.hellogbyeandroid.fragments.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.TabsFragmentSettings;
import hellogbye.com.hellogbyeandroid.fragments.TravelCompanionsFragment;
import hellogbye.com.hellogbyeandroid.fragments.TravlerDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.TravlersFragment;
import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.models.NavItem;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.network.Parser;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.SpeechRecognitionUtil;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;

public class MainActivity extends AppCompatActivity implements NavListAdapter.OnItemClickListener, HGBMainInterface, ActionBar.OnMenuVisibilityListener {
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
    private CostumeToolBar mToolbar;
    private FontTextView mName;
    private HGBPreferencesManager hgbPrefrenceManager;
    private ImageButton imageButton;
    private ImageButton purchaseButton;
    private ArrayList<CNCItem> mCNCItems;
    private UserTravelVO mUserTravelOrder;
    private List<NodesVO> alternativeFlights;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    protected OnBackPressedListener onBackPressedListener;
    private String solutionID;
    private ActionBar actionBar;
    private List<SettingsAttributeParamVO> mSettingsAttribute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_layout);
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());

        //check if we have travelitinery in db
        String strTravel = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_LAST_TRAVEL_VO, "");
        if (!"".equals(strTravel)) {
            UserTravelVO userTravelVO = (UserTravelVO) Parser.parseAirplaneData(strTravel);
            setTravelOrder(userTravelVO);
        }


        mTitle = mDrawerTitle = getTitle();
        // mNavTitles = getResources().getStringArray(R.array.nav_draw_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RecyclerView) findViewById(R.id.left_drawer_rv);
        mName = (FontTextView) findViewById(R.id.nav_profile_name);
        mNavDrawerLinearLayout = (LinearLayout) findViewById(R.id.drawer);
        mProfileImage = (RoundedImageView) findViewById(R.id.nav_profile_image);

        // set a custom shadow that overlays the main content when the drawer opens
        // mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // improve performance by indicating the list if fixed size.
        mDrawerList.setHasFixedSize(true);
        mDrawerList.setLayoutManager(new LinearLayoutManager(this));

        // set up the drawer's list view with items and click listener
        loadNavItems();
        mAdapter = new NavListAdapter(mNavItemsList, this, getApplicationContext());

        mDrawerList.setAdapter(mAdapter);

        mToolbar = (CostumeToolBar) findViewById(R.id.toolbar_costume);


        initToolBar();
        setNameInNavDraw();

    }

    private void setNameInNavDraw() {

        String strName = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_NAME, "");

        if (strName.equals(hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_NAME, ""))) {
            ConnectionManager.getInstance(MainActivity.this).getUserProfile(new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {

                    UserData userr = (UserData) data;
                    String name = userr.getFirstname() + " " + userr.getLastname();
                    hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_NAME, "");
                    mName.setText(name);

                }

                @Override
                public void onError(Object data) {

                }
            });
        } else {
            mName.setText(strName);
        }


    }

    private void initToolBar() {

        setSupportActionBar(mToolbar);
        imageButton = (ImageButton) mToolbar.findViewById(R.id.keyboard);
        purchaseButton = (ImageButton) mToolbar.findViewById(R.id.purchaseButton);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(ToolBarNavEnum.PAYMENT_DETAILS.getNavNumber(),null);
            }
        });
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String id = (String) v.getTag();
//                setHomeFragmentState(id);

                clearCNCItems();
            }
        });


        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar,
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                HGBUtility.hideKeyboard(getApplicationContext(), drawerView);

            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerToggle.syncState();


        //HGBUtility.loadHotelImage(getApplicationContext(), "http://a.abcnews.com/images/Technology/HT_ari_sprung_jef_140715_16x9_992.jpg", mProfileImage);
        selectItem(ToolBarNavEnum.HOME.getNavNumber(),null);

    }

    private void clearCNCItems() {

        setCNCItems(null);
        setTravelOrder(null);
        hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_LAST_TRAVEL_VO);
        Fragment currentFragment = getFragmentManager().findFragmentByTag(CNCFragment.class.toString());
        ((CNCFragment) currentFragment).initList();
    }

    private void setHomeFragmentState(String id) {
        try {
            Fragment currentFragment = getFragmentManager().findFragmentByTag(HomeFragment.class.toString());

            if (id.equals("keyboard")) {
                ((HomeFragment) currentFragment).setKeyboardMode();
            } else if (id.equals("mic")) {
                ((HomeFragment) currentFragment).setMicMode();
            }
            setHomeImage(id);

        } catch (Exception e) {
            e.printStackTrace();
            Crashlytics.logException(e);
        }

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
        selectItem(position,null);
    }


    @Override
    public void continueFlow(int fragment) {

        if(fragment == ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber()) {
            selectItem(ToolBarNavEnum.ITINARERY.getNavNumber(),null);
        }
    }

    @Override
    public void setAccountSettingsAttribute(List<SettingsAttributeParamVO> settingsAttribute) {
        this.mSettingsAttribute = settingsAttribute;
    }

    @Override
    public List<SettingsAttributeParamVO> getAccountSettingsAttribute() {
        return mSettingsAttribute;
    }

    @Override
    public void setAccountSettingsAttributeSpecific(List<SettingsAttributesVO> settingsAttribute) {

    }

    @Override
    public List<SettingsAttributesVO> getAccountSettingsAttributeSpecific() {
        return null;
    }


    public void selectItem(int position,Bundle bundle) {
        // update the main content by replacing fragments

        Fragment fragment = null;
        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);
        boolean stashToBack = true;
        int navPosition = position;//navBar.getNavNumber();
        switch (navBar) {
            case HOME:
                //  fragment = HomeFragment.newInstance(navPosition);
                fragment = CNCFragment.newInstance(navPosition);

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
                fragment = PreferenceSettingsFragment.newInstance(navPosition);
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
            case ALTERNATIVE_FLIGHT:
                fragment = AlternativeFlightFragment.newInstance(navPosition);
                stashToBack = false;
                break;
            case HOTEL:
                fragment = HotelFragment.newInstance(navPosition);
                break;
            case ALTERNATIVE_FLIGHT_DETAILS:
                fragment = AlternativeFlightsDetailsFragment.newInstance(navPosition);
                stashToBack = false;
                break;
            case PAYMENT_DETAILS:
                fragment = PaymentDetailsFragemnt.newInstance(navPosition);
                break;
            case PREFERENCES_TAB_SETTINGS:
                fragment = TabsFragmentSettings.newInstance(navPosition);
                break;
            case PAYMENT_TRAVLERS:
                fragment = TravlersFragment.newInstance(navPosition);
                break;
            case PAYMENT_TRAVLERS_DETAILS:
                fragment = TravlerDetailsFragment.newInstance(navPosition);
                break;



        }
        if(bundle != null){
            fragment.setArguments(bundle);
        }
        HGBUtility.goToNextFragmentIsAddToBackStack(this, fragment, stashToBack);
        mToolbar.initToolBarItems();
        mToolbar.updateToolBarView(position);
        mDrawerLayout.closeDrawer(mNavDrawerLinearLayout);
    }


    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        }

        //TODO this is when I want the fragment to contorl the back -Kate I suggest we do this for all Fragments
        FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1);
        String str = backEntry.getName();
        if (str.equals(HotelFragment.class.toString())) {
            return;
        }

        if (!HGBUtility.clearBackStackAndGoToNextFragment(this)) {
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
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
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
            boolean recognizerIntent =
                    SpeechRecognitionUtil.isSpeechAvailable(this);
            if (recognizerIntent) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now...");
                startActivityForResult(intent, 0);
            } else {
                Crashlytics.logException(new Exception("Speech not avaibale"));
                Log.e("MainActvity", "Speeach not avaiable");
            }

        } catch (Exception e) {
            Log.v("Speech", "Could not find any Speech Recognition Actions");
        }
    }

    @Override
    public void setTravelOrder(UserTravelVO travelorder) {
        mUserTravelOrder = travelorder;
        if (travelorder == null) {
            setSolutionID(null);
        } else {
            setSolutionID(mUserTravelOrder.getmSolutionID());
            Gson gson = new Gson();
            String json = gson.toJson(travelorder);
            hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_LAST_TRAVEL_VO, json);
        }

    }

    @Override
    public void setCNCItems(ArrayList<CNCItem> cncItemArrayList) {
        this.mCNCItems = cncItemArrayList;
    }

    @Override
    public void setSolutionID(String solutionID) {
        this.solutionID = solutionID;
    }

    @Override
    public String getSolutionID() {
        return solutionID;
    }

    @Override
    public void setAlternativeFlights(List<NodesVO> alternativeFlightsVO) {
        this.alternativeFlights = alternativeFlightsVO;
    }


    @Override
    public UserTravelVO getTravelOrder() {
        return mUserTravelOrder;
    }

    @Override
    public ArrayList<CNCItem> getCNCItems() {
        return mCNCItems;
    }

    @Override
    public List<NodesVO> getAlternativeFlights() {
        return alternativeFlights;
    }

    @Override
    public void addCNCItem(CNCItem cncitem) {

        if(mCNCItems == null ){
            mCNCItems = new ArrayList<>();
        }

        mCNCItems.add(cncitem);


    }

    @Override
    public void callRefreshItinerary(final int fragment) {

        ConnectionManager.getInstance(MainActivity.this).getItinerary(solutionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                setTravelOrder((UserTravelVO) data);
                continueFlow(fragment);
            }

            @Override
            public void onError(Object data) {
                Log.e("MainActivity", "Problem updating grid  " + data);
            }
        });

    }

    @Override
    public CostumeToolBar getToolBar() {
        return mToolbar;
    }

    @Override
    public void goToFragment(int fragmentname,Bundle bundle) {
        selectItem(fragmentname,bundle);
    }






    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            //   String fragmentTag = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();
            Fragment currentFragment = getFragmentManager().findFragmentByTag(CNCFragment.class.toString());
            ((CNCFragment) currentFragment).handleMyMessage(matches.get(0));
//            if (currentFragment instanceof CNCFragment) {
//                ((CNCFragment) currentFragment).handleMyMessage(matches.get(0));
//            }
//            HomeFragment fragment = (HomeFragment) getFragmentManager().findFragmentByTag(HomeFragment.class.toString());
//            if (fragment != null) {
//                fragment.handleClick(matches.get(0));
//            }
        }
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    @Override
    public void setHomeImage(String id) {
        if (id.equals("keyboard")) {
            imageButton.setBackgroundResource(R.drawable.app_bar_microphone_icn);
            imageButton.setTag("mic");
        } else if (id.equals("mic")) {
            imageButton.setBackgroundResource(R.drawable.keyboard_icon);
            imageButton.setTag("keyboard");
        }
    }

    @Override
    public void onMenuVisibilityChanged(boolean isVisible) {


        Log.d("", "");

    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }



    @Override
    protected void onStop() {
        super.onStop();
        hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        try {
            Gson gsonback = new Gson();
            String json = gsonback.toJson(mCNCItems);
            hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST, json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}