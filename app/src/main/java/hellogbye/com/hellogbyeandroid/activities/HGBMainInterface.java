package hellogbye.com.hellogbyeandroid.activities;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.models.vo.cnc.CNCItem;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.PersonalUserInformationVO;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.cnc.CNCTutorialsVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionStaticRelationshipTypesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.models.vo.profiles.DefaultsProfilesVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;

/**
 * Created by arisprung on 9/20/15.
 */
public interface HGBMainInterface {

    void setCNCItems(ArrayList<CNCItem> cncItemArrayList);
    void setSolutionID(String userID);
    String getSolutionID();
    ArrayList<CNCItem> getCNCItems();
    void addCNCItem(CNCItem cncitem);

    void setTravelOrder(UserTravelMainVO travelorder);
    UserTravelMainVO getTravelOrder();

    void setAlternativeFlights(List<NodesVO> alternativeFlightsVO);
    List<NodesVO> getAlternativeFlights();

    void setAccountSettingsAttribute(List<SettingsAttributeParamVO> settingsAttribute);
    List<SettingsAttributeParamVO> getAccountSettingsAttribute();


    UserProfileVO getCurrentUser();
    void setCurrentUser(UserProfileVO currentUser);

    BookingRequestVO getBookingRequest();
    void setBookingRequest(BookingRequestVO bookingRequest);

    ArrayList<CreditCardItem> getCreditCards();
    void setCreditCards( ArrayList<CreditCardItem> mCreditCardsList);



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

    void setAccountFarePreferences(List<SettingsAttributesVO> hotelChainAttributes);
    List<SettingsAttributesVO> getAccountFarePreferences();

    void setAccountSettingsHotelSmokingAttributes(List<SettingsAttributesVO> hotelSmokingAttributes);
    List<SettingsAttributesVO> getAccountSettingsHotelSmokingClassAttributes();

    void setAccountSettingsHotelBedTypeAttributes(List<SettingsAttributesVO> hotelBedTypeAttributes);
    List<SettingsAttributesVO> getAccountSettingsHotelBedTypeAttributes();


    void setCompanions(ArrayList<CompanionVO> companions);
    ArrayList<CompanionVO> getCompanions();

    void setCompanionsInvintation(ArrayList<CompanionVO> companions);
    ArrayList<CompanionVO> getCompanionsInvintation();

    void addInvitationCompanionsToCompanions(ArrayList<CompanionVO> companions);


    void setCompanionsStaticRelationshipTypes(ArrayList<CompanionStaticRelationshipTypesVO> componentsStaticRelationshipTypes);
    ArrayList<CompanionStaticRelationshipTypesVO> getCompanionsStaticRelationshipTypes();

    void setAccounts(ArrayList<AccountsVO> accounts);
    ArrayList<AccountsVO> getAccounts();

    void setPersonalUserInformation(PersonalUserInformationVO personalUserInformationVO);
    PersonalUserInformationVO getPersonalUserInformation();


    ArrayList<MyTripItem> getUpComingTrips();
    void setUpComingTrips( ArrayList<MyTripItem> upComingTrips);

    List<AccountDefaultSettingsVO> getAccountDefaultSettingsVOs();

    void setAccountDefaultSettingsVOs(List<AccountDefaultSettingsVO> accountDefaultSettingsVOs);


    List<DefaultsProfilesVO> getDefaultsProfilesVOs();
    void setDefaultsProfilesVOs(List<DefaultsProfilesVO> defaultsProfilesVOs);

    CNCTutorialsVO getCNCTutorialsVOs();
    void setCNCTutorialsVOs(CNCTutorialsVO cncTutorialsVO);



}
