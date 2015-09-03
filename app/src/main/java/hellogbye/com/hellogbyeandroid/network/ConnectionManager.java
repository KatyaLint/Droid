package hellogbye.com.hellogbyeandroid.network;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
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
        USER_POST_LOGIN, USER_GET_PROFILE,USER_POST_PREFERENCE,USER_POST_CHANGE_PASSWORD,
        USER_POST_TRAVEL_PROFILES,USER_GET_TRAVEL_PROFILES_DEFAULT,USER_POST_CHECKOUT,
        USER_GET_SEARCH_QUERY,USER_GET_UPDATE_SEARCH_QUERY
    }

    //TODO DOnt undersatnd these calls
   // updateSearchWithQuery


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

    public void postChangePasswordWithOldPassword(String prevpassword, String password, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_CHANGE_PASSWORD);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("previouspassword", prevpassword);
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

    public void getTravelProfiles(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES_DEFAULT);
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

    public void postChoosePrebuiltPreferenceProfileId(String profileId, String profileName, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_TRAVEL_PROFILES);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("defaultprofileid", profileId);
        map.put("profilename", profileName);

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
    public void checkoutSolutionId(String profileId, ArrayList<String> items, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_CHECKOUT);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("solutionId", profileId);
        map.put("ItemIds", items.toString());//TODO NEED TO CHECK MIGHT NOT WORK

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

    public void getSearchWithQuery(String query, String profileid, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_SEARCH_QUERY);
        query = query.replaceAll(" ","%20");
        if(profileid !=null){
            url= url+query+"&TravelPreferenceProfileId="+profileid;
        }else{
           url= url+query;
        }


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

//    public void getSearchWithQuery(String query, String profileid, final ServerRequestListener listener) {
//        String url = getURL(Services.USER_GET_SEARCH_QUERY);
//        query = query.replaceAll(" ","%20");
//        if(profileid !=null){
//            url= url+query+"&TravelPreferenceProfileId="+profileid;
//        }else{
//            url= url+query;
//        }
//
//
//        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
//                null, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                listener.onError(Parser.parseErrorMessage(error));
//            }
//        });
//    }

   // http://gtaqa-1141527982.us-east-1.elb.amazonaws.com/GTAREST/REST/Hotel?solution=e977aac6-0fd7-4321-8e1f-44cb597cfbb2&paxid=9d2c85f5-d295-4064-a8c6-a4d0015b52e4&checkin=2015-09-02&checkout=2015-09-04


    private String getURL(Services type) {
        String url = "";
        switch (type) {
            case USER_POST_LOGIN:
                return BASE_URL + "Session";
            case USER_GET_PROFILE:
                return BASE_URL + "UserProfile";
            case USER_POST_PREFERENCE:
                return BASE_URL + "TravelPreference/Profiles";
            case USER_POST_CHANGE_PASSWORD:
                return BASE_URL + "UserProfile/Password";
            case USER_GET_TRAVEL_PROFILES_DEFAULT:
                return BASE_URL + "TravelPreference/Profiles/Defaults";
            case USER_POST_TRAVEL_PROFILES:
                return BASE_URL + "TravelPreference/Profiles";
            case USER_POST_CHECKOUT:
                return BASE_URL + "CheckOut";
            case USER_GET_SEARCH_QUERY:
                return BASE_URL + "Solution/Primarysearch?query=";
            case USER_GET_UPDATE_SEARCH_QUERY:
                return BASE_URL + "Solution/UpdateSearch?query=";

        }
        return url;
    }


}
