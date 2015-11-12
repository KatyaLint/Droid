package hellogbye.com.hellogbyeandroid.models;

public enum ToolBarNavEnum {
    HOME(0,"Home"),ITINARERY(1,"Current Itinerary"),HISTORY(2,"History"),
    TRIPS (3,"My Trips"), COMPANIONS(4,"Travel Companions"),
    PREFERENCE(5,"Preference Settings"), ACCOUNT(6,"Account Settings"),
    HELP(7,"Help & Feedback"),CNC(8,"cnc"),ALTERNATIVE_FLIGHT(9,"Alternative Flight"),HOTEL(10,"Hotel"),
    ALTERNATIVE_FLIGHT_DETAILS(11,"Alternative flight details");

    private final int navNumber;
    private final String navTitle;

    private ToolBarNavEnum(int navNumber, String title){
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
