package hellogbye.com.hellogbyeandroid.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

/**
 * Created by arisprung on 8/31/15.
 */
public class CustomRequest extends Request<JSONObject> {

    private Listener<JSONObject> listener;
   // private JSONObject params;
    private Map<String, String> params;


    protected static final String PROTOCOL_CHARSET = "utf-8";
    public CustomRequest(String url, Map<String, String> params,
                         Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    public CustomRequest(int method, String url,Map<String, String> params,
                         Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = reponseListener;
        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jsonObjectWrapper = new JSONObject();
//        try{
//            HashMap<String, String> mapinner = new HashMap<String, String>();
//            mapinner.put("solution","c86d9879-eb15-4164-8b75-6bbac0787b75");
//            mapinner.put("paxid","9d2c85f5-d295-4064-a8c6-a4d0015b52e4");
//            mapinner.put("checkin","2015-09-03");
//            mapinner.put("checkout","2015-09-05");
//
//
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("solution","c86d9879-eb15-4164-8b75-6bbac0787b75");
//            jsonObject.put("paxid","9d2c85f5-d295-4064-a8c6-a4d0015b52e4");
//            jsonObject.put("checkin","2015-09-03");
//            jsonObject.put("checkout","2015-09-05");
//            jsonObjectWrapper.put("parameters", jsonObject.toString());
//            jsonObjectWrapper.put("hotel","c329c20a-4836-4bec-9580-48f7814e9fbd");
//
//            this.params.put("parameters", jsonObject.toString());
//            this.params.put("hotel", "c329c20a-4836-4bec-9580-48f7814e9fbd");
//
//        }catch (Exception e ){
//            e.printStackTrace();
//        }
        this.params = params;
    }

    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    };


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
       // headers.put("Content-Type", "application/json; charset=utf-8");

       // headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Session " + "6e8b5e9ff673405f9b8638e185dc09c2065d9791d75843a5bd91ae7d1b23f400e35ec0041540403ba83a7f7a8b6e8366");

//        HGBPreferencesManager sharedPreferences = HGBPreferencesManager.getInstance(mContext);
//        String token = sharedPreferences.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
//        if(!token.equals("")){
//            headers.put("Authorization", "Session " + );
//        }
        return headers;
    }



    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

//    @Override
//    public byte[] getBody() {
//        try {
//            return params == null ? null : params.toString().getBytes(PROTOCOL_CHARSET);
//        } catch (UnsupportedEncodingException uee) {
//            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
//                    params.toString(), PROTOCOL_CHARSET);
//            return null;
//        }
//    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        // TODO Auto-generated method stub
        listener.onResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        Log.e("", "ERROR: " + (error != null && error.networkResponse != null ? error.getClass().getSimpleName() + ": " + error.networkResponse.statusCode + " " + new String(error.networkResponse.data) : "null"));


    }

}
