package hellogbye.com.hellogbyeandroid.activities.dialogs;

import android.app.Activity;
import android.app.Dialog;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import java.util.UUID;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.ForgotPasswordActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.onboarding.OnBoardingPager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by amirlubashevsky on 02/08/2017.
 */

public class SignInDialog extends Dialog {

    private HGBPreferencesManager hgbPrefrenceManager;
    private FontEditTextView login_password;
    private FontEditTextView username_email;
    private FontCheckedTextView remmember_me_checkbox;
    private FragmentManager fragmentManager;
    private Activity activity;




    public SignInDialog(Activity context, int theme_noTitleBar_fullscreen) {
        super(context, theme_noTitleBar_fullscreen);
        this.activity = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_sigin_layout);

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getContext().getApplicationContext());

        ImageButton new_sign_in_close_btn = (ImageButton) findViewById(R.id.new_sign_in_close_btn);
        new_sign_in_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        remmember_me_checkbox = (FontCheckedTextView)findViewById(R.id.remmember_me_checkbox);
        login_password = (FontEditTextView)findViewById(R.id.login_password);
        username_email = (FontEditTextView)findViewById(R.id.username_email);

        remmember_me_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(remmember_me_checkbox.isChecked()){
                    remmember_me_checkbox.setChecked(false);
                }else{
                    remmember_me_checkbox.setChecked(true);
                }
            }
        });


        FontButtonView login_button = (FontButtonView) findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLoginToServer(username_email.getText().toString(),login_password.getText().toString());
            }
        });


        FontTextView forgotpassword = (FontTextView) findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
                getContext().startActivity(intent);

            }
        });

    }


    private void sendLoginToServer(final String email,String pass) {



        try{
//            int permissionCheck = ContextCompat.checkSelfPermission(CreateAccountActivity.this, Manifest.permission.READ_PHONE_STATE);
//
//            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(CreateAccountActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 002);
//                return;
//            } else {
//                //TODO
//            }
            // TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            // String strUdid = tm.getDeviceId();
            //  long udid = Long.valueOf(strUdid);

            String uuid = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_UUID,"");
            if("".equals(uuid)){
                uuid = UUID.randomUUID().toString();
            }

            String connectionID = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_LOGIN_ID, "");

            ConnectionManager.getInstance(getContext()).login(email,pass,uuid,connectionID,
                    new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                            UserLoginCredentials user = (UserLoginCredentials) data;
                            hgbPrefrenceManager.putBooleanSharedPreferences(HGBConstants.HGB_USER_IS_LOGIN_IN_PAST, true);
                            hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.TOKEN, user.getToken());
                            hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_USER_PROFILE_ID, user.getUserprofileid());

                            hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_USER_LAST_EMAIL, email);
                            hgbPrefrenceManager.putBooleanSharedPreferences(HGBConstants.HGB_FREE_USER, false);
                            hgbPrefrenceManager.putBooleanSharedPreferences(HGBConstants.REMMEMBER_ME, remmember_me_checkbox.isChecked());

                            goToMainActivity();
                        }

                        @Override
                        public void onError(Object data) {
                            HGBErrorHelper errorHelper = new HGBErrorHelper();
                            errorHelper.setMessageForError((String) data);
                            errorHelper.show(activity.getFragmentManager(), (String) data);
                           // ErrorMessage(data);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void goToMainActivity() {


            hgbPrefrenceManager = HGBPreferencesManager.getInstance(getContext());
            boolean doesExist = hgbPrefrenceManager.getBooleanSharedPreferences(HGBConstants.TRAVEL_PREF_ENTRY, false);

            if (doesExist) {
                Intent intent = new Intent(getContext(), MainActivityBottomTabs.class);
                getContext().startActivity(intent);
            } else {
                Intent intent = new Intent(getContext(), OnBoardingPager.class);
                getContext().startActivity(intent);
            }
            dismiss();
        }



}
