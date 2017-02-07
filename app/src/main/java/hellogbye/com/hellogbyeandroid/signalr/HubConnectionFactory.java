package hellogbye.com.hellogbyeandroid.signalr;

import android.util.Log;

import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.InvalidStateException;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

/**
 * Created by nyawka on 12/26/16.
 */

public class HubConnectionFactory {
    private static HubConnectionFactory mInstance= null;
    private HubConnection mConnection;
    private HubProxy mChat;

    protected HubConnectionFactory(){}

    public static synchronized HubConnectionFactory getInstance(){
        if(null == mInstance){
            mInstance = new HubConnectionFactory();
        }
        return mInstance;
    }

    public HubConnection getHubConnection() {
        return mConnection;
    }

    public HubProxy getChatHub() {
        return mChat;
    }

    public SignalRFuture<Void> connect(String url) {
        final SignalRFuture<Void> future = new SignalRFuture<Void>();
        createObjects(url, future);

        return future;
    }

    public void createObjects(String url, final SignalRFuture<Void> future){



        mConnection = new HubConnection(url, "" , true, new Logger() {

            @Override
            public void log(String message, LogLevel level) {
                if (level == LogLevel.Critical) {
                    Log.d("SignalR", level.toString() + ": " + message);
                }
            }
        });

        try {
            mChat = mConnection.createHubProxy("ChatHub");
        } catch (InvalidStateException e) {
            Log.d("SignalR", "Error getting creating proxy: " + e.toString());
            future.triggerError(e);
        }

        //ClientTransport transport = new LongPollingTransport(mConnection.getLogger()); // works as expected
        ClientTransport transport = new ServerSentEventsTransport(mConnection.getLogger()); // Works on WiFi, never connects on 3G, no SignalRRrror is thrown either
        //ClientTransport transport = new WebsocketTransport(mConnection.getLogger()); // Never connects, not SignalRRrror is thrown
        SignalRFuture<Void> connectionFuture = mConnection.start(transport);

        mConnection.connected(new Runnable() {
            @Override
            public void run() {

                future.setResult(null);
            }
        });

        mConnection.error(new ErrorCallback() {
            @Override
            public void onError(Throwable error) {
                Log.d("SignalR", "Connection SignalRRrror: " + error.toString());

                if (!future.isDone()) {
                    future.triggerError(error);
                }
            }
        });
    }

    public void disconnect() {
        mChat = null;
        mConnection.stop();
    }
}
