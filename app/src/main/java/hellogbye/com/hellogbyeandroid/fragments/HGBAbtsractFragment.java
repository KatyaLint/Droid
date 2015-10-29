package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;

import org.apache.http.Header;

import hellogbye.com.hellogbyeandroid.HGBMainInterface;

/**
 * Created by arisprung on 9/20/15.
 */
public class HGBAbtsractFragment extends Fragment {

    private HGBMainInterface mActivityInterface;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mActivityInterface = (HGBMainInterface) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement HostingActivityInterface");
        }
    }

    protected HGBMainInterface getActivityInterface() {
        if (mActivityInterface != null) {
            return mActivityInterface;
        }
        return  null;


    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

//    private void restartApp() {
////        Intent intent = new Intent(getActivity().getApplicationContext(), PickerFragmentActivity.class);
////        getActivity().startActivity(intent);
//
//        Intent i = getActivity().getBaseContext().getPackageManager()
//                .getLaunchIntentForPackage( getActivity().getBaseContext().getPackageName() );
//        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(i);
//    }

    protected void handleRequestFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        error.printStackTrace();



        String response = "";

        if (responseBody == null) { //no internet
            response = "No Internet Connection\n\nConnect to Wifi to continue";
        } else {
            try {
                response = new String(responseBody, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String title = "Server Error";
        String msg = "(" + statusCode + ") " + response;

        errorDialog(title, msg);
    }

    protected void serverOutputErrorDialog(byte[] responseBody) {
        String response = "";
        try {
            response = new String(responseBody, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        errorDialog(
                "Unexpected Data",
                "The server has returned an unexpected response:\n\n"
                        + response.substring(0, Math.min(500, response.length()))
        );
    }

    protected void errorDialog(String title, String msg) {
        new AlertDialog.Builder(getActivity())
                .setTitle("Error: " + title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    protected void handleRequestFailure(String error) {


        errorDialog("Error", error);
    }
}
