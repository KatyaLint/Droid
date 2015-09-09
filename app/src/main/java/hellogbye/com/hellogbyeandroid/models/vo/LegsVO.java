package hellogbye.com.hellogbyeandroid.models.vo;

import com.google.gson.annotations.SerializedName;

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

    @SerializedName("durationhours")
    private double mDurationHours;
    @SerializedName("durationmminutes")
    private double mDurationMinutes;
    @SerializedName("cityname")
    private String mCityName;
    @SerializedName("airportname")
    private String mAirportName;
    @SerializedName("airportcode")
    private String mAirportCode;
    @SerializedName("arrivaltime")
    private String mArrivalTime;

}
