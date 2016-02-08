package hellogbye.com.hellogbyeandroid.models.vo.airports;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 2/7/16.
 */
public class ResponsesVO {

    @SerializedName("type")
    private String type;

    @SerializedName("value")
    private String value;

    @SerializedName("positions")
    private ArrayList<String> positionss;



    @SerializedName("results")
    private ArrayList<AirportResultsVO> results = new ArrayList<AirportResultsVO>();

    public ArrayList<AirportResultsVO> getResults() {
        return results;
    }

    public void setResults(ArrayList<AirportResultsVO> results) {
        this.results = results;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

  }
