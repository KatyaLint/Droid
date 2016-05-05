package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import hellogbye.com.hellogbyeandroid.activities.HGBFlowInterface;
import hellogbye.com.hellogbyeandroid.activities.HGBMainInterface;
import hellogbye.com.hellogbyeandroid.activities.HGBVoiceInterface;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.views.CostumeToolBar;

/**
 * Created by arisprung on 9/20/15.
 */
public class HGBAbstractFragment extends Fragment {

    private HGBMainInterface mActivityInterface;
    private HGBFlowInterface mHGBFlowInterface;
    private HGBVoiceInterface mHGBVoiceInterface;
    private static String selectedItemGuid;
    private static String selectedUserGuid;
    private CostumeToolBar mToolBar;
    private static String settingGuidSelected;



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mHGBFlowInterface  = (HGBFlowInterface) getActivity();
            mHGBVoiceInterface  = (HGBVoiceInterface) getActivity();

            mActivityInterface = ((MainActivity) activity).getHGBSaveDataClass();

        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement HostingActivityInterface");
        }

    }

    protected HGBVoiceInterface getVoiceInterface() {
        if (mHGBVoiceInterface != null) {
            return mHGBVoiceInterface;
        }
        return  null;
    }

    protected HGBFlowInterface getFlowInterface() {
        if (mHGBFlowInterface != null) {
            return mHGBFlowInterface;
        }
        return  null;
    }

    protected HGBMainInterface getActivityInterface() {
        if (mActivityInterface != null) {
            return mActivityInterface;
        }
        return  null;
    }

    public NodesVO getLegWithGuid(UserTravelMainVO userOrder){
        String guid = getSelectedGuid();
        Map<String, NodesVO> itemsMap = userOrder.getItems();
        NodesVO userNode = itemsMap.get(guid);
        return userNode;
    }


    public NodesVO getNodeWithGuidAndPaxID(String selectedItemGuid){
        Map<String, NodesVO> items = getActivityInterface().getTravelOrder().getItems();
        NodesVO node = items.get(selectedItemGuid);
        return node;
    }


    public CreditCardItem getCreditCard(String token){

        for(CreditCardItem card:getActivityInterface().getCreditCards())
        {
            if(card.getToken().equals(token)){
                return card;
            }
        }
        return null;
    }


//    public ArrayList<NodesVO> getNodeWithGuidAndPaxID(String selectedItemGuid,String paxid){
//
//        ArrayList<NodesVO> listVo = new ArrayList<>();
//        ArrayList<PassengersVO> passengers = getActivityInterface().getTravelOrder().getPassengerses();
//        for (PassengersVO passenger :passengers){
//            if(passenger.getmPaxguid().equals(paxid)){
//                ArrayList<CellsVO> cells = passenger.getmCells();
//                for (CellsVO cell : cells){
//                    ArrayList<NodesVO> nodes = cell.getmNodes();
//                    for (NodesVO node: nodes){
//                        if(node.getmGuid()!= null && node.getmGuid().equals(selectedItemGuid)){
//                            listVo.add(node);
//                        }
//                    }
//                }
//            }
//
//        }
//
//        return listVo;
//    }


    public String getPrimaryGuid(String guiSelected, List<NodesVO> alternative){

                for (NodesVO node: alternative){
                    if(node.getmGuid().equals(guiSelected)){
                        return node.getmPrimaryguid();
                    }
                }
        return null;
    }


    public PassengersVO getTravellerWitGuid(UserTravelMainVO userOrder){
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


    public String getSettingGuidSelected() {
        return settingGuidSelected;
    }

    public void setSettingGuidSelected(final String settingGuidSelected) {
        this.settingGuidSelected = settingGuidSelected;
    }
}
