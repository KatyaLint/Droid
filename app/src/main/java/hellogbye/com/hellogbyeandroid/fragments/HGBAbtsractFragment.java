package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import org.apache.http.Header;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.HGBMainInterface;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;

/**
 * Created by arisprung on 9/20/15.
 */
public class HGBAbtsractFragment extends Fragment {

    private HGBMainInterface mActivityInterface;
    private static String selectedItemGuid;
    private static String selectedUserGuid;
    private CostumeToolBar mToolBar;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivityInterface = (HGBMainInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement HostingActivityInterface");
        }

     //   mActivityInterface.getToolBar().updateToolBarView(getArguments().getInt(HGBConstants.ARG_NAV_NUMBER));
    }




    protected HGBMainInterface getActivityInterface() {
        if (mActivityInterface != null) {
            return mActivityInterface;
        }
        return  null;


    }



    public NodesVO getLegWithGuid(UserTravelVO userOrder){
        String guid = getSelectedGuid();

        ArrayList<PassengersVO> passengers = userOrder.getPassengerses();
        for (PassengersVO passenger :passengers){
            ArrayList<CellsVO> cells = passenger.getmCells();
            for (CellsVO cell : cells){
                ArrayList<NodesVO> nodes = cell.getmNodes();
                for (NodesVO node: nodes){
                    if(node.getmGuid()!= null && node.getmGuid().equals(guid)){
                        node.setAccountID(passenger.getmPaxguid());
                        return node;
                    }
                }
            }
        }

        return null;
    }



    public String getPrimaryGuid(String guiSelected, List<NodesVO> alternative){

                for (NodesVO node: alternative){
                    if(node.getmGuid().equals(guiSelected)){
                        return node.getmPrimaryguid();
                    }
                }
        return null;
    }

    public CellsVO getCellWitGuid(UserTravelVO userOrder){
        String guid = getSelectedGuid();

        ArrayList<PassengersVO> passengers = userOrder.getPassengerses();
        for (PassengersVO passenger :passengers){
            ArrayList<CellsVO> cells = passenger.getmCells();
            for (CellsVO cell : cells){
                ArrayList<NodesVO> nodes = cell.getmNodes();
                for (NodesVO node: nodes){
                    if(node.getmGuid()!= null && node.getmGuid().equals(guid)){
                        node.setAccountID(passenger.getmPaxguid());
                        return cell;
                    }
                }
            }
        }
        return null;
    }

    public PassengersVO getTravellerWitGuid(UserTravelVO userOrder){
        String guid = getSelectedUserGuid();

        ArrayList<PassengersVO> passengers = userOrder.getPassengerses();
        for (PassengersVO passenger :passengers){
            if(guid.equals(passenger.getmPaxguid())){
                return passenger;
            }
        }

        return null;
    }



    public void selectedItemGuidNumber(String guidSlected){
        this.selectedItemGuid = guidSlected;

    }


    public void selectedUserGuidNumber(String guidSelectedUser) {
        this.selectedUserGuid = guidSelectedUser;
    }
    public String getSelectedGuid(){
        return selectedItemGuid;
    }

    public String getSelectedUserGuid(){
        return this.selectedUserGuid;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

//    private void restartApp() {
////        Intent intent = new Intent(getActivity().getApplicationContext(), PickerFragmentActivity.class);
////        getActivity().startActivity(intent);
//
//        Intent i = getActivity().getBaseContext().getPackageManager()
//                .getLaunchIntentForPackage( getActivity().getBaseContext().getPackageName() );
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(i);
//    }

    protected void handleRequestFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        error.printStackTrace();



        String response = "";

        if (responseBody == null) { //no internet
            response = "No Internet Connection\n\nConnect to Wifi to continue";
        } else {
            try {
                response = new String(responseBody, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String title = "Server Error";
        String msg = "(" + statusCode + ") " + response;

        errorDialog(title, msg);
    }

    protected void serverOutputErrorDialog(byte[] responseBody) {
        String response = "";
        try {
            response = new String(responseBody, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        errorDialog(
                "Unexpected Data",
                "The server has returned an unexpected response:\n\n"
                        + response.substring(0, Math.min(500, response.length()))
        );
    }

    protected void errorDialog(String title, String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Error: " + title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    protected void handleRequestFailure(String error) {


        errorDialog("Error", error);
    }


}
