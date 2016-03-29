package hellogbye.com.hellogbyeandroid.network;


import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.models.MyTripItem;
import hellogbye.com.hellogbyeandroid.models.TravelPreference;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
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

public class Parser {




    public static String parseErrorMessage(VolleyError error) {
        String message = onErrorResponse(error);
        return message;
//                try {
//                    String body = new String(error.networkResponse.data, "UTF-8");
//
//                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                        return "Network timed out error " + error.networkResponse.statusCode;
//                    } else if (error instanceof AuthFailureError) {
//                        return "AuthFailureError error " + error.networkResponse.statusCode;
//                    } else if (error instanceof ServerError) {
//                        return "ServerError error " + error.networkResponse.statusCode;
//                    } else if (error instanceof NetworkError) {
//                        return "NetworkError error " + error.networkResponse.statusCode;
//                    } else if (error instanceof ParseError) {
//                        return "ParseError error " + error.networkResponse.statusCode;
//                    }
//                    return "Error " + error.networkResponse.statusCode;
//                } catch (Exception exception) {
//                    return "NetworkError error ";
//                }
    }


    public static  String onErrorResponse(VolleyError error) {
        String json = null;

        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            switch(response.statusCode){
                case 400:
                    json = new String(response.data);
                    json = trimMessage(json, "messageid");
                    break;
                case 401:
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


    public static String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            JSONObject obj2 = obj.getJSONObject("errormessages");
            JSONArray obj3 = obj2.getJSONArray("messagedtos");
            JSONObject objtrimmedString = obj3.getJSONObject(0);
            Object str = objtrimmedString.get("messageid");
            trimmedString = (String) str;

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
        ArrayList<UserDataVO>  mTravelList = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<UserDataVO>>() {
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

    public static Object parseCompanionData(String response) {

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

    public static Object parseUser(String response) {
        UserDataVO userdata = new UserDataVO();
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<UserDataVO>() {
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

    public static Object parseAirportResult(String response) {
        AirportServerResultVO  airportServerResultVO = null;
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<AirportServerResultVO>() {
            }.getType();
            airportServerResultVO = gson.fromJson((String) response, listType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return airportServerResultVO;
    }

}
