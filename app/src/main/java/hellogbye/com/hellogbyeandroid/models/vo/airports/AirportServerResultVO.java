package hellogbye.com.hellogbyeandroid.models.vo.airports;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by nyawka on 2/7/16.
 */
public class AirportServerResultVO {
    @SerializedName("responses")
    private ArrayList<ResponsesVO> responses = new ArrayList<ResponsesVO>();


    public ArrayList<ResponsesVO> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<ResponsesVO> responses) {
        this.responses = responses;
    }
}
