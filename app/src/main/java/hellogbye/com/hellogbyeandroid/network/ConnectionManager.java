package hellogbye.com.hellogbyeandroid.network;

import android.content.Context;

public class ConnectionManager {


    public interface ServerRequestListener {
        public void onSuccess(Object data);

        public void onError(Object data);
    }

    public static String BASE_URL = "";
    private static ConnectionManager _instance;
    private Context mContext;

    public enum Services {
        CREATE_ORDER, CANCEL_ORDER, GET_COUNTRIES_CODE, GET_LAST_ADDRESSES, LOGIN, ADD_FAVORITE_RIDE, GET_FAVORITE_RIDE,
        USER_POST_REGISTER, USER_POST_LOGIN, USER_POST_UPDATE_NOTIFICATION_TOKEN, USER_POST_VALIDATE_SMS_CODE,
        SEND_PHONE, MEDIA_UPLOAD, PASSENGER_UPDATE_PROFILE, ADD_FAVORITE_DRIVER, GET_AVAILABLE_TAXIS, ADD_FAV_ADDRESS,
        GET_ORDER_DETAILS, GET_MY_RIDES, GET_ESTIMATION_ORDER, EULA, GET_FAV_DRIVER, REMOVE_FAV_RIDE,REGISTER_TOKEN;
    }


    private ConnectionManager() {


    }

    public static ConnectionManager getInstance(Context context) {
        if (_instance == null) {
            _instance = new ConnectionManager();
        }
        _instance.mContext = context;
        HGBJsonRequest.setContext(context);
        return _instance;
    }



//    public void getEstimateTimeForOrder(String id, final ServerRequestListener listener) {
//        String url = getURL(Services.GET_ESTIMATION_ORDER);
//        url = url + "?orderID=" + id;
//        HGBJsonRequest req = new HGBJsonRequest(Request.Method.GET, url,
//                null, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(Parser.parseEstimation(response));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                listener.onError(Parser.parseErrorMessage(error));
//            }
//        }, false);
//
//
//    }



//
//    public void sendPhoneNumber(String phone, String phonePrefix, final ServerRequestListener listener) {
//        String url = getURL(Services.SEND_PHONE);
//        JsonObject params = new JsonObject();
//        params.addProperty("phoneNumber", phone);
//        params.addProperty("phonePrefix", phonePrefix);
//
//
//        HGBJsonRequest req = new HGBJsonRequest(Request.Method.POST, url,
//                params, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                listener.onSuccess(Parser.parsePhoneNumber(response));
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                listener.onError(Parser.parseErrorMessage(error));
//            }
//        });
//
//
//    }




    private String getURL(Services type) {
        String url = "";
        switch (type) {
            case USER_POST_REGISTER:
                return BASE_URL + "user/register";
            case USER_POST_LOGIN:
                return "";
            case USER_POST_VALIDATE_SMS_CODE:
                return BASE_URL + "user/validateCode";
            case SEND_PHONE:
                return BASE_URL + "user/sendPhoneNumber?debug=true";
            case CREATE_ORDER:
                return BASE_URL + "order/createOrder";
            case CANCEL_ORDER:
                return BASE_URL + "order/cancelOrder";
            case GET_COUNTRIES_CODE:
                return BASE_URL + "content/getCountryCodes";
            case GET_LAST_ADDRESSES:
                return BASE_URL + "passenger/getLastAddresses";
            case LOGIN:
                return BASE_URL + "user/login";
            case ADD_FAVORITE_RIDE:
                return BASE_URL + "passenger/addFavoriteRide";
            case GET_FAVORITE_RIDE:
                return BASE_URL + "passenger/getFavoriteAddresses";
            case MEDIA_UPLOAD:
                return BASE_URL + "media/upload";
            case PASSENGER_UPDATE_PROFILE:
                return BASE_URL + "passenger/updateProfile";
            case ADD_FAVORITE_DRIVER:
                return BASE_URL + "passenger/addFavoriteDriver";
            case GET_AVAILABLE_TAXIS:
                return BASE_URL + "driver/getAvailableTaxis";
            case ADD_FAV_ADDRESS:
                return BASE_URL + "passenger/addFavoriteAddress";
            case GET_ORDER_DETAILS:
                return BASE_URL + "order/getOrderDetailsForPassenger";
            case GET_MY_RIDES:
                return BASE_URL + "passenger/getMyRides";
            case GET_ESTIMATION_ORDER:
                return BASE_URL + "order/getEstimationTime";
            case EULA:
                return BASE_URL + "passenger/elua";
            case GET_FAV_DRIVER:
                return BASE_URL + "passenger/getFavoriteDrivers";
            case REMOVE_FAV_RIDE:
                return BASE_URL + "passenger/removeFavoriteAddress";

            case REGISTER_TOKEN:
                return BASE_URL + "user/updateNotificationToken";




            default:


                break;
        }
        return url;
    }


}
