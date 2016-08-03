package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arisprung on 8/31/15.
 */
public class UserLoginCredentials {

    @SerializedName("token")
    private String token;

    @SerializedName("userprofileid")
    private String userprofileid;

    @SerializedName("modifieddatetime")
    private String modifieddatetime;

    @SerializedName("addeddatetime")
    private String addeddatetime;

    @SerializedName("isanonymous")
    private boolean isanonymous;

    public UserLoginCredentials(String token, String userprofileid, String modifieddatetime, String addeddatetime, boolean  isanonymous ) {
        this.token = token;
        this.userprofileid = userprofileid;
        this.modifieddatetime = modifieddatetime;
        this.addeddatetime = addeddatetime;
        this.isanonymous = isanonymous;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserprofileid() {
        return userprofileid;
    }

    public void setUserprofileid(String userprofileid) {
        this.userprofileid = userprofileid;
    }

    public String getModifieddatetime() {
        return modifieddatetime;
    }

    public void setModifieddatetime(String modifieddatetime) {
        this.modifieddatetime = modifieddatetime;
    }

    public String getAddeddatetime() {
        return addeddatetime;
    }

    public void setAddeddatetime(String addeddatetime) {
        this.addeddatetime = addeddatetime;
    }

    @Override
    public String toString() {
        return "UserLoginCredentials{" +
                "token='" + token + '\'' +
                ", userprofileid='" + userprofileid + '\'' +
                ", modifieddatetime='" + modifieddatetime + '\'' +
                ", addeddatetime='" + addeddatetime + '\'' +
                ", isanonymous='" + isanonymous + '\'' +
                '}';
    }
}
