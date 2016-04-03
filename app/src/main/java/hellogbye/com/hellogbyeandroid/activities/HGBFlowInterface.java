package hellogbye.com.hellogbyeandroid.activities;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionStaticRelationshipTypesVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;

/**
 * Created by nyawka on 3/16/16.
 */
public interface HGBFlowInterface {

    void callRefreshItinerary(final int fragment);
    CostumeToolBar getToolBar();

    void goToFragment(int fragment, Bundle bundle);

    void continueFlow(int fragment);
    void loadJSONFromAsset();
    void gotToStartMenuActivity();
    void setHomeImage(String id);
    void setCreditCardsSelected(HashSet<CreditCardItem> cardsList);
    HashSet<CreditCardItem> getCreditCardsSelected();
    void setBookingHashMap(HashMap<String, String> bookigItems);
    HashMap<String, String> getBookingHashMap();
    ArrayList<UserDataVO> getListUsers();
    UserDataVO getCurrentUser();
    void setCurrentUser(UserDataVO currentUser);
    void setEligabileCountries(ArrayList<CountryItemVO> list);
    ArrayList<CountryItemVO> getEligabileCountries();
    ArrayList<CreditCardItem> getCreditCards();
    void setCreditCards(ArrayList<CreditCardItem> mCreditCardList);
    void setListUsers(ArrayList<UserDataVO> travellist);




}