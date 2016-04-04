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
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.application.HGBApplication;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;


public class HGBStringXMLRequest extends Request<String> {
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
    private CreditCardItem creditCardItem;
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


    public HGBStringXMLRequest(int method, String url, Map<String, String> params, Listener<String> listener, ErrorListener errorListener) {

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
                HGBApplication.TIMEOUT_TIME,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        showLoader = true;
        progressDialog = new ProgressDialog(mContext);
        loading = "Loading...";
        setService(url);
        send();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {

        String strResponce = "<?xml version=\"1.0\" encoding=\"utf-8\"?> <soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
                " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Header>" +
                " <AuthHeader xmlns=\"https://MyCardStorage.com/\"><UserName>HelloGByeUser</UserName><Password>bWapRT#ayLJYN5S!</Password></AuthHeader> " +
                "</soap:Header> <soap:Body><AddCOF_Soap xmlns=\"https://MyCardStorage.com/\"> <addToken><ServiceSecurity><ServiceUserName>HelloGBye</ServiceUserName><ServicePassword>NQbhm#KNDqO2X</ServicePassword>" +
                "<MCSAccountID>1</MCSAccountID><SessionID>x3p3z8?h</SessionID></ServiceSecurity>" +
                "<TokenData><CardNumber>"+creditCardItem.getCardNumber()+"</CardNumber>" +
                "<CardType>"+creditCardItem.getCardtypeid()+"</CardType>" +
                "<ExpirationMonth>"+creditCardItem.getExpmonth()+"</ExpirationMonth>" +
                "<ExpirationYear>"+creditCardItem.getExpyear()+"</ExpirationYear>" +
                "<NickName>"+creditCardItem.getNickname()+"</NickName>" +
                "<FirstName>"+creditCardItem.getBuyerfirstname()+"</FirstName>" +
                "<LastName>"+creditCardItem.getBuyerlastname()+"</LastName>" +
                "<StreetAddress>"+creditCardItem.getBuyeraddress()+"</StreetAddress>" +
                "<ZipCode>"+creditCardItem.getBuyerzip()+"</ZipCode>" +
                "<CVV>"+creditCardItem.getToken()+"</CVV>" +
                "<Last4>"+creditCardItem.getLast4()+"</Last4>" +
                "</TokenData></addToken>" +
                " </AddCOF_Soap> </soap:Body> </soap:Envelope>";

        return strResponce.getBytes();
    }



    public HGBStringXMLRequest(int method, String url, CreditCardItem creditCardItem, Listener<String> listener, ErrorListener errorListener, boolean showLoader) {
        super(method, url, errorListener);
        this.listener = listener;
        //    this.params = params;
        this.errorListener = errorListener;
        this.creditCardItem = creditCardItem;
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new StethoInterceptor());
        this.queue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkHttpStack(client));
        // this.queue = Volley.newRequestQueue(mContext.getApplicationContext());
        this.url = url;
        setRetryPolicy((new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)));
        setService(url);
        progressDialog = new ProgressDialog(mContext);
        loading = "loading...";
        this.showLoader = showLoader;
        send();
    }


//    protected Map<String, String> getParams()
//            throws AuthFailureError {
//        return params;
//    };

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

//    public String getBodyContentType()
//    {
//        return "application/xml";
//    }


    @Override
    public String getBodyContentType() {
        return "text/xml";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "text/xml; charset=utf-8");
        headers.put("SOAPAction", "https://MyCardStorage.com/AddCOF_Soap");
        headers.put("Content-Length", "1079");

       // headers.put("Content-Type", "application/json");
//        HGBPreferencesManager sharedPreferences = HGBPreferencesManager.getInstance(mContext);
//        String token = sharedPreferences.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
//        if(!token.equals("")){
//            headers.put("Authorization", "Session " + token);
//        }
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
