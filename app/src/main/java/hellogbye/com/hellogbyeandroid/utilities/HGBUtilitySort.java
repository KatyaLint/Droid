package hellogbye.com.hellogbyeandroid.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;

/**
 * Created by nyawka on 11/15/16.
 */

public class HGBUtilitySort {


    public List<NodesVO>  sortData(String sortType, List<NodesVO> data) {
      if(sortType.equals("Price")){
          sortByPrice(data);
      }else{
          sortByAirline(data);
      }
        return data;
    }

    private void sortByAirline(List<NodesVO> data) {
        Collections.sort(data, new Comparator<NodesVO>() {
            @Override
            public int compare(NodesVO node2, NodesVO node1)
            {
                return node2.getmOperatorName().compareToIgnoreCase(node1.getmOperatorName());
            }
        });
    }


    private  void sortByPrice(List<NodesVO> data){
        Collections.sort(data, new Comparator<NodesVO>() {
            @Override
            public int compare(NodesVO node2, NodesVO node1)
            {
                if(node1.getCost() == node2.getCost()){
                    return 0;
                }
                return  node1.getCost() > node2.getCost() ? -1:1;
            }
        });
    }
}
