package hellogbye.com.hellogbyeandroid.activities;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.vo.UserSignUpDataVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBAnimationUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static hellogbye.com.hellogbyeandroid.R.id.pin_code_verification_layout;
import static hellogbye.com.hellogbyeandroid.R.id.sign_up_layout_ll;

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
    private FontEditTextView mStateProvince;
    private AutoCompleteTextView mCity;
    private FontEditTextView mZip;
    private FontButtonView mCreateAccount;
    private FontButtonView mTryNow;
    private FontButtonView mCreate;
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

    private final int NUMBER_OF_STAGES = 5;

    private int CURRENT_STATE = 0;
    private final int WELCOME_STATE = 1;
    private final int NAME_STATE = 2;
    private final int EMAIL_STATE = 3;
    private final int PASSWORD_STATE = 4;
    private final int ADDRESS_STATE = 5;

    private HGBPreferencesManager hgbPrefrenceManager;
    private CountDownTimer countDownTimer;
    private String[] countryarray;
    private List<ProvincesItem> mProvinceItems;
    private ArrayAdapter adapter;
    private UserSignUpDataVO userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_layout);
        initView();
        userData = new UserSignUpDataVO();
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        countDownTimer = new AnimationCountDownTimer(5000, 1000);
        animateWelcomeView();
    }

    private void initView() {
        mPlane = (ImageView) findViewById(R.id.airplane_01);
        mPlane2 = (ImageView) findViewById(R.id.airplane_02);
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
        mBirds = (ImageView) findViewById(R.id.birds);
        mUSCheck = (ImageView) findViewById(R.id.us_check);
        mCanadaCheck = (ImageView) findViewById(R.id.canada_check);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mSignInTextView = (FontTextView) findViewById(R.id.sign_in);
        mLabel1 = (FontTextView) findViewById(R.id.label_1);
        mLabel2 = (FontTextView) findViewById(R.id.label_2);
        mWelcomeTextView = (FontTextView) findViewById(R.id.welcome);
        mCanadaText = (FontTextView) findViewById(R.id.canada);
        mUSText = (FontTextView) findViewById(R.id.usa);
        mEmail = (FontEditTextView) findViewById(R.id.email);
        mHyperlink = (FontTextView) findViewById(R.id.sign_up_hyperlink);
        mCreateAccount = (FontButtonView) findViewById(R.id.create_account);
        mCreate = (FontButtonView) findViewById(R.id.create_user);
        mTryNow = (FontButtonView) findViewById(R.id.try_now);
        mFirstName = (FontEditTextView) findViewById(R.id.first_name);
        mLastName = (FontEditTextView) findViewById(R.id.last_name);
        mPassword1 = (FontEditTextView) findViewById(R.id.password1);
        mPassword2 = (FontEditTextView) findViewById(R.id.password2);
        mCity = (AutoCompleteTextView) findViewById(R.id.city);
        mZip = (FontEditTextView) findViewById(R.id.zip);
        mStateProvince = (FontEditTextView) findViewById(R.id.state_province);

        mCanadaLayout = (LinearLayout) findViewById(R.id.canada_layout);
        mAddressLayout = (LinearLayout) findViewById(R.id.country_layout);
        mRoot = (RelativeLayout) findViewById(R.id.create_account_root);
        mUSLayout = (LinearLayout) findViewById(R.id.us_layout);


        editTextViewListners();
        mUSLayout.setOnClickListener(this);
        mCanadaLayout.setOnClickListener(this);
        mCreateAccount.setOnClickListener(this);
        mStateProvince.setOnClickListener(this);
        mArrowBack.setOnClickListener(this);
        mCreate.setOnClickListener(this);
        mTryNow.setOnClickListener(this);
        mSignInTextView.setOnClickListener(this);
        initSpannableText();
    }

    private void editTextViewListners() {
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
            animateEmailView(true);
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

    }

    private void animateNameView(boolean animateFoward) {
        disableScreen();
        if(animateFoward){

        }else{

        }

        ArrayList<View> secondViewViews = new ArrayList<>();
        secondViewViews.add(mLabel2);
        secondViewViews.add(mLabel1);
        secondViewViews.add(mFirstName);
        secondViewViews.add(mLastName);
        secondViewViews.add(mArrowBack);

        ArrayList<View> firstViewViews = new ArrayList<>();
        firstViewViews.add(mWelcomeTextView);
        firstViewViews.add(mLogoOnBoarding);
        firstViewViews.add(mCreateAccount);
        firstViewViews.add(mTryNow);
        mLabel1.setText("Tell us your name");
        mLabel2.setText("Your name will allow you to book travel on our platform");

        if(animateFoward){
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(),firstViewViews,secondViewViews);
            mSignInTextView.setText("Next");
            CURRENT_STATE = NAME_STATE;

        }else{
            mSignInTextView.setText("Login");
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(),secondViewViews,firstViewViews);
            CURRENT_STATE = WELCOME_STATE;
        }
        updateProgressBar();
        animateFirstToSecondStaticViews(animateFoward);
    }

    private void animateEmailView(boolean animateFoward) {
        disableScreen();

        ArrayList<View> firstViewViews = new ArrayList<>();
        firstViewViews.add(mFirstName);
        firstViewViews.add(mLastName);


        ArrayList<View> secondViewViews = new ArrayList<>();
        secondViewViews.add(mEmail);



        if(animateFoward){
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(),firstViewViews,secondViewViews);
            animateLabels("What's your email?","We need your email to securely");
            CURRENT_STATE = EMAIL_STATE;

        }else{

            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(),secondViewViews,firstViewViews);
//            mLabel1.setText("Tell us your name");
//            mLabel2.setVisibility(View.VISIBLE);
//            mLabel2.setText("Your name will allow you to book travel on our platform");
            animateLabels("Tell us your name","Your name will allow you to book travel on our platform");
            CURRENT_STATE = NAME_STATE;
        }
        updateProgressBar();
        animateSecondToThirdStaticView(animateFoward);
    }



    private void animateAddressView() {
        disableScreen();
        CURRENT_STATE = ADDRESS_STATE;
        updateProgressBar();
        ArrayList<View> outView = new ArrayList<>();
        outView.add(mPassword1);
        outView.add(mPassword2);


        ArrayList<View> inView = new ArrayList<>();
        inView.add(mAddressLayout);


        animateLabels("We need your Address","This will be used for better targeting of your geo-location");

        HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(),outView,inView);
        animateFourthToFifthStaticViews();

    }

    private void animatePasswordView() {
        disableScreen();
        CURRENT_STATE = PASSWORD_STATE;
        updateProgressBar();

        ArrayList<View> outView = new ArrayList<>();
        outView.add(mEmail);

        ArrayList<View> inView = new ArrayList<>();
        inView.add(mPassword1);
        inView.add(mPassword2);


        animateLabels("Create your password","Please choose a password that is at least 8 characters in length");
        HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(),outView,inView);

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
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.try_now:

                break;
            case R.id.create_account:
                animateNameView(true);
                break;
            case R.id.sign_in:
                if (CURRENT_STATE != WELCOME_STATE) {
                    if (CURRENT_STATE == NAME_STATE) {
                        userData.setFirstName(mFirstName.getText().toString());
                        userData.setLastName(mFirstName.getText().toString());
                        goToEmailView(view);
                    } else if (CURRENT_STATE == EMAIL_STATE) {
                        userData.setUserEmail(mEmail.getText().toString());
                        gotoPasswordView(view);
                    } else if (CURRENT_STATE == PASSWORD_STATE) {
                        userData.setPassword(mPassword1.getText().toString());
                        userData.setConfirmPassword(mPassword2.getText().toString());
                        goToAddressView(view);
                    }

                } else {
                    //TODO Sign in
                }
                break;
            case R.id.arrow_back:

                processBackPressed();

                break;
            case R.id.create_user:
                sendSignUpDataToServer();
                break;

            case R.id.us_layout:
                setUSSelected(true);
                getStaticProvince("US");
                userData.setCountry("US");
                break;

            case R.id.canada_layout:
                setUSSelected(false);
                getStaticProvince("CA");
                userData.setCountry("CA");
                break;
            case R.id.state_province:
                userSelectedState();
                break;
        }
    }

    private void userSelectedState() {
        if (userData.getCountry() != null && mProvinceItems != null) {
            HGBUtility.showPikerDialogEditText(mStateProvince, CreateAccountActivity.this, "Choose province",
                    countryarray, 0, mProvinceItems.size() - 1, new PopUpAlertStringCB() {
                        @Override
                        public void itemSelected(String inputItem) {
                            for (ProvincesItem province : mProvinceItems) {
                                if (province.getProvincename().equals(inputItem)) {
                                    mStateProvince.setText(province.getProvincename());
                                    userData.setCountryProvince(province.getProvincecode());
                                    initAutoCityComplete();
                                    break;
                                }
                            }
                        }
                        @Override
                        public void itemCanceled() {
                        }
                    }, false);

        } else {
            Toast.makeText(getApplicationContext(), "Please select Country first", Toast.LENGTH_SHORT).show();
        }
    }

    private void processBackPressed() {
        switch (CURRENT_STATE) {
            case WELCOME_STATE:

                break;
            case NAME_STATE:
                animateNameView(false);
                break;
            case EMAIL_STATE:
                animateEmailView(false);
                break;
            case PASSWORD_STATE:

                break;
            case ADDRESS_STATE:

                break;
        }
    }

    private void setUSSelected(boolean b) {
        if (b) {
            mUSText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.COLOR_00516f));
            mCanadaText.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.COLOR_999999));
            mCanadaCheck.setVisibility(View.GONE);
            mUSCheck.setVisibility(View.VISIBLE);
        } else {
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
        mProgressBar.setProgress((100 / NUMBER_OF_STAGES) * CURRENT_STATE);
    }


    private void animateFirstToSecondStaticViews(boolean animateFoward) {
        if(animateFoward){
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
        }else{

            mPlane.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane01_2_1));//
            mCloud01.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01_2_1));
            mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_2_1));
            mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_2_1));
            mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_2_1));
            mCloud03.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud03_2_1));
            mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_2_1));
            mCloud04B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud4b_2_1));
            mCloud05.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud05_2_1));
            mCloud07.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud07_2_1));
            mBiulding1.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding01_2_1));
            mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_2_1));
        }

    }

    private void animateSecondToThirdStaticView(boolean animateFoward) {
        if(animateFoward){
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
        }else{
            mPlane2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane02_3_2));
            mCloud01.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01_3_2));
            mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_3_2));
            mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_3_2));
            mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_3_2));
            mCloud03.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud03_3_2));
            mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_3_2));
            mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_3_2));
            mCloud07.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud07_3_2));
            mCloud05.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud05_3_2));
            mCloud06.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud06_3_2));
        }

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

    private void animateFourthToFifthStaticViews() {
        mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_4_5));
        HGBAnimationUtility.FadeOutView(getApplicationContext(), mBirds);
        mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_4_5));
        mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_4_5));
        mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_4_5));
        mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_4_5));
        mCloud06.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud06_4_5));

    }

    private void getStaticProvince(String id) {
        // String countryID = userData.getCountry();
        ConnectionManager.getInstance(CreateAccountActivity.this).getStaticBookingProvince(id, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                mProvinceItems = (List<ProvincesItem>) data;
                if (mProvinceItems.size() > 0) {

                    setDropDownItems();
                }
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }

    private void setDropDownItems() {
        countryarray = new String[mProvinceItems.size()];
        for (int i = 0; i < mProvinceItems.size(); i++) {
            countryarray[i] = mProvinceItems.get(i).getProvincename();
        }
    }

    private void initAutoCityComplete() {
        mCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HGBUtility.hideKeyboard(getApplicationContext(), mCity);
                userData.setCity(mCity.getText().toString());
            }
        });

        mCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ConnectionManager.getInstance(CreateAccountActivity.this).postAutocompleteCity(charSequence.toString(), userData.getCountry(),
                        userData.getCountryProvince()
                        , new ConnectionManager.ServerRequestListener() {
                            @Override
                            public void onSuccess(Object data) {
                                adapter = new ArrayAdapter(CreateAccountActivity.this, android.R.layout.simple_list_item_1, (ArrayList<String>) data);
                                mCity.setAdapter(adapter);
                            }

                            @Override
                            public void onError(Object data) {
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }

    private void animateLabels(final String s,final String s1) {

        mLabel1.clearAnimation();
        mLabel2.clearAnimation();
        AlphaAnimation fadeoutLabel1 = new AlphaAnimation(1.0f,0.0f);
        fadeoutLabel1.setDuration(getResources().getInteger(R.integer.create_account_animation_duration)/2);
        fadeoutLabel1.setFillAfter(true);

        mLabel1.startAnimation(fadeoutLabel1);
        fadeoutLabel1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLabel1.setText(s);
                AlphaAnimation fadeinLabel1 = new AlphaAnimation(0.0f,1.0f);
                fadeinLabel1.setDuration(getResources().getInteger(R.integer.create_account_animation_duration)/2);
                fadeinLabel1.setFillAfter(true);
                mLabel1.startAnimation(fadeinLabel1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AlphaAnimation fadeoutLabel2 = new AlphaAnimation(1.0f,0.0f);
        fadeoutLabel2.setDuration(getResources().getInteger(R.integer.create_account_animation_duration)/2);
        fadeoutLabel2.setFillAfter(true);

        mLabel2.startAnimation(fadeoutLabel2);
        fadeoutLabel2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLabel2.setText(s1);
                AlphaAnimation fadeinLabel2 = new AlphaAnimation(0.0f,1.0f);
                fadeinLabel2.setDuration(getResources().getInteger(R.integer.create_account_animation_duration)/2);
                fadeinLabel2.setFillAfter(true);
                mLabel2.startAnimation(fadeinLabel2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void sendSignUpDataToServer(){

        ConnectionManager.getInstance(CreateAccountActivity.this).postUserCreateAccount(userData, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, false);
                resetPassword();
                //sign_up_layout_ll.setVisibility(View.GONE);
               // pin_code_verification_layout.setVisibility(View.VISIBLE);


            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);

            }
        });
    }


    private void resetPassword() {

        LayoutInflater li = LayoutInflater.from(CreateAccountActivity.this);
        final View promptsView = li.inflate(R.layout.popup_confirm_email_sent, null);

        HGBUtility.showAlertPopUp(CreateAccountActivity.this, null, promptsView,
                getResources().getString(R.string.popup_confirm_account_title), getResources().getString(R.string.ok_button), new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {

                    }

                    @Override
                    public void itemCanceled() {

                    }
                });
    }
}