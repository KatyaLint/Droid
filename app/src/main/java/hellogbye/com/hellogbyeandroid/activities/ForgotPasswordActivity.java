package hellogbye.com.hellogbyeandroid.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.CaptchaVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;

/**
 * Created by arisprung on 12/11/16.
 */

public class ForgotPasswordActivity extends BaseActivity {

    private FontEditTextView mEmail;
    private FontButtonView mReset;
    private ImageView forgot_password_refresh_image;
    private FontEditTextView forgot_enter_catcha_text;
    private ImageView captcha_image;
    private CaptchaVO captchaVO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);


        forgot_password_refresh_image = (ImageView)findViewById(R.id.forgot_password_refresh_image);
        forgot_enter_catcha_text = (FontEditTextView)findViewById(R.id.forgot_enter_catcha_text);
        captcha_image = (ImageView)findViewById(R.id.captcha_image);

        mEmail = (FontEditTextView) findViewById(R.id.forgot_email);
        mReset = (FontButtonView) findViewById(R.id.reset_password);

        mEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                   resetEmail();
                    return true;
                }
                return false;
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetEmail();
            }
        });


        forgot_password_refresh_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshCpatcha();
            }
        });

        refreshCpatcha();


    }

    private void resetEmail() {
        if (mEmail.getText().length() > 0 && HGBUtility.checkEmailIsValid(mEmail.getText().toString()) && forgot_enter_catcha_text.getText().length() > 0) {



            ConnectionManager.getInstance(ForgotPasswordActivity.this).putCaptcha(captchaVO.getId(),forgot_enter_catcha_text.getText().toString(),mEmail.getText().toString(),
                    new ConnectionManager.ServerRequestListener() {
                        @Override
                        public void onSuccess(Object data) {
                            Toast.makeText(getApplicationContext(), R.string.email_reset_succesfully, Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(Object data) {
                            LayoutInflater li = LayoutInflater.from(getApplicationContext());
                            View popupView = li.inflate(R.layout.popup_layout_log_out, null);
                            HGBUtility.showAlertPopUpOneButton(ForgotPasswordActivity.this, null, popupView,
                                    (String) data, null, true);

                        }
                    });


//            ConnectionManager.getInstance(ForgotPasswordActivity.this).resetPasswordWithEmail(mEmail.getText().toString(),
//                    new ConnectionManager.ServerRequestListener() {
//                        @Override
//                        public void onSuccess(Object data) {
//                            Toast.makeText(getApplicationContext(), R.string.email_reset_succesfully, Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//
//                        @Override
//                        public void onError(Object data) {
//                            LayoutInflater li = LayoutInflater.from(getApplicationContext());
//                            View popupView = li.inflate(R.layout.popup_layout_log_out, null);
//                            HGBUtility.showAlertPopUpOneButton(ForgotPasswordActivity.this, null, popupView,
//                                    (String) data, null);
//
//                        }
//                    });
        } else {
            Toast.makeText(getApplicationContext(), "Email not valid", Toast.LENGTH_SHORT).show();
        }
    }


    private void refreshCpatcha(){
        ConnectionManager.getInstance(ForgotPasswordActivity.this).getCaptcha(
                new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        captchaVO = (CaptchaVO)data;
                        Bitmap bitmap = StringToBitMap(captchaVO.getImage());
                        captcha_image.setImageBitmap(bitmap);

                    }

                    @Override
                    public void onError(Object data) {
                        LayoutInflater li = LayoutInflater.from(getApplicationContext());
                        View popupView = li.inflate(R.layout.popup_layout_log_out, null);
                        HGBUtility.showAlertPopUpOneButton(ForgotPasswordActivity.this, null, popupView,
                                (String) data, null, true);

                    }
                });
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
