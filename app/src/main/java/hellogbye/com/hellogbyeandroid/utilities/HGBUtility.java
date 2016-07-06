package hellogbye.com.hellogbyeandroid.utilities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;


import android.content.pm.PackageManager;
import android.location.Criteria;
import android.os.Build;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import java.util.Stack;
import java.util.concurrent.Exchanger;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;



/**
 * Created by arisprung on 8/17/15.
 */
public class HGBUtility {

    private static ProgressDialog progressDialog;
    private static Stack<Fragment> fragmentStack = new Stack<Fragment>();
    private static LocationManager lm;
    private static String BASE_SERVER_TIME_ZONE = "yyyy-MM-dd'T'HH:mm:ss";

    public static void downloadImage(Bitmap showedImgae){

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/DCIM/HGB");
        myDir.mkdirs();

        String fname = "mainusername.png";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file); //
         //   showedImgae = getRoundedCornerBitmap(showedImgae, 100);
            showedImgae.compress(Bitmap.CompressFormat.PNG, 100, out); // may be save but not round
          //  Toast.makeText(MyActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri contentUri = Uri.fromFile(file);
//        mediaScanIntent.setData(contentUri);
      //  getApplicationContext().sendBroadcast(mediaScanIntent);
    }

    public static Bitmap getBitmapFromCache(Context context){

        File sd = Environment.getExternalStorageDirectory();
        File image = new File(sd+"/DCIM/HGB/", "mainusername.png");
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(),bmOptions);
        if(bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 60, 60, true);
        }else{
            Drawable myDrawable = ContextCompat.getDrawable(context, R.drawable.profile_image);//context.getDrawable(R.drawable.profile_image);
            if(myDrawable != null) {
                bitmap = ((BitmapDrawable) myDrawable).getBitmap();
            }
        }


        return bitmap;
    }

    public static void getAndSaveUserImage(String imageUrl, ImageView imageView,final ImageView imageViewSec){

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .considerExifParams(true)
                .showImageForEmptyUri(R.drawable.profile_image)
                .cacheInMemory(true)
                .showImageOnLoading(R.drawable.profile_image)
                .showImageOnFail(R.drawable.profile_image)
                .showImageForEmptyUri(R.drawable.profile_image)
                .cacheOnDisk(true)
                .build();


        ImageLoader imageLoader = ImageLoader.getInstance();


        imageLoader.displayImage( imageUrl,imageView, options, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                String message = null;
                switch (failReason.getType()) {
                    case IO_ERROR:
                        message = "Input/Output error";
                        break;
                    case DECODING_ERROR:
                        message = "Image can't be decoded";
                        break;
                    case NETWORK_DENIED:
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "Out Of Memory error";
                        break;
                    case UNKNOWN:
                        message = "Unknown error";
                        break;
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(imageViewSec != null) { //can be null if from account screen changed picture
                    imageViewSec.setImageBitmap(loadedImage);
                }
                downloadImage( loadedImage);
            }
        });
    }







    public static void loadRoundedImage(String imageUrl,final ImageView imageView, int tempAvatar) {

    //    imageUrl = imageUrl + ".png";
        DisplayImageOptions options = new DisplayImageOptions.Builder()
               // .considerExifParams(true)
                .showImageForEmptyUri(tempAvatar)
                .cacheInMemory(true)
                .showImageOnLoading(tempAvatar)
                .showImageOnFail(tempAvatar) //R.drawable.profile_image
                .showImageForEmptyUri(tempAvatar)
                .cacheOnDisk(false)
//                .postProcessor(new BitmapProcessor() {
//                    @Override
//                    public Bitmap process(Bitmap bmp) {
//                        try {
//                        //    return Bitmap.createScaledBitmap(bmp,  bmp.getWidth()/2,bmp.getHeight()/2, false);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        return null;
//                    }
//                })
                .build();

        ImageLoader.getInstance().displayImage(imageUrl, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });

    }

    public static void loadHotelImage(Context context, String imageUrl, ImageView imageView) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .considerExifParams(true)
//                .showImageForEmptyUri(R.drawable.new_action_profile_edit_large)
//                .cacheInMemory(true)
//                .showImageOnLoading(R.drawable.icon_placeholder)
//                .showImageOnFail(R.drawable.icon_placeholder)
//                .showImageForEmptyUri(R.drawable.icon_placeholder)
                .cacheOnDisk(true)
                .postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bmp) {
                        try {
                            return Bitmap.createScaledBitmap(bmp, 300, 300, false);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .build();
    //    ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {


            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }


        });

    }

    public static void goToNextFragmentIsAddToBackStack(FragmentManager manager, Fragment fragment, boolean isAddToBackStack, boolean isAddAnimation){

        try{

        //    FragmentManager manager = activity.getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction(); //beginTransaction();
            if(isAddAnimation){
                transaction.setCustomAnimations(R.anim.slide_in_left,R.anim.slide_out_right,R.anim.slide_in_left,R.anim.slide_out_right);
            }

            transaction.replace(R.id.content_frame, fragment, fragment.getClass().toString());


            if (isAddToBackStack) {
                transaction.addToBackStack(fragment.getClass().toString());
                getFragmentStack().push(fragment);
            }

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            Crashlytics.logException(e);
        }

    }

    public static boolean isFragmentInBackstack(final FragmentManager fragmentManager, final String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return true;
            }
        }
        return false;
    }


    public static long dayDifference(String startDay, String endDate){
        Date date1 = getDateFromServer(startDay);
        Date date2 = getDateFromServer(endDate);
        long timeOne = date1.getTime();
        long timeTwo = date2.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = (timeTwo - timeOne) / oneDay;
        return delta;
    }

  /*  public static long getDateDiff(String strDate1, String strDate2)
    {

        Date date1 = getDateFromServer(strDate1);
        Date date2 = getDateFromServer(strDate2);
        long timeOne = date1.getTime();
        long timeTwo = date2.getTime();
        long oneDay = 1000 * 60 * 60 * 24;

        long delta = (timeTwo - timeOne) / oneDay;
        return delta+1;

    }*/




    public static void removeAllFragments(FragmentManager manager){
        Stack<Fragment> fragments = getFragmentStack();
        if (fragments == null) {
           return;
        }
        for (Fragment fragment:fragments){
            // To save any of the fragments, add this check.
            // A tag can be added as a third parameter to the fragment when you commit it
//            if (fragment.getTag().equals(fragment.getTag())) {
//                continue;
//            }

            manager.beginTransaction().remove(fragment).commit();
        }
        setFragmentsClear();
    }

    private static void setFragmentsClear(){
        fragmentStack.clear();
    }



    public static boolean clearBackStackAndGoToNextFragment(FragmentManager manager) {

        if(getFragmentStack().size() >= 2){
            FragmentTransaction fragmentTransaction =  manager.beginTransaction();

            fragmentTransaction.hide(getFragmentStack().pop());
            //fragmentStack.pop();

            Fragment fragmentTemp = getFragmentStack().lastElement();
            goToNextFragmentIsAddToBackStack(manager,fragmentTemp,false,false);
            return true;
        }


        //TODO change the action bar relative to current fragment
        return false;
    }



    public static String loadJSONFromAsset(String fileName, Activity activity) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public static void showLoader(boolean isShowLoader, Context mContext, String loading) {
        progressDialog = new ProgressDialog(mContext);
        isShowLoader = false;
        if (isShowLoader) {
            try {

                if (progressDialog != null && !progressDialog.isShowing()) {

                    progressDialog = ProgressDialog.show(mContext, "", loading);
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeLoader() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public static Stack<Fragment> getFragmentStack() {
        return fragmentStack;
    }

    public static void setFragmentStack(Stack<Fragment> fragmentStack) {
        HGBUtility.fragmentStack = fragmentStack;
    }

    public Bitmap GetBitmapMarker(Context mContext, int resourceId,  String mText)
    {
        try
        {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

            // set default bitmap config if none
            if(bitmapConfig == null)
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;

            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            paint.setTextSize((int) (14 * scale));
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);

            // draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(mText, 0, mText.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width())/2;
            int y = (bitmap.getHeight() + bounds.height())/2;

            canvas.drawText(mText, x * scale, y * scale, paint);

            return bitmap;

        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static String parseDateToServertime(String time) {

        String inputPattern = "dd/MM/yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);

        String str = null;

        try {

            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            Date date = null;
            try {
                date = inputFormat.parse(time);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String parseDateToEEEMMMDyy(String time) {

        String outputPattern = "EEE, MMM d, yy";//"EEE,MM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }




    public static String parseDateFromddMMyyyyToddmmYYYY(String time) {
        String outputPattern = "EEE, MMM dd, yyyy";
        String inputPattern = "MM/dd/yyyy";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        String str = null;

            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            Date date = null;
            try {
                date = inputFormat.parse(time);
                str = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return str;
        }

    public static String parseDateToddMMyyyyMyTrip(String time) {
        String outputPattern = "MMM dd,yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);

            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String addDayTo(String sourceDate){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = format.parse(sourceDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.DATE, 1); //minus number would decrement the days
        myDate = cal.getTime();


        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

// Using DateFormat format method we can create a string
// representation of a date with the defined format.
        String reportDate = df.format(myDate);


       return reportDate;
        //DateUtil.addDays(myDate, 1);

    }


/*    public static String addDayHourToDate(String dateToIncr) {
        String newDate="";
        String outputPattern = "EEE, MMM d, yy";
        DateFormat df = new SimpleDateFormat(outputPattern);

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + 23*3600*1000);

            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }*/


    public static String addDayHourToDate(String dateToIncr) {
        String newDate="";
        String outputPattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(outputPattern);

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + 23*3600*1000);

            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }



/*    public static String addDayHourToDate(String dateToIncr) {
        String newDate="";
        DateFormat df = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);

        SimpleDateFormat outputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + 23*3600*1000);

            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }*/



    public static String addDayToDate(String dateToIncr) {
        String newDate="";
        String outputPattern = "MM/dd/yyyy";
        DateFormat df = new SimpleDateFormat(outputPattern);

        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + (24 * 60 * 60 * 1000));
            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }


   /* public static String addDayToDate(String dateToIncr) {
        String newDate="";
        DateFormat df = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);

        SimpleDateFormat outputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        Date date ;
        try {
            date = df.parse(dateToIncr);
            Date dayAfter = new Date(date.getTime() + (24 * 60 * 60 * 1000));
            newDate = outputFormat.format(dayAfter);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return newDate;
    }*/


    public static String parseDateToddMMyyyy(String time) {

        String outputPattern = "MM/dd/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String parseDateToddMMyyyyForPayment(String time) {

        String outputPattern = "MM/dd/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String parseDateToHHmm(String time) {

        String outputPattern = "HH:mm a";
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = getDateFromServer(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String getDateDiffString(String strDate1, String strDate2)
    {

        Date date1 = getDateFromServer(strDate1);
        Date date2 = getDateFromServer(strDate2);
        long timeOne = date1.getTime();
        long timeTwo = date2.getTime();
        long oneDay = 1000 * 60 * 60 * 24;
        long delta = (timeTwo - timeOne) / oneDay;

        if (delta > 0) {
            return delta + " Nights";
        }
        else {
            delta *= -1;
            return delta + " Nights";
        }
    }





    public static Date getDateFromServer(String time){
        SimpleDateFormat inputFormat = new SimpleDateFormat(BASE_SERVER_TIME_ZONE);
        Date date = null;
        try {
            date = inputFormat.parse(time);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    public static Bitmap getMarkerBitmap(Context context, String text, int resource) {
        View markerHotelView = ((LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        TextView numTxt = (TextView) markerHotelView.findViewById(R.id.num_txt);
        numTxt.setBackgroundResource(resource);
        numTxt.setText("$" + text);

        return createDrawableFromView(context, markerHotelView);
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);

        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }


public static String formattDateToStringMonthDate(String dateInString) {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    Date startDate;
    try {
        startDate = df.parse(dateInString);
        Calendar mydate = new GregorianCalendar();
        mydate.setTime(startDate);
        String strDate= mydate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + ":" + mydate.get(Calendar.DATE);
        return strDate;

    } catch (ParseException e) {
        e.printStackTrace();
    }
    return null;

}

    public static void hideKeyboard(Context context,View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Context context,View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

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

    public static boolean isUserDataValid(UserDataVO user){
        boolean isValid = true;
        if(user.getFirstname().length()== 0){
            return false;
        }

        if(user.getLastname().length()== 0){
            return false;
        }

        if(user.getCity().length()== 0){
            return false;
        }

        if(user.getState().length()== 0){
            return false;
        }
        if(user.getCountry().length()== 0){
            return false;
        }
        //todo waiting on server bug to enable
//        if(user.getPostalcode().length()== 0){
//            return false;
//        }
//                if(user.getPhone().length()== 0){
//            return false;
//        }


        return isValid;
    }

    public static void showPikerDialogEditText(final FontEditTextView textView, Activity activity, String title,
                                       final String[] titleArray, int minValue, int maxValue, final PopUpAlertStringCB
                                               alertCB, boolean isNegativeButton) {

        View v1 = activity.getLayoutInflater().inflate(R.layout.picker_dialog, null);

        final NumberPicker genderPicker = (NumberPicker) v1.findViewById(R.id.np);
        genderPicker.setMinValue(minValue);
        genderPicker.setWrapSelectorWheel(false);
        genderPicker.setMaxValue(maxValue);
        genderPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        genderPicker.setDisplayedValues(titleArray);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(v1);
        builder.setTitle(title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(textView != null) { //can be null if i don't want to show anything like in cnc fragment
                    textView.setText(titleArray[genderPicker.getValue()]);
                    textView.setTag(genderPicker.getValue());
                }
                if(alertCB != null) {
                    alertCB.itemSelected(titleArray[genderPicker.getValue()]);
                }
                return;
            }
        });
        if(isNegativeButton) {
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.show();

    }



    public static void showPikerDialog(final int centerValue, final FontTextView textView, Activity activity, String title,
                                 final String[] titleArray, int minValue, int maxValue, final PopUpAlertStringCB
                                               alertCB, boolean isNegativeButton) {

        View v1 = activity.getLayoutInflater().inflate(R.layout.picker_dialog, null);

        final NumberPicker genderPicker = (NumberPicker) v1.findViewById(R.id.np);
        genderPicker.setMinValue(minValue);
        genderPicker.setWrapSelectorWheel(false);
        genderPicker.setMaxValue(maxValue);
        genderPicker.setValue(centerValue);

        genderPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        genderPicker.setDisplayedValues(titleArray);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(v1);
        builder.setTitle(title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(textView != null) { //can be null if i don't want to show anything like in cnc fragment
                    textView.setText(titleArray[genderPicker.getValue()]);
                    textView.setTag(genderPicker.getValue());
                }
                if(alertCB != null) {
                    alertCB.itemSelected(titleArray[genderPicker.getValue()]);
                }
                return;
            }
        });
        if(isNegativeButton) {
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

    }


    private static void clearText(final EditText[] input){
        if(input == null){
            return;
        }
        int arrLength = input.length;
        for(int i=0;i<arrLength;i++){
            input[i].setText("");
        }
    }

    public static void showAlertPopAddCompanion(final Activity activity, final EditText[] input, final View popupView ,
                                                final String popupTitle, final PopUpAlertStringCB
                                              alertCB){
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);



        alert.setCancelable(false);
        alert.setTitle(popupTitle)
                .setView(popupView)

                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int arrLength = 0;
                        if(input != null){
                            arrLength = input.length;
                        }
                        String newName="";
                        for(int i=0;i<arrLength;i++){
                            newName = newName + input[i].getText().toString() + "&";
                            input[i].setText("");
                        }
                        if(alertCB != null){
                            alertCB.itemSelected(newName);
                        }

                        clearText(input);
                        ((ViewGroup) popupView.getParent()).removeView(popupView);
                        IBinder token = input[0].getWindowToken();
                        ( (InputMethodManager) activity.getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                        dialog.cancel();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       // input.setText("");
                        clearText(input);
                        ((ViewGroup) popupView.getParent()).removeView(popupView);
                        IBinder token = input[0].getWindowToken();
                        ( (InputMethodManager) activity.getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                        dialog.cancel();
                    }
                }).create();


        final AlertDialog dialogAlert = alert.create();
        dialogAlert.show();
        dialogAlert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        input[2].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    // Disable ok button
                    ((AlertDialog) dialogAlert).getButton(
                            AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    // Something into edit text. Enable the button.
                    ((AlertDialog) dialogAlert).getButton(
                            AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }

            }
        });

    }


    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

  public static void showAlertPopUp(final Activity activity, final EditText input, final View popupView ,
                                    final String popupTitle, String positiveButton, final PopUpAlertStringCB
                                    alertCB){
      final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
      alert.setCancelable(false);
      alert.setTitle(popupTitle)
              .setView(popupView)
              .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                  public void onClick(DialogInterface dialog, int which) {
                      String newName ="";
                    if(input != null){
                        newName = input.getText().toString();
                        input.setText("");
                        IBinder token = input.getWindowToken();
                        ( (InputMethodManager) activity.getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                    }
                      if(alertCB != null){
                          alertCB.itemSelected(newName);
                      }
                      ((ViewGroup) popupView.getParent()).removeView(popupView);

                      dialog.cancel();
                  }
              })
              .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    if(input != null) {
                        input.setText("");
                        IBinder token = input.getWindowToken();
                        ( (InputMethodManager) activity.getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                    }
                      if(alertCB != null){
                          alertCB.itemCanceled();
                      }
                      ((ViewGroup) popupView.getParent()).removeView(popupView);
                      dialog.cancel();
                  }
              })
              .create().show();

  }


    public static void showAlertPopUpOneButton(final Activity activity, final EditText input, final View popupView ,
                                      final String popupTitle, final PopUpAlertStringCB
                                              alertCB){
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);
        alert.setTitle(popupTitle)
                .setView(popupView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = "";
                        if(input != null) {
                             newName = input.getText().toString();
                            input.setText("");
                            IBinder token = input.getWindowToken();
                            ( (InputMethodManager) activity.getSystemService( Context.INPUT_METHOD_SERVICE ) ).hideSoftInputFromWindow( token, 0 );
                        }
                        if(alertCB != null){
                            alertCB.itemSelected(newName);
                        }
//                        if (newName.length() != 0) {
//                            popUpConnection(newName);
//                        }

                        ((ViewGroup) popupView.getParent()).removeView(popupView);

                        dialog.cancel();
                    }
                })

                .create().show();
    }


    public static void showDateDialog(Activity activity, final FontEditTextView textView) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        textView.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public static void showDateDialog(Activity activity, final FontTextView textView) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(activity,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        textView.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
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


    public static final boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static String  createFolder(String folderName){
        String extFolName = Environment.getExternalStorageDirectory().toString();
        File f = null;
        String path = "";
        f = new File(extFolName + "/" + folderName + "/");
        if (f.exists()) {
            path = f.getAbsolutePath();
            return path;
        } else {
            if (f.mkdir()) {
                path = f.getAbsolutePath();
                return path;
            }
        }
        return path;
    }




/*    public static void checkPermissionsIfGranted(Activity activity){
        if(!checkPermission(activity.getApplicationContext())) {
            requestPermission(activity);
        }

    }
    public static final int PERMISSION_REQUEST_CODE = 1;

    private static boolean checkPermission(Context context){
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){

            return true;

        } else {

            return false;

        }
    }

    private static void requestPermission(Activity activity){

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_FINE_LOCATION)){

            Toast.makeText(activity,"GPS permission allows us to access location data. Please allow in App Settings for additional functionality.",Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_REQUEST_CODE);
        }
    }*/



    public static boolean isCameraPermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity,android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                // Log.v(TAG,"Permission is granted");
                return true;
            } else {

                //   Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            //    Log.v(TAG,"Permission is granted");
            return true;
        }

    }


    public static boolean isStoragePermissionGranted(Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
               // Log.v(TAG,"Permission is granted");
                return true;
            } else {

             //   Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
        //    Log.v(TAG,"Permission is granted");
            return true;
        }
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

    public static void setCCIcon(ImageView view, String cardid) {
        if (cardid.equals("1")) {
            view.setBackgroundResource(R.drawable.master_card);

        } else if (cardid.equals("2")) {
            //TODO need DISCOVERY card
            view.setBackgroundResource(R.drawable.master_card);

        } else if (cardid.equals("3")) {
            view.setBackgroundResource(R.drawable.master_card);

        } else if (cardid.equals("4")) {
            view.setBackgroundResource(R.drawable.visa);

        }

    }




}



