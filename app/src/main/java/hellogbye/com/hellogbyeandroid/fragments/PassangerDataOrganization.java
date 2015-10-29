package hellogbye.com.hellogbyeandroid.fragments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;

/**
 * Created by nyawka on 10/27/15.
 */
public class PassangerDataOrganization {

    public ArrayList<PassangersVO> passangersVOs;

    public void organizeDataStructure(UserTravelVO userTravelVO){
        ArrayList<PassengersVO> passengers = userTravelVO.getPassengerses();


        passangersVOs = new ArrayList<PassangersVO>();


        for (int i = 0; i < passengers.size(); i++) {
            PassengersVO passenger = passengers.get(i);
            PassangersVO passangersVO = new PassangersVO();
            ArrayList<CellsVO> cells = passenger.getmCells();
            for (int j = 0; j < cells.size(); j++) {
                CellsVO cell = cells.get(j);
                ArrayList<NodesVO> nodes = correctDateInNode(cell);
                passangersVO.editHashMap(cell.getmDate(),nodes);

            }

            passangersVOs.add(passangersVO);
        }

        correctTheHashMapCells();
        for (int i = 0; i <passangersVOs.size() ; i++) {
            PassangersVO passenger = passangersVOs.get(i);
            Collections.sort(passenger.getPassengerNodes());
        }
    }

    private ArrayList<NodesVO> correctDateInNode(CellsVO cell){
        ArrayList<NodesVO> nodes = cell.getmNodes();
        for (NodesVO node:nodes){
            node.setDateOfCell(cell.getmDate());
        }

        return nodes;
    }


    private void correctTheHashMapCells(){
        HashMap<String, ArrayList<NodesVO>> hashMapTemp = passangersVOs.get(0).getHashMap();
        Set<String> keys = hashMapTemp.keySet();

        String[] keysArr = keys.toArray(new String[keys.size()]);
        int nodesSize = 0;
        int maxNodesSize = 0;
        for (int i = 0; i < keysArr.length; i++) {
            String currKey = keysArr[i];
            for (int j = 0; j < passangersVOs.size(); j++) {
                PassangersVO passangersVOsTemp = passangersVOs.get(j);
                HashMap<String, ArrayList<NodesVO>> hashMapPass = passangersVOsTemp.getHashMap();
                ArrayList<NodesVO> nodes = hashMapPass.get(currKey);
                nodesSize = nodes.size();
                if(maxNodesSize < nodesSize){
                    maxNodesSize = maxNodesSize + nodesSize;
                }

            }
            updateAllPassengersForMaximum(maxNodesSize, currKey);
            maxNodesSize = 0;
        }
    }

    private void updateAllPassengersForMaximum(int maxNodesSize, String currKey) {
        System.out.println("Kate maxNodesSize" + maxNodesSize);

        for (int j = 0; j < passangersVOs.size(); j++) {
            PassangersVO passangersVOsTemp = passangersVOs.get(j);
            HashMap<String, ArrayList<NodesVO>> hashMapPass = passangersVOsTemp.getHashMap();
            ArrayList<NodesVO> nodes = hashMapPass.get(currKey);
            if(nodes.size() < maxNodesSize){
                int nodesToAdd = maxNodesSize - nodes.size();
                for(int i=0;i<nodesToAdd;i++){
                    NodesVO nodesVO = new NodesVO();
                    nodesVO.setDateOfCell(currKey);
                    nodesVO.setmType("");
                    nodes.add(nodesVO);
                    passangersVOs.get(j).addToPassenger(nodesVO);
                }
            }
        }

    }

    public class PassangersVO {

        public ArrayList<NodesVO> passengerNodes = new ArrayList<>();

        public HashMap<String,ArrayList<NodesVO> > hashMap = new HashMap<>();

        public void editHashMap(String date,ArrayList<NodesVO> nodes){
            hashMap.put(date,nodes);
            passengerNodes.addAll(nodes);
        }

        public void addToPassenger(NodesVO node){
            passengerNodes.add(node);
        }

        public HashMap<String,ArrayList<NodesVO> > getHashMap(){
            return hashMap;
        }

        public ArrayList<NodesVO> getPassengerNodes(){
            return passengerNodes;
        }


    }





    private int getMaxmimuRowNumber(ArrayList<PassengersVO> passengers){
        int maxRowNumber = 0;
        int maxRowNumberStub = 0;
        int biggestRowNumber = 0;
        for(PassengersVO passanger:passengers){
            ArrayList<CellsVO> cells = passanger.getmCells();
            maxRowNumber = cells.size();
            for (CellsVO cell: cells){
                ArrayList<NodesVO> nodes = cell.getmNodes();
                if(!nodes.isEmpty()) {
                    maxRowNumberStub = maxRowNumberStub + nodes.size();
                }else{
                    maxRowNumberStub = maxRowNumberStub + 1;
                }
                if(maxRowNumberStub > maxRowNumber){
                    maxRowNumber = maxRowNumberStub;
                }
            }
            if(maxRowNumber > biggestRowNumber){
                biggestRowNumber = maxRowNumber;
            }

            maxRowNumberStub = 0;
        }

        return biggestRowNumber;
    }


    private void getMaximumNodes(UserTravelVO userTravelVO){

        ArrayList<PassengersVO> passengers = userTravelVO.getPassengerses();

        ArrayList<CellsVO> mCells = new ArrayList<CellsVO>();




        for(int i=0;i<passengers.size();i++){
            PassengersVO passenger = passengers.get(i);

            ArrayList<CellsVO> cells = passenger.getmCells();
            for(int j=0;j<cells.size();j++){

                ArrayList<NodesVO> nodesNewNodes = new ArrayList<>();

                CellsVO cell = cells.get(j);

                ArrayList<NodesVO> nodes = cell.getmNodes();




                for(int k=0;k<nodes.size();k++){

                }
            }
        }





    }



}
