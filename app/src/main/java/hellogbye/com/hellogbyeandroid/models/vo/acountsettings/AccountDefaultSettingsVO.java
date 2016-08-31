package hellogbye.com.hellogbyeandroid.models.vo.acountsettings;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 11/4/15.
 */
public class AccountDefaultSettingsVO {

    private boolean isChecked = false;

    private String rank;

    @SerializedName("id")
    private String mId;

    @SerializedName("profilename")
    private String mProfileName;

    private boolean isActiveProfile = false;


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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isActiveProfile() {
        return isActiveProfile;
    }

    public void setActiveProfile(boolean activeProfile) {
        isActiveProfile = activeProfile;
    }
}
