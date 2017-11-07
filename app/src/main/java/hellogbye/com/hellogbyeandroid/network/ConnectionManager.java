package hellogbye.com.hellogbyeandroid.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.UserSignUpDataVO;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.UpdateAvailabilityItemVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.UpdateAvailabilityVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.ConversationVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;


public class ConnectionManager {


    private static HGBPreferencesManager mHGBPrefrenceManager;


    public interface ServerRequestListener {
        void onSuccess(Object data);

        void onError(Object data);
    }

    public static String BASE_URL_SIGNALR_URL = "http://apidev.hellogbye.com/dev/"; //"https://apiprod.hellogbye.com/prod/";
    public static String BASE_URL = "http://apidev.hellogbye.com/dev/rest/";

    //public static String BASE_URL = "http://demo.hellogbye.com/dev/rest/";
    // public static String BASE_URL = "http://apiuat.hellogbye.com/uat/rest/";
    //public static String BASE_URL = "http://cnc.hellogbye.com/cnc/rest/";



    private static ConnectionManager _instance;
    private Context mContext;

    private String SOLUTION = "?solution=";
    private String PREFERENCES = "Preferences";




    private ConnectionManager(Context context) {
        mHGBPrefrenceManager = HGBPreferencesManager.getInstance(context.getApplicationContext());
    }

    public static ConnectionManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new ConnectionManager(context);
        }
        _instance.mContext = context;
        HGBStringRequest.setContext(context);
        HGBJsonRequest.setContext(context);
        HGBStringXMLRequest.setContext(context);

        return _instance;
    }


    private static String changeServerURL(){

        String choosenServer = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.CHOOSEN_SERVER,"");

        if(choosenServer == null || choosenServer.isEmpty()){
            BASE_URL = "http://apidev.hellogbye.com/dev/rest/" ; //https://apiprod.hellogbye.com/prod/rest/"; // "http://apidev.hellogbye.com/dev/rest/ "https://apiprod.hellogbye.com/prod/rest/"; //"http://apidev.hellogbye.com/dev/rest/";// //"https://apiuat.hellogbye.com/uat/rest/";//This is default server
        }else{
            BASE_URL = choosenServer;
        }


        return BASE_URL;
    }

    ////////////////////////////////
    // POST
    ///////////////////////////////

    public void postAirlinePointsProgram(String programId , String programNumber, final ServerRequestListener listener) {
        String url = getURL(Services.AIRLINE_POINTS_USER_PROGRAMS);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("programid", programId);
            jsonObject.put("programnumber", programNumber);



        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirlinePointsProgram(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }


    public void postSubmitFeedback(String message, String preferenceID, String solutionID, ArrayList<ConversationVO> conversation, final ServerRequestListener listener) {
        String url = getURL(Services.SUBMIT_FEEDBACK);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsArray = new JSONArray(conversation);
        String messageFromAndroid = "Search Query:"+"\n" + "Solution ID:" + solutionID+"\n"+ "Version FE: Build version : 0.2.2, Build Date: 10/30/2017 7:48:07 PM"
                +"\n"+" Version BE: Build version : 0.0.0.0, Build Date:10/17/2017 4:32:34 PM"+"\n"
                +"Preference:"+preferenceID+"\n"
                +" User Feedback:" + message;
        try {
            jsonObject.put("message", messageFromAndroid);
            jsonObject.put("solutionid", solutionID);
            jsonObject.put("preference", preferenceID);
            jsonObject.put("conversation", jsArray);
            jsonObject.put("filedata", "");
            jsonObject.put("filename", "screenshot.png");

            //filedata  : "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…EoBJ6LUlkWAAAECBAgQIEDgQMCJOBhRBQIECBAgQIAAAQKlgBNRassiQIAAAQIECBAgcCDgRByMqAIBAgQIECBAgACBUsCJKLVlESBAgAABAgQIEDgQcCIORlSBAAECBAgQIECAQCngRJTasggQIECAAAECBAgcCDgRByOqQIAAAQIECBAgQKAUcCJKbVkECBAgQIAAAQIEDgSciIMRVSBAgAABAgQIECBQCjgRpbYsAgQIECBAgAABAgcCTsTBiCoQIECAAAECBAgQKAWciFJbFgECBAgQIECAAIEDASfiYEQVCBAgQIAAAQIECJQCTkSpLYsAAQIECBAgQIDAgYATcTCiCgQIECBAgAABAgRKASei1JZFgAABAgQIECBA4EDAiTgYUQUCBAgQIECAAAECpYATUWrLIkCAAAECBAgQIHAg4EQcjKgCAQIECBAgQIAAgVLAiSi1ZREgQIAAAQIECBA4EHAiDkZUgQABAgQIECBAgEAp4ESU2rIIECBAgAABAgQIHAg4EQcjqkCAAAECBAgQIECgFHAiSm1ZBAgQIECAAAECBA4EnIiDEVUgQIAAAQIECBAgUAo4EaW2LAIECBAgQIAAAQIHAk7EwYgqECBAgAABAgQIECgFnIhSWxYBAgQIECBAgACBAwEn4mBEFQgQIECAAAECBAiUAk5EqS2LAAECBAgQIECAwIGAE3EwogoECBAgQIAAAQIESgEnotSWRYAAAQIECBAgQOBAwIk4GFEFAgQIECBAgAABAqWAE1FqyyJAgAABAgQIECBwIOBEHIyoAgECBAgQIECAAIFSwIkotWURIECAAAECBAgQOBBwIg5GVIEAAQIECBAgQIBAKeBElNqyCBAgQIAAAQIECBwIOBEHI6pAgAABAgQIECBAoBRwIkptWQQIECBAgAABAgQOBJyIgxFVIECAAAECBAgQIFAKOBGltiwCBAgQIECAAAECBwJOxMGIKhAgQIAAAQIECBAoBZyIUlsWAQIECBAgQIAAgQMBJ+JgRBUIECBAgAABAgQIlAJORKktiwABAgQIECBAgMCBgBNxMKIKBAgQIECAAAECBEoBJ6LUlkWAAAECBAgQIEDgQMCJOBhRBQIECBAgQIAAAQKlgBNRassiQIAAAQIECBAgcCDgRByMqAIBAgQIECBAgACBUsCJKLVlESBAgAABAgQIEDgQcCIORlSBAAECBAgQIECAQCngRJTasggQIECAAAECBAgcCDgRByOqQIAAAQIECBAgQKAUcCJKbVkECBAgQIAAAQIEDgSciIMRVSBAgAABAgQIECBQCjgRpbYsAgQIECBAgAABAgcCTsTBiCoQIECAAAECBAgQKAWciFJbFgECBAgQIECAAIEDASfiYEQVCBAgQIAAAQIECJQCTkSpLYsAAQIECBAgQIDAgYATcTCiCgQIECBAgAABAgRKASei1JZFgAABAgQIECBA4EDAiTgYUQUCBAgQIECAAAECpYATUWrLIkCAAAECBAgQIHAg4EQcjKgCAQIECBAgQIAAgVLAiSi1ZREgQIAAAQIECBA4EHAiDkZUgQABAgQIECBAgEAp4ESU2rIIECBAgAABAgQIHAg4EQcjqkCAAAECBAgQIECgFHAiSm1ZBAgQIECAAAECBA4EnIiDEVUgQIAAAQIECBAgUAo4EaW2LAIECBAgQIAAAQIHAk7EwYgqECBAgAABAgQIECgFnIhSWxYBAgQIECBAgACBAwEn4mBEFQgQIECAAAECBAiUAk5EqS2LAAECBAgQIECAwIGAE3EwogoECBAgQIAAAQIESgEnotSWRYAAAQIECBAgQOBAwIk4GFEFAgQIECBAgAABAqWAE1FqyyJAgAABAgQIECBwIOBEHIyoAgECBAgQIECAAIFSwIkotWURIECAAAECBAgQOBBwIg5GVIEAAQIECBAgQIBAKeBElNqyCBAgQIAAAQIECBwIOBEHI6pAgAABAgQIECBAoBRwIkptWQQIECBAgAABAgQOBJyIgxFVIECAAAECBAgQIFAKOBGltiwCBAgQIECAAAECBwJOxMGIKhAgQIAAAQIECBAoBZyIUlsWAQIECBAgQIAAgQMBJ+JgRBUIECBAgAABAgQIlAJORKktiwABAgQIECBAgMCBgBNxMKIKBAgQIECAAAECBEoBJ6LUlkWAAAECBAgQIEDgQMCJOBhRBQIECBAgQIAAAQKlgBNRassiQIAAAQIECBAgcCDgRByMqAIBAgQIECBAgACBUsCJKLVlESBAgAABAgQIEDgQcCIORlSBAAECBAgQIECAQCngRJTasggQIECAAAECBAgcCDgRByOqQIAAAQIECBAgQKAUcCJKbVkECBAgQIAAAQIEDgSciIMRVSBAgAABAgQIECBQCjgRpbYsAgQIECBAgAABAgcCTsTBiCoQIECAAAECBAgQKAWciFJbFgECBAgQIECAAIEDASfiYEQVCBAgQIAAAQIECJQCTkSpLYsAAQIECBAgQIDAgYATcTCiCgQIECBAgAABAgRKASei1JZFgAABAgQIECBA4EDAiTgYUQUCBAgQIECAAAECpYATUWrLIkCAAAECBAgQIHAg4EQcjKgCAQIECBAgQIAAgVLAiSi1ZREgQIAAAQIECBA4EHAiDkZUgQABAgQIECBAgEAp4ESU2rIIECBAgAABAgQIHAg4EQcjqkCAAAECBAgQIECgFHAiSm1ZBAgQIECAAAECBA4EnIiDEVUgQIAAAQIECBAgUAo4EaW2LAIECBAgQIAAAQIHAk7EwYgqECBAgAABAgQIECgFnIhSWxYBAgQIECBAgACBAwEn4mBEFQgQIECAAAECBAiUAk5EqS2LAAECBAgQIECAwIGAE3EwogoECBAgQIAAAQIESgEnotSWRYAAAQIECBAgQOBAwIk4GFEFAgQIECBAgAABAqWAE1FqyyJAgAABAgQIECBwIOBEHIyoAgECBAgQIECAAIFSwIkotWURIECAAAECBAgQOBBwIg5GVIEAAQIECBAgQIBAKeBElNqyCBAgQIAAAQIECBwIOBEHI6pAgAABAgQIECBAoBRwIkptWQQIECBAgAABAgQOBJyIgxFVIECAAAECBAgQIFAKOBGltiwCBAgQIECAAAECBwJOxMGIKhAgQIAAAQIECBAoBZyIUlsWAQIECBAgQIAAgQMBJ+JgRBUIECBAgAABAgQIlAJORKktiwABAgQIECBAgMCBgBNxMKIKBAgQIECAAAECBEoBJ6LUlkWAAAECBAgQIEDgQMCJOBhRBQIECBAgQIAAAQKlgBNRassiQIAAAQIECBAgcCDgRByMqAIBAgQIECBAgACBUsCJKLVlESBAgAABAgQIEDgQcCIORlSBAAECBAgQIECAQCngRJTasggQIECAAAECBAgcCDgRByOqQIAAAQIECBAgQKAUcCJKbVkECBAgQIAAAQIEDgSciIMRVSBAgAABAgQIECBQCjgRpbYsAgQIECBAgAABAgcCTsTBiCoQIECAAAECBAgQKAWciFJbFgECBAgQIECAAIEDASfiYEQVCBAgQIAAAQIECJQCTkSpLYsAAQIECBAgQIDAgYATcTCiCgQIECBAgAABAgRKASei1JZFgAABAgQIECBA4EDAiTgYUQUCBAgQIECAAAECpYATUWrLIkCAAAECBAgQIHAg4EQcjKgCAQIECBAgQIAAgVLAiSi1ZREgQIAAAQIECBA4EHAiDkZUgQABAgQIECBAgEAp4ESU2rIIECBAgAABAgQIHAg4EQcjqkCAAAECBAgQIECgFHAiSm1ZBAgQIECAAAECBA4EnIiDEVUgQIAAAQIECBAgUAo4EaW2LAIECBAgQIAAAQIHAk7EwYgqECBAgAABAgQIECgFnIhSWxYBAgQIECBAgACBAwEn4mBEFQgQIECAAAECBAiUAk5EqS2LAAECBAgQIECAwIGAE3EwogoECBAgQIAAAQIESgEnotSWRYAAAQIECBAgQOBAwIk4GFEFAgQIECBAgAABAqWAE1FqyyJAgAABAgQIECBwIOBEHIyoAgECBAgQIECAAIFSwIkotWURIECAAAECBAgQOBBwIg5GVIEAAQIECBAgQIBAKeBElNqyCBAgQIAAAQIECBwIOBEHI6pAgAABAgQIECBAoBRwIkptWQQIECBAgAABAgQOBJyIgxFVIECAAAECBAgQIFAKOBGltiwCBAgQIECAAAECBwJOxMGIKhAgQIAAAQIECBAoBZyIUlsWAQIECBAgQIAAgQMBJ+JgRBUIECBAgAABAgQIlAJORKktiwABAgQIECBAgMCBgBNxMKIKBAgQIECAAAECBEoBJ6LUlkWAAAECBAgQIEDgQMCJOBhRBQIECBAgQIAAAQKlgBNRassiQIAAAQIECBAgcCDgRByMqAIBAgQIECBAgACBUsCJKLVlESBAgAABAgQIEDgQcCIORlSBAAECBAgQIECAQCngRJTasggQIECAAAECBAgcCDgRByOqQIAAAQIECBAgQKAUcCJKbVkECBAgQIAAAQIEDgSciIMRVSBAgAABAgQIECBQCjgRpbYsAgQIECBAgAABAgcCTsTBiCoQIECAAAECBAgQKAWciFJbFgECBAgQIECAAIEDASfiYEQVCBAgQIAAAQIECJQCTkSpLYsAAQIECBAgQIDAgYATcTCiCgQIECBAgAABAgRKASei1JZFgAABAgQIECBA4EDAiTgYUQUCBAgQIECAAAECpYATUWrLIkCAAAECBAgQIHAg4EQcjKgCAQIECBAgQIAAgVLAiSi1ZREgQIAAAQIECBA4EHAiDkZUgQABAgQIECBAgEAp4ESU2rIIECBAgAABAgQIHAg4EQcjqkCAAAECBAgQIECgFHAiSm1ZBAgQIECAAAECBA4EnIiDEVUgQIAAAQIECBAgUAo4EaW2LAIECBAgQIAAAQIHAk7EwYgqECBAgAABAgQIECgFBgljLLmXwqSiAAAAAElFTkSuQmCC"
          //message
//            Search Query:
//            Solution ID:
//            Version FE: Build version : 0.2.2, Build Date: 10/30/2017 7:48:07 PM
//            Version BE: Build version : 0.0.0.0, Build Date:10/17/2017 4:32:34 PM
//            Preference: 83ebbe90-10e8-4499-9808-01e6614dd4aa
//            User Feedback: testing testing"






        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }


    public void postSearchHotels(String solutionid, String hotelid, LatLng latlng,final ServerRequestListener listener) {
        String url = getURL(Services.HOTEL_SEARCH);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("solutionId", solutionid);
            jsonObject.put("PrimaryHotelId", hotelid);
            jsonObject.put("Latitude", latlng.latitude);
            jsonObject.put("Longitude", latlng.longitude);
            jsonObject.put("maxsearchresult", 16);



        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseHotelData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }
    public void postAutocompleteCity(String word, String country, String State,final ServerRequestListener listener) {
        String url = getURL(Services.STATIC_CITY_AUTOCOMPLETE);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("words", word);
            jsonObject.put("countrycode", country);
            jsonObject.put("statecode", State);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getCityList(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },false);

    }



    public void postUserActivation(String userActivationKey, final ServerRequestListener listener) {

        String url = getURL(Services.USER_ACTIVATION_PIN);
        url = url+userActivationKey;
        JSONObject jsonObject = new JSONObject();

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.loginData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, true);
    }

    public void postSendNOtificationToken(String key, final ServerRequestListener listener) {

        String url = getURL(Services.ACTIVATE_NOTIFICATION);
        url = url+key;
        JSONObject jsonObject = new JSONObject();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }

    public void postResendActivateEmail(String email, final ServerRequestListener listener) {

        String url = getURL(Services.RESEND_ACTIVATION);
        url = url+email;
        JSONObject jsonObject = new JSONObject();

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, true);
    }

    public void postUserCreateAccount(UserSignUpDataVO userData,boolean hellopromtion,boolean thirdpatyboolean, final ServerRequestListener listener) {

        String url = getURL(Services.USER_PROFILE_REGISTER);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("city", userData.getCity());
            jsonObject.put("confirmPassword", userData.getConfirmPassword());
            jsonObject.put("country", userData.getCountry());
            jsonObject.put("firstname", userData.getFirstName());
            jsonObject.put("lastname", userData.getLastName());
            jsonObject.put("password", userData.getPassword());
            jsonObject.put("state", userData.getCountryProvince());
            jsonObject.put("username", userData.getUserEmail());
            if(userData.getUserTravelerType()!= -1){
                jsonObject.put("marketingtravelertype", userData.getUserTravelerType());
            }
            jsonObject.put("gender", userData.getGender());
            jsonObject.put("acceptHgbMarketing", hellopromtion);
            jsonObject.put("acceptThirdPartyMarketing",thirdpatyboolean);
            jsonObject.put("usertypeid","NPAY");
            jsonObject.put("isprofilepublic",true);
            jsonObject.put("cardtoken","");





        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, true);
    }


    public void postSearchCompanionAdd(CompanionVO companionData, final ServerRequestListener listener) {

        String url = getURL(Services.COMPANIONS);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectStub = new JSONObject();

        try {

            jsonObject.put("addedviastatus", companionData.getmAddedvia());
            jsonObject.put("isAdult", companionData.ismIsAdult());
            jsonObject.put("relationshiptypeid", companionData.getRelationshiptypeid());

            jsonObjectStub.put("emailaddress", companionData.getCompanionUserProfile().getmEmailAddress());
            jsonObjectStub.put("firstname", companionData.getCompanionUserProfile().getmFirstName());
            jsonObjectStub.put("lastname",  companionData.getCompanionUserProfile().getmLastName());
            jsonObjectStub.put("dob",  companionData.getCompanionUserProfile().getmDoB());
            jsonObjectStub.put("gender",  companionData.getCompanionUserProfile().getmGender());
            jsonObject.put("stubcompanion",jsonObjectStub);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseCompanionData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, true);
    }



    public void postCompanions(String firstName, String lastName, String email, final ServerRequestListener listener) {

        String url = getURL(Services.COMPANIONS);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("firstname", firstName);
            jsonObject.put("lastname", lastName);
            jsonObject.put("emailaddress", email);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }



    public void pay(JSONObject json ,final ServerRequestListener listener) {
        String url = getURL(Services.BOOKING_PAY);

//        JSONObject jsonObject = new JSONObject();
//
//        try {
//            jsonObject.put("userprofileid", userprofileid);
//            jsonObject.put("mimetype", ".png");
//            jsonObject.put("avatar", avatar);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                json, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parsePayCheckoutResponse(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, true);
    }

    public void postAvatar(String userprofileid, String avatar, final ServerRequestListener listener) {
        String url = getURL(Services.USER_AVATAR);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userprofileid", userprofileid);
            jsonObject.put("mimetype", ".png");
            jsonObject.put("avatar", avatar);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }


    public void ItinerarySearch(String query, String prefrenceid, final ServerRequestListener listener) {
        String url = getURL(Services.ITINERARY);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("query", query);
            jsonObject.put("travelpreferenceprofileid", prefrenceid);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirplaneData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }


    public void ItineraryCNCSearch(String query, String prefrenceid, String itineraryid, final ServerRequestListener listener) {
        String url = getURL(Services.ITINERARY);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("query", query);
            jsonObject.put("travelpreferenceprofileid", prefrenceid);
            jsonObject.put("itineraryId", itineraryid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirplaneData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }

    public void deviceAuthentication(String deviceID,final ServerRequestListener listener){
        String url = getURL(Services.USER_PROFILE_DEVICE_AUTHENTICATION);
        url = url;// + deviceID;
        JSONObject jsonObject = new JSONObject();
       /* try {
            jsonObject.put("id", deviceID);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.loginData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void login(String email, String password,String id,String connectionID, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_LOGIN);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("username", email);
            jsonObject.put("DeviceUUID", id);
            jsonObject.put("ConnectionId", connectionID);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.loginData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }


    public void postChangePasswordWithOldPassword(String userName,String confirmpassword,String prevpassword, String password, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_CHANGE_PASSWORD);
//                        ConfirmPassword
//                        :
//                        "12345678"
//                        Username
//                        :
//                        "michael.gorlik@amginetech.com"
//                        password
//                        :
//                        "12345678"
//                        previouspassword
//                        :
//                        "Mg140989"
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Username", userName);
            jsonObject.put("ConfirmPassword", confirmpassword);
            jsonObject.put("previouspassword", prevpassword);
            jsonObject.put("password", password);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void postChoosePrebuiltPreferenceProfileId(String profileId, String profileName, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES);


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("defaultprofileid", profileId);
            jsonObject.put("profilename", profileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }


    public void checkoutSolutionId(String solutionid, HashSet<String> items, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_CHECKOUT);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("itineraryid", solutionid);

            JSONArray jsonArray = new JSONArray();
            for (String s : items) {
                jsonArray.put(s);
            }
            jsonObject.put("itemIds", jsonArray);//TODO NEED TO CHECK MIGHT NOT WORK

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirplaneData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },true);
    }

    public void postUserProfileAccountsWithEmail(String email, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_USER_PROFILE_ACCOUNTS);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", email);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }

    public void postPayItinerarForSolutionId(String profileId, ArrayList<String> items, final ServerRequestListener listener) {
        //TODO need to imopmemnt
    }

    public void postNewPreferenceProfile(String profilename, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profilename", profilename);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getSettingsAcount(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void postDefaultProfile(String defaultProfileId,String profilename, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("profilename", profilename);
            jsonObject.put("defaultprofileid", defaultProfileId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getSettingsAcount(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }




    public void postItineraryCNCSearch(ArrayList<AirportSendValuesVO> airportSendValuesVOs, String connectionID,final ServerRequestListener listener) {
        String url = getURL(Services.ITINERARYV2);//getURL(Services.ITINERARY);
        JSONObject jsonObjectMain = new JSONObject();

        JSONArray jsonArray = new JSONArray();

        for (AirportSendValuesVO airportSendValuesVO : airportSendValuesVOs) {

            try {

                jsonObjectMain.put("connectionid", connectionID);
                jsonObjectMain.put("itineraryid",airportSendValuesVO.getItineraryid());
                //Main for all query request

                jsonObjectMain.put("query", airportSendValuesVO.getQuery());
                jsonObjectMain.put("travelpreferenceprofileid", airportSendValuesVO.getTravelpreferenceprofileid());

//                //TODO need to remove
             /*   jsonObjectMain.put("latitude", "0");
                jsonObjectMain.put("longitude", "0");*/
                //latitude:32.0742538
                //longitude:34.8087051

                jsonObjectMain.put("latitude", 32.0742538);
                jsonObjectMain.put("longitude",34.8087051);

//                jsonObjectMain.put("latitude", airportSendValuesVO.getLatitude());
//                jsonObjectMain.put("longitude", airportSendValuesVO.getLongitude());

                jsonObjectMain.put("token", jsonArray);

                JSONObject jsonObjectSecond = new JSONObject();
                //it's array of cities
                jsonObjectSecond.put("type", "City");

                JSONObject jsonObjectThird = new JSONObject();
                JSONArray jsonArrayPosition = new JSONArray();
                jsonArrayPosition.put(jsonObjectThird);
                jsonObjectSecond.put("positions", jsonArrayPosition);

                jsonArray.put(jsonObjectSecond);

                jsonObjectThird.put("id", airportSendValuesVO.getId());
                jsonObjectThird.put("start", airportSendValuesVO.getStart());
                jsonObjectThird.put("end", airportSendValuesVO.getEnd());
                jsonObjectThird.put("value", airportSendValuesVO.getValue());
                jsonObjectThird.put("type", "City");


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObjectMain, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirplaneData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }

        }, false);
    }


    public void ItineraryCNCAddCompanionPost(ArrayList<AirportSendValuesVO> airportSendValuesVOs, final ServerRequestListener listener) {
        String url = getURL(Services.ITINERARY);
        url = url +"CNC";
        JSONObject jsonObjectMain = new JSONObject();

        for (AirportSendValuesVO airportSendValuesVO : airportSendValuesVOs) {

            try {

                jsonObjectMain.put("itineraryid", airportSendValuesVO.getId());
                //Main for all query request
                jsonObjectMain.put("query", airportSendValuesVO.getQuery());
                //TODO need to remove
                jsonObjectMain.put("latitude", 0);
                jsonObjectMain.put("longitude", 0);
                jsonObjectMain.put("travelpreferenceprofileid", airportSendValuesVO.getTravelpreferenceprofileid());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObjectMain, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirplaneData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }

        }, false);
    }


    public void addCreditCard(CreditCardItem creditCardItem, final ServerRequestListener listener) {

        //AddCreditCard addCreditCard = new AddCreditCard();

        HGBStringXMLRequest req = new HGBStringXMLRequest(Request.Method.POST, "https://beta.mycardstorage.com/api/api.asmx",creditCardItem,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },false);
    }


    public void AddCreditCardHelloGbye(JSONObject json,final ServerRequestListener listener) {
        String url = getURL(Services.CARD_TOKEN);


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                json, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseCreditCardList(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void sendPostSignalRRegistration(String connectionid, String userid, String solutionid, final ServerRequestListener listener) {

        //AddCreditCard addCreditCard = new AddCreditCard();

        String url = getURL(Services.POST_SIGNALR_REGISTRATION);
        JSONObject jsonObject = new JSONObject();


        try {

            jsonObject.put("connectionId", connectionid);
            jsonObject.put("userid", userid);
            jsonObject.put("solutionid",solutionid);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirplaneData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }

        }, false);
    }


    ////////////////////////////////
    // GETS
    ///////////////////////////////

    public void getBookedItinerary(String itineraryid,String connectionId,  final ServerRequestListener listener) {

        String url = getURL(Services.ITINERARY);
        url = url + "/booked/" + itineraryid +"/"+ connectionId;
        JSONObject jsonObject = new JSONObject();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirplaneData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }


    public void getItinerary(String solutionid, final ServerRequestListener listener) {

        String url = getURL(Services.ITINERARY);
        url = url + solutionid;
        JSONObject jsonObject = new JSONObject();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirplaneData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }



    public void getItineraryCNCSearch(String query,String connectionId, final ServerRequestListener listener) {

        String url = getURL(Services.ITINERARY_HIGHLIGHT);
        JSONObject jsonObject = new JSONObject();
        query = query.replaceAll(" " +
                "", "%20");

        url = url + query+"&clientConnectionId=" + connectionId;

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirportResult(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }

        }, false);

    }



    public void getCompanionsForSearch(final String searchParam, final ServerRequestListener listener){
        //  https://apiuat.hellogbye.com/uat/rest/UserProfile/Search?count=5&excludeCompanions=false&searchParam=a&skip=0
        String url = getURL(Services.COMPANION_SEARCH) + "searchParam="+searchParam+"&skip="+0;
        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseCompanionSearchData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
        // https://apiuat.hellogbye.com/uat/rest/UserProfile/Search?count=5&excludeCompanions=false&searchParam=a&skip=0
    }

    public void getConfirmationBooking(String iteneraryid,String itemGuid, final ServerRequestListener listener) {

        String url = getURL(Services.BOOKING_CONFIRMATION) + "itineraryId="+iteneraryid+"&itemGuid="+itemGuid;
        //  http://ec2-54-172-8-232.compute-1.amazonaws.com/web.api/rest/itinerary?count=15&skip=0

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }


    public void getMyTrips(final ServerRequestListener listener) {

        String url = getURL(Services.ITINERARY_MY_TRIP) + "?count=15&skip=0&upcomingtrips=true";
        //  http://ec2-54-172-8-232.compute-1.amazonaws.com/web.api/rest/itinerary?count=15&skip=0

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseMyTrips(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }



    public void getMyTripsPaid(final ServerRequestListener listener) {

        String url = getURL(Services.ITINERARY_MY_TRIP) + "?count=15&skip=0&upcomingtrips=true&paymentStatus=FPD,PPD";
        //  http://ec2-54-172-8-232.compute-1.amazonaws.com/web.api/rest/itinerary?count=15&skip=0

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseMyTrips(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }

    public void getCCSession(final ServerRequestListener listener) {

        String url = getURL(Services.CARD_SESSION);

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseCCSession(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }

    public void getMyTripsFavorite(final ServerRequestListener listener) {

        //   http://apidev.hellogbye.com/dev/rest/Itinerary?count=15&skip=0&isFavorite=true
        String url = getURL(Services.ITINERARY_MY_TRIP) + "?count=15&skip=0&isFavorite=true";
        //  http://ec2-54-172-8-232.compute-1.amazonaws.com/web.api/rest/itinerary?count=15&skip=0

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseMyTrips(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }

    public void getCreditCards(final ServerRequestListener listener) {
        String url = getURL(Services.CARD_TOKEN);

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseCreditCardList(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }

    public void getDefaultProfiles(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES_DEFAULT);

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseDefaultProfiles(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },false);

    }


    public void getPreferenceProfiles(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES);

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseDefaultProfiles(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }

    public void getUserSettingsDefault(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES);
        JSONObject jsonObject = new JSONObject();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getSettingsDefault(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

   /* public void getPreferencesForProfileId(String profileid, final ServerRequestListener listener) {
        String url = getURL(Services.USER_TRAVEL_PROFILES) + profileid + "/Preferences";
        JSONObject jsonObject = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }*/


    public void getUserProfile(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_PROFILE);
        JSONObject jsonObject = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseUser(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);

    }


/*
    public void getTravelProfiles(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES_DEFAULT);
        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getTravelProfileData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }
*/


    public void getSearchWithQuery(String query, String profileid, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_SEARCH_QUERY);
        query = query.replaceAll(" ", "%20");
        if (profileid != null) {
            url = url + query + "&TravelPreferenceProfileId=" + profileid;
        } else {
            url = url + query;
        }
        JSONObject jsonObject = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirplaneData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String parser = Parser.parseErrorMessage(error);

                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }


    public void getAlternateHotelsWithHotel(String solutioid, String paxid, String checkin, String checkout, final ServerRequestListener listener) {
        // http://gtaqa-1141527982.us-east-1.elb.amazonaws.com/GTAREST/REST/Hotel?solution=e977aac6-0fd7-4321-8e1f-44cb597cfbb2&paxid=9d2c85f5-d295-4064-a8c6-a4d0015b52e4&checkin=2015-09-02&checkout=2015-09-04
        String url = getURL(Services.USER_GET_HOTEL_ALTERNATIVE);
        String [] chekinArray = checkin.split("T");
        String [] chekoutArray = checkout.split("T");
        url = url + SOLUTION + solutioid + "&paxid=" + paxid + "&checkin=" + chekinArray[0] + "&checkout=" + chekoutArray[0];
        JSONObject jsonObject = new JSONObject();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseHotelData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }

    public void getAlternateHotelRoomsWithHotel(String solutioid, String paxid, String checkin, String checkout, String hotelcode, final ServerRequestListener listener) {
        String [] chekinArray = checkin.split("T");
        String [] chekoutArray = checkout.split("T");
        String url = getURL(Services.USER_HOTEL_ROOM_ALTERNATIVE);
        url = url+"?solution=" + solutioid + "&paxid=" + paxid + "&checkin=" + chekinArray[0] + "&checkout=" + chekoutArray[0] + "&hotelcode=" + hotelcode;

        JSONObject jsonObject = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseHotelRoomsData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },false);
    }

    //{"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}

    public void getUserSettingsAttributes(String attributesId, final ServerRequestListener listener) {
        String url = getURL(Services.USER_TRAVEL_PROFILES);
        JSONObject jsonObject = new JSONObject();


        url = url +  attributesId + "/" + PREFERENCES;

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getSettingsAttributeData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }


    public void getUserSettingAttributesForAttributeID(String attributesId, String type, final ServerRequestListener listener) {

        String url = getURL(Services.USER_GET_TRAVEL_PREFERENCES);

        JSONObject jsonObject = new JSONObject();

        url = url + "/" + PREFERENCES + "/" + type + "/" + "Attributes/" + attributesId + "/Values";


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getSettingsSpecificAttributeData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

//        }
    }



    public void getAlternativeHotelForFlightV2(String solutionid, String paxid, String checkin, String checkout, boolean isPromoAlternative, final ServerRequestListener listener) {


        String url = getURL(Services.USER_HOTEL_SOLUTIONS);
        String[] checkinSplit = checkin.split("T");
        String[] checkoutSplit = checkout.split("T");

        url = url + SOLUTION + solutionid + "&paxid=" + paxid + "&checkin=" + checkinSplit[0] + "&checkout=" + checkoutSplit[0] + "&isPromoAlternative="+isPromoAlternative;
        JSONObject jsonObject = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAlternativeFlight(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },true);

//https://apiprod.hellogbye.com/prod/rest/Hotel?solution=957a8e48-3d4c-4c38-bd03-598e434b0478&paxid=66ceb3dc-faf7-40c8-8292-32a641f782fd
// &checkin=2017-06-21&checkout=2017-06-24&isPromoAlternative=false

        //USER_HOTEL_SOLUTIONS
        //https://apiprod.hellogbye.com/prod/rest/Flight?solution=9f5dc7d9-24f2-46fd-b6ec-54ad0fb40b21&paxid=c47ae6eb-d3da-42b2-a2dc-af9e0eb322ee&flight=bfe3f7ec-fe19-430b-b9b3-a9176629d5c9
        //https://apiprod.hellogbye.com/prod/rest/Flight?solution=957a8e48-3d4c-4c38-bd03-598e434b0478&paxid=66ceb3dc-faf7-40c8-8292-32a641f782fd&flight=69cc12f0-5b98-4c30-a938-6caf696ede0b
    }


    public void getAlternateFlightsForFlight(String solutionid, String paxid, String flightid, final ServerRequestListener listener) {


        String url = getURL(Services.USER_FLIGHT_SOLUTIONS);
        url = url + SOLUTION + solutionid + "&paxid=" + paxid + "&flight=" + flightid;
        JSONObject jsonObject = new JSONObject();

        //https://apiprod.hellogbye.com/prod/rest/Flight?solution=9f5dc7d9-24f2-46fd-b6ec-54ad0fb40b21&paxid=093717ff-c063-4e99-a6b5-a78900a2d443&flight=4006f7aa-2a5d-4758-9118-51c1573dfd6e

        //https://apiprod.hellogbye.com/prod/rest/Flight?solution=957a8e48-3d4c-4c38-bd03-598e434b0478&paxid=66ceb3dc-faf7-40c8-8292-32a641f782fd&flight=69cc12f0-5b98-4c30-a938-6caf696ede0b


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAlternativeFlight(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },true);
    }


    //  http://cnc.hellogbye.com/cnc/rest/Statics/GetProvinceByCountryCode?countryCode=ID

    public void getStaticBookingProvince(String countryCode, final ServerRequestListener listener) {

        String url = getURL(Services.STATIC_PROVINCE_BY_COUNTRY_CODE);
        url = url + countryCode;
        JSONObject jsonObject = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseBookingProvinceOptions(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }



    public void getBookingOptions(final ServerRequestListener listener) {

        String url = getURL(Services.USER_GET_BOOKING_OPTIONS);

        JSONObject jsonObject = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseBookingOptions(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }

    public void getTravellersInforWithSolutionId(String solutionid, final ServerRequestListener listener) {

        String url = getURL(Services.USER_GET_TRAVELERS_INFO) + solutionid;
        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getTravels(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void getTravellerInforWithSolutionId(String solutionid, final ServerRequestListener listener) {

        String url = getURL(Services.USER_GET_TRAVELER_INFO) + solutionid;
        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getTravels(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void getUserProfileAccounts(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_USER_PROFILE_ACCOUNTS);
        JSONObject jsonObject = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.getAccounts(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },false);

    }

    public void getCompanionInvitation(final ServerRequestListener listener){

        String url = getURL(Services.COMPANIONS_INVITATION);

        JSONObject jsonObject = new JSONObject();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseCompanionsData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }

    public void getCompanions(final ServerRequestListener listener) {

        String url = getURL(Services.COMPANIONS);

        JSONObject jsonObject = new JSONObject();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseCompanionsData(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }


    public void getStaticCompanionsRelationTypesVO(final ServerRequestListener listener) {

        String url = getURL(Services.RELATIONSHIP_TYPES);
        //  http://ec2-54-172-8-232.compute-1.amazonaws.com/web.api/rest/itinerary?count=15&skip=0

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseStaticRelationTypes(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);

    }


    public void getAirlinePointsProgram(String itineraryId, String passengerId, final ServerRequestListener listener) {

        String url = getURL(Services.AIRLINE_POINTS_PROGRAM);
        //  http://ec2-54-172-8-232.compute-1.amazonaws.com/web.api/rest/itinerary?count=15&skip=0
        url  = url+itineraryId+"/passenger/"+passengerId;
        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirlinePointsPrograms(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);

    }


    public void getStaticAllAirlinePointsProgram( final ServerRequestListener listener) {

        String url = getURL(Services.STATIC_AIRLINE_POINTS_PROGRAM);
        //  http://ec2-54-172-8-232.compute-1.amazonaws.com/web.api/rest/itinerary?count=15&skip=0

        JSONObject jsonObject = new JSONObject();
        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseAirlinePointsPrograms(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);

    }
    //https://apiprod.hellogbye.com/prod/rest/AirlinePointsProgram/itinerary/cb37f18e-d8cb-45ed-8ccf-ef0df6d01701/passenger/c47ae6eb-d3da-42b2-a2dc-af9e0eb322ee

    ////////////////////////////////
    // DELETE
    ///////////////////////////////


    public void removeCreditCardHelloGbye(String token, final ServerRequestListener listener) {
        String url = getURL(Services.CARD_TOKEN);

        try{
            JSONObject json = new JSONObject();
            json.put("token",token);
            //THIS is a terrible work around because Volley doesnt support json body in DELETE https://code.google.com/p/android/issues/detail?id=65529
            // Hack - http://stackoverflow.com/questions/33553559/delete-request-with-header-and-parametes-volley
            HGBJsonRequest req = new HGBJsonRequest(true,Request.Method.DELETE, url,
                    json, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    listener.onSuccess(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    listener.onError(Parser.parseErrorMessage(error));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteAirlinePointsProgram(String programNumber, final ServerRequestListener listener) {
        String url = getURL(Services.AIRLINE_POINTS_USER_PROGRAMS);

        url = url + programNumber;
        JSONObject json = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.DELETE, url,
                json, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

    }


    public void deleteItinerary(String solutionid, final ServerRequestListener listener) {

        String url = getURL(Services.ITINERARY);
        url = url + solutionid;
        JSONObject jsonObject = new JSONObject();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.DELETE, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        }, false);
    }


    public void deleteUserCompanion(String companion_id, final ServerRequestListener listener) {
        String url = getURL(Services.COMPANIONS);
        url = url + "/" + companion_id;

        HashMap<String, String> map = new HashMap<String, String>();

        HGBStringRequest req = new HGBStringRequest(Request.Method.DELETE, url,
                map, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void rejectUserCompanion(String companion_id, final ServerRequestListener listener) {
        String url = getURL(Services.COMPANIONS);
        url = url + "/" + companion_id+"/Invite";

        HashMap<String, String> map = new HashMap<String, String>();

        HGBStringRequest req = new HGBStringRequest(Request.Method.DELETE, url,
                map, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void deleteUserProfileAccountsWithEmail(String email, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_USER_PROFILE_ACCOUNTS);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", email);*/

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.DELETE, url,
                jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });

  /*      HGBStringRequest req = new HGBStringRequest(Request.Method.DELETE, url,
                map, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError SignalRRrror) {
                listener.onError(Parser.parseErrorMessage(SignalRRrror));
            }
        });*/
    }


    public void deletePreferenceProfileId(String profileid, final ServerRequestListener listener) {
        String url = getURL(Services.USER_TRAVEL_PROFILES) + profileid;
        HashMap<String, String> map = new HashMap<String, String>();


        HGBStringRequest req = new HGBStringRequest(Request.Method.DELETE, url,
                map, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }


    ////////////////////////////////
    // PUT
    ///////////////////////////////



    public void updateAvailability(String itirneraryId, ArrayList<UserProfileVO> users, final ServerRequestListener listener){
        String url = getURL(Services.UPDATE_AVAILABILITY);

        JSONObject json1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        try {
            json1.put("ItineraryId", itirneraryId);

            for (UserProfileVO userData : users) {

                JSONObject jsonUser = new JSONObject();
                jsonUser.put("address", userData.getAddress());
                jsonUser.put("city", userData.getCity());
                jsonUser.put("dateofbirth", userData.getDob());
                jsonUser.put("email", userData.getEmailaddress());
                jsonUser.put("firstname", userData.getFirstname());
                jsonUser.put("gender", userData.getGender());
                jsonUser.put("lastname", userData.getLastname());
                jsonUser.put("middlename", userData.getMiddlename());
                jsonUser.put("paxguid", userData.getPaxid());
                jsonUser.put("paxmileage", userData.getPaxmileage());//TODO need to add
                jsonUser.put("postalcode", userData.getPostalcode());
                jsonUser.put("primaryphone", userData.getPhone());
                jsonUser.put("province", userData.getState());

                jsonArray.put(jsonUser);


            }
            json1.put("Paxdetails",jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                json1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                UpdateAvailabilityVO updateAvailabilityVO = (UpdateAvailabilityVO)Parser.getUpdateAvailabilityVO(response);
                Map<String,Object> map = (Map<String, Object>) Parser.getUpdateAvailabilityVOMap(response);


                //hotels
                ArrayList<UpdateAvailabilityItemVO>  hotels = (ArrayList<UpdateAvailabilityItemVO> )map.get("hotels");

                if(hotels != null && !hotels.isEmpty()){
                    Map<String, Object> taxesHotel = (Map<String, Object>) hotels.get(0);
                    Map<String, Object>  taxHotelHash = (Map<String, Object>) taxesHotel.get("taxbreakdown");
                    updateAvailabilityVO.getHotels().get(0).setTaxbreakdownhash(taxHotelHash);
                }

                //flights
                ArrayList<UpdateAvailabilityItemVO> flights = (ArrayList<UpdateAvailabilityItemVO>) map.get("flights");
                if(flights != null && !flights.isEmpty()) {

                    Map<String, Object> taxesFlight = (Map<String, Object>) flights.get(0);
                    Map<String, Object> taxFlightHash = (Map<String, Object>) taxesFlight.get("taxbreakdown");

                    updateAvailabilityVO.getFlights().get(0).setTaxbreakdownhash(taxFlightHash);
                }


              //  updateAvailability.getFlights().get(0).setTaxbreakdownhash();

                listener.onSuccess(updateAvailabilityVO);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },false);
    }

    public void confirmCompanion(String companion_id, final ServerRequestListener listener){
        String url = getURL(Services.COMPANIONS_CONFIRM);
        url = url + companion_id;
        JSONObject json1 = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                json1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void putItenararyTripName(final String name, final String itineraryID, final ServerRequestListener listener) {
        String url = getURL(Services.ITINERARY);
        url = url + itineraryID;

       /*JSONObject jsonObjectWrapper = new JSONObject();*/

        JSONObject json1 = new JSONObject();
        try {
            json1.put("name", name);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                json1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void putFavorityItenarary(final boolean isFavority,final String itineraryID, final ServerRequestListener listener) {
        String url = getURL(Services.ITINERARY);
        url = url + itineraryID;

       /*JSONObject jsonObjectWrapper = new JSONObject();*/

        JSONObject json1 = new JSONObject();
        try {
            json1.put("isfavorite", isFavority);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                json1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void putAccountsPreferences(String email, String travelpreferenceprofileid, final ServerRequestListener listener) {
        String url = getURL(Services.ACCOUNTS_PREFERENCES);

        JSONObject json1 = new JSONObject();
        try {
            json1.put("email", email);
            json1.put("travelpreferenceprofileid", travelpreferenceprofileid);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                json1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },false);
    }


    public void putCompanionRelationship(String companionid, int relationshiptypeId, final ServerRequestListener listener) {
        String url = getURL(Services.COMPANIONS);
        JSONObject json1 = new JSONObject();
        try {

            json1.put("companionid", companionid);
            json1.put("relationshiptypeId", relationshiptypeId);

        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                json1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void putCompanion(String id, UserProfileVO user, final ServerRequestListener listener) {
        String url = getURL(Services.COMPANIONS);
        JSONObject json1 = new JSONObject();
        try {

            json1.put("companionid", id);
            JSONObject json2 = new JSONObject();
            json2.put("state", user.getState());
            json2.put("emailaddress", user.getEmailaddress());
            json2.put("phone", user.getPhone());
            json2.put("postalcode", user.getPostalcode());
            json2.put("lastname", user.getLastname());
            json2.put("dob", HGBUtilityDate.parseDateToServertime(user.getDob()));
            json2.put("firstname", user.getFirstname());
            json2.put("title", user.getTitle());
            json2.put("address", user.getAddress());
            json2.put("city", user.getCity());
            json2.put("country", user.getCountry());
            json2.put("gender", user.getGender());
            json2.put("userprofileid", user.getUserprofileid());
            json2.put("middlename", user.getMiddlename());

            json1.put("stubcompanion", json2);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                json1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }



    public void putUserSettings(UserProfileVO user, final ServerRequestListener listener){

        String url = getURL(Services.USER_GET_PROFILE);
        JSONObject json1 = new JSONObject();
        try {

            json1.put("state", user.getState());
            json1.put("usercountry", user.getCountry());
            json1.put("phone", user.getPhone());
            json1.put("postalcode", user.getPostalcode());
            json1.put("lastname", user.getLastname());
            json1.put("dob", HGBUtilityDate.parseDateToServertime(user.getDob()));
            json1.put("firstname", user.getFirstname());
            json1.put("title", user.getTitle());
            json1.put("address", user.getAddress());
            json1.put("city", user.getCity());
            json1.put("country", user.getCountry());
            json1.put("avatar", user.getAvatar());
            json1.put("userprofileid", user.getUserprofileid());
            json1.put("middlename", user.getMiddlename());



        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                json1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }


    private JSONArray createArrayToPut(List<SettingsValuesVO> putAttributesValues) {
        JSONArray array = new JSONArray();

        try {
            for (SettingsValuesVO settingsValuesVO : putAttributesValues) {
                String description = settingsValuesVO.getmDescription();
                String id = settingsValuesVO.getmID();
                int rank = Integer.parseInt(settingsValuesVO.getmRank());
                String name = settingsValuesVO.getmName();


                JSONObject jsonObject = new JSONObject();
                jsonObject.put("description", description);
                jsonObject.put("id", id);
                jsonObject.put("rank", rank);
                jsonObject.put("name", name);
                array.put(jsonObject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return array;
    }

    public void putAttributesValues(String prefrenceid, String type, String attributeid, List<SettingsValuesVO> putAttributesValues, final ServerRequestListener listener) {
        //  {"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}
        String url = getURL(Services.USER_TRAVEL_PROFILES) + prefrenceid + "/Preferences/" + type + "/Attributes/" + attributeid + "/Values";

        JSONArray array = createArrayToPut(putAttributesValues);


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                array, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }


    public void putAlternateHotel(String solutionid, String paxid, String checkin, String checkout, String hotelid, final ServerRequestListener listener) {
        String url = getURL(Services.USER_PUT_HOTEL);
        JSONObject jsonObjectWrapper = new JSONObject();
        String [] chekinArray = checkin.split("T");
        String [] chekoutArray = checkout.split("T");
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("solution", solutionid);
            jsonObject.put("paxid", paxid);
            jsonObject.put("checkin", chekinArray[0]);
            jsonObject.put("checkout", chekoutArray[0]);
            jsonObjectWrapper.put("parameters", jsonObject);
            jsonObjectWrapper.put("hotel", hotelid);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                jsonObjectWrapper, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }


    public void putAlternateHotelRoom(String solutioid, String paxid,String hotelroomid, final ServerRequestListener listener) {
        String url = getURL(Services.USER_HOTEL_ROOM_ALTERNATIVE);
        JSONObject jsonObjectWrapper = new JSONObject();
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("solution", solutioid);
            jsonObject.put("paxid", paxid);

            jsonObjectWrapper.put("parameters", jsonObject);
            jsonObjectWrapper.put("hotelroom", hotelroomid);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                jsonObjectWrapper, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }


    public void UpdateCreditCardHelloGbye(JSONObject json,final ServerRequestListener listener) {
        String url = getURL(Services.CARD_TOKEN);


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                json, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(Parser.parseCreditCardList(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        },false);

    }

    public void putFlight(String solutioid, String paxid, String bookedflight, String newflight, final ServerRequestListener listener) {
        String url = getURL(Services.USER_FLIGHT_SOLUTIONS);
        JSONObject jsonObjectWrapper = new JSONObject();

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("solution", solutioid);
            jsonObject.put("paxid", paxid);
            jsonObject.put("flight", bookedflight); //primaryguid
            jsonObjectWrapper.put("parameters", jsonObject);
            jsonObjectWrapper.put("flight", newflight); //guid


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                jsonObjectWrapper, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public void resetPasswordWithEmail(String email, final ServerRequestListener listener) {
        //  {"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}
        String url = getURL(Services.USER_PROFILE_RESET_PASSWORD) + email;//TODO need to check this looks wierd.....
        JSONObject jsonObjectWrapper = new JSONObject();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                jsonObjectWrapper, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }


    public void putSolutionWithId(String solutionID, boolean isfav, final ServerRequestListener listener) {
        String url = getURL(Services.USER_SOLUTION) + solutionID;
        JSONObject jsonObjectWrapper = new JSONObject();
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isfavorite", isfav);


        } catch (Exception e) {
            e.printStackTrace();
        }

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
                jsonObjectWrapper, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(Parser.parseErrorMessage(error));
            }
        });
    }

    public enum Services {
        USER_POST_LOGIN("Session"),
        USER_GET_PROFILE("UserProfile"),
        USER_POST_CHANGE_PASSWORD("UserProfile/Password"),
        USER_GET_TRAVEL_PROFILES("TravelPreference/Profiles"),
        //     USER_GET_TRAVEL_PROFILES_DEFAULT("TravelPreference/Profiles/Defaults"),
        USER_POST_CHECKOUT("CheckOut"),
        USER_GET_SEARCH_QUERY("Solution/Primarysearch?query="),
        USER_GET_HOTEL_ALTERNATIVE("Hotel"),
        USER_HOTEL_ROOM_ALTERNATIVE("hotelroom"),
        USER_PUT_HOTEL("Hotel"),
        USER_GET_BOOKING_OPTIONS("Statics/BookingOptions"),
        USER_FLIGHT_SOLUTIONS("Flight"),
        USER_HOTEL_SOLUTIONS("Hotel"),
        USER_GET_TRAVELERS_INFO("Traveler/GetAll/"),
        USER_GET_TRAVELER_INFO("Traveler/Get/"),
        USER_GET_USER_PROFILE_ACCOUNTS("UserProfile/Accounts"),
        USER_POST_USER_PROFILE_EMAIL("UserProfile/ResetPassword?email="),
        USER_TRAVEL_PROFILES("TravelPreference/Profiles/"),
        USER_PROFILE_RESET_PASSWORD("UserProfile/ResetPassword?email="),
        USER_SOLUTION("Solution/"),
        ITINERARY("Itinerary/"),
        ITINERARYV2("Itinerary/V2"),
        USER_GET_TRAVEL_PREFERENCES("TravelPreference"),

        COMPANIONS("Companions"),
        COMPANIONS_CONFIRM("Companions/Confirm/"),
        COMPANIONS_INVITATION("Companions/Invitations"),
        CARD_TOKEN("Card/Token"),
        ITINERARY_MY_TRIP("Itinerary"),
        ITINERARY_HIGHLIGHT("Highlight?input="),
        USER_AVATAR("UserProfile/Avatar"),
        RELATIONSHIP_TYPES("Statics/RelationshipTypes"),
        ACCOUNTS_PREFERENCES("UserProfile/Accounts/TravelPreference"),
        USER_PROFILE_REGISTER("UserProfile/Register"),
        STATIC_PROVINCE_BY_COUNTRY_CODE("Statics/GetProvinceByCountryCode?countryCode="),
        CARD_SESSION("Card/Session"),
        BOOKING_PAY("booking/pay"),
        USER_PROFILE_DEVICE_AUTHENTICATION("Session/Anonymous/"),
        BOOKING_CONFIRMATION("Booking/flight/confirmation/pdf?"),
        SUBMIT_FEEDBACK("Feedback"),
        USER_ACTIVATION_PIN("UserProfile/Activate?activationKey="),
        ACTIVATE_NOTIFICATION("PushNotifications/Enable/"),

        HOTEL_SEARCH("hotel/Search"),
        USER_GET_TRAVEL_PROFILES_DEFAULT("TravelPreference/Profiles/Defaults"),
        STATIC_CITY_AUTOCOMPLETE("statics/cityautocomplete"),
        COMPANION_SEARCH("UserProfile/Search?count=5&excludeCompanions=false&"),
        RESEND_ACTIVATION("UserProfile/ResendWelcomeEmail?email="),
        POST_SIGNALR_REGISTRATION("Signalr/Register"),
        AIRLINE_POINTS_PROGRAM("AirlinePointsProgram/itinerary/"),
        AIRLINE_POINTS_USER_PROGRAMS("AirlinePointsProgram"),
        STATIC_AIRLINE_POINTS_PROGRAM("Statics/AllAirlinePointsPrograms"),
        UPDATE_AVAILABILITY("Itinerary/UpdateAvailability");

        //https://apiprod.hellogbye.com/prod/rest/AirlinePointsProgram/itinerary/cb37f18e-d8cb-45ed-8ccf-ef0df6d01701/passenger/c47ae6eb-d3da-42b2-a2dc-af9e0eb322ee


        String url;
        Services(String url){
            this.url =  url;
        }

        public String getURL(){
            String baseUrl = changeServerURL();
            baseUrl = baseUrl + url;
            return baseUrl;
        }
    }

    public static String getURL(Services type) {
        return type.getURL();
    }
}
