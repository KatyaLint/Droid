package hellogbye.com.hellogbyeandroid.models.vo.companion;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 1/6/16.
 */
public class CompanionUserProfileVO {
    @SerializedName("userprofileid")
    private String mUserProfileId;
    @SerializedName("title")
    private String mTitle;
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
    @SerializedName("avatarurl")
    private String mAvatar;
    @SerializedName("ispremiumuser")
    private boolean mIsPremiumUser;
    @SerializedName("istravelprofile")
    private boolean mIstravelProfile;
    @SerializedName("currency")
    private boolean mCurrency;

    @SerializedName("gender")
    private String mGender;
    @SerializedName("dob")
    private String mDoB;

    public String getmFirstName() {
        return mFirstName;
    }

    public void setmFirstName(String mFirstName) {
        this.mFirstName = mFirstName;
    }

    public String getmAvatar() {
        return mAvatar;
    }

    public void setmAvatar(String mAvatar) {
        this.mAvatar = mAvatar;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmLastName() {
        return mLastName;
    }

    public void setmLastName(String mLastName) {
        this.mLastName = mLastName;
    }

    public String getmEmailAddress() {
        return mEmailAddress;
    }

    public void setmEmailAddress(String mEmailAddress) {
        this.mEmailAddress = mEmailAddress;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmUserProfileId() {
        return mUserProfileId;
    }

    public void setmUserProfileId(String mUserProfileId) {
        this.mUserProfileId = mUserProfileId;
    }

    public boolean ismIsPremiumUser() {
        return mIsPremiumUser;
    }

    public void setmIsPremiumUser(boolean mIsPremiumUser) {
        this.mIsPremiumUser = mIsPremiumUser;
    }

    public boolean ismIstravelProfile() {
        return mIstravelProfile;
    }

    public void setmIstravelProfile(boolean mIstravelProfile) {
        this.mIstravelProfile = mIstravelProfile;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public boolean ismCurrency() {
        return mCurrency;
    }

    public void setmCurrency(boolean mCurrency) {
        this.mCurrency = mCurrency;
    }

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmDoB() {
        return mDoB;
    }

    public void setmDoB(String mDoB) {
        this.mDoB = mDoB;
    }
}
