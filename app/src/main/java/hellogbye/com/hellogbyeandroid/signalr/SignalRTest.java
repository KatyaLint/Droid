/*
package hellogbye.com.hellogbyeandroid.signalr;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.transport.LongPollingTransport;

*/
/**
 * Created by nyawka on 1/23/17.
 *//*


public class SignalRTest {

    private HubConnection _connection;
    private HubProxy _hub;
    private String serverUrl = "https://apiprod.hellogbye.com/prod/";
    private String SERVER_HUB_CHAT = "cncHub";
    private boolean isConnected;

    public void startNewSignalR(){
        String host = serverUrl; //The url from your web site
        _connection = new HubConnection( host );
        _hub = _connection.createHubProxy(SERVER_HUB_CHAT);
    }

    private SignalRFuture<Void> _awaitConnection;

    private void startConnection(){
        _awaitConnection = _connection.start(new LongPollingTransport(_connection.getLogger()));
//you can do here a while loop for reintents or something ( this is a good practices, at least 3 tries.
        try {
            _awaitConnection.get(2000, TimeUnit.MILLISECONDS);
          //  escucharGrupos();
            isConnected = true;
            break;
        } catch (InterruptedException e) {
            System.out.println("Disconnect . . .");
        } catch (ExecutionException e) {
            System.out.println("Error . . .");
        }catch(Exception e){}
    }

    private SubscriptionHandler1 handlerCon;

    private void starHandlersConnection(){
        handlerCon = new SubscriptionHandler1<String>() {
            @Override
            public void run(String p1) {
                //Here is where we get back the response from the server. Do stuffs
            }
        };

        _hub.on("cncSubmitQueryR",handlerCon,String.class);
    }


}
*/
