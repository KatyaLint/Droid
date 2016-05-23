package hellogbye.com.hellogbyeandroid.models.vo.companion;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 5/22/16.
 */
public class CompanionInviteUserProfileVO {

    @SerializedName("userprofileid")
    private String mUserProfileId;
    @SerializedName("firstname")
    private String mFirstName;
    @SerializedName("lastname")
    private String mLastName;
    @SerializedName("emailaddress")
    private String mEmailAddress;
    @SerializedName("country")
    private String mCountry;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("city")
    private String mCity;
    @SerializedName("state")
    private String mState;
    @SerializedName("ispremiumuser")
    private String mIsPremiumUser;

    public String getmUserProfileId() {
        return mUserProfileId;
    }

    public void setmUserProfileId(String mUserProfileId) {
        this.mUserProfileId = mUserProfileId;
    }

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }
}
