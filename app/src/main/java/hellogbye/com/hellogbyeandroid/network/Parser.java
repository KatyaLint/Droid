package hellogbye.com.hellogbyeandroid.network;


import android.util.Log;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;

public class Parser {


    public static String parseErrorMessage(VolleyError error) {
        String err = "server error";
        JSONObject errJson = null;
        try {
            err =  errJson.getString("message");
             errJson = new JSONObject(new String(error.networkResponse.data));
            if(errJson == null){
                return  errJson.getString("message");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  err;
        }
        return errJson.toString();
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

}
