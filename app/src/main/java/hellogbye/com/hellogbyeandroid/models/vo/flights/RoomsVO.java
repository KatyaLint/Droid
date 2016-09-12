package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 9/9/15.
 */
public class RoomsVO {
    @SerializedName("primaryguid")
    private String mPrimaryguid;
    @SerializedName("roomtype")
    private String mRoomType;
    @SerializedName("code")
    private String mCode;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("smoking")
    private String mSmoking;

    @SerializedName("bedtypes")
    private ArrayList<String> mBedTypesVOs = new ArrayList<String>();


    @SerializedName("cost")
    private double mCost;
    @SerializedName("nightlyrate")
    private ArrayList<Double> mNightLyrateVOs = new ArrayList<Double>();

    @SerializedName("images")
    private ArrayList<String> mImages = new ArrayList<String>();

    @SerializedName("averagerate")
    private double mAverageRate;
    @SerializedName("surchargertotal")
    private double mSurchargerTotal;

    @SerializedName("isalternative")
    private boolean mIsAlternative;
    @SerializedName("hasalternative")
    private boolean mHasalternative;


    @SerializedName("type")
    private String mType;
    @SerializedName("dateorder")
    private String mDateOrder;
    @SerializedName("guid")
    private String mGuid;
    @SerializedName("rank")
    private double mRank;
    @SerializedName("checkin")
    private String mCheckIn;
    @SerializedName("checkout")
    private String mCheckOut;
    @SerializedName("totalbeforetax")
    private double mTotalBeforeTax;



    public double getmCost() {
        return mCost;
    }

    public void setmCost(double mCost) {
        this.mCost = mCost;
    }

    public String getmRoomType() {
        return mRoomType;
    }

    public void setmRoomType(String mRoomType) {
        this.mRoomType = mRoomType;
    }

    public String getmPrimaryguid() {
        return mPrimaryguid;
    }

    public String getmCode() {
        return mCode;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmSmoking() {
        return mSmoking;
    }

    public ArrayList<String> getmBedTypesVOs() {
        return mBedTypesVOs;
    }

    public ArrayList<Double> getmNightLyrateVOs() {
        return mNightLyrateVOs;
    }

    public ArrayList<String> getmImages() {
        return mImages;
    }

    public double getmAverageRate() {
        return mAverageRate;
    }

    public double getmSurchargerTotal() {
        return mSurchargerTotal;
    }

    public boolean ismIsAlternative() {
        return mIsAlternative;
    }

    public boolean ismHasalternative() {
        return mHasalternative;
    }

    public String getmType() {
        return mType;
    }

    public String getmDateOrder() {
        return mDateOrder;
    }

    public String getmGuid() {
        return mGuid;
    }

    public double getmRank() {
        return mRank;
    }

    public String getmCheckIn() {
        return mCheckIn;
    }

    public String getmCheckOut() {
        return mCheckOut;
    }

    public double getmTotalBeforeTax() {
        return mTotalBeforeTax;
    }
}
