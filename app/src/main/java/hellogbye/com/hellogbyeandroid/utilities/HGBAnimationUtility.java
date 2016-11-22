package hellogbye.com.hellogbyeandroid.utilities;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;

import static android.media.CamcorderProfile.get;

/**
 * Created by arisprung on 11/8/16.
 */

public class HGBAnimationUtility {

    public static void CreateAccountDynamicViews(Context context,final ArrayList<View> outviews,
                                                 final ArrayList<View> inviews){
       final long time = context.getResources().getInteger(R.integer.create_account_animation_duration)/2;
        AlphaAnimation fadeOut = new AlphaAnimation (1.0f, 0.0f);
        fadeOut.setDuration(time);
        fadeOut.setFillAfter(true);

        for (int i = 0; i < outviews.size(); i++) {
            outviews.get(i).setClickable(false);
            outviews.get(i).setEnabled(false);
            //outviews.get(i).setFocusable(false);
            outviews.get(i).setVisibility(View.GONE);
            outviews.get(i).startAnimation(fadeOut);
            if(i==0){
                fadeOut.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        AlphaAnimation fadeIn = new AlphaAnimation (0.0f, 1.0f);
                        fadeIn.setDuration(time);
                        fadeIn.setFillAfter(true);
                        for (int i = 0; i < inviews.size(); i++) {
                            inviews.get(i).setClickable(true);
                            inviews.get(i).setFocusable(true);
                            inviews.get(i).setVisibility(View.VISIBLE);
                            inviews.get(i).bringToFront();
                            inviews.get(i).startAnimation(fadeIn);

                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        }




    }

    public static void FadInView(Context context,final View view){
        Animation fadeIn = AnimationUtils.loadAnimation(context,
                R.anim.fade_in);
        view.startAnimation(fadeIn);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public static void FadeOutView(Context context,final View view){
        Animation fadeout = AnimationUtils.loadAnimation(context,
                R.anim.fade_out);
        view.startAnimation(fadeout);

        fadeout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
