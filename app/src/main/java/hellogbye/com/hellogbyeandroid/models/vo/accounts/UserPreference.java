package hellogbye.com.hellogbyeandroid.models.vo.accounts;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 9/2/15.
 */
public class UserPreference {

    @SerializedName("id")
    private String id;

    @SerializedName("profilename")
    private String profilename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }
}
