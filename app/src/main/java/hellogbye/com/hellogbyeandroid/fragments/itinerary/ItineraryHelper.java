package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import android.app.Activity;
import android.os.Bundle;

import java.util.List;

import hellogbye.com.hellogbyeandroid.activities.HGBMainInterface;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by nyawka on 6/19/17.
 */

public class ItineraryHelper {
    public static void getAlternativeFlights(String paxID ,String flightid, final HGBMainInterface hgbMainInterface, Activity activity,final IntItineraryHelper intItineraryHelper) {

        String solutionID = hgbMainInterface.getTravelOrder().getmSolutionID();

        String paxId = paxID; //getSelectedUserGuid();
        String flightID = flightid;


        /*  String solutionID = getActivityInterface().getTravelOrder().getmSolutionID();

        String paxId = getSelectedUserGuid();
        String flightID = currentNodeVO.getmPrimaryguid();*/


        ConnectionManager.getInstance(activity).getAlternateFlightsForFlight(solutionID, paxId, flightID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                System.out.println("Kate getAlternateFlightsForFlight onSuccess");

                List<NodesVO> alternativeFlightsVOs = (List<NodesVO>)data;//gson.fromJson((String) data, listType);

                hgbMainInterface.setAlternativeFlights(alternativeFlightsVOs);
                intItineraryHelper.finished();

            }

            @Override
            public void onError(Object data) {
                intItineraryHelper.errorReceived(data);

                //  ErrorMessage(data);
            }
        });
    }


    public static void getAlternativeHotel(String paxID ,String flightid, String checkin, String checkout, boolean isPromoAlternative, final HGBMainInterface hgbMainInterface, Activity activity,final IntItineraryHelper intItineraryHelper) {

        String solutionID = hgbMainInterface.getTravelOrder().getmSolutionID();

        String paxId = paxID; //getSelectedUserGuid();
        String flightID = flightid;

        System.out.println("Kate getAlternativeHotel onSuccess");
        /*  String solutionID = getActivityInterface().getTravelOrder().getmSolutionID();

        String paxId = getSelectedUserGuid();
        String flightID = currentNodeVO.getmPrimaryguid();*/


        ConnectionManager.getInstance(activity).getAlternativeHotelForFlightV2( solutionID, paxId, checkin,  checkout,  isPromoAlternative, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {


                List<NodesVO> alternativeHotelsVOs = (List<NodesVO>)data;//gson.fromJson((String) data, listType);


                hgbMainInterface.setAlternativeHotels(alternativeHotelsVOs);
                intItineraryHelper.finished();

            }

            @Override
            public void onError(Object data) {
                intItineraryHelper.errorReceived(data);

                //  ErrorMessage(data);
            }
        });
    }
}
