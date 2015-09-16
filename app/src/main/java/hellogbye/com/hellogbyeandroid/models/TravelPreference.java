package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arisprung on 9/16/15.
 */
public class TravelPreference {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("addeddatetime")
    private String addeddatetime;

    @SerializedName("modifieddatetime")
    private String modifieddatetime;


    public TravelPreference() {

    }

    public TravelPreference(int id, String name, String description, String addeddatetime, String modifieddatetime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.addeddatetime = addeddatetime;
        this.modifieddatetime = modifieddatetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddeddatetime() {
        return addeddatetime;
    }

    public void setAddeddatetime(String addeddatetime) {
        this.addeddatetime = addeddatetime;
    }

    public String getModifieddatetime() {
        return modifieddatetime;
    }

    public void setModifieddatetime(String modifieddatetime) {
        this.modifieddatetime = modifieddatetime;
    }
}
