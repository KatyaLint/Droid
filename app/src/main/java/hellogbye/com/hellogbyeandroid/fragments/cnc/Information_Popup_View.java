package hellogbye.com.hellogbyeandroid.fragments.cnc;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.HGBFlowInterface;
import hellogbye.com.hellogbyeandroid.activities.HGBMainInterface;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;




/**
 * Created by nyawka on 7/16/17.
 */

public class Information_Popup_View {

    private Activity activity;
    private HGBFlowInterface flowInterface;
    private HGBMainInterface mainInterface;
    private CNCSignalRFragment.IClearCNC iClearCNC;
    private boolean isFreeUser ;

    public Information_Popup_View(Activity activity, final HGBFlowInterface flowInterface, HGBMainInterface activityInterface, CNCSignalRFragment.IClearCNC iClearCNC, boolean isFreeUser){
        this.activity = activity;
        this.flowInterface = flowInterface;
        this.mainInterface = activityInterface;
        this.iClearCNC = iClearCNC;
        this.isFreeUser = isFreeUser;
    }


    public void freeUserPopUp(final boolean isCNCScreen){

        LayoutInflater li = LayoutInflater.from(activity);

        final View popup_cnc_add_dialog = li.inflate(R.layout.popup_cnc_add_dialog, null);
      /*  FontTextView popup_flight_baggage_text = (FontTextView) popupView.findViewById(R.id.popup_flight_baggage_text);
        String currency = getActivityInterface().getCurrentUser().getCurrency();
        String text = String.format(getActivity().getResources().getString(R.string.popup_flight_baggage_info_text),currency );
        popup_flight_baggage_text.setText(text);*/



        LinearLayout cnc_add_dialog_add_trip = (LinearLayout)popup_cnc_add_dialog.findViewById(R.id.cnc_add_dialog_add_trip);
        cnc_add_dialog_add_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClearCNC.clearCNCScreen();// clearCNCItems();
                if(!isCNCScreen) {
                    Bundle args = new Bundle();
                    args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
                    flowInterface.goToFragment(ToolBarNavEnum.CNC.getNavNumber(), args);
                }
                HGBUtility.dialog.cancel();
            }
        });


        LinearLayout cnc_add_dialog_add_companion = (LinearLayout)popup_cnc_add_dialog.findViewById(R.id.cnc_add_dialog_add_companion);
        cnc_add_dialog_add_companion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle arg = new Bundle();

                //Kate check
                arg.putBoolean(HGBConstants.BUNDLE_ADD_COMPANION_CNC, true);
                //flowInterface.goToFragment(ToolBarNavEnum.COMPANIONS.getNavNumber(), null);
                flowInterface.goToFragment(ToolBarNavEnum.ALL_COMPANIONS_VIEW.getNavNumber(), arg);

                HGBUtility.dialog.cancel();
            }
        });


        final LinearLayout cnc_add_dialog_favorites = (LinearLayout) popup_cnc_add_dialog.findViewById(R.id.cnc_add_dialog_favorites);
        cnc_add_dialog_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorityItinerary(cnc_add_dialog_favorites);
                HGBUtility.dialog.cancel();
            }
        });




        FontTextView cnc_add_dialog_favorites_text  =  (FontTextView)cnc_add_dialog_favorites.findViewById(R.id.cnc_add_dialog_favorites_text);
        ImageView add_to_favorites_img = (ImageView)cnc_add_dialog_favorites.findViewById(R.id.add_to_favorites_img);
        ImageView remove_to_favorites_img = (ImageView)cnc_add_dialog_favorites.findViewById(R.id.remove_to_favorites_img);

        System.out.println("Kate isFreeUser =" + isFreeUser);

        if (mainInterface.getTravelOrder() != null && !mainInterface.getTravelOrder().ismIsFavorite()) {
            cnc_add_dialog_favorites_text.setText(R.string.cnc_add_dialog_add_favorites);
            add_to_favorites_img.setVisibility(View.VISIBLE);
            remove_to_favorites_img.setVisibility(View.GONE);
        } else {
            cnc_add_dialog_favorites_text.setText(R.string.cnc_add_dialog_remove_favorites);
            add_to_favorites_img.setVisibility(View.GONE);
            remove_to_favorites_img.setVisibility(View.VISIBLE);
        }


        if( mainInterface.getCNCItems().size() > 2){
            cnc_add_dialog_add_companion.setVisibility(View.VISIBLE);
            cnc_add_dialog_favorites.setVisibility(View.VISIBLE);
        }else{
            cnc_add_dialog_add_companion.setVisibility(View.GONE);
            cnc_add_dialog_favorites.setVisibility(View.GONE);
        }

        if(isFreeUser) { //leave only add new trip
            cnc_add_dialog_add_companion.setVisibility(View.GONE);
            cnc_add_dialog_favorites.setVisibility(View.GONE);
        }

        HGBUtility.showAlertPopUp(activity, null, popup_cnc_add_dialog,
                null, null, new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {

                    }

                    @Override
                    public void itemCanceled() {

                    }
                });


    }

    private void setFavorityItinerary(final LinearLayout cnc_add_dialog_favorites) {
        UserTravelMainVO travelOrder = mainInterface.getTravelOrder();
        final String solutionID = travelOrder.getmSolutionID();
        boolean isFavorite = travelOrder.ismIsFavorite();

        ConnectionManager.getInstance(activity).putFavorityItenarary(!isFavorite, solutionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                FontTextView cnc_add_dialog_favorites_text  =  (FontTextView)cnc_add_dialog_favorites.findViewById(R.id.cnc_add_dialog_favorites_text);
                if (mainInterface.getTravelOrder().ismIsFavorite()) {
                    cnc_add_dialog_favorites_text.setText(R.string.cnc_add_dialog_add_favorites);
                } else {
                    cnc_add_dialog_favorites_text.setText(R.string.cnc_add_dialog_remove_favorites);
                }

                getFavorityCurrentItinerary(solutionID);
            }

            @Override
            public void onError(Object data) {

               // ErrorMessage(data);
            }
        });
    }


    private void getFavorityCurrentItinerary(String solutionId) {

        ConnectionManager.getInstance(activity).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;
                mainInterface.setTravelOrder(userTravelMainVO);

            }

            @Override
            public void onError(Object data) {
              //  ErrorMessage(data);
            }
        });
    }


}
