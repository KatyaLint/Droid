package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 9/9/15.
 */
public class LegsVO {
    @SerializedName("type")
    private String mType;
    @SerializedName("parentguid")
    private String mParentguid;
    @SerializedName("guid")
    private String mGuid;
    @SerializedName("flightnumber")
    private String mFlightNumber;
    @SerializedName("flighttime")
    private String mFlightTime;
    @SerializedName("departure")
    private String mDeparture;
    @SerializedName("departuretime")
    private String mDepartureTime;
    @SerializedName("arrival")
    private String mArrival;
    @SerializedName("code")
    private String mCode;
    @SerializedName("origin")
    private String mOrigin;
    @SerializedName("destination")
    private String mDestination;
    @SerializedName("origincityname")
    private String mOriginCityName;
    @SerializedName("destinationcityname")
    private String mDestinationCityName;
    @SerializedName("originairportname")
    private String mOriginAirPortName;
    @SerializedName("destinationairportname")
    private String mDestinationAirportName;
    @SerializedName("carriercode")
    private String mCarrierCode;
    @SerializedName("carriername")
    private String mCarrierName;
    @SerializedName("equipment")
    private String mEquipment;
    @SerializedName("paxguid")
    private String mPaxguid;

    @SerializedName("ref")
    private String mRef;
    @SerializedName("seq")
    private String mSeq;
    @SerializedName("carrierbadgeurl")
    private String mCarrierBadgeUrl;
    @SerializedName("carrierlogourl")
    private String mCarrierLogoUrl;

    @SerializedName("fareclass")
    private String mFareClass;
    @SerializedName("farepreference")
    private String mFarePreference;

    @SerializedName("normalizedduration")
    private double mNormalizedDuration;

    //stop over details
    @SerializedName("durationhours")
    private double mDurationHours;
    @SerializedName("durationminutes")
    private double mDurationMinutes;
    @SerializedName("cityname")
    private String mCityName;
    @SerializedName("airportname")
    private String mAirportName;
    @SerializedName("airportcode")
    private String mAirportCode;
    @SerializedName("arrivaltime")
    private String mArrivalTime;

    @SerializedName("originairportcoordinates")
    private AirportCoordinatesVO originairportcoordinates = new AirportCoordinatesVO();

    @SerializedName("destinationairportcoordinates")
    private AirportCoordinatesVO destinationairportcoordinates = new AirportCoordinatesVO();

    @SerializedName("airportcoordinates")
    private AirportCoordinatesVO airportcoordinates = new AirportCoordinatesVO();

    @SerializedName("availablefareclasses")
    private ArrayList<String> mAvailablefareclasses;

    @SerializedName("availablefarepreferences")
    private ArrayList<String> mAvailablefarepreferences;

    @SerializedName("availablemealpreferences")
    private ArrayList<String> mAvailablemealpreferences;

    @SerializedName("availablebaggagepreferences")
    private ArrayList<String> mAvailablebaggagepreferences;

//    @SerializedName("availableseatnumbers")
//    private ArrayList<String> mAvailableseatnumbers;

    public double getmNormalizedDuration() {
        return mNormalizedDuration;
    }


    public String getmType() {
        return mType;
    }

    public String getmOrigin() {
        return mOrigin;
    }

    public void setmOrigin(String mOrigin) {
        this.mOrigin = mOrigin;
    }

    public String getmDestination() {
        return mDestination;
    }

    public void setmDestination(String mDestination) {
        this.mDestination = mDestination;
    }

    public String getmOriginCityName() {
        return mOriginCityName;
    }

    public void setmOriginCityName(String mOriginCityName) {
        this.mOriginCityName = mOriginCityName;
    }

    public String getmDestinationCityName() {
        return mDestinationCityName;
    }

    public void setmDestinationCityName(String mDestinationCityName) {
        this.mDestinationCityName = mDestinationCityName;
    }

    public String getmCarrierName() {
        return mCarrierName;
    }

    public void setmCarrierName(String mCarrierName) {
        this.mCarrierName = mCarrierName;
    }

    public String getmFlightNumber() {
        return mFlightNumber;
    }

    public void setmFlightNumber(String mFlightNumber) {
        this.mFlightNumber = mFlightNumber;
    }

    public String getmCarrierCode() {
        return mCarrierCode;
    }

    public void setmCarrierCode(String mCarrierCode) {
        this.mCarrierCode = mCarrierCode;
    }

    public String getmDepartureTime() {
        return mDepartureTime;
    }

    public void setmDepartureTime(String mDepartureTime) {
        this.mDepartureTime = mDepartureTime;
    }

    public String getmFareClass() {
        return mFareClass;
    }

    public void setmFareClass(String mFareClass) {
        this.mFareClass = mFareClass;
    }

    public String getmCityName() {
        return mCityName;
    }

    public void setmCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    public double getmDurationHours() {
        return mDurationHours;
    }

    public void setmDurationHours(double mDurationHours) {
        this.mDurationHours = mDurationHours;
    }

    public double getmDurationMinutes() {
        return mDurationMinutes;
    }

    public void setmDurationMinutes(double mDurationMinutes) {
        this.mDurationMinutes = mDurationMinutes;
    }

    public String getmParentguid() {
        return mParentguid;
    }

    public void setmParentguid(String mParentguid) {
        this.mParentguid = mParentguid;
    }

    public String getmGuid() {
        return mGuid;
    }

    public void setmGuid(String mGuid) {
        this.mGuid = mGuid;
    }
}
