package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.IClickedItem;
import hellogbye.com.hellogbyeandroid.ISwipeAdapterExecution;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.creditcardadapters.CreditCardSwipeItemsAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontButtonView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 12/2/15.
 */
public class CreditCardListFragment extends HGBAbstractFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CreditCardSwipeItemsAdapter mAdapter;
    private FontTextView mAdd;
    //private FloatingActionButton mAddImageView;
    private RelativeLayout mMissingLinearLayout;
    private Bundle bundle;
    public static Fragment newInstance(int position) {
        Fragment fragment = new CreditCardListFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.credit_card_list_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = new Bundle();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.credit_card_recycler_view);
        mAdd = (FontTextView)view.findViewById(R.id.add_new_card);
      //  mAddImageView = (FloatingActionButton) view.findViewById(R.id.fab);
        mMissingLinearLayout = (RelativeLayout) view.findViewById(R.id.payment_missing_rl);

        ((MainActivityBottomTabs)getActivity()).getPreference_add_card().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
            }
        });
//        mAddImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
//            }
//        });

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);



        mAdapter = new CreditCardSwipeItemsAdapter(getActivity());

        mAdapter.setClickedItemIterface(new IClickedItem(){

            @Override
            public String clickedItem(String clickedItem) {
                bundle.putString(HGBConstants.PAYMENT_FILL_CREDIT_CARD,clickedItem);
                getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), bundle);
                return "";
            }
        });


        mRecyclerView.setAdapter(mAdapter);
        removeCreditCardSwipe();

        ConnectionManager.getInstance(getActivity()).getCreditCards(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                getFlowInterface().setCreditCards((ArrayList<CreditCardItem>) data);
                if (((ArrayList<CreditCardItem>) data).size() != 0) {
                    mMissingLinearLayout.setVisibility(View.GONE);
                    mAdapter.setDataSet((ArrayList<CreditCardItem>) data);
                    mAdapter.notifyDataSetChanged();

                } else {
                    mMissingLinearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }


    private void removeCreditCardSwipe(){
        mAdapter.addClickeListeners(new ISwipeAdapterExecution() {
            @Override
            public void clickedItem(String companionId) {
            }

            @Override
            public void deleteClicked(String token) {
                ArrayList<CreditCardItem> creditCards = getFlowInterface().getCreditCards();
                for (int i = 0; i < creditCards.size(); i++) {
                    String creditCardToken = creditCards.get(i).getToken();
                    if (creditCardToken != null && creditCardToken.equals(token)) {
                        removeCardFromServer(i);
                        break;
                    }
                }
            }

            @Override
            public void confirmItem(String companionId) {

            }

            @Override
            public void rejectItem(String companionId) {

            }
        });
    }


    private void removeCardFromServer(final int number) {

            ConnectionManager.getInstance(getActivity()).removeCreditCardHelloGbye(getFlowInterface().getCreditCards().get(number).getToken(), new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {

                    getFlowInterface().getCreditCards().remove(number);
                    mAdapter.notifyDataSetChanged();

                }

                @Override
                public void onError(Object data) {

                    ErrorMessage(data);

                }
            });
    }
}
