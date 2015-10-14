package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 9/9/15.
 */
public class PassengersVO {
    @SerializedName("paxguid")
    private String mPaxguid;
    @SerializedName("accountid")
    private String mAccountID;
    @SerializedName("name")
    private String mName;
    @SerializedName("displayname")
    private String mDisplayName;
    @SerializedName("totalprice")
    private double mTotalPrice;
    @SerializedName("totalflightprice")
    private double mTotalFlightPrice;
    @SerializedName("totalhotelprice")
    private double mTotalHotelPrice;
    @SerializedName("paxstatus")
    private String mPaxStatus;

    @SerializedName("cells")
    private ArrayList<CellsVO> mCells = new ArrayList<CellsVO>();


    public ArrayList<CellsVO> getmCells() {
        return mCells;
    }

    public void setmCells(ArrayList<CellsVO> mCells) {
        this.mCells = mCells;
    }
}
