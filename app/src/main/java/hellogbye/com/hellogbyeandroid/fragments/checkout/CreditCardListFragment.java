package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.BuildConfig;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.CreditCardAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserData;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 12/2/15.
 */
public class CreditCardListFragment extends HGBAbtsractFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CreditCardAdapter mAdapter;

    private FontTextView mProceed;
    private FontTextView mTotalPrice;
    private LinearLayout mAddCCLinearLayout;
    private ArrayList<CreditCardItem> mCreditCardList;

    public static Fragment newInstance(int position) {
        Fragment fragment = new CreditCardListFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.choose_cc_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ConnectionManager.getInstance(getActivity()).getCreditCards(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                mCreditCardList = ((ArrayList<CreditCardItem>) data);
                mCreditCardList.get(0).setSelected(true);
                mAdapter = new CreditCardAdapter(mCreditCardList, getActivity().getApplicationContext());
                mRecyclerView.setAdapter(mAdapter);
                getActivityInterface().setCreditCards((ArrayList<CreditCardItem>) data);
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });
        mRecyclerView = (RecyclerView) view.findViewById(R.id.select_cc_recyclerView);
        mProceed = (FontTextView) view.findViewById(R.id.cc_proceed);
        mTotalPrice = (FontTextView) view.findViewById(R.id.cc_total_price);
        mAddCCLinearLayout = (LinearLayout) view.findViewById(R.id.select_cc_header);

        mTotalPrice.setText(getActivityInterface().getTotalPrice());


        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                CreditCardItem selectedCreditCard = getSelectedCreditCrad();

                try {

                    JSONArray jsonArray = new JSONArray();
                    for (UserData userData : getActivityInterface().getListUsers()) {
                        JSONObject jsonUser = new JSONObject();
                        jsonUser.put("dateofbirth", userData.getDob());
                        jsonUser.put("lastname", userData.getLastname());
                        jsonUser.put("city", userData.getCity());
                        jsonUser.put("primaryphone", userData.getPhone());
                        jsonUser.put("title", userData.getTitle().trim());
                        jsonUser.put("province", userData.getState());
                        jsonUser.put("specialrequest", "");//TODO need to add
                        jsonUser.put("paxguid", userData.getPaxid());
                        jsonUser.put("address", userData.getAddress());
                        jsonUser.put("passportcountry", userData.getCountry());//TODO need to add
                        jsonUser.put("middlename", "");//TODO need to add
                        jsonUser.put("email", userData.getEmailaddress());
                        jsonUser.put("paxmileage", 1);//TODO need to add

                        if(BuildConfig.IS_DEV){
                            jsonUser.put("firstname", "Roofus");
                            jsonUser.put("lastname", "Summers");
                        }else{
                            jsonUser.put("firstname", userData.getFirstname());
                            jsonUser.put("lastname", userData.getLastname());
                        }

                        jsonUser.put("frequentflyernumber", "");//TODO need to add
                        jsonUser.put("postalcode", userData.getPostalcode());
                        jsonUser.put("country", userData.getCountry());
                        jsonUser.put("mealpreference", "");//TODO need to add



                        //TODO this need to change once we switch new design
                        JSONArray bookingArray = new JSONArray();

                        for (String s : getActivityInterface().getItenerayItems()) {
                            bookingArray.put(s);
                        }

                        jsonUser.put("bookingitems", bookingArray);
                        jsonArray.put(jsonUser);


                    }

                    jsonObject.put("paxdetails", jsonArray);


                    jsonObject.put("itineraryid", getActivityInterface().getSolutionID());


                    //Booking Items
                    JSONObject bookingItems = new JSONObject();
                    JSONArray bookingArray = new JSONArray();
                    for (String s : getActivityInterface().getItenerayItems()) {
                        bookingArray.put(s);
                    }
                    bookingItems.put(selectedCreditCard.getToken(), bookingArray);
                    jsonObject.put("bookingitems", bookingItems);


                    JSONArray creditJsonArray = new JSONArray();

                    //TODO we neeed to add more if when we support multiple cards

                    JSONObject creditObject = new JSONObject();

                    creditObject.put("cardnumber", selectedCreditCard.getToken());
                    creditObject.put("expirymonth", selectedCreditCard.getExpmonth());
                    creditObject.put("cvv", "123");//TODO this is hard coded needed to fix
                    creditObject.put("firstname", selectedCreditCard.getBuyerfirstname());
                    creditObject.put("lastname", selectedCreditCard.getBuyerlastname());
                    creditObject.put("cardtype", selectedCreditCard.getCardtypeid());
                    creditObject.put("expiryyear", selectedCreditCard.getExpyear());

                    JSONObject creditAddressObject = new JSONObject();
                    creditAddressObject.put("postalcode", selectedCreditCard.getBuyerzip());
                    creditAddressObject.put("country", getActivityInterface().getCurrentUser().getCountry());
                    creditAddressObject.put("suite_apt", "");
                    creditAddressObject.put("city", getActivityInterface().getCurrentUser().getCity());
                    creditAddressObject.put("province", getActivityInterface().getCurrentUser().getState());
                    creditAddressObject.put("address", selectedCreditCard.getBuyeraddress());
                    creditObject.put("billingaddress",creditAddressObject);

                    creditJsonArray.put(creditObject);

                    jsonObject.put("carddetails",creditJsonArray);




                } catch (Exception e) {
                    e.printStackTrace();
                }


                ConnectionManager.getInstance(getActivity()).pay(jsonObject, new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        Log.d("", "");
                        //TODO need to go back to HOME
                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getFragmentManager(),(String) data);
                    }
                });

            }
        });

        mAddCCLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
            }
        });

    }


    private CreditCardItem getSelectedCreditCrad() {

        return mCreditCardList.get(mAdapter.getLastCheckedPos());
    }


}
