package hellogbye.com.hellogbyeandroid.models.vo.statics;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

import hellogbye.com.hellogbyeandroid.models.CountryItemVO;


public class BookingRequestVO {

    @SerializedName("titles")
    private ArrayList<String> titles = new ArrayList<String>();

    @SerializedName("underagedtitles")
    private ArrayList<String> underagedtitles  = new ArrayList<String>();

    @SerializedName("cardtypes")
    private HashMap<String,String> cardtypes;

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

    public void sortCountryItems(){
        if(getCountries() == null){
            Log.e("BookingRequestVO","sortCountryItems getCountries is null");
            return;
        }
        ArrayList<CountryItemVO> firstItems = new ArrayList<>();
        for (CountryItemVO countryItemVO : getCountries()){
            if(countryItemVO.getCode().equals("CA") ||countryItemVO.getCode().equals("US")){
                firstItems.add(countryItemVO);
            }
        }
        getCountries().removeAll(firstItems);
        getCountries().addAll(0,firstItems);

    }


    @Override
    public String toString() {
        return "BookingRequestVO{" +
                "titles=" + getTitles() +
                ", countries=" + getCountries() +
                '}';
    }

    public ArrayList<String> getUnderagedtitles() {
        return underagedtitles;
    }

    public void setUnderagedtitles(ArrayList<String> underagedtitles) {
        this.underagedtitles = underagedtitles;
    }


}
