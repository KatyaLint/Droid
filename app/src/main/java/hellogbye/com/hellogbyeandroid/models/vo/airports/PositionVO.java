package hellogbye.com.hellogbyeandroid.models.vo.airports;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 2/7/16.
 */
public class PositionVO {
    @SerializedName("start")
    private double start;
    @SerializedName("end")
    private double end;


    public double getStart() {
        return start;
    }

    public void setStart(double start) {
        this.start = start;
    }

    public double getEnd() {
        return end;
    }

    public void setEnd(double end) {
        this.end = end;
    }
}
