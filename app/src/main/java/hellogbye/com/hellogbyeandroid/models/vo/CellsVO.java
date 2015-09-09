package hellogbye.com.hellogbyeandroid.models.vo;

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
}
