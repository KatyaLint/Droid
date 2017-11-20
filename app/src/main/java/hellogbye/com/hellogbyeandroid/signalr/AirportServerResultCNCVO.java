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

    @SerializedName("responses")
    private ArrayList<ResponsesVO> responses = new ArrayList<ResponsesVO>();

    @SerializedName("dontinitiatesearch")
    private boolean dontinitiatesearch;

    @SerializedName("nosearch")
    private boolean nosearch;

    @SerializedName("query")
    private String query;

    @SerializedName("itineraryid")
    private String itineraryid;

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

    public ArrayList<ResponsesVO> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<ResponsesVO> responses) {
        this.responses = responses;
    }

    public String getItineraryid() {
        return itineraryid;
    }

    public void setItineraryid(String itineraryid) {
        this.itineraryid = itineraryid;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isDontinitiatesearch() {
        return dontinitiatesearch;
    }

    public void setDontinitiatesearch(boolean dontinitiatesearch) {
        this.dontinitiatesearch = dontinitiatesearch;
    }
}
