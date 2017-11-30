package hellogbye.com.hellogbyeandroid.activities.dialogs;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.activities.RefreshComplete;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.models.vo.UserSignUpDataVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.onboarding.OnBoardingPager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontCheckedTextView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;

/**
 * Created by amirlubashevsky on 02/08/2017.
 */

public class CreateAccountWithFB {//extends BaseActivity {//Dialog {

    private HGBPreferencesManager hgbPrefrenceManager;
    private FontEditTextView login_password;
    private FontEditTextView username_email;
    private FontCheckedTextView remmember_me_checkbox;
    private FragmentManager fragmentManager;
    private Activity activity;
    private CallbackManager callbackManager;
    private UserSignUpDataVO userData;
    private RefreshComplete refreshComplete;


    public CreateAccountWithFB(Activity activity, CallbackManager callbackManager, RefreshComplete refreshComplete){
          this.activity = activity;
          this.callbackManager = callbackManager;
          initializeFB();
        this.refreshComplete = refreshComplete;
      }

    private void initializeFB(){
        LayoutInflater li = LayoutInflater.from(activity);
        final  View popupView = li.inflate(R.layout.new_create_account_with_fb_layout, null);

        LinearLayout sign_in_with_email = (LinearLayout) popupView.findViewById(R.id.sign_in_with_email);
        sign_in_with_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HGBUtility.dialog != null){
                    HGBUtility.dialog.cancel();
                }
                refreshComplete.onRefreshError("");

            }
        });


        hgbPrefrenceManager = HGBPreferencesManager.getInstance(activity.getApplicationContext());

        LogInFB(popupView);

        HGBUtility.showAlertPopUpOneButton(activity, null,  popupView ,
                null, null, false);

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
              //  ErrorMessage(data);

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

                logInAsFBUser(accessToken, object);

                //sendLoginToServer(email,userID );

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location"); // Parámetros que pedimos a facebook
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void LogInFB(View popView){
        LoginButton login_button_fb_btn = (LoginButton)popView.findViewById(R.id.login_button_fb_btn);
        login_button_fb_btn.setReadPermissions( Arrays.asList("public_profile", "email"));


      //  callbackManager = CallbackManager.Factory.create();

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


            hgbPrefrenceManager = HGBPreferencesManager.getInstance(activity);
            boolean doesExist = hgbPrefrenceManager.getBooleanSharedPreferences(HGBConstants.TRAVEL_PREF_ENTRY, false);

            if (doesExist) {
                Intent intent = new Intent(activity, MainActivityBottomTabs.class);
                activity.startActivity(intent);
            } else {
                Intent intent = new Intent(activity, OnBoardingPager.class);
                activity.startActivity(intent);
            }

        }

}
