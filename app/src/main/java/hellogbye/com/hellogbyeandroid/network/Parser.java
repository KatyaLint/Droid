package hellogbye.com.hellogbyeandroid.network;


import com.android.volley.VolleyError;

import org.json.JSONObject;

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


//    public static Object parseEstimation(String response) {
//        Estimation est = new Estimation();
//
//        try {
//            JSONObject json = new JSONObject(response);
//            est.setEstimateTime(json.getInt("estimateTime"));
//            est.setLatitude(json.getDouble("lat"));
//            est.setLongnitude(json.getDouble("lon"));
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return est;
//
//    }

}
