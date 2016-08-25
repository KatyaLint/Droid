package hellogbye.com.hellogbyeandroid.models.vo.companion;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;

/**
 * Created by nyawka on 8/24/16.
 */
public class CompanionsSearchItemsVO {
    @SerializedName("searchtype")
    private String mSearchType;
    @SerializedName("companions")
    private ArrayList<CompanionVO> mNodes = new ArrayList<CompanionVO>();

    public ArrayList<CompanionVO> getmNodes() {
        return mNodes;
    }

    public void setmNodes(ArrayList<CompanionVO> mNodes) {
        this.mNodes = mNodes;
    }
}
