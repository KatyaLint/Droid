package hellogbye.com.hellogbyeandroid.models.vo.companion;

import com.google.gson.annotations.SerializedName;



/**
 * Created by nyawka on 1/6/16.
 */
public class CompanionVO {
    @SerializedName("confirmationstatus")
    private String mConfirmationstatus;
    @SerializedName("companionid")
    private String mCompanionid;
    @SerializedName("addedatetime")
    private String mAddedatetime;

    @SerializedName("companionuserprofile")
    private
    CompanionUserProfileVO campanionUserProfile ;


    public String getmConfirmationstatus() {
        return mConfirmationstatus;
    }

    public void setmConfirmationstatus(String mConfirmationstatus) {
        this.mConfirmationstatus = mConfirmationstatus;
    }

    public CompanionUserProfileVO getCampanionUserProfile() {
        return campanionUserProfile;
    }

    public void setCampanionUserProfile(CompanionUserProfileVO campanionUserProfile) {
        this.campanionUserProfile = campanionUserProfile;
    }

    public String getmCompanionid() {
        return mCompanionid;
    }

    public void setmCompanionid(String mCompanionid) {
        this.mCompanionid = mCompanionid;
    }
}
