package hellogbye.com.hellogbyeandroid.signalr;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.models.vo.airports.ResponsesVO;

/**
 * Created by nyawka on 1/30/17.
 */

public class HighlightdataVO {

    @SerializedName("responses")
    private ArrayList<ResponsesVO> responses = new ArrayList<ResponsesVO>();

    public ArrayList<ResponsesVO> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<ResponsesVO> responses) {
        this.responses = responses;
    }

}
