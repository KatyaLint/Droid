package hellogbye.com.hellogbyeandroid.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.UserLoginCredentials;
import hellogbye.com.hellogbyeandroid.models.vo.UserSignUpDataVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

public class SignUpActivity extends BaseActivity {

    private Button sign_up_button;
    private FontEditTextView sign_up_password;
    private FontEditTextView sign_up_confirm_password;
    private FontEditTextView sign_up_email;
    private FontEditTextView sign_up_last_name;
    private FontEditTextView sign_up_first_name;
    private AutoCompleteTextView sign_up_city;
    private FontTextView sign_up_country;
    private FontTextView sign_up_province_name;
    private BookingRequestVO bookingResponse;
    private UserSignUpDataVO userData;
    private FontTextView sign_up_hyperlink;
    private HGBPreferencesManager hgbPrefrenceManager;
    private LinearLayout sign_up_layout_ll;
    private View pin_code_verification_layout;
    private ArrayAdapter adapter;
    private FontTextView pin_code_verification_next;
    private FontEditTextView pin_code_verification_pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getApplicationContext());
        userData = new UserSignUpDataVO();

        getStaticBooking();

        sign_up_button = (Button) findViewById(R.id.sign_up_button);
        sign_up_button.setEnabled(false);


        sign_up_password = (FontEditTextView) findViewById(R.id.sign_up_password);
        sign_up_confirm_password = (FontEditTextView) findViewById(R.id.sign_up_confirm_password);


        sign_up_first_name = (FontEditTextView) findViewById(R.id.sign_up_first_name);
        sign_up_last_name = (FontEditTextView) findViewById(R.id.sign_up_last_name);

        sign_up_email = (FontEditTextView) findViewById(R.id.sign_up_email);


        sign_up_city = (AutoCompleteTextView) findViewById(R.id.sign_up_city);
        sign_up_country = (FontTextView) findViewById(R.id.sign_up_country);
        sign_up_province_name = (FontTextView) findViewById(R.id.sign_up_province_name);


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

        pin_code_verification_layout = (View) findViewById(R.id.pin_code_verification_layout);
        sign_up_layout_ll = (LinearLayout) findViewById(R.id.sign_up_layout_ll);
        sign_up_layout_ll.setVisibility(View.VISIBLE);
        pin_code_verification_layout.setVisibility(View.GONE);

        pin_code_verification_next = (FontTextView) pin_code_verification_layout.findViewById(R.id.pin_code_verification_next);
        pin_code_verification_pin = (FontEditTextView) pin_code_verification_layout.findViewById(R.id.pin_code_verification_pin);

        pin_code_verification_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateUserWithKey();
            }
        });

        sign_up_confirm_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String password = sign_up_password.getText().toString();
                String confirm_password = sign_up_confirm_password.getText().toString();
                if (password.equals(confirm_password)
                        && HGBUtility.isValidEmail(sign_up_email.getText().toString().trim())
                        && !sign_up_first_name.getText().toString().isEmpty()
                        && !sign_up_last_name.getText().toString().isEmpty()) {

                    sign_up_button.setEnabled(true);
                } else {
                    sign_up_button.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        initAutoCityComplete();



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


                ConnectionManager.getInstance(SignUpActivity.this).postUserCreateAccount(userData, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, false);

                        resetPassword();
                        sign_up_layout_ll.setVisibility(View.GONE);
                        pin_code_verification_layout.setVisibility(View.VISIBLE);


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

                HGBUtility.showPikerDialog(0, sign_up_country, SignUpActivity.this, "Choose country",
                        countryarray, 0, countries.size() - 1, new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                for (CountryItemVO countrie : countries) {
                                    if (countrie.getName().equals(inputItem)) {
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

    private void initAutoCityComplete() {
        sign_up_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HGBUtility.hideKeyboard(getApplicationContext(), sign_up_city);

            }
        });

        sign_up_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                ConnectionManager.getInstance(SignUpActivity.this).postAutocompleteCity(editable.toString(), userData.getCountry(),
                        userData.getCountryProvince()
                        , new ConnectionManager.ServerRequestListener() {
                            @Override
                            public void onSuccess(Object data) {
                                adapter = new ArrayAdapter(SignUpActivity.this, android.R.layout.simple_list_item_1, (ArrayList<String>) data);
                                sign_up_city.setAdapter(adapter);
                            }

                            @Override
                            public void onError(Object data) {
                                // ErrorMessage(data);

                            }
                        });


            }
        });

    }


    private void activateUserWithKey() {

        String userPinCode = pin_code_verification_pin.getText().toString();

        ConnectionManager.getInstance(SignUpActivity.this).postUserActivation(userPinCode, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                sign_up_layout_ll.setVisibility(View.VISIBLE);
                pin_code_verification_layout.setVisibility(View.GONE);
                UserLoginCredentials user = (UserLoginCredentials) data;
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.TOKEN, user.getToken());
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_PROFILE_ID, user.getUserprofileid());

                hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, false);
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_PSWD, null);
                hgbPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_USER_LAST_EMAIL, null);

                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);

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

        LayoutInflater li = LayoutInflater.from(SignUpActivity.this);
        final View promptsView = li.inflate(R.layout.popup_confirm_email_sent, null);

        HGBUtility.showAlertPopUp(SignUpActivity.this, null, promptsView,
                getResources().getString(R.string.popup_confirm_account_title), getResources().getString(R.string.ok_button), new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {

                    }

                    @Override
                    public void itemCanceled() {

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


    private View.OnClickListener provinceClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getStaticProvince();
        }
    };


    private void getStaticBooking() {

        ConnectionManager.getInstance(SignUpActivity.this).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                bookingResponse = (BookingRequestVO) data;
                sortCountryItems();
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


    public void sortCountryItems() {
        ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
        ArrayList<CountryItemVO> firstItems = new ArrayList<>();
        for (CountryItemVO countryItemVO : countries) {
            if (countryItemVO.getCode().equals("CA") || countryItemVO.getCode().equals("US")) {
                firstItems.add(countryItemVO);
            }
        }
        countries.removeAll(firstItems);
        countries.addAll(0, firstItems);
    }

    private void getStaticProvince() {
        String countryID = userData.getCountry();
        ConnectionManager.getInstance(SignUpActivity.this).getStaticBookingProvince(countryID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<ProvincesItem> provinceItems = (List<ProvincesItem>) data;
                if (provinceItems.size() > 0) {
                    setDropDownItems(provinceItems);
                    sign_up_province_name.setVisibility(View.VISIBLE);
                } else {
                    sign_up_province_name.setVisibility(View.GONE);
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


    private void setDropDownItems(final List<ProvincesItem> provinceItems) {
        //  final ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
        String[] countryarray = new String[provinceItems.size()];
        for (int i = 0; i < provinceItems.size(); i++) {
            countryarray[i] = provinceItems.get(i).getProvincename();
        }

        HGBUtility.showPikerDialog(0, sign_up_province_name, SignUpActivity.this, "Choose province",
                countryarray, 0, provinceItems.size() - 1, new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        for (ProvincesItem province : provinceItems) {
                            if (province.getProvincename().equals(inputItem)) {
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
