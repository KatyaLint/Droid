//package hellogbye.com.hellogbyeandroid.activities;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import hellogbye.com.hellogbyeandroid.R;
//import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
//import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
//import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
//import hellogbye.com.hellogbyeandroid.onboarding.OnBoardingPager;
//import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
//import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
//import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
//import hellogbye.com.hellogbyeandroid.views.FontTextView;
//
///**
// * Created by arisprung on 9/8/15.
// */
//public class LoginActivity extends BaseActivity {
//
//    private EditText mEmailEditText;
//    private EditText mPasswordEditText;
//    private CheckBox mRemmeberMeCheckbox;
//    private FontTextView mLoginTextView;
//    private HGBPreferencesManager hgbPrefrenceManager;
//    private FontTextView mForgotPasswordTextView;
//    private boolean remember_me;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.login_layout);
//
//        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
//        mEmailEditText = (EditText) findViewById(R.id.email);
//        mPasswordEditText = (EditText) findViewById(R.id.password);
//        mRemmeberMeCheckbox = (CheckBox) findViewById(R.id.remmember_me_checkbox);
//        mLoginTextView = (FontTextView) findViewById(R.id.user_login);
//        mForgotPasswordTextView = (FontTextView) findViewById(R.id.forgotpassword);
//
//        remember_me = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.REMMEMBER_ME,false);
//        String email = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, null);
//
//
//        if(remember_me && email != null){
//            mEmailEditText.setText(email);
//            String pswd = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_PSWD, null);
//            if(pswd != null){
//                mPasswordEditText.setText(pswd);
//            }
//        }
//
//
//        mForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //TODO forgot password
//                resetPassword();
//            }
//        });
//
//
//        mLoginTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.REMMEMBER_ME, mRemmeberMeCheckbox.isChecked());
//
//                ConnectionManager.getInstance(LoginActivity.this).login(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString(),
//                        new ConnectionManager.ServerRequestListener() {
//                    @Override
//                    public void onSuccess(Object data) {
//                        UserLoginCredentials user = (UserLoginCredentials) data;
//                        hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_USER_IS_LOGIN_IN_PAST, true);
//                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
//                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, user.getUserprofileid());
//
//                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, mEmailEditText.getText().toString());
//                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_PSWD, mPasswordEditText.getText().toString());
//                        hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER,false);
//                        goToMainActivity();
//                    }
//
//                    @Override
//                    public void onError(Object data) {
//                        HGBErrorHelper errorHelper = new HGBErrorHelper();
//                        errorHelper.setMessageForError((String) data);
//                        errorHelper.show(getFragmentManager(), (String) data);
//                    }
//                });
//            }
//        });
//    }
//
//
//
//    private void goToMainActivity() {
//
//        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
//        boolean doesExist = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.TRAVEL_PREF_ENTRY, true);
//
//        if (doesExist) {
//            Intent intent = new Intent(getApplicationContext(), MainActivityBottomTabs.class);
//            startActivity(intent);
//        } else {
// //           Intent intent = new Intent(getApplicationContext(), TravelPrefrenceStartingActivity.class);
//            Intent intent = new Intent(getApplicationContext(), OnBoardingPager.class);
//            startActivity(intent);
//
//        }
//        finish();
//    }
//
//    private void resetPassword() {
//
//        LayoutInflater li = LayoutInflater.from(getApplicationContext());
//        final  View promptsView = li.inflate(R.layout.popup_forgotpassword_layout, null);
//
//        final EditText userInput = (EditText) promptsView
//                .findViewById(R.id.editTextDialogUserInput);
//
//        final  View popupView = li.inflate(R.layout.popup_layout_log_out, null);
//
//
//
//        HGBUtility.showAlertPopUp(LoginActivity.this,  userInput, promptsView,
//                getResources().getString(R.string.reset_your_password),getResources().getString(R.string.save_button), new PopUpAlertStringCB() {
//                    @Override
//                    public void itemSelected(String inputItem) {
//
//                        ConnectionManager.getInstance(LoginActivity.this).resetPasswordWithEmail(inputItem,
//                                new ConnectionManager.ServerRequestListener() {
//                            @Override
//                            public void onSuccess(Object data) {
//                                Toast.makeText(getApplicationContext(), R.string.email_reset_succesfully, Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onError(Object data) {
//                                HGBUtility.showAlertPopUpOneButton(LoginActivity.this,  null, popupView,
//                                        (String)data, null);
//
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void itemCanceled() {
//
//                    }
//                });
//
//    }
//}
