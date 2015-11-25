package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by arisprung on 11/24/15.
 */
public class BookingRequest {

    @SerializedName("titles")
    private ArrayList<String> titles;

    @SerializedName("countries")
    private ArrayList<CountryItem> countries;




    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<CountryItem> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<CountryItem> countries) {
        this.countries = countries;
    }
}
