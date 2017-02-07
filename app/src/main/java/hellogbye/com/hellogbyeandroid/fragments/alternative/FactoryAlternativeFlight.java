package hellogbye.com.hellogbyeandroid.fragments.alternative;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageButton;

import com.google.gson.Gson;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by nyawka on 8/8/16.
 */
public class FactoryAlternativeFlight extends HGBAbstractFragment {

    public static Fragment newInstance(int position) {
        Fragment fragment = new FactoryAlternativeFlight();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isRoundTrip =  checkIfRoundTrip();
        Bundle args = new Bundle();



        if(isRoundTrip) {
            args.putBoolean(HGBConstants.BUNDLE_ROUND_TRIP, true);
            getFlowInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_ROUND_TRIP.getNavNumber(),args);
        }else{
            args.putBoolean(HGBConstants.BUNDLE_ROUND_TRIP, false);
            getFlowInterface().goToFragment(ToolBarNavEnum.ALTERNATIVE_FLIGHT_ONE_WAY_TRIP.getNavNumber(),args);
        }



        ((MainActivityBottomTabs)getActivity()).goToCncScreeButton();

    }


    private boolean getCurrentNode(){
        NodesVO currentNode;
        boolean isRoundBoolean = false;
        List<NodesVO> alternativeFlights = getActivityInterface().getAlternativeFlights();

        if (alternativeFlights != null) {
            isRoundBoolean = false;
         /*   currentNode = getNodeFromAlternative(alternativeFlights);
            if(currentNode.getParentflightid() != null){
                isRoundBoolean = true;
            }*/
            // primaryGuid = currentNode.getmPrimaryguid();
            //  isMyFlight = false;
        }else {
         /* UserTravelMainVO userOrder = getActivityInterface().getTravelOrder();
            currentNode = getLegWithGuid(userOrder);*/
            isRoundBoolean = isRoundTrip();
        }
      //  isRoundBoolean = isRoundTrip();
        return isRoundBoolean;
    }


    private boolean isRoundTrip(){
        UserTravelMainVO userOrder = getActivityInterface().getTravelOrder();
        Map<String, NodesVO> flightItems = userOrder.getItems();
        Collection<NodesVO> values = flightItems.values();
        for(NodesVO value : values){
            if(value.getParentflightid() != null ){
                return true;
            }
        }
        return false;
    }

    private boolean isRound(NodesVO currentNode){
        String primaryGuid = currentNode.getmPrimaryguid();

        Map<String, NodesVO> flightItems = getActivityInterface().getTravelOrder().getItems();
        //  nodeVO = flightItems.get(primaryGuid);
        Collection<NodesVO> nodesValue = flightItems.values();
        for(NodesVO node : nodesValue){
            if(node.getParentflightid() != null && node.getParentflightid().equals(primaryGuid)){
                return true;
            }
        }
        return false;
    }

    public NodesVO getNodeFromAlternative(List<NodesVO> alternativeFlights) {

        String selectedGuid = getSelectedGuid();
        for(NodesVO nodeVO:alternativeFlights){
            if(selectedGuid.equals(nodeVO.getmGuid())){
                return nodeVO;
            }
        }

        return null;
    }



    private boolean checkIfRoundTrip(  ){
        return getCurrentNode();
       /* NodesVO currentNode = getCurrentNode();
            if(currentNode.getParentflightid() != null){
                return true;
            }

        return false;*/
    }
}
