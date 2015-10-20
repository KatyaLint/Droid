package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;

/**
 * Created by nyawka on 10/19/15.
 */
public class FlightGridMainFragment extends HGBAbtsractFragment {


    public FlightGridMainFragment(){}
    private UserTravelVO airplaneDataVO;

    public View createGridView(Activity activity,View rootView, UserTravelVO userTravelVO, View view, LayoutInflater inflater){
        TableLayout table = (TableLayout)rootView.findViewById(R.id.tlGridTable);

      //  LayoutInflater inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


  //      View tr = inflater.inflate(R.layout.grid_view_inner_item_horizontal_scroll, container,false);

        int counter = 0;


        ArrayList<PassengersVO> passengers = userTravelVO.getPassengerses();



        int columnNumber = passengers.size();
        int rowNumber = getMaxmimuRowNumber(passengers);

        initializeItems(rowNumber*columnNumber);

        System.out.println("Kate rowNumber =" + rowNumber);
        for(int i=0;i< rowNumber  ;i++){  // row
            row[i] = new TableRow(activity);
            System.out.println("Kate rowNumber i " + i);
            for(int j=0;j< columnNumber ;j++) {  //column
                System.out.println("Kate counter =" + counter);
             //   PassengersVO currentPassanger = passengers.get(j);
                child[counter] = inflater.inflate(R.layout.grid_view_inner_item_horizontal_scroll, null);
                textView[counter] = (TextView) child[counter].findViewById(R.id.flight_matos);
                textView[counter].setText("counter =" + counter + "row " + j) ;

                row[i].addView(child[counter]);
                counter ++;
            }
            table.addView(row[i]);
        }



//
//        for(int i=0;i<rowNumber;i++) {
//            table.addView(row[i]);
//        }


//        for(int i=0;i<rowNumber ;i++){  // row
//            row[i] = new TableRow(activity);
//            System.out.println("Kate rowNumber i " + i);
//          //  for(int j=0;j< rowNumber ;j++) {  //column
//                System.out.println("Kate counter =" + counter);
//                //   PassengersVO currentPassanger = passengers.get(j);
//                child[counter] = inflater.inflate(R.layout.grid_view_inner_item_horizontal_scroll, null);
//                textView[counter] = (TextView) child[counter].findViewById(R.id.flight_matos);
//                textView[counter].setText("counter =" + counter + "row " ) ;
//
//                row[i].addView(child[counter]);
//                counter ++;
//          //  }
//        }






        return rootView;
    }

    LinearLayout[] linaerLayout ;
    TextView[] textView ;
    TableRow[] row ;


    View[] child ;


    private void initializeItems(int itemNumber){
       linaerLayout = new LinearLayout[itemNumber];
       textView = new TextView[itemNumber];
       row = new TableRow[itemNumber];
       child = new View[itemNumber];
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
                maxRowNumberStub = maxRowNumberStub+nodes.size();
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


    public UserTravelVO getAirplaneDataVO() {
        return airplaneDataVO;
    }

    public void setAirplaneDataVO(UserTravelVO airplaneDataVO) {
        this.airplaneDataVO = airplaneDataVO;
    }
}
