package hellogbye.com.hellogbyeandroid.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fenchtose.tooltip.Tooltip;
import com.fenchtose.tooltip.TooltipAnimation;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.BuildConfig;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.models.vo.UserSignUpDataVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.onboarding.OnBoardingPager;
import hellogbye.com.hellogbyeandroid.utilities.HGBAnimationUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityNetwork;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static android.R.attr.id;
import static android.R.attr.type;
import static com.appsee.pc.e;
import static java.lang.Long.parseLong;
import static org.jcodec.SliceType.P;

/**
 * Created by arisprung on 11/8/16.
 */

public class CreateAccountActivity extends BaseActivity implements View.OnClickListener, TextWatcher {

    private final int NUMBER_OF_STAGES = 5;
    private final int WELCOME_STATE = 1;
    private final int NAME_STATE = 2;
    private final int EMAIL_STATE = 3;
    private final int PASSWORD_STATE = 4;
    private final int ADDRESS_STATE = 5;
    private final int LOGIN_STATE = 6;
    //private ImageView mPlane;
    private ImageView mPlane1;
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
    private View mUnderlineTitle;
    private LinearLayout mBirds;
    private FontButtonView login;
    private ImageView mCanadaCheck;
    private ImageView mUSCheck;
    private ProgressBar mProgressBar;
    private FontTextView mLabel1;
    private FontTextView mLabel2;
    private FontTextView mNextTextView;
    private FontTextView mWelcomeTextView;
    private FontTextView mHyperlink;
    private FontEditTextView mLoginEmail;
    private FontEditTextView mLoginPassword;
    private FontEditTextView mStateProvince;
    private FontTextView mSignIn;
    private boolean isStateSelected= false;
    private CheckBox mHelloGbyePromotionCheckBox;
    private CheckBox mThirdPartyPromotionCheckBox;


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
    private FontTextView mTitle;
    private LinearLayout mAddressLayout;
    private LinearLayout mUSLayout;
    private LinearLayout mCanadaLayout;
    private LinearLayout mLogin;
    private RelativeLayout mRoot;
    private ImageView mBird1;
    private ImageView mBird2;
    private CheckBox mRemmeberMeCheckbox;
    private FontTextView mForgotPasswordTextView;
    private boolean remember_me;
    private int CURRENT_STATE = 0;
    private HGBPreferencesManager hgbPrefrenceManager;
    private CountDownTimer countDownTimer;
    private String[] countryarray;
    private List<ProvincesItem> mProvinceItems;
    private ArrayAdapter adapter;
    private UserSignUpDataVO userData;
    private String android_id;
    private ArrayList<String> mCityList;
    private AlertDialog levelDialog;
    private Tooltip mTooltip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_layout);
        mCityList = new ArrayList<>();
        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        android_id = Settings.Secure.getString(CreateAccountActivity.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String string = FirebaseInstanceId.getInstance().getToken();
        initView();
        userData = new UserSignUpDataVO();
        countDownTimer = new AnimationCountDownTimer(getResources().getInteger(R.integer.create_account_animation_duration), 1000);
        animateWelcomeView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkFlow();
    }

    private void initView() {
        // mPlane = (ImageView) findViewById(R.id.airplane_01);
        mPlane1 = (ImageView) findViewById(R.id.airplane_01_01);
        mPlane2 = (ImageView) findViewById(R.id.airplane_02);
        mSun = (ImageView) findViewById(R.id.sun);
        mLogoOnBoarding = (ImageView) findViewById(R.id.logo_onboardong);
        mBiulding2 = (ImageView) findViewById(R.id.building_02);
        mBiulding1 = (ImageView) findViewById(R.id.building_01);
        mCloud01 = (ImageView) findViewById(R.id.cloud_01);
        mUnderlineTitle= (View) findViewById(R.id.title_underline_1);
        mCloud01B = (ImageView) findViewById(R.id.cloud_1_b);
        mCloud02 = (ImageView) findViewById(R.id.cloud_02);
        mCloud03 = (ImageView) findViewById(R.id.cloud_03);
        mCloud04 = (ImageView) findViewById(R.id.cloud_4);
        mCloud04B = (ImageView) findViewById(R.id.cloud_4_b);
        mCloud05 = (ImageView) findViewById(R.id.cloud_5);
        mCloud06 = (ImageView) findViewById(R.id.cloud_6);
        mCloud07 = (ImageView) findViewById(R.id.cloud_7);
        mArrowBack = (ImageView) findViewById(R.id.arrow_back);
        mBirds = (LinearLayout) findViewById(R.id.birds);
        mUSCheck = (ImageView) findViewById(R.id.us_check);
        mCanadaCheck = (ImageView) findViewById(R.id.canada_check);
        mForgotPasswordTextView = (FontTextView) findViewById(R.id.forgotpassword);
        mBird1 = (ImageView) findViewById(R.id.bird_1);
        mBird2 = (ImageView) findViewById(R.id.bird_2);
        mRemmeberMeCheckbox = (CheckBox) findViewById(R.id.remmember_me_checkbox);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mNextTextView = (FontTextView) findViewById(R.id.sign_in);
        mLabel1 = (FontTextView) findViewById(R.id.label_1);
        mLabel2 = (FontTextView) findViewById(R.id.label_2);
        mWelcomeTextView = (FontTextView) findViewById(R.id.welcome);
        mCanadaText = (FontTextView) findViewById(R.id.canada);
        mUSText = (FontTextView) findViewById(R.id.usa);
        mEmail = (FontEditTextView) findViewById(R.id.email);
        mHyperlink = (FontTextView) findViewById(R.id.sign_up_hyperlink);
        mCreateAccount = (FontButtonView) findViewById(R.id.create_account);
        mCreate = (FontButtonView) findViewById(R.id.create_user);
        mTryNow = (FontButtonView) findViewById(R.id.try_now_new_create);
        mFirstName = (FontEditTextView) findViewById(R.id.first_name);
        mLastName = (FontEditTextView) findViewById(R.id.last_name);
        mPassword1 = (FontEditTextView) findViewById(R.id.password1);
        mPassword2 = (FontEditTextView) findViewById(R.id.password2);
        mSignIn = (FontTextView) findViewById(R.id.create_login);
        mTitle = (FontTextView) findViewById(R.id.user_title);
        mCity = (AutoCompleteTextView) findViewById(R.id.city);
        mCity.setThreshold(1);
        mZip = (FontEditTextView) findViewById(R.id.zip);
        mStateProvince = (FontEditTextView) findViewById(R.id.state_province);
        mLogin = (LinearLayout) findViewById(R.id.login);
        mCanadaLayout = (LinearLayout) findViewById(R.id.canada_layout);
        mAddressLayout = (LinearLayout) findViewById(R.id.country_layout);
        mRoot = (RelativeLayout) findViewById(R.id.create_account_disable_while_animating);
        mUSLayout = (LinearLayout) findViewById(R.id.us_layout);
        mLoginEmail = (FontEditTextView) findViewById(R.id.username);
        mLoginPassword = (FontEditTextView) findViewById(R.id.login_password);
        login = (FontButtonView) findViewById(R.id.login_button);
        mHelloGbyePromotionCheckBox = (CheckBox)findViewById(R.id.promotion_checkbox);
        mThirdPartyPromotionCheckBox = (CheckBox)findViewById(R.id.special_offer_checkbox);



        mEmail.addTextChangedListener(this);
        mFirstName.addTextChangedListener(this);
        mLastName.addTextChangedListener(this);
        mPassword1.addTextChangedListener(this);
        mPassword2.addTextChangedListener(this);


        editTextViewListners();
        mUSLayout.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        mCanadaLayout.setOnClickListener(this);
        mCreateAccount.setOnClickListener(this);
        mStateProvince.setOnClickListener(this);
        mArrowBack.setOnClickListener(this);
        mCreate.setOnClickListener(this);
        mTryNow.setOnClickListener(this);
        mNextTextView.setOnClickListener(this);
        mNextTextView.setClickable(BuildConfig.IS_DEV);
        initSpannableText();


        //FOR BOTTOM MARGIN OF NAVAGATION BUTTONS
        int i = HGBUtility.getNavBarHight(getApplicationContext());
        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        llp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        llp.setMargins(0, 0, 0, i);
        mHyperlink.setLayoutParams(llp);



        mPassword1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(b){
                    showToolTip();
                }

            }
        });

        mPassword1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (mPassword1.getRight() - mPassword1.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        showToolTip();
                        return true;
                    }
                }
                return false;
            }
        });


        mForgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendLoginToServer();



            }
        });
    }

    private void sendLoginToServer() {
        try{
//            int permissionCheck = ContextCompat.checkSelfPermission(CreateAccountActivity.this, Manifest.permission.READ_PHONE_STATE);
//
//            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(CreateAccountActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 002);
//                return;
//            } else {
//                //TODO
//            }
           // TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.REMMEMBER_ME, mRemmeberMeCheckbox.isChecked());
           // String strUdid = tm.getDeviceId();
          //  long udid = Long.valueOf(strUdid);
            ConnectionManager.getInstance(CreateAccountActivity.this).login(mLoginEmail.getText().toString(), mLoginPassword.getText().toString(),android_id.hashCode(),
                    new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                            UserLoginCredentials user = (UserLoginCredentials) data;
                            hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_USER_IS_LOGIN_IN_PAST, true);
                            hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
                            hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, user.getUserprofileid());

                            hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, mLoginEmail.getText().toString());
                            hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_PSWD, mLoginPassword.getText().toString());
                            hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, false);
                            goToMainActivity();
                        }

                        @Override
                        public void onError(Object data) {
                            HGBErrorHelper errorHelper = new HGBErrorHelper();
                            errorHelper.setMessageForError((String) data);
                            errorHelper.show(getFragmentManager(), (String) data);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void editTextViewListners() {
        mPassword2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    userData.setPassword(mPassword1.getText().toString());
                    userData.setConfirmPassword(mPassword2.getText().toString());
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
                    userData.setFirstName(mFirstName.getText().toString());
                    userData.setLastName(mLastName.getText().toString());

                    goToEmailView();
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
                    userData.setUserEmail(mEmail.getText().toString());

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
        if (true) {//TODO checkPasswordValid()
            animateAddressView(true);
        } else {
            Toast.makeText(getApplicationContext(), "Address not valid", Toast.LENGTH_SHORT).show();//TODO need to take care of this in UI
        }
    }

    private void goToEmailView() {
        HGBUtility.hideKeyboard(getApplicationContext(), mEmail);
        if (true) {//checkNameIsValid()
            animateEmailView(true);
        } else {
            Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT).show();//TODO need to take care of this in UI
        }
    }

    private boolean checkNameIsValid() {
        if (mFirstName.length() > 0 && mLastName.length() > 0) {
            return true;
        }
        return false;
    }

    private boolean checkPasswordValid() {
        if (mPassword1.getText().length() > 1 && mPassword2.getText().length() > 1) {
            if (mPassword1.getText().toString().equalsIgnoreCase(mPassword2.getText().toString())) {

                if (mPassword1.getText().toString().matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,12}$")) {
                    return true;
                } else {
                 //   Toast.makeText(getApplicationContext(), "Password must contain 8-12 characters, with at least one number, one uppercase letter, one lowercase letter", Toast.LENGTH_SHORT).show();//TODO need to take care of this in UI
                    return false;
                }

            } else {
               // Toast.makeText(getApplicationContext(), "Passwords dont match", Toast.LENGTH_SHORT).show();//TODO need to take care of this in UI
                return false;
            }

        } else {
            //Toast.makeText(getApplicationContext(), "Passwords not valid", Toast.LENGTH_SHORT).show();//TODO need to take care of this in UI
            return false;
        }

    }


    private void gotoPasswordView(View textView) {
        HGBUtility.hideKeyboard(getApplicationContext(), textView);
        if (true) {// TODO HGBUtility.checkEmailIsValid(mEmail.getText()()
            animatePasswordView(true);



        } else {
           // Toast.makeText(getApplicationContext(), "Email not valid", Toast.LENGTH_SHORT).show();//TODO need to take care of this in UI
        }
    }

    private void showToolTip() {
        View contentView1 = getLayoutInflater().inflate(R.layout.tooltip_layout, null);

        mTooltip = new Tooltip.Builder(getApplicationContext())
                .anchor(mPassword1, Tooltip.BOTTOM)
                .content(contentView1)
                .into((RelativeLayout)findViewById(R.id.root))
                .animate(new TooltipAnimation(TooltipAnimation.REVEAL, 500))
                .autoCancel(5000)
                .withTip(new Tooltip.Tip(75, 35, ContextCompat.getColor(getApplicationContext(), R.color.COLOR_003D4C_40_percent_opacity)))
                .show();
        mTooltip.setAlpha(0.7f);
    }

    private void initSpannableText() {
        SpannableString ss = new SpannableString(getResources().getString(R.string.sign_up_term_of_use));
        ss.setSpan(new myClickableSpan(1), 52, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new myClickableSpan(2), 66, 80, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mHyperlink.setText(ss);
        mHyperlink.setAlpha(0.4f);
        mHyperlink.setMovementMethod(LinkMovementMethod.getInstance());


        SpannableString signin = new SpannableString(getResources().getString(R.string.already_have_an_account_sign_in));
        signin.setSpan(new signInClickableSpan(1), 25, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSignIn.setText(signin);
        mSignIn.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void animateWelcomeView() {
        CURRENT_STATE = WELCOME_STATE;
        updateProgressBar();

    }

    private void animateNameView(boolean animateFoward) {
        disableScreen();

        ArrayList<View> secondViewViews = new ArrayList<>();
        secondViewViews.add(mLabel2);
        secondViewViews.add(mLabel1);
        secondViewViews.add(mFirstName);
        secondViewViews.add(mLastName);
        secondViewViews.add(mUnderlineTitle);
        secondViewViews.add(mTitle);
        secondViewViews.add(mArrowBack);

        ArrayList<View> firstViewViews = new ArrayList<>();
        firstViewViews.add(mWelcomeTextView);
        firstViewViews.add(mLogoOnBoarding);
        firstViewViews.add(mCreateAccount);
        firstViewViews.add(mTryNow);
        firstViewViews.add(mSignIn);
        mLabel1.setText(R.string.tell_name);
        mLabel2.setText(R.string.allow_book);

        if (animateFoward) {
            mSignIn.setVisibility(View.GONE);
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), firstViewViews, secondViewViews);
            mNextTextView.setVisibility(View.VISIBLE);
            mNextTextView.setText(R.string.next);
            CURRENT_STATE = NAME_STATE;

        } else {

            mNextTextView.setVisibility(View.GONE);
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), secondViewViews, firstViewViews);
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
        firstViewViews.add(mTitle);
        firstViewViews.add(mUnderlineTitle);


        ArrayList<View> secondViewViews = new ArrayList<>();
        secondViewViews.add(mEmail);

        if (animateFoward) {
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), firstViewViews, secondViewViews);
            animateLabels(getString(R.string.whats_email), getString(R.string.email_secure));
            CURRENT_STATE = EMAIL_STATE;

        } else {

            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), secondViewViews, firstViewViews);
            animateLabels(getString(R.string.tell_name), getString(R.string.allow_book));
            CURRENT_STATE = NAME_STATE;
        }
        updateProgressBar();
        animateSecondToThirdStaticView(animateFoward);
    }

    private void animatePasswordView(boolean animateFoward) {
        disableScreen();


        ArrayList<View> firstViewViews = new ArrayList<>();
        firstViewViews.add(mEmail);
        String email = mEmail.getText().toString();
        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, email);

        ArrayList<View> secondViewViews = new ArrayList<>();
        secondViewViews.add(mPassword1);
        secondViewViews.add(mPassword2);


        if (animateFoward) {
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), firstViewViews, secondViewViews);
            animateLabels(getString(R.string.create_password), getString(R.string.choose_password));
            CURRENT_STATE = PASSWORD_STATE;

        } else {

            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), secondViewViews, firstViewViews);
            animateLabels(getString(R.string.whats_email), getString(R.string.email_secure));
            CURRENT_STATE = EMAIL_STATE;
        }
        animatePasswordStaticViews(animateFoward);
        updateProgressBar();
    }

    private void animateAddressView(boolean animateFoward) {
        disableScreen();

        ArrayList<View> firstViewViews = new ArrayList<>();
        firstViewViews.add(mPassword1);
        firstViewViews.add(mPassword2);

        ArrayList<View> secondViewViews = new ArrayList<>();
        secondViewViews.add(mAddressLayout);

        if (animateFoward) {
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), firstViewViews, secondViewViews);
            animateLabels(getString(R.string.need_address), getString(R.string.used_geo_location));
            CURRENT_STATE = ADDRESS_STATE;
            mNextTextView.setVisibility(View.GONE);

        } else {
            mNextTextView.setVisibility(View.VISIBLE);
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), secondViewViews, firstViewViews);
            animateLabels(getString(R.string.create_password), getString(R.string.choose_password));
            CURRENT_STATE = PASSWORD_STATE;
        }
        animateFourthToFifthStaticViews(animateFoward);
        updateProgressBar();
    }


    private void disableScreen() {
        mRoot.setClickable(true);
        countDownTimer.start();
    }


    private void resetPassword() {
        startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.user_title:
                HGBUtility.showPikerDialog(0,mTitle, CreateAccountActivity.this, "SELECT TITLE", getResources().getStringArray(R.array.title_array), 0, 2, null, true);
                break;

            case R.id.create_account:
                animateNameView(true);
                break;
            case R.id.sign_in:
                if (CURRENT_STATE != WELCOME_STATE) {
                    if (CURRENT_STATE == NAME_STATE) {
                        userData.setFirstName(mFirstName.getText().toString());
                        userData.setLastName(mLastName.getText().toString());

                        goToEmailView();
                    } else if (CURRENT_STATE == EMAIL_STATE) {
                        userData.setUserEmail(mEmail.getText().toString());
                        gotoPasswordView(view);
                    } else if (CURRENT_STATE == PASSWORD_STATE) {
                        userData.setPassword(mPassword1.getText().toString());
                        userData.setConfirmPassword(mPassword2.getText().toString());

                        goToAddressView(view);
                    }
                    setNextEnable(false);

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
                isStateSelected = true;
                break;

            case R.id.canada_layout:
                setUSSelected(false);
                getStaticProvince("CA");
                userData.setCountry("CA");
                isStateSelected = true;
                break;
            case R.id.state_province:
                userSelectedState();
                break;


            case R.id.try_now_new_create:

                ConnectionManager.getInstance(CreateAccountActivity.this).
                        deviceAuthentication(android_id, new ConnectionManager.ServerRequestListener() {
                            @Override
                            public void onSuccess(Object data) {
                                UserLoginCredentials user = (UserLoginCredentials) data;
                                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
                                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, true);
                                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, user.getUserprofileid());
                                String freeUserEmail = "demo" + user.getUserprofileid() + "@hellogbye.com";
                                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, freeUserEmail);
//                        hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
                                goToMainActivity();

                            }

                            @Override
                            public void onError(Object data) {
                                HGBErrorHelper errorHelper = new HGBErrorHelper();
                                errorHelper.setMessageForError((String) data);
                                errorHelper.show(getFragmentManager(), (String) data);
                            }
                        });
                break;

        }
    }

    private void goToLoginState(boolean animateFoward) {

        disableScreen();

        ArrayList<View> secondViewViews = new ArrayList<>();
        secondViewViews.add(mArrowBack);
        secondViewViews.add(mLogin);

        ArrayList<View> firstViewViews = new ArrayList<>();
        firstViewViews.add(mWelcomeTextView);
        firstViewViews.add(mLogoOnBoarding);
        firstViewViews.add(mCreateAccount);
        firstViewViews.add(mTryNow);
        firstViewViews.add(mSignIn);


        if (animateFoward) {
            mSignIn.setVisibility(View.GONE);
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), firstViewViews, secondViewViews);
            mNextTextView.setText(R.string.next);

            remember_me = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.REMMEMBER_ME, false);
            String email = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, null);
            if (remember_me && email != null) {
                mLoginEmail.setText(email);
                String pswd = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_PSWD, null);
                if (pswd != null) {
                    mLoginPassword.setText(pswd);
                }
            }
            CURRENT_STATE = LOGIN_STATE;

        } else {
            mSignIn.setVisibility(View.GONE);
            mNextTextView.setText(R.string.login_);
            HGBAnimationUtility.CreateAccountDynamicViews(getApplicationContext(), secondViewViews, firstViewViews);
            HGBUtility.hideKeyboard(getApplicationContext(), mLoginEmail);
            CURRENT_STATE = WELCOME_STATE;

        }
        updateProgressBar();
        animateFirstToLoginStaticViews(animateFoward);


    }

    private void userSelectedState() {

        if (userData.getCountry() != null && mProvinceItems != null) {

            if (levelDialog == null || isStateSelected) {
                buildProvinceDialog();

            }
            levelDialog.show();
        } else {
            Toast.makeText(getApplicationContext(), "Please select Country first", Toast.LENGTH_SHORT).show();
        }

    }

    private void buildProvinceDialog() {
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                CreateAccountActivity.this, R.layout.dialog_radio, countryarray);
        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Which State/Province?");
        builder.setSingleChoiceItems(adapter, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                for (ProvincesItem province : mProvinceItems) {
                    if (countryarray[item].contains(province.getProvincename())) {
                        mStateProvince.setText(province.getProvincename());
                        userData.setCountryProvince(province.getProvincecode());
                        initAutoCityComplete();
                        break;
                    }
                }
                levelDialog.hide();
            }
        });
        levelDialog = builder.create();
    }

    private void processBackPressed() {
        switch (CURRENT_STATE) {
            case WELCOME_STATE:
                finish();
                break;
            case NAME_STATE:
                animateNameView(false);
                break;
            case EMAIL_STATE:
                animateEmailView(false);
                break;
            case PASSWORD_STATE:
                animatePasswordView(false);
                break;
            case ADDRESS_STATE:
                animateAddressView(false);
                break;
            case LOGIN_STATE:
                goToLoginState(false);
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

    private void updateProgressBar() {
        mProgressBar.setProgress((100 / NUMBER_OF_STAGES) * CURRENT_STATE);
    }

    private void animateFirstToLoginStaticViews(boolean animateFoward) {
        if (animateFoward) {
            mPlane1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane01_1_2));//
            Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane01_1_2);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    HGBUtility.showKeyboard(getApplicationContext(), mLoginEmail);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mPlane1.setAnimation(anim);
            mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding01_1_login));
            mBiulding1.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding01_1_login));
        } else {

            mPlane1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane01_2_1));//
            mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding01_login_1));
            mBiulding1.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding01_login_1));
        }

    }

    private void animateFirstToSecondStaticViews(boolean animateFoward) {
        if (animateFoward) {
            mPlane1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane01_1_2));//
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
        } else {

            mPlane1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane01_2_1));//
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
        if (animateFoward) {
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
        } else {
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

    private void animatePasswordStaticViews(boolean animateFoward) {

        if (animateFoward) {
            mBirds.setVisibility(View.VISIBLE);
            Animation set = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.birds_3_4);
            set.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {


                    mBird1.setBackgroundResource(R.drawable.animation_bird);
                    AnimationDrawable rocketAnimation1 = (AnimationDrawable) mBird1.getBackground();
                    rocketAnimation1.start();

                    mBird2.setBackgroundResource(R.drawable.animation_bird2);
                    AnimationDrawable rocketAnimation2 = (AnimationDrawable) mBird2.getBackground();
                    rocketAnimation2.start();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mBirds.setAnimation(set);
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
        } else {
            mBirds.setVisibility(View.GONE);
            Animation set = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.birds_4_3);
            mBirds.setAnimation(set);
            mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_4_3));
            mPlane2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.plane02_4_3));
            mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_4_3));
            mCloud01.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01_4_3));
            mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_4_3));
            mCloud03.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud03_4_3));
            mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_4_3));
            mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_4_3));
            mCloud06.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud06_4_3));
            mCloud05.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud05_4_3));
        }

    }

    private void animateFourthToFifthStaticViews(boolean animateFoward) {

        if (animateFoward) {
            mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_4_5));
            HGBAnimationUtility.FadeOutView(getApplicationContext(), mBirds);
            mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_4_5));
            mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_4_5));
            mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_4_5));
            mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_4_5));
            mCloud06.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud06_4_5));
        } else {
            mSun.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sun_5_4));
            HGBAnimationUtility.FadInView(getApplicationContext(), mBirds);
            mCloud02.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud02_5_4));
            mCloud01B.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud01b_5_4));
            mBiulding2.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buiding02_5_4));
            mCloud04.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud04_5_4));
            mCloud06.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.cloud06_5_4));
        }


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
            countryarray[i] = mProvinceItems.get(i).getProvincecode() + " - " + mProvinceItems.get(i).getProvincename();
        }
    }

    private void initAutoCityComplete() {
        mCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mCity.dismissDropDown();
                HGBUtility.hideKeyboard(getApplicationContext(), mCity);
                userData.setCity(mCity.getText().toString());
                mZip.requestFocus();

            }
        });

        mCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {

                ConnectionManager.getInstance(CreateAccountActivity.this).postAutocompleteCity(editable.toString(), userData.getCountry(),
                        userData.getCountryProvince()
                        , new ConnectionManager.ServerRequestListener() {
                            @Override
                            public void onSuccess(Object data) {

                                mCityList = (ArrayList<String>) data;
                                adapter = new ArrayAdapter(CreateAccountActivity.this, android.R.layout.simple_list_item_1, mCityList);
                                mCity.setAdapter(adapter);
                                if(editable.length()==1){
                                    mCity.showDropDown();
                                }

                            }

                            @Override
                            public void onError(Object data) {
                            }
                        });
            }
        });

    }

    private void animateLabels(final String s, final String s1) {
        mLabel1.clearAnimation();
        mLabel2.clearAnimation();
        AlphaAnimation fadeoutLabel1 = new AlphaAnimation(1.0f, 0.0f);
        fadeoutLabel1.setDuration(getResources().getInteger(R.integer.create_account_animation_duration) / 2);
        fadeoutLabel1.setFillAfter(true);

        mLabel1.startAnimation(fadeoutLabel1);
        fadeoutLabel1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLabel1.setText(s);
                AlphaAnimation fadeinLabel1 = new AlphaAnimation(0.0f, 1.0f);
                fadeinLabel1.setDuration(getResources().getInteger(R.integer.create_account_animation_duration) / 2);
                fadeinLabel1.setFillAfter(true);
                mLabel1.startAnimation(fadeinLabel1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AlphaAnimation fadeoutLabel2 = new AlphaAnimation(1.0f, 0.0f);
        fadeoutLabel2.setDuration(getResources().getInteger(R.integer.create_account_animation_duration) / 2);
        fadeoutLabel2.setFillAfter(true);

        mLabel2.startAnimation(fadeoutLabel2);
        fadeoutLabel2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mLabel2.setText(s1);
                AlphaAnimation fadeinLabel2 = new AlphaAnimation(0.0f, 1.0f);
                fadeinLabel2.setDuration(getResources().getInteger(R.integer.create_account_animation_duration) / 2);
                fadeinLabel2.setFillAfter(true);
                mLabel2.startAnimation(fadeinLabel2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void sendSignUpDataToServer() {

        ConnectionManager.getInstance(CreateAccountActivity.this).postUserCreateAccount(userData,mHelloGbyePromotionCheckBox.isChecked(),mThirdPartyPromotionCheckBox.isChecked(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, false);
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, userData.getUserEmail());
                Intent intent = new Intent(getBaseContext(), EnterPinActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);

            }
        });
    }

    private void checkFlow() {
        if (!HGBUtilityNetwork.haveNetworkConnection(getApplicationContext())) {

            Toast.makeText(getApplicationContext(), "There is no network please connect in order to continue", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        String strToken = hgbPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.TOKEN, "");

        if (!strToken.equals("")) {
            if(getIntent().hasExtra("free_user_sign_in") || getIntent().hasExtra("free_user_create_user")){
                checkIfCameFromFreeUser();
            }else{
                goToMainActivity();
            }



        }
    }

    private void goToMainActivity() {

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        boolean doesExist = hgbPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.TRAVEL_PREF_ENTRY, false);

        if (doesExist) {
            Intent intent = new Intent(getApplicationContext(), MainActivityBottomTabs.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), OnBoardingPager.class);
            startActivity(intent);
        }
        //finish();
    }

    @Override
    public void onBackPressed() {
        processBackPressed();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

        switch (CURRENT_STATE) {
            case WELCOME_STATE:

                break;
            case NAME_STATE:
                setNextEnable(checkNameIsValid());
                break;
            case EMAIL_STATE:
                setNextEnable(HGBUtility.checkEmailIsValid(editable.toString()));
                break;
            case PASSWORD_STATE:
                setNextEnable(checkPasswordValid());
                break;
            case ADDRESS_STATE:

                break;
            case LOGIN_STATE:

                break;
        }

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

    public class myClickableSpan extends ClickableSpan {
        int pos;

        public myClickableSpan(int position) {
            this.pos = position;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ContextCompat.getColor(getApplicationContext(), R.color.COLOR_00516f));
            ds.setUnderlineText(true);

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


    public class signInClickableSpan extends ClickableSpan {
        int pos;

        public signInClickableSpan(int position) {
            this.pos = position;
        }

        @Override
        public void onClick(View widget) {
            goToLoginState(true);

        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ContextCompat.getColor(getApplicationContext(), R.color.COLOR_00516f));
            ds.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }


    private void setNextEnable(boolean isEnable) {
        //We might need this in the future. Its  to have the next button diabled if information was not added
//        if (isEnable || BuildConfig.IS_DEV) {//
//            mNextTextView.setAlpha(1.0f);
//            mNextTextView.setClickable(true);
//
//        } else {
//            mNextTextView.setAlpha(0.2f);
//            mNextTextView.setClickable(false);
//        }
        mNextTextView.setAlpha(1.0f);
        mNextTextView.setClickable(true);

    }

    private void checkIfCameFromFreeUser(){

            if(getIntent().getBooleanExtra("free_user_sign_in",false)){
                goToLoginState(true);
            }

            if(getIntent().getBooleanExtra("free_user_create_user",false)){
                animateNameView(true);
            }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 002:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    sendLoginToServer();
                }
                break;

            default:
                break;
        }
    }
}
