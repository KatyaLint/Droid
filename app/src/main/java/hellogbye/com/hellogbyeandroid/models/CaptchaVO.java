package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amirlubashevsky on 07/11/2017.
 */

public class CaptchaVO {

    @SerializedName("id")
    private String id;

    @SerializedName("image")
    private String image;

    @SerializedName("imagetype")
    private String imagetype;

    @SerializedName("expirytime")
    private String expiretime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImagetype() {
        return imagetype;
    }

    public void setImagetype(String imagetype) {
        this.imagetype = imagetype;
    }

    public String getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime;
    }
}
