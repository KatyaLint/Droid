package hellogbye.com.hellogbyeandroid.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBAnimationUtility;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/8/16.
 */

public class CreateAccountActivity extends Activity implements View.OnClickListener {


    private ImageView mPlane;
    private ImageView mSun;
    private ImageView mLogoOnBoarding;
    private ImageView mBiulding2;
    private ImageView mBiulding1;
    private ImageView mCloud01;
    private ImageView mCloud01B;
    private ImageView mCloud02;
    private ImageView mCloud03;
    private ImageView mCloud04;
    private ImageView mCloud04B;
    private ImageView mCloud05;
    private ImageView mCloud06;
    private ImageView mCloud07;
    private ImageView mArrowBack;


    private FontTextView mLabel1;
    private FontTextView mLabel2;
    private FontTextView mSignInTextView;
    private FontTextView mWelcomeTextView;
    private FontTextView mHyperlink;
    private FontButtonView mCreateAccount;
    private FontButtonView mTryNow;

    private int CURRENT_STATE = 0;
    private final int WELCOME_STATE = 1;
    private final int NAME_STATE = 2;
    private final int EMAIL_STATE = 3;
    private final int PASSWORD_STATE = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_layout);
        initView();

        animateWelcomeView();
    }

    private void initView() {
        mPlane = (ImageView) findViewById(R.id.airplane_01);
        mSun = (ImageView) findViewById(R.id.sun);
        mLogoOnBoarding = (ImageView) findViewById(R.id.logo_onboardong);

        mBiulding2 = (ImageView) findViewById(R.id.building_02);
        mBiulding1 = (ImageView) findViewById(R.id.building_01);
        mCloud01 = (ImageView) findViewById(R.id.cloud_01);
        mCloud01B = (ImageView) findViewById(R.id.cloud_1_b);
        mCloud02 = (ImageView) findViewById(R.id.cloud_02);
        mCloud03 = (ImageView) findViewById(R.id.cloud_03);
        mCloud04 = (ImageView) findViewById(R.id.cloud_4);
        mCloud04B = (ImageView) findViewById(R.id.cloud_4_b);
        mCloud05 = (ImageView) findViewById(R.id.cloud_5);
        mCloud06 = (ImageView) findViewById(R.id.cloud_6);
        mCloud07 = (ImageView) findViewById(R.id.cloud_7);
        mArrowBack = (ImageView) findViewById(R.id.arrow_back);

        mSignInTextView = (FontTextView) findViewById(R.id.sign_in);
        mLabel1 = (FontTextView) findViewById(R.id.label_1);
        mLabel2 = (FontTextView) findViewById(R.id.label_2);
        mWelcomeTextView = (FontTextView) findViewById(R.id.welcome);
        mHyperlink = (FontTextView) findViewById(R.id.sign_up_hyperlink);
        mCreateAccount = (FontButtonView) findViewById(R.id.create_account);
        mTryNow = (FontButtonView) findViewById(R.id.try_now);



        mCreateAccount.setOnClickListener(this);
        mTryNow.setOnClickListener(this);
        initSpannableText();

    }

    private void initSpannableText() {
        SpannableString ss = new SpannableString(getResources().getString(R.string.sign_up_term_of_use));
        ss.setSpan(new myClickableSpan(1), 52, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new myClickableSpan(2), 66, 80, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mHyperlink.setText(ss);
    }

    private void animateWelcomeView() {
        CURRENT_STATE = WELCOME_STATE;
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_create_screen_1_plane);
        mPlane.startAnimation(animSlide);

        HGBAnimationUtility.FadInView(getApplicationContext(), mWelcomeTextView);
        HGBAnimationUtility.FadInView(getApplicationContext(), mLogoOnBoarding);
    }

    private void animateNameView() {
        CURRENT_STATE = NAME_STATE;
        mLabel1.setVisibility(View.VISIBLE);
        mLabel2.setVisibility(View.VISIBLE);
        HGBAnimationUtility.FadInView(getApplicationContext(), mLabel2);
        HGBAnimationUtility.FadInView(getApplicationContext(), mLabel1);
        mLabel1.setText("Tell us your name");
        mLabel2.setText("Your name will allow you to book travel on our platform");
        mArrowBack.setVisibility(View.VISIBLE);
        mSignInTextView.setText("Next");
        HGBAnimationUtility.FadInView(getApplicationContext(), mArrowBack);
        mArrowBack.bringToFront();

        HGBAnimationUtility.FadeOutView(getApplicationContext(), mWelcomeTextView);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mLogoOnBoarding);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mCreateAccount);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mTryNow);
        mPlane.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_create_screen_2_plane));
        mCloud01.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01_1_2));
        mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_1_2));
        mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_1_2));
        mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_1_2));
        mCloud03.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud03_1_2));
        mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_1_2));
        mCloud04B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud4b_1_2));
        mCloud05.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud05_1_2));
        mCloud07.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud07_1_2));
        mBiulding1.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding01_1_2));
        mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_1_2));

    }

    private void animateEmailView() {

    }

    private void animatePasswordView() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.try_now:

                break;
            case R.id.create_account:
                animateNameView();
                break;
            case R.id.sign_in:
                if(CURRENT_STATE != WELCOME_STATE ){
                    if(CURRENT_STATE== NAME_STATE){
                        animateEmailView();
                    }else if(CURRENT_STATE == EMAIL_STATE){
                        animatePasswordView();
                    }else if(CURRENT_STATE == PASSWORD_STATE){

                    }

                }else{
                    //TODO Sign in
                }
                break;
            case R.id.arrow_back:

                break;



        }
    }

        public class myClickableSpan extends ClickableSpan {
            int pos;

            public myClickableSpan(int position) {
                this.pos = position;
            }

            @Override
            public void onClick(View widget) {
                String url = "";
                switch (pos) {
                    case 1:
                        url = getString(R.string.url_user_agreement);
                        break;
                    case 2:
                        url = getString(R.string.url_pp);
                        break;

                }
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        }


    }
