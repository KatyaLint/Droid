package hellogbye.com.hellogbyeandroid.models.vo.creditcard;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by amirlubashevsky on 10/10/2017.
 */

public class UpdateAvailabilityItemVO {

    //hotels
    @SerializedName("hotelid")
    private String hotelid;

    @SerializedName("basefare")
    private double basefare;

    @SerializedName("basefareusd")
    private double basefareusd;

    @SerializedName("taxbreakdown")
    private TaxVO taxbreakdown;

    @SerializedName("averagerate")
    private double averagerate;

    @SerializedName("surchargetotal")
    private double surchargetotal;

    @SerializedName("surchargetotalusd")
    private double surchargetotalusd;

    @SerializedName("cost")
    private double cost;

    @SerializedName("costusd")
    private double costusd;

    @SerializedName("isavailable")
    private boolean isavailable;

    @SerializedName("nightlyrate")
    private ArrayList<Double> nightlyrate = new ArrayList<Double>();

    @SerializedName("nightlyrateusd")
    private ArrayList<Double> nightlyrateusd = new ArrayList<Double>();


    @SerializedName("supportedcards")
    private ArrayList<String> supportedcards;
  //  private String[] supportedcards;

    //flights
    @SerializedName("flightsid")
    private String flightid;

    @SerializedName("totalamount")
    private double totalamount;

    @SerializedName("gst")
    private double gst;

    @SerializedName("hst")
    private double hst;

    @SerializedName("totaltax")
    private double totaltax;

    @SerializedName("ischildflight")
    private boolean ischildflight;
}
