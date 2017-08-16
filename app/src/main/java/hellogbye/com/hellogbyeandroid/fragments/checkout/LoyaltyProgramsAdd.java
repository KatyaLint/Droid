package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by amirlubashevsky on 13/08/2017.
 */

public class LoyaltyProgramsAdd extends HGBAbstractFragment {

    private RecyclerView checkout_recycle_view_preferences;
    private CheckoutLoyltyAdapter checkoutLoyltyAdapter;
  // private List<AirlinePointsProgramVO> staticAirlinePointsProgramCurrent;

    public static Fragment newInstance(int position) {
        Fragment fragment = new LoyaltyProgramsAdd();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    /** The system calls this only when creating the layout in a dialog. */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    private void loyaltyProgrammPopupMembershipCode(final AirlinePointsProgramVO selectedProgramm){
        LayoutInflater li = LayoutInflater.from(getActivity());
        final View promptsView = li.inflate(R.layout.popup_layout_loyalty_membership_code, null);
        final EditText input = (EditText) promptsView
                .findViewById(R.id.loyalty_membership_code_edittext);

        FontTextView lyoalty_membership_name = (FontTextView)promptsView.findViewById(R.id.lyoalty_membership_name);
        lyoalty_membership_name.setText(selectedProgramm.getProgramname());




              //  input.setText(itirnarary_title_Bar.getText());
                HGBUtility.showAlertPopUp(getActivity(), input, promptsView, null
                        , getActivity().getResources().getString(R.string.save_button),
                        new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {

                                List<AirlinePointsProgramVO> userAirlineProgram = getActivityInterface().getUserAirlinePointsProgram();
                                userAirlineProgram.add(selectedProgramm);
                                checkoutLoyltyAdapter.updateItems(userAirlineProgram);

//                                itirnarary_title_Bar.setText(inputItem);
//                                userTravelMainVO.setmSolutionName(inputItem);
//                                sendNewSolutionName(inputItem, activity, solutionID);
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivityInterface().setSelectedProgram(null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getFlowInterface().enableFullScreen(true);

        AirlinePointsProgramVO selectedProgramm = getActivityInterface().getSelectedProgram();
        if(selectedProgramm != null){
            loyaltyProgrammPopupMembershipCode(selectedProgramm);
        }


        // Inflate the layout to use as dialog or embedded fragment
        View rootView = inflater.inflate(R.layout.checkout_loyalty_program_first_screen, container, false);

        FontTextView user_loyalty_programm_name = (FontTextView)rootView.findViewById(R.id.user_loyalty_programm_name);
        user_loyalty_programm_name.setText("No Preferences");

        checkout_recycle_view_preferences = (RecyclerView)rootView.findViewById(R.id.checkout_recycle_view_preferences);
        checkout_recycle_view_preferences.setLayoutManager(new LinearLayoutManager(getContext()));

        //checkout_recycle_view.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        List<AirlinePointsProgramVO> userAirlineProgram = getActivityInterface().getUserAirlinePointsProgram();

        checkoutLoyltyAdapter = new CheckoutLoyltyAdapter(userAirlineProgram);
        checkoutLoyltyAdapter.updateItems(userAirlineProgram);
        checkout_recycle_view_preferences.setAdapter(checkoutLoyltyAdapter);


        FontButtonView layout_program_add_btn = (FontButtonView) rootView.findViewById(R.id.layout_program_add_btn);
        layout_program_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFlowInterface().goToFragment(ToolBarNavEnum.LOYLTY_NEW_PROGRAMME.getNavNumber(), null);
            }
        });

        return rootView;
    }



}
