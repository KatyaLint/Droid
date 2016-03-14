package hellogbye.com.hellogbyeandroid.models.vo.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 3/13/16.
 */
public class AccountsVO {
    @SerializedName("email")
    private String email;

    @SerializedName("travelpreferenceprofile")
    private UserPreference travelpreferenceprofile;

    public UserPreference getTravelpreferenceprofile() {
        return travelpreferenceprofile;
    }

    public String getEmail() {
        return email;
    }

}
