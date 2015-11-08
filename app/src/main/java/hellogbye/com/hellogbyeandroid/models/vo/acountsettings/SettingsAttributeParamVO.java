package hellogbye.com.hellogbyeandroid.models.vo.acountsettings;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by nyawka on 11/5/15.
 */
public class SettingsAttributeParamVO {
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
}
