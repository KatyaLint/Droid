package hellogbye.com.hellogbyeandroid.models.vo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nyawka on 9/7/15.
 */
public class IdVO {

    private MoneyVO mMoneyVO;

    @SerializedName("parentguid")
    private String mParentguid;
    @SerializedName("guid")
    private String mGuid;
    @SerializedName("primaryguid")
    private String mPrimaryguid; //TODO difference between mParentgui and mPrimaryguid

    public String getmParentguid() {
        return mParentguid;
    }

    public void setmParentguid(String mParentguid) {
        this.mParentguid = mParentguid;
    }

    public String getmGuid() {
        return mGuid;
    }

    public void setmGuid(String mGuid) {
        this.mGuid = mGuid;
    }

    public String getmPrimaryguid() {
        return mPrimaryguid;
    }

    public void setmPrimaryguid(String mPrimaryguid) {
        this.mPrimaryguid = mPrimaryguid;
    }



    public MoneyVO getmMoneyVO() {
        return mMoneyVO;
    }

    public void setmMoneyVO(MoneyVO mMoneyVO) {
        this.mMoneyVO = mMoneyVO;
    }
}
