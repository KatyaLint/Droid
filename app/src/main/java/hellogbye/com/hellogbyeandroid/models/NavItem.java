package hellogbye.com.hellogbyeandroid.models;

/**
 * Created by arisprung on 8/19/15.
 */
public class NavItem {

    private ToolBarNavEnum name;
    private boolean isSelected;

    public NavItem(ToolBarNavEnum name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name.getNavTitle();
    }

//    public void setName(String name) {
//        this.name = name;
//    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}

