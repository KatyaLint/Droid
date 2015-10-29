package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nyawka on 9/9/15.
 */
public class PassengersVO {
    @SerializedName("paxguid")
    private String mPaxguid;
    @SerializedName("accountid")
    private String mAccountID;
    @SerializedName("name")
    private String mName;
    @SerializedName("displayname")
    private String mDisplayName;
    @SerializedName("totalprice")
    private double mTotalPrice;
    @SerializedName("totalflightprice")
    private double mTotalFlightPrice;
    @SerializedName("totalhotelprice")
    private double mTotalHotelPrice;
    @SerializedName("paxstatus")
    private String mPaxStatus;

    @SerializedName("cells")
    private ArrayList<CellsVO> mCells = new ArrayList<CellsVO>();


    public ArrayList<CellsVO> getmCells() {
        return mCells;
    }

    public void setmCells(ArrayList<CellsVO> mCells) {
        this.mCells = mCells;
    }

    public String getmPaxguid() {
        return mPaxguid;
    }

    public void setmPaxguid(String mPaxguid) {
        this.mPaxguid = mPaxguid;
    }

    public String getmDisplayName() {
        return mDisplayName;
    }

    public void setmDisplayName(String mDisplayName) {
        this.mDisplayName = mDisplayName;
    }




    public ArrayList<NodesVO> passengerNodes = new ArrayList<>();

    public HashMap<String,ArrayList<NodesVO> > hashMap = new HashMap<>();

    public void editHashMap(String date,ArrayList<NodesVO> nodes){
        hashMap.put(date,nodes);
        passengerNodes.addAll(nodes);
    }

    public void addToPassenger(NodesVO node){
        passengerNodes.add(node);
    }

    public HashMap<String,ArrayList<NodesVO> > getHashMap(){
        return hashMap;
    }

    public ArrayList<NodesVO> getPassengerNodes(){
        return passengerNodes;
    }



}
