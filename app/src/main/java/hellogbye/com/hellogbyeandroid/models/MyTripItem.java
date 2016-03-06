package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arisprung on 12/15/15.
 */
public class MyTripItem {

    @SerializedName("solutionid")
    private String solutionid;

    @SerializedName("name")
    private String name;

    @SerializedName("isfavorite")
    private boolean isfavorite;

    @SerializedName("paymentstatus")
    private String paymentstatus;

    @SerializedName("addeddatetime")
    private String addeddatetime;

    @SerializedName("modifieddatetime")
    private String modifieddatetime;


    @SerializedName("enddate")
    private String enddate;

    @SerializedName("startdate")
    private String startdate;


    private boolean editDelete = false;



    public String getSolutionid() {
        return solutionid;
    }

    public void setSolutionid(String solutionid) {
        this.solutionid = solutionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isfavorite() {
        return isfavorite;
    }

    public void setIsfavorite(boolean isfavorite) {
        this.isfavorite = isfavorite;
    }

    public String getPaymentstatus() {
        return paymentstatus;
    }

    public void setPaymentstatus(String paymentstatus) {
        this.paymentstatus = paymentstatus;
    }

    public String getAddeddatetime() {
        return addeddatetime;
    }

    public void setAddeddatetime(String addeddatetime) {
        this.addeddatetime = addeddatetime;
    }

    public String getModifieddatetime() {
        return modifieddatetime;
    }

    public void setModifieddatetime(String modifieddatetime) {
        this.modifieddatetime = modifieddatetime;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public boolean isEditDelete() {
        return editDelete;
    }

    public void setEditDelete(boolean editDelete) {
        this.editDelete = editDelete;
    }
}
