package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.onboarding.OnBoardingPager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/22/16.
 */

public class EnterPinActivity extends Activity {
    private FontButtonView pin_code_verification_next;
    private FontEditTextView pin_code_verification_pin;
    private FontTextView resend_text;
    private HGBPreferencesManager hgbPrefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pin_code_verification);
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        pin_code_verification_next = (FontButtonView) findViewById(R.id.pin_code_verification_next);
        resend_text = (FontTextView)findViewById(R.id.resend_text);
        pin_code_verification_pin = (FontEditTextView) findViewById(R.id.pin_code_verification_pin);

        pin_code_verification_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateUserWithKey();
            }
        });

        resend_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(EnterPinActivity.this).postResendActivateEmail(
                        hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL,""), new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        Toast.makeText(getApplicationContext(),"Activation code sent",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getFragmentManager(), (String) data);

                    }
                });

            }
        });
    }

    private void activateUserWithKey() {

        String userPinCode = pin_code_verification_pin.getText().toString();

        ConnectionManager.getInstance(EnterPinActivity.this).postUserActivation(userPinCode, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                UserLoginCredentials user = (UserLoginCredentials) data;
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, user.getUserprofileid());

                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, false);
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_PSWD, null);
              //  hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, null);

                goToMainActivity();

            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);

            }
        });

    }

    private void goToMainActivity() {

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        boolean doesExist = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.TRAVEL_PREF_ENTRY, false);

        if (doesExist) {
            Intent intent = new Intent(getApplicationContext(), MainActivityBottomTabs.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), OnBoardingPager.class);
            startActivity(intent);
        }
        finish();
    }

}
