package hellogbye.com.hellogbyeandroid.models.vo.creditcard;

/**
 * Created by arisprung on 11/15/15.
 */
public class PaymentChild {

    private String nameText;
    private String totalText;
    private boolean selected;
    private String guid;
    private String paxguid;
    private String creditcard;

    public PaymentChild(String nameText, String totalText, boolean selected,String guid,String paxguid,String creditcard) {
        this.nameText = nameText;
        this.totalText = totalText;
        this.selected = selected;
        this.guid = guid;
        this.paxguid = paxguid;
        this.creditcard = creditcard;
    }

    public String getNameText() {
        return nameText;
    }

    public void setNameText(String nameText) {
        this.nameText = nameText;
    }

    public String getTotalText() {
        return totalText;
    }

    public void setTotalText(String totalText) {
        this.totalText = totalText;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getPaxguid() {
        return paxguid;
    }

    public void setPaxguid(String paxguid) {
        this.paxguid = paxguid;
    }

    public String getCreditcard() {
        return creditcard;
    }

    public void setCreditcard(String creditcard) {
        this.creditcard = creditcard;
    }
}
