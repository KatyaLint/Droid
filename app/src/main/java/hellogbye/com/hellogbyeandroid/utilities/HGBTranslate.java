package hellogbye.com.hellogbyeandroid.utilities;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.network.HGBJsonRequest;
import hellogbye.com.hellogbyeandroid.network.Parser;

/**
 * Created by arisprung on 6/23/16.
 */
public class HGBTranslate {

    public static final String MAIN_LANGUAGE = "en";

    public static void translateQueary(Context context,final String message, final HGBTranslateInterface tranlsateinterface){
        String strResponce = "";

        final String strQuery = message.replaceAll(" ", "%20");
        final String api= context.getResources().getString(R.string.google_maps_key);//AIzaSyANMrd66GHfMu43HBJMAxvxSyB4W5rTk-E&q=


        HGBJsonRequest req1 = new HGBJsonRequest(Request.Method.GET, "https://www.googleapis.com/language/translate/v2/detect?key="+api+"&q="+ strQuery,
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

                    if(MAIN_LANGUAGE.equalsIgnoreCase(strLang)){
                        if(tranlsateinterface != null){
                             String strQueryResult = strQuery.replaceAll("%20"," " );
                            tranlsateinterface.onSuccess(strQueryResult);
                        }
                    }else{
                        HGBJsonRequest req2 = new HGBJsonRequest(Request.Method.GET, "https://www.googleapis.com/language/translate/v2?key="+api+"&q=" + strQuery + "&source=" + strLang + "&target="+MAIN_LANGUAGE,
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
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                tranlsateinterface.onError(error.getMessage());
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
