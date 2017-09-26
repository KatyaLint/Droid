package hellogbye.com.hellogbyeandroid.models.vo.creditcard;

/**
 * Created by arisprung on 11/15/15.
 */
public class PaymentChild {

    private String nameText;
    private String totalText;
    private boolean selected;
    private String guid;
    private String paxguid;
    private String creditcard;
    private String creditcardid;
    private String parentflight;
    private String flightPath;
    private String hotelName;
    private String flightDuraion;
    private String hotelPricePerNight;
    private String flighNumber;
    private String flightClass;
    private String flightDeparture;
    private String flightArrival;
    private String hotelRoomType;
    private String hotelCheckIn;
    private String hotelDuration;
    private String operatorName;

    public PaymentChild(String nameText, String totalText, boolean selected,String guid,String paxguid,String creditcard,String parentflight) {
        this.nameText = nameText;
        this.totalText = totalText;
        this.selected = selected;
        this.guid = guid;
        this.paxguid = paxguid;
        this.creditcard = creditcard;
        this.parentflight = parentflight;
    }

    public String getFlightPath() {
        return flightPath;
    }

    public void setFlightPath(String flightPath) {
        this.flightPath = flightPath;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getFlightDuraion() {
        return flightDuraion;
    }

    public void setFlightDuraion(String flightDuraion) {
        this.flightDuraion = flightDuraion;
    }

    public String getHotelPricePerNight() {
        return hotelPricePerNight;
    }

    public void setHotelPricePerNight(String hotelPricePerNight) {
        this.hotelPricePerNight = hotelPricePerNight;
    }

    public String getFlighNumber() {
        return flighNumber;
    }

    public void setFlighNumber(String flighNumber) {
        this.flighNumber = flighNumber;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getFlightDeparture() {
        return flightDeparture;
    }

    public void setFlightDeparture(String flightDeparture) {
        this.flightDeparture = flightDeparture;
    }

    public String getFlightArrival() {
        return flightArrival;
    }

    public void setFlightArrival(String flightArrival) {
        this.flightArrival = flightArrival;
    }

    public String getHotelRoomType() {
        return hotelRoomType;
    }

    public void setHotelRoomType(String hotelRoomType) {
        this.hotelRoomType = hotelRoomType;
    }

    public String getHotelCheckIn() {
        return hotelCheckIn;
    }

    public void setHotelCheckIn(String hotelCheckIn) {
        this.hotelCheckIn = hotelCheckIn;
    }

    public String getHotelDuration() {
        return hotelDuration;
    }

    public void setHotelDuration(String hotelDuration) {
        this.hotelDuration = hotelDuration;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getTotalText() {
        return totalText;
    }

    public void setTotalText(String totalText) {
        this.totalText = totalText;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPaxguid() {
        return paxguid;
    }

    public void setPaxguid(String paxguid) {
        this.paxguid = paxguid;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }

    public String getCreditcardid() {
        return creditcardid;
    }

    public void setCreditcardid(String creditcardid) {
        this.creditcardid = creditcardid;
    }

    public String getParentflight() {
        return parentflight;
    }

    public void setParentflight(String parentflight) {
        this.parentflight = parentflight;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}
