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

    public SettingsValuesVO(String id, String name, String description, String rank) {
        this.mID = id;
        this.mName = name;
        this.mDescription = description;
        this.mRank = rank;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getmRank() {
        return mRank;
    }

    public void setmRank(String mRank) {
        this.mRank = mRank;
    }
}
