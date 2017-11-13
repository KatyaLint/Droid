package hellogbye.com.hellogbyeandroid.utilities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;


import android.content.pm.PackageManager;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.location.Criteria;
import android.os.Build;
import android.provider.Settings;
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
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TableRow;
import android.widget.TextView;

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

import java.util.Calendar;

import java.util.Stack;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;



/**
 * Created by arisprung on 8/17/15.
 */
public class HGBUtility {

    private static ProgressDialog progressDialog;
    private static Stack<Fragment> fragmentStack = new Stack<Fragment>();
    public static AlertDialog dialog;


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

    public final static boolean checkEmailIsValid(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
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
                        message = "Input/Output SignalRRrror";
                        break;
                    case DECODING_ERROR:
                        message = "Image can't be decoded";
                        break;
                    case NETWORK_DENIED:
                        message = "Downloads are denied";
                        break;
                    case OUT_OF_MEMORY:
                        message = "Out Of Memory SignalRRrror";
                        break;
                    case UNKNOWN:
                        message = "Unknown SignalRRrror";
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




    public static void loadAirplainImage(String imageUrl,final ImageView imageView) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
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


    public static void loadRoundedImage(String imageUrl,final ImageView imageView, int tempAvatar) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
               // .considerExifParams(true)
                .showImageForEmptyUri(tempAvatar)
                .cacheInMemory(true)
                .showImageOnLoading(tempAvatar)
                .showImageOnFail(tempAvatar) //R.drawable.profile_image
                .showImageForEmptyUri(tempAvatar)
                .cacheOnDisk(false)
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


            Fragment mFragment = manager.findFragmentByTag(fragment.getClass().toString());

            transaction.replace(R.id.container, fragment, fragment.getClass().toString());


        /*    if (mFragment == null ) {
                transaction.addToBackStack(fragment.getClass().toString());
                getFragmentStack().push(fragment);
            }*/


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


    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public static boolean isFragmentInBackstack(final FragmentManager fragmentManager, final String fragmentTagName) {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            if (fragmentTagName.equals(fragmentManager.getBackStackEntryAt(entry).getName())) {
                return true;
            }
        }
        return false;
    }

    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
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






    public static void hideKeyboard(Context context,View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Context context,final View view) {
        if (view != null) {
            final InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            view.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    view.requestFocus();
                    imm.showSoftInput(view, 0);
                }
            }, 100);
        }
    }



    public static boolean isUserDataValid(UserProfileVO user){
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



  public static void showAlertPopUp(final Context activity, final EditText input, final View popupView ,
                                  /*  View customTitleView, */
                                    final String popupTitle, String positiveButton, final PopUpAlertStringCB
                                    alertCB){
      final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
      alert.setCancelable(false);
      if(popupTitle != null) {
          alert.setTitle(popupTitle);
      }
      alert .setView(popupView);

     /* if(customTitleView != null) {
          alert.setCustomTitle(customTitleView);
      }*/

      alert.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
              if (input != null) {
                  input.setText("");
                  IBinder token = input.getWindowToken();
                  ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(token, 0);
              }
              if (alertCB != null) {
                  alertCB.itemCanceled();
              }
              ((ViewGroup) popupView.getParent()).removeView(popupView);
              dialog.cancel();
          }
      });

      alert.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
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
      });

      dialog = alert.create();
      dialog.setOnShowListener(new DialogInterface.OnShowListener() {

          @Override
          public void onShow(DialogInterface dialog) {
              InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
              if(input != null)
                imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);
          }
      });
      dialog.show();


      Button negative_button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

      Button positive_button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);


      if (negative_button != null) {

          negative_button.setTextColor(ContextCompat.getColor(activity, R.color.COLOR_999999));
      }
      if (positive_button != null) {
          positive_button.setTextColor(ContextCompat.getColor(activity, R.color.COLOR_EE3A3C));
      }


  }


    public static void showAlertPopUpOneButton(final Context activity, final EditText input, final View popupView ,
                                      final String popupTitle, final PopUpAlertStringCB
                                              alertCB){

        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);
        if(popupTitle != null) {
            alert.setTitle(popupTitle);
        }
        alert.setView(popupView)
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
                });



        AlertDialog dialog = alert.create();
        dialog.show();

        Button positive_button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);

        if (positive_button != null) {
            positive_button.setTextColor(ContextCompat.getColor(activity, R.color.COLOR_EE3A3C));
        }



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




    private static ImageView setPicture(ImageView view, String cardid){
        if ("1".equals(cardid)) {
            view.setBackgroundResource(R.drawable.amex_icons);

        } else if ("2".equals(cardid)) {
            view.setBackgroundResource(R.drawable.d_iscover_icon);

        } else if ("3".equals(cardid)) {
            view.setBackgroundResource(R.drawable.master_card_icon);

        } else if ("4".equals(cardid)) {
            view.setBackgroundResource(R.drawable.visa_icon);

        } else {
            view.setBackgroundResource(R.drawable.all_card_icon);
        }
        return view;
    }

    public static void setCCIcon(ImageView view, String cardid, boolean isColorful) {
        setPicture(view, cardid);

        //TODO in the future try to conver to grey image
//        ColorMatrix matrix = new ColorMatrix();
//        matrix.setSaturation(0);
//
//        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
//
//        view = setPicture(view, cardid);
//        if(!isColorful) {
//            view.setColorFilter(filter);
//        }
    }


    public static int roundNumber(double number){

        double dAbs = Math.abs(number);
        int i = (int) dAbs;
        double result = dAbs - (double) i;
        if(result<0.5){
            return number<0 ? -i : i;
        }else{
            return number<0 ? -(i+1) : i+1;
        }

    /*    int RoundedUpCost = (int) Math.ceil(number);
        return RoundedUpCost;*/
    }
    public static int getNavBarHight (Context context)
    {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}



