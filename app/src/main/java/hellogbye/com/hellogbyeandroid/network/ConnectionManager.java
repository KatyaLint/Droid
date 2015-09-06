package hellogbye.com.hellogbyeandroid.network;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
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

    private String SOLUTION="?solution=";

    public enum Services {
        USER_POST_LOGIN, USER_GET_PROFILE,USER_POST_PREFERENCE,USER_POST_CHANGE_PASSWORD,
        USER_POST_TRAVEL_PROFILES,USER_GET_TRAVEL_PROFILES_DEFAULT,USER_POST_CHECKOUT,
        USER_GET_SEARCH_QUERY,USER_GET_UPDATE_SEARCH_QUERY,USER_GET_HOTEL_ALTERNATIVE,
        USER_HOTEL_ROOM_ALTERNATIVE,USER_PUT_HOTEL,USER_GET_BOOKING_OPTIONS,
        USER_FLIGHT_SOLUTIONS,USER_GET_TRAVELER_INFO,USER_GET_USER_PROFILE_ACCOUNTS,
        USER_POST_USER_PROFILE_EMAIL,USER_TRAVEL_PROFILES,USER_PROFILE_RESET_PASSWORD,
        USER_SOLUTION
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
        HGBStringRequest.setContext(context);
        HGBJsonRequest.setContext(context);
        return _instance;
    }


    ////////////////////////////////
    // POST
    ///////////////////////////////


    public void login(String email, String password, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_LOGIN);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", email);
        map.put("password", password);

        HGBStringRequest req = new HGBStringRequest(Request.Method.POST, url,
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





    public void postChangePasswordWithOldPassword(String prevpassword, String password, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_CHANGE_PASSWORD);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("previouspassword", prevpassword);
        map.put("password", password);

        HGBStringRequest req = new HGBStringRequest(Request.Method.POST, url,
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

    public void postChoosePrebuiltPreferenceProfileId(String profileId, String profileName, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_TRAVEL_PROFILES);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("defaultprofileid", profileId);
        map.put("profilename", profileName);

        HGBStringRequest req = new HGBStringRequest(Request.Method.POST, url,
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

        HGBStringRequest req = new HGBStringRequest(Request.Method.POST, url,
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

    public void postUserProfileAccountsWithEmail(String email, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_USER_PROFILE_ACCOUNTS);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", email);

        HGBStringRequest req = new HGBStringRequest(Request.Method.POST, url,
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

    public void postPayItinerarForSolutionId(String profileId, ArrayList<String> items, final ServerRequestListener listener) {
        //TODO need to imopmemnt
    }

    public void postNewPreferenceProfile(String profilename, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_PREFERENCE);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("profilename", profilename);

        HGBStringRequest req = new HGBStringRequest(Request.Method.POST, url,
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
    // GETS
    ///////////////////////////////


    public void getPreferenceProfiles(final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_PREFERENCE);


        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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


    public void getPreferencesForProfileId(String profileid,final ServerRequestListener listener) {
        String url = getURL(Services.USER_TRAVEL_PROFILES)+profileid+"/Preferences";


        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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



    public void getPreference(final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_PREFERENCE);


        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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


        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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



    public void getTravelProfiles(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES_DEFAULT);
        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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


    public void getSearchWithQuery(String query, String profileid, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_SEARCH_QUERY);
        query = query.replaceAll(" ","%20");
        if(profileid !=null){
            url= url+query+"&TravelPreferenceProfileId="+profileid;
        }else{
           url= url+query;
        }


        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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
        url= url+SOLUTION+"&paxid="+paxid+"&checkin="+checkin+"&checkout="+checkout;



        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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

        String url = getURL(Services.USER_HOTEL_ROOM_ALTERNATIVE);
        url= url+solutioid+"&paxid="+paxid+"&checkin="+checkin+"&checkout="+checkout+"hotelcode="+hotelcode;



        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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



    public void getAlternateFlightsForFlight(String solutioid, String paxid,String flightid ,final ServerRequestListener listener) {

        String url = getURL(Services.USER_FLIGHT_SOLUTIONS);
        url= url+SOLUTION+"&paxid="+paxid+"&flight="+flightid;



        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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
        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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
        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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



    public void getUserProfileAccounts(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_USER_PROFILE_ACCOUNTS);


        HGBStringRequest req = new HGBStringRequest(Request.Method.GET, url,
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


    ////////////////////////////////
    // DELETE
    ///////////////////////////////


    public void deleteUserProfileAccountsWithEmail(String email, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_USER_PROFILE_ACCOUNTS);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("username", email);

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





    public void deletePreferenceProfileId(String profileid, final ServerRequestListener listener) {
        String url = getURL(Services.USER_TRAVEL_PROFILES)+profileid;
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

    public void putAttributesValues(String profileid,String prefrenceid,String attributeid,String description,String id,String rank,String name,final ServerRequestListener listener) {
        //  {"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}
        String url = getURL(Services.USER_TRAVEL_PROFILES)+profileid+"/Preferences/"+prefrenceid+"/Attributes/"+attributeid+"/Values";
        HashMap<String, String> map = new HashMap<String, String>();
        JSONArray array = new JSONArray();
        try{
            JSONObject json1 = new JSONObject();
            json1.put("description",description);
            json1.put("id",id);
            json1.put("rank",rank);
            json1.put("name",name);
            array.put(json1);
        }catch (Exception e ){
            e.printStackTrace();
        }

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

    public void putAlternateHotel(String solutionid, String paxid,String checkin,String checkout,String hotelid ,final ServerRequestListener listener) {
        //  {"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}
        String url = getURL(Services.USER_PUT_HOTEL);
        JSONObject jsonObjectWrapper = new JSONObject();
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("solution", solutionid);
            jsonObject.put("paxid", paxid);
            jsonObject.put("checkin",checkin);
            jsonObject.put("checkout", checkout);
            jsonObjectWrapper.put("parameters", jsonObject);
            jsonObjectWrapper.put("hotel", hotelid);


        }catch (Exception e ){
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


    public void putAlternateHotelRoom(String solutioid, String paxid,String checkin,String checkout,String hotelroomid , final ServerRequestListener listener) {
        //  {"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}
        String url = getURL(Services.USER_HOTEL_ROOM_ALTERNATIVE);
        JSONObject jsonObjectWrapper = new JSONObject();
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("solution", solutioid);
            jsonObject.put("paxid", paxid);
            jsonObject.put("checkin", checkin);
            jsonObject.put("checkout", checkout);
            jsonObjectWrapper.put("parameters", jsonObject);
            jsonObjectWrapper.put("hotelroom", hotelroomid);


        }catch (Exception e ){
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

    public void putFlight(String solutioid, String paxid,String bookedflight,String newflight , final ServerRequestListener listener) {
        //  {"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}
        String url = getURL(Services.USER_FLIGHT_SOLUTIONS);
        JSONObject jsonObjectWrapper = new JSONObject();
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("solution", solutioid);
            jsonObject.put("paxid", paxid);
            jsonObject.put("flight", bookedflight);
            jsonObjectWrapper.put("parameters", jsonObject);
            jsonObjectWrapper.put("flight", newflight);


        }catch (Exception e ){
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

    public void resetPasswordWithEmail(String email,  final ServerRequestListener listener) {
        //  {"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}
        String url = getURL(Services.USER_PROFILE_RESET_PASSWORD)+email;//TODO need to check this looks wierd.....
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



    public void putSolutionWithId(String solutionID,boolean isfav , final ServerRequestListener listener) {
        String url = getURL(Services.USER_SOLUTION)+solutionID;
        JSONObject jsonObjectWrapper = new JSONObject();
        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isfavorite", isfav);



        }catch (Exception e ){
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
                return BASE_URL + "Hotel";
            case USER_HOTEL_ROOM_ALTERNATIVE:
                return BASE_URL + "HotelRoom";
            case USER_PUT_HOTEL:
                return BASE_URL + "Hotel";
            case USER_GET_BOOKING_OPTIONS:
                return BASE_URL + "Statics/BookingOptions";
            case USER_FLIGHT_SOLUTIONS:
                return BASE_URL + "Flight";
            case USER_GET_TRAVELER_INFO:
                return BASE_URL + "Traveler/Get/";
            case USER_GET_USER_PROFILE_ACCOUNTS:
                return BASE_URL + "UserProfile/Accounts";
            case USER_POST_USER_PROFILE_EMAIL:
                return BASE_URL + "UserProfile/ResetPassword?email=";
            case USER_TRAVEL_PROFILES:
                return BASE_URL + "TravelPreference/Profiles/";
            case USER_PROFILE_RESET_PASSWORD:
                return BASE_URL + "UserProfile/ResetPassword?email=";
            case USER_SOLUTION:
                return BASE_URL + "Solution/";





        }
        return url;
    }


}
