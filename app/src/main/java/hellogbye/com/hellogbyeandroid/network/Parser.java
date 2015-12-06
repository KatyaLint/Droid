package hellogbye.com.hellogbyeandroid.network;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.models.BookingRequest;
import hellogbye.com.hellogbyeandroid.models.TravelPreference;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AcountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;

public class Parser {


    public static String parseErrorMessage(VolleyError error) {

                try {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        return "Network timed out error " + error.networkResponse.statusCode;
                    } else if (error instanceof AuthFailureError) {
                        return "AuthFailureError error " + error.networkResponse.statusCode;
                    } else if (error instanceof ServerError) {
                        return "ServerError error " + error.networkResponse.statusCode;
                    } else if (error instanceof NetworkError) {
                        return "NetworkError error " + error.networkResponse.statusCode;
                    } else if (error instanceof ParseError) {
                        return "ParseError error " + error.networkResponse.statusCode;
                    }
                    return "Error " + error.networkResponse.statusCode;
                } catch (Exception exception) {
                    return "NetworkError error ";
                }
    }






    public static Object getSettingsDefault(String response){
        List<AcountDefaultSettingsVO> acountDefaultSettings = null;
        try {
            Type listType = new TypeToken<List<AcountDefaultSettingsVO>>() {
            }.getType();

            Gson gson = new Gson();

            acountDefaultSettings = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acountDefaultSettings;
    }


    public static Object getSettingsSpecificAttributeData(String response){
        List<SettingsAttributesVO> acountSettingsAttributes = null;
        try {
            Type listType = new TypeToken<List<SettingsAttributesVO>>() {
            }.getType();

            Gson gson = new Gson();

            acountSettingsAttributes = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acountSettingsAttributes;
    }




    public static Object getSettingsAttributeData(String response){
        List<SettingsAttributeParamVO> acountSettingsAttributes = null;
        try {
            Type listType = new TypeToken<List<SettingsAttributeParamVO>>() {
            }.getType();

            Gson gson = new Gson();

            acountSettingsAttributes = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acountSettingsAttributes;
    }


    public static Object parseAlternativeFlight(String response) {
        List<NodesVO> alternativeFlightsVOs = null;
        try {
            Type listType = new TypeToken<List<NodesVO>>() {
            }.getType();

            Gson gson = new Gson();

          alternativeFlightsVOs = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return alternativeFlightsVOs;

    }


    public static Object getTravelProfileData(String response){
        ArrayList<TravelPreference>  mTravelPrefList = null;
        try {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<TravelPreference>>() {
        }.getType();
         mTravelPrefList = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mTravelPrefList;
    }

    public static Object getTravels(String response){
        ArrayList<UserData>  mTravelList = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<UserData>>() {
            }.getType();
            mTravelList = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mTravelList;
    }

    public static Object loginData(String response){
        UserLoginCredentials user = null;
        try {
        Gson gson = new Gson();
        Type type = new TypeToken<UserLoginCredentials>() {
        }.getType();
        user = gson.fromJson((String) response, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
        //hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
    }



    public static Object parseAirplaneData(String response) {
        UserTravelVO airplaneDataVO = null;
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<UserTravelVO>() {
            }.getType();
            airplaneDataVO = gson.fromJson(response, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return airplaneDataVO;
    }

    public static Object parseHotelData(String response) {
        CellsVO cell = new CellsVO();
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<NodesVO>>() {
            }.getType();
            List<NodesVO> posts = (List<NodesVO>) gson.fromJson(response, listType);
            cell.setmNodes((ArrayList) posts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cell;
    }

    public static Object parseUser(String response) {
        UserData userdata = new UserData();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<UserData>() {
            }.getType();
            userdata = gson.fromJson(response, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userdata;
    }

    public static Object parseBookingOptions(String response) {
        BookingRequest bookingrequest = new BookingRequest();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<BookingRequest>() {
            }.getType();
            bookingrequest = gson.fromJson(response, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingrequest;
    }






}
