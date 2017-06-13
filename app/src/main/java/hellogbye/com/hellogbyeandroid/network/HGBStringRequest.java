package hellogbye.com.hellogbyeandroid.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.application.HGBApplication;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;


public class HGBStringRequest extends Request<String> {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);
   // private String authorizationString;
    private RequestQueue queue;
    private static final String TAG = "NETWORK";
    private String url;
    private String service;
    private static Context mContext;
    private Listener<String> listener;
    private ErrorListener errorListener;
  // HashMap<String,String> params = new HashMap<String, String>();
    private JSONObject jsonObject = new JSONObject();
    private Map<String, String> params;

    private boolean showLoader;
    ProgressDialog progressDialog;
    String loading;


    public HGBStringRequest(int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {

        super(method, url, errorListener);
        this.listener = listener;
        this.params = params;
//        params.put("username","omer.vinik@amginetech.com");
//        params.put("password","password");
        this.errorListener = errorListener;
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        this.queue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkHttpStack(client));
      //  this.queue = Volley.newRequestQueue(mContext.getApplicationContext());
        this.url = url;
        setRetryPolicy((new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        showLoader = true;
        progressDialog = new ProgressDialog(mContext);
        loading = "Loading...";
        setService(url);
        send();
    }

    public HGBStringRequest(int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener, boolean showLoader) {
        super(method, url, errorListener);
        this.listener = listener;
       //    this.params = params;
        this.errorListener = errorListener;
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        this.queue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkHttpStack(client));
       // this.queue = Volley.newRequestQueue(mContext.getApplicationContext());
        this.url = url;
        setRetryPolicy((new DefaultRetryPolicy(
                HGBApplication.TIMEOUT_TIME,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        setService(url);
        progressDialog = new ProgressDialog(mContext);
        loading = "loading...";
        this.showLoader = showLoader;
        send();
    }


    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return params;
    };

    private void setService(String url) {
        URI uri;
        try {
            uri = new URI(url);

            String path = uri.getPath();
            service = path.substring(path.lastIndexOf('/') + 1);

        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void deliverResponse(String response) {
        Log.i(TAG, service + ":\t" + response.toString());
        removeLoader();
        if (listener != null) {

            listener.onResponse(response.toString());
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        Log.e(TAG, "ERROR: " + service + " " + (error != null && error.networkResponse != null ? error.getClass().getSimpleName() + ": " + error.networkResponse.statusCode + " " + new String(error.networkResponse.data) : "null"));

        removeLoader();
        if (errorListener != null) {
            errorListener.onErrorResponse(error);
        }
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
       // headers.put("Content-Type", "application/json");
        HGBPreferencesManager sharedPreferences = HGBPreferencesManager.getInstance(mContext);
        String token = sharedPreferences.getStringSharedPreferences(HGBConstants.TOKEN, "");
        if(!token.equals("")){
            headers.put("Authorization", "Session " + token);
        }
        return headers;
    }

//    public void setHeader(String header) {
//        authorizationString = header;
//    }


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
    protected Response<String> parseNetworkResponse(final NetworkResponse response) {


        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(jsonString,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

//    @Override
//    public String getBodyContentType() {
//        return "application/x-www-form-urlencoded; charset=UTF-8";
//    }


    public void send() {
      //  Log.d(TAG, service + "\nURL: " + url + "\nPARAMS: " + params);
   //     Log.d(TAG, "TOKEN: " + token);
//        DataStore.getInstance(mContext).showLoader();
        showLoader();

        queue.add(this);
    }

//    public static void setToken(String userToken) {
//        token = userToken;
//    }

    public static void setContext(Context context) {
        mContext = context;
    }



    public void showLoader() {
        if (showLoader) {
            try {

                if (progressDialog != null && !progressDialog.isShowing()) {

                    progressDialog = ProgressDialog.show(mContext, "", loading);
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void removeLoader() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }


}
