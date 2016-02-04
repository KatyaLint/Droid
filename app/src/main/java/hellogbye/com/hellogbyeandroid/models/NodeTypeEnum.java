package hellogbye.com.hellogbyeandroid.models;

/**
 * Created by nyawka on 11/16/15.
 */
public enum NodeTypeEnum {
    HOTEL("hotel"),FLIGHT("flight"),EMPTY("empty");
    private String mType;

    NodeTypeEnum(String type){
        this.mType = type;
    }

    public String getType(){
        return mType;
    }
}
