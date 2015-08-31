package hellogbye.com.hellogbyeandroid.network;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.activities.LoginTest;

public class ConnectionManager {


    public interface ServerRequestListener {
        public void onSuccess(Object data);

        public void onError(Object data);
    }

    public static String BASE_URL = "http://gtaqa-1141527982.us-east-1.elb.amazonaws.com/GTAREST/REST/";
    private static ConnectionManager _instance;
    private Context mContext;

    public enum Services {
        USER_POST_LOGIN, USER_GET_PROFILE;
    }


    private ConnectionManager() {


    }

    public static ConnectionManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new ConnectionManager();
        }
        _instance.mContext = context;
        HGBJsonRequest.setContext(context);
        return _instance;
    }


//    public void getEstimateTimeForOrder(String id, final ServerRequestListener listener) {
//        String url = getURL(Services.GET_ESTIMATION_ORDER);
//        url = url + "?orderID=" + id;
//        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
//                null, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(Parser.parseEstimation(response));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                listener.onError(Parser.parseErrorMessage(error));
//            }
//        }, false);
//
//
//    }


    public void login(String email, String password, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_LOGIN);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", email);
        map.put("password", password);

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
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


    public void getUserProfile(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_PROFILE);


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
                null, new Response.Listener<String>() {
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


    private String getURL(Services type) {
        String url = "";
        switch (type) {
            case USER_POST_LOGIN:
                return BASE_URL + "Session";
            case USER_GET_PROFILE:
                return BASE_URL + "UserProfile";

        }
        return url;
    }


}
