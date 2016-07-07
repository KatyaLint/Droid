package hellogbye.com.hellogbyeandroid.fragments.settings;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.activities.StartingMenuActivity;
import hellogbye.com.hellogbyeandroid.adapters.settingaccount.AccountSettingsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 8/17/15.
 */
public class AccountPersonalInfoHelpAndFeedbackFragment extends HGBAbstractFragment {


    private View promptsView;
    private FontEditTextView account_submit_edit_text;

    public static Fragment newInstance(int position) {
        Fragment fragment = new AccountPersonalInfoHelpAndFeedbackFragment();

        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.account_settings_help_feedback, container, false);
       // View rootView = inflater.inflate(R.layout.account_settings_help_feedback_test, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState)  {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
      //  View rootView = inflater.inflate(R.layout.account_settings_help_feedback, container, false);

        LayoutInflater li = LayoutInflater.from(getActivity());
        promptsView = li.inflate(R.layout.popup_alert_layout, null);

       final FontButtonView account_submit_feedback = (FontButtonView) rootView.findViewById(R.id.account_submit_feedback);
        account_submit_feedback.setEnabled(false);

      account_submit_edit_text = (FontEditTextView) rootView.findViewById(R.id.account_submit_edit_text);
        account_submit_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(charSequence.length()>=1){
                    account_submit_feedback.setEnabled(true);
                }else{
                    account_submit_feedback.setEnabled(false);
                }

            }

            public void afterTextChanged(Editable s) {

            }
        });


        account_submit_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendFeedback();
            }
        });


    }


    private void sendFeedback(){

        ConnectionManager.getInstance(getActivity()).postSubmitFeedback(account_submit_edit_text.getText().toString(),new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                HGBUtility.showAlertPopUpOneButton(getActivity(), null, promptsView, getResources().getString(R.string.feedback_title),
                        new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                IBinder token = account_submit_edit_text.getWindowToken();
                                ((InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );


                                ((MainActivity)getActivity()).onBackPressed();
                            }
                            @Override
                            public void itemCanceled() {
                            }
                        });
            }



            @Override
            public void onError(Object data) {
                ErrorMessage(data);

            }
        });
    }

}