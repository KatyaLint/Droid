package hellogbye.com.hellogbyeandroid.fragments.settings;


import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;

/**
 * Created by arisprung on 8/17/15.
 */
public class AccountPersonalInfoHelpAndFeedbackFragment extends HGBAbstractFragment {


    private View promptsView;
    private FontEditTextView account_submit_edit_text;
    private ScrollView mFeedbackRelativeLayout;

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
        mFeedbackRelativeLayout = (ScrollView)rootView.findViewById(R.id.feedback_rl);
       // View rootView = inflater.inflate(R.layout.account_settings_help_feedback_test, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, Bundle savedInstanceState)  {

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

        mFeedbackRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = mFeedbackRelativeLayout.getRootView().getHeight() - mFeedbackRelativeLayout.getHeight();
                if (heightDiff > HGBUtility.dpToPx(getActivity().getApplicationContext(), 200)) { // if more than 200 dp, it's probably a keyboard...
                    mFeedbackRelativeLayout.fullScroll(ScrollView.FOCUS_DOWN);
                }
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