package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.onboarding.OnBoardingPager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


public class EnterPinActivity extends Activity {

    private FontTextView resend_text;
    private HGBPreferencesManager hgbPrefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }


        setContentView(R.layout.pin_code_verification);
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        resend_text = (FontTextView)findViewById(R.id.resend_text);



        resend_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(EnterPinActivity.this).postResendActivateEmail(
                        hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_LAST_EMAIL,""), new ConnectionManager.ServerRequestListener() {
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



    private void goToMainActivity() {

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        boolean doesExist = hgbPrefrenceManager.getBooleanSharedPreferences(HGBConstants.TRAVEL_PREF_ENTRY, false);

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
