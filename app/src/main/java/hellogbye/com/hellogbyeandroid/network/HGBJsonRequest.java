package hellogbye.com.hellogbyeandroid.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.application.HGBApplication;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;


public class HGBJsonRequest extends Request<String> {
    protected static final String PROTOCOL_CHARSET = "utf-8";
    /**
     * Content type for request.
     */
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    private String authorizationString;
    private RequestQueue queue;
    private static final String TAG = "NETWORK";
    private String url;
    private String service;
    private static Context mContext;
    private Listener<String> listener;
    private ErrorListener errorListener;
    private JSONArray jsonArrayParams;
    private JSONObject jsonParams;
    private static String token;
    private boolean showLoader;
    private ProgressDialog progressDialog;
    private String loading;



    public HGBJsonRequest(int method, String url, JSONArray params, Listener<String> listener, ErrorListener errorListener) {

//        super(method, url, (stringBodyRequest == null || stringBodyRequest.length() == 0) ? null : stringBodyRequest, listener,	errorListener);
        super(method, url, errorListener);

        this.listener = listener;
        this.jsonArrayParams = params;


        this.errorListener = errorListener;
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        this.queue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkHttpStack(client));
       // this.queue = Volley.newRequestQueue(mContext.getApplicationContext());
        this.url = url;
        showLoader = true;
        progressDialog = new ProgressDialog(mContext);
        loading = "Loading...";
        setRetryPolicy(new DefaultRetryPolicy(
                HGBApplication.TIMEOUT_TIME,

                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setService(url);
        send();
    }

    public HGBJsonRequest(int method, String url, JSONObject params, Listener<String> listener, ErrorListener errorListener, boolean showLoader) {

//        super(method, url, (stringBodyRequest == null || stringBodyRequest.length() == 0) ? null : stringBodyRequest, listener,	errorListener);
        super(method, url, errorListener);

        this.listener = listener;
        this.jsonParams = params;


        this.errorListener = errorListener;
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        this.queue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkHttpStack(client));
      //  this.queue = Volley.newRequestQueue(mContext.getApplicationContext());
        this.url = url;
        this.showLoader = showLoader;
        progressDialog = new ProgressDialog(mContext);
        loading = "Loading...";
        setRetryPolicy(new DefaultRetryPolicy(
                HGBApplication.TIMEOUT_TIME,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setService(url);
        send();
    }

    public HGBJsonRequest(int method, String url, JSONObject params, Listener<String> listener, ErrorListener errorListener) {

//        super(method, url, (stringBodyRequest == null || stringBodyRequest.length() == 0) ? null : stringBodyRequest, listener,	errorListener);
        super(method, url, errorListener);

        this.listener = listener;
        this.jsonParams = params;


        this.errorListener = errorListener;
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        this.queue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkHttpStack(client));
       // this.queue = Volley.newRequestQueue(mContext.getApplicationContext());
        this.url = url;
        this.showLoader = true;
        progressDialog = new ProgressDialog(mContext);
        loading = "Loading...";
        setRetryPolicy(new DefaultRetryPolicy(
                HGBApplication.TIMEOUT_TIME,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        setService(url);
        send();
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

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

            listener.onResponse(response);
        }


    }

    @Override
    public void deliverError(VolleyError error) {
        Log.e(TAG, "ERROR: " + service + " " + (error != null && error.networkResponse != null ? error.getClass().getSimpleName() + ": " + error.networkResponse.statusCode + " " + new String(error.networkResponse.data) : "null"));
       removeLoader();
        if (errorListener != null && error != null) {
            errorListener.onErrorResponse(error);
        }

//		super.deliverError(error);
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json; charset=utf-8");
        HGBPreferencesManager sharedPreferences = HGBPreferencesManager.getInstance(mContext);
        String token = sharedPreferences.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
        Log.d("Token", token);
        if (!token.equals("")) {
            headers.put("Authorization", "Session " + token);
            Log.d("Session: ", token);
        }
        return headers;
    }

    public void setHeader(String header) {
        authorizationString = header;
    }


    @Override
    public byte[] getBody() {
        try {

            if (jsonArrayParams != null) {
                return jsonArrayParams == null ? null : jsonArrayParams.toString().getBytes(PROTOCOL_CHARSET);
            } else if (jsonParams != null) {
                return jsonParams == null ? null : jsonParams.toString().getBytes(PROTOCOL_CHARSET);
            }

//            Object oo = returnParams();
//            return oo == null ? null : oo.toString().getBytes(PROTOCOL_CHARSET);
     //       return jsonParams == null ? null : jsonParams.toString().getBytes(PROTOCOL_CHARSET);

        } catch (UnsupportedEncodingException uee) {
            if (jsonArrayParams != null) {
                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                        jsonArrayParams.toString(), PROTOCOL_CHARSET);
            } else if (jsonParams != null) {
                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                        jsonParams.toString(), PROTOCOL_CHARSET);
            }

            return null;
        }
        return null;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String jsonString;
        try {
            jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.d("HGBJsonRequest",jsonString);
        } catch (UnsupportedEncodingException e) {
            jsonString = new String(response.data);
        }
        return Response.success(jsonString,
                HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }


    public void send() {
        Log.d(TAG, service + "\nURL: " + url + "\nPARAMS: " + returnParams());
        Log.d(TAG, "TOKEN: " + token);
//        DataStore.getInstance(mContext).showLoader();
        showLoader();

        queue.add(this);
    }

    public static void setToken(String userToken) {
        token = userToken;
    }

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

    private Object returnParams() {

        try {
            if (jsonArrayParams != null) {
                return jsonArrayParams == null ? null : jsonArrayParams.toString().getBytes(PROTOCOL_CHARSET);
            } else if (jsonParams != null) {
                return jsonParams == null ? null : jsonParams.toString().getBytes(PROTOCOL_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            if (jsonArrayParams != null) {
                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                        jsonArrayParams.toString(), PROTOCOL_CHARSET);
            } else if (jsonParams != null) {
                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                        jsonParams.toString(), PROTOCOL_CHARSET);
            }

            return null;
        }

        return null;
    }
}
