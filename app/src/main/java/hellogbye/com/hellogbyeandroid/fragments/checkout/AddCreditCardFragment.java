package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.CreditCardSessionItem;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
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
    private FontEditTextView mCardCCV;
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

    private ArrayList<String> listOfPattern;
    private final int MY_SCAN_REQUEST_CODE = 123;

    private ProgressDialog progressDialog;
    private CreditCardSessionItem creditCardItemSession;


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
        progressDialog = new ProgressDialog(getActivity());
        getActivityInterface().loadJSONFromAsset();
        init(view);
        buildCountryDialog();
        buildStateDialog();
        listOfPattern = new ArrayList<String>();
        String ptAmeExp = "^3[47][0-9]{5,}$";
        listOfPattern.add(ptAmeExp);
        String ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
        listOfPattern.add(ptDiscover);
        String ptMasterCard = "^5[1-5][0-9]{5,}$";
        listOfPattern.add(ptMasterCard);
        String ptVisa = "^4[0-9]{6,}$";
        listOfPattern.add(ptVisa);

//        String ptDinClb = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";
//        listOfPattern.add(ptDinClb);
//
//        String ptJcb = "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$";
//        listOfPattern.add(ptJcb);

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
                sendCCToServer();

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
        mCardExpiry = (FontEditTextView) view.findViewById(R.id.cc_expiry);
        mCardCCV = (FontEditTextView) view.findViewById(R.id.cc_ccv);
        mCardFirstName = (FontEditTextView) view.findViewById(R.id.cc_first_name);
        mCardLastName = (FontEditTextView) view.findViewById(R.id.cc_last_name);
        mCardStreet = (FontEditTextView) view.findViewById(R.id.cc_billint_st);
        mCardCountry = (FontTextView) view.findViewById(R.id.cc_country);
        mCardCity = (FontEditTextView) view.findViewById(R.id.cc_billing_city);
        mCardProvince = (FontTextView) view.findViewById(R.id.cc_billing_province);
        mCardPostal = (FontEditTextView) view.findViewById(R.id.cc_billing_postal);
        mSave = (FontTextView) view.findViewById(R.id.cc_save);
        mScan = (FontTextView) view.findViewById(R.id.cc_scan);
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
            } else {
                resultDisplayStr = "Scan was canceled.";
            }
            // do something with resultDisplayStr, maybe display it in a textView
            // resultTextView.setText(resultStr);
        }
        // else handle other activity results
    }


    private void sendCCToServer() {
        progressDialog = ProgressDialog.show(getActivity(), "", "Adding Credit Card");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        final CreditCardItem creditCardItem = new CreditCardItem();
        creditCardItem.setBuyerfirstname(mCardFirstName.getText().toString());
        creditCardItem.setBuyerlastname(mCardLastName.getText().toString());
        creditCardItem.setBuyerzip(mCardPostal.getText().toString());
        creditCardItem.setBuyeraddress(mCardStreet.getText().toString());
        creditCardItem.setCardtypeid(getCCType());

        String expDate = mCardExpiry.getText().toString();
        if (expDate.contains("/")) {
            String[] parts = expDate.split("/");
            String partMonth = parts[0];
            String partYear = parts[1];
            creditCardItem.setExpmonth(partMonth);
            creditCardItem.setExpyear(partYear);
        }


        String last4 = mCardNumber.getText().toString();
        last4 = last4.substring(last4.length() - 4, last4.length());
        creditCardItem.setLast4(last4);
        creditCardItem.setToken(mCardCCV.getText().toString());
        creditCardItem.setCardNumber(mCardNumber.getText().toString());

        ConnectionManager.getInstance(getActivity()).getCCSession(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                creditCardItemSession = (CreditCardSessionItem) data;


                if (creditCardItemSession == null) {
                    HGBErrorHelper errorHelper = new HGBErrorHelper();
                    errorHelper.show(getFragmentManager(), "There was a probelm please try again");
                    return;
                }

                creditCardItem.setNickname(creditCardItemSession.getNickname());


                ConnectionManager.getInstance(getActivity()).addCreditCard(creditCardItem, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {


                        JSONObject jsonObj = null;
                        try {
                            jsonObj = XML.toJSONObject((String) data);
                            JSONObject json1 = jsonObj.getJSONObject("soap:Envelope");
                            JSONObject json2 = json1.getJSONObject("soap:Body");
                            JSONObject json3 = json2.getJSONObject("AddCOF_SoapResponse");
                            JSONObject json4 = json3.getJSONObject("AddCOF_SoapResult");
                            JSONObject json5 = json4.getJSONObject("TokenData");
                            String strToken = json5.getString("Token");
                            String strNickName = json5.getString("NickName");
                            String strLast4 = json5.getString("Last4");

                            JSONObject json = new JSONObject();
                            json.put("Last4", strLast4);
                            json.put("NickName", strNickName);
                            json.put("Token", strToken);


                            ConnectionManager.getInstance(getActivity()).AddCreditCardHelloGbye(json, new ConnectionManager.ServerRequestListener() {
                                @Override
                                public void onSuccess(Object data) {
                                    progressDialog.hide();
                                    getFragmentManager().popBackStack();

                                }

                                @Override
                                public void onError(Object data) {

                                    progressDialog.hide();
                                    HGBErrorHelper errorHelper = new HGBErrorHelper();
                                    errorHelper.show(getFragmentManager(), (String) data);

                                }
                            });


                        } catch (JSONException e) {
                            Log.e("JSON exception", e.getMessage());
                            e.printStackTrace();
                            progressDialog.hide();
                            HGBErrorHelper errorHelper = new HGBErrorHelper();
                            errorHelper.show(getFragmentManager(), e.getMessage());
                        }
                        Log.d("JSON", jsonObj.toString());
                    }

                    @Override
                    public void onError(Object data) {
                        Log.e("", "");
                        progressDialog.hide();
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.show(getFragmentManager(), (String) data);

                    }
                });


            }

            @Override
            public void onError(Object data) {

                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });


    }

    private String getCCType() {
        for (int i = 0; i < listOfPattern.size(); i++) {
            if (mCardNumber.getText().toString().matches(listOfPattern.get(i))) {
                return String.valueOf(i + 1);
            }
        }
        return "0";
    }


}
