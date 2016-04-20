package hellogbye.com.hellogbyeandroid.utilities;


import android.app.AlertDialog;
import android.app.Dialog;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by nyawka on 9/2/15.
 */
public class HGBErrorHelper extends android.app.DialogFragment {

    private  static AlertDialog.Builder builder1;
    private static String NETWORK_ERROR = "Sorry you have ";
    private static String OK = "Ok";
    private String mMessage = "Please try again later";
    private String mMessageTitle = "Error";

    public void setMessageForError(String mMessage){
        this.mMessage = mMessage;

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())

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


        return alertDialog;
    }






    @Override
    public int show(FragmentTransaction transaction, String tag) {
        if(tag != null){
            this.mMessage = tag;
        }
        return super.show(transaction, tag);
    }

    //    @Override
//    public void show(FragmentManager manager, String tag) {
//        if(tag != null){
//            this.mMessage = tag;
//        }
//        super.show(manager, tag);
//    }
}
