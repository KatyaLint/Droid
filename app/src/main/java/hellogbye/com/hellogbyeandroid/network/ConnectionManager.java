package hellogbye.com.hellogbyeandroid.network;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

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
        USER_GET_SEARCH_QUERY,USER_GET_UPDATE_SEARCH_QUERY,USER_GET_HOTEL_ALTERNATIVE,
        USER_GET_HOTEL_ROOM_ALTERNATIVE,USER_PUT_HOTEL,USER_GET_BOOKING_OPTIONS,
        USER_GET_FLIGHT_SOLUTIONS,USER_GET_TRAVELER_INFO,USER_GET_USER_PROFILE_ACCOUNTS,
        USER_POST_USER_PROFILE_EMAIL,USER_TRAVEL_PROFILES
    }

    //TODO DOnt undersatnd these calls
   // updateSearchWithQuery
    //putAlternateHotel


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


    public void getAlternateHotelsWithHotel(String solutioid, String paxid,String checkin,String checkout, final ServerRequestListener listener) {
        // http://gtaqa-1141527982.us-east-1.elb.amazonaws.com/GTAREST/REST/Hotel?solution=e977aac6-0fd7-4321-8e1f-44cb597cfbb2&paxid=9d2c85f5-d295-4064-a8c6-a4d0015b52e4&checkin=2015-09-02&checkout=2015-09-04
        String url = getURL(Services.USER_GET_HOTEL_ALTERNATIVE);
        url= url+solutioid+"&paxid="+paxid+"&checkin="+checkin+"&checkout="+checkout;



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

    public void getAlternateHotelRoomsWithHotel(String solutioid, String paxid,String checkin,String checkout,String hotelcode ,final ServerRequestListener listener) {

        String url = getURL(Services.USER_GET_HOTEL_ROOM_ALTERNATIVE);
        url= url+solutioid+"&paxid="+paxid+"&checkin="+checkin+"&checkout="+checkout+"hotelcode="+hotelcode;



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

    //{"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}

    public void putAlternateHotel(String solutioid, String paxid,String checkin,String checkout, final ServerRequestListener listener) {
      //  {"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}
        String url = getURL(Services.USER_PUT_HOTEL);
        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jsonObjectWrapper = new JSONObject();
        try{
            HashMap<String, String> mapinner = new HashMap<String, String>();
            mapinner.put("solution","c86d9879-eb15-4164-8b75-6bbac0787b75");
            mapinner.put("paxid","9d2c85f5-d295-4064-a8c6-a4d0015b52e4");
            mapinner.put("checkin","2015-09-03");
            mapinner.put("checkout","2015-09-05");


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("solution","c86d9879-eb15-4164-8b75-6bbac0787b75");
            jsonObject.put("paxid","9d2c85f5-d295-4064-a8c6-a4d0015b52e4");
            jsonObject.put("checkin","2015-09-03");
            jsonObject.put("checkout","2015-09-05");
            jsonObjectWrapper.put("parameters", jsonObject.toString());
            jsonObjectWrapper.put("hotel","c329c20a-4836-4bec-9580-48f7814e9fbd");

            map.put("parameters", jsonObject.toString());
            map.put("hotel", "c329c20a-4836-4bec-9580-48f7814e9fbd");

        }catch (Exception e ){
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.PUT, url, map, null, null);
        requestQueue.add(jsObjRequest);


//
//        HGBJsonRequest req = new HGBJsonRequest(Request.Method.PUT, url,
//                map, new Response.Listener<String>() {
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
    }




    public void getAlternateFlightsForFlight(String solutioid, String paxid,String flightid ,final ServerRequestListener listener) {

        String url = getURL(Services.USER_GET_FLIGHT_SOLUTIONS);
        url= url+solutioid+"&paxid="+paxid+"&flight="+flightid;



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

    public void getBookingOptions(final ServerRequestListener listener) {

        String url = getURL(Services.USER_GET_BOOKING_OPTIONS);
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

    public void getTravellersInforWithSolutionId(String solutionid,final ServerRequestListener listener) {

        String url = getURL(Services.USER_GET_TRAVELER_INFO)+solutionid;
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

    public void postPayItinerarForSolutionId(String profileId, ArrayList<String> items, final ServerRequestListener listener) {
      //TODO need to imopmemnt
    }

    public void getUserProfileAccounts(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_USER_PROFILE_ACCOUNTS);


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

    public void postUserProfileAccountsWithEmail(String email, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_USER_PROFILE_ACCOUNTS);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", email);

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

    public void deleteUserProfileAccountsWithEmail(String email, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_USER_PROFILE_ACCOUNTS);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", email);

        HGBJsonRequest req = new HGBJsonRequest(Request.Method.DELETE, url,
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


    public void getPreferenceProfiles(final ServerRequestListener listener) {
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

    public void postNewPreferenceProfile(String profilename, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_PREFERENCE);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("profilename", profilename);

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

    public void deletePreferenceProfileId(String profileid, final ServerRequestListener listener) {
        String url = getURL(Services.USER_TRAVEL_PROFILES)+profileid;
        HashMap<String, String> map = new HashMap<String, String>();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.DELETE, url,
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

    public void getPreferencesForProfileId(String profileid,final ServerRequestListener listener) {
        String url = getURL(Services.USER_TRAVEL_PROFILES)+profileid+"/Preferences";


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
            case USER_GET_HOTEL_ALTERNATIVE:
                return BASE_URL + "Hotel?solution=";
            case USER_GET_HOTEL_ROOM_ALTERNATIVE:
                return BASE_URL + "HotelRoom?solution=";
            case USER_PUT_HOTEL:
                return BASE_URL + "Hotel";
            case USER_GET_BOOKING_OPTIONS:
                return BASE_URL + "Statics/BookingOptions";
            case USER_GET_FLIGHT_SOLUTIONS:
                return BASE_URL + "Flight?solution=";
            case USER_GET_TRAVELER_INFO:
                return BASE_URL + "Traveler/Get/";
            case USER_GET_USER_PROFILE_ACCOUNTS:
                return BASE_URL + "UserProfile/Accounts";
            case USER_POST_USER_PROFILE_EMAIL:
                return BASE_URL + "UserProfile/ResetPassword?email=";
            case USER_TRAVEL_PROFILES:
                return BASE_URL + "TravelPreference/Profiles/";














        }
        return url;
    }


}
