package hellogbye.com.hellogbyeandroid.models;

/**
 * Created by arisprung on 8/19/15.
 */
public class NavItem {

    private ToolBarNavEnum name;
    private boolean isSelected;
    private int iconEnable;
    private int iconDisable;

    public NavItem(ToolBarNavEnum name, boolean isSelected, int iconEnable, int iconDisable) {
        this.name = name;
        this.isSelected = isSelected;
        this.iconEnable = iconEnable;
        this.iconDisable = iconDisable;
    }

    public String getName() {
        return name.getNavTitle();
    }

    public int getIndex(){
        return name.getNavNumber();
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

    public int getIconEnable() {
        return iconEnable;
    }
    public int getIconDisable() {
        return iconDisable;
    }

}

