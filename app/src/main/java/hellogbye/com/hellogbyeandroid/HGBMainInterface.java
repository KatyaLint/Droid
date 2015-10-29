package hellogbye.com.hellogbyeandroid;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;

/**
 * Created by arisprung on 9/20/15.
 */
public interface HGBMainInterface  {

     void openVoiceToTextControl();
     void setTravelOrder(UserTravelVO travelorder);
    void setSolutionID(String userID);
    String getSolutionID();
     void setAlternativeFlights(List<AlternativeFlightsVO> alternativeFlightsVO);
     UserTravelVO getTravelOrder();
    List<AlternativeFlightsVO>  getAlternativeFlights();
}
