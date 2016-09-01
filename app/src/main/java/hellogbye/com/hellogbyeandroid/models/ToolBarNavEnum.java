package hellogbye.com.hellogbyeandroid.models;

public enum ToolBarNavEnum {
    TRIPS (0,"My Trips"),
    COMPANIONS(1,"My Companions"),
    ACCOUNT(2,"My Account"),
    NOTIFICATIONS(3,"Notifications"),
    CNC(4,"CNC"),
    TREVEL_PREFERENCE(5,"Preference Settings"),
    HELP(6,"Help & Feedback"),
    HOME(7,"Home"),
    ITINARERY(8,"Current Itinerary"),
    ALTERNATIVE_FLIGHT_ROUND_TRIP(9,"Flight Details"),
    HOTEL(10,"Hotel Details"),
    ALTERNATIVE_FLIGHT_DETAILS(11,"Alternative Flight"),
    PAYMENT_DETAILS(12,"Payment Info"),
    PREFERENCES_TAB_SETTINGS(13,"Preferences Settings"),
    PAYMENT_TRAVELERS(14,"Travelers"),
    PAYMENT_TRAVELERS_DETAILS(15,"Traveler"),
    PREFERENCES_SEARCH_LIST_SETTINGS(16,"Preferences Settings"),
    PREFERENCES_DRAG_LIST_SETTINGS(17,"Preferences Settings"),
    PREFERENCES_CHECK_LIST_SETTINGS(18,"Preferences Settings"),
    PREFERENCES_SPECIFIC_LIST_SETTINGS(19,"Preferences Settings"),
    SELECT_CREDIT_CARD(20,"Review"),
    ADD_CREDIT_CARD(21,"Add Credit Card"),
    COMPANIONS_DETAILS(22,"Companions Details"),
    HISTORY(23,"History"),
    COMPANIONS_PERSONAL_DETAILS(24,"Personal Info"),
    PREFERENCE_SETTINGS_EMAILS(25,"Email"),
    CREDIT_CARD_LIST(26,"Payment Cards"),
    CHECKOUT_CONFIRMATION(27,"Checkout confirmation"),
    CNC_TUTORIAL(28,"CNC Tutorial"),
    HAZARDOUS_NOTICE(29,"Make Payment"),
    ALL_COMPANIONS_VIEW(30,"My Companions"),
    COMPANION_HELP_FEEDBACK(31,"Help and Feedback"),
    PREFERENCES_CHECK_AS_RADIO_SETTINGS(32,"Preferences Settings"),
    PREFERENCES_MEMBERSHIP(33,"Membership"),
    ALTERNATIVE_FLIGHT_FACTORY(34,"Flight Details"),
    ALTERNATIVE_FLIGHT_ONE_WAY_TRIP(35,"Flight Details"),
    FREE_USER_FRAGMENT(36,"Free User"),
    SELECT_HOTEL_FRAGMENT(37,"Select Hotel")
    ;



    private final int navNumber;
    private final String navTitle;

    ToolBarNavEnum(int navNumber, String title){
        this.navNumber = navNumber;
        this.navTitle = title;
    }

    public int getNavNumber() {
        return navNumber;
    }

    public String getNavTitle() {
        return navTitle;
    }

    public static String getNavNameByPosition(int val){
        ToolBarNavEnum retMTab = null;
        for (ToolBarNavEnum tempTab : ToolBarNavEnum.values()) {
            if(tempTab.getNavNumber() == val)  {
                retMTab = tempTab;
                break;
            }
        }
        return retMTab.getNavTitle();
    }

    public static ToolBarNavEnum getNav(int val){
        ToolBarNavEnum retMTab = null;
        for (ToolBarNavEnum tempTab : ToolBarNavEnum.values()) {
            if(tempTab.getNavNumber() == val)  {
                retMTab = tempTab;
                break;
            }
        }
        return retMTab;
    }

}
