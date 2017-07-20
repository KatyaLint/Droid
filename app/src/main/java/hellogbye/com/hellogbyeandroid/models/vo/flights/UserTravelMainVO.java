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

    @SerializedName("hasbookedversion")
    private boolean mHasbookedversion;



    //Common
    @SerializedName("isfavorite")
    private boolean mIsFavorite;
    @SerializedName("totalprice")
    private String mTotalPrice;

    @SerializedName("totalpenalty")
    private double totalpenalty;
    //OLD


    private String[] location;

    @SerializedName("passengers")
    private ArrayList<PassengersVO> passengerses = new ArrayList<PassengersVO>();


    @SerializedName("items")
    private Map<String,NodesVO> items = new HashMap<String,NodesVO>();

    @SerializedName("conversation")
    private ArrayList<ConversationVO> conversation = new ArrayList<ConversationVO>();

    @SerializedName("currency")
    private String mCurrency;



    //items
    //conversation


    public String getmSolutionID() {
        return mSolutionID;
    }

    public ArrayList<PassengersVO> getPassengerses() {
        return passengerses;
    }
    public Map<String, NodesVO> getItems() {
        return items;
    }
    public void setItems(Map<String, NodesVO> items) {
        this.items = items;
    }

    public String getmSolutionName() {
        return mSolutionName;
    }

    public void setmSolutionName(String mSolutionName) {
        this.mSolutionName = mSolutionName;
    }

    public boolean ismIsFavorite() {
        return mIsFavorite;
    }

    public void setmIsFavorite(boolean mIsFavorite) {
        this.mIsFavorite = mIsFavorite;
    }

    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] location) {
        this.location = location;
    }

    public ArrayList<ConversationVO> getConversation() {
        return conversation;
    }

    public void setConversation(ArrayList<ConversationVO> conversation) {
        this.conversation = conversation;
    }

    public String getmTotalPrice() {
        return mTotalPrice;
    }

    public void setmTotalPrice(String mTotalPrice) {
        this.mTotalPrice = mTotalPrice;
    }

    public String getmCurrency() {
        return mCurrency;
    }

    public void setmCurrency(String mCurrency) {
        this.mCurrency = mCurrency;
    }

    public boolean getmHasbookedversion() {
        return mHasbookedversion;
    }

    public void setmHasbookedversion(boolean mHasbookedversion) {
        this.mHasbookedversion = mHasbookedversion;
    }
}
