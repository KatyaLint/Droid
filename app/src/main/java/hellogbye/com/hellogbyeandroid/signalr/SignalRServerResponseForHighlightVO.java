package hellogbye.com.hellogbyeandroid.signalr;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 2/2/17.
 */

public class SignalRServerResponseForHighlightVO {
    @SerializedName("originalquery")
    private String mOriginalQuery;

    @SerializedName("solutionid")
    private String mSolutionid;

    @SerializedName("message")
    private String mMessage;

    @SerializedName("signalrclientid")
    private String mSignalrclientid;


    public String getmSolutionid() {
        return mSolutionid;
    }

    public void setmSolutionid(String mSolutionid) {
        this.mSolutionid = mSolutionid;
    }
}
