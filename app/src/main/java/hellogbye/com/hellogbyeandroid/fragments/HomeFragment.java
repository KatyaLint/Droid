//package hellogbye.com.hellogbyeandroid.fragments;
//
//import android.app.Activity;
//import android.app.Fragment;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.text.method.ScrollingMovementMethod;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import hellogbye.com.hellogbyeandroid.R;
//import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
//import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
//import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
//import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
//import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
//import hellogbye.com.hellogbyeandroid.views.FontTextView;
//
///**
// * Created by arisprung on 8/17/15.
// */
//public class HomeFragment extends HGBAbtsractFragment {
//
//
//    private EditText mQueryEditText;
//    private FontTextView mSearch;
//    private ImageView mMicImageView;
//    private FontTextView mBubble;
//    private RelativeLayout mHGBSpinner;
//    private LinearLayout mSpeechLayout;
//    private LinearLayout mKeyBoardLayout;
//    private final int MIC_STATE = 1;
//    private final int KEYBOARD_STATE = 2;
//    private int HOME_FRAGMENT_STATE = 0;
//
//    public HomeFragment() {
//        // Empty constructor required for fragment subclasses
//    }
//
//    public static Fragment newInstance(int position) {
//        Fragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_home_layout, container, false);
//        int i = getArguments().getInt(HGBConstants.ARG_NAV_NUMBER);
//        //    String strFrag = getResources().getStringArray(R.array.nav_draw_array)[i];
//        String strFrag = ToolBarNavEnum.getNavNameByPosition(i);
//        mQueryEditText = (EditText) rootView.findViewById(R.id.query_edit_text);
//        mSearch = (FontTextView) rootView.findViewById(R.id.search);
//        mBubble = (FontTextView) rootView.findViewById(R.id.please_hold);
//        mMicImageView = (ImageView) rootView.findViewById(R.id.mic);
//        mSpeechLayout = (LinearLayout) rootView.findViewById(R.id.speech_ll);
//        mKeyBoardLayout = (LinearLayout) rootView.findViewById(R.id.keyboard_layout);
//        mHGBSpinner = (RelativeLayout) rootView.findViewById(R.id.loader_spinner);
//
//        mQueryEditText.setMovementMethod(new ScrollingMovementMethod());
//        mQueryEditText.setOnEditorActionListener(new EditText.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    handleClick(mQueryEditText.getText().toString());
//                    return false;
//                }
//                return false;
//            }
//        });
//
//        mSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                handleClick(mQueryEditText.getText().toString());
//            }
//        });
//
//        mMicImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //handleClick(query);
////                Fragment fragment = new HotelFragment();
////                HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragment, true);
//                // getActivityInterface().openVoiceToTextControl();
//
//
//                getVoiceInterface().openVoiceToTextControl();
//
//
//            }
//        });
//
//
//
//        //   getActivity().setTitle(strFrag);
//        return rootView;
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        IntentFilter intentFilter = new IntentFilter(HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION);
//        getActivity().registerReceiver(mHomeFragmentReciever, intentFilter);
//        checkState();
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//    }
//
//    //    @Override
////    protected void onDestroy() {
////        super.onDestroy();
////        unregisterReceiver(mGCMReceiver);
////    }
//
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        getActivity().unregisterReceiver(mHomeFragmentReciever);
//    }
//
//    private BroadcastReceiver mHomeFragmentReciever = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            String message = intent.getStringExtra(HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION);
//
//            if (HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION_KEYBOARD_ACTION.equals(message)) {
//                setKeyboardMode();
//            } else if (HGBConstants.HOME_FRAGMENT_TOOLBAR_ACTION_MIC_ACTION.equals(message)) {
//                setMicMode();
//            }
//
//        }
//    };
//
//    public void setKeyboardMode() {
//        HOME_FRAGMENT_STATE = KEYBOARD_STATE;
//        mSpeechLayout.setVisibility(View.INVISIBLE);
//        mKeyBoardLayout.setVisibility(View.VISIBLE);
//        getFlowInterface().setHomeImage("keyboard");
//    }
//
//    public void setMicMode() {
//        HOME_FRAGMENT_STATE = MIC_STATE;
//        mSpeechLayout.setVisibility(View.VISIBLE);
//        mKeyBoardLayout.setVisibility(View.INVISIBLE);
//        getFlowInterface().setHomeImage("mic");
//    }
//
//
//    public void handleClick(String query) {
//
//        mHGBSpinner.setVisibility(View.VISIBLE);
//
//        ConnectionManager.getInstance(getActivity()).getSearchWithQuery(query, "", new ConnectionManager.ServerRequestListener() {
//            @Override
//            public void onSuccess(Object data) {
//                if (data != null) {
//                    getActivityInterface().setTravelOrder((UserTravelMainVO) data);
//
////                    ItineraryFragment fragemnt = new ItineraryFragment();
////                    HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragemnt, true); //now we always want to add to the backstack
//                  getFlowInterface().goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(),null);
//
//                    checkState();
////                    HotelFragment fragemnt = new HotelFragment();
////                    HGBUtility.goToNextFragmentIsAddToBackStack(getActivity(), fragemnt, true); //now we always want to add to the backstack
//                }
//
//            }
//
//            @Override
//            public void onError(Object data) {
//
//                HGBErrorHelper errorHelper = new HGBErrorHelper();
//                errorHelper.show(getFragmentManager(), (String) data);
//                mHGBSpinner.setVisibility(View.GONE);
//                handleRequestFailure((String) data);
//                checkState();
//
//            }
//        });
//
//    }
//
//    private void checkState() {
//
//        if(HOME_FRAGMENT_STATE == MIC_STATE){
//            setMicMode();
//
//        }else if(HOME_FRAGMENT_STATE == KEYBOARD_STATE){
//            setKeyboardMode();
//        }else{
//            setMicMode();
//        }
//    }
//
//
//}
