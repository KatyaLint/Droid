package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 9/9/15.
 */
public class NodesVO
{
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
    private int mNormalizedDuration;





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
    private int mStarRating;
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


}
