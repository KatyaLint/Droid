package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 9/9/15.
 */
public class UserTravelVO {
    @SerializedName("solutionid")
    private String mSolutionID;
    @SerializedName("isfavorite")
    private boolean mIsFavorite;
    @SerializedName("totalprice")
    private String mTotalPrice;
    @SerializedName("parsermessage")
    private String mParseMessage;
    @SerializedName("passengers")

    private ArrayList<PassengersVO> passengerses = new ArrayList<PassengersVO>();

    public String getmSolutionID() {
        return mSolutionID;
    }

    public void setmSolutionID(String mSolutionID) {
        this.mSolutionID = mSolutionID;
    }

    public boolean ismIsFavorite() {
        return mIsFavorite;
    }

    public void setmIsFavorite(boolean mIsFavorite) {
        this.mIsFavorite = mIsFavorite;
    }

    public String getmTotalPrice() {
        return mTotalPrice;
    }

    public void setmTotalPrice(String mTotalPrice) {
        this.mTotalPrice = mTotalPrice;
    }

    public String getmParseMessage() {
        return mParseMessage;
    }

    public void setmParseMessage(String mParseMessage) {
        this.mParseMessage = mParseMessage;
    }

    public ArrayList<PassengersVO> getPassengerses() {
        return passengerses;
    }

    public void setPassengerses(ArrayList<PassengersVO> passengerses) {
        this.passengerses = passengerses;
    }
}
