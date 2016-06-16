package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import hellogbye.com.hellogbyeandroid.BuildConfig;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 9/8/15.
 */
public class StartingMenuActivity extends Activity {

    private FontTextView mSignUp;
    private FontTextView mLogin;
    //private FontTextView mPrivacyPolicy;
    private HGBPreferencesManager hgbPrefrenceManager;
    private FontTextView change_server_url;
    private FontTextView try_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final  String android_id = Settings.Secure.getString(StartingMenuActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());

        if(!HGBUtility.haveNetworkConnection(getApplicationContext())){

            Toast.makeText(getApplicationContext(),"There is no network please connect in order to continue",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String strToken = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
        if (!strToken.equals("")) {
            goToMainActivity();
            finish();
        }

        setContentView(R.layout.starting_menu_layout);
        mSignUp = (FontTextView) findViewById(R.id.create_account);
        mLogin = (FontTextView) findViewById(R.id.login_button);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO start Create Acount page

                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);

            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
//
//        SpannableString ss = new SpannableString(getResources().getString(R.string.terms));
//        ss.setSpan(new myClickableSpan(1), 49, 63, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        ss.setSpan(new myClickableSpan(2), 68, 82, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // ss.setSpan(termClickableSpan, 3, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


//        mPrivacyPolicy = (FontTextView) findViewById(R.id.terms_conditions);
//        mPrivacyPolicy.setText(ss);
//        mPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        // mPrivacyPolicy.setHighlightColor(Color.TRANSPARENT);

        change_server_url = (FontTextView)findViewById(R.id.change_server_url);
        change_server_url.setOnClickListener(changeServerUrlListener);


        if (BuildConfig.IS_DEV) {
            change_server_url.setVisibility(View.VISIBLE);
        }else{
            change_server_url.setVisibility(View.GONE);
        }


        try_now = (FontTextView)findViewById(R.id.try_now);
        try_now.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                ConnectionManager.getInstance(StartingMenuActivity.this).deviceAuthentication(android_id, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        UserLoginCredentials user = (UserLoginCredentials) data;
                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
                        hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER,true);
//                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
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
        });
    }

    private  View.OnClickListener changeServerUrlListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            String[] account_settings = getResources().getStringArray(R.array.base_url);

            HGBUtility.showPikerDialog(change_server_url, StartingMenuActivity.this, "Choose url",
                    account_settings, 0, account_settings.length -1, new PopUpAlertStringCB(){

                        @Override
                        public void itemSelected(String inputItem) {

                            ConnectionManager.BASE_URL = inputItem;
                        }

                        @Override
                        public void itemCanceled() {

                        }
                    }, true);
        }
    };

 /*   public class myClickableSpan extends ClickableSpan {

        int pos;

        public myClickableSpan(int position) {
            this.pos = position;
        }

        @Override
        public void onClick(View widget) {
            String url = "";
            switch (pos) {
                case 1:
                    url = getString(R.string.url_user_agreement);
                    break;
                case 2:
                    url = getString(R.string.url_pp);
                    break;

            }


            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        }

    }*/

    private void goToMainActivity() {

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        boolean doesExist = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.TRAVEL_PREF_ENTRY, false);
        if (doesExist) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), TravelPrefrenceStartingActivity.class);
            startActivity(intent);
        }
    }

}
