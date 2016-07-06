package hellogbye.com.hellogbyeandroid.models.vo.airports;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 2/7/16.
 */
public class AirportResultsVO {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("airportname")
    private String airportname;

    @SerializedName("citycode")
    private String citycode;

    @SerializedName("cityname")
    private String cityname;

    @SerializedName("state")
    private String state;

    @SerializedName("countrycode")
    private String countrycode;

    @SerializedName("country")
    private String country;

    @SerializedName("regioncode")
    private String regioncode;

    @SerializedName("regionname")
    private String regionname;

    @SerializedName("group")
    private String groupd;

    @SerializedName("isdefault")
    private boolean isdefault;

    public String getAirportname() {
        return airportname;
    }

    public void setAirportname(String airportname) {
        this.airportname = airportname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isdefault() {
        return isdefault;
    }

    public void setIsdefault(boolean isdefault) {
        this.isdefault = isdefault;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
