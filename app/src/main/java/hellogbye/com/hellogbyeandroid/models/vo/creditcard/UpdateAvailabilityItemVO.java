package hellogbye.com.hellogbyeandroid.models.vo.creditcard;
import com.facebook.stetho.json.ObjectMapper;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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


    private Map<String,Object> taxbreakdownhash;

    public Map<String, Object> getTaxbreakdownhash() {
        return taxbreakdownhash;
    }

    public void setTaxbreakdownhash(Map<String, Object> taxbreakdownhash) {
        this.taxbreakdownhash = taxbreakdownhash;
    }



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

    public String getHotelid() {
        return hotelid;
    }

    public void setHotelid(String hotelid) {
        this.hotelid = hotelid;
    }

    public double getBasefare() {
        return basefare;
    }

    public void setBasefare(double basefare) {
        this.basefare = basefare;
    }

    public double getBasefareusd() {
        return basefareusd;
    }

    public void setBasefareusd(double basefareusd) {
        this.basefareusd = basefareusd;
    }

    public TaxVO getTaxbreakdown() {
        return taxbreakdown;
    }

    public void setTaxbreakdown(TaxVO taxbreakdown) {
        this.taxbreakdown = taxbreakdown;
    }

    public double getAveragerate() {
        return averagerate;
    }

    public void setAveragerate(double averagerate) {
        this.averagerate = averagerate;
    }

    public double getSurchargetotal() {
        return surchargetotal;
    }

    public void setSurchargetotal(double surchargetotal) {
        this.surchargetotal = surchargetotal;
    }

    public double getSurchargetotalusd() {
        return surchargetotalusd;
    }

    public void setSurchargetotalusd(double surchargetotalusd) {
        this.surchargetotalusd = surchargetotalusd;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCostusd() {
        return costusd;
    }

    public void setCostusd(double costusd) {
        this.costusd = costusd;
    }

    public boolean isavailable() {
        return isavailable;
    }

    public void setIsavailable(boolean isavailable) {
        this.isavailable = isavailable;
    }

    public ArrayList<Double> getNightlyrate() {
        return nightlyrate;
    }

    public void setNightlyrate(ArrayList<Double> nightlyrate) {
        this.nightlyrate = nightlyrate;
    }

    public ArrayList<Double> getNightlyrateusd() {
        return nightlyrateusd;
    }

    public void setNightlyrateusd(ArrayList<Double> nightlyrateusd) {
        this.nightlyrateusd = nightlyrateusd;
    }

    public ArrayList<String> getSupportedcards() {
        return supportedcards;
    }

    public void setSupportedcards(ArrayList<String> supportedcards) {
        this.supportedcards = supportedcards;
    }

    public String getFlightid() {
        return flightid;
    }

    public void setFlightid(String flightid) {
        this.flightid = flightid;
    }

    public double getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(double totalamount) {
        this.totalamount = totalamount;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getHst() {
        return hst;
    }

    public void setHst(double hst) {
        this.hst = hst;
    }

    public double getTotaltax() {
        return totaltax;
    }

    public void setTotaltax(double totaltax) {
        this.totaltax = totaltax;
    }

    public boolean ischildflight() {
        return ischildflight;
    }

    public void setIschildflight(boolean ischildflight) {
        this.ischildflight = ischildflight;
    }
}
