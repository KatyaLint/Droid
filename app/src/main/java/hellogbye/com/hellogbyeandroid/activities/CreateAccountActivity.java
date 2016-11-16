package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBAnimationUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/8/16.
 */

public class CreateAccountActivity extends Activity implements View.OnClickListener {


    private ImageView mPlane;
    private ImageView mPlane2;
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
    private ImageView mBirds;

    private ImageView mCanadaCheck;
    private ImageView mUSCheck;


    private ProgressBar mProgressBar;


    private FontTextView mLabel1;
    private FontTextView mLabel2;
    private FontTextView mSignInTextView;
    private FontTextView mWelcomeTextView;
    private FontTextView mHyperlink;
    private FontTextView mStateProvince;
    private FontEditTextView mCityProvince;
    private FontEditTextView mZip;

    private FontButtonView mCreateAccount;
    private FontButtonView mTryNow;

    private FontTextView mUSText;
    private FontTextView mCanadaText;



    private FontEditTextView mFirstName;
    private FontEditTextView mLastName;
    private FontEditTextView mEmail;
    private FontEditTextView mPassword1;
    private FontEditTextView mPassword2;

    private LinearLayout mAddressLayout;
    private LinearLayout mUSLayout;
    private LinearLayout mCanadaLayout;

    private RelativeLayout mRoot;


    private int CURRENT_STATE = 0;
    private final int WELCOME_STATE = 1;
    private final int NAME_STATE = 2;
    private final int EMAIL_STATE = 3;
    private final int PASSWORD_STATE = 4;
    private final int ADDRESS_STATE = 5;
    private final int NUMBER_STAGES = 5;
    private int mProgress;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_layout);
        initView();
        countDownTimer = new AnimationCountDownTimer(5000, 1000);
        animateWelcomeView();
    }

    private void initView() {
        mPlane = (ImageView) findViewById(R.id.airplane_01);
        mPlane2 = (ImageView) findViewById(R.id.airplane_02);
        mSun = (ImageView) findViewById(R.id.sun);
        mLogoOnBoarding = (ImageView) findViewById(R.id.logo_onboardong);

        mRoot = (RelativeLayout) findViewById(R.id.create_account_root);

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
        mBirds = (ImageView) findViewById(R.id.birds);

        mUSCheck = (ImageView) findViewById(R.id.us_check);
        mCanadaCheck= (ImageView) findViewById(R.id.canada_check);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mSignInTextView = (FontTextView) findViewById(R.id.sign_in);
        mLabel1 = (FontTextView) findViewById(R.id.label_1);
        mLabel2 = (FontTextView) findViewById(R.id.label_2);
        mWelcomeTextView = (FontTextView) findViewById(R.id.welcome);

        mCanadaText= (FontTextView) findViewById(R.id.canada);
        mUSText= (FontTextView) findViewById(R.id.usa);

        mHyperlink = (FontTextView) findViewById(R.id.sign_up_hyperlink);
        mCreateAccount = (FontButtonView) findViewById(R.id.create_account);

        mTryNow = (FontButtonView) findViewById(R.id.try_now);
        mFirstName = (FontEditTextView) findViewById(R.id.first_name);
        mLastName = (FontEditTextView) findViewById(R.id.last_name);
        mPassword1 = (FontEditTextView) findViewById(R.id.password1);
        mPassword2 = (FontEditTextView) findViewById(R.id.password2);

        mUSLayout = (LinearLayout)findViewById(R.id.us_layout);
        mCanadaLayout= (LinearLayout)findViewById(R.id.canada_layout);
        mUSLayout.setOnClickListener(this);
        mCanadaLayout.setOnClickListener(this);

        mCityProvince = (FontEditTextView) findViewById(R.id.city);
        mZip = (FontEditTextView) findViewById(R.id.zip);
        mStateProvince = (FontTextView) findViewById(R.id.state_province);


        mAddressLayout = (LinearLayout)findViewById(R.id.country_layout);


        mPassword2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    goToAddressView(textView);
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }
        });

        mLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    goToEmailView(textView);
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }
        });

        mEmail = (FontEditTextView) findViewById(R.id.email);
        mEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    gotoPasswordView(textView);
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }
        });


        mCreateAccount.setOnClickListener(this);
        mTryNow.setOnClickListener(this);
        mSignInTextView.setOnClickListener(this);
        initSpannableText();

    }

    private void goToAddressView(View textView) {
        HGBUtility.hideKeyboard(getApplicationContext(), textView);

        if (true) {
            animateAddressView();
        } else {
            Toast.makeText(getApplicationContext(), "Password not valid", Toast.LENGTH_SHORT).show();//TODO need to take care of this in UI
        }

    }


    private void goToEmailView(View textView) {
        HGBUtility.hideKeyboard(getApplicationContext(), textView);

        if (true) {
            animateEmailView();
        } else {
            Toast.makeText(getApplicationContext(), "Name not valid", Toast.LENGTH_SHORT).show();//TODO need to take care of this in UI
        }
    }

    private boolean checkNameIsValid() {

        if (mFirstName.getText().length() > 1 && mLastName.getText().length() > 1) {
            return true;
        }
        return false;
    }

    private boolean checkEmailIsValid() {
        if (mEmail.getText().length() > 1) {
            return true;
        }
        return false;
    }

    private void gotoPasswordView(View textView) {
        HGBUtility.hideKeyboard(getApplicationContext(), textView);
        if (true) {
            animatePasswordView();
        } else {
            Toast.makeText(getApplicationContext(), "Password not valid", Toast.LENGTH_SHORT).show();//TODO need to take care of this in UI
        }

    }

    private void initSpannableText() {
        SpannableString ss = new SpannableString(getResources().getString(R.string.sign_up_term_of_use));
        ss.setSpan(new myClickableSpan(1), 52, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new myClickableSpan(2), 66, 80, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mHyperlink.setText(ss);
    }

    private void animateWelcomeView() {
        CURRENT_STATE = WELCOME_STATE;
        updateProgressBar();
//        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_create_screen_1_plane);
//        mPlane.startAnimation(animSlide);
//
//        HGBAnimationUtility.FadInView(getApplicationContext(), mWelcomeTextView);
//        HGBAnimationUtility.FadInView(getApplicationContext(), mLogoOnBoarding);
    }

    private void animateNameView() {
        disableScreen();
        CURRENT_STATE = NAME_STATE;
        updateProgressBar();
        mLabel1.setVisibility(View.VISIBLE);
        mLabel2.setVisibility(View.VISIBLE);
        HGBAnimationUtility.FadInView(getApplicationContext(), mLabel2);
        HGBAnimationUtility.FadInView(getApplicationContext(), mLabel1);
        HGBAnimationUtility.FadInView(getApplicationContext(), mFirstName);
        HGBAnimationUtility.FadInView(getApplicationContext(), mLastName);

        mLabel1.setText("Tell us your name");
        mLabel2.setText("Your name will allow you to book travel on our platform");
        mFirstName.setVisibility(View.VISIBLE);
        mArrowBack.setVisibility(View.VISIBLE);
        mLastName.setVisibility(View.VISIBLE);
        mSignInTextView.setText("Next");
        mSignInTextView.bringToFront();
        HGBAnimationUtility.FadInView(getApplicationContext(), mArrowBack);
        mArrowBack.bringToFront();
        mSignInTextView.bringToFront();
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mWelcomeTextView);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mLogoOnBoarding);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mCreateAccount);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mTryNow);

        animateNameStaticViews();
    }


    private void animateEmailView() {
        disableScreen();
        CURRENT_STATE = EMAIL_STATE;
        updateProgressBar();
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mFirstName);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mLastName);
        mLastName.setFocusable(false);
        mFirstName.setFocusable(false);
        mEmail.setVisibility(View.VISIBLE);
        HGBAnimationUtility.FadInView(getApplicationContext(), mEmail);
        mLabel1.setText("What's your email?");
        mLabel2.setText("We need your email to securely  ");

        animateEmailStaticView();
    }


    private void animateAddressView() {
        disableScreen();
        CURRENT_STATE = ADDRESS_STATE;
        updateProgressBar();
        mLabel1.setText("We need your Address");
        mLabel2.setText("This will be used for better targeting of your geo-location");
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mPassword1);
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mPassword2);
        mAddressLayout.setVisibility(View.VISIBLE);
        mAddressLayout.bringToFront();
        HGBAnimationUtility.FadInView(getApplicationContext(), mAddressLayout);
        animateAddressStaticViews();

    }

    private void animatePasswordView() {
        disableScreen();
        CURRENT_STATE = PASSWORD_STATE;
        updateProgressBar();

        HGBAnimationUtility.FadeOutView(getApplicationContext(), mEmail);
        mPassword1.setVisibility(View.VISIBLE);
        mPassword2.setVisibility(View.VISIBLE);
        HGBAnimationUtility.FadInView(getApplicationContext(), mPassword1);
        HGBAnimationUtility.FadInView(getApplicationContext(), mPassword2);
        mLabel1.setText("Create your password");
        mLabel2.setText("Please choose a password that is at least 8 characters in length");

        animatePasswordStaticViews();
    }


    private void disableScreen() {
        mRoot.setClickable(true);
        countDownTimer.start();
    }

    public class AnimationCountDownTimer extends CountDownTimer {
        public AnimationCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            mRoot.setClickable(false);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // mRoot.setClickable(false);
        }
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
                if (CURRENT_STATE != WELCOME_STATE) {
                    if (CURRENT_STATE == NAME_STATE) {
                        goToEmailView(view);
                    } else if (CURRENT_STATE == EMAIL_STATE) {
                        gotoPasswordView(view);
                    } else if (CURRENT_STATE == PASSWORD_STATE) {
                        goToAddressView(view);
                    }

                } else {
                    //TODO Sign in
                }
                break;
            case R.id.arrow_back:

                break;

            case R.id.us_layout:
                setUSSelected(true);
                break;

            case R.id.canada_layout:
                setUSSelected(false);
                break;


        }
    }

    private void setUSSelected(boolean b) {

        if(b){
            mUSText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.COLOR_00516f));
            mCanadaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.COLOR_999999));
            mCanadaCheck.setVisibility(View.GONE);
            mUSCheck.setVisibility(View.VISIBLE);
        }else{
            mCanadaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.COLOR_00516f));
            mUSText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.COLOR_999999));
            mUSCheck.setVisibility(View.GONE);
            mCanadaCheck.setVisibility(View.VISIBLE);
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

    private void updateProgressBar() {
        mProgressBar.setProgress((100 / NUMBER_STAGES) * CURRENT_STATE);
    }


    private void animateNameStaticViews() {
        mPlane.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane01_1_2));//
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

    private void animateEmailStaticView() {
        mPlane2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane02_2_3));
        mCloud01.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01_2_3));
        mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_2_3));
        mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_2_3));
        mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_2_3));
        mCloud03.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud03_2_3));
        mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_2_3));
        mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_2_3));
        mCloud07.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud07_2_3));
        mCloud05.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud05_2_3));
        mCloud06.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud06_2_3));
    }

    private void animatePasswordStaticViews() {
        mBirds.setVisibility(View.VISIBLE);
        mBirds.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.birds_3_4));
        mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_3_4));
        mPlane2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane02_3_4));
        mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_3_4));
        mCloud01.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01_3_4));
        mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_3_4));
        mCloud03.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud03_3_4));
        mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_3_4));
        mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_3_4));
        mCloud06.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud06_3_4));
        mCloud05.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud05_3_4));
    }

    private void animateAddressStaticViews() {

        mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_4_5));
        HGBAnimationUtility.FadeOutView(getApplicationContext(),mBirds);
        mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_4_5));
        mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_4_5));
        mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_4_5));
        mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_4_5));
        mCloud06.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud06_4_5));

    }
//
//    private void getStaticProvince() {
//       // String countryID = userData.getCountry();
//        ConnectionManager.getInstance(CreateAccountActivity.this).getStaticBookingProvince("", new ConnectionManager.ServerRequestListener() {
//            @Override
//            public void onSuccess(Object data) {
//                List<ProvincesItem> provinceItems = (List<ProvincesItem>) data;
//                if (provinceItems.size() > 0) {
//                    setDropDownItems(provinceItems);
//                    sign_up_province_name.setVisibility(View.VISIBLE);
//                } else {
//                    sign_up_province_name.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onError(Object data) {
//                HGBErrorHelper errorHelper = new HGBErrorHelper();
//                errorHelper.setMessageForError((String) data);
//                errorHelper.show(getFragmentManager(), (String) data);
//            }
//        });
//    }
//
//
//    private void setDropDownItems(final List<ProvincesItem> provinceItems) {
//        //  final ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
//        String[] countryarray = new String[provinceItems.size()];
//        for (int i = 0; i < provinceItems.size(); i++) {
//            countryarray[i] = provinceItems.get(i).getProvincename();
//        }
//
//        HGBUtility.showPikerDialog(0, sign_up_province_name, CreateAccountActivity.this, "Choose province",
//                countryarray, 0, provinceItems.size() - 1, new PopUpAlertStringCB() {
//                    @Override
//                    public void itemSelected(String inputItem) {
//                        for (ProvincesItem province : provinceItems) {
//                            if (province.getProvincename().equals(inputItem)) {
//                                userData.setCountryProvince(province.getProvincecode());
//                                break;
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void itemCanceled() {
//
//                    }
//                }, false);
//
//    }
}
