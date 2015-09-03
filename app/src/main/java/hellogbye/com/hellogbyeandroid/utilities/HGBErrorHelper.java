package hellogbye.com.hellogbyeandroid.utilities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by nyawka on 9/2/15.
 */
public class HGBErrorHelper extends DialogFragment {

    private  static AlertDialog.Builder builder1;
    private static String NETWORK_ERROR = "Sorry you have ";
    private static String OK = "Ok";
    private String mMessage = "Please try again latter";
    private String mMessageTitle = "Error";


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())

                        // Set Dialog Title
                .setTitle(mMessageTitle)
                        // Set Dialog Message
                .setMessage(mMessage)

                        // Positive button
                .setPositiveButton(OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                }).create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if(tag != null){
            this.mMessage = tag;
        }
        super.show(manager, tag);
    }
}
