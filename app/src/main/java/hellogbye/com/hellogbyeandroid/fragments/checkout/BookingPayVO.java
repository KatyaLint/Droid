package hellogbye.com.hellogbyeandroid.fragments.checkout;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
/**
 * Created by amirlubashevsky on 25/10/2017.
 */

public class BookingPayVO {

    @SerializedName("itineraryid")
    private String itineraryid;

    @SerializedName("issuccessful")
    private boolean issuccessful;

    @SerializedName("itemid")
    private String itemid;

    @SerializedName("errormessages")
    private ArrayList<String> errormessages;

    public String getItineraryid() {
        return itineraryid;
    }

    public void setItineraryid(String itineraryid) {
        this.itineraryid = itineraryid;
    }

    public boolean issuccessful() {
        return issuccessful;
    }

    public void setIssuccessful(boolean issuccessful) {
        this.issuccessful = issuccessful;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public ArrayList<String> getErrormessages() {
        return errormessages;
    }

    public void setErrormessages(ArrayList<String> errormessages) {
        this.errormessages = errormessages;
    }
}
