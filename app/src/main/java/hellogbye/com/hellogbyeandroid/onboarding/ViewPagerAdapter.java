package hellogbye.com.hellogbyeandroid.onboarding;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/10/16.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context mContext;
    private int[] mResources;
    private ArrayList<OnboardingPagerTextVO> onboardingPagerTextVOArrayList;
    private OnBoardingPager.IOnBoardingNextClickListener onBoardingNextClickListener;

    public ViewPagerAdapter(Context mContext, ArrayList<OnboardingPagerTextVO> onboardingPagerTextVOs) {
        this.mContext = mContext;
        this.onboardingPagerTextVOArrayList = onboardingPagerTextVOs;
    }

    public void setClickListener(OnBoardingPager.IOnBoardingNextClickListener onBoardingNextClickListener){
        this.onBoardingNextClickListener = onBoardingNextClickListener;
    }

    @Override
    public int getCount() {
        return onboardingPagerTextVOArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.pager_item, container, false);
        OnboardingPagerTextVO onboardingPagerTextVO =  onboardingPagerTextVOArrayList.get(position);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
        imageView.setImageResource(onboardingPagerTextVO.getImageResource());

        FontTextView onboarding_text_title = (FontTextView)itemView.findViewById(R.id.onboarding_text_title);
        onboarding_text_title.setText(onboardingPagerTextVO.getTextTitle());

        FontTextView onboarding_text_explanation = (FontTextView)itemView.findViewById(R.id.onboarding_text_explanation);
        onboarding_text_explanation.setText(onboardingPagerTextVO.getTextExplanation());

        container.addView(itemView);
        if(onboardingPagerTextVOArrayList.size() -1 == position){
           FontButtonView onboarding_next_button = (FontButtonView)itemView.findViewById(R.id.onboarding_next_button);
            onboarding_next_button.setVisibility(View.VISIBLE);
            onboarding_next_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBoardingNextClickListener.onClickNext();;

                }
            });
        }

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}