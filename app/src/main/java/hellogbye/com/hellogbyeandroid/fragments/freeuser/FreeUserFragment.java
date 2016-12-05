package hellogbye.com.hellogbyeandroid.fragments.freeuser;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.CreateAccountActivity;
import hellogbye.com.hellogbyeandroid.activities.SignUpActivity;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;

/**
 * Created by nyawka on 4/14/16.
 */
public class FreeUserFragment extends HGBAbstractFragment {
    private FontButtonView free_user_sign_in_btn;
    private FontButtonView free_user_create_new_account_btn;


/*    private FontTextView free_user_free;
    private FontTextView free_user_already_a_member;
    private FontTextView free_user_premium;*//*


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
    private FontEditTextView pin_code_verification_pin;*/



    public static Fragment newInstance(int position) {
        Fragment fragment = new FreeUserFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getFlowInterface().enableFullScreen(true);
        getFlowInterface().getToolBar().setVisibility(View.GONE);
        View rootView = inflater.inflate(R.layout.free_user_layout, container, false);
      /*  View rootView = inflater.inflate(R.layout.sign_up_layout, container, false);


        hgbPrefrenceManager = HGBPreferencesManager.getInstance(getContext());
        userData = new UserSignUpDataVO();

        getStaticBooking();

        sign_up_button = (Button)rootView.findViewById(R.id.sign_up_button);
        sign_up_button.setEnabled(false);


        sign_up_password = (FontEditTextView)rootView.findViewById(R.id.sign_up_password);
        sign_up_confirm_password = (FontEditTextView)rootView.findViewById(R.id.sign_up_confirm_password);


        sign_up_first_name = (FontEditTextView)rootView.findViewById(R.id.sign_up_first_name);
        sign_up_last_name = (FontEditTextView)rootView.findViewById(R.id.sign_up_last_name);

        sign_up_email = (FontEditTextView)rootView.findViewById(R.id.sign_up_email);


        sign_up_city = (AutoCompleteTextView)rootView.findViewById(R.id.sign_up_city);
        sign_up_country = (FontTextView)rootView.findViewById(R.id.sign_up_country);
        sign_up_province_name = (FontTextView)rootView.findViewById(R.id.sign_up_province_name);


        SpannableString ss = new SpannableString(getResources().getString(R.string.sign_up_term_of_use));
      //  ss.setSpan(new SignUpActivity.myClickableSpan(1), 52, 61, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
      //  ss.setSpan(new SignUpActivity.myClickableSpan(2), 66, 80, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        sign_up_hyperlink = (FontTextView) rootView.findViewById(R.id.sign_up_hyperlink);
        sign_up_hyperlink.setText(ss);
        sign_up_hyperlink.setMovementMethod(LinkMovementMethod.getInstance());

        pin_code_verification_layout = (View) rootView.findViewById(R.id.pin_code_verification_layout);
        sign_up_layout_ll = (LinearLayout) rootView.findViewById(R.id.sign_up_layout_ll);
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


                ConnectionManager.getInstance(getActivity()).postUserCreateAccount(userData, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        hgbPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_FREE_USER, false);

                        resetPassword();
                        sign_up_layout_ll.setVisibility(View.GONE);
                        pin_code_verification_layout.setVisibility(View.VISIBLE);


                    }

                    @Override
                    public void onError(Object data) {
                       ErrorMessage(data);

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

                HGBUtility.showPikerDialog(0, sign_up_country, getActivity(), "Choose country",
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
*/


        free_user_sign_in_btn = (FontButtonView)rootView.findViewById(R.id.free_user_sign_in_btn);
        free_user_sign_in_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreateAccountActivity.class);
                startActivity(intent);
            //    getFlowInterface().goToFragment(ToolBarNavEnum.HOTEL.getNavNumber(),null);

            }
        });
        free_user_create_new_account_btn = (FontButtonView)rootView.findViewById(R.id.free_user_create_new_account_btn);
        free_user_create_new_account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });
   /*     free_user_premium = (FontTextView)rootView.findViewById(R.id.free_user_premium);
        free_user_premium.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }
        });*/

        return rootView;
    }

  /*  private void getStaticProvince() {
        String countryID = userData.getCountry();
        ConnectionManager.getInstance(getActivity()).getStaticBookingProvince(countryID, new ConnectionManager.ServerRequestListener() {
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
               ErrorMessage(data);
            }
        });
    }
    private void setDropDownItems(final List<ProvincesItem> provinceItems) {
        //  final ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
        String[] countryarray = new String[provinceItems.size()];
        for (int i = 0; i < provinceItems.size(); i++) {
            countryarray[i] = provinceItems.get(i).getProvincename();
        }

        HGBUtility.showPikerDialog(0, sign_up_province_name, getActivity(), "Choose province",
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

    private void resetPassword() {

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View promptsView = li.inflate(R.layout.popup_confirm_email_sent, null);

        HGBUtility.showAlertPopUp(getActivity(), null, promptsView,
                getResources().getString(R.string.popup_confirm_account_title), getResources().getString(R.string.ok_button), new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {

                    }

                    @Override
                    public void itemCanceled() {

                    }
                });
    }

    private void initAutoCityComplete() {
        sign_up_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HGBUtility.hideKeyboard(getContext(), sign_up_city);

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
                ConnectionManager.getInstance(getActivity()).postAutocompleteCity(editable.toString(), userData.getCountry(),
                        userData.getCountryProvince()
                        , new ConnectionManager.ServerRequestListener() {
                            @Override
                            public void onSuccess(Object data) {
                                adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, (ArrayList<String>) data);
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

        ConnectionManager.getInstance(getActivity()).postUserActivation(userPinCode, new ConnectionManager.ServerRequestListener() {
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

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }

            @Override
            public void onError(Object data) {
              ErrorMessage(data);

            }
        });

    }


    private void getStaticBooking() {

        ConnectionManager.getInstance(getActivity()).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                bookingResponse = (BookingRequestVO) data;
                sortCountryItems();
                //BookingRequest bookingrequest = (BookingRequest)data;
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);

            }
        });
    }

    private void sortCountryItems() {
        ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
        ArrayList<CountryItemVO> firstItems = new ArrayList<>();
        for (CountryItemVO countryItemVO : countries) {
            if (countryItemVO.getCode().equals("CA") || countryItemVO.getCode().equals("US")) {
                firstItems.add(countryItemVO);
            }
        }
        countries.removeAll(firstItems);
        countries.addAll(0, firstItems);
    }*/



}




