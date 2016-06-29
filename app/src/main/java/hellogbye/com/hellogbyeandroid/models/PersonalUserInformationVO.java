package hellogbye.com.hellogbyeandroid.models;

/**
 * Created by nyawka on 6/29/16.
 */
public class PersonalUserInformationVO {

    private String mTravelPreferencesProfileId;
    private String userEmailLogIn;

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
}
