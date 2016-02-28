package hellogbye.com.hellogbyeandroid.activities;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;

public class SignUpActivity extends AppCompatActivity {

    private Button sign_up_button;
    private FontEditTextView sign_up_password;
    private FontEditTextView sign_up_confirm_password;
    private FontEditTextView sign_up_email;
    private FontEditTextView sign_up_last_name;
    private FontEditTextView sign_up_first_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        sign_up_button = (Button)findViewById(R.id.sign_up_button);
        sign_up_button.setEnabled(false);


        sign_up_password = (FontEditTextView)findViewById(R.id.sign_up_password);
        sign_up_confirm_password = (FontEditTextView)findViewById(R.id.sign_up_confirm_password);


        sign_up_first_name = (FontEditTextView)findViewById(R.id.sign_up_first_name);
        sign_up_last_name = (FontEditTextView)findViewById(R.id.sign_up_last_name);

        sign_up_email = (FontEditTextView)findViewById(R.id.sign_up_email);

        sign_up_confirm_password.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                String password = sign_up_password.getText().toString();
                String confirm_password = sign_up_confirm_password.getText().toString();
                if(password.equals(confirm_password)
                        && HGBUtility.isValidEmail(sign_up_email.getText().toString().trim())
                        && !sign_up_first_name.getText().toString().isEmpty()
                        && !sign_up_last_name.getText().toString().isEmpty()){

                    sign_up_button.setEnabled(true);
                }else {
                    sign_up_button.setEnabled(false);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }




}
