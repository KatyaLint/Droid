package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arisprung on 2/13/17.
 */

public class Occupant {

    @SerializedName("id")
    private String id;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
