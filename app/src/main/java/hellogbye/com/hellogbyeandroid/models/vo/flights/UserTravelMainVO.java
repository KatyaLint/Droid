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
    public String getmTotalPrice() {
        return mTotalPrice;
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
}
