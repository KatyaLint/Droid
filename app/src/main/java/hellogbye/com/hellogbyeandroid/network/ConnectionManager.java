package hellogbye.com.hellogbyeandroid.network;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import java.util.HashMap;

public class ConnectionManager {


    public interface ServerRequestListener {
         void onSuccess(Object data);
         void onError(Object data);
    }

    public static String BASE_URL = "http://gtaqa-1141527982.us-east-1.elb.amazonaws.com/GTAREST/REST/";
    private static ConnectionManager _instance;
    private Context mContext;

    public enum Services {
        USER_POST_LOGIN, USER_GET_PROFILE,USER_POST_PREFERENCE
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



    public void getPreference(final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_PREFERENCE);
        HashMap<String, String> map = new HashMap<String, String>();

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
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
            case USER_POST_PREFERENCE:
                return BASE_URL + "TravelPreference/Profiles";

        }
        return url;
    }


}
