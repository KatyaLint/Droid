package hellogbye.com.hellogbyeandroid.signalr;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import hellogbye.com.hellogbyeandroid.fragments.cnc.CNCSignalRFragment;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.network.Parser;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

/**
 * Created by nyawka on 12/20/16.
 */

public class SignalRService extends Service {

    private final IBinder mBinder = new LocalBinder(); // Binder given to clients
    private HGBPreferencesManager hgbPrefrenceManager;

    private HubConnection _connection;
    private HubProxy _hub;
    /*private String serverUrl = "https://apiprod.hellogbye.com/prod/";*/
    private String serverUrl = "http://apidev.hellogbye.com/dev/"; //"http://apidev.hellogbye.com/dev/"; //"https://apiprod.hellogbye.com/prod/";
    private String SERVER_HUB_CHAT = "cncHub";
    private CNCSignalRFragment.IHiglightReceivedFromServer CNCHiglightResponceCB;

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        startSignalR();
        return mBinder;
    }

    public void setCNCHiglightResponceCB(CNCSignalRFragment.IHiglightReceivedFromServer CNCHiglightResponceCB) {
        this.CNCHiglightResponceCB = CNCHiglightResponceCB;
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public SignalRService getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalRService.this;
        }
    }



    private void startSignalR() {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());

        String strToken = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
       // request.addHeader("session_token", strToken);
        String host = serverUrl; //The url from your web site
        String sessionToken = "session_token="+strToken;

        _connection = new HubConnection(host,sessionToken,true,new Logger() {

            @Override
            public void log(String message, LogLevel level) {
                Log.d("SignalR", level.toString() + ": " + message);

            }
        });

        _hub = _connection.createHubProxy(SERVER_HUB_CHAT);

        // Subscribe to the connected event
        _connection.connected(new Runnable() {

            @Override
            public void run() {
                System.out.println("Kate CONNECTED");

            }
        });




        ClientTransport clientTransport = new ServerSentEventsTransport(_connection.getLogger());

        SignalRFuture<Void> signalRFuture = _connection.start(clientTransport).done(new Action<Void>() {

            @Override
            public void run(Void obj) throws Exception {

                String userProfileID = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, "");

                Map<String, String> nameValuePairs = new HashMap<String, String>();

                nameValuePairs.put("signalRclientId",_connection.getConnectionId());
                nameValuePairs.put("userId",userProfileID);

                _hub.invoke("cncClientRegisterR", nameValuePairs).done(new Action<Void>() {
                    @Override
                    public void run(Void aVoid) throws Exception {
                    }
                }).onError(new ErrorCallback() {

                    @Override
                    public void onError(Throwable error) {
                    }
                });


            }
        });

        try {
            signalRFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }


        _hub.on("cncOnShowSolutionR",
                new SubscriptionHandler1<JsonObject>() {
                    @Override
                    public void run(final JsonObject msg) {

                        String response = msg.toString();
                        SignalRServerResponseForHighlightVO signalRServerResponseForHighlightVO = (SignalRServerResponseForHighlightVO) Parser.parseSignalRHighlightResponse(response);


                        System.out.println("Kate finalMsg cncOnShowSolutionR=" + msg.toString());
                        CNCHiglightResponceCB.answearFromServerToUserChooses(signalRServerResponseForHighlightVO);


                    }
                }
                , JsonObject.class);

        _hub.on("cncOnHighlightQueryR",
                new SubscriptionHandler1<JsonObject>() {
                    @Override
                    public void run(final JsonObject msg) {
                        System.out.println("Kate finalMsg cncOnHighlightQueryR=" );
                        String response = msg.toString();

                     //   AirportServerResultCNCVO airportServerResultCNCVO = (AirportServerResultCNCVO)msg;

                     //   String json = msg;
                        AirportServerResultCNCVO airportServerResultVO = (AirportServerResultCNCVO) Parser.parseAirportCNCResult(response);

                        //  final String finalMsg = msg.UserName + " says " + msg.Message;


                        System.out.println("Kate finalMsg cncOnHighlightQueryR airportServerResultVO=" + airportServerResultVO.toString() );

                        CNCHiglightResponceCB.higlightReceived(airportServerResultVO);


                    }
                }
                , JsonObject.class);

        _hub.on("cncOnMessageToClientR",
                new SubscriptionHandler1<Object>() {
                    @Override
                    public void run(final Object msg) {
                        //  final String finalMsg = msg.UserName + " says " + msg.Message;
                        System.out.println("Kate finalMsg cncOnMessageToClientR =" + msg.toString());

                    }
                }
                , Object.class);

    }


    public void cncSubmitHighlightNew(ArrayList<AirportSendValuesVO> airportSendValuesVO, String preferencesProfileId){

        String query = airportSendValuesVO.get(0).getQuery();
        String userProfileId = airportSendValuesVO.get(0).getTravelpreferenceprofileid();
        System.out.println("Kate sfds");
        JsonObject jsonObject =  createParams(query, userProfileId, preferencesProfileId, airportSendValuesVO);
     //   System.out.println("Kate query =" + airportSendValuesVO.get(0).getQuery() + "Json" + jsonObject.toString());
        //      String sendData = jsonObject.toString();
        _hub.invoke("cncSubmitHighlightQueryR", jsonObject).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println("Kate cncSubmitQueryR errro" );
            }
        });
    }

    public void cncSubmitHighlightExist() {

    }


    public void cncSubmitQueryR(String query, String preferencesID, String preferencesProfileId) {
      //  Map<String, Object> jsonObject =  createParams(query, airportSendValuesVOsTemp, preferencesProfileId);
      //  ArrayList<AirportSendValuesVO> selectedUserChoose,

        System.out.println("Kate cncSubmitQueryR");

        JsonObject jsonObject =  createParams(query, preferencesID, preferencesProfileId, null);
        System.out.println("Kate query =" + query + "Json" + jsonObject.toString());
  //      String sendData = jsonObject.toString();
        _hub.invoke("cncSubmitQueryR", jsonObject).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println("Kate cncSubmitQueryR errro" );
            }
        });
    }


 /*   private JSONObject createParams(String query, ArrayList<AirportSendValuesVO> selectedUserChoose, String userProfileId) throws JSONException {

        JSONObject gsonResult = new JSONObject();
        JSONObject gsonResult2 = new JSONObject();

        gsonResult2.put("currentHotelGuid","");
        gsonResult.put("clientContext",gsonResult2);

        if(selectedUserChoose != null) {


            gsonResult.put("travelpreferenceprofileId", selectedUserChoose.get(0).getTravelpreferenceprofileid());
            gsonResult.put("itineraryid", selectedUserChoose.get(0).getId());
        }

        if(userProfileId != null) {
            gsonResult.put("travelpreferenceprofileId", userProfileId);
        }
        gsonResult.put("signalRclientId",_connection.getConnectionId().toString());


        gsonResult.put("query",query);
        gsonResult.put("latitude","37.785834");
        gsonResult.put("longitude","-122.406417");


        return gsonResult;
    }
*/

    private JsonObject createParams(String query, String travelPreferenceProfileId,  String userProfileId,ArrayList<AirportSendValuesVO>  airportSendValuesVO)  {

        JsonObject gsonResult = new JsonObject();
        JsonObject gsonResult2 = new JsonObject();

        gsonResult2.addProperty("currentHotelGuid","");
        gsonResult.add("clientContext",gsonResult2);

        if(travelPreferenceProfileId != null) {

            gsonResult.addProperty("travelpreferenceprofileId", travelPreferenceProfileId);
          //  gsonResult.addProperty("itineraryid", selectedUserChoose.get(0).getId());
        }

        if(userProfileId != null) {
           gsonResult.addProperty("travelpreferenceprofileId", userProfileId);
         //   jObject.key("travelpreferenceprofileId").value(userProfileId);
        }


        gsonResult.addProperty("signalRclientId",_connection.getConnectionId().toString());


        gsonResult.addProperty("query",query);
        gsonResult.addProperty("latitude","37.785834");
        gsonResult.addProperty("longitude","-122.406417");


        if(airportSendValuesVO != null){


            JsonObject jsonObjectPosition = new JsonObject();


           /* JsonObject gsonResult = new JsonObject();
            JsonObject gsonResult2 = new JsonObject();

            gsonResult2.addProperty("currentHotelGuid","");
            gsonResult.add("clientContext",gsonResult2);*/


            JsonArray jsonArray = new JsonArray();
            for(AirportSendValuesVO sendValuesVO : airportSendValuesVO) {


                JsonObject jsonObjectToken = new JsonObject();
                jsonObjectToken.addProperty("value", sendValuesVO.getValue());
                jsonObjectToken.addProperty("end", sendValuesVO.getEnd());
                jsonObjectToken.addProperty("id", sendValuesVO.getId());
                jsonObjectToken.addProperty("type", sendValuesVO.getType());
                jsonObjectToken.addProperty("start", sendValuesVO.getStart());
                jsonArray.add(jsonObjectToken);
            }

         //   jsonObjectPosition.put()

 /*               JsonObject jsPositionArr =  new JsonObject();
                jsPositionArr.add("position",jsonArray);
                jsPositionArr.addProperty("type",airportSendValuesVO.get(0).getType());

            JsonArray jsonTokenArray = new JsonArray();
            jsonTokenArray.add(jsPositionArr);

            jsPositionArr.add("toke",jsonTokenArray);*/


         //   JsonObject jsonTokenObjMain = new JsonObject();
            JsonArray jsonTokenArrayMain = new JsonArray();
          //  jsonTokenObjMain.add("token",jsonTokenArrayMain);
            JsonObject jsTokenMainObj=  new JsonObject();
            jsonTokenArrayMain.add(jsTokenMainObj);

            jsTokenMainObj.addProperty("type",airportSendValuesVO.get(0).getType());
            jsTokenMainObj.add("position",jsonArray);

            gsonResult.add("token",jsonTokenArrayMain);

        }


        return gsonResult;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        startSignalR();
        return result;
    }

    @Override
    public void onDestroy() {
        _connection.stop();
        super.onDestroy();
    }
}
