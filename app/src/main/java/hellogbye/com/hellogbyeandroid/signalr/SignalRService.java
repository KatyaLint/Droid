package hellogbye.com.hellogbyeandroid.signalr;


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    private HubConnection mHubConnection;
    private HubProxy mHubProxy;
    private Handler mHandler; // to display Toast message
    private final IBinder mBinder = new LocalBinder(); // Binder given to clients
    private HGBPreferencesManager hgbPrefrenceManager;
    private String connectionID;
    private ServiceCallbacks serviceCallbacks;


    public void setCallbacks(ServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int result = super.onStartCommand(intent, flags, startId);
        startSignalR();
        return result;
    }

    @Override
    public void onDestroy() {
        mHubConnection.stop();
        super.onDestroy();
    }

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

    /**
     * method for clients (activities)
     */
    public void sendMessage(String message) {
        String SERVER_METHOD_SEND = "Send";
        mHubProxy.invoke(SERVER_METHOD_SEND, message);
    }

    private void startSignalR() {
        Platform.loadPlatformComponent(new AndroidPlatformComponent());


        System.out.println("Kate signalr");
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        Credentials credentials = new Credentials() {
            @Override
            public void prepareRequest(Request request) {
                String strToken = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
                 request.addHeader("ssession_token", strToken);
            }
        };

        String serverUrl = "https://apiprod.hellogbye.com/prod/";

     /*   String strToken = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
        String sessionToken = "session_token="+strToken;*/

        //mHubConnection = new HubConnection(serverUrl);


        String strToken = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");
       // request.addHeader("session_token", strToken);
        String sessionToken = "session_token="+strToken;

        mHubConnection = new HubConnection(serverUrl,sessionToken,true,new Logger() {

            @Override
            public void log(String message, LogLevel level) {
                Log.d("SignalR", level.toString() + ": " + message);
                /*if (level == LogLevel.Critical) {
                    Log.d("SignalR", level.toString() + ": " + message);
                }*/
            }
        });

       // mHubConnection.setCredentials(credentials);
       /* , sessionToken , true, new Logger() {

            @Override
            public void log(String message, LogLevel level) {
                if (level == LogLevel.Critical) {
                    Log.d("SignalR", level.toString() + ": " + message);
                }
            }
        });*/


        // Subscribe to the connected event
        mHubConnection.connected(new Runnable() {

            @Override
            public void run() {
                System.out.println("Kate CONNECTED");

            }
        });




    /*    mHubConnection = HubConnection(serverUrl, sessionToken, true, new Logger() {

            @Override
            public void log(String message, LogLevel level) {
                if (level == LogLevel.Critical) {
                    Log.d("SignalR", level.toString() + ": " + message);
                }
            }
        });*/
        
        //mHubConnection = new HubConnection(serverUrl);
      //  mHubConnection.setCredentials(credentials);
        String SERVER_HUB_CHAT = "cncHub";
        mHubProxy = mHubConnection.createHubProxy("cncHub");

        ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());

        SignalRFuture<Void> signalRFuture = mHubConnection.start(clientTransport).done(new Action<Void>() {

            @Override
            public void run(Void obj) throws Exception {
                System.out.println("Kate Done Connecting!");

                String userProfileID = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, "");
                System.out.println("Kate mHubConnection.getConnectionId() =" + mHubConnection.getConnectionId());
                System.out.println("Kate userProfileID =" + userProfileID);

              /*  String reg = "signalRclientId="+ mHubConnection.getConnectionId();
                String reg2 = "userId="+userProfileID;*/

                JSONObject jsonObject = new JSONObject();


                    jsonObject.put("signalRclientId", mHubConnection.getConnectionId());
                    jsonObject.put("userId", userProfileID);




                    mHubProxy.invoke("cncClientRegisterR", jsonObject).done(new Action<Void>() {
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



 /*       String CLIENT_METHOD_BROADAST_MESSAGE = "broadcastMessage";
        mHubProxy.on(CLIENT_METHOD_BROADAST_MESSAGE,
                new SubscriptionHandler1<CustomMessage>() {
                    @Override
                    public void run(final CustomMessage msg) {
                        final String finalMsg = msg.UserName + " says " + msg.Message;
                        // display Toast message
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), finalMsg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                , CustomMessage.class);*/


        mHubProxy.on("cncOnShowSolutionR",
                new SubscriptionHandler1<JSONObject>() {
                    @Override
                    public void run(final JSONObject msg) {
                      //  final String finalMsg = msg.UserName + " says " + msg.Message;
                        System.out.println("Kate finalMsg =" + msg.toString());
                        // display Toast message
                       /* mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), finalMsg, Toast.LENGTH_SHORT).show();
                            }
                        });*/
                    }
                }
                , JSONObject.class);

        mHubProxy.on("cncOnHighlightQueryR",
                new SubscriptionHandler1<JSONObject>() {
                    @Override
                    public void run(final JSONObject msg) {
                        //  final String finalMsg = msg.UserName + " says " + msg.Message;
                        System.out.println("Kate finalMsg =" + msg.toString());

                    }
                }
                , JSONObject.class);

        mHubProxy.on("cncOnMessageToClientR",
                new SubscriptionHandler1<JSONObject>() {
                    @Override
                    public void run(final JSONObject msg) {
                        //  final String finalMsg = msg.UserName + " says " + msg.Message;
                        System.out.println("Kate finalMsg =" + msg.toString());

                    }
                }
                , JSONObject.class);

    }



    /*@SuppressWarnings("deprecation")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(this, "Service Start", Toast.LENGTH_LONG).show();

        String server = "https://apiprod.hellogbye.com/prod/";
        HubConnection connection = new HubConnection(server);
        HubProxy proxy = connection.createHubProxy("ptThroughputHub");

        SignalRFuture<Void> awaitConnection = connection.start();
        try {
            awaitConnection.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //--HERE IS MY SOLUTION-----------------------------------------------------------
        //Invoke JoinGroup to start receiving broadcast messages
        proxy.invoke("JoinGroup", "Group1");

        //Then call on() to handle the messages when they are received.
        proxy.on( "broadcastMessage", new SubscriptionHandler1<String>() {
            @Override
            public void run(String msg) {
             //   Log.d("result := ", msg);
            }
        }, String.class);

        //--------------------------------------------------------------------------------
    }*/


    public void cncSubmitQueryR(String query, ArrayList<AirportSendValuesVO> airportSendValuesVOsTemp, String preferencesProfileId) throws JSONException {
        JSONObject jsonObject =  createParams(query, airportSendValuesVOsTemp, preferencesProfileId);
        System.out.println("Kate query =" + query + "Json" + jsonObject.toString());

        mHubProxy.invoke("cncSubmitQueryR", jsonObject).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable throwable) {
                System.out.println("Kate cncSubmitQueryR errro" );
            }
        });
    }


    /*-(void) cncSubmitQueryR:(NSString*)data
    {
        NSMutableDictionary* body = [self params:data tokens:nil];

        [_hub invoke:@"cncSubmitQueryR" withArgs:[NSArray arrayWithObject:body] completionHandler:nil];
    }*/

    private JSONObject createParams(String query, ArrayList<AirportSendValuesVO> selectedUserChoose, String userProfileId) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("clientContext", new JSONObject().put("currentHotelGuid",""));

        //travel profile id
       // getApplicationContext().get.getPersonalUserInformation().getmTravelPreferencesProfileId()
        if(selectedUserChoose != null) {
            jsonObject.put("travelpreferenceprofileId", selectedUserChoose.get(0).getTravelpreferenceprofileid());
            jsonObject.put("itineraryid", selectedUserChoose.get(0).getId());
        }

        if(userProfileId != null) {
            jsonObject.put("travelpreferenceprofileId", userProfileId);


        }


        jsonObject.put("signalRclientId",mHubConnection.getConnectionId().toString());

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


        jsonObject.put("latitude",37.785834);
        jsonObject.put("longitude",-122.406417);
        jsonObject.put("query",query);

      /*  if(selectedUserChoose != null){
            JSONObject jsonObjectTokens = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for (AirportSendValuesVO airportSendValuesVO : selectedUserChoose) {
                jsonArray.put(airportSendValuesVO.);
            }
            jsonObjectTokens.put("token",selectedUserChoose);
        }*/

        return jsonObject;
    }


/*    -(NSMutableDictionary*) params:(NSString*) data tokens:(NSArray<HighlightCellOfHighlightPositionAndValue*> *) tokens
    {
        NSMutableDictionary* body = [[NSMutableDictionary alloc] init];

        body[@"clientContext"] = [self clientContext];

        PreferenceProfile *selectedOrDefaultPreferenceProfile = [[UserProfileManager sharedManager] selectedOrDefaultPreferenceProfile];
        NSString *profileId = selectedOrDefaultPreferenceProfile.profileId;

        if(profileId)
        {
            body[@"travelpreferenceprofileId"] = profileId;
        }

        body[@"signalRclientId"] = self.connectionId;

        if( [[UserSessionManager sharedManager] hasLocation] )
        {
            body[@"latitude"] = [[UserSessionManager sharedManager] getUsersLatitude];
            body[@"longitude"] = [[UserSessionManager sharedManager] getUsersLongitude];
        }
        else
        {
            body[@"latitude"] = @"0";
            body[@"longitude"] = @"0";
        }

        body[@"query"] = data;

        if( tokens )
        {
            NSMutableArray* dictTokens = [[NSMutableArray alloc] init];

            for( HighlightCellOfHighlightPositionAndValue* cell in tokens )
            {
                [dictTokens addObject:[cell dictionary]];
            }

            body[@"token"] = dictTokens;
        }

        return body;
    }*/
}
