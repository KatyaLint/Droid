package hellogbye.com.hellogbyeandroid.models;

/**
 * Created by arisprung on 3/29/16.
 */
public class PaymentSummaryItem {

    private String name;
    private String total;
    private String last4;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }
}
