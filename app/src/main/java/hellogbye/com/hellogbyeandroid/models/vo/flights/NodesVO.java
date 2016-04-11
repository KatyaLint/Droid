package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by nyawka on 9/9/15.
 */
public class NodesVO implements Comparable<NodesVO>, Cloneable
{

    private String userName;
    private String accountID;
    private boolean isEmpty = false;


    public NodesVO getClone(){
        try { // call clone in Object.
        return (NodesVO) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println (" Cloning not allowed. " );
            return this;
        }
    }

    public String getDateOfCell() {
        return dateOfCell;
    }

    public void setDateOfCell(String dateOfCell) {
            this.dateOfCell = dateOfCell;
    }

    private String dateOfCell = "";

    @SerializedName("totalpenalty")
    private double totalpenalty;

    @SerializedName("skip")
    private boolean mSkip;

    //-------Flight-----------------------
    @SerializedName("legs")
    private ArrayList<LegsVO> legs = new ArrayList<LegsVO>();

    @SerializedName("flighttime")
    private String mFlightTime;
    @SerializedName("departure")
    private String mDeparture;
    @SerializedName("departuretime")
    private String mDepartureTime;
    @SerializedName("arrival")
    private String mArrival;
    @SerializedName("arriveltime")
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

    @SerializedName("travelsector")
    private String mTravelsector;
    @SerializedName("primaryguid")
    private String mPrimaryguid;
    @SerializedName("normalizedduration")
    private double mNormalizedDuration;





    //-------Hotel-----------------------
    @SerializedName("hotelcode")
    private String mHotelCode;
    @SerializedName("cityname")
    private String mCityName;
    @SerializedName("hotelname")
    private String mHotelName;
    @SerializedName("hotelchain")
    private String mHotelChain;
    @SerializedName("starrating")
    private float mStarRating;
    @SerializedName("shortdescription")
    private String mShortDescription;
    @SerializedName("locationdescription")
    private String mLocationDescription;
    @SerializedName("postalcode")
    private String mPostalCode;
    @SerializedName("latitude")
    private double mLatitude;
    @SerializedName("longitude")
    private double mLongitude;
    @SerializedName("address1")
    private String mAddress1;
    @SerializedName("address2")
    private String mAddress2;
    @SerializedName("address3")
    private String mAddress3;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("image")
    private String mImage;
    @SerializedName("allimages")
    private ArrayList<AllImagesVO> allImagesVOs= new ArrayList<AllImagesVO>();
    @SerializedName("minimumamount")
    private double mMinimumAmount;
    @SerializedName("maximumamount")
    private double mMaximumAmount;
    @SerializedName("checkin")
    private String mCheckIn;
    @SerializedName("checkout")
    private String mCheckOut;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("stateprovince")
    private String mStateProvince;
    @SerializedName("defaultroomguid")
    private String mDefaultRoomGuid;
    @SerializedName("rooms")
    private ArrayList<RoomsVO> roomsVOs = new ArrayList<RoomsVO>();
    @SerializedName("hasalternative")
    private boolean mHasAlternative;

    //-------Common----------------------
    @SerializedName("paymentprocessingstate")
    private String mPaymentProcessingState;
    @SerializedName("paxguid")
    private String mPaxguid;
    @SerializedName("type")
    private String mType;
    @SerializedName("dateorder")
    private String mDateOrder;
    @SerializedName("guid")
    private String mGuid;
    @SerializedName("rank")
    private double mRank;
    @SerializedName("isalternative")
    private boolean mIsAlternative;
    @SerializedName("amenities")
    private String mAmenities;


    public float getmStarRating() {
        return mStarRating;
    }

    public void setmStarRating(float mStarRating) {
        this.mStarRating = mStarRating;
    }

    public String getmHotelName() {
        return mHotelName;
    }

    public void setmHotelName(String mHotelName) {
        this.mHotelName = mHotelName;
    }

    public String getmShortDescription() {
        return mShortDescription;
    }

    public void setmShortDescription(String mShortDescription) {
        this.mShortDescription = mShortDescription;
    }

    public String getmLocationDescription() {
        return mLocationDescription;
    }

    public void setmLocationDescription(String mLocationDescription) {
        this.mLocationDescription = mLocationDescription;
    }

    public String getmPostalCode() {
        return mPostalCode;
    }

    public void setmPostalCode(String mPostalCode) {
        this.mPostalCode = mPostalCode;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public ArrayList<AllImagesVO> getAllImagesVOs() {
        return allImagesVOs;
    }

    public void setAllImagesVOs(ArrayList<AllImagesVO> allImagesVOs) {
        this.allImagesVOs = allImagesVOs;
    }

    public String getmAddress1() {
        return mAddress1;
    }

    public void setmAddress1(String mAddress1) {
        this.mAddress1 = mAddress1;
    }

    public String getmAddress2() {
        return mAddress2;
    }

    public void setmAddress2(String mAddress2) {
        this.mAddress2 = mAddress2;
    }

    public String getmAddress3() {
        return mAddress3;
    }

    public void setmAddress3(String mAddress3) {
        this.mAddress3 = mAddress3;
    }

    public double getmMinimumAmount() {
        return mMinimumAmount;
    }

    public void setmMinimumAmount(double mMinimumAmount) {
        this.mMinimumAmount = mMinimumAmount;
    }

    public double getmMaximumAmount() {
        return mMaximumAmount;
    }

    public void setmMaximumAmount(double mMaximumAmount) {
        this.mMaximumAmount = mMaximumAmount;
    }

    public String getmCheckIn() {
        return mCheckIn;
    }

    public void setmCheckIn(String mCheckIn) {
        this.mCheckIn = mCheckIn;
    }

    public String getmCheckOut() {
        return mCheckOut;
    }

    public void setmCheckOut(String mCheckOut) {
        this.mCheckOut = mCheckOut;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmStateProvince() {
        return mStateProvince;
    }

    public void setmStateProvince(String mStateProvince) {
        this.mStateProvince = mStateProvince;
    }

    public String getmDefaultRoomGuid() {
        return mDefaultRoomGuid;
    }

    public void setmDefaultRoomGuid(String mDefaultRoomGuid) {
        this.mDefaultRoomGuid = mDefaultRoomGuid;
    }

    public boolean ismHasAlternative() {
        return mHasAlternative;
    }

    public void setmHasAlternative(boolean mHasAlternative) {
        this.mHasAlternative = mHasAlternative;
    }


    public ArrayList<LegsVO> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<LegsVO> legs) {
        this.legs = legs;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
    public String getmAmenities() {
        return mAmenities;
    }

    public void setmAmenities(String mAmenities) {
        this.mAmenities = mAmenities;

    }


    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
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

    public String getmDestination() {
        return mDestination;
    }

    public void setmDestination(String mDestination) {
        this.mDestination = mDestination;
    }

    public String getmFlightTime() {
        return mFlightTime;
    }

    public void setmFlightTime(String mFlightTime) {
        this.mFlightTime = mFlightTime;
    }

    public String getmOperatorName() {
        return mOperatorName;
    }

    public void setmOperatorName(String mOperatorName) {
        this.mOperatorName = mOperatorName;
    }


    public String getmCityName() {
        return mCityName;
    }

    public void setmCityName(String mCityName) {
        this.mCityName = mCityName;
    }

    public String getmDepartureTime() {
        return mDepartureTime;
    }

    public void setmDepartureTime(String mDepartureTime) {
        this.mDepartureTime = mDepartureTime;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public String getmDeparture() {
        return mDeparture;
    }

    public void setmDeparture(String mDeparture) {
        this.mDeparture = mDeparture;
    }

    @Override
    public int compareTo(NodesVO nodesVO) {
        return getDateOfCell().compareTo(nodesVO.getDateOfCell());
    }

    public String getmHotelCode() {
        return mHotelCode;
    }

    public void setmHotelCode(String mHotelCode) {
        this.mHotelCode = mHotelCode;
    }

    public ArrayList<RoomsVO> getRoomsVOs() {
        return roomsVOs;
    }

    public void setRoomsVOs(ArrayList<RoomsVO> roomsVOs) {
        this.roomsVOs = roomsVOs;
    }


    public String getmGuid() {
        return mGuid;
    }

    public void setmGuid(String mGuid) {
        this.mGuid = mGuid;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public boolean ismSkip() {
        return mSkip;
    }

    public void setmSkip(boolean mSkip) {
        this.mSkip = mSkip;
    }

    public String getmArrivalTime() {
        return mArrivalTime;
    }

    public void setmArrivalTime(String mArrivalTime) {
        this.mArrivalTime = mArrivalTime;
    }

    public String getmTravelTime() {
        return mTravelTime;
    }

    public void setmTravelTime(String mTravelTime) {
        this.mTravelTime = mTravelTime;
    }

    public String getmPrimaryguid() {
        return mPrimaryguid;
    }

    public void setmPrimaryguid(String mPrimaryguid) {
        this.mPrimaryguid = mPrimaryguid;
    }

    public String getmOrigin() {
        return mOrigin;
    }

    public void setmOrigin(String mOrigin) {
        this.mOrigin = mOrigin;
    }

    public String getmEquipment() {
        return mEquipment;
    }

    public void setmEquipment(String mEquipment) {
        this.mEquipment = mEquipment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getmArrival() {
        return mArrival;
    }

    public void setmArrival(String mArrival) {
        this.mArrival = mArrival;
    }


}
