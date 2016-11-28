package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 11/22/16.
 */

public class FairclassPreferencesVO {

    @SerializedName("id")
    private
    String id;
    @SerializedName("fareclass")
    private
    String fareclass;
    @SerializedName("farepreference")
    private
    String farepreference;
    @SerializedName("cost")
    private
    double cost;
    @SerializedName("isalternative")
    private boolean isalternative;

    private String currencyType;

    private String solutionID;

    private String flightID;

    private String paxID;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFareclass() {
        return fareclass;
    }

    public void setFareclass(String fareclass) {
        this.fareclass = fareclass;
    }

    public String getFarepreference() {
        return farepreference;
    }

    public void setFarepreference(String farepreference) {
        this.farepreference = farepreference;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public boolean isalternative() {
        return isalternative;
    }

    public void setIsalternative(boolean isalternative) {
        this.isalternative = isalternative;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public String getSolutionID() {
        return solutionID;
    }

    public void setSolutionID(String solutionID) {
        this.solutionID = solutionID;
    }

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getPaxID() {
        return paxID;
    }

    public void setPaxID(String paxID) {
        this.paxID = paxID;
    }
}
