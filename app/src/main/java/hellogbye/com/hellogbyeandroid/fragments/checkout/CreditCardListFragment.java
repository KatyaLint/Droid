//package hellogbye.com.hellogbyeandroid.fragments.checkout;
//
//import android.app.Fragment;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//
//import hellogbye.com.hellogbyeandroid.R;
//import hellogbye.com.hellogbyeandroid.adapters.CreditCardAdapter;
//import hellogbye.com.hellogbyeandroid.fragments.HGBAbtsractFragment;
//import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
//import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
//import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;
//import hellogbye.com.hellogbyeandroid.views.FontTextView;
//
///**
// * Created by arisprung on 12/2/15.
// */
//public class CreditCardListFragment extends HGBAbtsractFragment {
//
//    private RecyclerView mRecyclerView;
//    private RecyclerView.LayoutManager mLayoutManager;
//    private CreditCardAdapter mAdapter;
//
//    private FontTextView mProceed;
//    private FontTextView mTotalPrice;
//    private LinearLayout mAddCCLinearLayout;
//
//    public static Fragment newInstance(int position) {
//        Fragment fragment = new CreditCardListFragment();
//        Bundle args = new Bundle();
//        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.choose_cc_layout, container, false);
//
//        return rootView;
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        mRecyclerView = (RecyclerView)view.findViewById(R.id.select_cc_recyclerView);
//        mProceed = (FontTextView)view.findViewById(R.id.cc_proceed);
//        mTotalPrice = (FontTextView)view.findViewById(R.id.cc_total_price);
//        mAddCCLinearLayout = (LinearLayout)view.findViewById(R.id.select_cc_header);
//
//        mTotalPrice.setText(getActivityInterface().getTotalPrice());
//
//
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
//        // use this setting to improve performance if you know that changes
//        // in content do not change the layout size of the RecyclerView
//        mRecyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        getActivityInterface().getCreditCards().get(0).setSelected(true);
//        mAdapter = new CreditCardAdapter(getActivityInterface().getCreditCards(),getActivity().getApplicationContext());
//        mRecyclerView.setAdapter(mAdapter);
//
//
//        mProceed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        mAddCCLinearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFlowInterface().goToFragment(ToolBarNavEnum.ADD_CREDIT_CARD.getNavNumber(),null);
//            }
//        });
//
//    }
//}
