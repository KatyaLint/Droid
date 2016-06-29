package hellogbye.com.hellogbyeandroid.activities;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.vo.UserSignUpDataVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

public class SignUpActivity extends AppCompatActivity {

    private Button sign_up_button;
    private FontEditTextView sign_up_password;
    private FontEditTextView sign_up_confirm_password;
    private FontEditTextView sign_up_email;
    private FontEditTextView sign_up_last_name;
    private FontEditTextView sign_up_first_name;
    private FontEditTextView sign_up_city;
    private FontTextView sign_up_country;
    private FontTextView sign_up_province_name;
    private BookingRequestVO bookingResponse;
    private UserSignUpDataVO userData;
    private FontTextView sign_up_hyperlink;
    private HGBPreferencesManager hgbPrefrenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        userData = new UserSignUpDataVO();

        getStaticBooking();

        sign_up_button = (Button)findViewById(R.id.sign_up_button);
        sign_up_button.setEnabled(false);


        sign_up_password = (FontEditTextView)findViewById(R.id.sign_up_password);
        sign_up_confirm_password = (FontEditTextView)findViewById(R.id.sign_up_confirm_password);


        sign_up_first_name = (FontEditTextView)findViewById(R.id.sign_up_first_name);
        sign_up_last_name = (FontEditTextView)findViewById(R.id.sign_up_last_name);

        sign_up_email = (FontEditTextView)findViewById(R.id.sign_up_email);


        sign_up_city = (FontEditTextView)findViewById(R.id.sign_up_city);
        sign_up_country = (FontTextView)findViewById(R.id.sign_up_country);
        sign_up_province_name = (FontTextView)findViewById(R.id.sign_up_province_name);


        SpannableString ss = new SpannableString(getResources().getString(R.string.sign_up_term_of_use));
        ss.setSpan(new myClickableSpan(1), 52, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new myClickableSpan(2), 66, 80, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // ss.setSpan(termClickableSpan, 3, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


//        mPrivacyPolicy = (FontTextView) findViewById(R.id.terms_conditions);
//        mPrivacyPolicy.setText(ss);
//        mPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());
        // mPrivacyPolicy.setHighlightColor(Color.TRANSPARENT);

        sign_up_hyperlink = (FontTextView) findViewById(R.id.sign_up_hyperlink);
        sign_up_hyperlink.setText(ss);
        sign_up_hyperlink.setMovementMethod(LinkMovementMethod.getInstance());



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


        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO we need to check if user didnt fill in all mandatory data

                userData.setUserEmail(sign_up_email.getText().toString());
                userData.setFirstName(sign_up_first_name.getText().toString());
                userData.setLastName(sign_up_last_name.getText().toString());
                userData.setCity(sign_up_city.getText().toString());
                userData.setConfirmPassword(sign_up_confirm_password.getText().toString());
                userData.setPassword(sign_up_password.getText().toString());


                ConnectionManager.getInstance(SignUpActivity.this).postUserCreateAccount(userData,new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER,false);
                      //  bookingResponse = (BookingRequestVO)data;
                        //BookingRequest bookingrequest = (BookingRequest)data;

                        final AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                        alert.setCancelable(false);
                        alert.setMessage("A confirmation code was sent to your email. Please verify then login");
                        alert.setTitle("Confirmation Email")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(getApplicationContext(), StartingMenuActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .create().show();

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getFragmentManager(), (String) data);

                    }
                });


            }
        });

        sign_up_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
                String[] countryarray = new String[countries.size()];
                for (int i = 0; i < countries.size(); i++) {
                    countryarray[i] = countries.get(i).getName();
                }

                HGBUtility.showPikerDialog(0,sign_up_country, SignUpActivity.this, "Choose country",
                        countryarray, 0, countries.size() - 1, new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                for (CountryItemVO countrie: countries) {
                                    if(countrie.getName().equals(inputItem)){
                                        userData.setCountry(countrie.getCode());
                                        getStaticProvince();
                                        //userData.setCountryID();
                                    //    sign_up_province_name.setOnClickListener(provinceClicked);

                                        break;
                                    }
                                }

                            }

                            @Override
                            public void itemCanceled() {

                            }
                        }, false);
            }
        });



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




    private View.OnClickListener provinceClicked = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            getStaticProvince();
        }
    };
    private void getStaticBooking(){

            ConnectionManager.getInstance(SignUpActivity.this).getBookingOptions(new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                     bookingResponse = (BookingRequestVO)data;
                    //BookingRequest bookingrequest = (BookingRequest)data;

                }

                @Override
                public void onError(Object data) {
                    HGBErrorHelper errorHelper = new HGBErrorHelper();
                    errorHelper.setMessageForError((String) data);
                    errorHelper.show(getFragmentManager(), (String) data);

                }
            });
        }

    private void getStaticProvince(){
        String countryID =  userData.getCountry();
        ConnectionManager.getInstance(SignUpActivity.this).getStaticBookingProvince(countryID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<ProvincesItem> provinceItems = (List<ProvincesItem>)data;
                if(provinceItems.size() > 0){
                    setDropDownItems(provinceItems);
                    sign_up_province_name.setVisibility(View.VISIBLE);
                }else{
                    sign_up_province_name.setVisibility(View.GONE);
                }
            //    bookingResponse = (BookingRequestVO)data;
                //BookingRequest bookingrequest = (BookingRequest)data;
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
    }


    private void setDropDownItems(final List<ProvincesItem> provinceItems){
      //  final ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
        String[] countryarray = new String[provinceItems.size()];
        for (int i = 0; i < provinceItems.size(); i++) {
            countryarray[i] = provinceItems.get(i).getProvincename();
        }

        HGBUtility.showPikerDialog(0,sign_up_province_name, SignUpActivity.this, "Choose province",
                countryarray, 0, provinceItems.size() - 1, new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        for (ProvincesItem province: provinceItems) {
                            if(province.getProvincename().equals(inputItem)){
                                userData.setCountryProvince(province.getProvincecode());
                                break;
                            }
                        }

                    }

                    @Override
                    public void itemCanceled() {

                    }
                }, false);

    }

}
