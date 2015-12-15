package hellogbye.com.hellogbyeandroid.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.CountryItem;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.CreditCardEditText;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

/**
 * Created by arisprung on 11/24/15.
 */
public class AddCreditCardFragment extends HGBAbtsractFragment {

    private CreditCardEditText mCardNumber;
    private FontEditTextView mCardExpiry;
    private FontEditTextView  mCardCCV;
    private FontEditTextView mCardFirstName;
    private FontEditTextView mCardLastName;
    private FontEditTextView mCardStreet;
    private FontTextView mCardCountry;
    private FontEditTextView mCardCity;
    private FontTextView mCardProvince;
    private FontEditTextView mCardPostal;
    private FontTextView mSave;
    private FontTextView mScan;

    private NumberPicker countryPicker;
    private NumberPicker statePicker;
    private AlertDialog countryDialog;
    private AlertDialog stateDialog;

    private final int MY_SCAN_REQUEST_CODE = 123;


    public static Fragment newInstance(int position) {
        Fragment fragment = new AddCreditCardFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.add_credit_card_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivityInterface().loadJSONFromAsset();
        init(view);
        buildCountryDialog();
        buildStateDialog();

        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scanIntent = new Intent(getActivity().getApplicationContext(), CardIOActivity.class);

                // customize these values to suit your needs.
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, true); // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, true); // default: false

                // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
                startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mCardCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countryDialog.show();
            }
        });

        mCardProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateDialog.show();
            }
        });
    }

    private void init(View view) {
        mCardNumber = (CreditCardEditText) view.findViewById(R.id.cc_number);
        mCardExpiry  = (FontEditTextView)view.findViewById(R.id.cc_expiry);
        mCardCCV  = (FontEditTextView)view.findViewById(R.id.cc_ccv);
        mCardFirstName  = (FontEditTextView)view.findViewById(R.id.cc_first_name);
        mCardLastName  = (FontEditTextView)view.findViewById(R.id.cc_last_name);
        mCardStreet  = (FontEditTextView)view.findViewById(R.id.cc_billint_st);
        mCardCountry  = (FontTextView)view.findViewById(R.id.cc_country);
        mCardCity  = (FontEditTextView)view.findViewById(R.id.cc_billing_city);
        mCardProvince  = (FontTextView)view.findViewById(R.id.cc_billing_province);
        mCardPostal  = (FontEditTextView)view.findViewById(R.id.cc_billing_postal);
        mSave = (FontTextView)view.findViewById(R.id.cc_save);
        mScan = (FontTextView)view.findViewById(R.id.cc_scan);
    }

    private void buildCountryDialog() {
        View v1 = getActivity().getLayoutInflater().inflate(R.layout.picker_dialog, null);

        countryPicker = (NumberPicker) v1.findViewById(R.id.np);
        countryPicker.setMinValue(0);
        countryPicker.setMaxValue(getActivityInterface().getEligabileCountries().size() - 1);
        final String[] countryarray = new String[getActivityInterface().getEligabileCountries().size()];
        for (int i = 0; i < getActivityInterface().getEligabileCountries().size(); i++) {
            countryarray[i] = getActivityInterface().getEligabileCountries().get(i).getName();
        }
        countryPicker.setDisplayedValues(countryarray);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v1);
        builder.setTitle("Select Country");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mCardCountry.setText(countryarray[countryPicker.getValue()]);
                buildStateDialog();
                return;
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        countryDialog = builder.create();
    }

    private void buildStateDialog() {
        View v1 = getActivity().getLayoutInflater().inflate(R.layout.picker_dialog, null);

        statePicker = (NumberPicker) v1.findViewById(R.id.np);
        statePicker.setMinValue(0);

        statePicker.setMaxValue(getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().size() - 1);
        final String[] stateArray = new String[getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().size()];
        for (int i = 0; i < getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().size(); i++) {
            stateArray[i] = getActivityInterface().getEligabileCountries().get(countryPicker.getValue()).getProvinces().get(i).getName();
        }
        statePicker.setDisplayedValues(stateArray);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v1);
        builder.setTitle("Select Province");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mCardProvince.setText(stateArray[statePicker.getValue()]);
                return;
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });


        stateDialog = builder.create();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_SCAN_REQUEST_CODE) {
            String resultDisplayStr;
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.getRedactedCardNumber() + "\n";
                mCardNumber.setText(scanResult.getRedactedCardNumber());
                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid()) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                    mCardExpiry.setText(scanResult.expiryMonth + "/" + scanResult.expiryYear);
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                    mCardCCV.setText(scanResult.cvv);
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                    mCardPostal.setText(scanResult.postalCode);
                }
            }
            else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultStr);
        }
        // else handle other activity results
    }
}
