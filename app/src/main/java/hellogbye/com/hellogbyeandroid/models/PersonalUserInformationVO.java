package hellogbye.com.hellogbyeandroid.models;

/**
 * Created by nyawka on 6/29/16.
 */
public class PersonalUserInformationVO {

    private String mTravelPreferencesProfileId;
    private String userEmailLogIn;
    private String mTravelPreferencesProfileName;

    public String getUserEmailLogIn() {
        return userEmailLogIn;
    }

    public void setUserEmailLogIn(String userEmailLogIn) {
        this.userEmailLogIn = userEmailLogIn;
    }

    public String getmTravelPreferencesProfileId() {

        return mTravelPreferencesProfileId;
    }

    public void setmTravelPreferencesProfileId(String mTravelPreferencesProfileId) {
        this.mTravelPreferencesProfileId = mTravelPreferencesProfileId;
    }


    public String getmTravelPreferencesProfileName() {
        return mTravelPreferencesProfileName;
    }

    public void setmTravelPreferencesProfileName(String mTravelPreferencesProfileName) {
        this.mTravelPreferencesProfileName = mTravelPreferencesProfileName;
    }
}
