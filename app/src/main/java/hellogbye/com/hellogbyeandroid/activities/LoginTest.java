package hellogbye.com.hellogbyeandroid.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.models.UserPreference;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;


/**
 * Created by arisprung on 8/31/15.
 */
public class LoginTest extends ActionBarActivity {

    private EditText emailEditText;
    private EditText paswordEditText;
    private Button loginButton;
    private Button userProfileButton;
    private TextView responceText;
    private HGBPreferencesManager hgbPrefrenceManager;
    private Button travelPreference;
    private Button oopenDialog;
    private FragmentManager fm;

    String solutionID = "e977aac6-0fd7-4321-8e1f-44cb597cfbb2";
    String pax = "9d2c85f5-d295-4064-a8c6-a4d0015b52e4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.testlayout);

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());

        emailEditText = (EditText)findViewById(R.id.email);
        paswordEditText = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.login);
        userProfileButton= (Button)findViewById(R.id.userprofile);
        responceText = (TextView)findViewById(R.id.responce);

        travelPreference = (Button)findViewById(R.id.userPreference);





        travelPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(LoginTest.this).getPreferencesForProfileId("7316d95c-732f-4b59-8d71-d3874e26878a", new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

                        responceText.setText((String) data);
//                        Gson gson = new Gson();
//                        Type type = new TypeToken<UserPreference>() {
//                        }.getType();
//                        UserPreference[] arr = gson.fromJson((String) data, UserPreference[].class);
//
//
//                        Log.d("PreferenceTest", arr.toString());
                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.show(getSupportFragmentManager(), "Sorry, we have connection problem");
                    }
                });

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(LoginTest.this).login(emailEditText.getText().toString(), paswordEditText.getText().toString(), new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

                        responceText.setText((String) data);
                        Gson gson = new Gson();
                        Type type = new TypeToken<UserLoginCredentials>() {
                        }.getType();
                        UserLoginCredentials user = gson.fromJson((String) data, type);
                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
                        Log.d("LoginTest", user.toString());
                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.show(getSupportFragmentManager(), "Sorry, there are login problem");
                    }
                });
            }
        });
        // http://gtaqa-1141527982.us-east-1.elb.amazonaws.com/GTAREST/REST/Hotel?solution=e977aac6-0fd7-4321-8e1f-44cb597cfbb2&paxid=9d2c85f5-d295-4064-a8c6-a4d0015b52e4&checkin=2015-09-02&checkout=2015-09-04
        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(LoginTest.this).putAlternateHotel(solutionID, pax, "2015-09-02", "2015-09-04", new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        responceText.setText((String) data);

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        try {
                            errorHelper.show(getSupportFragmentManager(), (String) data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }
}
