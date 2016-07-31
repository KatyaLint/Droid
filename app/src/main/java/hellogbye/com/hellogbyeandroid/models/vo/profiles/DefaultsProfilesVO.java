package hellogbye.com.hellogbyeandroid.models.vo.profiles;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 7/31/16.
 */
public class DefaultsProfilesVO {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
