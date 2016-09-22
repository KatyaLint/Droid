package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 9/22/16.
 */
public class ChangeTripName  extends HGBAbstractFragment {

    public static void onClickListener(final Activity activity, final String solutionID, final UserTravelMainVO userTravelMainVO){

        LayoutInflater li = LayoutInflater.from(activity);
        final View promptsView = li.inflate(R.layout.popup_layout_change_iteinarary_name, null);
        final EditText input = (EditText) promptsView
                .findViewById(R.id.change_iteinarary_name);

       final FontTextView itirnarary_title_Bar = ((MainActivityBottomTabs)activity).getItirnaryTitleBar();

        itirnarary_title_Bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                input.setText(itirnarary_title_Bar.getText());
                HGBUtility.showAlertPopUp(activity, input, promptsView, activity.getResources().getString(R.string.edit_trip_name)
                        , activity.getResources().getString(R.string.save_button),
                        new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                itirnarary_title_Bar.setText(inputItem);
                                userTravelMainVO.setmSolutionName(inputItem);
                                sendNewSolutionName(inputItem, activity, solutionID);
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        });
            }
        });
    }


    static void sendNewSolutionName(String solutionName, Activity activity, String solutionID) {
        ConnectionManager.getInstance(activity).putItenararyTripName(solutionName, solutionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

            }

            @Override
            public void onError(Object data) {
             //   ErrorMessage(data);
            }
        });
    }
}
