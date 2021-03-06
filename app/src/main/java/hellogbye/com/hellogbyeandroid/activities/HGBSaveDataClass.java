package hellogbye.com.hellogbyeandroid.activities;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.PersonalUserInformationVO;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionStaticRelationshipTypesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;


/**
 * Created by nyawka on 3/16/16.
 */
public class HGBSaveDataClass implements HGBMainInterface {

    private String mTotalPrice;
    private List<SettingsAttributeParamVO> mSettingsAttribute;
    private List<SettingsAttributesVO> settingsAttribute;
    private List<SettingsAttributesVO> flightCarrierAttributes;
    private List<SettingsAttributesVO> flightCabinClassAttributes;
    private List<SettingsAttributesVO> flightAircraftAttributes;
    private List<SettingsAttributesVO> hotelStarAttributes;
    private List<SettingsAttributesVO> hotelSmokingAttributes;
    private List<SettingsAttributesVO> hotelChainAttributes;
    private List<SettingsAttributesVO> hotelBedTypeAttributes;
    private ArrayList<CompanionVO> companions;
    private ArrayList<CompanionVO> companionsInvintation;
    private ArrayList<CompanionStaticRelationshipTypesVO> componionStaticDescriptionVOs;
    private ArrayList<AccountsVO> accounts;
    private UserProfileVO mCurrentUser;
    private BookingRequestVO mBookingRequestVO;
    private ArrayList<CreditCardItem> mCreditCardList;
    private ArrayList<UserProfileVO> mTravelList;
    private UserTravelMainVO mUserTravelOrder;
    private ArrayList<CNCItem> mCNCItems;
    private String solutionID;
    private List<NodesVO> alternativeFlights;
    private HGBPreferencesManager hgbPrefrenceManager;
    private PersonalUserInformationVO personalUserInformationVO;
    private ArrayList<MyTripItem> myUpcomingTrips;

//    public HGBSaveDataClass(MainActivity mainActivity ,HGBPreferencesManager hgbPrefrenceManager) {
//        this.hgbPrefrenceManager = hgbPrefrenceManager;
//    }

    public void setPreferenceManager(HGBPreferencesManager hgbPrefrenceManager){
        this.hgbPrefrenceManager = hgbPrefrenceManager;
    }

    @Override
    public void setCNCItems(ArrayList<CNCItem> cncItemArrayList) {
        this.mCNCItems = cncItemArrayList;
    }

    @Override
    public void setSolutionID(String userID) {
        this.solutionID = userID;
    }

    @Override
    public String getSolutionID() {
        return solutionID;
    }

    @Override
    public ArrayList<CNCItem> getCNCItems() {
        return mCNCItems;
    }

    @Override
    public void addCNCItem(CNCItem cncitem) {
        if (mCNCItems == null) {
            mCNCItems = new ArrayList<>();
        }

        mCNCItems.add(cncitem);
    }

    @Override
    public void setTravelOrder(UserTravelMainVO travelorder) {
        mUserTravelOrder = travelorder;
        if (travelorder == null) {
            setSolutionID(null);
        } else {
            setSolutionID(mUserTravelOrder.getmSolutionID());

            GsonBuilder gsonBuilder = new GsonBuilder().serializeSpecialFloatingPointValues();
            Gson gson = gsonBuilder.create();
           // Gson gson = new Gson();
            String json = gson.toJson(travelorder);
            hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_LAST_TRAVEL_VO, json);
        }
    }

    @Override
    public UserTravelMainVO getTravelOrder() {
        return mUserTravelOrder;
    }

    @Override
    public void setAlternativeFlights(List<NodesVO> alternativeFlightsVO) {
        this.alternativeFlights = alternativeFlightsVO;
    }

    @Override
    public List<NodesVO> getAlternativeFlights() {
        return alternativeFlights;
    }

 /*   @Override
    public void setTotalPrice(String totalPrice) {
        mTotalPrice = totalPrice;
    }

    @Override
    public String getTotalPrice() {
        return mTotalPrice;
    }*/

    @Override
    public void setAccountSettingsAttribute(List<SettingsAttributeParamVO> settingsAttribute) {
        this.mSettingsAttribute = settingsAttribute;
    }

    @Override
    public List<SettingsAttributeParamVO> getAccountSettingsAttribute() {
        return mSettingsAttribute;
    }

 /*   @Override
    public void setListUsers(ArrayList<UserProfileVO> mTravelList) {
        this.mTravelList = mTravelList;
    }

    @Override
    public ArrayList<UserProfileVO> getListUsers() {
        return mTravelList;
    }*/

    @Override
    public UserProfileVO getCurrentUser() {
        return mCurrentUser;
    }

    @Override
    public void setCurrentUser(UserProfileVO currentUser) {
        this.mCurrentUser = currentUser;
    }

    @Override
    public BookingRequestVO getBookingRequest() {
        return this.mBookingRequestVO;
    }

    @Override
    public void setBookingRequest(BookingRequestVO bookingRequest) {
        this.mBookingRequestVO = bookingRequest;
    }

    @Override
    public ArrayList<CreditCardItem> getCreditCards() {
        return this.mCreditCardList;
    }

    @Override
    public void setCreditCards(ArrayList<CreditCardItem> mcreditCardsList) {
        this.mCreditCardList = mcreditCardsList;
    }

    @Override
    public void setAccountSettingsFlightStopAttributes(List<SettingsAttributesVO> settingsAttribute) {
        this.settingsAttribute = settingsAttribute;
    }

    @Override
    public List<SettingsAttributesVO> getAccountSettingsFlightStopAttributes() {
        return settingsAttribute;
    }

    @Override
    public void setAccountSettingsFlightCarrierAttributes(List<SettingsAttributesVO> flightCarrierAttributes) {
        this.flightCarrierAttributes = flightCarrierAttributes;
    }

    @Override
    public List<SettingsAttributesVO> getAccountSettingsFlightCarrierAttributes() {
        return flightCarrierAttributes;
    }

    @Override
    public void setAccountSettingsFlightCabinClassAttributes(List<SettingsAttributesVO> flightCabinClassAttributes) {
        this.flightCabinClassAttributes = flightCabinClassAttributes;
    }

    @Override
    public List<SettingsAttributesVO> getAccountSettingsFlightCabinClassAttributes() {
        return flightCabinClassAttributes;
    }

    @Override
    public void setAccountSettingsFlightAircraftAttributes(List<SettingsAttributesVO> flightAircraftAttributes) {
        this.flightAircraftAttributes = flightAircraftAttributes;
    }

    @Override
    public List<SettingsAttributesVO> getAccountSettingsFlightAircraftAttributes() {
        return flightAircraftAttributes;
    }

    @Override
    public void setAccountSettingsHotelStarAttributes(List<SettingsAttributesVO> hotelStarAttributes) {
        this.hotelStarAttributes = hotelStarAttributes;
    }

    @Override
    public List<SettingsAttributesVO> getAccountSettingsHotelStarAttributes() {
        return hotelStarAttributes;
    }

    @Override
    public void setAccountSettingsHotelChainAttributes(List<SettingsAttributesVO> hotelChainAttributes) {
        this.hotelChainAttributes = hotelChainAttributes;
    }

    @Override
    public List<SettingsAttributesVO> getAccountSettingsHotelChainAttributes() {
        return hotelChainAttributes;
    }

    @Override
    public void setAccountSettingsHotelSmokingAttributes(List<SettingsAttributesVO> hotelSmokingAttributes) {
        this.hotelSmokingAttributes = hotelSmokingAttributes;
    }

    @Override
    public List<SettingsAttributesVO> getAccountSettingsHotelSmokingClassAttributes() {
        return hotelSmokingAttributes;
    }

    @Override
    public void setAccountSettingsHotelBedTypeAttributes(List<SettingsAttributesVO> hotelBedTypeAttributes) {
        this.hotelBedTypeAttributes = hotelBedTypeAttributes;
    }

    @Override
    public List<SettingsAttributesVO> getAccountSettingsHotelBedTypeAttributes() {
        return hotelBedTypeAttributes;
    }

    @Override
    public void setCompanions(ArrayList<CompanionVO> companions) {
        this.companions = companions;
    }

    @Override
    public ArrayList<CompanionVO> getCompanions() {
        return companions;
    }

    @Override
    public void setCompanionsInvintation(ArrayList<CompanionVO> companionsInvintation) {
        this.companionsInvintation = companionsInvintation;
    }

    @Override
    public ArrayList<CompanionVO> getCompanionsInvintation() {
        return companionsInvintation;
    }

    @Override
    public void addInvitationCompanionsToCompanions(ArrayList<CompanionVO> companions) {
        this.companions.addAll(companions);
    }

    @Override
    public void setCompanionsStaticRelationshipTypes(ArrayList<CompanionStaticRelationshipTypesVO> componentsStaticRelationshipTypes) {
        this.componionStaticDescriptionVOs = componentsStaticRelationshipTypes;
    }

    @Override
    public ArrayList<CompanionStaticRelationshipTypesVO> getCompanionsStaticRelationshipTypes() {
        return componionStaticDescriptionVOs;
    }

    @Override
    public void setAccounts(ArrayList<AccountsVO> accounts) {
        this.accounts = accounts;
    }

    @Override
    public ArrayList<AccountsVO> getAccounts() {
        return accounts;
    }

    @Override
    public void setPersonalUserInformation(PersonalUserInformationVO personalUserInformationVO) {
        this.personalUserInformationVO = personalUserInformationVO;
    }

    @Override
    public PersonalUserInformationVO getPersonalUserInformation() {
        return personalUserInformationVO;
    }

    @Override
    public ArrayList<MyTripItem> getUpComingTrips() {
        return myUpcomingTrips;
    }

    @Override
    public void setUpComingTrips(ArrayList<MyTripItem> upComingTrips) {
        this.myUpcomingTrips = upComingTrips;
    }

 /*   @Override
    public void setCreditCardsSelected(HashSet<CreditCardItem> cardsList) {

    }

    @Override
    public HashSet<CreditCardItem> getCreditCardsSelected() {
        return null;
    }

    @Override
    public void setBookingHashMap(HashMap<String, String> bookigItems) {

    }

    @Override
    public HashMap<String, String> getBookingHashMap() {
        return null;
    }*/
}
