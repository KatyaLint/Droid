package hellogbye.com.hellogbyeandroid.models.vo.flights;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 12/13/15.
 */
public class ConversationVO {
    @SerializedName("message")
    private String mMessage;

    @SerializedName("participant")
    private String mParticipant;
    @SerializedName("messagedatetime")
    private String mMessageDateTime;

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }
}
