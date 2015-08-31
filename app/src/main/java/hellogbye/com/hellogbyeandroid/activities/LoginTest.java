package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.HashMap;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.network.CustomRequest;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;


/**
 * Created by arisprung on 8/31/15.
 */
public class LoginTest extends Activity {

    private EditText emailEditText;
    private EditText paswordEditText;
    private Button loginButton;
    private Button userProfileButton;
    private TextView responceText;
    private HGBPreferencesManager hgbPrefrenceManager;


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
                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN,user.getToken());
                        Log.d("LoginTest", user.toString());
                    }

                    @Override
                    public void onError(Object data) {

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

                    }
                });
            }
        });








    }
}
