package hellogbye.com.hellogbyeandroid.activities.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.ForgotPasswordActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.activities.RefreshComplete;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.models.vo.UserSignUpDataVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.onboarding.OnBoardingPager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by amirlubashevsky on 02/08/2017.
 */

public class SignInWithFB extends Dialog {//extends BaseActivity {//Dialog {

    private HGBPreferencesManager hgbPrefrenceManager;
    private FontEditTextView login_password;
    private FontEditTextView username_email;
    private FontCheckedTextView login_remmember_me_checkbox;
    private FragmentManager fragmentManager;
    private Activity activity;
    private CallbackManager callbackManager;
    private UserSignUpDataVO userData;
    private RefreshComplete refreshComplete;


    public SignInWithFB(Activity context, int theme_noTitleBar_fullscreen, CallbackManager callbackManager, RefreshComplete refreshComplete) {
        super(context, theme_noTitleBar_fullscreen);
        this.activity = context;
        this.callbackManager = callbackManager;
        //  initializeFB();
        this.refreshComplete = refreshComplete;

    }

    private void sendLoginToServer(final String email,String pass) {

        try{

            String uuid = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_UUID,"");
            if("".equals(uuid)){
                uuid = UUID.randomUUID().toString();
            }

            String connectionID = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_LOGIN_ID, "");

            ConnectionManager.getInstance(activity).login(email,pass,uuid,connectionID,
                    new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                            UserLoginCredentials user = (UserLoginCredentials) data;
                            hgbPrefrenceManager.putBooleanSharedPreferences(HGBConstants.HGB_USER_IS_LOGIN_IN_PAST, true);
                            hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.TOKEN, user.getToken());
                            hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_USER_PROFILE_ID, user.getUserprofileid());

                            hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_USER_LAST_EMAIL, email);
                            hgbPrefrenceManager.putBooleanSharedPreferences(HGBConstants.HGB_FREE_USER, false);
                            hgbPrefrenceManager.putBooleanSharedPreferences(HGBConstants.REMMEMBER_ME, login_remmember_me_checkbox.isChecked());

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

        System.out.println("Kate goToMainActivity");
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(activity);
        boolean doesExist = hgbPrefrenceManager.getBooleanSharedPreferences(HGBConstants.TRAVEL_PREF_ENTRY, false);

        if (doesExist) {
            Intent intent = new Intent(activity, MainActivityBottomTabs.class);
            activity.startActivity(intent);
        } else {
            Intent intent = new Intent(activity, OnBoardingPager.class);
            activity.startActivity(intent);
        }
        if(HGBUtility.dialog != null){
            HGBUtility.dialog.dismiss();
        }

        this.dismiss();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_sigin_layout);
        signInInitialization();

    }

    private void signInInitialization(){
        ImageButton new_sign_in_close_btn = (ImageButton) findViewById(R.id.new_sign_in_close_btn);
        new_sign_in_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


          hgbPrefrenceManager = HGBPreferencesManager.getInstance(activity.getApplicationContext());

          login_remmember_me_checkbox = (FontCheckedTextView)findViewById(R.id.remmember_me_checkbox);
          login_password = (FontEditTextView)findViewById(R.id.login_password);
          username_email = (FontEditTextView)findViewById(R.id.username_email);

          login_remmember_me_checkbox.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if(login_remmember_me_checkbox.isChecked()){
                      login_remmember_me_checkbox.setChecked(false);
                  }else{
                      login_remmember_me_checkbox.setChecked(true);
                  }
              }
          });


          FontButtonView login_button = (FontButtonView)findViewById(R.id.login_button);
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
                  Intent intent = new Intent(activity, ForgotPasswordActivity.class);
                  activity.startActivity(intent);

              }
          });


          LogInFB();
      }


    private void logiWithFBSuccess(Object data){
        UserLoginCredentials user = (UserLoginCredentials) data;
        hgbPrefrenceManager.putBooleanSharedPreferences(HGBConstants.HGB_USER_IS_LOGIN_IN_PAST, true);
        hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.TOKEN, user.getToken());
        hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_USER_PROFILE_ID, user.getUserprofileid());

        hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_USER_LAST_EMAIL, userData.getUserEmail());
        hgbPrefrenceManager.putBooleanSharedPreferences(HGBConstants.HGB_FREE_USER, false);

        goToMainActivity();
    }

    private void logInAsFBUser(final String token,final JSONObject object){

        ConnectionManager.getInstance(activity).postSessionExternal(token, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                Bundle bFacebookData = getFacebookData(object);
             //   String email = bFacebookData.getString("email");
                logiWithFBSuccess(data);
               // sendLoginToServer(email, token );

            }

            @Override
            public void onError(Object data) {

                System.out.println("Kate postSessionExternal error");
                postUserCreateFBAccountUser();

            }
        });
    }



    private void postUserCreateFBAccountUser(){


        ConnectionManager.getInstance(activity).postUserCreateFBAccount(userData, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                sendLoginToServer(userData.getUserEmail(), userData.getToken() );

            }

            @Override
            public void onError(Object data) {
                //  ErrorMessage(data);



            }
        });


    }


    public void requestUserProfile(LoginResult loginResult){

//        ProgressDialog progressDialog = new ProgressDialog(CreateAccountActivity.this);
//        progressDialog.setMessage("Procesando datos...");
//        progressDialog.show();

        final String accessToken = loginResult.getAccessToken().getToken();

        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {


                // Get facebook data from login
                Bundle bFacebookData = getFacebookData(object);
                String email = bFacebookData.getString("email");
                String userID = bFacebookData.getString("id");
                String location = bFacebookData.getString("location");
                String first_name = bFacebookData.getString("first_name");
                String last_name = bFacebookData.getString("last_name");
                String gender = bFacebookData.getString("gender");

                userData = new UserSignUpDataVO();
                userData.setUserEmail(email);
                userData.setCountry(location);
                userData.setToken(accessToken);
                userData.setFirstName(first_name);
                userData.setLastName(last_name);
                userData.setGender(gender);

                System.out.println("Kate accessToken =" + accessToken);
                logInAsFBUser(accessToken, object);

                //sendLoginToServer(email,userID );


            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location"); // Parámetros que pedimos a facebook
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void LogInFB(){
        LoginButton login_button_fb_btn = (LoginButton)findViewById(R.id.login_button_fb_btn);
        login_button_fb_btn.setReadPermissions( Arrays.asList("public_profile", "email"));

        login_button_fb_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                requestUserProfile(loginResult);
                refreshComplete.onRefreshSuccess("");
            }

            @Override
            public void onCancel() {
                // App code

            }

            @Override
            public void onError(FacebookException exception) {
                // App code

            }
        });
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");

                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));

            return bundle;
        }
        catch(JSONException e) {

        }
        return null;
    }


}
