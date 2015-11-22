package hellogbye.com.hellogbyeandroid.models.vo.acountsettings;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 11/5/15.
 */
public class SettingsAttributesVO {

    @SerializedName("values")
    private ArrayList<SettingsValuesVO> attributesVOs = new ArrayList<SettingsValuesVO>();
    @SerializedName("rank")
    private double mRank;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("type")
    private String mType;
    @SerializedName("description")
    private String mDescription;

    public ArrayList<SettingsValuesVO> getAttributesVOs() {
        return attributesVOs;
    }

    public void setAttributesVOs(ArrayList<SettingsValuesVO> attributesVOs) {
        this.attributesVOs = attributesVOs;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}
