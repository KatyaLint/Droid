package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import hellogbye.com.hellogbyeandroid.models.NodeTypeEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;

/**
 * Created by nyawka on 7/13/17.
 */

public class ItineraryInitializationNodes {


    public void makeEqualAllNodes(UserTravelMainVO user){
        ArrayList<PassengersVO> passangers = user.getPassengerses();

        for(PassengersVO passenger : passangers){
            HashMap<String, ArrayList<NodesVO>> hashMap = passenger.getHashMap();
            Set<String> dates = hashMap.keySet();
            for (String date:dates) {
                ArrayList<NodesVO> nodes = hashMap.get(date);
                int nodesSize = nodes.size();
                int maxNodeSize = runForPassangers(passangers, nodesSize, date); //get Maximum size of nodes to add to another users
                //  if(nodesSize < maxNodeSize){
                addToPassangersAllNodes(passangers, maxNodeSize, date);
                // }
            }
        }
    }

    private void addToPassangersAllNodes(ArrayList<PassengersVO> passangers,int maxNodeSize, String date){
        for(PassengersVO passenger : passangers) {
            HashMap<String, ArrayList<NodesVO>> hashMap = passenger.getHashMap();
            ArrayList<NodesVO> dates = hashMap.get(date);
            int correntSize = 0;
            if(dates != null){
                correntSize = dates.size();
            }else{
                dates = new ArrayList<>();
            }

            int maxNodesToAdd = maxNodeSize - correntSize;

            for (int i=0;i<maxNodesToAdd;i++){
                NodesVO nodesVO = new NodesVO();
                nodesVO.setEmpty(true);
                dates.add(nodesVO);
            }
            if(maxNodesToAdd > 0) {
                passenger.setDateHashMap(date, dates);
            }
        }

    }

    private int runForPassangers(ArrayList<PassengersVO> passangers, int nodesSize, String date){

        for(PassengersVO passenger : passangers) {
            HashMap<String, ArrayList<NodesVO>> hashMap = passenger.getHashMap();
            ArrayList<NodesVO> dates = hashMap.get(date);
            if(dates != null) {
                int nodesSizeTemp = dates.size();
                if (nodesSize < nodesSizeTemp) {
                    nodesSize = nodesSizeTemp;
                }
            }
        }
        return nodesSize;
    }







    public void createMainNodes(UserTravelMainVO user){
        Map<String, NodesVO> items = user.getItems();

        ArrayList<PassengersVO> passangers = user.getPassengerses();
        ArrayList<NodesVO> nodesVOs = new ArrayList<>();

        String arrival = "";
        String departure = "";
        for (PassengersVO passenger: passangers){
            ArrayList<NodesVO> passengerNodesVOs = new ArrayList<>();
            ArrayList<String> ItineraryItems = passenger.getmItineraryItems();
            for (String itineraryItem :ItineraryItems){

                NodesVO node = items.get(itineraryItem);
                long difference = 0;
                if(NodeTypeEnum.FLIGHT.getType().equals(node.getmType())){
                    departure = node.getmDeparture();
                    arrival = node.getmArrival();
                    difference = HGBUtilityDate.dayDifference(departure,arrival) ;
                }else if(NodeTypeEnum.HOTEL.getType().equals(node.getmType())){
                    departure = node.getmCheckIn();
                    arrival = node.getmCheckOut();
                    difference = HGBUtilityDate.nightDifference(departure,arrival) ;
                }

                //  long difference = HGBUtilityDate.dayDifference(departure,arrival) ;

                departure = HGBUtilityDate.parseDateToddMMyyyy(departure);
                //    departure = HGBUtility.parseDateToEEEMMMDyy(departure); //parse return 23 is 2 different scenario need time not just date
                //      HGBUtility.parseDateToddMMyyyyMyTrip(departure);
                if(node.getmType().equals(NodeTypeEnum.HOTEL.getType())){
                    String time = HGBUtilityDate.addDayHourToDate(departure);
                    node.setDateOfCell(time);
                }else {
                    node.setDateOfCell(departure);
                }
                //  node.setDateOfCell(departure);
                node.setUserName(passenger.getmName());
                node.setAccountID(passenger.getmPaxguid());
                nodesVOs.add(node);

                correctPassangerNodes(node, passengerNodesVOs,  passenger);


                for(int i=1;i<=difference;i++){ //adding days, if hotel is 3 day, adding another 2

                    departure =  HGBUtilityDate.addDayToDate(departure);

                    node.setUserName(passenger.getmName());
                    node.setDateOfCell(departure);
                    correctPassangerNodes(node, passengerNodesVOs,  passenger);
                    nodesVOs.add(node);

                }

            }

            if(passengerNodesVOs == null || passengerNodesVOs.get(0) == null){
                //TODO chack why!!!!!
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError("Bad Case Scenario");
            }
            // passenger.addToPassengerNodeVOS(nodesVOs);

            passenger.setDateHashMap(passengerNodesVOs.get(0).getDateOfCell(), passengerNodesVOs);
        }
    }


    private void correctPassangerNodes(NodesVO node, ArrayList<NodesVO> passangerNodesVOs, PassengersVO passanger){

        if(!passangerNodesVOs.isEmpty() ){    //&& !node.getDateOfCell().equals(passangerNodesVOs.get(0).getDateOfCell())){
            String dateOfCell = passangerNodesVOs.get(0).getDateOfCell();
            if(!node.getDateOfCell().equals(dateOfCell)) {
                ArrayList<NodesVO> passangerNodesVOsTemp = new ArrayList<>();
                passangerNodesVOsTemp.addAll(passangerNodesVOs);
                passanger.setDateHashMap(passangerNodesVOs.get(0).getDateOfCell(), passangerNodesVOsTemp);

                passangerNodesVOs.clear();
            }
        }

        passangerNodesVOs.add(node.getClone());
    }

}
