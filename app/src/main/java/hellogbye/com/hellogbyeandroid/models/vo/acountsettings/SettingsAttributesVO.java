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
}
