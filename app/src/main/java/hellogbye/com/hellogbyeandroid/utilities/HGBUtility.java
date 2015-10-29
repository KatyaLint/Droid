package hellogbye.com.hellogbyeandroid.utilities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

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

import hellogbye.com.hellogbyeandroid.R;


/**
 * Created by arisprung on 8/17/15.
 */
public class HGBUtility {


    private static ProgressDialog progressDialog;

    public static void loadRoundedImage(Context context, String imageUrl, ImageView imageView) {

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
                            return getRoundedCornerBitmap(Bitmap.createScaledBitmap(bmp, HGBConstants.PROFILE_IMAGE_WIDTH, HGBConstants.PROFILE_IMAGE_HEIGHT, false), 90);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
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
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

                view.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }


        });

    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    private static Stack<Fragment> fragmentStack = new Stack<Fragment>();


    public static void goToNextFragmentIsAddToBackStack(Activity activity, Fragment fragment, boolean isAddToBackStack){

        try{
            FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment, fragment.getClass().toString());

            if (isAddToBackStack) {
                transaction.addToBackStack(fragment.getClass().toString());
                fragmentStack.push(fragment);
            }

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            Crashlytics.logException(e);
        }

    }

    public static boolean clearBackStackAndGoToNextFragment(Activity activity) {

        if(fragmentStack.size() >= 2){
            FragmentTransaction fragmentTransaction =  activity.getFragmentManager().beginTransaction();
            fragmentTransaction.hide(fragmentStack.pop());
            //fragmentStack.pop();
            Fragment fragmentTemp = fragmentStack.lastElement();
            goToNextFragmentIsAddToBackStack(activity,fragmentTemp,false);
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
//    SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM");
//
//    try {
//
//        Date date = formatter.parse(dateInString);
//
//       return formatter.format(date);
//
//    } catch (ParseException e) {
//        e.printStackTrace();
//    }
//    return null;
}



