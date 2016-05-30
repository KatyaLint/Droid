package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by arisprung on 10/29/15.
 */
public class UserDataVO {


    @SerializedName("userprofileid")
    private String userprofileid;

    @SerializedName("emailaddress")
    private String emailaddress;


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

    @SerializedName("avatarurl")
    private String avatar;

    @SerializedName("ispremiumuser")
    private boolean ispremiumuser;

    @SerializedName("paxid")
    private String paxid;

    @SerializedName("phone")
    private String phone;

    @SerializedName("title")
    private String title;

    @SerializedName("gender")
    private String gender;

    private HashSet<String> bookingItems;


    public UserDataVO() {
    }


    public UserDataVO(String userprofileid, String emailaddress, String firstname, String lastname, String dob, String country, String address, String city, String state, String postalcode, String avatar, boolean ispremiumuser, String paxid, String phone, String title) {
        this.userprofileid = userprofileid;
        this.emailaddress = emailaddress;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        setCountry(country);
        setAddress(address);
        setCity(city);
      setState(state);
        this.postalcode = postalcode;
        this.avatar = avatar;
        this.ispremiumuser = ispremiumuser;
        this.paxid = paxid;
        this.phone = phone;
        setTitle(title);
    }


    public String getUserprofileid() {
        return userprofileid;
    }

    public void setUserprofileid(String userprofileid) {
        this.userprofileid = userprofileid;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
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

        if(country == null){ //can be from parser
            this.country = "";
        }else {
            this.country = country;
        }
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
        if(city == null){ //can be from parser
            this.city = "";
        }else {
            this.city = city;
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if(state == null){ //can be from parser
            this.state = "";
        }else {
            this.state = state;
        }
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

    public String getPaxid() {
        return paxid;
    }

    public void setPaxid(String paxid) {
        this.paxid = paxid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title != null) {
            this.title = title.trim();
        } else{
            this.title = "";
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public HashSet<String> getBookingItems() {
        return bookingItems;
    }

    public void setBookingItems(HashSet<String> bookingItems) {
        this.bookingItems = bookingItems;
    }


}
