package hellogbye.com.hellogbyeandroid.models.vo.companion;

import com.google.gson.annotations.SerializedName;



/**
 * Created by nyawka on 1/6/16.
 */
public class CompanionVO {
    @SerializedName("confirmationstatus")
    private String mConfirmationstatus;
    @SerializedName("relationshiptype")
    private String relationshiptype;
    @SerializedName("relationshiptypeid")
    private String relationshiptypeid;

    @SerializedName("companionid")
    private String mCompanionid;
    @SerializedName("addeddatetime")
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

    public String getRelationshiptypeid() {
        return relationshiptypeid;
    }

    public void setRelationshiptypeid(String relationshiptypeid) {
        this.relationshiptypeid = relationshiptypeid;
    }

    public String getRelationshiptype() {
        return relationshiptype;
    }

    public void setRelationshiptype(String relationshiptype) {
        this.relationshiptype = relationshiptype;
    }

    public String getmAddedatetime() {
        return mAddedatetime;
    }

    public void setmAddedatetime(String mAddedatetime) {
        this.mAddedatetime = mAddedatetime;
    }
}
