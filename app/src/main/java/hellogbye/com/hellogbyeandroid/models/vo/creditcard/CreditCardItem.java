package hellogbye.com.hellogbyeandroid.models.vo.creditcard;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arisprung on 12/2/15.
 */
public class CreditCardItem {

    @SerializedName("cardtypeid")
    private String cardtypeid;

    @SerializedName("expmonth")
    private String expmonth;

    @SerializedName("expyear")
    private String expyear;

    @SerializedName("buyerfirstname")
    private String buyerfirstname;

    @SerializedName("buyerlastname")
    private String buyerlastname;

    @SerializedName("buyeraddress")
    private String buyeraddress;

    @SerializedName("buyerzip")
    private String buyerzip;

    @SerializedName("token")
    private String token;

    @SerializedName("userid")
    private String userid;

    @SerializedName("nickname")
    private String nickname;


    @SerializedName("last4")
    private String last4;

    @SerializedName("addeddatetime")
    private String addeddatetime;

    @SerializedName("modifieddatetime")
    private String modifieddatetime;

    private String cvv;

    private boolean isSelected;

    private String cardNumber;
    private boolean updateCard = false;

    public CreditCardItem(){

    }

    public CreditCardItem(String last4){
        this.last4 = last4;
    }

    public CreditCardItem(String cardtypeid, String expmonth, String expyear, String buyerfirstname, String buyerlastname, String buyeraddress, String buyerzip, String token, String userid, String nickname, String last4, String addeddatetime, String modifieddatetime, boolean isSelected) {
        this.cardtypeid = cardtypeid;
        this.expmonth = expmonth;
        this.expyear = expyear;
        this.buyerfirstname = buyerfirstname;
        this.buyerlastname = buyerlastname;
        this.buyeraddress = buyeraddress;
        this.buyerzip = buyerzip;
        this.token = token;
        this.userid = userid;
        this.nickname = nickname;
        this.last4 = last4;
        this.addeddatetime = addeddatetime;
        this.modifieddatetime = modifieddatetime;
        this.isSelected = isSelected;
    }

    public String getCardtypeid() {
        return cardtypeid;
    }

    public void setCardtypeid(String cardtypeid) {
        this.cardtypeid = cardtypeid;
    }

    public String getExpmonth() {
        return expmonth;
    }

    public void setExpmonth(String expmonth) {
        this.expmonth = expmonth;
    }

    public String getExpyear() {
        return expyear;
    }

    public void setExpyear(String expyear) {
        this.expyear = expyear;
    }

    public String getBuyerfirstname() {
        return buyerfirstname;
    }

    public void setBuyerfirstname(String buyerfirstname) {
        this.buyerfirstname = buyerfirstname;
    }

    public String getBuyerlastname() {
        return buyerlastname;
    }

    public void setBuyerlastname(String buyerlastname) {
        this.buyerlastname = buyerlastname;
    }

    public String getBuyeraddress() {
        return buyeraddress;
    }

    public void setBuyeraddress(String buyeraddress) {
        this.buyeraddress = buyeraddress;
    }

    public String getBuyerzip() {
        return buyerzip;
    }

    public void setBuyerzip(String buyerzip) {
        this.buyerzip = buyerzip;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setUpdateCard(boolean updateCard) {
        this.updateCard = updateCard;
    }

    public boolean isUpdateCard() {
        return updateCard;
    }
}
