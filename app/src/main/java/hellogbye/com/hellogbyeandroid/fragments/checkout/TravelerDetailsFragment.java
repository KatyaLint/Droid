package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.CountryItemVO;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.statics.BookingRequestVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/24/15.
 */
public class TravelerDetailsFragment extends HGBAbstractFragment {


    private FontTextView mTitle;
    private EditText mFirstName;
    private EditText mLastName;
    private FontTextView mDOB;
    private FontTextView mSave;

    private FontTextView mGender;
    private EditText mEmail;
    private EditText mPhone;
    private EditText mAddress;
    private EditText mCity;
    private FontTextView mCountry;
    private EditText mPostalCode;
    private FontTextView mState;
    private UserProfileVO mUser;

    private String[] stateArray;

    private BookingRequestVO bookingResponse;


    public static Fragment newInstance(int position) {
        Fragment fragment = new TravelerDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.travler_detail_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitle = (FontTextView) view.findViewById(R.id.travel_detail_title);
        mFirstName = (EditText) view.findViewById(R.id.travel_detail_first_name);
        mLastName = (EditText) view.findViewById(R.id.travel_detail_last_name);
        mDOB = (FontTextView) view.findViewById(R.id.travel_detail_dob);
        mGender = (FontTextView) view.findViewById(R.id.travel_detail_gender);
        mEmail = (EditText) view.findViewById(R.id.travel_detail_email);
        mPhone = (EditText) view.findViewById(R.id.travel_detail_phone);
        mAddress = (EditText) view.findViewById(R.id.travel_detail_adress);
        mCity = (EditText) view.findViewById(R.id.travel_detail_city);
        mCountry = (FontTextView) view.findViewById(R.id.travel_detail_country);
        mPostalCode = (EditText) view.findViewById(R.id.travel_detail_postal_code);
        mState = (FontTextView) view.findViewById(R.id.travel_detail_province);
        mSave = (FontTextView) view.findViewById(R.id.travler_detail_save);

        //getStaticBooking();
        bookingResponse = getActivityInterface().getBookingRequest();

        Bundle args = getArguments();
        if (args != null) {
            int position = args.getInt(HGBConstants.BUNDLE_USER_JSON_POSITION);
            mUser = getFlowInterface().getListUsers().get(position);
        }

        if (mUser != null) {
            initUser();
        }

        setSaveButton();
        setClickListner();


    }

/*    final String[] titleArray = {"Mr", "Mrs", "Miss"};*/
    final String SLECT_TITLE = "Select Title";


    private void selectStates(String countryPicked) {
        int countryPick = Integer.parseInt(countryPicked);
        ArrayList<CountryItemVO> countries = getActivityInterface().getBookingRequest().getCountries();
        stateArray = new String[countries.get(countryPick).getProvinces().size()];
        ArrayList<ProvincesItem> provinces = countries.get(countryPick).getProvinces();
        for (int i = 0; i < provinces.size(); i++) {
            stateArray[i] = provinces.get(i).getProvincename();
        }
    }

    private void setClickListner() {

        mCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ArrayList<CountryItemVO> countries = bookingResponse.getCountries();
                String[] countryarray = new String[countries.size()];
                for (int i = 0; i < countries.size(); i++) {
                    countryarray[i] = countries.get(i).getName();
                }

                HGBUtility.showPikerDialog(0,mCountry, getActivity(), "Choose country",
                        countryarray, 0, countries.size() - 1, new PopUpAlertStringCB() {
                            @Override
                            public void itemSelected(String inputItem) {
                                for (CountryItemVO countrie : countries) {
                                    if (countrie.getName().equals(inputItem)) {
                                        mCountry.setTag(countrie.getCode());
                                        getStaticProvince();

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

        mState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStaticProvince();
            }
        });

        mGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  showGenderDialog();
                HGBUtility.showPikerDialog(0,mGender, getActivity(), getResources().getString(R.string.select_gender), getResources().getStringArray(R.array.gender_array), 0, 1, null, true);
            }
        });

        mTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HGBUtility.showPikerDialog(0,mTitle, getActivity(), SLECT_TITLE, getResources().getStringArray(R.array.title_array), 0, 1, null, true);


                // showTitleDialog();
            }
        });

        mDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HGBUtility.showDateDialog(getActivity(), mDOB);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mUser.setTitle(mTitle.getText().toString());
                mUser.setGender(mGender.getText().toString());
                mUser.setAddress(mAddress.getText().toString());

                if (mCountry.getText().toString().equals("Canada")) {
                    mUser.setCountry("CN");
                } else if (mCountry.getText().toString().equals("UnitedStates")) {
                    mUser.setCountry("US");
                }else{
                    mUser.setCountry(mCountry.getText().toString());
                }

                mUser.setState(mState.getText().toString());
                mUser.setPostalcode(mPostalCode.getText().toString());
                mUser.setDob(mDOB.getText().toString());
                mUser.setCity(mCity.getText().toString());
                mUser.setFirstname(mFirstName.getText().toString());
                mUser.setLastname(mLastName.getText().toString());
                mUser.setPhone(mPhone.getText().toString());
                mUser.setEmailaddress(mEmail.getText().toString());


                ConnectionManager.getInstance(getActivity()).putCompanion(mUser.getPaxid(), mUser, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        getFragmentManager().popBackStack();
                    }

                    @Override
                    public void onError(Object data) {
                        Toast.makeText(getActivity().getApplicationContext(), "There was a problem saving your information please try again", Toast.LENGTH_SHORT).show();
                        ErrorMessage(data);
                    }
                });



            }
        });
    }

    private void setSaveButton() {
        if (HGBUtility.isUserDataValid(mUser)) {
            mSave.setEnabled(true);
        } else {
            mSave.setEnabled(false);
        }
        mSave.setPadding(0, 30, 0, 30);

    }


    /*final String[] genderArray = {"M", "F"};*/
    /*final String GENDER_TITLE = "Select Gender";*/


    private void initUser() {

        mTitle.setText(mUser.getTitle());
        mFirstName.setText(mUser.getFirstname());
        mLastName.setText(mUser.getLastname());
        mDOB.setText(HGBUtilityDate.parseDateToddMMyyyyForPayment(mUser.getDob()));
        mEmail.setText(mUser.getEmailaddress());
        mPhone.setText(mUser.getPhone());
        mAddress.setText(mUser.getAddress());
        mCity.setText(mUser.getCity());

        if (mUser.getCountry().equals("CA")) {
            mCountry.setText("Canada");
        } else if (mUser.getCountry().equals("US")) {
            mCountry.setText("UnitedStates");
        }else{
            mCountry.setText(mUser.getCountry());
        }

        mPostalCode.setText(mUser.getPostalcode());
        mState.setText(mUser.getState());

        ConnectionManager.getInstance(getActivity()).getStaticBookingProvince(mUser.getCountry(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<ProvincesItem> provinceItems = (List<ProvincesItem>) data;
//                if (provinceItems.size() > 0) {
//
//                } else {
//
//                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });


        setTextListner();

    }

    private void setTextListner() {

        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setFirstname(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setLastname(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setCity(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setState(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setCountry(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        mPostalCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUser.setPostalcode(s.toString());
                setSaveButton();
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


    }

    private void getStaticBooking() {

        ConnectionManager.getInstance(getActivity()).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                bookingResponse = (BookingRequestVO) data;

            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);

            }
        });
    }

    private void getStaticProvince() {
      /*  String countryID;
        if (mCountry.getTag() == null) {
            countryID = mCountry.getText().toString();
            if (countryID.equals("Canada")) {
                countryID = "CA";
            } else if (countryID.equals("UnitedStates")) {
                countryID = "US";
            }
        } else {
            countryID = (String) mCountry.getTag();
        }*/
        String  countryID = (String) mCountry.getTag();
        ConnectionManager.getInstance(getActivity()).getStaticBookingProvince(countryID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                List<ProvincesItem> provinceItems = (List<ProvincesItem>) data;
                if (provinceItems.size() > 0) {
                    setDropDownItems(provinceItems);
                    mState.setVisibility(View.VISIBLE);
                } else {
                    mState.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    private void setDropDownItems(final List<ProvincesItem> provinceItems) {
        String[] countryarray = new String[provinceItems.size()];
        for (int i = 0; i < provinceItems.size(); i++) {
            countryarray[i] = provinceItems.get(i).getProvincename();
        }

        HGBUtility.showPikerDialog(0,mState, getActivity(), "Choose province",
                countryarray, 0, provinceItems.size() - 1, new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {
                        for (ProvincesItem province : provinceItems) {
                            if (province.getProvincename().equals(inputItem)) {
                                mState.setText(province.getProvincename());
                                mState.setTag(province.getProvincecode());
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
