package hellogbye.com.hellogbyeandroid.models.vo.cnc;

/**
 * Created by arisprung on 11/3/15.
 */
public class CNCItem {

    private String text;
    private int type;
    private boolean addSignalRText = false;


    public CNCItem(){

    }
    public CNCItem(int type){
        this.type = type;
    }

    public CNCItem(String text, int type) {
        this.text = text;
        this.type = type;
    }


    public String getText() {
        return text;
    }

    public void setText(String item) {
        this.text = item;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAddSIgnalRText() {
        return addSignalRText;
    }

    public void setAddSIgnalRText(boolean addSIgnalRText) {
        this.addSignalRText = addSIgnalRText;
    }
}
