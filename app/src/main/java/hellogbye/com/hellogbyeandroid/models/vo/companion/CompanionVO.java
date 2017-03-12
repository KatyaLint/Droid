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
    @SerializedName("addedvia")
    private String mAddedvia;

    @SerializedName("companionuserprofile")
    private CompanionUserProfileVO companionUserProfile;

    @SerializedName("invitoruserprofile")
    private CompanionInviteUserProfileVO companionInviteUserProfileVO;

    @SerializedName("invitationtoken")
    private String mInvitationtoken;

    @SerializedName("isfamilymember")
    private boolean mIsFamilyMember;



    public String getmConfirmationstatus() {
        return mConfirmationstatus;
    }

    public void setmConfirmationstatus(String mConfirmationstatus) {
        this.mConfirmationstatus = mConfirmationstatus;
    }

    public CompanionUserProfileVO getCompanionUserProfile() {
        return companionUserProfile;
    }

    public void setCompanionUserProfile(CompanionUserProfileVO companionUserProfile) {
        this.companionUserProfile = companionUserProfile;
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

    public CompanionInviteUserProfileVO getCompanionInviteUserProfileVO() {
        return companionInviteUserProfileVO;
    }

    public void setCompanionInviteUserProfileVO(CompanionInviteUserProfileVO companionInviteUserProfileVO) {
        this.companionInviteUserProfileVO = companionInviteUserProfileVO;
    }

    public String getmInvitationtoken() {
        return mInvitationtoken;
    }

    public void setmInvitationtoken(String mInvitationtoken) {
        this.mInvitationtoken = mInvitationtoken;
    }

    public String getmAddedvia() {
        return mAddedvia;
    }

    public void setmAddedvia(String mAddedvia) {
        this.mAddedvia = mAddedvia;
    }

    public boolean ismIsFamilyMember() {
        return mIsFamilyMember;
    }

    public void setmIsFamilyMember(boolean mIsFamilyMember) {
        this.mIsFamilyMember = mIsFamilyMember;
    }
}
