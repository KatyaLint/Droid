package hellogbye.com.hellogbyeandroid.fragments.checkout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.creditcardadapters.AlertCheckoutAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.PinnedHeaderListView;

/**
 * Created by amirlubashevsky on 15/08/2017.
 */

public class ManagePaymentFragment extends HGBAbstractFragment {
    private ArrayList<CreditCardItem> itemsList;
    public static Fragment newInstance(int position) {
        Fragment fragment = new ManagePaymentFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getFlowInterface().enableFullScreen(true);
        View rootView = inflater.inflate(R.layout.checkout_credit_card_managment, container, false);
        itemsList = creditCardLastNumbers();

        FontButtonView checkout_add_credit_card = (FontButtonView)rootView.findViewById(R.id.checkout_add_credit_card);
        checkout_add_credit_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
            }
        });



        AlertCheckoutAdapter adapter = new AlertCheckoutAdapter(itemsList,getActivity().getApplicationContext());
        ListView checkout_recycle_view = (ListView)rootView.findViewById(R.id.checkout_recycle_view);
        checkout_recycle_view.setAdapter(adapter);

        checkout_recycle_view.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {
              //  FontTextView itemSelected = (FontTextView)view.findViewById(R.id.alert_checkout_text);
                CreditCardItem slectedCreditCard = itemsList.get(position);
                if(slectedCreditCard == null){
                    ErrorMessage("Wrong credit Card");
                    return;
                }

                getActivityInterface().setSelectedCreditCard(slectedCreditCard);
                getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_DETAILS.getNavNumber(), null);

//                if ( selectedText.getLast4().equals(getString(R.string.cancel))) {
//
//                } else if (selectedText.getLast4().equals(getString(R.string.add_card))) {
//                    getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);

//                if (selectedText.getLast4().equals(getString(R.string.remove_card))) {
//                    if (!mSelectedView.getText().toString().equals(getString(R.string.select_card))) {
//                        CreditCardItem selectedCreditCard = getCardByNumber(mSelectedView.getText().toString());
//                        getFlowInterface().getCreditCardsSelected().remove(selectedCreditCard);
//                        calculateCard(selectedCreditCard, mSelectedView, false);
//                    }
//
//                } else {
//                    CreditCardItem selectedCreditCard = getCardByNumber(selectedText.getLast4());
//                    getFlowInterface().getCreditCardsSelected().add(selectedCreditCard);
//                    calculateCard(selectedCreditCard, mSelectedView, true);
//                }
//                mSelectedView = null;


            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {
            }
        });

        return rootView;
    }



    private CreditCardItem getCardByNumber(String selectedText) {
        for (CreditCardItem credit : getFlowInterface().getCreditCards()) {
            if (selectedText.equals(credit.getLast4())) {
                return credit;
            }
        }
        return null;
    }


    private  ArrayList<CreditCardItem>  creditCardLastNumbers(){
        ArrayList<CreditCardItem> itemsList = new ArrayList<>();



        ArrayList<CreditCardItem> creditCards = getFlowInterface().getCreditCards();
        for (CreditCardItem item : creditCards) {
            itemsList.add(item);
        }

        CreditCardItem cAdd = new CreditCardItem();
        cAdd.setLast4(getActivity().getResources().getString(R.string.add_card));
        itemsList.add(cAdd);

//        CreditCardItem cLast = new CreditCardItem();
//        cLast.setLast4(getActivity().getResources().getString(R.string.remove_card));

       // itemsList.add(cLast);
        return itemsList;
    }

}
