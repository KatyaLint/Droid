package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
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
import android.widget.EditText;
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
import hellogbye.com.hellogbyeandroid.fragments.CNCFragment;
import hellogbye.com.hellogbyeandroid.fragments.HotelFragment;
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
    private ImageButton up_bar_favorite;
    private ImageButton newIteneraryImageButton;


    private UserProfileVO mCurrentUser;
    private OnBackPressedListener onBackPressedListener;
    public FontTextView my_trip_profile;
    private HGBSaveDataClass hgbSaveDataClass = new HGBSaveDataClass();
    private PreferenceSettingsFragment.OnItemClickListener editClickCB;
    private FontTextView itirnarary_title_Bar;

    private ArrayList<UserProfileVO> mTravelList = new ArrayList<>();
    private ArrayList<CountryItemVO> mEligabileCountryList = new ArrayList<>();
    private ArrayList<CreditCardItem> mCreditCardList = new ArrayList<>();
    public boolean isFreeUser;
    private CharSequence mTitle;
    private FontTextView preference_save_changes;
    private PreferencesSettingsMainClass.saveButtonClicked onSavePreferencesButtonClicked;
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

    public HGBSaveDataClass getHGBSaveDataClass() {
        return hgbSaveDataClass;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        hgbSaveDataClass.setPersonalUserInformation(new PersonalUserInformationVO());
        setContentView(R.layout.main_activity_bottom_tab_layout);

        //INIT ToolBar
        mToolbar = (CostumeToolBar) findViewById(R.id.toolbar_costume);
        initToolBar();
        initSearchBar();

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

        getUserData();
        getCompanionsFromServer();
        getCountries();


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
        mBottomBar.setActiveTabColor(ContextCompat.getColor(getApplicationContext(), R.color.electric_tix_blue_press));

        mBottomBar.setOnMenuTabClickListener(new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                selectBottemTab(menuItemId);
            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });

    }


    private void querySearchChanges(String query){
        Intent intent = new Intent("search_query");
        intent.putExtra("query_type", "submit");
        intent.putExtra("query", query);
        sendBroadcast(intent);
    }

    private void initSearchBar() {
        SearchView searchView = (SearchView) mToolbar.findViewById(R.id.search);
        // Sets searchable configuration defined in searchable.xml for this SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                querySearchChanges(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                querySearchChanges(newText);
                return false;
            }
        });
    }

    private void clearCNCItems() {

        hgbSaveDataClass.setCNCItems(null);
        hgbSaveDataClass.setTravelOrder(null);
        hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_LAST_TRAVEL_VO);
        Bundle args = new Bundle();
        args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
        selectItem(ToolBarNavEnum.CNC.getNavNumber(), null, true);
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

        if (count == 1 && str.equals(TripsTabsView.class.toString())) {
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



    public void setOnSavePreferencesButtonClicked(PreferencesSettingsMainClass.saveButtonClicked onSavePreferencesButtonClicked) {
        this.onSavePreferencesButtonClicked = onSavePreferencesButtonClicked;
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

        preferencesChanges();
        toolBarProfileChnage();
        setOnClickListenerForItineraryTopBar();
        setOnClickListenerForSavePreferences();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        newIteneraryImageButton = (ImageButton) mToolbar.findViewById(R.id.toolbar_new_iternerary);

        newIteneraryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCNCItems();
            }
        });


    }

    private void setOnClickListenerForSavePreferences() {
        preference_save_changes = (FontTextView) mToolbar.findViewById(R.id.preference_save_changes);
        preference_save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSavePreferencesButtonClicked != null) {
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


        LayoutInflater li = LayoutInflater.from(MainActivityBottomTabs.this);
        final View promptsView = li.inflate(R.layout.popup_layout_change_iteinarary_name, null);
        final EditText input = (EditText) promptsView
                .findViewById(R.id.change_iteinarary_name);


        itirnarary_title_Bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                input.setText(itirnarary_title_Bar.getText());
                HGBUtility.showAlertPopUp(MainActivityBottomTabs.this, input, promptsView, getResources().getString(R.string.edit_trip_name)
                        , getResources().getString(R.string.save_button),
                        new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                itirnarary_title_Bar.setText(inputItem);
                                sendNewSolutionName(inputItem);
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        });
            }
        });
    }

    private void setFavorityItinerary() {
        UserTravelMainVO travelOrder = hgbSaveDataClass.getTravelOrder();
        final String solutionID = travelOrder.getmSolutionID();
        boolean isFavorite = travelOrder.ismIsFavorite();

        ConnectionManager.getInstance(MainActivityBottomTabs.this).putFavorityItenarary(!isFavorite, solutionID, new ConnectionManager.ServerRequestListener() {
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
                reportError(data);
            }
        });
    }

    public void setFavority(boolean isFavority) {
        if (isFavority) {
            up_bar_favorite.setBackgroundResource(R.drawable.star_in_favorite);
            //   hgbSaveDataClass.getTravelOrder().setmIsFavorite(false);

        } else {
            //    hgbSaveDataClass.getTravelOrder().setmIsFavorite(true);
            up_bar_favorite.setBackgroundResource(R.drawable.thin_0651_star_favorite_rating);
        }
    }


    public void setTitleForItirnarary(String solutionName) {

        itirnarary_title_Bar.setText(hgbSaveDataClass.getTravelOrder().getmSolutionName());
        itirnarary_title_Bar.setTag(hgbSaveDataClass.getTravelOrder().getmSolutionID());

    }

    private void getCurrentItinerary(String solutionId) {

        ConnectionManager.getInstance(MainActivityBottomTabs.this).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;
                hgbSaveDataClass.setTravelOrder(userTravelMainVO);
            }

            @Override
            public void onError(Object data) {
                reportError(data);

                // ErrorMessage(data);

            }
        });
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


        final ImageButton toolbar_go_to_iternerary = (ImageButton) mToolbar.findViewById(R.id.toolbar_go_to_iternerary);
        toolbar_go_to_iternerary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(), null);


                if (hgbSaveDataClass.getTravelOrder() == null) {
                    toolbar_go_to_iternerary.setEnabled(false);
                } else {
                    goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(), null);
                }


            }
        });

    }

    private void getUserData() {

        ConnectionManager.getInstance(MainActivityBottomTabs.this).getUserProfile(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserProfileVO mCurrentUser = (UserProfileVO) data;
                String logInEmail = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, "");

                hgbSaveDataClass.getPersonalUserInformation().setUserEmailLogIn(logInEmail);
                hgbSaveDataClass.setCurrentUser(mCurrentUser);


                if (!mCurrentUser.getIsTravelprofile()) {
                    showUserProfiles();
                }

                String profileID = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, "");
                hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileId(profileID);
                //my_trips_image_profile.setImageBitmap(HGBUtility.getBitmapFromCache(getBaseContext()));
                getAccountsProfiles();
                //  selectItem(ToolBarNavEnum.TRIPS.getNavNumber(), null,true);


            }

            @Override
            public void onError(Object data) {
                reportError(data);
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
                reportError(data);
            }
        });
    }

    public void editProfileTypeMainToolBar() {
        ArrayList<AccountsVO> accounts = hgbSaveDataClass.getAccounts();
        my_trip_profile = (FontTextView) findViewById(R.id.my_trip_profile);

        for (AccountsVO account : accounts) {
            String userEmailLogIn = hgbSaveDataClass.getPersonalUserInformation().getUserEmailLogIn();
            if (account.getEmail().equals(userEmailLogIn)) {
                my_trip_profile.setText(account.getTravelpreferenceprofile().getProfilename());
                my_trip_profile.setTag(account.getTravelpreferenceprofile().getId());
                //   hgbSaveDataClass.getCurrentUser().setmTravelPreferencesProfileId(account.getTravelpreferenceprofile().getId());
                hgbSaveDataClass.getPersonalUserInformation().setmTravelPreferencesProfileId(account.getTravelpreferenceprofile().getId());
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
                reportError(data);
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
                reportError(data);
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
                reportError(data);
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
                reportError(data);
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
                reportError(data);
            }
        });
    }

    private void getCountries() {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                //responceText.setText((String) data);
                BookingRequestVO bookingrequest = (BookingRequestVO) data;
                hgbSaveDataClass.setBookingRequest(bookingrequest);
                hgbSaveDataClass.getBookingRequest().sortCountryItems();
            }

            @Override
            public void onError(Object data) {
                reportError(data);
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


    public void selectItem(int position, Bundle bundle, final boolean stashFragment) {
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
            case FREE_USER_FRAGMENT:
                fragment = FreeUserFragment.newInstance(navPosition);
                stashToBack = false;
                break;

        }


        if (bundle != null) {
            Bundle arguments = fragment.getArguments();
            arguments.putAll(bundle);
            fragment.setArguments(arguments);
        }

        if (isFreeUser && (navBar.equals(ToolBarNavEnum.COMPANIONS_PERSONAL_DETAILS) || navBar.equals(ToolBarNavEnum.PAYMENT_DETAILS) || navBar.equals(ToolBarNavEnum.COMPANIONS) || navBar.equals(ToolBarNavEnum.ALL_COMPANIONS_VIEW) || navBar.equals(ToolBarNavEnum.CREDIT_CARD_LIST))) {
            isAddAnimation = true;
            fragment = isFreeUser(fragment, navPosition);
        }


        HGBUtility.hideKeyboard(MainActivityBottomTabs.this);
        HGBUtility.goToNextFragmentIsAddToBackStack(getSupportFragmentManager(), fragment, stashToBack, isAddAnimation);
        mToolbar.initToolBarItems();
        mToolbar.updateToolBarView(position);
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

    private void sendNewSolutionName(String solutionName) {
        ConnectionManager.getInstance(MainActivityBottomTabs.this).putItenararyTripName(solutionName, hgbSaveDataClass.getTravelOrder().getmSolutionID(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onError(Object data) {
                reportError(data);
            }
        });
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

    @Override
    public void gotToStartMenuActivity() {
        HGBUtility.removeAllFragments(getSupportFragmentManager());
        //  hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.CHOOSEN_SERVER, "");


        Intent intent = new Intent(getApplicationContext(), StartingMenuActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

        finish();
    }

    public void setEditClickCB(PreferenceSettingsFragment.OnItemClickListener editClickCB) {
        this.editClickCB = editClickCB;
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
                enableFullScreen(false);
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
    public void callRefreshItinerary(final int fragment) {

        ConnectionManager.getInstance(MainActivityBottomTabs.this).getItinerary(hgbSaveDataClass.getSolutionID(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                hgbSaveDataClass.setTravelOrder((UserTravelMainVO) data);
                continueFlow(fragment);
            }

            @Override
            public void onError(Object data) {
                reportError("Problem updating grid ");

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

   /* @Override
    public void goToFragment(int fragmentname, Bundle bundle, boolean) {
        selectItem(fragmentname, bundle);
    }*/

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



    private void reportError(Object data){
        HGBErrorHelper errorHelper = new HGBErrorHelper();
        errorHelper.setMessageForError((String) data);
        errorHelper.show(getFragmentManager(), (String) data);
    }

}
