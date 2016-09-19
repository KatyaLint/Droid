package hellogbye.com.hellogbyeandroid.models.vo.accounts;

import com.google.gson.annotations.SerializedName;

import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;

/**
 * Created by nyawka on 3/13/16.
 */
public class AccountsVO {

    @SerializedName("email")
    private String email;

    @SerializedName("travelpreferenceprofile")
    private AccountDefaultSettingsVO travelpreferenceprofile;

    public AccountDefaultSettingsVO getTravelpreferenceprofile() {
        return travelpreferenceprofile;
    }

    public String getEmail() {
        return email;
    }

}
