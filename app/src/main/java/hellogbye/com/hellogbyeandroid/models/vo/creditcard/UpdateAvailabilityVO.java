package hellogbye.com.hellogbyeandroid.models.vo.creditcard;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by amirlubashevsky on 10/10/2017.
 */

public class UpdateAvailabilityVO {

    @SerializedName("hotels")
    private ArrayList<UpdateAvailabilityItemVO> hotels;

    @SerializedName("flights")
    private ArrayList<UpdateAvailabilityItemVO> flights;


    public ArrayList<UpdateAvailabilityItemVO> getHotels() {
        return hotels;
    }

    public void setHotels(ArrayList<UpdateAvailabilityItemVO> hotels) {
        this.hotels = hotels;
    }

    public ArrayList<UpdateAvailabilityItemVO> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<UpdateAvailabilityItemVO> flights) {
        this.flights = flights;
    }
}
