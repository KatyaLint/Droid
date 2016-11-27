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
}
