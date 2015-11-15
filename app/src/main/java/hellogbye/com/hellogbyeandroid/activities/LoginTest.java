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
    private Button hotelRoomButton;
    private TextView responceText;
    private HGBPreferencesManager hgbPrefrenceManager;
    private Button travelPreference;
    private Button oopenDialog;
    private FragmentManager fm;

    String solutionID = "e2292ac5-9536-4d97-b563-9c2240abf355";
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

        hotelRoomButton= (Button)findViewById(R.id.hotelroom);
        travelPreference = (Button)findViewById(R.id.userPreference);


        hotelRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(LoginTest.this).putFlight(solutionID,
                        pax, "54805cec-ab8f-4260-8ecc-a23c94a41c8c", "54805cec-ab8f-4260-8ecc-a23c94a41c8c", new ConnectionManager.ServerRequestListener() {
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
                        errorHelper.show(getFragmentManager(), (String)data);
                    }
                });

            }
        });


        travelPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(LoginTest.this).getPreferenceProfiles( new ConnectionManager.ServerRequestListener() {
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
                        errorHelper.show(getFragmentManager(), (String) data);
                    }
                });

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(LoginTest.this).login(emailEditText.getText().toString(),
                        paswordEditText.getText().toString(),
                        new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

//                        responceText.setText((String) data);
//                        Gson gson = new Gson();
//                        Type type = new TypeToken<UserLoginCredentials>() {
//                        }.getType();
                        UserLoginCredentials user = (UserLoginCredentials)data;
                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
                        Log.d("LoginTest", user.toString());
                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.show(getFragmentManager(), (String) data);
                    }
                });
            }
        });
        // http://gtaqa-1141527982.us-east-1.elb.amazonaws.com/GTAREST/REST/Hotel?solution=e977aac6-0fd7-4321-8e1f-44cb597cfbb2&paxid=9d2c85f5-d295-4064-a8c6-a4d0015b52e4&checkin=2015-09-02&checkout=2015-09-04
        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(LoginTest.this).getBookingOptions(new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        responceText.setText((String) data);

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        try {
                            errorHelper.show(getFragmentManager(), (String) data);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        });

    }
}
