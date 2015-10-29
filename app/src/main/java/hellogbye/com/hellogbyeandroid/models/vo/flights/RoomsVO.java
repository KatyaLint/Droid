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
    private ArrayList<BedTypesVO> mBedTypesVOs = new ArrayList<BedTypesVO>();
    @SerializedName("cost")
    private double mCost;
    @SerializedName("nightlyrate")
    private ArrayList<Double> mNightLyrateVOs = new ArrayList<Double>();

    @SerializedName("averagerate")
    private double mAverageRate;
    @SerializedName("surchargertotal")
    private double mSurchargerTotal;
    @SerializedName("isalternative")
    private boolean mIsAlternative;
    @SerializedName("type")
    private String mType;
    @SerializedName("dateorder")
    private String mDateOrder;
    @SerializedName("guid")
    private String mGuid;
    @SerializedName("rank")
    private int mRank;
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
}
