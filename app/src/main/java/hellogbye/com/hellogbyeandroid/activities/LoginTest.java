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
                ConnectionManager.getInstance(LoginTest.this).getPreference(new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {

                        responceText.setText((String) data);
                        Gson gson = new Gson();
                        Type type = new TypeToken<UserPreference>() {
                        }.getType();
                        UserPreference[] arr = gson.fromJson((String) data, UserPreference[].class);


                        Log.d("PreferenceTest", arr.toString());
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
                        Type type = new TypeToken<UserLoginCredentials>() {}.getType();
                        UserLoginCredentials user = gson.fromJson((String) data, type);
                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN,user.getToken());
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

        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectionManager.getInstance(LoginTest.this).getUserProfile( new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        responceText.setText((String) data);

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.show(getSupportFragmentManager(), "Sorry, can't get your profile data");
                    }
                });
            }
        });








    }
}
