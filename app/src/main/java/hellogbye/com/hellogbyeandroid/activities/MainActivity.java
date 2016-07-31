
package hellogbye.com.hellogbyeandroid.activities;


import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.NavListAdapter;
import hellogbye.com.hellogbyeandroid.fragments.CNCFragment;

import hellogbye.com.hellogbyeandroid.fragments.HotelFragment;

import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightsDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.AddCreditCardFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.CheckoutConfirmationFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.CreditCardListFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.HazardousNoticeFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.NewPaymentDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.SummaryPaymentFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.TravelerDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.TravelersFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionsTravelers;
import hellogbye.com.hellogbyeandroid.fragments.companions.TravelCompanionTabsWidgetFragment;
import hellogbye.com.hellogbyeandroid.fragments.freeuser.FreeUserFragment;
import hellogbye.com.hellogbyeandroid.fragments.itinerary.ItineraryFragment;
import hellogbye.com.hellogbyeandroid.fragments.membership.MembershipFragment;
import hellogbye.com.hellogbyeandroid.fragments.mytrips.TripsTabsView;
import hellogbye.com.hellogbyeandroid.fragments.notification.NotificationFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsRadioCheckFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesCheckListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesDragListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesSearchListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesSettingsMainClass;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesTabsFragmentSettings;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountPersonalInfoHelpAndFeedbackFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.PreferenceSettingsEmailFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountPersonalInfoSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.NavItem;
import hellogbye.com.hellogbyeandroid.models.PersonalUserInformationVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.network.Parser;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.SpeechRecognitionUtil;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;


public class MainActivity extends AppCompatActivity implements NavListAdapter.OnItemClickListener, HGBVoiceInterface, HGBFlowInterface, ActionBar.OnMenuVisibilityListener {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mDrawerList;
    private LinearLayout mNavDrawerLinearLayout;

    private android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    private RoundedImageView mProfileImage;
    private NavListAdapter mAdapter;
    private ArrayList<NavItem> mNavItemsList;
    private CostumeToolBar mToolbar;
    private FontTextView mName;
    private HGBPreferencesManager hgbPrefrenceManager;
    private ImageButton imageButton;
    private ImageButton up_bar_favorite;

    private OnBackPressedListener onBackPressedListener;

    private UserProfileVO mCurrentUser;

    public FontTextView my_trip_profile;
    private HGBSaveDataClass hgbSaveDataClass = new HGBSaveDataClass();
    private PreferenceSettingsFragment.OnItemClickListener editClickCB;
    //private MyTripsFragment.OnItemClickListener editMyTripsClickCB;
    private FontTextView itirnarary_title_Bar;

    private ArrayList<UserProfileVO> mTravelList = new ArrayList<>();
    private ArrayList<CountryItemVO> mEligabileCountryList = new ArrayList<>();
    private ArrayList<CreditCardItem> mCreditCardList = new ArrayList<>();
    private boolean isFreeUser;

    private FrameLayout frameLayout;
    private FontTextView preference_save_changes;
    private PreferencesSettingsMainClass.saveButtonClicked onSavePreferencesButtonClicked;

    public HGBSaveDataClass getHGBSaveDataClass() {
        return hgbSaveDataClass;
    }

    private HashSet<CreditCardItem> mSelectedCreditCards = new HashSet<>();

    private HashMap<String, String> mBookingHashMap = new HashMap<>();

   // private ImageView mRightPaneImageView;

    private final static int RIGHT_PANE_ANIMATION_TIME = 300;

    private boolean isRightPaneOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hgbSaveDataClass.setPersonalUserInformation(new PersonalUserInformationVO());
        setContentView(R.layout.main_activity_layout);

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        hgbSaveDataClass.setPreferenceManager(hgbPrefrenceManager); //= new HGBSaveDataClass(this, hgbPrefrenceManager);
        isFreeUser = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, false);

        //check if we have travelitinery in db
        String strTravel = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_LAST_TRAVEL_VO, "");
        if (!"".equals(strTravel)) {
            UserTravelMainVO userTravelVO = (UserTravelMainVO) Parser.parseAirplaneData(strTravel);
            hgbSaveDataClass.setTravelOrder(userTravelVO);
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


        getUserData();
        getCompanionsFromServer();
   //     initRightPane();

        getCountries();

        boolean locationToken = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.HGB_LOCATION_TOKEN, true);
        String location = HGBUtility.getLocation(MainActivity.this, locationToken);
        if(location != null && hgbSaveDataClass.getTravelOrder()!= null){
            String[] locationArr = location.split("&");
            hgbSaveDataClass.getTravelOrder().setLocation(locationArr);
            hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_LOCATION_TOKEN, false);
        }

    }


    private void getCurrentItinerary(String solutionId){

        ConnectionManager.getInstance(MainActivity.this).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;
                hgbSaveDataClass.setTravelOrder(userTravelMainVO);
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);

               // ErrorMessage(data);

            }
        });
    }



    private void getCountries(){
        ConnectionManager.getInstance(MainActivity.this).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                //responceText.setText((String) data);
                BookingRequestVO bookingrequest = (BookingRequestVO)data;
                hgbSaveDataClass.setBookingRequest(bookingrequest);
                hgbSaveDataClass.getBookingRequest().sortCountryItems();
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }

    private void getAccountsProfiles() {
        ConnectionManager.getInstance(MainActivity.this).getUserProfileAccounts(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<AccountsVO> accounts = (ArrayList<AccountsVO>) data;
                hgbSaveDataClass.setAccounts(accounts);
              //  AccountsVO account = accounts.get(0);
              //  hgbSaveDataClass.getCurrentUser().setEmailaddress(account.getEmail());
                editProfileTypeMainToolBar();
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }


    public void editProfileTypeMainToolBar() {
        ArrayList<AccountsVO> accounts = hgbSaveDataClass.getAccounts();
        my_trip_profile = (FontTextView) findViewById(R.id.my_trip_profile);

        for(AccountsVO account: accounts){
            String userEmailLogIn = hgbSaveDataClass.getPersonalUserInformation().getUserEmailLogIn();
            if(account.getEmail().equals(userEmailLogIn)){
                my_trip_profile.setText(account.getTravelpreferenceprofile().getProfilename());
                my_trip_profile.setTag(account.getTravelpreferenceprofile().getId());
             //   hgbSaveDataClass.getCurrentUser().setmTravelPreferencesProfileId(account.getTravelpreferenceprofile().getId());
                hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileId(account.getTravelpreferenceprofile().getId());
                break;
            }
        }
    }


    private void getCompanionsFromServer() {
            ConnectionManager.getInstance(MainActivity.this).getCompanions(new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    ArrayList<CompanionVO> companions =(ArrayList<CompanionVO>)data;
                    hgbSaveDataClass.setCompanions(companions);
                    getCompanionsInvitation();
                }


            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }



    private void getCompanionsInvitation(){
        ConnectionManager.getInstance(MainActivity.this).getCompanionInvitation(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<CompanionVO> companionsInvitation =(ArrayList<CompanionVO>)data;
                hgbSaveDataClass.addInvitationCompanionsToCompanions(companionsInvitation);
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }


    private void getUserData(){

        ConnectionManager.getInstance(MainActivity.this).getUserProfile(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserProfileVO mCurrentUser = (UserProfileVO) data;
                String logInEmail = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL,"");
                hgbSaveDataClass.getPersonalUserInformation().setUserEmailLogIn(logInEmail);
                hgbSaveDataClass.setCurrentUser(mCurrentUser);
                String profileID = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID,"");
                hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileId(profileID);
                //     ImageView my_trips_image_profile = (ImageView)findViewById(R.id.my_trips_image_profile);
                HGBUtility.getAndSaveUserImage(mCurrentUser.getAvatar(), mProfileImage, null);
                //my_trips_image_profile.setImageBitmap(HGBUtility.getBitmapFromCache(getBaseContext()));
                getAccountsProfiles();
                selectItem(ToolBarNavEnum.TRIPS.getNavNumber(), null,true);

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }


    public void updateProfilePicture(Bitmap thumbnail){
        if(mProfileImage != null && thumbnail != null){
            mProfileImage.setImageBitmap(thumbnail);
        }

    }

    private void setNameInNavDraw() {

        String strName = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_NAME, "");
        // For remember me
        if (strName.equals(hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_NAME, ""))) {
            ConnectionManager.getInstance(MainActivity.this).getUserProfile(new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    mCurrentUser = (UserProfileVO) data;
                    String name = mCurrentUser.getFirstname() + " " + mCurrentUser.getLastname();
                    hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_NAME, "");
                    mName.setText(name);
                }

                @Override
                public void onError(Object data) {
                    HGBErrorHelper errorHelper = new HGBErrorHelper();
                    errorHelper.setMessageForError((String) data);
                    errorHelper.show(getFragmentManager(), (String) data);
                }
            });
        } else {
            mName.setText(strName);
        }
    }

    private void toolBarProfileChnage() {

        final LinearLayout tool_bar_profile_name = (LinearLayout) mToolbar.findViewById(R.id.tool_bar_profile_name);
        tool_bar_profile_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle args = new Bundle();
                args.putString("edit_mode", "true");
                goToFragment(ToolBarNavEnum.TREVEL_PREFERENCE.getNavNumber(), args);
                LinearLayout edit_preferences = (LinearLayout) mToolbar.findViewById(R.id.preferences_edit_mode);
                edit_preferences.setVisibility(View.GONE);
            }
        });


        final ImageButton toolbar_go_to_iternerary = (ImageButton)mToolbar.findViewById(R.id.toolbar_go_to_iternerary);
        toolbar_go_to_iternerary.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

               // goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(), null);


                if(hgbSaveDataClass.getTravelOrder() == null){
                    toolbar_go_to_iternerary.setEnabled(false);
                }else {
                    goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(), null);
                }


            }
        });

    }

    private void preferencesChanges() {
        final ImageButton edit_preferences = (ImageButton) mToolbar.findViewById(R.id.edit_preferences);
        edit_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View checkButton = mToolbar.findViewById(R.id.check_preferences);
                if (checkButton.getVisibility() == View.VISIBLE) {
                    //delete
                    editClickCB.onItemClick("delete");
                } else if (checkButton.getVisibility() == View.GONE) {
                    edit_preferences.setBackgroundResource(R.drawable.ic_delete);
                    mToolbar.findViewById(R.id.check_preferences).setVisibility(View.VISIBLE);
                    editClickCB.onItemClick("edit");
                }
            }
        });

        final ImageButton check_preferences = (ImageButton) mToolbar.findViewById(R.id.check_preferences);
        check_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mToolbar.findViewById(R.id.check_preferences).setVisibility(View.GONE);
                edit_preferences.setBackgroundResource(R.drawable.edit_img);
                editClickCB.onItemClick("done");
            }
        });
    }


    private void initToolBar() {

        setSupportActionBar(mToolbar);

        preferencesChanges();

        toolBarProfileChnage();
        setOnClickListenerForItineraryTopBar();
        setOnClickListenerForSavePreferences();

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar,
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                if(view.getId()== R.id.right_content_frame){
                    animateRightPaneClosed();
                    FragmentManager fm = getSupportFragmentManager();
                    //if you added fragment CNCFragment layout xml
                    CNCFragment fragment = (CNCFragment)fm.findFragmentById(R.id.right_content_frame);
                    fragment.stopTextTutorial();
                }
                super.onDrawerClosed(view);

            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                super.onDrawerSlide(drawerView, 0); // this disables the arrow @ completed state
                HGBUtility.hideKeyboard(getApplicationContext(), drawerView);
                if(drawerView.getId()== R.id.right_content_frame){
                    animateRightPaneOpened();

                    FragmentManager fm = getSupportFragmentManager();
                    //if you added fragment CNCFragment layout xml
                    CNCFragment fragment = (CNCFragment)fm.findFragmentById(R.id.right_content_frame);
                    fragment.requestFocusOnMessage();
                    fragment.startTutorialText();
                }

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, 0); // this disables the animation
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mDrawerToggle.syncState();


        //HGBUtility.loadHotelImage(getApplicationContext(), "http://a.abcnews.com/images/Technology/HT_ari_sprung_jef_140715_16x9_992.jpg", mProfileImage);
        //selectItem(ToolBarNavEnum.HOME.getNavNumber(), null);

    }


    private void animateRightPaneOpened() {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(RIGHT_PANE_ANIMATION_TIME);
        fadeOut.setFillAfter(true);
    }


    private void animateRightPaneClosed() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(RIGHT_PANE_ANIMATION_TIME);
        fadeIn.setFillAfter(true);
    }


    private void loadNavItems() {
        mNavItemsList = new ArrayList<>();
        mNavItemsList.add(new NavItem(ToolBarNavEnum.TRIPS, false, R.drawable.my_trips_icon_enable, R.drawable.my_trips_icon_disable));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.COMPANIONS, false, R.drawable.companions_icon_enable, R.drawable.companions_icon_disable));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.ACCOUNT, false, R.drawable.my_account_enable, R.drawable.my_account_disable));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.NOTIFICATIONS, false, R.drawable.notifications_enable, R.drawable.notifications_disable));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.CNC, false, R.drawable.my_account_enable, R.drawable.my_account_disable));

    }

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
        selectItem(position, null,true);
    }


    @Override
    public void continueFlow(int fragment) {

        if (fragment == ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber()) {
            selectItem(ToolBarNavEnum.ITINARERY.getNavNumber(), null,true);
        }
    }

    @Override
    public void gotToStartMenuActivity() {
        HGBUtility.removeAllFragments(getSupportFragmentManager());
      //  hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.CHOOSEN_SERVER,"");


        Intent intent = new Intent(getApplicationContext(), StartingMenuActivity.class);

       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }


    @Override
    public void setCreditCardsSelected(HashSet<CreditCardItem> cardsList) {
        mSelectedCreditCards = cardsList;
    }

    @Override
    public HashSet<CreditCardItem> getCreditCardsSelected() {
        return mSelectedCreditCards;
    }

    @Override
    public void setBookingHashMap(HashMap<String, String> bookigItems) {
        mBookingHashMap = bookigItems;
    }

    @Override
    public HashMap<String, String> getBookingHashMap() {
        return mBookingHashMap;
    }


    @Override
    public ArrayList<UserProfileVO> getListUsers() {
        return mTravelList;
    }

    @Override
    public UserProfileVO getCurrentUser() {
        return mCurrentUser;
    }

    @Override
    public void setCurrentUser(UserProfileVO currentUser) {
        this.mCurrentUser = currentUser;
    }


    @Override
    public ArrayList<CountryItemVO> getEligabileCountries() {
        return mEligabileCountryList;
    }

    @Override
    public void setEligabileCountries(ArrayList<CountryItemVO> list) {
        mEligabileCountryList = list;

    }

    @Override
    public ArrayList<CreditCardItem> getCreditCards() {
        return mCreditCardList;
    }

    @Override
    public void setCreditCards(ArrayList<CreditCardItem> mCreditCardList) {

        this.mCreditCardList = mCreditCardList;

    }

    @Override
    public void setListUsers(ArrayList<UserProfileVO> travellist) {
        mTravelList = travellist;
    }

    @Override
    public void closeRightPane() {
      /*  mDrawerLayout.closeDrawer(frameLayout);

        isRightPaneOpened = false;*/
    }



    @Override
    public void openRightPane() {

    /*    mDrawerLayout.openDrawer(frameLayout);
        isRightPaneOpened = true;*/

    }

    private Fragment isFreeUser(Fragment fragment, int navPosition) {

        if (isFreeUser)
        {
            fragment = FreeUserFragment.newInstance(navPosition);
          //  mToolbar.setVisibility(View.GONE);
        }
       /* else {
            mToolbar.setVisibility(View.VISIBLE);
        }*/
        mToolbar.setVisibility(View.VISIBLE);
        return fragment;
    }

    public void selectItem(int position, Bundle bundle,final boolean stashFragment) {
        // update the main content by replacing fragments

        Fragment fragment = null;
        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);
        boolean stashToBack = stashFragment;
        int navPosition = position;//navBar.getNavNumber();
        boolean isAddAnimation = false;
        switch (navBar) {
            case HOME:
                //  fragment = HomeFragment.newInstance(navPosition);
                //openRightPane();
              //  clearCNCItems();
                break;
            case CNC:
                fragment = CNCFragment.newInstance(navPosition);

                break;
//            case HISTORY:
//                fragment = HistoryFragment.newInstance(navPosition);
//                break;
            case TRIPS:
                fragment = TripsTabsView.newInstance(navPosition);
                break;
            case ALL_COMPANIONS_VIEW:
                fragment = CompanionsTravelers.newInstance(navPosition);
                break;
            case COMPANIONS:
                fragment = TravelCompanionTabsWidgetFragment.newInstance(navPosition);
                break;
            case COMPANIONS_DETAILS:
                fragment = CompanionDetailsFragment.newInstance(navPosition);
                break;
            case COMPANIONS_PERSONAL_DETAILS:
                fragment = AccountPersonalInfoSettingsFragment.newInstance(navPosition);
            //    stashToBack = false;
                //     fragment = isFreeUser(fragment, navPosition);
                break;
            case COMPANION_HELP_FEEDBACK:
                fragment = AccountPersonalInfoHelpAndFeedbackFragment.newInstance(navPosition);
                break;

            case PREFERENCE_SETTINGS_EMAILS:
                fragment = PreferenceSettingsEmailFragment.newInstance(navPosition);
                break;
            case TREVEL_PREFERENCE:
                fragment = PreferenceSettingsFragment.newInstance(navPosition);
                break;
            case ACCOUNT:
                fragment = AccountSettingsFragment.newInstance(navPosition);
                break;
            case NOTIFICATIONS:
                fragment = NotificationFragment.newInstance(navPosition);
                break;
            case PREFERENCES_MEMBERSHIP:
                fragment = MembershipFragment.newInstance(navPosition);
                break;
            case ITINARERY:
                fragment = ItineraryFragment.newInstance(navPosition);

               // setItineraryTitleName();
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
                fragment = NewPaymentDetailsFragment.newInstance(navPosition);
                //     fragment = isFreeUser( fragment , navPosition);
                break;
            case PREFERENCES_TAB_SETTINGS:
                fragment = PreferencesTabsFragmentSettings.newInstance(navPosition);
                break;
            case PREFERENCES_SEARCH_LIST_SETTINGS:
                fragment = PreferencesSearchListFragment.newInstance(navPosition);
                break;
            case PREFERENCES_CHECK_LIST_SETTINGS:
                fragment = PreferencesCheckListFragment.newInstance(navPosition);
                break;
            case PREFERENCES_CHECK_AS_RADIO_SETTINGS:
                fragment = PreferenceSettingsRadioCheckFragment.newInstance(navPosition);
                break;
            case PREFERENCES_DRAG_LIST_SETTINGS:
                fragment = PreferencesDragListFragment.newInstance(navPosition);
                break;
            case CHECKOUT_CONFIRMATION:
                fragment = CheckoutConfirmationFragment.newInstance(navPosition);
                break;
            case PAYMENT_TRAVELERS:
                fragment = TravelersFragment.newInstance(navPosition);

                break;
            case PAYMENT_TRAVELERS_DETAILS:
                fragment = TravelerDetailsFragment.newInstance(navPosition);
                break;
            case SELECT_CREDIT_CARD:
                fragment = SummaryPaymentFragment.newInstance(navPosition);
                break;
            case ADD_CREDIT_CARD:
                fragment = AddCreditCardFragment.newInstance(navPosition);
                break;
            case CREDIT_CARD_LIST:
                fragment = CreditCardListFragment.newInstance(navPosition);
                break;
            case HAZARDOUS_NOTICE:
                fragment = HazardousNoticeFragment.newInstance(navPosition);
                break;

        }


        if (bundle != null) {
            Bundle arguments = fragment.getArguments();
            arguments.putAll(bundle);
            fragment.setArguments(arguments);
        }

        if (isFreeUser && (navBar.equals(ToolBarNavEnum.COMPANIONS_PERSONAL_DETAILS) || navBar.equals(ToolBarNavEnum.PAYMENT_DETAILS) || navBar.equals(ToolBarNavEnum.COMPANIONS))) {
            isAddAnimation = true;
            fragment = isFreeUser(fragment, navPosition);
        }


        HGBUtility.hideKeyboard(MainActivity.this);
        HGBUtility.goToNextFragmentIsAddToBackStack(getSupportFragmentManager(), fragment, stashToBack, isAddAnimation);
        mToolbar.initToolBarItems();
        mToolbar.updateToolBarView(position);
        mDrawerLayout.closeDrawer(mNavDrawerLinearLayout);
    }


    private void setItineraryTitleName() {
        if(hgbSaveDataClass.getTravelOrder() != null) {
            itirnarary_title_Bar.setText(hgbSaveDataClass.getTravelOrder().getmSolutionName());
        }
    }
    private void setOnClickListenerForSavePreferences() {
        preference_save_changes = (FontTextView)mToolbar.findViewById(R.id.preference_save_changes);
        preference_save_changes.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(onSavePreferencesButtonClicked != null) {
                    onSavePreferencesButtonClicked.onSaveClicked();
                }

            }
        });
    }

    private void setOnClickListenerForItineraryTopBar() {
        up_bar_favorite = (ImageButton) mToolbar.findViewById(R.id.up_bar_favorite);
        up_bar_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorityItinerary();
                // goToFragment(ToolBarNavEnum.PAYMENT_DETAILS.getNavNumber(), null);
            }
        });

        itirnarary_title_Bar = (FontTextView) findViewById(R.id.itirnarary_title_Bar);


        LayoutInflater li = LayoutInflater.from(MainActivity.this);
        final View promptsView = li.inflate(R.layout.popup_layout_change_iteinarary_name, null);
        final EditText input = (EditText) promptsView
                .findViewById(R.id.change_iteinarary_name);


        itirnarary_title_Bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                input.setText(itirnarary_title_Bar.getText());
                HGBUtility.showAlertPopUp(MainActivity.this, input, promptsView, getResources().getString(R.string.edit_trip_name)
                        ,getResources().getString(R.string.save_button),
                        new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                itirnarary_title_Bar.setText(inputItem);
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        });
            }
        });
    }

    private void LogOutPopup(){
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        final  View popupView = li.inflate(R.layout.popup_alert_layout, null);
        HGBUtility.showAlertPopUp(MainActivity.this,  null, popupView,
                getResources().getString(R.string.main_exit_application),getResources().getString(R.string.ok_button), new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                    //    HGBUtility.removeAllFragments(getSupportFragmentManager());
                        gotToStartMenuActivity();
                    }

                    @Override
                    public void itemCanceled() {

                    }
                });
    }

    @Override
    public void onBackPressed() {
        mToolbar.setVisibility(View.VISIBLE);
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        }

  /*      if(isRightPaneOpened){
            closeRightPane();
            return;
        }*/

        //TODO this is when I want the fragment to contorl the back -Kate I suggest we do this for all Fragments

        int count = HGBUtility.getFragmentStack().size(); //  getFragmentManager().getBackStackEntryCount();

        //TODO getBackStackEntryAt sometimes equels 0

        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(count - 1);
        String str = backEntry.getName();
        if (str.equals(HotelFragment.class.toString()) && !HotelFragment.IS_MAIN_BACK_ALLOWED) {
            return;
        }

        if(count == 1 && str.equals(TripsTabsView.class.toString())){
            LogOutPopup();

            return;
        }

        if (HGBUtility.clearBackStackAndGoToNextFragment(getSupportFragmentManager())) {
            Fragment fragmentTemp = HGBUtility.getFragmentStack().lastElement();
            Bundle arguments = fragmentTemp.getArguments();
            int fragNumber = arguments.getInt(HGBConstants.ARG_NAV_NUMBER);
            mToolbar.updateToolBarView(fragNumber);
            return;
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

            Intent detailsIntent =  new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
            sendOrderedBroadcast(
                    detailsIntent, null, new LanguageDetailsChecker(), null, Activity.RESULT_OK, null, null);

//            boolean recognizerIntent =
//                    SpeechRecognitionUtil.isSpeechAvailable(this);
//            if (recognizerIntent) {
//                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
//                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
//                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
//                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Now...");
//                startActivityForResult(intent, 0);
//            } else {
//                Crashlytics.logException(new Exception("Speech not avaibale"));
//                Log.e("MainActvity", "Speeach not avaiable");
//            }

        } catch (Exception e) {
            Log.v("Speech", "Could not find any Speech Recognition Actions");
        }
    }

public void setTitleForItirnarary(String solutionName){

        itirnarary_title_Bar.setText(hgbSaveDataClass.getTravelOrder().getmSolutionName());

}

    public void setFavority(boolean isFavority){
        if (isFavority) {
            up_bar_favorite.setBackgroundResource(R.drawable.star_in_favorite);
            //   hgbSaveDataClass.getTravelOrder().setmIsFavorite(false);

        } else {
            //    hgbSaveDataClass.getTravelOrder().setmIsFavorite(true);
            up_bar_favorite.setBackgroundResource(R.drawable.thin_0651_star_favorite_rating);
        }
    }

    private void setFavorityItinerary() {
        UserTravelMainVO travelOrder = hgbSaveDataClass.getTravelOrder();
        final String solutionID = travelOrder.getmSolutionID();
        boolean isFavorite = travelOrder.ismIsFavorite();

        ConnectionManager.getInstance(MainActivity.this).putFavorityItenarary(!isFavorite, solutionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (hgbSaveDataClass.getTravelOrder().ismIsFavorite()) {
                 //   hgbSaveDataClass.getTravelOrder().setmIsFavorite(false);
                    up_bar_favorite.setBackgroundResource(R.drawable.thin_0651_star_favorite_rating);
                } else {
                //    hgbSaveDataClass.getTravelOrder().setmIsFavorite(true);
                    up_bar_favorite.setBackgroundResource(R.drawable.star_in_favorite);
                }

                getCurrentItinerary(solutionID);
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }

    @Override
    public void callRefreshItinerary(final int fragment) {

        ConnectionManager.getInstance(MainActivity.this).getItinerary(hgbSaveDataClass.getSolutionID(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                hgbSaveDataClass.setTravelOrder((UserTravelMainVO) data);
                continueFlow(fragment);
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), "Problem updating grid ");
            }
        });

    }

    @Override
    public CostumeToolBar getToolBar() {
        return mToolbar;
    }

    @Override
    public void goToFragment(int fragmentname, Bundle bundle, boolean stashFragment) {
        selectItem(fragmentname, bundle, stashFragment);
    }

    @Override
    public void goToFragment(int fragment, Bundle bundle) {
        selectItem(fragment, bundle,true);
    }

   /* @Override
    public void goToFragment(int fragmentname, Bundle bundle, boolean) {
        selectItem(fragmentname, bundle);
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == 0 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            //   String fragmentTag = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();
            //   Fragment currentFragment = getFragmentManager().findFragmentByTag(CNCFragment.class.toString());


            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(CNCFragment.class.toString());

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
    /*    if (id.equals("keyboard")) {
            imageButton.setBackgroundResource(R.drawable.app_bar_microphone_icn);
            imageButton.setTag("mic");
        } else if (id.equals("mic")) {
            imageButton.setBackgroundResource(R.drawable.group_7);
            imageButton.setTag("keyboard");
        }*/
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
      //  hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        try {
            Gson gsonback = new Gson();
            String json = gsonback.toJson(hgbSaveDataClass.getCNCItems());
            // When user exit the app, next time hi will see his itirnarary

            hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST, json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setEditClickCB(PreferenceSettingsFragment.OnItemClickListener editClickCB) {
        this.editClickCB = editClickCB;
    }

    public void setOnSavePreferencesButtonClicked(PreferencesSettingsMainClass.saveButtonClicked onSavePreferencesButtonClicked) {
        this.onSavePreferencesButtonClicked = onSavePreferencesButtonClicked;
    }

    public class LanguageDetailsChecker extends BroadcastReceiver
    {
        private List<String> supportedLanguages;

        private String languagePreference;

        @Override
        public void onReceive(Context context, Intent i)
        {
            Bundle results = getResultExtras(true);
            if (results.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE))
            {
                languagePreference =
                        results.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE);
            }
            if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES))
            {
                supportedLanguages =
                        results.getStringArrayList(
                                RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES);
            }
            try {

                boolean recognizerIntent =
                        SpeechRecognitionUtil.isSpeechAvailable(MainActivity.this);
                if (recognizerIntent) {
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePreference);
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
    }


//    public void setEditMyTripsClickCB(MyTripsFragment.OnItemClickListener editMyTripsClickCB) {
//        this.editMyTripsClickCB = editMyTripsClickCB;
//    }
}