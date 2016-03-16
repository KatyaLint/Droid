package hellogbye.com.hellogbyeandroid.models.vo.statics;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.models.CountryItemVO;


public class BookingRequestVO {

    @SerializedName("titles")
    private ArrayList<String> titles;

    @SerializedName("countries")
    private ArrayList<CountryItemVO> countries;


//    @SerializedName("cardtypes")
//    private ArrayList<String> cardtypes;

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<CountryItemVO> getCountries() {
        return countries;
    }

    public void setCountries(ArrayList<CountryItemVO> countries) {
        this.countries = countries;
    }
}
