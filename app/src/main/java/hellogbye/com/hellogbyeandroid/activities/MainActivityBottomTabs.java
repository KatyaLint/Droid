package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.alternative.FareClassFragment;

import hellogbye.com.hellogbyeandroid.fragments.checkout.AirlinePointsProgramVO;
import hellogbye.com.hellogbyeandroid.fragments.checkout.BookingPayVO;

import hellogbye.com.hellogbyeandroid.fragments.checkout.LoyaltyProgramsAdd;
import hellogbye.com.hellogbyeandroid.fragments.checkout.LoyaltyProgramsPopup;
import hellogbye.com.hellogbyeandroid.fragments.checkout.SummaryPaymentExpendableFragment;
import hellogbye.com.hellogbyeandroid.fragments.cnc.CNCSignalRFragment;
import hellogbye.com.hellogbyeandroid.fragments.cnc.CNCTutorials;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionAddNewCompanion;
import hellogbye.com.hellogbyeandroid.fragments.hotel.HotelFragment;
import hellogbye.com.hellogbyeandroid.fragments.hotel.SelectNewHotelFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightTabsWidgetFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightsDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.FactoryAlternativeFlight;
import hellogbye.com.hellogbyeandroid.fragments.checkout.AddCreditCardFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.CheckoutConfirmationFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.CreditCardListFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.HazardousNoticeFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.NewPaymentDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.TravelerDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.TravelersFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionsTravelers;
import hellogbye.com.hellogbyeandroid.fragments.companions.TravelCompanionTabsWidgetFragment;
import hellogbye.com.hellogbyeandroid.fragments.hotel.SelectNewRoomFragment;
import hellogbye.com.hellogbyeandroid.fragments.itinerary.ItineraryFragmentComposeView;
import hellogbye.com.hellogbyeandroid.fragments.membership.MembershipFragment;
import hellogbye.com.hellogbyeandroid.fragments.mytrips.TripsTabsView;
import hellogbye.com.hellogbyeandroid.fragments.notification.NotificationFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsRadioCheckFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsSlideFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesCheckListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesDragListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesSearchListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesTabWidgetFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.preferencespopup.UserProfilePreferences;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountPersonalInfoHelpAndFeedbackFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountPersonalInfoSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.PreferenceSettingsEmailFragment;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.PersonalUserInformationVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.cnc.CNCTutorialsVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionStaticRelationshipTypesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.UpdateAvailabilityVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.models.vo.profiles.DefaultsProfilesVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.network.Parser;
import hellogbye.com.hellogbyeandroid.signalr.SignalRService;
import hellogbye.com.hellogbyeandroid.utilities.GPSTracker;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityNetwork;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


/**
 * Created by arisprung on 8/7/16.
 */
public class MainActivityBottomTabs extends BaseActivity implements HGBVoiceInterface, HGBFlowInterface {


    private HGBSaveDataClass hgbSaveDataClass = new HGBSaveDataClass();

    private CostumeToolBar mToolbar;
    private HGBPreferencesManager hgbPrefrenceManager;
    private UserProfileVO mCurrentUser;
    private OnBackPressedListener onBackPressedListener;
    private ArrayList<String> mCvvList;
    private ArrayList<UserProfileVO> mTravelList = new ArrayList<>();
    private ArrayList<CountryItemVO> mEligabileCountryList = new ArrayList<>();
    private ArrayList<CreditCardItem> mCreditCardList = new ArrayList<>();
    public boolean isFreeUser;
    private CharSequence mTitle;
    private FontTextView preference_save_changes;
    private ImageView preference_add_card;
    private HashSet<CreditCardItem> mSelectedCreditCards = new HashSet<>();
    private HashMap<String, String> mBookingHashMap = new HashMap<>();
    private BottomBar mBottomBar;

    private final int BOTTOM_BAR_FIRST_INDEX = 0;
    private final int BOTTOM_BAR_SECOND_INDEX = 1;
    private final int BOTTOM_BAR_THIRD_INDEX = 2;
    private final int BOTTOM_BAR_FOURTH_INDEX = 3;
    private final int BOTTOM_BAR_FIFTH_INDEX = 4;
    //  private LinearLayout tool_bar_profile_name;
    private LinearLayout edit_preferences_ll;
    private FontTextView edit_preferences_imagebtn;
    private ImageButton check_preferences;
    // private ImageButton up_bar_favorite;
    private ImageButton toolbar_new_iternerary;
    private ImageButton toolbar_add_companion;
    private FontTextView my_trip_profile;
    private FontTextView itirnarary_title_Bar;
    private ImageButton toolbar_new_iternerary_cnc;
    private ImageButton toolbar_new_iternerary_cnc_chat_message;
    private SearchView search_view_tool_bar;
    private ImageButton search_maginfy;
    private FontTextView titleBar;
    private ImageButton tool_bar_delete_preferences;


    private AutoCompleteTextView mAutoComplete;
    private ImageButton toolbar_profile_popup;
    private LinearLayout connection_toast_layout_connected;
    private LinearLayout connection_toast_layout_disconnected;
    private HorizontalScrollView table_scroll;
    private ImageButton toolbar_new_grid_add_companions;
    private UserProfilePreferences userProfilePreferences;
    private UpdateAvailabilityVO updateAvailabilityVO;
    private List<BookingPayVO> bookingPayAnswear;
    private List<AirlinePointsProgramVO> userAirlinePointsProgramVO;


    public HGBSaveDataClass getHGBSaveDataClass() {
        return hgbSaveDataClass;
    }


    private SignalRService mService;
    private boolean mBound = false;



    public SignalRService getSignalRService(){
        return mService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //after free user, remove all fragments and start all over again
        removeAllFragments();


        CNCTutorials cncTutorials = new CNCTutorials();
        CNCTutorialsVO cncTutorialsVO = cncTutorials.parseTutorials(MainActivityBottomTabs.this);
        hgbSaveDataClass.setCNCTutorialsVOs(cncTutorialsVO);

        Intent intent = new Intent();
        intent.setClass(getBaseContext(), SignalRService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        getUpComingTrips();

        // getRelationshipTypes();

        hgbSaveDataClass.setPersonalUserInformation(new PersonalUserInformationVO());
        setContentView(R.layout.main_activity_bottom_tab_layout);

        userProfilePreferences = new UserProfilePreferences();

        userProfilePreferences.getUserSettings(MainActivityBottomTabs.this, MainActivityBottomTabs.this,hgbSaveDataClass, new CNCSignalRFragment.IProfileUpdated() {

            @Override
            public void profileUpdated(String profilename) {

                getUserData();
            }
        }, false);

        getCompanionsFromServer();
        getCountries();

        //INIT ToolBar
        mToolbar = (CostumeToolBar) findViewById(R.id.toolbar_costume);
        initToolBar();

        mTitle = getTitle();

        initBottomBar(savedInstanceState);


        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        hgbSaveDataClass.setPreferenceManager(hgbPrefrenceManager); //= new HGBSaveDataClass(this, hgbPrefrenceManager);
        isFreeUser = hgbPrefrenceManager.getBooleanSharedPreferences(HGBConstants.HGB_FREE_USER, false);

        //check if we have travelitinery in db
        String strTravel = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_LAST_TRAVEL_VO, "");
        if (!"".equals(strTravel)) {
            UserTravelMainVO userTravelVO = (UserTravelMainVO) Parser.parseAirplaneData(strTravel);

            hgbSaveDataClass.setTravelOrder(userTravelVO);
        }


        String pref = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_LAST_EMAIL, "");

        //INIT Location

        boolean locationToken = hgbPrefrenceManager.getBooleanSharedPreferences(HGBConstants.HGB_LOCATION_TOKEN, true);


        boolean location = HGBUtilityNetwork.isGPSEnable(MainActivityBottomTabs.this);//getLocation(MainActivityBottomTabs.this, locationToken);
        GPSTracker gpsTracker = new GPSTracker(getBaseContext());
        if(location){
            Location locationCoord = gpsTracker.getLocation();
            // String[] locationArr = location.split("&");
            //   hgbSaveDataClass.getTravelOrder().setLocation(locationArr);
            //  hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_LOCATION_TOKEN, false);

        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);


        getStaticAllAirlinePointsProgram(null);


     /*   if (location != null && hgbSaveDataClass.getTravelOrder() != null) {
            String[] locationArr = location.split("&");
            hgbSaveDataClass.getTravelOrder().setLocation(locationArr);
            hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_LOCATION_TOKEN, false);
        }*/

    }


    public UserProfilePreferences getUserProfilePreferences(){
        return userProfilePreferences;
    }

    private void initBottomBar(Bundle savedInstanceState) {

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.useFixedMode();
        mBottomBar.setItems(R.menu.menu_bottombar);
        mBottomBar.setTextAppearance(R.style.bottom_bar_text);
        mBottomBar.setActiveTabColor(ContextCompat.getColor(getApplicationContext(), R.color.COLOR_00516f));

        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            int i = 0;
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                if (this.i != 0) {
                    selectBottemTab(menuItemId);
                }
                this.i++;
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {
                if (this.i != 1) {
                    selectBottemTab(menuItemId);
                }
                this.i++;
            }
        });

    }



    private void querySearchChanges(String query){
        Intent intent = new Intent("search_query");
        intent.putExtra("query_type", "submit");
        intent.putExtra("query", query);
        sendBroadcast(intent);
    }



    private void selectBottemTab(int menuItemId) {

        switch (menuItemId) {
            case R.id.bb_menu_my_trips:
                //mNavController.switchTab(INDEX_MY_TRIPS);

                selectItem(ToolBarNavEnum.TRIPS.getNavNumber(), null, true);
                break;
            case R.id.bb_menu_companions:
                selectItem(ToolBarNavEnum.COMPANIONS.getNavNumber(), null, true);
                break;
            case R.id.bb_menu_cnc:
                selectItem(ToolBarNavEnum.CNC.getNavNumber(), null, true);
                break;
            case R.id.bb_menu_notiifcations:
                selectItem(ToolBarNavEnum.NOTIFICATIONS.getNavNumber(), null, true);
                break;
            case R.id.bb_menu_my_account:
                selectItem(ToolBarNavEnum.ACCOUNT.getNavNumber(), null, true);
                break;
        }
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

    /*    if (count == 1 && str.equals(CNCFragment.class.toString())) {
            LogOutPopup();
            return;
        }
*/


        if (count == 1 && str.equals(CNCSignalRFragment.class.toString())) {
            LogOutPopup();
            return;
        }


        if (HGBUtility.clearBackStackAndGoToNextFragment(getSupportFragmentManager())) {
            Fragment fragmentTemp = HGBUtility.getFragmentStack().lastElement();
            Bundle arguments = fragmentTemp.getArguments();
            int fragNumber = arguments.getInt(HGBConstants.ARG_NAV_NUMBER);
            mToolbar.updateToolBarView(fragNumber);
        }

    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }


        if(isLogoutExit){
            hgbPrefrenceManager.deleteSharedPrefrence(HGBConstants.HGB_CNC_LIST);
            hgbPrefrenceManager.deleteSharedPrefrence(HGBConstants.HGB_LAST_TRAVEL_VO);
            return;
        }

        try {
            Gson gsonback = new Gson();
            String json = gsonback.toJson(hgbSaveDataClass.getCNCItems());
            // When user exit the app, next time hi will see his itirnarary

            hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_CNC_LIST, json);

        } catch (Exception e) {
            e.printStackTrace();
        }
        unregisterReceiver(receiver);
        unregisterReceiver(broadcastReceiver);
    }


//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mNavController.onSaveInstanceState(outState);
//    }



    public void LogOutPopup(){
        LayoutInflater li = LayoutInflater.from(MainActivityBottomTabs.this);
        final View promptsView = li.inflate(R.layout.popup_logout_layout, null);



/*        FontButtonView btn_account_logout_button = (FontButtonView)this.findViewById(R.id.account_logout_button);
        btn_account_logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {*/

        HGBUtility.showAlertPopUp(MainActivityBottomTabs.this, null, promptsView,
                null,getResources().getString(R.string.logout_button),
                new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        gotToStartMenuActivity();
                    }

                    @Override
                    public void itemCanceled() {

                    }
                });
        // }

        //  });
    }

 /*   private void LogOutPopup() {
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        final View popupView = li.inflate(R.layout.popup_alert_layout, null);
        HGBUtility.showAlertPopUp(MainActivityBottomTabs.this, null, popupView,
                getResources().getString(R.string.main_exit_application), getResources().getString(R.string.ok_button), new PopUpAlertStringCB() {
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
*/

    private void initToolBar() {


        setSupportActionBar(mToolbar);

        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        mAutoComplete = (AutoCompleteTextView) mToolbar.findViewById(R.id.autocomplete);
        toolbar_add_companion = (ImageButton) mToolbar.findViewById(R.id.toolbar_add_companion);

        toolbar_add_companion = (ImageButton)mToolbar.findViewById(R.id.toolbar_add_companion);
   /*     tool_bar_profile_name = (LinearLayout) mToolbar.findViewById(R.id.tool_bar_profile_name);
        tool_bar_profile_name.setVisibility(View.GONE);*/
        edit_preferences_ll = (LinearLayout) mToolbar.findViewById(R.id.preferences_edit_mode);
        // edit_preferences_imagebtn = (ImageButton) mToolbar.findViewById(R.id.edit_preferences);
        edit_preferences_imagebtn = (FontTextView) mToolbar.findViewById(R.id.edit_preferences);
        check_preferences = (ImageButton) mToolbar.findViewById(R.id.check_preferences);
        itirnarary_title_Bar = (FontTextView)mToolbar.findViewById(R.id.itirnarary_title_Bar);
        //   up_bar_favorite = (ImageButton)mToolbar.findViewById(R.id.up_bar_favorite);
        preference_save_changes = (FontTextView) mToolbar.findViewById(R.id.preference_save_changes);
        preference_add_card = (ImageView) mToolbar.findViewById(R.id.add_cc);
        toolbar_new_iternerary = (ImageButton) mToolbar.findViewById(R.id.toolbar_new_iternerary);
        toolbar_new_iternerary_cnc = (ImageButton) mToolbar.findViewById(R.id.toolbar_new_iternerary_cnc);
        toolbar_new_grid_add_companions = (ImageButton) mToolbar.findViewById(R.id.toolbar_new_grid_add_companions);

        toolbar_new_iternerary_cnc_chat_message = (ImageButton) mToolbar.findViewById(R.id.toolbar_new_iternerary_cnc_chat_message);
        search_view_tool_bar = (SearchView)mToolbar.findViewById(R.id.search_view_tool_bar);
        search_maginfy = (ImageButton)mToolbar.findViewById(R.id.search_maginfy);
        titleBar = (FontTextView)mToolbar.findViewById(R.id.titleBar);
        my_trip_profile = (FontTextView) findViewById(R.id.my_trip_profile);
        tool_bar_delete_preferences = (ImageButton)findViewById(R.id.tool_bar_delete_preferences);
        toolbar_profile_popup = (ImageButton)findViewById(R.id.toolbar_profile_popup);

        connection_toast_layout_connected = (LinearLayout)findViewById(R.id.connection_toast_layout_connected);
        connection_toast_layout_disconnected = (LinearLayout)findViewById(R.id.connection_toast_layout_disconnected);


        //  toolbar_trip_name = (FontTextView)findViewById(R.id.toolbar_trip_name);

        table_scroll = (HorizontalScrollView)findViewById(R.id.table_scroll);
        // table_scroll = ((MainActivityBottomTabs)getBaseContext()).getItitneraryHS();

    }

/*    public FontTextView getToolBarTripName(){
        return toolbar_trip_name;
    }*/


    public HorizontalScrollView getItitneraryHS(){
        return table_scroll;
    }
    public ImageButton getToolbarProfilePopup(){
        return toolbar_profile_popup;
    }
    public ImageButton getToolBarDeletePreferences(){
        return tool_bar_delete_preferences;
    }
    public FontTextView getTitleBar(){
        return titleBar;
    }
    public ImageButton getSearchMagifyImage(){
        return search_maginfy;
    }

    public ImageButton getToolbar_new_iterneraryCnc(){
        return toolbar_new_iternerary_cnc;
    }


    public ImageButton getToolbar_toolbar_new_grid_add_companions(){
        return toolbar_new_grid_add_companions;
    }

    public ImageButton getToolbar_new_iterneraryCnc_Chat_Message(){
        return toolbar_new_iternerary_cnc_chat_message;
    }

    public void goToCncScreeButton(){
        toolbar_new_iternerary_cnc_chat_message.setVisibility(View.VISIBLE);
        toolbar_new_iternerary_cnc_chat_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(ToolBarNavEnum.CNC.getNavNumber(),null);
            }
        });
    }





    public SearchView getSearchView(){
        return search_view_tool_bar;
    }

    public ImageButton getNewIternararyButton(){
        return toolbar_new_iternerary;
    }
    public FontTextView getPreferencesSaveButton(){
        return preference_save_changes;
    }

    public ImageView getPreference_add_card() {
        return preference_add_card;
    }

    public ImageButton getAddCompanionButton() {
        return toolbar_add_companion;
    }
    public LinearLayout getToolBarEditPreferences(){
        return edit_preferences_ll;
    }

    public FontTextView getMyTripProfile(){
        return my_trip_profile;
    }

    /*public ImageButton getFavorityImageButton(){
        return up_bar_favorite;}*/

    public FontTextView getItirnaryTitleBar(){
        return itirnarary_title_Bar;
    }

 /*   public LinearLayout getToolBarProfileChange(){
        return tool_bar_profile_name;
    }*/

  /*  public ImageButton getEditPreferenceBtn(){
        return edit_preferences_imagebtn;
    }*/

    public FontTextView getEditPreferenceBtn(){
        return edit_preferences_imagebtn;
    }
    public ImageButton getCheckPreferenceButton(){
        return check_preferences;
    }

    private void getUserData() {

        ConnectionManager.getInstance(MainActivityBottomTabs.this).getUserProfile(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserProfileVO mCurrentUser = (UserProfileVO) data;

                String logInEmail = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_LAST_EMAIL, "");
                hgbSaveDataClass.getPersonalUserInformation().setUserEmailLogIn(logInEmail);

                hgbSaveDataClass.setCurrentUser(mCurrentUser);


                //showUserProfiles();
                if (!mCurrentUser.getIsTravelprofile()) {

                    userProfilePreferences.getUserDefaultSettings(MainActivityBottomTabs.this, MainActivityBottomTabs.this, hgbSaveDataClass);
                    // showUserProfiles();
                }

                String profileID = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_PROFILE_ID, "");
             /*   List<DefaultsProfilesVO> accountDefaultSettings = hgbSaveDataClass.getDefaultsProfilesVOs();
                for(DefaultsProfilesVO defaultsProfilesVO : accountDefaultSettings){
                    if(defaultsProfilesVO.getId().equals(profileID)){
                        hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileName(defaultsProfilesVO.getProfilename());
                    }
                }*/

                hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileId(profileID);

                //my_trips_image_profile.setImageBitmap(HGBUtility.getBitmapFromCache(getBaseContext()));
                getAccountsProfiles();

                //  selectItem(ToolBarNavEnum.TRIPS.getNavNumber(), null,true);

                //selectBottemTab(R.id.bb_menu_cnc);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void getAccountsProfiles() {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getUserProfileAccounts(new ConnectionManager.ServerRequestListener() {
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
                ErrorMessage(data);
            }
        });
    }

    private void editProfileTypeMainToolBar() {
        //TODO remove to profile fragment
        ArrayList<AccountsVO> accounts = hgbSaveDataClass.getAccounts();

        for (AccountsVO account : accounts) {
            String userEmailLogIn = hgbSaveDataClass.getPersonalUserInformation().getUserEmailLogIn();
            if (account.getEmail().equals(userEmailLogIn) && account.getTravelpreferenceprofile() != null) {
                my_trip_profile.setText(account.getTravelpreferenceprofile().getmProfileName());
                my_trip_profile.setTag(account.getTravelpreferenceprofile().getmId());

                hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileName(account.getTravelpreferenceprofile().getmProfileName());

                hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileId(account.getTravelpreferenceprofile().getmId());

                break;
            }
        }
        selectBottemTab(R.id.bb_menu_cnc);
    }


    private void showUserProfiles() {

        //     https://apiuat.hellogbye.com/uat/rest/TravelPreference/Profiles/Defaults

        userProfilePreferences.getUserDefaultSettings(MainActivityBottomTabs.this, MainActivityBottomTabs.this, hgbSaveDataClass);

   /*     ConnectionManager.getInstance(MainActivityBottomTabs.this).getDefaultProfiles(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                ArrayList<DefaultsProfilesVO> userProfileVO = (ArrayList<DefaultsProfilesVO>) data;
           //     if (!userProfileVO.isEmpty()) {
                    setUserDefaultProfiles(userProfileVO);



                    //showAlertProfilesDialog(userProfileVO);
          //      }


            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });*/

    }


    private void getCompanionsFromServer() {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getCompanions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<CompanionVO> companions = (ArrayList<CompanionVO>) data;
                hgbSaveDataClass.setCompanions(companions);
                getCompanionsInvitation();
            }


            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void getCompanionsInvitation() {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getCompanionInvitation(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<CompanionVO> companionsInvitation = (ArrayList<CompanionVO>) data;
                hgbSaveDataClass.addInvitationCompanionsToCompanions(companionsInvitation);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void getCountries() {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                BookingRequestVO bookingrequest = (BookingRequestVO) data;
                hgbSaveDataClass.setBookingRequest(bookingrequest);
                System.out.println("Kate bookingrequest =" + bookingrequest.getCountries());
                hgbSaveDataClass.getBookingRequest().sortCountryItems();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }



    public void selectItem(int position, Bundle bundle, final boolean stashFragment) {
        // update the main content by replacing fragments

        Fragment fragment = null;

        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);
        boolean stashToBack = stashFragment;
        int navPosition = position;//navBar.getNavNumber();
        boolean isAddAnimation = false;

        switch (navBar) {
            case LOYLTY_NEW_PROGRAMME:
                fragment = LoyaltyProgramsPopup.newInstance(navPosition);
                break;
            case LOYALTY_ADD_PROGRAMME:
                fragment = LoyaltyProgramsAdd.newInstance(navPosition);
                break;
            case COMPANION_ADD_NEW_COMPANION:
                fragment = CompanionAddNewCompanion.newInstance(navPosition);

                break;
            case CNC:
                // fragment = CNCFragment.newInstance(navPosition);
                fragment = CNCSignalRFragment.newInstance(navPosition);
                selectBottomBar(R.id.bb_menu_cnc);

                break;
            case TRIPS:
                fragment = TripsTabsView.newInstance(navPosition);
                selectBottomBar(R.id.bb_menu_my_trips);
                break;
            case ALL_COMPANIONS_VIEW:
                fragment = CompanionsTravelers.newInstance(navPosition);
                break;
            case COMPANIONS:
                fragment = TravelCompanionTabsWidgetFragment.newInstance(navPosition);
                selectBottomBar(R.id.bb_menu_companions);
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
            case TRAVEL_PREFERENCE:
                // fragment = PreferenceSettingsFragment.newInstance(navPosition);
                fragment = PreferenceSettingsSlideFragment.newInstance(navPosition);

                break;
            case ACCOUNT:
                fragment = AccountSettingsFragment.newInstance(navPosition);
                selectBottomBar(R.id.bb_menu_my_account);
                break;
            case NOTIFICATIONS:
                fragment = NotificationFragment.newInstance(navPosition);
                selectBottomBar(R.id.bb_menu_notiifcations);
                break;
            case PREFERENCES_MEMBERSHIP:
                fragment = MembershipFragment.newInstance(navPosition);
                break;
            case ITINERARY:
                //fragment = ItineraryFragment.newInstance(navPosition);
                fragment = ItineraryFragmentComposeView.newInstance(navPosition);
                stashToBack = false;
                //fragment = ItineraryFragmentAdapter.newInstance(navPosition);
                //   fragment = ItineraryFragmentHelperAdapter.newInstance(navPosition);
                // fragment = ItineraryFragmentNoScrollingAdapter.newInstance(navPosition);


                // setItineraryTitleName();
                break;


            case ALTERNATIVE_FLIGHT_ROUND_TRIP:
                fragment = AlternativeFlightTabsWidgetFragment.newInstance(navPosition);
                // stashToBack = false;
                break;
            case ALTERNATIVE_FLIGHT_ONE_WAY_TRIP:
                fragment = AlternativeFlightFragment.newInstance(navPosition);
                //  stashToBack = false;
                break;
            case FARE_CLASS_FRAGMENT:
                fragment = FareClassFragment.newInstance(navPosition);
                break;
            case HOTEL:
                fragment = HotelFragment.newInstance(navPosition);
                break;
            case ALTERNATIVE_FLIGHT_DETAILS:
                fragment = AlternativeFlightsDetailsFragment.newInstance(navPosition);
                //  stashToBack = false;
                break;
            case ALTERNATIVE_FLIGHT_FACTORY:
                fragment = FactoryAlternativeFlight.newInstance(navPosition);
                stashToBack = false;
                break;
            case PAYMENT_DETAILS:
                fragment = NewPaymentDetailsFragment.newInstance(navPosition);
                //     fragment = isFreeUser( fragment , navPosition);
                break;
            case PREFERENCES_TAB_SETTINGS:
                fragment = PreferencesTabWidgetFragment.newInstance(navPosition);
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

            case SELECT_CREDIT_CARD:
                //fragment = SummaryPaymentFragment.newInstance(navPosition);

                fragment = SummaryPaymentExpendableFragment.newInstance(navPosition);

                break;
            case ADD_CREDIT_CARD:
                fragment = AddCreditCardFragment.newInstance(navPosition);
                break;
            //TODO remove all not needed files
//            case CREDIT_CARDS_LIST:
//                fragment = ManagePaymentFragment.newInstance(navPosition);
//                  break;
            case CREDIT_CARD_LIST:
                fragment = CreditCardListFragment.newInstance(navPosition);
                break;
            case HAZARDOUS_NOTICE:
                fragment = HazardousNoticeFragment.newInstance(navPosition);
                break;
            case FREE_USER_FRAGMENT:
                // fragment = FreeUserFragment.newInstance(navPosition);
                //   stashToBack = false;
                freeUserPopUp();
                //isAddAnimation = true;
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
            case SELECT_ROOM_FRAGMENT:
                fragment = SelectNewRoomFragment.newInstance(navPosition);
                break;

            case SELECT_HOTEL_FRAGMENT:
                fragment = SelectNewHotelFragment.newInstance(navPosition);
                break;

        }


        if (bundle != null) {
            Bundle arguments = fragment.getArguments();
            arguments.putAll(bundle);
            fragment.setArguments(arguments);
        }

        mToolbar.initToolBarItems();

        if (isFreeUser && (navBar.equals(ToolBarNavEnum.COMPANIONS_PERSONAL_DETAILS) ||
                navBar.equals(ToolBarNavEnum.PAYMENT_DETAILS) ||
                navBar.equals(ToolBarNavEnum.COMPANIONS) ||
                navBar.equals(ToolBarNavEnum.PAYMENT_TRAVELERS) ||
                navBar.equals(ToolBarNavEnum.ALL_COMPANIONS_VIEW) ||
                navBar.equals(ToolBarNavEnum.CREDIT_CARD_LIST) ||
                navBar.equals(ToolBarNavEnum.NOTIFICATIONS))) {

            isAddAnimation = true;
            fragment = isFreeUser(fragment, navPosition);
            // mToolbar.setVisibility(View.GONE);
        }else {


            mToolbar.updateToolBarView(position);
            HGBUtility.goToNextFragmentIsAddToBackStack(getSupportFragmentManager(), fragment, stashToBack, isAddAnimation);

 /*           mToolbar.initToolBarItems();
            mToolbar.updateToolBarView(position);*/
        }

        HGBUtility.hideKeyboard(MainActivityBottomTabs.this);


    }

    private void freeUserPopUp(){
        LayoutInflater li = LayoutInflater.from(MainActivityBottomTabs.this);
        final  View popupView = li.inflate(R.layout.popup_free_user_regestration, null);
      /*  FontTextView popup_flight_baggage_text = (FontTextView) popupView.findViewById(R.id.popup_flight_baggage_text);
        String currency = getActivityInterface().getCurrentUser().getCurrency();
        String text = String.format(getActivity().getResources().getString(R.string.popup_flight_baggage_info_text),currency );
        popup_flight_baggage_text.setText(text);*/



        LinearLayout free_user_sign_in = (LinearLayout)popupView.findViewById(R.id.free_user_sign_in);
        free_user_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityBottomTabs.this, CreateAccountActivity.class);
                intent.putExtra("free_user_sign_in",true);
                startActivity(intent);
            }
        });

        LinearLayout free_user_create_new = (LinearLayout)popupView.findViewById(R.id.free_user_create_new);
        free_user_create_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivityBottomTabs.this, CreateAccountActivity.class);
                intent.putExtra("free_user_create_user",true);
                startActivity(intent);

            }
        });



        HGBUtility.showAlertPopUp(MainActivityBottomTabs.this, null, popupView,
                null, null, new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {

                    }

                    @Override
                    public void itemCanceled() {

                    }
                });


    }



    @Override
    public void enableFullScreen(boolean fullscreen) {
        if (fullscreen) {
            mBottomBar.hide();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        } else {
            mBottomBar.show();
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }

    }

    @Override
    public void setCvvList(ArrayList<String> cvvList) {
        mCvvList = cvvList;
    }

    @Override
    public ArrayList<String> getCvvList() {
        return mCvvList;
    }


    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public boolean getIsFreeUser(){
        return isFreeUser;
    }

    private Fragment isFreeUser(Fragment fragment, int navPosition) {

        if (isFreeUser) {

            //  freeUserPopUp();



            //  Intent intent = new Intent(getBaseContext(), SignUpActivity.class);
            //  startActivity(intent);

            //  fragment = FreeUserFragment.newInstance(navPosition);
            //  mToolbar.setVisibility(View.GONE);
        }
       /* else {
            mToolbar.setVisibility(View.VISIBLE);
        }*/
        //  mToolbar.setVisibility(View.VISIBLE);
        return fragment;
    }


    /////////////////////////////////
    /////   HGBFlowInterface  //////
    /////////////////////////////////

    @Override
    public void continueFlow(int fragment) {

        if (fragment == ToolBarNavEnum.ALTERNATIVE_FLIGHT_ROUND_TRIP.getNavNumber()) {
            selectItem(ToolBarNavEnum.ITINERARY.getNavNumber(), null, true);
        }
    }

    private boolean isLogoutExit = false;




    @Override
    public void gotToStartMenuActivity() {
        //  hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        hgbPrefrenceManager.removeKey(HGBConstants.TOKEN);
        hgbPrefrenceManager.removeKey(HGBConstants.CHOOSEN_SERVER);
        isLogoutExit = true;
        HGBUtility.removeAllFragments(getSupportFragmentManager());
        Intent intent = new Intent(getBaseContext(), CreateAccountActivity.class);



        hgbPrefrenceManager.removeKey(HGBConstants.HGB_FREE_USER);
        hgbPrefrenceManager.removeKey(HGBConstants.HGB_USER_PROFILE_ID);
        hgbPrefrenceManager.removeKey(HGBConstants.HGB_USER_LAST_EMAIL);



        startActivity(intent);

        // getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.content_frame)).commit();

        // finish();

    }

    public void removeAllFragments(){
        HGBUtility.removeAllFragments(getSupportFragmentManager());
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
    public void setUpdateAvailability(UpdateAvailabilityVO updateAvailabilityVO) {
        this.updateAvailabilityVO = updateAvailabilityVO;
    }

    @Override
    public UpdateAvailabilityVO getUpdateAvailability() {
        return this.updateAvailabilityVO;
    }

    @Override
    public void setBookingPayAnswear(List<BookingPayVO> bookingPayAnswear) {
        this.bookingPayAnswear = bookingPayAnswear;
    }

    @Override
    public List<BookingPayVO> getBookingPayAnswear() {
        return bookingPayAnswear;
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

    @Override
    public void selectBottomBar(int selection) {
        switch (selection) {
            case R.id.bb_menu_my_trips:
                mBottomBar.selectTabAtPosition(BOTTOM_BAR_FIRST_INDEX, true, false);
                mBottomBar.setmCurrentTabPosition(BOTTOM_BAR_FIRST_INDEX);
                break;
            case R.id.bb_menu_companions:
                mBottomBar.selectTabAtPosition(BOTTOM_BAR_SECOND_INDEX, true, false);
                mBottomBar.setmCurrentTabPosition(BOTTOM_BAR_SECOND_INDEX);
                break;
            case R.id.bb_menu_cnc:
                mBottomBar.selectTabAtPosition(BOTTOM_BAR_THIRD_INDEX, true, false);
                mBottomBar.setmCurrentTabPosition(BOTTOM_BAR_THIRD_INDEX);

                break;
            case R.id.bb_menu_notiifcations:
                mBottomBar.selectTabAtPosition(BOTTOM_BAR_FOURTH_INDEX, true, false);
                mBottomBar.setmCurrentTabPosition(BOTTOM_BAR_FOURTH_INDEX);
                break;
            case R.id.bb_menu_my_account:
                mBottomBar.selectTabAtPosition(BOTTOM_BAR_FIFTH_INDEX, true, false);
                mBottomBar.setmCurrentTabPosition(BOTTOM_BAR_FIFTH_INDEX);
                break;
        }

    }



    @Override
    public void callRefreshItinerary(final int fragment) {

        ConnectionManager.getInstance(MainActivityBottomTabs.this).getItinerary(hgbSaveDataClass.getSolutionID(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                hgbSaveDataClass.setTravelOrder((UserTravelMainVO) data);
                continueFlow(fragment);

            }

            @Override
            public void onError(Object data) {
                ErrorMessage("Problem updating grid ");

            }
        });

    }

    private void getPostBookingItinerary(){

        UserTravelMainVO userOrder = hgbSaveDataClass.getTravelOrder();
        boolean isBookedVersion = userOrder.getmHasbookedversion();
        if(!isBookedVersion){
            return;
        }

        String solutionID = userOrder.getmSolutionID();
        HGBPreferencesManager mHGBPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        String signalrConnectionID = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_ID, "");
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getBookedItinerary(solutionID,signalrConnectionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;

                hgbSaveDataClass.setBookedTravelOrder(userTravelMainVO);
                goToFragment(ToolBarNavEnum.ITINERARY.getNavNumber(), null);
                //   handleHGBMessage(getString(R.string.itinerary_created));
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);

            }
        });

    }




    @Override
    public void callRefreshItineraryWithCallback(final int fragment,final RefreshComplete refreslistner, String solutionID) {
        //hgbSaveDataClass.getTravelOrder().getmSolutionID()
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getItinerary(solutionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                hgbSaveDataClass.setTravelOrder((UserTravelMainVO) data);
                continueFlow(fragment);
                refreslistner.onRefreshSuccess(data);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage("Problem updating grid ");
                refreslistner.onRefreshError(data);

            }
        });
    }

    @Override
    protected void onResume() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("logout");
        registerReceiver(receiver, filter);



        super.onResume();
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
        selectItem(fragment, bundle, true);
    }



    /////////////////////////////////
    /////   HGBVoiceInterface  //////
    /////////////////////////////////

    @Override
    public void openVoiceToTextControl() {
        /*try {

            Intent detailsIntent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
            sendOrderedBroadcast(
                    detailsIntent, null, new LanguageDetailsChecker(), null, Activity.RESULT_OK, null, null);

        } catch (Exception e) {
            Log.v("Speech", "Could not find any Speech Recognition Actions");
        }*/
    }

  /*  public class LanguageDetailsChecker extends BroadcastReceiver {
        private List<String> supportedLanguages;

        private String languagePreference;

        @Override
        public void onReceive(Context context, Intent i) {
            System.out.println("Kate LanguageDetailsChecker");
            Bundle results = getResultExtras(true);
            if (results.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)) {
                languagePreference =
                        results.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE);
            }
            if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)) {
                supportedLanguages =
                        results.getStringArrayList(
                                RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES);
            }
            try {

                boolean recognizerIntent =
                        SpeechRecognitionUtil.isSpeechAvailable(MainActivityBottomTabs.this);
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
    }*/

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        System.out.println("Kate onActivityResult");
        if (requestCode == 0 && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            //   String fragmentTag = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1).getName();
            //   Fragment currentFragment = getFragmentManager().findFragmentByTag(CNCFragment.class.toString());


        //    Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(CNCFragment.class.toString());


            Fragment currentFragment = getSupportFragmentManager().findFragmentByTag(CNCSignalRFragment.class.toString());



          //  ((CNCSignalRFragment) currentFragment).handleMyMessage(matches.get(0));




            //((CNCFragment) currentFragment).handleMyMessage(matches.get(0));
//            if (currentFragment instanceof CNCFragment) {
//                ((CNCFragment) currentFragment).handleMyMessage(matches.get(0));
//            }
//            HomeFragment fragment = (HomeFragment) getFragmentManager().findFragmentByTag(HomeFragment.class.toString());
//            if (fragment != null) {
//                fragment.handleClick(matches.get(0));
//            }
        }
    }
*/



    private void ErrorMessage(Object data){
        HGBErrorHelper errorHelper = new HGBErrorHelper();
        errorHelper.setMessageForError((String) data);
        errorHelper.show(getFragmentManager(), (String) data);
    }

    public AutoCompleteTextView getmAutoComplete() {
        return mAutoComplete;
    }


    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to SignalRService, cast the IBinder and get SignalRService instance
            SignalRService.LocalBinder binder = (SignalRService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };



    private void getUpComingTrips(){
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getMyTripsPaid(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<MyTripItem> mItemsList = (ArrayList<MyTripItem>) data;
                hgbSaveDataClass.setUpComingTrips(mItemsList);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });

    }


    private boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            boolean isConnected = isNetworkAvailable(context);
            if(isConnected){
                connection_toast_layout_connected.setVisibility(View.VISIBLE);
                connection_toast_layout_disconnected.setVisibility(View.GONE);
                //  cnc_fragment_profile_line.setVisibility(View.VISIBLE);

                //  connection_toast_rl.setVisibility(View.VISIBLE);

                Animation anim = AnimationUtils.loadAnimation(MainActivityBottomTabs.this, R.anim.fade_out);
                anim.setAnimationListener(new Animation.AnimationListener(){
                    @Override
                    public void onAnimationStart(Animation arg0) {
                    }
                    @Override
                    public void onAnimationRepeat(Animation arg0) {
                    }
                    @Override
                    public void onAnimationEnd(Animation arg0) {
                        connection_toast_layout_connected.setVisibility(View.GONE);
                    }
                });

                connection_toast_layout_connected.startAnimation(anim);


            }else{
                //  connection_toast_rl.setVisibility(View.VISIBLE);
                connection_toast_layout_disconnected.setVisibility(View.VISIBLE);
                connection_toast_layout_connected.setVisibility(View.GONE);
                //     cnc_fragment_profile_line.setVisibility(View.GONE);

            }


        }
    };


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            gotToStartMenuActivity();
        }
    };


    public void postToSignalR(String connectionId, String userId, String solutionId){
      /*  String connectionid = "";
        String userid = "";
        String solutionid = "";*/
        ConnectionManager.getInstance(MainActivityBottomTabs.this).sendPostSignalRRegistration(connectionId, userId, solutionId,
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

                    }

                    @Override
                    public void onError(Object data) {

                    }
                });
    }


    //-------------------------------------


    public void setNewRelationshipForCompanion(int relationshiptypeId, String paxID){
        //   String paxID  = companionVO.getmCompanionid();
        ConnectionManager.getInstance(MainActivityBottomTabs.this).putCompanionRelationship(paxID, relationshiptypeId,new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    public void getRelationshipTypes(){
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getStaticCompanionsRelationTypesVO( new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<CompanionStaticRelationshipTypesVO> companionStaticRelationshipTypesVOs = (ArrayList<CompanionStaticRelationshipTypesVO> )data;
                hgbSaveDataClass.setCompanionsStaticRelationshipTypes(companionStaticRelationshipTypesVOs);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    public void postCompanion(CompanionVO companionVO) {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).postSearchCompanionAdd(companionVO, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                CompanionVO companionVO = (CompanionVO) data;
                //   getCompanions();
                LayoutInflater li = LayoutInflater.from(MainActivityBottomTabs.this);
                View popupView = li.inflate(R.layout.popup_layout_log_out, null);
                HGBUtility.showAlertPopUpOneButton(MainActivityBottomTabs.this, null, popupView,
                        "Companion Request Sent", new PopUpAlertStringCB() {

                            @Override
                            public void itemSelected(String inputItem) {
                                getCompanions(null);
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        });

                //  setNewRelationshipForCompanion(companionVO);

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    public void getCompanions(final RefreshComplete refreslistner){

        ConnectionManager.getInstance(MainActivityBottomTabs.this).getCompanions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                ArrayList<CompanionVO> companions =(ArrayList<CompanionVO>)data;
                hgbSaveDataClass.setCompanions(companions);
                if(refreslistner != null) {
                    refreslistner.onRefreshSuccess(data);
                }


            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


//    public void getTravelerGetAllInfo(String itineraryId, final RefreshComplete refreslistner){
//
//        ConnectionManager.getInstance(MainActivityBottomTabs.this).getUserTravelerAll( itineraryId, new ConnectionManager.ServerRequestListener() {
//            @Override
//            public void onSuccess(Object data) {
//
//                List<UserProfileVO> airlinePointsProgramVO = (List<UserProfileVO>)data;
//                refreslistner.onRefreshSuccess(airlinePointsProgramVO);
//
//            }
//
//            @Override
//            public void onError(Object data) {
//                ErrorMessage(data);
//            }
//        });
//    }


    public void getAirlinePointsProgram(String itineraryId, String passengerId, final RefreshComplete refreslistner){
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getAirlinePointsProgram( hgbSaveDataClass.getTravelOrder().getmSolutionID(),  passengerId,new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                List<AirlinePointsProgramVO> airlinePointsProgramVO = (List<AirlinePointsProgramVO>)data;
                hgbSaveDataClass.setUserAirlinePointsProgram(airlinePointsProgramVO);
                refreslistner.onRefreshSuccess(airlinePointsProgramVO);

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    public void postLoyaltyProgram(AirlinePointsProgramVO selectedAirlineProgram, final RefreshComplete refreslistner){

        ConnectionManager.getInstance(MainActivityBottomTabs.this).postAirlinePointsProgram(selectedAirlineProgram.getId(),selectedAirlineProgram.getProgramnumber(),new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                refreslistner.onRefreshSuccess(data);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });

    }

    public void deleteLoyaltyProgram(AirlinePointsProgramVO selectedAirlineProgram, final RefreshComplete refreslistner){

        ConnectionManager.getInstance(MainActivityBottomTabs.this).deleteAirlinePointsProgram(selectedAirlineProgram.getId(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                refreslistner.onRefreshSuccess(data);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }
    

    public void getStaticAllAirlinePointsProgram(final RefreshComplete refreslistner){

        //getStaticAllAirlinePointsProgram

        ConnectionManager.getInstance(MainActivityBottomTabs.this).getStaticAllAirlinePointsProgram(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<AirlinePointsProgramVO> airlinePointsProgramVO = (ArrayList<AirlinePointsProgramVO>)data;
                hgbSaveDataClass.setStaticAirlinePointsProgram(airlinePointsProgramVO);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    public void getTravellersInfoWithSolutionId(final RefreshComplete refreslistner) {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getTravellersInforWithSolutionId(hgbSaveDataClass.getTravelOrder().getmSolutionID(),
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        ArrayList<UserProfileVO> usersProfiles = (ArrayList<UserProfileVO>) data;
                        for(UserProfileVO userProfileVO : usersProfiles){

                        }

                        setListUsers(usersProfiles);
                        refreslistner.onRefreshSuccess(data);
                       // goToFragment(ToolBarNavEnum.PAYMENT_TRAVELERS.getNavNumber(), null);
                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });
    }

    public void getTravellerInfoWithSolutionId(final RefreshComplete refreslistner) {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getTravellerInforWithSolutionId(hgbSaveDataClass.getTravelOrder().getmSolutionID(),
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

                       List<UserProfileVO> mCurrentUser = (List<UserProfileVO>) data;
                        hgbSaveDataClass.setCurrentUser(mCurrentUser.get(0));
                        goToFragment(ToolBarNavEnum.SELECT_CREDIT_CARD.getNavNumber(), null);
                    }

                    @Override
                    public void onError(Object data) {
                        ErrorMessage(data);
                    }
                });
    }

    public void updateAvailability(final RefreshComplete refreslistner){

        ArrayList<UserProfileVO> users = getListUsers();
        UserTravelMainVO order = hgbSaveDataClass.getTravelOrder();
        ConnectionManager.getInstance(MainActivityBottomTabs.this).updateAvailability(order.getmSolutionID(), users,
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        UpdateAvailabilityVO updateAvailabilityVO = (UpdateAvailabilityVO)data;
                        setUpdateAvailability(updateAvailabilityVO);
                        refreslistner.onRefreshSuccess(data);
                    }

                    @Override
                    public void onError(Object data) {
                        refreslistner.onRefreshError(data);
                        ErrorMessage(data);
                    }
                });

    }


    public void payForCheckout(final RefreshComplete refreslistne,  JSONObject jsonObject ) {

        ConnectionManager.getInstance(MainActivityBottomTabs.this).pay(jsonObject, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<BookingPayVO> bookingPayVOList = (List<BookingPayVO>)data;
                setBookingPayAnswear(bookingPayVOList);
                // Toast.makeText(getActivity().getApplicationContext(), "Trip Booked", Toast.LENGTH_SHORT).show();
                goToFragment(ToolBarNavEnum.CHECKOUT_CONFIRMATION.getNavNumber(), null);

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    public void getCreditCardsList(final RefreshComplete refreslistner){
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getCreditCards(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<CreditCardItem> creditCards = (ArrayList<CreditCardItem>) data;
                setCreditCards(creditCards);
                refreslistner.onRefreshSuccess(data);
                // goToFragment(ToolBarNavEnum.SELECT_CREDIT_CARD.getNavNumber(), null);

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }



    private AlertDialog mSeatTypeDialog;
    private String[] seatArray = {"No Preference","Window","Aisle"};
    public void buildSeatTypeDialog(final String chooseItem, final RefreshComplete refreslistner) {

        int choosenNum =0;
        for(int i=0;i<seatArray.length;i++){
            String seatName = seatArray[i];
            if(chooseItem.equalsIgnoreCase(seatName)){
                choosenNum = i;
                break;
            }
        }

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                MainActivityBottomTabs.this, R.layout.dialog_radio, seatArray);
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityBottomTabs.this);
        builder.setTitle("Preferred Seat Type");
        builder.setSingleChoiceItems(adapter, choosenNum, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
//                travel_seat_type.setText(seatArray[item]);

                refreslistner.onRefreshSuccess(seatArray[item]);

                mSeatTypeDialog.hide();
            }
        });
        mSeatTypeDialog = builder.create();
        mSeatTypeDialog.show();
    }

}
