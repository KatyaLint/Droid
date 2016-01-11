package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nyawka on 9/9/15.
 */
public class UserTravelMainVO {
    @SerializedName("solutionid")
    private String mSolutionID;
    @SerializedName("startdatetime")
    private String mStartDateTime;
    @SerializedName("endtdatetime")
    private String mEndDateTime;
    @SerializedName("solutionname")
    private String mSolutionName;

    //Common
    @SerializedName("isfavorite")
    private boolean mIsFavorite;
    @SerializedName("totalprice")
    private String mTotalPrice;

    //OLD
    @SerializedName("parsermessage")
    private String mParseMessage;

    @SerializedName("passengers")
    private ArrayList<PassengersVO> passengerses = new ArrayList<PassengersVO>();


    @SerializedName("items")
    private Map<String,NodesVO> items = new HashMap<String,NodesVO>();

    @SerializedName("conversation")
    private ArrayList<ConversationVO> conversation = new ArrayList<ConversationVO>();


    //items
    //conversation


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

    public Map<String, NodesVO> getItems() {
        return items;
    }

    public void setItems(Map<String, NodesVO> items) {
        this.items = items;
    }
}
