package hellogbye.com.hellogbyeandroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import hellogbye.com.hellogbyeandroid.R;

/**
 * Created by arisprung on 8/19/15.
 */
public class HGBProgressRelativeLayout extends RelativeLayout {
    private ImageView mSpinnerImageView;
    private ImageView mSuitcaseImageView;



    public HGBProgressRelativeLayout(Context context, AttributeSet attr) {
        super(context, attr);
        mSpinnerImageView = new ImageView(context);
        mSuitcaseImageView = new ImageView(context);
        //TODO need to change good images for good fit
        mSpinnerImageView.setImageResource(R.drawable.spinner_red);
        mSuitcaseImageView.setImageResource(R.drawable.suitcase_loader);
        Animation rotation = AnimationUtils.loadAnimation(context, R.anim.loader_spinner_rotate);
        mSpinnerImageView.startAnimation(rotation);
        LayoutParams fullScreenLayout = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        fullScreenLayout.addRule(RelativeLayout.CENTER_IN_PARENT,
                RelativeLayout.TRUE);


        addView(mSpinnerImageView, fullScreenLayout);
        addView(mSuitcaseImageView,fullScreenLayout);
    }
}
