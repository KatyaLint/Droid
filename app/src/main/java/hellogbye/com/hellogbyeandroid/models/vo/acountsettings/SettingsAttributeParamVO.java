package hellogbye.com.hellogbyeandroid.models.vo.acountsettings;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 11/5/15.
 */
public class SettingsAttributeParamVO {


    private boolean isChecked = false;

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("rank")
    private double mRank;

    @SerializedName("attributes")
    private ArrayList<SettingsAttributesVO> attributesVOs = new ArrayList<SettingsAttributesVO>();

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public double getmRank() {
        return mRank;
    }

    public void setmRank(double mRank) {
        this.mRank = mRank;
    }

    public ArrayList<SettingsAttributesVO> getAttributesVOs() {
        return attributesVOs;
    }

    public void setAttributesVOs(ArrayList<SettingsAttributesVO> attributesVOs) {
        this.attributesVOs = attributesVOs;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
