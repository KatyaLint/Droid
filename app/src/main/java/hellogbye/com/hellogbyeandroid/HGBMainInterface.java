package hellogbye.com.hellogbyeandroid;

import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;

/**
 * Created by arisprung on 9/20/15.
 */
public interface HGBMainInterface  {

    public void openVoiceToTextControl();
    public void setTravelOrder(UserTravelVO travelorder);
    public UserTravelVO getTravelOrder();
}
