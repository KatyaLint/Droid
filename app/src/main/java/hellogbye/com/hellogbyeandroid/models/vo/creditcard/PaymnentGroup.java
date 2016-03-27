package hellogbye.com.hellogbyeandroid.models.vo.creditcard;

import java.util.ArrayList;

/**
 * Created by arisprung on 11/15/15.
 */
public class PaymnentGroup {

    private String nameText;
    private String totalText;
    private boolean selected;
    private ArrayList<String> items;

    public PaymnentGroup(String nameText, String totalText, boolean selected, ArrayList<String> items) {
        this.nameText = nameText;
        this.totalText = totalText;
        this.selected = selected;
        this.items = items;
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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setTotalText(String totalText) {
        this.totalText = totalText;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
}
