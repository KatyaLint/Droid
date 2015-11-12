package hellogbye.com.hellogbyeandroid.models.vo.alternativeflights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;

/**
 * Created by nyawka on 10/8/15.
 */
public class AlternativeFlightsVO {
    @SerializedName("legs")
    private ArrayList<LegsVO> legs = new ArrayList<LegsVO>();

    @SerializedName("isalternative")
    private boolean mIsAlternative;
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
    @SerializedName("origin")
    private String mOrigin;
    @SerializedName("destination")
    private String mDestination;
    @SerializedName("origincityname")
    private String mOriginCityName;
    @SerializedName("destinationcityname")
    private String mDestinationCityName;
    @SerializedName("originairportname")
    private String mOriginAirportName;
    @SerializedName("destinationairportname")
    private String mDestinationAirportName;
    @SerializedName("operator")
    private String mOperator;
    @SerializedName("operatorname")
    private String mOperatorName;
    @SerializedName("equipment")
    private String mEquipment;
    @SerializedName("fareclass")
    private String mFareClass;
    @SerializedName("farepreference")
    private String mFarePreference;
    @SerializedName("cost")
    private double cost;
    @SerializedName("tax")
    private double mTax;
    @SerializedName("traveltime")
    private String mTravelTime;
    @SerializedName("paxguid")
    private String mPaxguid;
    @SerializedName("travelsector")
    private String mTravelsector;
    @SerializedName("primaryguid")
    private String mPrimaryguid;
    @SerializedName("normalizedduration")
    private double mNormalizedDuration;
    @SerializedName("type")
    private String mType;

    @SerializedName("dateorder")
    private String mDateOrder;
    @SerializedName("guid")
    private String mGuid;
    @SerializedName("rank")
    private double mRank;
    @SerializedName("paymentprocessingstate")
    private String mPaymentProcessingState;

    public ArrayList<LegsVO> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<LegsVO> legs) {
        this.legs = legs;
    }

    public String getmDeparture() {
        return mDeparture;
    }

    public void setmDeparture(String mDeparture) {
        this.mDeparture = mDeparture;
    }

    public String getmFlightTime() {
        return mFlightTime;
    }

    public void setmFlightTime(String mFlightTime) {
        this.mFlightTime = mFlightTime;
    }

    public String getmDepartureTime() {
        return mDepartureTime;
    }

    public void setmDepartureTime(String mDepartureTime) {
        this.mDepartureTime = mDepartureTime;
    }

    public String getmArrivalTime() {
        return mArrivalTime;
    }

    public void setmArrivalTime(String mArrivalTime) {
        this.mArrivalTime = mArrivalTime;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getmTravelTime() {
        return mTravelTime;
    }

    public void setmTravelTime(String mTravelTime) {
        this.mTravelTime = mTravelTime;
    }

    public boolean ismIsAlternative() {
        return mIsAlternative;
    }

    public void setmIsAlternative(boolean mIsAlternative) {
        this.mIsAlternative = mIsAlternative;
    }

    public String getmOperatorName() {
        return mOperatorName;
    }

    public void setmOperatorName(String mOperatorName) {
        this.mOperatorName = mOperatorName;
    }

    public String getmOperator() {
        return mOperator;
    }

    public void setmOperator(String mOperator) {
        this.mOperator = mOperator;
    }

    public String getmGuid() {
        return mGuid;
    }

    public void setmGuid(String mGuid) {
        this.mGuid = mGuid;
    }
}
