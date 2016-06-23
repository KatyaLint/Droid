package hellogbye.com.hellogbyeandroid.utilities;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import hellogbye.com.hellogbyeandroid.network.HGBJsonRequest;
import hellogbye.com.hellogbyeandroid.network.Parser;

/**
 * Created by arisprung on 6/23/16.
 */
public class HGBTranslate {

    public static void translateQueary(String message, final HGBTranslateInterface tranlsateinterface){
        String strResponce = "";
        final String strQuery = message.replaceAll(" ", "%20");


        HGBJsonRequest req1 = new HGBJsonRequest(Request.Method.GET, "https://www.googleapis.com/language/translate/v2/detect?key=AIzaSyANMrd66GHfMu43HBJMAxvxSyB4W5rTk-E&q=" + strQuery,
                new JSONObject(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject json1 = jsonObject.getJSONObject("data");
                    JSONArray array = json1.getJSONArray("detections");
                    JSONArray array1 = array.getJSONArray(0);
                    JSONObject langJson = array1.getJSONObject(0);
                    String strLang = langJson.getString("language");
                    HGBJsonRequest req2 = new HGBJsonRequest(Request.Method.GET, "https://www.googleapis.com/language/translate/v2?key=AIzaSyANMrd66GHfMu43HBJMAxvxSyB4W5rTk-E&q=" + strQuery + "&source=" + strLang + "&target=en",
                            new JSONObject(), new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject json1 = jsonObject.getJSONObject("data");
                                JSONArray array = json1.getJSONArray("translations");
                                JSONObject array1 = array.getJSONObject(0);
                                String strText = array1.getString("translatedText");
                                if(tranlsateinterface != null){
                                    tranlsateinterface.onSuccess(strText);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }



                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            tranlsateinterface.onError(error.toString());
                        }

                    }, false);

                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }, false);


    }

//
//    HGBTranslate.translateQueary(query, new HGBTranslateInterface() {
//        @Override
//        public void onSuccess(String message) {
//            String url = getURL(Services.ITINERARY_HIGHLIGHT);
//            JSONObject jsonObject3 = new JSONObject();
//            message = message.replaceAll(" ", "%20");
//
//            url = url + message;
//
//
//            HGBJsonRequest req3 = new HGBJsonRequest(Request.Method.GET, url,
//                    jsonObject3, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    listener.onSuccess(Parser.parseAirportResult(response));
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    listener.onError(Parser.parseErrorMessage(error));
//                }
//
//            }, false);
//        }
//
//        @Override
//        public void onError(String error) {
//            listener.onError(error);
//        }
//    });


}
