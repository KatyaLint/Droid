package hellogbye.com.hellogbyeandroid.models.vo.acountsettings;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 11/4/15.
 */
public class AcountDefaultSettingsVO {

    private String rank;

    @SerializedName("id")
    private String mId;

    @SerializedName("profilename")
    private String mProfileName;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmProfileName() {
        return mProfileName;
    }

    public void setmProfileName(String mProfileName) {
        this.mProfileName = mProfileName;
    }


    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
