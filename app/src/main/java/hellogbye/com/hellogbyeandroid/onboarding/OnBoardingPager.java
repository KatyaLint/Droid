package hellogbye.com.hellogbyeandroid.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.BaseActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;

/**
 * Created by nyawka on 11/10/16.
 */

public class OnBoardingPager extends BaseActivity implements ViewPager.OnPageChangeListener{


   // private ImageButton btnNext, btnFinish;
    private ViewPager intro_images;
    private LinearLayout pager_indicator;
    private int dotsCount;
    private ImageView[] dots;
    private ViewPagerAdapter mAdapter;

    private ArrayList<OnboardingPagerTextVO> onboardingPagerTextVOArrayList;


    private int[] mImageResources = {
            R.drawable.illustration_just_type_or_speak,
            R.drawable.illustration_heres_how_you_search,
            R.drawable.illustration_customize_your_itinerary,
            R.drawable.illustration_make_changes_in_a_flash

    };
    private HGBPreferencesManager hgbPrefrenceManager;

/*    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setReference();

        toolbar.setVisibility(View.GONE);
    }*/

    /* @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // To make activity full screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setReference();

        toolbar.setVisibility(View.GONE);

    }*/

    private void initializeScreenData(){
        onboardingPagerTextVOArrayList = new ArrayList<>();
        onboardingPagerTextVOArrayList.add(new OnboardingPagerTextVO(R.drawable.illustration_just_type_or_speak,
                getResources().getString(R.string.onboarding_title_1),getResources().getString(R.string.onboarding_explanation_1)));

        onboardingPagerTextVOArrayList.add(new OnboardingPagerTextVO(R.drawable.illustration_heres_how_you_search,
                getResources().getString(R.string.onboarding_title_2),getResources().getString(R.string.onboarding_explanation_2)));

        onboardingPagerTextVOArrayList.add(new OnboardingPagerTextVO(R.drawable.illustration_customize_your_itinerary,
                getResources().getString(R.string.onboarding_title_3),getResources().getString(R.string.onboarding_explanation_3)));

        onboardingPagerTextVOArrayList.add(new OnboardingPagerTextVO(R.drawable.illustration_make_changes_in_a_flash,
                getResources().getString(R.string.onboarding_title_4),getResources().getString(R.string.onboarding_explanation_4)));

    }

    public interface IOnBoardingNextClickListener{
        void onClickNext();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.onboarding_indicator);

        initializeScreenData();

        intro_images = (ViewPager) findViewById(R.id.pager_introduction);
     /*   btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnFinish = (ImageButton) findViewById(R.id.btn_finish);*/

        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

  /*      btnNext.setOnClickListener(this);
        btnFinish.setOnClickListener(this);*/
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        mAdapter = new ViewPagerAdapter(OnBoardingPager.this, onboardingPagerTextVOArrayList);
        mAdapter.setClickListener(new IOnBoardingNextClickListener(){

            @Override
            public void onClickNext() {

                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.TRAVEL_PREF_ENTRY,true);

                Intent intent = new Intent(getApplicationContext(),MainActivityBottomTabs.class);
                startActivity(intent);
                finish();

            }
        });
        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();


        intro_images.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                // do transformation here

                    ImageView image = (ImageView) page.findViewById(R.id.img_pager_item);
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
                    image.startAnimation(fadeInAnimation);

            }
        });
    }


    /*  @Override
    public void setReference() {
        view = LayoutInflater.from(this).inflate(R.layout.onboarding_indicator, container);

        intro_images = (ViewPager) view.findViewById(R.id.pager_introduction);
        btnNext = (ImageButton) view.findViewById(R.id.btn_next);
        btnFinish = (ImageButton) view.findViewById(R.id.btn_finish);

        pager_indicator = (LinearLayout) view.findViewById(R.id.viewPagerCountDots);

        btnNext.setOnClickListener(this);
        btnFinish.setOnClickListener(this);


        intro_images.setAdapter(mAdapter);
        intro_images.setCurrentItem(0);
        intro_images.setOnPageChangeListener(this);
        setUiPageViewController();
    }*/

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins((int)getResources().getDimension(R.dimen.DP4), 0, (int)getResources().getDimension(R.dimen.DP4), 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselected_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

      /*  if (position + 1 == dotsCount) {
            btnNext.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.VISIBLE);
            btnFinish.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}