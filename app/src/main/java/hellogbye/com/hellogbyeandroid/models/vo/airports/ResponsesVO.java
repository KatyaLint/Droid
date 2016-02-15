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

    @SerializedName("position")
    private PositionVO positionVO;
    //private ArrayList<PositionVO> position = new ArrayList<PositionVO>();
//    private ArrayList<String> positionss;



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

    public PositionVO getPositionVO() {
        return positionVO;
    }

    public void setPositionVO(PositionVO positionVO) {
        this.positionVO = positionVO;
    }
}
