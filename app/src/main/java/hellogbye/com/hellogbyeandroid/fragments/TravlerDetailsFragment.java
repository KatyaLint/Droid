package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.CountryItem;
import hellogbye.com.hellogbyeandroid.models.ProvincesItem;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/24/15.
 */
public class TravlerDetailsFragment extends HGBAbtsractFragment {


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
    private UserData mUser;

    private NumberPicker countryPicker;
    private NumberPicker statePicker;

    private HashMap<String, ArrayList<ProvincesItem>> list = new HashMap<>();

    private ArrayList<CountryItem> mEligabileCountryList = new ArrayList<>();


    public static Fragment newInstance(int position) {
        Fragment fragment = new TravlerDetailsFragment();
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

        Gson gson = new Gson();
        Bundle args = getArguments();
        if (args != null) {
            String strJson = args.getString("user_json");
            mUser = gson.fromJson(strJson, UserData.class);
        }

        if (mUser != null) {
            initUser();
        }

        if (HGBUtility.isUserDataValid(mUser)) {
            mSave.setBackgroundResource(R.drawable.red_button);
        } else {
            mSave.setBackgroundResource(R.drawable.red_disable_button);
        }


        ConnectionManager.getInstance(getActivity()).getBookingOptions(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {


                //TODO need to load provnices from API not file
                loadJSONFromAsset();
                // BookingRequest booking = (BookingRequest)data;

                countryPicker = new NumberPicker(getActivity());
                countryPicker.setMinValue(0);
                countryPicker.setMaxValue(mEligabileCountryList.size());
                String[] countryarray = new String[mEligabileCountryList.size()];
                for (int i = 0; i < mEligabileCountryList.size(); i++) {
                    countryarray[i] = mEligabileCountryList.get(i).getName();
                }
                countryPicker.setDisplayedValues(countryarray);


//


            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });

    }

    private void initUser() {

        mTitle.setText(mUser.getTitle());
        mFirstName.setText(mUser.getFirstname());
        mLastName.setText(mUser.getLastname());
        mDOB.setText(HGBUtility.parseDateToddMMyyyyForPayment(mUser.getDob()));
//        mGender.setText(mUser.());
        mEmail.setText(mUser.getEmailaddress());
        mPhone.setText(mUser.getPhone());
        mAddress.setText(mUser.getAddress());
        mCity.setText(mUser.getCity());
        mCountry.setText(mUser.getCountry());
        mPostalCode.setText(mUser.getPostalcode());
        mState.setText(mUser.getState());

        mCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

    }

    public void loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getActivity().getAssets().open("countrieswithprovinces.txt");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            Type listType = new TypeToken<List<CountryItem>>() {
            }.getType();
            mEligabileCountryList = (ArrayList<CountryItem>) gson.fromJson(json, listType);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void setStatePicker(String name) {
        String[] stateArray = null;

        statePicker = new NumberPicker(getActivity());
        statePicker.setMinValue(0);
        for (CountryItem item : mEligabileCountryList) {
            if (item.getName().equals(name)) {
                statePicker.setMaxValue(item.getProvinces().size());

                stateArray = new String[item.getProvinces().size()];
                for (int i = 0; i < item.getProvinces().size(); i++) {
                    stateArray[i] = item.getProvinces().get(i).getName();
                }
                break;
            }
        }

        statePicker.setDisplayedValues(stateArray);
    }

}
