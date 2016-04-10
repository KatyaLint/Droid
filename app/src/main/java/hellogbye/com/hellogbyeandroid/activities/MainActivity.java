
package hellogbye.com.hellogbyeandroid.activities;


import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import hellogbye.com.hellogbyeandroid.OnBackPressedListener;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.NavListAdapter;
import hellogbye.com.hellogbyeandroid.fragments.checkout.NewPaymentDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountPersonalEmailSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountPersonalInfoSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.settings.AccountSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.AddCreditCardFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightFragment;
import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightsDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.CNCFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.SummaryPaymentFragment;
import hellogbye.com.hellogbyeandroid.fragments.HelpFeedbackFragment;
import hellogbye.com.hellogbyeandroid.fragments.HotelFragment;
import hellogbye.com.hellogbyeandroid.fragments.itinerary.ItineraryFragment;
import hellogbye.com.hellogbyeandroid.fragments.MyTripsFragment;
import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesCheckListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesDragListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesSearchListFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesTabsFragmentSettings;
import hellogbye.com.hellogbyeandroid.fragments.companions.TravelCompanionsFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.TravlerDetailsFragment;
import hellogbye.com.hellogbyeandroid.fragments.checkout.TravlersFragment;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.NavItem;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionStaticRelationshipTypesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
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
    private ImageButton purchaseButton;

    protected OnBackPressedListener onBackPressedListener;

    private UserDataVO mCurrentUser;

    public FontTextView my_trip_profile;
    private  HGBSaveDataClass hgbSaveDataClass = new HGBSaveDataClass();
    private PreferenceSettingsFragment.OnItemClickListener editClickCB;
    private MyTripsFragment.OnItemClickListener editMyTripsClickCB;
    private FontTextView itirnarary_title_Bar;
<<<<<<< HEAD
=======
    private FontTextView mProfileName;
    private ArrayList<UserDataVO> mTravelList = new ArrayList<>();
    private ArrayList<CountryItemVO> mEligabileCountryList = new ArrayList<>();
    private ArrayList<CreditCardItem> mCreditCardList = new ArrayList<>();

>>>>>>> master

    public HGBSaveDataClass getHGBSaveDataClass(){
        return hgbSaveDataClass;
    }

    private HashSet<String> itenearySet;

    private HashSet<CreditCardItem> mSelectedCreditCards = new HashSet<>();

    private HashMap<String,String> mBookingHashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_layout);

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        hgbSaveDataClass.setPreferenceManager(hgbPrefrenceManager); //= new HGBSaveDataClass(this, hgbPrefrenceManager);


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


    }


    private void getAccountsProfiles(){
        ConnectionManager.getInstance(MainActivity.this).getUserProfileAccounts(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<AccountsVO> accounts = ( ArrayList<AccountsVO> )data;
                hgbSaveDataClass.setAccounts(accounts);
                //TODO , now getting first account from list
                AccountsVO account = accounts.get(0);
                hgbSaveDataClass.getCurrentUser().setEmailaddress(account.getEmail());
                editProfileTipeMainToolBar();
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }


    public void editProfileTipeMainToolBar(){
        AccountsVO account = hgbSaveDataClass.getAccounts().get(0);
        my_trip_profile = (FontTextView)findViewById(R.id.my_trip_profile);
        my_trip_profile.setText(account.getTravelpreferenceprofile().getProfilename());
        my_trip_profile.setTag(account.getTravelpreferenceprofile().getId());
    }

    private void getCompanionsFromServer(){

            ConnectionManager.getInstance(MainActivity.this).getCompanions(new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    ArrayList<CompanionVO> companions =(ArrayList<CompanionVO>)data;
                    hgbSaveDataClass.setCompanions(companions);
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

                UserDataVO mCurrentUser = (UserDataVO) data;
                hgbSaveDataClass.setCurrentUser(mCurrentUser);
           //     ImageView my_trips_image_profile = (ImageView)findViewById(R.id.my_trips_image_profile);
                HGBUtility.getAndSaveUserImage(mCurrentUser.getAvatar(), mProfileImage, null);
                //my_trips_image_profile.setImageBitmap(HGBUtility.getBitmapFromCache(getBaseContext()));
                getAccountsProfiles();
                selectItem(ToolBarNavEnum.HOME.getNavNumber(), null);

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }

    private void setNameInNavDraw() {

        String strName = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_NAME, "");
        // For remember me
        if (strName.equals(hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_NAME, ""))) {
            ConnectionManager.getInstance(MainActivity.this).getUserProfile(new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    mCurrentUser = (UserDataVO) data;
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


    private void editMyTrips(){
        final FontTextView my_trip_edit_button = (FontTextView) mToolbar.findViewById(R.id.my_trip_edit_button);
        my_trip_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(my_trip_edit_button.getText().equals("Edit")) {
                    editMyTripsClickCB.onItemEditMyTripsClick("edit");
                    my_trip_edit_button.setText("Done");
                }else{
                    editMyTripsClickCB.onItemEditMyTripsClick("done");
                    my_trip_edit_button.setText("Edit");
                }
            }
        });

    }


    private void toolBarProfileChnage(){

        final LinearLayout tool_bar_profile_name = (LinearLayout) mToolbar.findViewById(R.id.tool_bar_profile_name);
        tool_bar_profile_name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Bundle args = new Bundle();
                args.putString("edit_mode", "true");
                goToFragment(ToolBarNavEnum.PREFERENCE.getNavNumber(), args);
                                LinearLayout edit_preferences = (LinearLayout) mToolbar.findViewById(R.id.preferences_edit_mode);
                edit_preferences.setVisibility(View.GONE);
            }
        });

    }

    private void preferencesChanges(){
        final ImageButton edit_preferences = (ImageButton) mToolbar.findViewById(R.id.edit_preferences);
        edit_preferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View checkButton = mToolbar.findViewById(R.id.check_preferences);
                if(checkButton.getVisibility() == View.VISIBLE){
                    //delete
                    editClickCB.onItemClick("delete");
                }else if(checkButton.getVisibility() == View.GONE){
                    edit_preferences.setBackgroundResource(R.drawable.ic_delete);
                    mToolbar.findViewById(R.id.check_preferences).setVisibility(View.VISIBLE);
                    editClickCB.onItemClick("edit");
                }
            }
        });

        final ImageButton check_preferences = (ImageButton) mToolbar.findViewById(R.id.check_preferences);
        check_preferences.setOnClickListener(new View.OnClickListener(){
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
        imageButton = (ImageButton) mToolbar.findViewById(R.id.keyboard);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCNCItems();
            }
        });



        final ImageButton my_trips = (ImageButton) mToolbar.findViewById(R.id.my_trips_button);
        my_trips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFragment(ToolBarNavEnum.HOME.getNavNumber(),null);
            }
        });

        editMyTrips();

        preferencesChanges();

        toolBarProfileChnage();
        setOnClickListenerForItineraryTopBar();

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
        //selectItem(ToolBarNavEnum.HOME.getNavNumber(), null);

    }

    private void clearCNCItems() {

        hgbSaveDataClass.setCNCItems(null);
        hgbSaveDataClass.setTravelOrder(null);
        hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        hgbPrefrenceManager.removeKey(HGBPreferencesManager.HGB_LAST_TRAVEL_VO);
        Fragment currentFragment = getFragmentManager().findFragmentByTag(CNCFragment.class.toString());
        ((CNCFragment) currentFragment).initList();
    }



    private void loadNavItems() {
        mNavItemsList = new ArrayList<>();
        mNavItemsList.add(new NavItem(ToolBarNavEnum.TRIPS, false, R.drawable.my_trips_icon_enable, R.drawable.my_trips_icon_disable));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.COMPANIONS, false, R.drawable.companions_icon_enable,  R.drawable.companions_icon_disable));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.PREFERENCE, false, R.drawable.notifications_enable, R.drawable.notifications_disable));
        mNavItemsList.add(new NavItem(ToolBarNavEnum.ACCOUNT, false, R.drawable.my_account_enable, R.drawable.my_account_disable));

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
        selectItem(position, null);
    }


    @Override
    public void continueFlow(int fragment) {

        if (fragment == ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber()) {
            selectItem(ToolBarNavEnum.ITINARERY.getNavNumber(), null);
        }
    }

    @Override
    public void loadJSONFromAsset() {

//        if (hgbSaveDataClass.getEligabileCountries().size() > 0) {
//            return;
//        }
//
//        String json = HGBUtility.loadJSONFromAsset("countrieswithprovinces.txt", this);
//
//        Gson gson = new Gson();
//        Type listType = new TypeToken<List<CountryItemVO>>() {
//        }.getType();
//        ArrayList<CountryItemVO> list = gson.fromJson(json, listType);
//        hgbSaveDataClass.setEligabileCountries(list);

    }


    @Override
    public void gotToStartMenuActivity() {
        HGBUtility.clearAllFragments(MainActivity.this);
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
        Intent intent = new Intent(getApplicationContext(), StartingMenuActivity.class);
        startActivity(intent);
    }

//
//    @Override
//    public void setCompanionsStaticRelationshipTypes(ArrayList<CompanionStaticRelationshipTypesVO> componentsStaticRelationshipTypes) {
//        this.componionStaticDescriptionVOs = componentsStaticRelationshipTypes;
//    }
//
//    @Override
//    public ArrayList<CompanionStaticRelationshipTypesVO> getCompanionsStaticRelationshipTypes() {
//        return componionStaticDescriptionVOs;
//    }
//
//    @Override
//    public void setAccounts(ArrayList<AccountsVO> accounts) {
//        this.accounts = accounts;
//    }
//
//    @Override
//    public ArrayList<AccountsVO> getAccounts() {
//        return this.accounts;
//    }


    @Override
    public void setCreditCardsSelected(HashSet<CreditCardItem> cardsList) {
        mSelectedCreditCards = cardsList;
    }

    @Override
    public  HashSet<CreditCardItem>  getCreditCardsSelected() {
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
    public ArrayList<UserDataVO> getListUsers() {
        return mTravelList;
    }

    @Override
    public UserDataVO getCurrentUser() {
        return mCurrentUser;
    }

    @Override
    public void setCurrentUser(UserDataVO currentUser) {
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
    public void setListUsers(ArrayList<UserDataVO> travellist) {
        mTravelList = travellist;
    }



    public void selectItem(int position, Bundle bundle) {
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
//            case HISTORY:
//                fragment = HistoryFragment.newInstance(navPosition);
//                break;
            case TRIPS:
                fragment = MyTripsFragment.newInstance(navPosition);
                break;
            case COMPANIONS:
                fragment = TravelCompanionsFragment.newInstance(navPosition);
                break;
            case COMPANIONS_DETAILS:
                fragment = CompanionDetailsFragment.newInstance(navPosition);
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
                itirnarary_title_Bar.setText(hgbSaveDataClass.getTravelOrder().getmSolutionName());
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
            case PREFERENCES_DRAG_LIST_SETTINGS:
                fragment = PreferencesDragListFragment.newInstance(navPosition);
                break;

            case PAYMENT_TRAVLERS:
                fragment = TravlersFragment.newInstance(navPosition);
                break;
            case PAYMENT_TRAVLERS_DETAILS:
                fragment = TravlerDetailsFragment.newInstance(navPosition);
                break;
            case SELECT_CREDIT_CARD:
                fragment = SummaryPaymentFragment.newInstance(navPosition);
                break;
            case ADD_CREDIT_CARD:
                fragment = AddCreditCardFragment.newInstance(navPosition);
                break;
            case COMPANIONS_PERSONAL_DETAILS:
                fragment = AccountPersonalInfoSettingsFragment.newInstance(navPosition);
                break;
            case COMPANIONS_PERSONAL_EMAILS:
                fragment = AccountPersonalEmailSettingsFragment.newInstance(navPosition);
                break;
        }

        if (bundle != null) {
            fragment.setArguments(bundle);
        }


        HGBUtility.goToNextFragmentIsAddToBackStack(this, fragment, stashToBack);
        mToolbar.initToolBarItems();
        mToolbar.updateToolBarView(position);
        mDrawerLayout.closeDrawer(mNavDrawerLinearLayout);
    }


    private void setOnClickListenerForItineraryTopBar(){
        purchaseButton = (ImageButton) mToolbar.findViewById(R.id.purchaseButton);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorityItinerary();
                // goToFragment(ToolBarNavEnum.PAYMENT_DETAILS.getNavNumber(), null);
            }
        });
        //Kate
        itirnarary_title_Bar = (FontTextView)findViewById(R.id.itirnarary_title_Bar);



        LayoutInflater li = LayoutInflater.from(MainActivity.this);
       final View promptsView = li.inflate(R.layout.popup_layout_change_iteinarary_name, null);
       final EditText input = (EditText) promptsView
                .findViewById(R.id.change_iteinarary_name);


        itirnarary_title_Bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                input.setText(itirnarary_title_Bar.getText());
                HGBUtility.showAlertPopUp(MainActivity.this, input, promptsView, getResources().getString(R.string.edit_trip_name),
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

    @Override
    public void onBackPressed() {

        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
        }

        //TODO this is when I want the fragment to contorl the back -Kate I suggest we do this for all Fragments
        FragmentManager.BackStackEntry backEntry = getFragmentManager().getBackStackEntryAt(getFragmentManager().getBackStackEntryCount() - 1);
        String str = backEntry.getName();
        if (str.equals(HotelFragment.class.toString()) &&  !HotelFragment.IS_MAIN_BACK_ALLOWED) {
            return;
        }


        if (HGBUtility.clearBackStackAndGoToNextFragment(this)) {
            // super.onBackPressed();
//            Stack<Fragment> fragmentStack = HGBUtility.getFragmentStack();
            Fragment fragmentTemp = HGBUtility.getFragmentStack().lastElement();
           // Fragment fragment = fragmentStack.peek();
            Bundle arguments = fragmentTemp.getArguments();
            int fragNumber = arguments.getInt(HGBConstants.ARG_NAV_NUMBER);

            mToolbar.updateToolBarView(fragNumber);

           // editProfileTipeMainToolBar();
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


    private void setFavorityItinerary(){
        UserTravelMainVO travelOrder = hgbSaveDataClass.getTravelOrder();
        String solutionID = travelOrder.getmSolutionID();
        boolean isFavorite = travelOrder.ismIsFavorite();
        ConnectionManager.getInstance(MainActivity.this).putFavorityItenarary(isFavorite,solutionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                System.out.println("Kate setFavorityItinerary cool");
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
    public void goToFragment(int fragmentname, Bundle bundle) {
        selectItem(fragmentname, bundle);
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
            String json = gsonback.toJson(hgbSaveDataClass.getCNCItems());
            hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST, json);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setEditClickCB(PreferenceSettingsFragment.OnItemClickListener editClickCB) {
        this.editClickCB = editClickCB;
    }


    public void setEditMyTripsClickCB(MyTripsFragment.OnItemClickListener editMyTripsClickCB) {
        this.editMyTripsClickCB = editMyTripsClickCB;
    }
}