package hellogbye.com.hellogbyeandroid.models.vo.acountsettings;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nyawka on 11/5/15.
 */
public class SettingsAttributesVO {

    @SerializedName("values")
    private List<SettingsValuesVO> attributesVOs = new ArrayList<SettingsValuesVO>();
    @SerializedName("rank")
    private String mRank;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("type")
    private String mType;
    @SerializedName("description")
    private String mDescription;

    public List<SettingsValuesVO> getAttributesVOs() {
        return attributesVOs;
    }

    public void setAttributesVOs(List<SettingsValuesVO> attributesVOs) {
        this.attributesVOs = attributesVOs;
    }


//    SettingsAttributesVO(String name,String description,String id,String rank){
//        this.mName = name;
//        this.mDescription = description;
//        this.mId = id;
//        this.mRank = rank;
//    }


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

    public String getmRank() {
        return mRank;
    }

    public void setmRank(String mRank) {
        this.mRank = mRank;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
