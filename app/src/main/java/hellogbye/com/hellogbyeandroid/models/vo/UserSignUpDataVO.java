package hellogbye.com.hellogbyeandroid.models.vo;

/**
 * Created by nyawka on 3/15/16.
 */
public class UserSignUpDataVO {
    private String city;
    private String confirmPassword;
    private String country;
    private String countryProvince;
    private String countryID;
    private String firstName;
    private String lastName;
    private String password;
    private String state="";
    private String username;
    private String userEmail;
    private int userTravelerType;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getCountryProvince() {
        return countryProvince;
    }

    public void setCountryProvince(String countryProvince) {
        this.countryProvince = countryProvince;
    }

    public int getUserTravelerType() {
        return userTravelerType;
    }

    public void setUserTravelerType(int userTravelerType) {
        this.userTravelerType = userTravelerType;
    }
}
