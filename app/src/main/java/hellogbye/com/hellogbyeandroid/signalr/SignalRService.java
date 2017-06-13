package hellogbye.com.hellogbyeandroid.signalr;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


import hellogbye.com.hellogbyeandroid.fragments.cnc.CNCSignalRFragment;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.network.Parser;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
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
    private String serverUrl = ConnectionManager.BASE_URL_SIGNALR_URL; // "http://apidev.hellogbye.com/dev/"; //"http://apidev.hellogbye.com/dev/"; //"https://apiprod.hellogbye.com/prod/";
    private String SERVER_HUB_CHAT = "hgbhub";//"cncHub";
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

        String strToken = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.TOKEN, "");
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

            }
        });




        ClientTransport clientTransport = new ServerSentEventsTransport(_connection.getLogger());

        SignalRFuture<Void> signalRFuture = _connection.start(clientTransport).done(new Action<Void>() {

            @Override
            public void run(Void obj) throws Exception {

                JsonObject jsonObject = new JsonObject();
                String invokationFunctionName = null;
                String connectionId = _connection.getConnectionId();
                String signalrConnectionID = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_ID, "");

              /*  if(signalrConnectionID != null && !signalrConnectionID.isEmpty()) {
                    invokationFunctionName = "cncClientRegisterR";
                    jsonObject.addProperty("signalRclientId",connectionId);

                }*/


          /*      if(signalrConnectionID != null && !signalrConnectionID.isEmpty()) {
                    invokationFunctionName = "cncClientReRegisterR";
                    jsonObject.addProperty("oldSignalRclientid",signalrConnectionID);
                    jsonObject.addProperty("newSignalRclientid",connectionId);
                }else{
                    invokationFunctionName = "cncClientRegisterR";
                    jsonObject.addProperty("signalRclientId",connectionId);
                }*/


                hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_ID, connectionId);


            //    jsonObject.addProperty(invokationFunctionName,_connection.getConnectionId());

                String userProfileID = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_PROFILE_ID, "");

                jsonObject.addProperty("userId",userProfileID);


                _hub.invoke(invokationFunctionName, jsonObject).done(new Action<Void>() {
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

                        CNCHiglightResponceCB.AnswearFromServerToUserChooses(signalRServerResponseForHighlightVO);


                    }
                }
                , JsonObject.class);

        _hub.on("cncOnHighlightQueryR",
                new SubscriptionHandler1<JsonObject>() {
                    @Override
                    public void run(final JsonObject msg) {

                        String response = msg.toString();

                        AirportServerResultCNCVO airportServerResultVO = (AirportServerResultCNCVO) Parser.parseAirportCNCResult(response);

                        CNCHiglightResponceCB.HiglightReceived(airportServerResultVO);


                    }
                }
                , JsonObject.class);



        _hub.on("cncOnMessageToClientR",
                new SubscriptionHandler1<Object>() {
                    @Override
                    public void run(final Object msg) {
                        //  final String finalMsg = msg.UserName + " says " + msg.Message;

                    }
                }
                , Object.class);

    }


    public void cncSubmitHighlightNew(ArrayList<AirportSendValuesVO> airportSendValuesVO, String preferencesProfileId){

        String query = airportSendValuesVO.get(0).getQuery();
        String userProfileId = airportSendValuesVO.get(0).getTravelpreferenceprofileid();

        JsonObject jsonObject =  createParams(query, userProfileId, preferencesProfileId, airportSendValuesVO);


        _hub.invoke("cncSubmitHighlightQueryR", jsonObject).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable throwable) {
                CNCHiglightResponceCB.SignalRRrror("Error int cncSubmitHiglight " +throwable.getMessage() );
            }
        });
    }

    public void cncSubmitHighlightExist(String query, String userProfileId, String preferencesProfileId,String solutionID) {

        JsonObject jsonObject =  createParams(query, userProfileId, preferencesProfileId, null);

        jsonObject.addProperty("solutionId",solutionID);

        _hub.invoke("cncSubmitHighlightQueryCmR", jsonObject).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable throwable) {
                CNCHiglightResponceCB.SignalRRrror("Error int cncSubmitHighlightQueryCmR " +throwable.getMessage() );
            }
        });

    }



    public void cncSubmitQueryCmR(String query, String preferencesID, String preferencesProfileId, String solutionID) {

        JsonObject jsonObject =  createParams(query, preferencesID, preferencesProfileId, null);
        jsonObject.addProperty("solutionId",solutionID);

        _hub.invoke("cncSubmitQueryCmR", jsonObject).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable throwable) {
                CNCHiglightResponceCB.SignalRRrror("Error int cncSubmitQueryCmR " +throwable.getMessage() );
            }
        });
    }

    public void cncSubmitQueryR(String query, String preferencesID, String preferencesProfileId) {

       /* JsonObject jsonObject =  createParams(query, preferencesID, preferencesProfileId, null);

        _hub.invoke("cncSubmitQueryR", jsonObject).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable throwable) {
                CNCHiglightResponceCB.SignalRRrror("Error int cncSubmitQueryR " +throwable.getMessage() );
            }
        });*/
    }


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
        gsonResult.addProperty("latitude",37.785834);
        gsonResult.addProperty("longitude",-122.406417);


        if(airportSendValuesVO != null){


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

            JsonArray jsonTokenArrayMain = new JsonArray();
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
