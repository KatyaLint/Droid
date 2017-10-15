package hellogbye.com.hellogbyeandroid.models.vo.creditcard;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

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

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getCity_tax() {
        return city_tax;
    }

    public void setCity_tax(String city_tax) {
        this.city_tax = city_tax;
    }

    public double getCombination_of_taxes() {
        return combination_of_taxes;
    }

    public void setCombination_of_taxes(double combination_of_taxes) {
        this.combination_of_taxes = combination_of_taxes;
    }

    public double getIsrael_departure_tax() {
        return israel_departure_tax;
    }

    public void setIsrael_departure_tax(double israel_departure_tax) {
        this.israel_departure_tax = israel_departure_tax;
    }

    public double getRussia_intl_terminal_use_charge() {
        return russia_intl_terminal_use_charge;
    }

    public void setRussia_intl_terminal_use_charge(double russia_intl_terminal_use_charge) {
        this.russia_intl_terminal_use_charge = russia_intl_terminal_use_charge;
    }

    public double getUs_agriculture_fee() {
        return us_agriculture_fee;
    }

    public void setUs_agriculture_fee(double us_agriculture_fee) {
        this.us_agriculture_fee = us_agriculture_fee;
    }

    public double getUsa_immigration_user_fee() {
        return usa_immigration_user_fee;
    }

    public void setUsa_immigration_user_fee(double usa_immigration_user_fee) {
        this.usa_immigration_user_fee = usa_immigration_user_fee;
    }

    public double getUsa_transportation_tax() {
        return usa_transportation_tax;
    }

    public void setUsa_transportation_tax(double usa_transportation_tax) {
        this.usa_transportation_tax = usa_transportation_tax;
    }

    public double getUnited_states_customs_user_fee() {
        return united_states_customs_user_fee;
    }

    public void setUnited_states_customs_user_fee(double united_states_customs_user_fee) {
        this.united_states_customs_user_fee = united_states_customs_user_fee;
    }
}
