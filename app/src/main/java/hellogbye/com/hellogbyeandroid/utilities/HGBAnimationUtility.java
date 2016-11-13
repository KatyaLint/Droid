package hellogbye.com.hellogbyeandroid.utilities;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import hellogbye.com.hellogbyeandroid.R;

/**
 * Created by arisprung on 11/8/16.
 */

public class HGBAnimationUtility {

    public static void FadInView(Context context,final View view){
        Animation fadeIn = AnimationUtils.loadAnimation(context,
                R.anim.fade_in);
        view.startAnimation(fadeIn);
//        fadeIn.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                view.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

    }

    public static void FadeOutView(Context context,final View view){
        Animation fadeout = AnimationUtils.loadAnimation(context,
                R.anim.fade_out);
        view.startAnimation(fadeout);

//        fadeout.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                view.setVisibility(View.GONE);
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
    }
}
