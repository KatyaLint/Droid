//package hellogbye.com.hellogbyeandroid.activities;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
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
//import hellogbye.com.hellogbyeandroid.views.FontButtonView;
//import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
//import hellogbye.com.hellogbyeandroid.views.FontTextView;
//
//import static hellogbye.com.hellogbyeandroid.R.id.forgotpassword;
//
///**
// * Created by arisprung on 11/22/16.
// */
//
//public class NewLoginActivity extends Activity {
//
//    private FontEditTextView mEmailEditText;
//    private FontEditTextView mPasswordEditText;
//    private CheckBox mRemmeberMeCheckbox;
//    private HGBPreferencesManager hgbPrefrenceManager;
//    private FontTextView mForgotPasswordTextView;
//    private boolean remember_me;
//    private FontButtonView login;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.new_login_layout);
//
//        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
//        mEmailEditText = (FontEditTextView) findViewById(R.id.username);
//        mPasswordEditText = (FontEditTextView) findViewById(R.id.login_password);
//        mRemmeberMeCheckbox = (CheckBox) findViewById(R.id.remmember_me_checkbox);
//        mForgotPasswordTextView = (FontTextView) findViewById(R.id.forgotpassword);
//        login = (FontButtonView)findViewById(R.id.login_button);
//
//
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
//                resetPassword();
//            }
//        });
//
//
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.REMMEMBER_ME, mRemmeberMeCheckbox.isChecked());
//
//                ConnectionManager.getInstance(NewLoginActivity.this).login(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString(),
//                        new ConnectionManager.ServerRequestListener() {
//                            @Override
//                            public void onSuccess(Object data) {
//                                UserLoginCredentials user = (UserLoginCredentials) data;
//                                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_USER_IS_LOGIN_IN_PAST, true);
//                                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
//                                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, user.getUserprofileid());
//
//                                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, mEmailEditText.getText().toString());
//                                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_PSWD, mPasswordEditText.getText().toString());
//                                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER,false);
//                                goToMainActivity();
//                            }
//
//                            @Override
//                            public void onError(Object data) {
//                                HGBErrorHelper errorHelper = new HGBErrorHelper();
//                                errorHelper.setMessageForError((String) data);
//                                errorHelper.show(getFragmentManager(), (String) data);
//                            }
//                        });
//            }
//        });
//    }
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
//            //           Intent intent = new Intent(getApplicationContext(), TravelPrefrenceStartingActivity.class);
//            Intent intent = new Intent(getApplicationContext(), OnBoardingPager.class);
//            startActivity(intent);
//
//        }
//     //   finish();
//    }
//
//    private void resetPassword() {
//
//        LayoutInflater li = LayoutInflater.from(getApplicationContext());
//        final View promptsView = li.inflate(R.layout.popup_forgotpassword_layout, null);
//
//        final EditText userInput = (EditText) promptsView
//                .findViewById(R.id.editTextDialogUserInput);
//
//        final  View popupView = li.inflate(R.layout.popup_layout_log_out, null);
//
//
//
//        HGBUtility.showAlertPopUp(NewLoginActivity.this,  userInput, promptsView,
//                getResources().getString(R.string.reset_your_password),getResources().getString(R.string.save_button), new PopUpAlertStringCB() {
//                    @Override
//                    public void itemSelected(String inputItem) {
//
//                        ConnectionManager.getInstance(NewLoginActivity.this).resetPasswordWithEmail(inputItem,
//                                new ConnectionManager.ServerRequestListener() {
//                                    @Override
//                                    public void onSuccess(Object data) {
//                                        Toast.makeText(getApplicationContext(), R.string.email_reset_succesfully, Toast.LENGTH_SHORT).show();
//                                    }
//
//                                    @Override
//                                    public void onError(Object data) {
//                                        HGBUtility.showAlertPopUpOneButton(NewLoginActivity.this,  null, popupView,
//                                                (String)data, null);
//                                    }
//                                });
//                    }
//
//                    @Override
//                    public void itemCanceled() {
//                    }
//                });
//
//    }
//}
