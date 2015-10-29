package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 9/9/15.
 */
public class CellsVO {
    @SerializedName("date")
    private String mDate;





    @SerializedName("nodes")
    private ArrayList<NodesVO> mNodes = new ArrayList<NodesVO>();

    public ArrayList<NodesVO> getmNodes() {
        return mNodes;
    }

    public void setmNodes(ArrayList<NodesVO> mNodes) {
        this.mNodes = mNodes;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }
}
