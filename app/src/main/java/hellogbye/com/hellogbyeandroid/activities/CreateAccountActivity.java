package hellogbye.com.hellogbyeandroid.activities;

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
    private ImageView mBiulding;
    private ImageView mCloud01;
    private ImageView mCloud02;
    private ImageView mCloud03;
    private ImageView mCloud04;
    private ImageView mCloud05;
    private ImageView mCloud06;
    private ImageView mCloud07;
    private ImageView mCloud08;
    private ImageView mArrowBack;


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

        mBiulding = (ImageView) findViewById(R.id.building);
        mCloud01 = (ImageView) findViewById(R.id.cloud_01);
        mCloud02 = (ImageView) findViewById(R.id.cloud_02);
        mCloud03 = (ImageView) findViewById(R.id.cloud_03);
        mCloud04 = (ImageView) findViewById(R.id.cloud_04);
        mCloud05 = (ImageView) findViewById(R.id.cloud_05);
        mCloud06 = (ImageView) findViewById(R.id.cloud_06);
        mCloud07 = (ImageView) findViewById(R.id.cloud_07);
        mCloud08 = (ImageView) findViewById(R.id.cloud_08);
        mArrowBack = (ImageView) findViewById(R.id.arrow_back);

        mSignInTextView = (FontTextView) findViewById(R.id.sign_in);
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
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_plane_welcome_screen);
        mPlane.startAnimation(animSlide);
        HGBAnimationUtility.FadInView(getApplicationContext(), mWelcomeTextView);
        HGBAnimationUtility.FadInView(getApplicationContext(), mLogoOnBoarding);
    }

    private void animateNameView() {
        CURRENT_STATE = NAME_STATE;
        mArrowBack.setVisibility(View.VISIBLE);
        HGBAnimationUtility.FadInView(getApplicationContext(), mArrowBack);
        mArrowBack.bringToFront();
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mWelcomeTextView);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mLogoOnBoarding);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mCreateAccount);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mTryNow);
        Animation out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_plane_welcome_screen_out);
        mPlane.startAnimation(out);
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
