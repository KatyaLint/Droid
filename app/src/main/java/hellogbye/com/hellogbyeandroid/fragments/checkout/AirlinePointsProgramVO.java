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
}
