package hellogbye.com.hellogbyeandroid.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
import com.google.gson.JsonObject;


import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;



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
    private JsonObject params;
    private static String token;
    private boolean showLoader;
    ProgressDialog progressDialog;
    String loading;


    public HGBJsonRequest(int method, String url, JsonObject params, Listener<String> listener, ErrorListener errorListener) {

        super(method, url, errorListener);
        this.listener = listener;
        this.params = params;
        this.errorListener = errorListener;
        this.queue = Volley.newRequestQueue(mContext.getApplicationContext());
        this.url = url;
        showLoader = true;
        progressDialog = new ProgressDialog(mContext);
        loading = "Loading...";
        setService(url);
        send();
    }

    public HGBJsonRequest(int method, String url, JsonObject params, Listener<String> listener, ErrorListener errorListener, boolean showLoader) {
        super(method, url, errorListener);
        this.listener = listener;
        this.params = params;
        this.errorListener = errorListener;
        this.queue = Volley.newRequestQueue(mContext.getApplicationContext());
        this.url = url;
        setService(url);
        progressDialog = new ProgressDialog(mContext);
        loading = "loading...";
        this.showLoader = showLoader;
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
        if (errorListener != null) {
            errorListener.onErrorResponse(error);
        }
    }


    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Token token=" + token);
        if (!TextUtils.isEmpty(this.authorizationString)) {
            headers.put("Authorization", this.authorizationString);
        }
        return headers;
    }

    public void setHeader(String header) {
        authorizationString = header;
    }


    @Override
    public byte[] getBody() {
        try {
            return params == null ? null : params.toString().getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    params.toString(), PROTOCOL_CHARSET);
            return null;
        }
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String jsonString;
        try {
            jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
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
        Log.d(TAG, service + "\nURL: " + url + "\nPARAMS: " + params);
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
}
