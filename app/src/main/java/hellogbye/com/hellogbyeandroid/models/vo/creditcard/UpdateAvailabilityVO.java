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


}
