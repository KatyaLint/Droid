package hellogbye.com.hellogbyeandroid.signalr;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by nyawka on 1/29/17.
 */

public class jObj {


    private Map<String, Object> jsonObject;


    public Map<String, Object> getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(Map<String, Object> jsonObject) {
        this.jsonObject = jsonObject;
    }
}
