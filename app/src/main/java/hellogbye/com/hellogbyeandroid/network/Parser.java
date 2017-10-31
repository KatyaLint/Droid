package hellogbye.com.hellogbyeandroid.network;


import android.app.Activity;
import android.content.Intent;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.application.HGBApplication;
import hellogbye.com.hellogbyeandroid.fragments.checkout.AirlinePointsProgramVO;
import hellogbye.com.hellogbyeandroid.fragments.checkout.BookingPayVO;
import hellogbye.com.hellogbyeandroid.models.CreditCardSessionItem;

import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.UpdateAvailabilityVO;
import hellogbye.com.hellogbyeandroid.signalr.AirportServerResultCNCVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionsSearchItemsVO;
import hellogbye.com.hellogbyeandroid.models.vo.profiles.DefaultsProfilesVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.TravelPreference;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AccountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportServerResultVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionStaticRelationshipTypesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.signalr.SignalRServerResponseForHighlightVO;

public class Parser {




    public static String parseErrorMessage(VolleyError error) {
        String message = onErrorResponse(error);
        return message;
//                try {
//                    String body = new String(SignalRRrror.networkResponse.data, "UTF-8");
//
//                    if (SignalRRrror instanceof TimeoutError || SignalRRrror instanceof NoConnectionError) {
//                        return "Network timed out SignalRRrror " + SignalRRrror.networkResponse.statusCode;
//                    } else if (SignalRRrror instanceof AuthFailureError) {
//                        return "AuthFailureError SignalRRrror " + SignalRRrror.networkResponse.statusCode;
//                    } else if (SignalRRrror instanceof ServerError) {
//                        return "ServerError SignalRRrror " + SignalRRrror.networkResponse.statusCode;
//                    } else if (SignalRRrror instanceof NetworkError) {
//                        return "NetworkError SignalRRrror " + SignalRRrror.networkResponse.statusCode;
//                    } else if (SignalRRrror instanceof ParseError) {
//                        return "ParseError SignalRRrror " + SignalRRrror.networkResponse.statusCode;
//                    }
//                    return "Error " + SignalRRrror.networkResponse.statusCode;
//                } catch (Exception exception) {
//                    return "NetworkError SignalRRrror ";
//                }
    }


    public static  String onErrorResponse(VolleyError error) {
        String json = null;

        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            switch(response.statusCode){
                case 400:
                    json = new String(response.data);
                    json = trimMessageJson(json, "messageid");
                    break;
                case 405:
                    json = new String(response.data);
                    break;
                case 401:

                    //TODO log out
                    Intent intent = new Intent();
                    intent.setAction("logout");
                    HGBApplication.getInstance().sendBroadcast(intent);
                    json = "Session AuthFailureError";
                    break;
                default:
                    json = new String(response.data);
                    json = trimMessage(json, "messageid");
                    break;
            }
            //Additional cases
        }else{
            json =  error.getMessage();
        }
        return json;
    }

    public static String trimMessageJson(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
           // JSONObject obj2 = obj.getJSONObject("errormessages");
            JSONArray obj3 = obj.getJSONArray("errormessages");
            JSONObject objtrimmedString = obj3.getJSONObject(0);
            Object str = objtrimmedString.get("messageid");
            trimmedString = (String) str;
            // trimmedString = obj.getString("SignalRRrror"); //(String) str;

        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

/*    public static String trimMessageJson(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
             JSONObject obj2 = obj.getJSONObject("ErrorMessages");
            JSONArray obj3 = obj2.getJSONArray("MessageDtos");
            JSONObject objtrimmedString = obj3.getJSONObject(0);
            Object str = objtrimmedString.get("MessageID");
            trimmedString = (String) str;
           // trimmedString = obj.getString("SignalRRrror"); //(String) str;

        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }*/

    public static String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
           // JSONObject obj2 = obj.getJSONObject("SignalRRrror");
           /* JSONArray obj3 = obj2.getJSONArray("messagedtos");
            JSONObject objtrimmedString = obj3.getJSONObject(0);
            Object str = objtrimmedString.get("messageid");*/
            trimmedString = obj.getString("SignalRRrror"); //(String) str;

        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }


    public static Object getSettingsAcount(String response){
        AccountDefaultSettingsVO acountDefaultSettings = null;
        try {
            Type listType = new TypeToken<AccountDefaultSettingsVO>() {
            }.getType();

            Gson gson = new Gson();

            acountDefaultSettings = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return acountDefaultSettings;
    }

    public static Object getCityList(String response){
        ArrayList<String> list = null;
        try {
            String replace = response.replace("[","");
            String replace1 = replace.replace("]","");
            String replace2 = replace1.replace("\"", "");
            list = new ArrayList<String>(Arrays.asList(replace2.split(",")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    public static Object getSettingsDefault(String response){
        List<AccountDefaultSettingsVO> acountDefaultSettings = null;
        try {
            Type listType = new TypeToken<List<AccountDefaultSettingsVO>>() {
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


    public static Object parseCreditCardList(String response){
        ArrayList<CreditCardItem> creditCardList = null;
        try {
            Type listType = new TypeToken<ArrayList<CreditCardItem>>() {
            }.getType();

            Gson gson = new Gson();

            creditCardList = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return creditCardList;
    }


    public static Object parseDefaultProfiles(String response){
        ArrayList<DefaultsProfilesVO> creditCardList = null;
        try {
            Type listType = new TypeToken<ArrayList<DefaultsProfilesVO>>() {
            }.getType();

            Gson gson = new Gson();

            creditCardList = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return creditCardList;
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
        ArrayList<UserProfileVO>  mTravelList = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<UserProfileVO>>() {
            }.getType();
            mTravelList = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mTravelList;
    }

    public static Object getUpdateAvailabilityVO(String response){
        UpdateAvailabilityVO updateAvailabilityVO = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<UpdateAvailabilityVO>() {
            }.getType();
            updateAvailabilityVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return updateAvailabilityVO;
    }
    public static Object getUpdateAvailabilityVOMap(String response){
        UpdateAvailabilityVO updateAvailabilityVO = null;

        Map<String, Object> myMap = null;

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
             myMap = gson.fromJson(response, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return myMap;
    }






    public static Object getTravel(String response){
        UserProfileVO  mTravelList = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<UserProfileVO>() {
            }.getType();
            mTravelList = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mTravelList;
    }

    public static Object getAccounts(String response){
        ArrayList<AccountsVO>  mResponseData = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<AccountsVO>>() {
            }.getType();
            mResponseData = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mResponseData;
    }

//    public static Object authenticationData(String response){
//        UserLoginCredentials user = null;
//        try {
//            Gson gson = new Gson();
//            Type type = new TypeToken<UserLoginCredentials>() {
//            }.getType();
//            user = gson.fromJson((String) response, type);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return user;
//    }

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
    }



    public static Object parseCompanionSearchData(String response) {

        CompanionsSearchItemsVO  campanionVO = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<CompanionsSearchItemsVO>() {
            }.getType();
            campanionVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campanionVO;

    }


    public static Object parseCompanionsData(String response) {

        List<CompanionVO>  campanionVO = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<CompanionVO>>() {
            }.getType();
            campanionVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campanionVO;
    }

    public static Object parseCompanionData(String response) {

        CompanionVO  campanionVO = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<CompanionVO>() {
            }.getType();
            campanionVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campanionVO;

    }

    public static Object parsePayCheckoutResponse(String response) {

        List<BookingPayVO>  campanionVO = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<BookingPayVO>>() {
            }.getType();
            campanionVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return campanionVO;

    }


    public static Object parseAirplaneData(String response) {

        UserTravelMainVO airplaneDataVO = null;
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<UserTravelMainVO>() {
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
            List<NodesVO> posts = gson.fromJson(response, listType);
            cell.setmNodes((ArrayList) posts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cell;
    }

    public static Object parseHotelRoomsData(String response) {
        NodesVO node = new NodesVO();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<NodesVO>() {
            }.getType();
            node = gson.fromJson(response, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return node;
    }

    public static Object parseUser(String response) {
        UserProfileVO userdata = new UserProfileVO();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<UserProfileVO>() {
            }.getType();
            userdata = gson.fromJson(response, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userdata;
    }

    public static Object parseUsers(String response) {
        List<UserProfileVO> userdata = new ArrayList<>();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<List<UserProfileVO>>() {
            }.getType();
            userdata = gson.fromJson(response, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userdata;
    }


    public static Object parseBookingOptions(String response) {
        BookingRequestVO bookingrequest = new BookingRequestVO();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<BookingRequestVO>() {
            }.getType();
            bookingrequest = gson.fromJson(response, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingrequest;
    }


    public static Object parseBookingProvinceOptions(String response) {
        List<ProvincesItem> bookingrequest = new  ArrayList<ProvincesItem>();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken< List<ProvincesItem>>() {
            }.getType();
            bookingrequest = gson.fromJson(response, type);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookingrequest;
    }



    public static Object parseMyTrips(String response) {
        ArrayList<MyTripItem>  mMyTripslList = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<MyTripItem>>() {
            }.getType();
            mMyTripslList = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mMyTripslList;
    }

    public static Object parseStaticRelationTypes(String response) {
        ArrayList<CompanionStaticRelationshipTypesVO>  mCompanionRelationshipTypes= null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<CompanionStaticRelationshipTypesVO>>() {
            }.getType();
            mCompanionRelationshipTypes = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCompanionRelationshipTypes;
    }


    public static Object parseAirlinePointsProgram(String response) {
        AirlinePointsProgramVO  airlinePointsProgramVO= null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<AirlinePointsProgramVO>() {
            }.getType();
            airlinePointsProgramVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return airlinePointsProgramVO;
    }


    public static Object parseAirlinePointsPrograms(String response) {
        ArrayList<AirlinePointsProgramVO>  airlinePointsProgramVO= null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<AirlinePointsProgramVO>>() {
            }.getType();
            airlinePointsProgramVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return airlinePointsProgramVO;
    }

    public static Object parseSignalRHighlightResponse(String response) {
        SignalRServerResponseForHighlightVO  mCompanionRelationshipTypes= null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<SignalRServerResponseForHighlightVO>() {
            }.getType();
            mCompanionRelationshipTypes = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mCompanionRelationshipTypes;
    }

    public static Object parseAirportResult(String response) {
        AirportServerResultCNCVO  airportServerResultVO = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<AirportServerResultCNCVO>() {
            }.getType();
            airportServerResultVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return airportServerResultVO;
    }


    public static Object parseAirportCNCResult(String response) {
        AirportServerResultCNCVO  airportServerResultVO = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<AirportServerResultCNCVO>() {
            }.getType();
            airportServerResultVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return airportServerResultVO;
    }


    public static Object parseCCSession(String response) {
        CreditCardSessionItem ccItem = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<CreditCardSessionItem>() {
            }.getType();
            ccItem = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ccItem;

    }
}
