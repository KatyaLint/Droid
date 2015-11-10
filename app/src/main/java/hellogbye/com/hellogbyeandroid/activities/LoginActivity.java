package hellogbye.com.hellogbyeandroid.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 9/8/15.
 */
public class LoginActivity extends FragmentActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private CheckBox mRemmeberMeCheckbox;
    private FontTextView mLoginTextView;
    private HGBPreferencesManager hgbPrefrenceManager;
    private FontTextView mForgotPasswordTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_layout);


//        if(!"".equals(hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.TOKEN,""))){
//            if(hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.REMMEMBER_ME,false)){
//                goToMainActivity();
//            }
//        }
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        mEmailEditText = (EditText) findViewById(R.id.email);
        mPasswordEditText = (EditText) findViewById(R.id.password);
        mRemmeberMeCheckbox = (CheckBox) findViewById(R.id.remmember_me_checkbox);
        mLoginTextView = (FontTextView) findViewById(R.id.user_login);
        mForgotPasswordTextView = (FontTextView) findViewById(R.id.forgotpassword);

        mForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO forgot password
                resetPassword();
            }
        });


        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.REMMEMBER_ME, mRemmeberMeCheckbox.isChecked());

                ConnectionManager.getInstance(LoginActivity.this).login(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString(), new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        Gson gson = new Gson();
                        Type type = new TypeToken<UserLoginCredentials>() {
                        }.getType();
                        UserLoginCredentials user = gson.fromJson((String) data, type);
                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
                        goToMainActivity();

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.show(getFragmentManager(), (String) data);
                    }
                });
            }
        });
    }


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

    private void resetPassword() {

        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.forgotpassword_layout, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                LoginActivity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ConnectionManager.getInstance(LoginActivity.this).resetPasswordWithEmail(userInput.getText().toString(), new ConnectionManager.ServerRequestListener() {
                                    @Override
                                    public void onSuccess(Object data) {
                                        Toast.makeText(getApplicationContext(), R.string.email_reset_succesfully, Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onError(Object data) {
                                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                                        errorHelper.show(getFragmentManager(), (String) data);
                                    }
                                });
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}
