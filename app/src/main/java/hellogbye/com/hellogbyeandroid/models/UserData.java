package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arisprung on 10/29/15.
 */
public class UserData {

    @SerializedName("userprofileid")
    private String userprofileid;

    @SerializedName("firstname")
    private String firstname;


    @SerializedName("lastname")
    private String lastname;


    @SerializedName("dob")
    private String dob;

    @SerializedName("country")
    private String country;

    @SerializedName("address")
    private String address;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("postalcode")
    private String postalcode;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("ispremiumuser")
    private boolean ispremiumuser;

    public UserData(String userprofileid, String firstname, String lastname, String dob, String country, String address, String city, String state, String postalcode, String avatar, boolean ispremiumuser) {
        this.userprofileid = userprofileid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.country = country;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalcode = postalcode;
        this.avatar = avatar;
        this.ispremiumuser = ispremiumuser;
    }

    public UserData(){

    }

    public String getUserprofileid() {
        return userprofileid;
    }

    public void setUserprofileid(String userprofileid) {
        this.userprofileid = userprofileid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean ispremiumuser() {
        return ispremiumuser;
    }

    public void setIspremiumuser(boolean ispremiumuser) {
        this.ispremiumuser = ispremiumuser;
    }
}
