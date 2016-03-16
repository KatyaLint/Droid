//package hellogbye.com.hellogbyeandroid.activities;
//
//import android.app.Fragment;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.List;
//
//import hellogbye.com.hellogbyeandroid.fragments.CNCFragment;
//import hellogbye.com.hellogbyeandroid.fragments.HelpFeedbackFragment;
//import hellogbye.com.hellogbyeandroid.fragments.HistoryFragment;
//import hellogbye.com.hellogbyeandroid.fragments.HotelFragment;
//import hellogbye.com.hellogbyeandroid.fragments.MyTripsFragment;
//import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightFragment;
//import hellogbye.com.hellogbyeandroid.fragments.alternative.AlternativeFlightsDetailsFragment;
//import hellogbye.com.hellogbyeandroid.fragments.checkout.AddCreditCardFragment;
//import hellogbye.com.hellogbyeandroid.fragments.checkout.CreditCardListFragment;
//import hellogbye.com.hellogbyeandroid.fragments.checkout.PaymentDetailsFragemnt;
//import hellogbye.com.hellogbyeandroid.fragments.checkout.TravlerDetailsFragment;
//import hellogbye.com.hellogbyeandroid.fragments.checkout.TravlersFragment;
//import hellogbye.com.hellogbyeandroid.fragments.companions.CompanionDetailsFragment;
//import hellogbye.com.hellogbyeandroid.fragments.companions.TravelCompanionsFragment;
//import hellogbye.com.hellogbyeandroid.fragments.itinerary.ItineraryFragment;
//import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
//import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesCheckListFragment;
//import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesDragListFragment;
//import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesSearchListFragment;
//import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferencesTabsFragmentSettings;
//import hellogbye.com.hellogbyeandroid.fragments.settings.AccountPersonalInfoSettingsFragment;
//import hellogbye.com.hellogbyeandroid.fragments.settings.AccountSettingsFragment;
//import hellogbye.com.hellogbyeandroid.models.CNCItem;
//import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
//import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
//import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
//import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
//import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;
//
///**
// * Created by nyawka on 3/16/16.
// */
//public class HGBFlowClass implements HGBFlowInterface {
//
//
//
//
//
//    @Override
//    public void callRefreshItinerary(int fragment) {
//
//    }
//
//    @Override
//    public CostumeToolBar getToolBar() {
//        return null;
//    }
//
//    @Override
//    public void goToFragment(int fragment, Bundle bundle) {
//
//    }
//
//    @Override
//    public void continueFlow(int fragment) {
//        if (fragment == ToolBarNavEnum.ALTERNATIVE_FLIGHT.getNavNumber()) {
//            selectItem(ToolBarNavEnum.ITINARERY.getNavNumber(), null);
//        }
//    }
//
//
//    public void selectItem(int position, Bundle bundle) {
//        // update the main content by replacing fragments
//
//        Fragment fragment = null;
//        ToolBarNavEnum navBar = ToolBarNavEnum.getNav(position);
//        boolean stashToBack = true;
//        int navPosition = position;//navBar.getNavNumber();
//
//        switch (navBar) {
//            case HOME:
//                //  fragment = HomeFragment.newInstance(navPosition);
//                fragment = CNCFragment.newInstance(navPosition);
//
//                break;
//            case HISTORY:
//                fragment = HistoryFragment.newInstance(navPosition);
//                break;
//            case TRIPS:
//                fragment = MyTripsFragment.newInstance(navPosition);
//                break;
//            case COMPANIONS:
//                fragment = TravelCompanionsFragment.newInstance(navPosition);
//                break;
//            case COMPANIONS_DETAILS:
//                fragment = CompanionDetailsFragment.newInstance(navPosition);
//                break;
//            case PREFERENCE:
//                fragment = PreferenceSettingsFragment.newInstance(navPosition);
//                break;
//            case ACCOUNT:
//                fragment = AccountSettingsFragment.newInstance(navPosition);
//                break;
//            case HELP:
//                fragment = HelpFeedbackFragment.newInstance(navPosition);
//                break;
//            case ITINARERY:
//                fragment = ItineraryFragment.newInstance(navPosition);
//                break;
//            case ALTERNATIVE_FLIGHT:
//                fragment = AlternativeFlightFragment.newInstance(navPosition);
//                stashToBack = false;
//                break;
//            case HOTEL:
//                fragment = HotelFragment.newInstance(navPosition);
//                break;
//            case ALTERNATIVE_FLIGHT_DETAILS:
//                fragment = AlternativeFlightsDetailsFragment.newInstance(navPosition);
//                stashToBack = false;
//                break;
//            case PAYMENT_DETAILS:
//                fragment = PaymentDetailsFragemnt.newInstance(navPosition);
//                break;
//            case PREFERENCES_TAB_SETTINGS:
//                fragment = PreferencesTabsFragmentSettings.newInstance(navPosition);
//                break;
//            case PREFERENCES_SEARCH_LIST_SETTINGS:
//                fragment = PreferencesSearchListFragment.newInstance(navPosition);
//                break;
//            case PREFERENCES_CHECK_LIST_SETTINGS:
//                fragment = PreferencesCheckListFragment.newInstance(navPosition);
//                break;
//            case PREFERENCES_DRAG_LIST_SETTINGS:
//                fragment = PreferencesDragListFragment.newInstance(navPosition);
//                break;
//
//            case PAYMENT_TRAVLERS:
//                fragment = TravlersFragment.newInstance(navPosition);
//                break;
//            case PAYMENT_TRAVLERS_DETAILS:
//                fragment = TravlerDetailsFragment.newInstance(navPosition);
//                break;
//            case SELECT_CREDIT_CARD:
//                fragment = CreditCardListFragment.newInstance(navPosition);
//                break;
//            case ADD_CREDIT_CARD:
//                fragment = AddCreditCardFragment.newInstance(navPosition);
//                break;
//            case COMPANIONS_PERSONAL_DETAILS:
//                fragment = AccountPersonalInfoSettingsFragment.newInstance(navPosition);
//                break;
//        }
//
//        if (bundle != null) {
//            fragment.setArguments(bundle);
//        }
//
//
//        //TODO Kate
//
////        HGBUtility.goToNextFragmentIsAddToBackStack(this, fragment, stashToBack);
////        mToolbar.initToolBarItems();
////        mToolbar.updateToolBarView(position);
////        mDrawerLayout.closeDrawer(mNavDrawerLinearLayout);
//    }
//
//
//    @Override
//    public void loadJSONFromAsset() {
//        if (mEligabileCountryList.size() > 0) {
//            return;
//        }
//
//        String json = HGBUtility.loadJSONFromAsset("countrieswithprovinces.txt", this);
//
//        Gson gson = new Gson();
//        Type listType = new TypeToken<List<CountryItemVO>>() {
//        }.getType();
//        ArrayList<CountryItemVO> list = (ArrayList<CountryItemVO>) gson.fromJson(json, listType);
//        setEligabileCountries(list);
//
//    }
//
//    @Override
//    public void gotToStartMenuActivity() {
//        HGBUtility.clearAllFragments(MainActivity.this);
//        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
//        Intent intent = new Intent(getApplicationContext(), StartingMenuActivity.class);
//        startActivity(intent);
//    }
//
//    @Override
//    public void setHomeImage(String id) {
//
//    }
//}
