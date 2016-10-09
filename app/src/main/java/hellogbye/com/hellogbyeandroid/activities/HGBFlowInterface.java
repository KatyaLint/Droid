package hellogbye.com.hellogbyeandroid.activities;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;

/**
 * Created by nyawka on 3/16/16.
 */
public interface HGBFlowInterface {

    void callRefreshItinerary(final int fragment);
    void callRefreshItineraryWithCallback(final int fragment, RefreshComplete refreshlistner);
    CostumeToolBar getToolBar();

    void goToFragment(int fragment, Bundle bundle, boolean stashFragment);
    void goToFragment(int fragment, Bundle bundle);

    void continueFlow(int fragment);
    void gotToStartMenuActivity();

    void setCreditCardsSelected(HashSet<CreditCardItem> cardsList);
    HashSet<CreditCardItem> getCreditCardsSelected();
    void setBookingHashMap(HashMap<String, String> bookigItems);
    HashMap<String, String> getBookingHashMap();
    ArrayList<UserProfileVO> getListUsers();
    void setListUsers(ArrayList<UserProfileVO> travellist);
    UserProfileVO getCurrentUser();
    void setCurrentUser(UserProfileVO currentUser);
    void setEligabileCountries(ArrayList<CountryItemVO> list);
    ArrayList<CountryItemVO> getEligabileCountries();
    ArrayList<CreditCardItem> getCreditCards();
    void setCreditCards(ArrayList<CreditCardItem> mCreditCardList);

    void closeRightPane();
    void openRightPane();
    void selectBottomBar(int selection);
    void enableFullScreen(boolean visible);

    void setCvvList(ArrayList<String> cvvList);
    ArrayList<String> getCvvList();








}
