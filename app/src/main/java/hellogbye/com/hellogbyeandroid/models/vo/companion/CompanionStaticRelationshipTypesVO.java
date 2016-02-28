package hellogbye.com.hellogbyeandroid.models.vo.companion;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 2/28/16.
 */
public class CompanionStaticRelationshipTypesVO {
    @SerializedName("id")
    private int mId;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("addeddatetime")
    private String mAddeddatetime;
    @SerializedName("modifieddatetime")
    private String mModifieddatetime;



    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmAddeddatetime() {
        return mAddeddatetime;
    }

    public void setmAddeddatetime(String mAddeddatetime) {
        this.mAddeddatetime = mAddeddatetime;
    }

    public String getmModifieddatetime() {
        return mModifieddatetime;
    }

    public void setmModifieddatetime(String mModifieddatetime) {
        this.mModifieddatetime = mModifieddatetime;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }
}
