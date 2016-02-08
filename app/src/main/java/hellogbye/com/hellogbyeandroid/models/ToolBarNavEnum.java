package hellogbye.com.hellogbyeandroid.models;

public enum ToolBarNavEnum {
    HOME(23,"Home"),
    ITINARERY(24,"Current Itinerary"),
    HISTORY(25,"History"),
    TRIPS (0,"My Trips"),
    COMPANIONS(1,"Travel Companions"),
    PREFERENCE(2,"Preference Settings"),
    ACCOUNT(3,"Account Settings"),
    HELP(4,"Help & Feedback"),
    CNC(8,"cnc"),
    ALTERNATIVE_FLIGHT(9,"Alternative Flight"),
    HOTEL(10,"Hotel"),
    ALTERNATIVE_FLIGHT_DETAILS(11,"Alternative flight details"),
    PAYMENT_DETAILS(12,"Payment details"),
    PREFERENCES_TAB_SETTINGS(13,"Preferences Settings"),
    PAYMENT_TRAVLERS(14,"Preferences Travelrs"),
    PAYMENT_TRAVLERS_DETAILS(15,"Preferences Travelrs Details"),
    PREFERENCES_SEARCH_LIST_SETTINGS(16,"Preferences Settings"),
    PREFERENCES_DRAG_LIST_SETTINGS(17,"Preferences Settings"),
    PREFERENCES_CHECK_LIST_SETTINGS(18,"Preferences Settings"),
    PREFERENCES_SPECIFIC_LIST_SETTINGS(19,"Preferences Settings"),
    SELECT_CREDIT_CARD(20,"Select Credit Card"),
    ADD_CREDIT_CARD(21,"Add Credit Card"),
    COMPANIONS_DETAILS(22,"Companions Details"),
    COMPANIONS_PERSONAL_DETAILS(26,"Personal Info");


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
