package hellogbye.com.hellogbyeandroid.models.vo.airports;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 1/29/17.
 */

public class AiroportServerResultHighlightdata {

    @SerializedName("responses")
    private ArrayList<ResponsesVO> responses = new ArrayList<ResponsesVO>();

    @SerializedName("itineraryid")
    String mIternararyId;

}
