package hellogbye.com.hellogbyeandroid.utilities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.crashlytics.android.Crashlytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import hellogbye.com.hellogbyeandroid.R;


/**
 * Created by arisprung on 8/17/15.
 */
public class HGBUtility {

    public static void loadImage(Context context, String imageUrl, ImageView imageView) {

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

    public static void goToNextFragmentIsAddToBackStack(Activity activity, Fragment fragment, boolean isAddToBackStack){
        try{
            FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, fragment, fragment.getClass().toString());

            if (isAddToBackStack) {
                transaction.addToBackStack(fragment.getClass().toString());
            }

            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            Crashlytics.logException(e);
        }

    }

    public static void clearBackStackAndGoToNextFragment(Activity activity, Fragment fragment) {

        if (activity == null) {
            return;
        }

        activity.getFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        goToNextFragmentIsAddToBackStack(activity, fragment, true); //now we always want to add to the backstack
    }
}
