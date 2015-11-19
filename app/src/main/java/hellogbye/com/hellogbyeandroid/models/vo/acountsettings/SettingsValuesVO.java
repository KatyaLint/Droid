package hellogbye.com.hellogbyeandroid.models.vo.acountsettings;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 11/5/15.
 */
public class SettingsValuesVO {

    @SerializedName("rank")
    private String mRank;
    @SerializedName("id")
    private String mID;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
