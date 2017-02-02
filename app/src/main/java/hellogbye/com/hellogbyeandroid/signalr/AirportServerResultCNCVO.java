package hellogbye.com.hellogbyeandroid.signalr;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.models.vo.airports.ResponsesVO;


/**
 * Created by nyawka on 2/7/16.
 */
public class AirportServerResultCNCVO {



    @SerializedName("highlightdata")
    private HighlightdataVO highlightdataVO;



    @SerializedName("originalquery")
    private
    String mOriginalQuery;

    @SerializedName("solutionid")
    private
    String mSolutionId;

    public String getmOriginalQuery() {
        return mOriginalQuery;
    }

    public void setmOriginalQuery(String mOriginalQuery) {
        this.mOriginalQuery = mOriginalQuery;
    }

    public String getmSolutionId() {
        return mSolutionId;
    }

    public void setmSolutionId(String mSolutionId) {
        this.mSolutionId = mSolutionId;
    }


    public HighlightdataVO getHighlightdataVO() {
        return highlightdataVO;
    }

    public void setHighlightdataVO(HighlightdataVO highlightdataVO) {
        this.highlightdataVO = highlightdataVO;
    }
}
