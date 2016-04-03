package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arisprung on 11/24/15.
 */
public class ProvincesItem {

    @SerializedName("provincecode")
    private String provincecode;

    @SerializedName("countrycode")
    private String countrycode;

    @SerializedName("provincename")
    private String provincename;


    public String getProvincename() {
        return provincename;
    }

    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    public String getCountrycode() {
        return countrycode;
    }

    public void setCountrycode(String countrycode) {
        this.countrycode = countrycode;
    }

    public String getProvincecode() {
        return provincecode;
    }

    public void setProvincecode(String provincecode) {
        this.provincecode = provincecode;
    }
}
