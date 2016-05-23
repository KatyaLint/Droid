package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.CreditCardAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;

/**
 * Created by arisprung on 12/2/15.
 */
public class CreditCardListFragment extends HGBAbstractFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CreditCardAdapter mAdapter;
    private FloatingActionButton mAddImageView;
    private LinearLayout mMissingLinearLayout;

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
        mRecyclerView = (RecyclerView)view.findViewById(R.id.credit_card_recycler_view);
        mAddImageView = (FloatingActionButton) view.findViewById(R.id.add_fab);
        mMissingLinearLayout = (LinearLayout)view.findViewById(R.id.payment_missing_ll);


        mAddImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(), null);
            }
        });

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        ConnectionManager.getInstance(getActivity()).getCreditCards(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                getFlowInterface().setCreditCards((ArrayList<CreditCardItem>) data);
                if(((ArrayList<CreditCardItem>) data).size()!=0){
                    mMissingLinearLayout.setVisibility(View.GONE);
                    mAdapter = new CreditCardAdapter(getFlowInterface().getCreditCards(),getActivity().getApplicationContext());
                    mRecyclerView.setAdapter(mAdapter);
                }else{
                    mMissingLinearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });

    }
}
