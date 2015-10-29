package hellogbye.com.hellogbyeandroid.network;


import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.CellsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelVO;

public class Parser {


    public static String parseErrorMessage(VolleyError error) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            return "Network timed out error "+error.networkResponse.statusCode;
        } else if (error instanceof AuthFailureError) {
            return "AuthFailureError error "+error.networkResponse.statusCode;
        } else if (error instanceof ServerError) {
            return "ServerError error "+error.networkResponse.statusCode;
        } else if (error instanceof NetworkError) {
            return "NetworkError error "+error.networkResponse.statusCode;
        } else if (error instanceof ParseError) {
            return "ParseError error "+error.networkResponse.statusCode;
        }
        return"Error "+error.networkResponse.statusCode;
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
            Type listType = new TypeToken<List<NodesVO>>(){}.getType();
            List<NodesVO> posts = (List<NodesVO>) gson.fromJson(response, listType);
            cell.setmNodes((ArrayList)posts);
            Log.d("","");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cell;
    }

}
