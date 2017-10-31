package hellogbye.com.hellogbyeandroid.fragments.checkout;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amirlubashevsky on 09/08/2017.
 */

public class AirlinePointsProgramVO {

    @SerializedName("id")
    private String id;

    @SerializedName("modifieddatetime")
    private String modifieddatetime;

    @SerializedName("addeddatetime")
    private String addeddatetime;

    @SerializedName("programname")
    private String programname;

    @SerializedName("firstname")
    private String firstname;

    @SerializedName("lastname")
    private String lastname;

    @SerializedName("userprofileid")
    private String userprofileid;

    @SerializedName("programnumber")
    private String programnumber;

    @SerializedName("programid")
    private int programid;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProgramname() {
        return programname;
    }

    public void setProgramname(String programname) {
        this.programname = programname;
    }

    public String getProgramnumber() {
        return programnumber;
    }

    public void setProgramnumber(String programnumber) {
        this.programnumber = programnumber;
    }

    public int getProgramid() {
        return programid;
    }

    public void setProgramid(int programid) {
        this.programid = programid;
    }

    public String getUserprofileid() {
        return userprofileid;
    }

    public void setUserprofileid(String userprofileid) {
        this.userprofileid = userprofileid;
    }
}
