package hellogbye.com.hellogbyeandroid.models.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 9/7/15.
 */
public class MoneyVO {
    @SerializedName("cost")
    private String mCost;
    @SerializedName("tax")
    private String mTax;

    public String getmCost() {
        return mCost;
    }

    public void setmCost(String mCost) {
        this.mCost = mCost;
    }

    public String getmTax() {
        return mTax;
    }

    public void setmTax(String mTax) {
        this.mTax = mTax;
    }
}
