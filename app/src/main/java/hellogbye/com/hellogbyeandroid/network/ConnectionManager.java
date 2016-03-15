package hellogbye.com.hellogbyeandroid.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;


public class ConnectionManager {

    public interface ServerRequestListener {
        void onSuccess(Object data);

        void onError(Object data);
    }


    public static String BASE_URL = "http://cnc.hellogbye.com/cnc/rest/";


    //public static String BASE_URL =  "http://cnc.hellogbye.com/cnc/rest/itinerary?count=15&skip=30";

    //  public static String BASE_URL = "http://ec2-54-172-8-232.compute-1.amazonaws.com/web.api/rest/";


    private static ConnectionManager _instance;
    private Context mContext;

    private String SOLUTION = "?solution=";
    private String PREFERENCES = "Preferences";


    public enum Services {
        USER_POST_LOGIN, USER_GET_PROFILE, USER_POST_CHANGE_PASSWORD,
        USER_GET_TRAVEL_PROFILES, USER_GET_TRAVEL_PROFILES_DEFAULT, USER_POST_CHECKOUT,
        USER_GET_SEARCH_QUERY, USER_GET_HOTEL_ALTERNATIVE,
        USER_HOTEL_ROOM_ALTERNATIVE, USER_PUT_HOTEL, USER_GET_BOOKING_OPTIONS,
        USER_FLIGHT_SOLUTIONS, USER_GET_TRAVELER_INFO, USER_GET_USER_PROFILE_ACCOUNTS,
        USER_POST_USER_PROFILE_EMAIL, USER_TRAVEL_PROFILES, USER_PROFILE_RESET_PASSWORD,
        USER_SOLUTION, ITINERARY, USER_GET_TRAVEL_PREFERENCES, ITINERARY_CNC,
        USER_POST_TRAVEL_PREFERENCES, COMPANIONS, CARD_TOKEN, ITINERARY_MY_TRIP,
        ITINERARY_HIGHLIGHT, USER_AVATAR, RELATIONSHIP_TYPES, ACCOUNTS_PREFERENCES

    }

    private ConnectionManager() {

    }

    public static ConnectionManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new ConnectionManager();
        }
        _instance.mContext = context;
        HGBStringRequest.setContext(context);
        HGBJsonRequest.setContext(context);
        HGBStringXMLRequest.setContext(context);
        return _instance;
    }


    ////////////////////////////////
    // POST
    ///////////////////////////////


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
        String url = getURL(Services.ITINERARY_CNC);
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


    public void login(String email, String password, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_LOGIN);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("password", password);
            jsonObject.put("username", email);

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


    public void postChangePasswordWithOldPassword(String prevpassword, String password, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_CHANGE_PASSWORD);

        JSONObject jsonObject = new JSONObject();
        try {
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


    public void checkoutSolutionId(String profileId, HashSet<String> items, final ServerRequestListener listener) {
        String url = getURL(Services.USER_POST_CHECKOUT);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("itineraryid", profileId);

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

    public void ItineraryCNCSearchPost(ArrayList<AirportSendValuesVO> airportSendValuesVOs, final ServerRequestListener listener) {
        String url = getURL(Services.ITINERARY);
        JSONObject jsonObjectMain = new JSONObject();


        JSONArray jsonArray = new JSONArray();

        for (AirportSendValuesVO airportSendValuesVO : airportSendValuesVOs) {

            try {

                //Main for all query request
                jsonObjectMain.put("query", airportSendValuesVO.getQuery());
                jsonObjectMain.put("travelpreferenceprofileid", airportSendValuesVO.getTravelpreferenceprofileid());
                jsonObjectMain.put("latitude", airportSendValuesVO.getLatitude());
                jsonObjectMain.put("longitude", airportSendValuesVO.getLongitude());


                jsonObjectMain.put("token", jsonArray);

                JSONObject jsonObjectSecond = new JSONObject();
                //it's array of cities
                jsonObjectSecond.put("type", airportSendValuesVO.getType());

                JSONObject jsonObjectThird = new JSONObject();
                JSONArray jsonArrayPosition = new JSONArray();
                jsonArrayPosition.put(jsonObjectThird);
                jsonObjectSecond.put("positions", jsonArrayPosition);

                jsonArray.put(jsonObjectSecond);

                jsonObjectThird.put("id", airportSendValuesVO.getId());
                jsonObjectThird.put("start", airportSendValuesVO.getStart());
                jsonObjectThird.put("end", airportSendValuesVO.getEnd());
                jsonObjectThird.put("value", airportSendValuesVO.getValue());


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


    ////////////////////////////////
    // GETS
    ///////////////////////////////


    public void ItineraryCNCSearchGet(String query, final ServerRequestListener listener) {

        String url = getURL(Services.ITINERARY_HIGHLIGHT);
        JSONObject jsonObject = new JSONObject();
        query = query.replaceAll(" ", "%20");

        url = url + query;

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


    public void getMyTrips(final ServerRequestListener listener) {

        String url = getURL(Services.ITINERARY_MY_TRIP) + "?count=15&skip=0";
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

        String url = getURL(Services.ITINERARY_MY_TRIP) + "?count=15&skip=0&upcomingtrips=true&paymentStatus=TPD,PPD";
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

    public void getMyTripsFavorite(final ServerRequestListener listener) {

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


    public void getPreferenceProfiles(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES);

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


    public void getPreferencesForProfileId(String profileid, final ServerRequestListener listener) {
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

    }


    public void getPreference(final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES);
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
        url = url + SOLUTION + solutioid + "&paxid=" + paxid + "&checkin=" + checkin + "&checkout=" + checkout;
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

        String url = getURL(Services.USER_HOTEL_ROOM_ALTERNATIVE);
        url = url + solutioid + "&paxid=" + paxid + "&checkin=" + checkin + "&checkout=" + checkout + "hotelcode=" + hotelcode;

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

    //{"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}

    public void getUserSettingsAttributes(String attributesId, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_TRAVEL_PROFILES);
        JSONObject jsonObject = new JSONObject();


        url = url + "/" + attributesId + "/" + PREFERENCES;

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

    public void getAlternateFlightsForFlight(String solutionid, String paxid, String flightid, final ServerRequestListener listener) {


        String url = getURL(Services.USER_FLIGHT_SOLUTIONS);
        url = url + SOLUTION + solutionid + "&paxid=" + paxid + "&flight=" + flightid;
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
        });
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
        });
    }

    public void getTravellersInforWithSolutionId(String solutionid, final ServerRequestListener listener) {

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
        });

    }


    public void getCompanions(final ServerRequestListener listener) {

        String url = getURL(Services.COMPANIONS);

        JSONObject jsonObject = new JSONObject();


        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
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

    ////////////////////////////////
    // DELETE
    ///////////////////////////////


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
        });
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

    public void putCompanion(String id, UserData user, final ServerRequestListener listener) {
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
            json2.put("dob", HGBUtility.parseDateToServertime(user.getDob()));
            json2.put("firstname", user.getFirstname());
            json2.put("title", user.getTitle());
            json2.put("address", user.getAddress());
            json2.put("city", user.getCity());
            json2.put("country", user.getCountry());
            json2.put("gender", user.getGender());
            json2.put("userprofileid", user.getUserprofileid());

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


    //gender
    //email
    public void putUserSettings(UserData user, final ServerRequestListener listener) {
        String url = getURL(Services.USER_GET_PROFILE);
        JSONObject json1 = new JSONObject();
        try {

            json1.put("state", user.getState());
            json1.put("usercountry", user.getCountry());
            json1.put("phone", user.getPhone());
            json1.put("postalcode", user.getPostalcode());
            json1.put("lastname", user.getLastname());
            json1.put("dob", user.getDob());
            json1.put("firstname", user.getFirstname());
            json1.put("title", user.getTitle());
            json1.put("address", user.getAddress());
            json1.put("city", user.getCity());
            json1.put("country", user.getCountry());
            json1.put("avatar", user.getAvatar());
            json1.put("userprofileid", user.getUserprofileid());


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
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("solution", solutionid);
            jsonObject.put("paxid", paxid);
            jsonObject.put("checkin", checkin);
            jsonObject.put("checkout", checkout);
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
        }, false);
    }


    public void putAlternateHotelRoom(String solutioid, String paxid, String checkin, String checkout, String hotelroomid, final ServerRequestListener listener) {
        //  {"parameters":{"solution":"c86d9879-eb15-4164-8b75-6bbac0787b75","paxid":"9d2c85f5-d295-4064-a8c6-a4d0015b52e4","checkin":"2015-09-03","checkout":"2015-09-04"},"hotel":"c329c20a-4836-4bec-9580-48f7814e9fbd"}
        String url = getURL(Services.USER_HOTEL_ROOM_ALTERNATIVE);
        JSONObject jsonObjectWrapper = new JSONObject();
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("solution", solutioid);
            jsonObject.put("paxid", paxid);
            jsonObject.put("checkin", checkin);
            jsonObject.put("checkout", checkout);
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


    private String getURL(Services type) {
        String url = "";
        switch (type) {
            case USER_POST_LOGIN:
                return BASE_URL + "Session";
            case USER_GET_PROFILE:
                return BASE_URL + "UserProfile";
            case USER_POST_CHANGE_PASSWORD:
                return BASE_URL + "UserProfile/Password";
            case USER_GET_TRAVEL_PROFILES_DEFAULT:
                return BASE_URL + "TravelPreference/Profiles/Defaults";
            case USER_GET_TRAVEL_PROFILES:
                return BASE_URL + "TravelPreference/Profiles";
            case USER_GET_TRAVEL_PREFERENCES:
                return BASE_URL + "TravelPreference";
            case USER_POST_CHECKOUT:
                return BASE_URL + "CheckOut";
            case USER_GET_SEARCH_QUERY:
                return BASE_URL + "Solution/Primarysearch?query=";
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
            case ITINERARY:
                return BASE_URL + "Itinerary/";
            case ITINERARY_MY_TRIP:
                return BASE_URL + "Itinerary";
            case ITINERARY_CNC:
                return BASE_URL + "Itinerary/"; //return BASE_URL + "Itinerary/CNC";
            case ITINERARY_HIGHLIGHT:
                return BASE_URL + "Highlight?input="; //return BASE_URL + "Itinerary/CNC";
            case COMPANIONS:
                return BASE_URL + "Companions";
            case CARD_TOKEN:
                return BASE_URL + "Card/Token";
            case USER_AVATAR:
                return BASE_URL + "UserProfile/Avatar";
            case RELATIONSHIP_TYPES:
                return BASE_URL + "Statics/RelationshipTypes";
            case ACCOUNTS_PREFERENCES:
                return BASE_URL + "UserProfile/Accounts/TravelPreference";

        }
        return url;
    }
    //  http://cnc.hellogbye.com/cnc/rest/UserProfile/Accounts/TravelPreference

}
