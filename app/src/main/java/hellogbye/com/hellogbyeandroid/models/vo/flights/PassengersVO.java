package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by nyawka on 9/9/15.
 */
public class PassengersVO {
    @SerializedName("paxguid")
    private String mPaxguid;
    @SerializedName("name")
    private String mName;
    @SerializedName("totalprice")
    private double mTotalPrice = 0;
    @SerializedName("totalflightprice")
    private double mTotalFlightPrice = 0;
    @SerializedName("totalhotelprice")
    private double mTotalHotelPrice = 0;

    @SerializedName("totalpenalty")
    private double totalpenalty;

    @SerializedName("avatarurl")
    private String avatarurl;




    @SerializedName("itineraryitems")
    private ArrayList<String> mItineraryItems = new ArrayList<String>();
    private HashSet<String> mBookingItems = new HashSet<>();



    public String getmPaxguid() {
        return mPaxguid;
    }


    public ArrayList<NodesVO> passengerNodes = new ArrayList<>();

    public HashMap<String,ArrayList<NodesVO> > hashMap = new HashMap<>();



    public void setDateHashMap(String date,ArrayList<NodesVO> nodes){
        hashMap.put(date,nodes);

    }


    public void editHashMap(String date,ArrayList<NodesVO> nodes){
        hashMap.put(date,nodes);
        passengerNodes.addAll(nodes);
    }

    public void addToPassenger(NodesVO node){
        passengerNodes.add(node);
    }

    public void addToPassengerNodeVOS(ArrayList<NodesVO> node){
        passengerNodes.addAll(node);
    }


    public HashMap<String,ArrayList<NodesVO> > getHashMap(){
        return hashMap;
    }

    public ArrayList<NodesVO> getPassengerNodes(){
        return passengerNodes;
    }


    public void clearData(){
        hashMap.clear();
        passengerNodes.clear();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public double getmTotalPrice() {
        return mTotalPrice;
    }

    public void setmTotalPrice(double mTotalPrice) {
        this.mTotalPrice = mTotalPrice;
    }

    public double getmTotalFlightPrice() {
        return mTotalFlightPrice;
    }

    public void setmTotalFlightPrice(double mTotalFlightPrice) {
        this.mTotalFlightPrice = mTotalFlightPrice;
    }

    public double getmTotalHotelPrice() {
        return mTotalHotelPrice;
    }

    public HashSet<String> getmBookingItems() {
        return mBookingItems;
    }

    public void setmBookingItems(HashSet<String> mBookingItems) {
        this.mBookingItems = mBookingItems;
    }

    public void setmTotalHotelPrice(double mTotalHotelPrice) {
        this.mTotalHotelPrice = mTotalHotelPrice;
    }

    public ArrayList<String> getmItineraryItems() {
        return mItineraryItems;
    }

    public void setmItineraryItems(ArrayList<String> mItineraryItems) {
        this.mItineraryItems = mItineraryItems;
    }


}
