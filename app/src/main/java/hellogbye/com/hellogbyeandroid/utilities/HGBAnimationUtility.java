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

    public static void FadInView(Context context,View view){
        Animation animSlide = AnimationUtils.loadAnimation(context,
                R.anim.fade_in);
        view.startAnimation(animSlide);

    }

    public static void FadeOutView(Context context,View view){
        Animation animSlide = AnimationUtils.loadAnimation(context,
                R.anim.fade_out);
        view.startAnimation(animSlide);
    }
}
