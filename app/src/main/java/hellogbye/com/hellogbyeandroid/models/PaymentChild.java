package hellogbye.com.hellogbyeandroid.models;

/**
 * Created by arisprung on 11/15/15.
 */
public class PaymentChild {

    private String nameText;
    private String totalText;
    private boolean selected;
    private String guid;

    public PaymentChild(String nameText, String totalText, boolean selected,String guid) {
        this.nameText = nameText;
        this.totalText = totalText;
        this.selected = selected;
        this.guid = guid;
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
}
