package hellogbye.com.hellogbyeandroid.models;

/**
 * Created by arisprung on 11/15/15.
 */
public class PaymnentGroup {

    private String nameText;
    private String totalText;
    private boolean selected;

    public PaymnentGroup(String nameText, String totalText, boolean selected) {
        this.nameText = nameText;
        this.totalText = totalText;
        this.selected = selected;
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
}
