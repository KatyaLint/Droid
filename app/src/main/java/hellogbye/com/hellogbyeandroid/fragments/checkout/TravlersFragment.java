package hellogbye.com.hellogbyeandroid.fragments.checkout;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.TravlerAdapter;
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
 * Created by arisprung on 11/23/15.
 */
public class TravlersFragment extends HGBAbtsractFragment {


    private FontTextView mNext;

    private TravlerAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public static Fragment newInstance(int position) {
        Fragment fragment = new TravlersFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.payment_travlers_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNext = (FontTextView) view.findViewById(R.id.traveler_next);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.traveler_recyclerView);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        ConnectionManager.getInstance(getActivity()).getTravellersInforWithSolutionId(getActivityInterface().getSolutionID(), new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                getActivityInterface().setListUsers((ArrayList<UserData>) data);
                mAdapter = new TravlerAdapter(getActivityInterface().getListUsers(), getActivity().getApplicationContext(), TravlersFragment.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.SetOnItemClickListener(new TravlerAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View v, int position) {
                        Bundle args = new Bundle();
                        args.putInt("user_json_position", position);
                        getActivityInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVLERS_DETAILS.getNavNumber(), args);
                    }
                });
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.show(getFragmentManager(), (String) data);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivityInterface().goToFragment(ToolBarNavEnum.SELECT_CREDIT_CARD.getNavNumber(), null);
            }
        });
    }

    private void setNextButtonBackround(boolean isEnabled) {
        mNext.setEnabled(isEnabled);
        if (isEnabled) {
            mNext.setBackgroundResource(R.drawable.red_button);

        } else {
            mNext.setBackgroundResource(R.drawable.red_disable_button);
        }
    }

    public void deselectNext() {
        setNextButtonBackround(false);

    }
}
