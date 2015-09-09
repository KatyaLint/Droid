package hellogbye.com.hellogbyeandroid.models.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 9/7/15.
 */
public class AirplaneDataVO {
    @SerializedName("type")
    private String mAirplaneType;
    @SerializedName("fareclass")
    private String mFareClass;
    @SerializedName("farepreference")
    private String mFarePreference;
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
    @SerializedName("arrivaltime")
    private String mArrivalTime;
    @SerializedName("code")
    private String mCode;
    @SerializedName("origin")
    private String mOrigin;
    @SerializedName("destination")
    private String mDestination;
    @SerializedName("origincityname")
    private String mOriginCityName;
    @SerializedName("originairportname")
    private String mOriginAirportName;
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
    private long mSeq;
    @SerializedName("carrierbadgeurl")
    private String mCarrierBadgeUrl;
    @SerializedName("carrierlogourl")
    private String mCarrierLogoUrl;
    @SerializedName("normalizeddurration")
    private long mNormalizedDurration;


    @SerializedName("parentguid")
    private String mParentguid;
    @SerializedName("guid")
    private String mGuid;


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




    private IdVO mIdVO = new IdVO();


    public IdVO getmIdVO() {
        return mIdVO;
    }

    public void setmIdVO(IdVO mIdVO) {
        this.mIdVO = mIdVO;
    }
//
//    public MoneyVO getmMoneyVO() {
//        return mMoneyVO;
//    }
//
//    public void setmMoneyVO(MoneyVO mMoneyVO) {
//        this.mMoneyVO = mMoneyVO;
//    }

    public String getmAirplaneType() {
        return mAirplaneType;
    }

    public void setmAirplaneType(String mAirplaneType) {
        this.mAirplaneType = mAirplaneType;
    }

    public String getmFlightNumber() {
        return mFlightNumber;
    }

    public void setmFlightNumber(String mFlightNumber) {
        this.mFlightNumber = mFlightNumber;
    }

    public String getmFlightTime() {
        return mFlightTime;
    }

    public void setmFlightTime(String mFlightTime) {
        this.mFlightTime = mFlightTime;
    }

    public String getmDeparture() {
        return mDeparture;
    }

    public void setmDeparture(String mDeparture) {
        this.mDeparture = mDeparture;
    }

    public String getmDepartureTime() {
        return mDepartureTime;
    }

    public void setmDepartureTime(String mDepartureTime) {
        this.mDepartureTime = mDepartureTime;
    }

    public String getmArrival() {
        return mArrival;
    }

    public void setmArrival(String mArrival) {
        this.mArrival = mArrival;
    }

    public String getmArrivalTime() {
        return mArrivalTime;
    }

    public void setmArrivalTime(String mArrivalTime) {
        this.mArrivalTime = mArrivalTime;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
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

    public String getmOriginAirportName() {
        return mOriginAirportName;
    }

    public void setmOriginAirportName(String mOriginAirportName) {
        this.mOriginAirportName = mOriginAirportName;
    }

    public String getmDestinationAirportName() {
        return mDestinationAirportName;
    }

    public void setmDestinationAirportName(String mDestinationAirportName) {
        this.mDestinationAirportName = mDestinationAirportName;
    }

    public String getmCarrierCode() {
        return mCarrierCode;
    }

    public void setmCarrierCode(String mCarrierCode) {
        this.mCarrierCode = mCarrierCode;
    }

    public String getmCarrierName() {
        return mCarrierName;
    }

    public void setmCarrierName(String mCarrierName) {
        this.mCarrierName = mCarrierName;
    }

    public String getmEquipment() {
        return mEquipment;
    }

    public void setmEquipment(String mEquipment) {
        this.mEquipment = mEquipment;
    }

    public String getmPaxguid() {
        return mPaxguid;
    }

    public void setmPaxguid(String mPaxguid) {
        this.mPaxguid = mPaxguid;
    }

    public String getmRef() {
        return mRef;
    }

    public void setmRef(String mRef) {
        this.mRef = mRef;
    }

    public long getmSeq() {
        return mSeq;
    }

    public void setmSeq(long mSeq) {
        this.mSeq = mSeq;
    }

    public String getmCarrierBadgeUrl() {
        return mCarrierBadgeUrl;
    }

    public void setmCarrierBadgeUrl(String mCarrierBadgeUrl) {
        this.mCarrierBadgeUrl = mCarrierBadgeUrl;
    }

    public String getmCarrierLogoUrl() {
        return mCarrierLogoUrl;
    }

    public void setmCarrierLogoUrl(String mCarrierLogoUrl) {
        this.mCarrierLogoUrl = mCarrierLogoUrl;
    }

    public String getmFareClass() {
        return mFareClass;
    }

    public void setmFareClass(String mFareClass) {
        this.mFareClass = mFareClass;
    }

    public String getmFarePreference() {
        return mFarePreference;
    }

    public void setmFarePreference(String mFarePreference) {
        this.mFarePreference = mFarePreference;
    }

    public long getmNormalizedDurration() {
        return mNormalizedDurration;
    }

    public void setmNormalizedDurration(long mNormalizedDurration) {
        this.mNormalizedDurration = mNormalizedDurration;
    }
}
