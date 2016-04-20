package hellogbye.com.hellogbyeandroid.fragments.checkout;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.TravlerAdapter;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserDataVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.CreditCardItem;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/23/15.
 */
public class TravelersFragment extends HGBAbstractFragment {


    private FontTextView mNext;

    private TravlerAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    public static Fragment newInstance(int position) {
        Fragment fragment = new TravelersFragment();
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


                getFlowInterface().setListUsers((ArrayList<UserDataVO>) data);
                mAdapter = new TravlerAdapter(getFlowInterface().getListUsers(), getActivity().getApplicationContext());

                mRecyclerView.setAdapter(mAdapter);
                mAdapter.SetOnItemClickListener(new TravlerAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View v, int position) {
                        Bundle args = new Bundle();
                        args.putInt("user_json_position", position);
                        getFlowInterface().goToFragment(ToolBarNavEnum.PAYMENT_TRAVLERS_DETAILS.getNavNumber(), args);
                    }
                });
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError((String) data);
                errorHelper.show(getActivity().getFragmentManager(), (String) data);
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectionManager.getInstance(getActivity()).getCreditCards(new ConnectionManager.ServerRequestListener() {
                    @Override
                    public void onSuccess(Object data) {
                        getActivityInterface().setCreditCards((ArrayList<CreditCardItem>) data);
                        getFlowInterface().goToFragment(ToolBarNavEnum.SELECT_CREDIT_CARD.getNavNumber(), null);

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getActivity().getFragmentManager(), (String) data);
                    }
                });


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


}
