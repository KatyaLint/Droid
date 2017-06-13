package hellogbye.com.hellogbyeandroid.signalr;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import hellogbye.com.hellogbyeandroid.activities.CreateAccountActivity;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
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
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

/**
 * Created by nyawka on 12/20/16.
 */

public class SignalRServiceLogin extends Service {

    private final IBinder mBinder = new LocalBinder(); // Binder given to clients
    private HGBPreferencesManager hgbPrefrenceManager;

    private HubConnection _connection;
    private HubProxy _hub;
    /*private String serverUrl = "https://apiprod.hellogbye.com/prod/";*/
    private String serverUrl = ConnectionManager.BASE_URL_SIGNALR_URL; // "http://apidev.hellogbye.com/dev/"; //"http://apidev.hellogbye.com/dev/"; //"https://apiprod.hellogbye.com/prod/";
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
        public SignalRServiceLogin getService() {
            // Return this instance of SignalRService so clients can call public methods
            return SignalRServiceLogin.this;
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

        SignalRFuture<Void> signalRFuture = _connection.start(clientTransport);


        signalRFuture.done(new Action<Void>() {

            @Override
            public void run(Void obj) throws Exception {

                JsonObject jsonObject = new JsonObject();
                String connectionId = _connection.getConnectionId();


                hgbPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_LOGIN_ID, connectionId);

                String userProfileID = hgbPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_PROFILE_ID, "");

                jsonObject.addProperty("userId",userProfileID);


            }
        }).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable throwable) {

            }
        }).onCancelled(new Runnable() {
            @Override
            public void run() {

            }
        });



        try {
            signalRFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return;
        }



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
