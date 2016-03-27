package hellogbye.com.hellogbyeandroid;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.models.CountryItem;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionStaticRelationshipTypesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;

/**
 * Created by arisprung on 9/20/15.
 */
public interface HGBMainInterface {

    void openVoiceToTextControl();

    void setTravelOrder(UserTravelMainVO travelorder);

    void setCNCItems(ArrayList<CNCItem> cncItemArrayList);

    void setSolutionID(String userID);

    void setHomeImage(String id);

    String getSolutionID();

    void setAlternativeFlights(List<NodesVO> alternativeFlightsVO);

    UserTravelMainVO getTravelOrder();

    ArrayList<CNCItem> getCNCItems();

    List<NodesVO> getAlternativeFlights();

    void addCNCItem(CNCItem cncitem);

    void callRefreshItinerary(final int fragment);

    CostumeToolBar getToolBar();

    void goToFragment(int fragment, Bundle bundle);

    void continueFlow(int fragment);

    void setTotalPrice(String totalPrice);

    String getTotalPrice();

    void loadJSONFromAsset();


    void setAccountSettingsAttribute(List<SettingsAttributeParamVO> settingsAttribute);
    List<SettingsAttributeParamVO> getAccountSettingsAttribute();



    ArrayList<UserData> getListUsers();
    UserData getCurrentUser();
    void setCurrentUser(UserData currentUser);

    ArrayList<CountryItem> getEligabileCountries();
    void setEligabileCountries(ArrayList<CountryItem> mEligabileCountries);

    ArrayList<CreditCardItem> getCreditCards();
    void setCreditCards( ArrayList<CreditCardItem> mCreditCardsList);

    void setListUsers( ArrayList<UserData> mTravelList);

    void setAccountSettingsFlightStopAttributes(List<SettingsAttributesVO> settingsAttribute);
    List<SettingsAttributesVO> getAccountSettingsFlightStopAttributes();

    void setAccountSettingsFlightCarrierAttributes(List<SettingsAttributesVO> flightCarrierAttributes);
    List<SettingsAttributesVO> getAccountSettingsFlightCarrierAttributes();

    void setAccountSettingsFlightCabinClassAttributes(List<SettingsAttributesVO> flightCabinClassAttributes);
    List<SettingsAttributesVO> getAccountSettingsFlightCabinClassAttributes();

    void setAccountSettingsFlightAircraftAttributes(List<SettingsAttributesVO> flightAircraftAttributes);
    List<SettingsAttributesVO> getAccountSettingsFlightAircraftAttributes();


    void setAccountSettingsHotelStarAttributes(List<SettingsAttributesVO> hotelStarAttributes);
    List<SettingsAttributesVO> getAccountSettingsHotelStarAttributes();

    void setAccountSettingsHotelChainAttributes(List<SettingsAttributesVO> hotelChainAttributes);
    List<SettingsAttributesVO> getAccountSettingsHotelChainAttributes();

    void setAccountSettingsHotelSmokingAttributes(List<SettingsAttributesVO> hotelSmokingAttributes);
    List<SettingsAttributesVO> getAccountSettingsHotelSmokingClassAttributes();

    void setAccountSettingsHotelBedTypeAttributes(List<SettingsAttributesVO> hotelBedTypeAttributes);
    List<SettingsAttributesVO> getAccountSettingsHotelBedTypeAttributes();


    void setCompanions(ArrayList<CompanionVO> companions);

    ArrayList<CompanionVO> getCompanions();

    void gotToStartMenuActivity();


    void setCompanionsStaticRelationshipTypes(ArrayList<CompanionStaticRelationshipTypesVO> componentsStaticRelationshipTypes);

    ArrayList<CompanionStaticRelationshipTypesVO> getCompanionsStaticRelationshipTypes();

    void setAccounts(ArrayList<AccountsVO> accounts);

    ArrayList<AccountsVO> getAccounts();



    void setCreditCardsSelected(HashSet<CreditCardItem> cardsList);

    HashSet<CreditCardItem> getCreditCardsSelected();


    void setBookingHashMap(HashMap<String,String> bookigItems);

    HashMap<String,String> getBookingHashMap();


}
