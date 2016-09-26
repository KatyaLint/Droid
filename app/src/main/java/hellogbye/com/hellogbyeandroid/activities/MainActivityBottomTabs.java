package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.userprofilesadapter.UserProfilesAdapter;
import hellogbye.com.hellogbyeandroid.fragments.cnc.CNCFragment;
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
import hellogbye.com.hellogbyeandroid.fragments.checkout.SummaryPaymentFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.TravelerDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.TravelersFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionsTravelers;
import hellogbye.com.hellogbyeandroid.fragments.companions.TravelCompanionTabsWidgetFragment;
import hellogbye.com.hellogbyeandroid.fragments.freeuser.FreeUserFragment;
import hellogbye.com.hellogbyeandroid.fragments.hotel.SelectNewRoomFragment;
import hellogbye.com.hellogbyeandroid.fragments.itinerary.ItineraryFragment;
import hellogbye.com.hellogbyeandroid.fragments.membership.MembershipFragment;
import hellogbye.com.hellogbyeandroid.fragments.mytrips.TripsTabsView;
import hellogbye.com.hellogbyeandroid.fragments.notification.NotificationFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsRadioCheckFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesCheckListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesDragListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesSearchListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesTabWidgetFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountPersonalInfoHelpAndFeedbackFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountPersonalInfoSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.PreferenceSettingsEmailFragment;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.PersonalUserInformationVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.models.vo.profiles.DefaultsProfilesVO;
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

/**
 * Created by arisprung on 8/7/16.
 */
public class MainActivityBottomTabs extends BaseActivity implements HGBVoiceInterface, HGBFlowInterface {

    private CostumeToolBar mToolbar;
    private HGBPreferencesManager hgbPrefrenceManager;
    private UserProfileVO mCurrentUser;
    private OnBackPressedListener onBackPressedListener;

    private HGBSaveDataClass hgbSaveDataClass = new HGBSaveDataClass();
    private PreferenceSettingsFragment.OnItemClickListener editClickCB;


    private ArrayList<UserProfileVO> mTravelList = new ArrayList<>();
    private ArrayList<CountryItemVO> mEligabileCountryList = new ArrayList<>();
    private ArrayList<CreditCardItem> mCreditCardList = new ArrayList<>();
    public boolean isFreeUser;
    private CharSequence mTitle;
    private FontTextView preference_save_changes;
    private AlertDialog selectDefaultProfileDialog;
    private ArrayList<DefaultsProfilesVO> userDefaultProfiles;

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
    private ImageButton up_bar_favorite;
    private ImageButton toolbar_new_iternerary;
    private ImageButton toolbar_add_companion;
    private FontTextView my_trip_profile;
    private FontTextView itirnarary_title_Bar;
    private ImageButton toolbar_new_iternerary_cnc;
    private SearchView search_view_tool_bar;
    private ImageButton search_maginfy;
    private FontTextView titleBar;
    private ImageButton tool_bar_delete_preferences;


    private AutoCompleteTextView mAutoComplete;

    private NodesVO mSelectedHotelNode;
    //private FontTextView toolbar_trip_name;

    public HGBSaveDataClass getHGBSaveDataClass() {
        return hgbSaveDataClass;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getUserData();
        getCompanionsFromServer();
        getCountries();


        hgbSaveDataClass.setPersonalUserInformation(new PersonalUserInformationVO());
        setContentView(R.layout.main_activity_bottom_tab_layout);

        //INIT ToolBar
        mToolbar = (CostumeToolBar) findViewById(R.id.toolbar_costume);
        initToolBar();


        //INIT Bottom tabs


//        List<Fragment> fragments = new ArrayList<>(5);
//
//        fragments.add(TripsTabsView.newInstance(0));
//        fragments.add(TravelCompanionTabsWidgetFragment.newInstance(0));
//        fragments.add(CNCFragment.newInstance(0));
//        fragments.add(NotificationFragment.newInstance(0));
//        fragments.add( AccountSettingsFragment.newInstance(0));
//
//        mNavController = new FragNavController(getSupportFragmentManager(), R.id.container, fragments);
        mTitle = getTitle();

        initBottomBar(savedInstanceState);


        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        hgbSaveDataClass.setPreferenceManager(hgbPrefrenceManager); //= new HGBSaveDataClass(this, hgbPrefrenceManager);
        isFreeUser = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, false);

        //check if we have travelitinery in db
        String strTravel = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_LAST_TRAVEL_VO, "");
        if (!"".equals(strTravel)) {
            UserTravelMainVO userTravelVO = (UserTravelMainVO) Parser.parseAirplaneData(strTravel);
            hgbSaveDataClass.setTravelOrder(userTravelVO);
        }




        //INIT Location

        boolean locationToken = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.HGB_LOCATION_TOKEN, true);
        String location = HGBUtility.getLocation(MainActivityBottomTabs.this, locationToken);
        if (location != null && hgbSaveDataClass.getTravelOrder() != null) {
            String[] locationArr = location.split("&");
            hgbSaveDataClass.getTravelOrder().setLocation(locationArr);
            hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_LOCATION_TOKEN, false);
        }

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


/*    private void clearCNCItems() {
>>>>>>> master

        hgbSaveDataClass.setCNCItems(null);
        hgbSaveDataClass.setTravelOrder(null);
        hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_LAST_TRAVEL_VO);
        Bundle args = new Bundle();
        args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
        selectItem(ToolBarNavEnum.CNC.getNavNumber(), null, true);
    }*/

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

        if (count == 1 && str.equals(CNCFragment.class.toString())) {
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

        if(isLogoutExit){
            hgbPrefrenceManager.deleteSharedPrefrence(HGBPreferencesManager.HGB_CNC_LIST);
            hgbPrefrenceManager.deleteSharedPrefrence(HGBPreferencesManager.HGB_LAST_TRAVEL_VO);
            return;
        }

            try {
                Gson gsonback = new Gson();
                String json = gsonback.toJson(hgbSaveDataClass.getCNCItems());
                // When user exit the app, next time hi will see his itirnarary

                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST, json);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }


//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        mNavController.onSaveInstanceState(outState);
//    }


    private void LogOutPopup() {
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
        up_bar_favorite = (ImageButton)mToolbar.findViewById(R.id.up_bar_favorite);
        preference_save_changes = (FontTextView) mToolbar.findViewById(R.id.preference_save_changes);
        toolbar_new_iternerary = (ImageButton) mToolbar.findViewById(R.id.toolbar_new_iternerary);
        toolbar_new_iternerary_cnc = (ImageButton) mToolbar.findViewById(R.id.toolbar_new_iternerary_cnc);
        search_view_tool_bar = (SearchView)mToolbar.findViewById(R.id.search_view_tool_bar);
        search_maginfy = (ImageButton)mToolbar.findViewById(R.id.search_maginfy);
        titleBar = (FontTextView)mToolbar.findViewById(R.id.titleBar);
        my_trip_profile = (FontTextView) findViewById(R.id.my_trip_profile);
        tool_bar_delete_preferences = (ImageButton)findViewById(R.id.tool_bar_delete_preferences);
      //  toolbar_trip_name = (FontTextView)findViewById(R.id.toolbar_trip_name);

    }

/*    public FontTextView getToolBarTripName(){
        return toolbar_trip_name;
    }*/

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
    public SearchView getSearchView(){
        return search_view_tool_bar;
    }

    public ImageButton getNewIternararyButton(){
        return toolbar_new_iternerary;
    }
    public FontTextView getPreferencesSaveButton(){
        return preference_save_changes;
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

    public ImageButton getFavorityImageButton(){
        return up_bar_favorite;
    }
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
                String logInEmail = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, "");

                hgbSaveDataClass.getPersonalUserInformation().setUserEmailLogIn(logInEmail);
                hgbSaveDataClass.setCurrentUser(mCurrentUser);


                //showUserProfiles();
                if (!mCurrentUser.getIsTravelprofile()) {
                    showUserProfiles();
                }

                String profileID = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, "");
                hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileId(profileID);
                //my_trips_image_profile.setImageBitmap(HGBUtility.getBitmapFromCache(getBaseContext()));
                getAccountsProfiles();
                selectBottemTab(R.id.bb_menu_cnc);
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

    public void editProfileTypeMainToolBar() {
        //TODO remove to profile fragment
        ArrayList<AccountsVO> accounts = hgbSaveDataClass.getAccounts();


        for (AccountsVO account : accounts) {
            String userEmailLogIn = hgbSaveDataClass.getPersonalUserInformation().getUserEmailLogIn();
            if (account.getEmail().equals(userEmailLogIn)) {
                my_trip_profile.setText(account.getTravelpreferenceprofile().getmProfileName());
                my_trip_profile.setTag(account.getTravelpreferenceprofile().getmId());
                //   hgbSaveDataClass.getCurrentUser().setmTravelPreferencesProfileId(account.getTravelpreferenceprofile().getId());
                hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileId(account.getTravelpreferenceprofile().getmId());
                break;
            }
        }
    }

    private void showUserProfiles() {

        //     https://apiuat.hellogbye.com/uat/rest/TravelPreference/Profiles/Defaults


        ConnectionManager.getInstance(MainActivityBottomTabs.this).getDefaultProfiles(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                ArrayList<DefaultsProfilesVO> userProfileVO = (ArrayList<DefaultsProfilesVO>) data;
                if (!userProfileVO.isEmpty()) {
                    setUserDefaultProfiles(userProfileVO);
                    showAlertProfilesDialog(userProfileVO);
                }


            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });

    }


    private void showAlertProfilesDialog(ArrayList<DefaultsProfilesVO> userProfileVOs) {
        LayoutInflater li = LayoutInflater.from(MainActivityBottomTabs.this);
        View promptsView = li.inflate(R.layout.popup_custom_title, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivityBottomTabs.this);
        dialogBuilder.setCustomTitle(promptsView);
        // dialogBuilder.setTitle(getResources().getString(R.string.profile_choose_between));

        final ArrayList<String> itemsList = new ArrayList<String>();
        for (DefaultsProfilesVO userProfileVO : userProfileVOs) {
            itemsList.add(userProfileVO.getName());
        }
        // final CharSequence[] list = itemsList.toArray(new String[itemsList.size()]);
        UserProfilesAdapter adapter = new UserProfilesAdapter(itemsList, this.getBaseContext());


      //  UserProfilesListAdapter adapter = new UserProfilesListAdapter(itemsList, this.getBaseContext());

        dialogBuilder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                DefaultsProfilesVO defaultProfile = userDefaultProfiles.get(item);
                postDefaultProfile(String.valueOf(defaultProfile.getId()), defaultProfile.getName());
                selectDefaultProfileDialog.dismiss();
            }
        });
        //Create alert dialog object via builder
        selectDefaultProfileDialog = dialogBuilder.create();
        selectDefaultProfileDialog.setCancelable(false);
        selectDefaultProfileDialog.show();
    }


    private void postDefaultProfile(String profileId, String profileName) {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).postDefaultProfile(profileId, profileName, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    AccountDefaultSettingsVO accountDefault = (AccountDefaultSettingsVO) data;
                    putNewPreferencesForUser(hgbSaveDataClass.getPersonalUserInformation().getUserEmailLogIn(), accountDefault.getmId());
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });

    }


    private void putNewPreferencesForUser(final String userEmail, final String accountID) {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).putAccountsPreferences(userEmail, accountID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, accountID);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    public void setUserDefaultProfiles(ArrayList<DefaultsProfilesVO> userDefaultProfiles) {
        this.userDefaultProfiles = userDefaultProfiles;
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
            case CNC:
                fragment = CNCFragment.newInstance(navPosition);
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
                fragment = PreferenceSettingsFragment.newInstance(navPosition);
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
            case ITINARERY:
                fragment = ItineraryFragment.newInstance(navPosition);

                // setItineraryTitleName();
                break;
            case ALTERNATIVE_FLIGHT_ROUND_TRIP:
                fragment = AlternativeFlightTabsWidgetFragment.newInstance(navPosition);
                stashToBack = false;
                break;
            case ALTERNATIVE_FLIGHT_ONE_WAY_TRIP:
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
            case FREE_USER_FRAGMENT:
                fragment = FreeUserFragment.newInstance(navPosition);
                stashToBack = false;
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

        if (isFreeUser && (navBar.equals(ToolBarNavEnum.COMPANIONS_PERSONAL_DETAILS) ||
                navBar.equals(ToolBarNavEnum.PAYMENT_DETAILS) ||
                navBar.equals(ToolBarNavEnum.COMPANIONS) ||
                navBar.equals(ToolBarNavEnum.ALL_COMPANIONS_VIEW) ||
                navBar.equals(ToolBarNavEnum.CREDIT_CARD_LIST))) {

            isAddAnimation = true;
            fragment = isFreeUser(fragment, navPosition);
        }


        HGBUtility.hideKeyboard(MainActivityBottomTabs.this);
        HGBUtility.goToNextFragmentIsAddToBackStack(getSupportFragmentManager(), fragment, stashToBack, isAddAnimation);
        mToolbar.initToolBarItems();
        mToolbar.updateToolBarView(position);
    }

    @Override
    public void enableFullScreen(boolean fullscreen) {
        if (fullscreen) {
            mBottomBar.hide();
       //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

        } else {
            mBottomBar.show();
        //    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }

    }


    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }


    private Fragment isFreeUser(Fragment fragment, int navPosition) {

        if (isFreeUser) {
            fragment = FreeUserFragment.newInstance(navPosition);
            //  mToolbar.setVisibility(View.GONE);
        }
       /* else {
            mToolbar.setVisibility(View.VISIBLE);
        }*/
        mToolbar.setVisibility(View.VISIBLE);
        return fragment;
    }




    /////////////////////////////////
    /////   HGBFlowInterface  //////
    /////////////////////////////////

    @Override
    public void continueFlow(int fragment) {

        if (fragment == ToolBarNavEnum.ALTERNATIVE_FLIGHT_ROUND_TRIP.getNavNumber()) {
            selectItem(ToolBarNavEnum.ITINARERY.getNavNumber(), null, true);
        }
    }

    private boolean isLogoutExit = false;

    @Override
    public void gotToStartMenuActivity() {
        HGBUtility.removeAllFragments(getSupportFragmentManager());
        //  hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.CHOOSEN_SERVER, "");
        isLogoutExit = true;
/*        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_LAST_TRAVEL_VO,"");
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST,""); */

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


    @Override
    public void callRefreshItineraryWithCallback(final int fragment,final RefreshComplete refreslistner) {

        ConnectionManager.getInstance(MainActivityBottomTabs.this).getItinerary(hgbSaveDataClass.getSolutionID(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                hgbSaveDataClass.setTravelOrder((UserTravelMainVO) data);
                continueFlow(fragment);
                refreslistner.onRefreshSuccess();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage("Problem updating grid ");
                refreslistner.onRefreshError();

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
        selectItem(fragment, bundle, true);
    }



    /////////////////////////////////
    /////   HGBVoiceInterface  //////
    /////////////////////////////////


    @Override
    public void openVoiceToTextControl() {
        try {

            Intent detailsIntent = new Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS);
            sendOrderedBroadcast(
                    detailsIntent, null, new LanguageDetailsChecker(), null, Activity.RESULT_OK, null, null);

        } catch (Exception e) {
            Log.v("Speech", "Could not find any Speech Recognition Actions");
        }
    }

    public class LanguageDetailsChecker extends BroadcastReceiver {
        private List<String> supportedLanguages;

        private String languagePreference;

        @Override
        public void onReceive(Context context, Intent i) {
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
    }

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




    private void ErrorMessage(Object data){
        HGBErrorHelper errorHelper = new HGBErrorHelper();
        errorHelper.setMessageForError((String) data);
        errorHelper.show(getFragmentManager(), (String) data);
    }
    public AutoCompleteTextView getmAutoComplete() {
        return mAutoComplete;
    }
}
