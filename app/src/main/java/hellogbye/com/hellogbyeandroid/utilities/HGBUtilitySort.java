package hellogbye.com.hellogbyeandroid.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;

/**
 * Created by nyawka on 11/15/16.
 */

public class HGBUtilitySort {

    private String selectedGuid;
    public List<NodesVO>  sortData(String sortType, List<NodesVO> data, String strSelectedGuid) {
        this.selectedGuid = strSelectedGuid;
      if(sortType.equals("Price")){
          sortByPrice(data);
      }else if(sortType.equals("Airline")){
          sortByAirline(data);
      }else if(sortType.equals("Duration")){
          sortByDuration(data);
      }else {
          sortByNumberOfStops(data);
      }
      return data;
    }

    private void sortByAirline(List<NodesVO> data) {
        Collections.sort(data, new Comparator<NodesVO>() {
            @Override
            public int compare(NodesVO node2, NodesVO node1)
            {
                if(node1.getmGuid().equals(selectedGuid) ||  node2.getmGuid().equals(selectedGuid)){
                    return 1;
                }
                return node2.getmOperatorName().compareToIgnoreCase(node1.getmOperatorName());
            }
        });
    }

    private int calculateMinutes(NodesVO node){
     //   String travelTime = node.getmTravelTime().trim();
        String[] resultHours = node.getmTravelTime().split("h");
        String[] resultMinutes = resultHours[1].split("m");



        int travelMinutes = Integer.parseInt(resultHours[0])*60+Integer.parseInt(resultMinutes[0].trim());

        return travelMinutes;
    }

    private void sortByDuration(List<NodesVO> data) {

        final SimpleDateFormat sdf = new SimpleDateFormat("hh mm");
       /* Date date1 = sdf.parse("2009-12-31");
        Date date2 = sdf.parse("2010-01-31");*/

        Collections.sort(data, new Comparator<NodesVO>() {
            @Override
            public int compare( NodesVO node1, NodesVO node2)
            {

                if(node1.getmGuid().equals(selectedGuid) ||  node2.getmGuid().equals(selectedGuid)){
                    return 1;
                }

                int travelMinutes1 = calculateMinutes(node1);
                int travelMinutes2 = calculateMinutes(node2);

               /* if(travelMinutes1 >= travelMinutes2){
                    return -1;
                }
                return 1;*/


                if(travelMinutes1 == travelMinutes2){
                    return 0;
                }
                return  travelMinutes1 > travelMinutes2 ? -1:1;
               /* Date date1 = new Date();
                Date date2 = new Date();
                try {
                     date1 = sdf.parse(node1.getmTravelTime());
                     date2 = sdf.parse(node2.getmTravelTime());
                } catch (ParseException e) {
                    System.out.println("Kate error");
                    e.printStackTrace();
                }
               if(date1.before(date2)){
                   return 1;
               }*/

                //return node2.getmTravelTime().compareToIgnoreCase(node1.getmTravelTime());
            }
        });
    }

    private void sortByNumberOfStops(List<NodesVO> data) {


        Collections.sort(data, new Comparator<NodesVO>() {
            @Override
            public int compare(NodesVO node2, NodesVO node1)
            {
                if(node1.getmGuid().equals(selectedGuid) ||  node2.getmGuid().equals(selectedGuid)){
                    return 1;
                }

                int length2 = node2.getLegs().size() - 1;
                int length1 = node1.getLegs().size() - 1;
                /*if(length1 > length2){
                    return -1;
                }
                return 1;*/

                if(length2 == length1){
                    return 0;
                }
                return  length1 > length2 ? -1:1;


              //  return node2.getLegs().size().compareToIgnoreCase(node1.getLegs().size());
            }
        });
    }

    private  void sortByPrice(List<NodesVO> data){
        Collections.sort(data, new Comparator<NodesVO>() {
            @Override
            public int compare(NodesVO node2, NodesVO node1)
            {
                if(node1.getmGuid().equals(selectedGuid) ||  node2.getmGuid().equals(selectedGuid)){
                    return 1;
                }

                if(node1.getCost() == node2.getCost()){
                    return 0;
                }
                return  node1.getCost() > node2.getCost() ? -1:1;
            }
        });
    }
}
