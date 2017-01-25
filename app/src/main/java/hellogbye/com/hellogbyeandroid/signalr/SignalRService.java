package hellogbye.com.hellogbyeandroid.signalr;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.Credentials;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.Request;
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
    private String serverUrl = "https://apiprod.hellogbye.com/prod/";
    private String SERVER_HUB_CHAT = "cncHub";

    @Override
    public IBinder onBind(Intent intent) {
        // Return the communication channel to the service.
        startSignalR();
        return mBinder;
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
                System.out.println("Kate Done Connecting! mHubConnection.getConnectionId() =" + _connection.getConnectionId()+ "Kate userProfileID =" + userProfileID);


                Map<String, String> nameValuePairs = new HashMap<String, String>();

                nameValuePairs.put("signalRclientId",_connection.getConnectionId());
                nameValuePairs.put("userId",userProfileID);

                    _hub.invoke("cncClientRegisterR", nameValuePairs).done(new Action<Void>() {
                    @Override
                    public void run(Void aVoid) throws Exception {
                        System.out.println("Kate cncClientRegisterR done!!!!");
                    }
                }).onError(new ErrorCallback() {

                    @Override
                    public void onError(Throwable error) {
                        // Error handling
                        System.out.println("Kate error =" + error.getMessage());
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
                new SubscriptionHandler1<Object>() {
                    @Override
                    public void run(final Object msg) {
                      //  final String finalMsg = msg.UserName + " says " + msg.Message;
                        System.out.println("Kate finalMsg cncOnShowSolutionR=" + msg.toString());
                        // display Toast message
                       /* mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), finalMsg, Toast.LENGTH_SHORT).show();
                            }
                        });*/
                    }
                }
                , Object.class);

        _hub.on("cncOnHighlightQueryR",
                new SubscriptionHandler1<Object>() {
                    @Override
                    public void run(final Object msg) {
                        //  final String finalMsg = msg.UserName + " says " + msg.Message;
                        System.out.println("Kate finalMsg cncOnHighlightQueryR=" + msg.toString());

                    }
                }
                , Object.class);

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


    public void cncSubmitQueryR(String query, ArrayList<AirportSendValuesVO> airportSendValuesVOsTemp, String preferencesProfileId) throws JSONException {
        Map<String, Object> jsonObject =  createParams(query, airportSendValuesVOsTemp, preferencesProfileId);
        System.out.println("Kate query =" + query + "Json" + jsonObject.toString());

        _hub.invoke("cncSubmitQueryR", jsonObject).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println("Kate cncSubmitQueryR errro" );
            }
        });
    }


    private Map<String, Object> createParams(String query, ArrayList<AirportSendValuesVO> selectedUserChoose, String userProfileId) throws JSONException {

        Map<String, Object> nameValuePairs1 = new HashMap<String, Object>();
        Map<String, Object> nameValuePairs2 = new HashMap<String, Object>();

     /*   nameValuePairs.put("signalRclientId",_connection.getConnectionId());
        nameValuePairs.put("userId",userProfileID);*/

/*
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("clientContext", new JSONObject().put("currentHotelGuid",""));*/



      /*  JsonObject jsonObject1 = new JsonObject();

        JsonObject jObj22 = new JsonObject();
        jObj22.addProperty("currentHotelGuid","");*/



        nameValuePairs2.put("currentHotelGuid","");

        nameValuePairs1.put("clientContext",nameValuePairs2);

        //jsonObject1.add("clientContext",jObj22);




        //travel profile id
       // getApplicationContext().get.getPersonalUserInformation().getmTravelPreferencesProfileId()
        if(selectedUserChoose != null) {
            nameValuePairs1.put("travelpreferenceprofileId", selectedUserChoose.get(0).getTravelpreferenceprofileid());
            nameValuePairs1.put("itineraryid", selectedUserChoose.get(0).getId());

            /*jsonObject1.addProperty("travelpreferenceprofileId", selectedUserChoose.get(0).getTravelpreferenceprofileid());
            jsonObject1.addProperty("itineraryid", selectedUserChoose.get(0).getId());*/
    /*        jsonObject.put("travelpreferenceprofileId", selectedUserChoose.get(0).getTravelpreferenceprofileid());
            jsonObject.put("itineraryid", selectedUserChoose.get(0).getId());*/
        }

        if(userProfileId != null) {
           // jsonObject.put("travelpreferenceprofileId", userProfileId);
            nameValuePairs1.put("travelpreferenceprofileId", userProfileId);
            //jsonObject1.addProperty("travelpreferenceprofileId", userProfileId);

        }
        nameValuePairs1.put("signalRclientId",_connection.getConnectionId().toString());
       // jsonObject1.addProperty("signalRclientId",_connection.getConnectionId().toString());
        //jsonObject.put("signalRclientId",_connection.getConnectionId().toString());

        /*if( [[UserSessionManager sharedManager] hasLocation] )
        {
            body[@"latitude"] = [[UserSessionManager sharedManager] getUsersLatitude];
            body[@"longitude"] = [[UserSessionManager sharedManager] getUsersLongitude];
        }
        else
        {
            body[@"latitude"] = @"0";
            body[@"longitude"] = @"0";
        }*/


    /*    jsonObject1.addProperty("latitude",37.785834);
        jsonObject1.addProperty("longitude",-122.406417);
        jsonObject1.addProperty("query",query);*/



        nameValuePairs1.put("query",query);
        nameValuePairs1.put("latitude",37.785834);
        nameValuePairs1.put("longitude",-122.406417);

      /*  jsonObject.put("latitude",37.785834);
        jsonObject.put("longitude",-122.406417);
        jsonObject.put("query",query);*/

      /*  if(selectedUserChoose != null){
            JSONObject jsonObjectTokens = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (AirportSendValuesVO airportSendValuesVO : selectedUserChoose) {
                jsonArray.put(airportSendValuesVO.);
            }
            jsonObjectTokens.put("token",selectedUserChoose);
        }*/

        return nameValuePairs1;
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
