package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 9/9/15.
 */
public class AllImagesVO {
    @SerializedName("caption")
    private String mCaption;
    @SerializedName("image")
    private String mImage;
    @SerializedName("width")
    private long mWidth;
    @SerializedName("height")
    private long mHotelName;


    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}
