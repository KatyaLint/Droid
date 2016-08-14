package hellogbye.com.hellogbyeandroid.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by arisprung on 8/14/16.
 */
public class Amenities {
    @SerializedName("amenityid")
    private String amenitiesid;
    @SerializedName("amenity")
    private String amenity;

    public String getAmenitiesid() {
        return amenitiesid;
    }

    public void setAmenitiesid(String amenitiesid) {
        this.amenitiesid = amenitiesid;
    }

    public String getAmenity() {
        return amenity;
    }

    public void setAmenity(String amenity) {
        this.amenity = amenity;
    }
}
