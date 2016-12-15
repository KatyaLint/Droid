package hellogbye.com.hellogbyeandroid.utilities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import hellogbye.com.hellogbyeandroid.R;

/**
 * Created by nyawka on 12/15/16.
 */

public class HGBUtilityNetwork {

    private static LocationManager lm;

    public static boolean haveNetworkConnection(Context context) {
        
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    public static boolean isGPSEnable(final Context context){
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {
            return false;
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        } catch(Exception ex) {
            return false;
        }

        if(!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("gps_network_not_enabled");//context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton("open_location_settings"//context.getResources().getString(R.string.open_location_settings)
                    , new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Cancel"//context.getString(R.string.Cancel)
                    , new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            }).show();
         //   dialog.show();
            return false;
        }
        return true;
    }


    /**
     * Check if Gps or network location available if not alert to user,
     * if user agree go to activity that open gps
     * @param activity
     * @return String first latitude, '&', latitude
     */
    public static String getLocation(Activity activity, boolean isAlertEnable ){

        lm = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);

        boolean gps_enabled = false;
        boolean network_enabled = false;
        String strLocation = null;
        //Criteria criteria = new Criteria();
        //String bestProvider = lm.getBestProvider(criteria, false);

        // lm.requestLocationUpdates(bestProvider, 100, 1, locationListener);
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListener);
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        Location location = null;
        if(isAlertEnable && !gps_enabled && !network_enabled){
            showGPSDisabledAlertToUser(activity);
        }else if(network_enabled){

            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        }else if(gps_enabled){
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        if(location != null) {
            strLocation =location.getLatitude() + "&" + location.getLongitude();
        }else{
            strLocation =latitude + "&" + longitude;
        }
        return strLocation;
    }

    private static double latitude;
    private static double longitude;

    public static void removeGPSListener(){
        if(lm != null){
            lm.removeUpdates(locationListener);
        }
    }



    private static LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    private static void showGPSDisabledAlertToUser(final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                activity.startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                }).create().show();
        /*AlertDialog alert = alertDialogBuilder.create();
        alert.show();*/
    }

    /* Position */
    private static final long MINIMUM_TIME = 10000;  // 10s
    private static final float MINIMUM_DISTANCE = 50; // 50m

    public static void checkPermissions(Activity context){
        lm  = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        // Get the best provider between gps, network and passive
        Criteria criteria = new Criteria();
        String mProviderName = lm.getBestProvider(criteria, true);
        // API 23: we have to check if ACCESS_FINE_LOCATION and/or ACCESS_COARSE_LOCATION permission are granted

        if (Build.VERSION.SDK_INT >= 23) {

            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                // No one provider activated: prompt GPS

                if (mProviderName == null || mProviderName.equals("")) {
                    context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }

                // At least one provider activated. Get the coordinates
                switch (mProviderName) {
                    case "passive":
                        lm.requestLocationUpdates(mProviderName, MINIMUM_TIME, MINIMUM_DISTANCE, locationListener);
                        Location location = lm.getLastKnownLocation(mProviderName);
                        break;

                    case "network":
                        break;

                    case "gps":
                        break;

                }

                // One or both permissions are denied.
            } else {

                // The ACCESS_COARSE_LOCATION is denied, then I request it and manage the result in
                // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            HGBConstants.MY_PERMISSION_ACCESS_COARSE_LOCATION);
                }
                // The ACCESS_FINE_LOCATION is denied, then I request it and manage the result in
                // onRequestPermissionsResult() using the constant MY_PERMISSION_ACCESS_FINE_LOCATION
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            HGBConstants.MY_PERMISSION_ACCESS_FINE_LOCATION);
                }

            }
        }else{
            lm.requestLocationUpdates(mProviderName, MINIMUM_TIME, MINIMUM_DISTANCE, locationListener);
        }
    }
}
