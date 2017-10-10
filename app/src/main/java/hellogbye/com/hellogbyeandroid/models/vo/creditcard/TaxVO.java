package hellogbye.com.hellogbyeandroid.models.vo.creditcard;
import com.google.gson.annotations.SerializedName;
/**
 * Created by amirlubashevsky on 10/10/2017.
 */

public class TaxVO {
    @SerializedName("tax")
    private String tax;

    @SerializedName("city tax")
    private String city_tax;

    @SerializedName("combination of taxes")
    private double combination_of_taxes;

    @SerializedName("israel departure tax")
    private double israel_departure_tax;

    @SerializedName("russia intl. terminal use charge")
    private double russia_intl_terminal_use_charge;

    @SerializedName("u.s agriculture fee")
    private double us_agriculture_fee;

    @SerializedName("u.s.a immigration user fee")
    private double usa_immigration_user_fee;

    @SerializedName("u.s.a transportation tax")
    private double usa_transportation_tax;

    @SerializedName("united states customs user fee")
    private double united_states_customs_user_fee;
}
