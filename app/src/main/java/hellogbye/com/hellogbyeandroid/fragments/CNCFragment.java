package hellogbye.com.hellogbyeandroid.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.CNCAdapter;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportResultsVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportServerResultVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.ResponsesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.ConversationVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBTranslate;
import hellogbye.com.hellogbyeandroid.utilities.HGBTranslateInterface;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/3/15.
 */
public class CNCFragment extends HGBAbstractFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CNCAdapter mAdapter;
    private EditText mEditText;
    private ImageView mMicImageView;
    private FontTextView mSendTextView;
    private HGBPreferencesManager mHGBPrefrenceManager;
    private FontTextView mProfileTutorialText;
    private FontTextView mSpeechTutorialText;

    private String[] locationArr;
   // private Button cnc_fragment_trip_settings;
    private static int SPLASH_TIME_OUT = 5000;
    //  private CostumeToolBar mToolbar;

    private PreferenceSettingsFragment.OnItemClickListener editClickCB;
    private ImageButton toolbar_go_to_iternerary;
    private ImageButton cncDissmissImageButton;

    private FontTextView mTextTutoralBody;
    private FontTextView mTextTutoralHeader;
    private  Handler tutorailTexthandler = new Handler();
    private   Runnable mRunnable;
    private String[] account_settings;
    private int mTutorialTextNumber = 0;


  //  private Queue<AirportSendValuesVO> airportSendValuesVOs = new ArrayDeque<>();
    private ArrayList<AirportSendValuesVO> airportSendValuesVOs = new ArrayList<AirportSendValuesVO>();
    private int maxAirportSize = 0;
    private boolean clearCNCscreen;
    private Bundle args;
    private int selectedCount = 0;
 //   private  AirportSendValuesVO airportSendValuesVO;


    public static Fragment newInstance(int position) {
        Fragment fragment = new CNCFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        account_settings = getResources().getStringArray(R.array.tutorial_arr);

        View rootView = inflater.inflate(R.layout.cnc_fragment_layout, container, false);
        //   mToolbar = (CostumeToolBar)rootView.findViewById(R.id.toolbar_cnc);

        mHGBPrefrenceManager = HGBPreferencesManager.getInstance(getActivity().getApplicationContext());

        init(rootView);
        initList();


        //    updateToolBarView();


        getAccountsProfiles();


        joinCompanionToTravel();

        args = getArguments();
        clearCNCscreen = args.getBoolean(HGBConstants.CNC_CLEAR_CHAT);
        startTutorial();
        if(clearCNCscreen){
         //   startTutorialText();
            clearCNCItems();

            //This is to ckeck and display tutorial this version was cancelled waiting for new one


            mTextTutoralHeader.setVisibility(View.VISIBLE);
            mTextTutoralBody.setVisibility(View.VISIBLE);
        }else{
            mTextTutoralHeader.setVisibility(View.GONE);
            mTextTutoralBody.setVisibility(View.GONE);
        }


        String solution_id = args.getString(HGBConstants.SOLUTION_ITINERARY_ID);
        if(solution_id != null){
            addWaitingItem();
            getCurrentItinerary(solution_id);
        }

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFlowInterface().enableFullScreen(true);

    }


    private void addUserConversation(){
        ArrayList<ConversationVO> conversations = getActivityInterface().getTravelOrder().getConversation();
        for(ConversationVO conversation : conversations){
            handleHGBMessageMe(conversation.getmMessage());
            handleHGBMessage(getString(R.string.itinerary_created));
        }
    }

    private void getCurrentItinerary(String solutionId){

        ConnectionManager.getInstance(getActivity()).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                args.putString(HGBConstants.SOLUTION_ITINERARY_ID,null);
                args.putBoolean(HGBConstants.CNC_CLEAR_CHAT,false);
                UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;
                getActivityInterface().setTravelOrder(userTravelMainVO);
                addUserConversation();
                getFlowInterface().goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(), null);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);

            }
        });
    }

    private void clearCNCItems() {

        getActivityInterface().setCNCItems(null);
        getActivityInterface().setTravelOrder(null);
        mHGBPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        mHGBPrefrenceManager.removeKey(HGBPreferencesManager.HGB_LAST_TRAVEL_VO);
        initList();
    }



    private void joinCompanionToTravel(){

        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        String user_id = args.getString(HGBConstants.BUNDLE_ADD_COMPANION_ID);

        if(user_id == null){
            return;
        }

        String tripSolutionId = getActivityInterface().getTravelOrder().getmSolutionID(); //getActivityInterface().getSolutionID();

        String userName = "";
        String joinQuery = "Join ";

     //   ArrayList<AccountsVO> accounts = getActivityInterface().getAccounts();
        ArrayList<CompanionVO> companions = getActivityInterface().getCompanions();

        for(CompanionVO companionVO : companions){
            if(companionVO.getmCompanionid().equals(user_id)){
                userName = companionVO.getCompanionUserProfile().getmFirstName();
                break;
            }
        }

       /* for(AccountsVO account:accounts){
            if(account.getTravelpreferenceprofile().getId().equals(user_id)){

                userName = account.getTravelpreferenceprofile().getProfilename();
                break;
            }
        }*/

        joinQuery = joinQuery + userName + " to the trip";

        AirportSendValuesVO airportSendValuesVO = new AirportSendValuesVO();
        airportSendValuesVO.setId(tripSolutionId);
        airportSendValuesVO.setQuery(joinQuery);
        airportSendValuesVO.setLatitude("0");
        airportSendValuesVO.setLongitude("0");
        airportSendValuesVO.setTravelpreferenceprofileid(user_id);


        ArrayList<AirportSendValuesVO> airportSendValuesVOs = new ArrayList<>();
        airportSendValuesVOs.add(airportSendValuesVO);
        addCompanionToQuery(airportSendValuesVOs);

        handleHGBMessageMe(joinQuery);
        args.putString(HGBConstants.BUNDLE_ADD_COMPANION_ID, null);
    }

    private void addCompanionToQuery(ArrayList<AirportSendValuesVO> airportSendValuesVOs){

        ConnectionManager.getInstance(getActivity()).ItineraryCNCAddCompanionPost(airportSendValuesVOs,  new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                handleHGBMessagesViews(userTraveler);
                getActivityInterface().setTravelOrder(userTraveler);
             //   clearCNCscreen = false;
                args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, false);

            }
            @Override
            public void onError(Object data) {

                ErrorMessage(data);
                handleHGBMessage(getResources().getString(R.string.cnc_error));
                CNCFragment.this.airportSendValuesVOs.clear();
            }
        });
    }



    private void startTutorial() {

        if(mHGBPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.HGB_CNC_FIRST_TIME,true)){
            mHGBPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_CNC_FIRST_TIME,false);
            mProfileTutorialText.setVisibility(View.VISIBLE);
            mSpeechTutorialText.setVisibility(View.VISIBLE);
            final Animation fadeOut = new AlphaAnimation(1, 0);
            fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
            fadeOut.setDuration(500);

            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    mProfileTutorialText.setVisibility(View.GONE);
                    mSpeechTutorialText.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    mProfileTutorialText.startAnimation(fadeOut);
                    mSpeechTutorialText.startAnimation(fadeOut);


                }
            }, SPLASH_TIME_OUT);
        }

        mRunnable = new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                changeTutorialText();
                tutorailTexthandler.postDelayed(mRunnable, 2000);
            }
        };


    }


    private void getAccountsProfiles(){
        ConnectionManager.getInstance(getActivity()).getUserProfileAccounts(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<AccountsVO> accounts = ( ArrayList<AccountsVO> )data;
                getActivityInterface().setAccounts(accounts);
                ((MainActivityBottomTabs) getActivity()).editProfileTypeMainToolBar();
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    public void initList() {

        loadCNCList();
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new CNCAdapter(getActivity().getApplicationContext(), getActivityInterface().getCNCItems());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.SetOnItemClickListener(new CNCAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String strText = getActivityInterface().getCNCItems().get(position).getText();
                //TODO this logic needs to change once we get final api
                if (getString(R.string.itinerary_created).equals(strText)
                        || getString(R.string.grid_has_been_updated).equals(strText)) {
                    getFlowInterface().goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(),null);
                }
            }
        });





    }

    private void loadCNCList() {
        String strCNCList = mHGBPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST, "");

//        if ("".equals(strCNCList) && getActivityInterface().getCNCItems()== null ||
//                strCNCList==null && getActivityInterface().getCNCItems()== null) {
        if ((strCNCList.equals("") || strCNCList.equals("null")) && getActivityInterface().getCNCItems()== null ) {
            Resources res = getResources();
            String userName = "";
            if( getActivityInterface().getCurrentUser() != null){
                userName = getActivityInterface().getCurrentUser().getFirstname();
            }

            String text = String.format(res.getString(R.string.default_cnc_message),userName );

            getActivityInterface().addCNCItem(new CNCItem(text, CNCAdapter.HGB_ITEM));
            mTextTutoralHeader.setVisibility(View.VISIBLE);
            mTextTutoralBody.setVisibility(View.VISIBLE);
        } else {

            if (getActivityInterface().getCNCItems() == null) {
                try {
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<CNCItem>>() {
                    }.getType();
                    ArrayList<CNCItem> posts = (ArrayList<CNCItem>) gson.fromJson(strCNCList, listType);
                    getActivityInterface().setCNCItems(posts);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void init(View view) {

 /*       cnc_fragment_trip_settings = (Button)view.findViewById(R.id.cnc_fragment_trip_settings);
        cnc_fragment_trip_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               *//* getFlowInterface().closeRightPane();*//*
                getFlowInterface().goToFragment(ToolBarNavEnum.TREVEL_PREFERENCE.getNavNumber(), null);
            }
        });*/

        mRecyclerView = (RecyclerView) view.findViewById(R.id.cnc_recycler_view);
        mEditText = (FontEditTextView) view.findViewById(R.id.cnc_edit_text);
        mMicImageView = (ImageView) view.findViewById(R.id.cnc_mic);
        mSendTextView = (FontTextView) view.findViewById(R.id.cnc_send);


        mTextTutoralHeader = (FontTextView) view.findViewById(R.id.text_tutorial_header);
        mTextTutoralBody = (FontTextView)view.findViewById(R.id.text_tutorial_body);


        mSendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMessage = mEditText.getText().toString();
                handleMyMessage(strMessage);
            }
        });




        mProfileTutorialText = (FontTextView)view.findViewById(R.id.profile_tutorial);
        mSpeechTutorialText = (FontTextView)view.findViewById(R.id.speak_tutorial);


        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    setMessageSendingState(true);
                } else {
                    setMessageSendingState(false);
                }
            }
        });

        mMicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVoiceInterface().openVoiceToTextControl();
            }
        });

    }


    public void handleMyMessage(final String strMessage) {
        stopTextTutorial();
        mTextTutoralHeader.setVisibility(View.GONE);
        mTextTutoralBody.setVisibility(View.GONE);
        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.ME_ITEM));
        addWaitingItem();
        mAdapter.notifyDataSetChanged();
        resetMessageEditText();
     //   enterCNCMessage(strMessage);

        HGBTranslate.translateQueary(getActivity().getApplicationContext(),strMessage, new HGBTranslateInterface() {
            @Override
            public void onSuccess(String message) {
                enterCNCMessage(message);
            }

            @Override
            public void onError(String error) {
                enterCNCMessage(strMessage);
            }
        });



    }

    private void enterCNCMessage(final String strMessage) {



        clearCNCscreen = args.getBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
        if(!clearCNCscreen) {
            sendCNCMessageToServer(strMessage);
        } else {


            sendMessageToServer(strMessage, new iAfterServer() {

                @Override
                public void serverFinished(AirportServerResultVO airportResult) {
                    ArrayList<ResponsesVO> responses = airportResult.getResponses();
                    maxAirportSize = 0;//responses.size();
                   /* for (ResponsesVO response : responses) {
                   //     if (response.getType().equals("City") || response.getType().equals("AirportCode") || response.getType().equals("AirportName") ) {
                            maxAirportSize = maxAirportSize + 1;
                     //   }
                    }*/
                    maxAirportSize = responses.size();


                    for (ResponsesVO response: responses) {
                      //  if(response.getType().equals("City") || response.getType().equals("AirportCode") ) {
                            String airport = getResources().getString(R.string.cnc_choose_airport);
                            airport = airport + " " + response.getValue() + "?";
                            AirportSendValuesVO  airportSendValuesVO = new AirportSendValuesVO();
                            airportSendValuesVO.setQuery(strMessage);

                            airportSendValuesVO.setTravelpreferenceprofileid(getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId());
                            airportSendValuesVO.setType(response.getType());
                            airportSendValuesVO.setStart(response.getPositionVO().getStart());
                            airportSendValuesVO.setEnd(response.getPositionVO().getEnd());
                            airportSendValuesVO.setValue(response.getValue());


                            final ArrayList<AirportResultsVO> results = response.getResults();
                            airportSendValuesVO.setResults(results);

                           // if (response.getType().equals("City") || response.getType().equals("AirportName")  ) {
                                int centerValue = 0;
                                String[] titleArray = new String[results.size()];
                                for (int j = 0; j < results.size(); j++) {
                                    titleArray[j] = results.get(j).getName();
                                    if(results.get(j).isdefault()){
                                        centerValue = j;
                                    }
                                }

                                airportSendValuesVO.setCenteredItem(centerValue);
                                airportSendValuesVO.setTitleArray(titleArray);
                                airportSendValuesVOs.add(airportSendValuesVO);



                    }
                    popupDialogForAirports();
                }

            });
        }
    }


    private void popupDialogForAirports(){


        for(int j = airportSendValuesVOs.size()-1;j>=0;j--){
           final AirportSendValuesVO airportSendValueVO = airportSendValuesVOs.get(j);
            String airport = getResources().getString(R.string.cnc_choose_airport);
            airport = airport + " " + airportSendValueVO.getValue() + "?";


            if(airportSendValueVO.getType().equals("AirportCode")){
                sendUserAnswearToServer(airportSendValueVO.getResults().get(0));

            }else {

                HGBUtility.showPikerDialog(airportSendValueVO.getCenteredItem(), null, getActivity(), airport,
                        airportSendValueVO.getTitleArray(), 0, airportSendValueVO.getResults().size() - 1, new PopUpAlertStringCB() {

                            @Override
                            public void itemSelected(String inputItem) {

                                AirportResultsVO choosenAirport = findChoosenAirport(inputItem, airportSendValueVO.getResults());
                                sendUserAnswearToServer(choosenAirport);
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        }, false);
            }
        }


    }



    private void sendUserAnswearToServer(AirportResultsVO choosenAirport){
        selectedCount = selectedCount+1;
        args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, false);


        for(AirportSendValuesVO airportSendValuesVO :airportSendValuesVOs){

            ArrayList<AirportResultsVO> results = airportSendValuesVO.getResults();


            for (AirportResultsVO result : results){
                if(result.getId().equals(choosenAirport.getId())){
                    airportSendValuesVO.setId(choosenAirport.getId());
                    String location = HGBUtility.getLocation(getActivity(), false);


                    if (location != null && getActivityInterface().getTravelOrder() != null) {
                        String[] locationArr = location.split("&");
                        getActivityInterface().getTravelOrder().setLocation(locationArr);
                    }

                    locationArr = location.split("&");
                    airportSendValuesVO.setLatitude(locationArr[0]);
                    airportSendValuesVO.setLongitude(locationArr[1]);
                }
            }

        }



        if (maxAirportSize == selectedCount) {
            sendVOForIternarary(airportSendValuesVOs);
        }

    }


    interface iAfterServer {
        void serverFinished(AirportServerResultVO airportResult);
    }

    public void handleHGBMessage(String strMessage) {
        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.HGB_ITEM));
        removeWaitingItem();
    }

    public void handleHGBMessageMe(String strMessage) {
        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.ME_ITEM));
        addWaitingItem();
        //removeWaitingItem();
    }

    private void sendMessageToServer(final String strMessage,final iAfterServer iserverFinished) {
        ConnectionManager.getInstance(getActivity()).ItineraryCNCSearchGet(strMessage, new ConnectionManager.ServerRequestListener() {

                    @Override
                    public void onSuccess(Object data) {
                        AirportServerResultVO airportResult = (AirportServerResultVO) data;
                        iserverFinished.serverFinished(airportResult);

                    }

                    @Override
                    public void onError(Object data) {
                        handleHGBMessage(getResources().getString(R.string.cnc_error));
                        ErrorMessage(data);


                    }
                }
        );
    }


    private void sendCNCMessageToServer(String strMessage){
        ArrayList<AirportSendValuesVO> airportSendValuesVOsTemp = new ArrayList<AirportSendValuesVO>();
        AirportSendValuesVO airportSendValuesVO = new AirportSendValuesVO();
        ArrayList<CNCItem> cncItems = getActivityInterface().getCNCItems();


        if(!cncItems.isEmpty() && cncItems.size()>1){

            UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
            String solutionId = travelOrder.getmSolutionID();
            airportSendValuesVO.setQuery(strMessage);
            airportSendValuesVO.setId(solutionId);
            airportSendValuesVO.setTravelpreferenceprofileid(getActivityInterface().getCurrentUser().getUserprofileid());

            airportSendValuesVOsTemp.add(airportSendValuesVO);

            ConnectionManager.getInstance(getActivity()).ItineraryCNCAddCompanionPost(airportSendValuesVOsTemp,  new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                    handleHGBMessagesViews(userTraveler);
                    getActivityInterface().setTravelOrder(userTraveler);

                }
                @Override
                public void onError(Object data) {
                    ErrorMessage(data);
                    handleHGBMessage(getResources().getString(R.string.cnc_error));
                    CNCFragment.this.airportSendValuesVOs.clear();
                }
            });
        }
    }


    private void handleHGBMessagesViews(UserTravelMainVO userTraveler){
        if(userTraveler.getItems().size() <=0){
            handleHGBMessage(getString(R.string.itinerary_no_items));
        }
        else if (getActivityInterface().getSolutionID() == null) {
            handleHGBMessage(getString(R.string.itinerary_created));
        }else{
            handleHGBMessage(getString(R.string.grid_has_been_updated));
        }

        airportSendValuesVOs.clear();
    }

    private void sendVOForIternarary(ArrayList<AirportSendValuesVO> airportSendValuesVO){

        ConnectionManager.getInstance(getActivity()).ItineraryCNCSearchPost(airportSendValuesVO,  new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                handleHGBMessagesViews(userTraveler);
                getActivityInterface().setTravelOrder(userTraveler);

            }
            @Override
            public void onError(Object data) {
                ErrorMessage(data);
                handleHGBMessage(getResources().getString(R.string.cnc_error));
                airportSendValuesVOs.clear();
            }
        });
    }



    private AirportResultsVO findChoosenAirport(String choosenAirport, ArrayList<AirportResultsVO> results){

 /*       for(AirportSendValuesVO airportSendValueVO:airportSendValuesVOs){
            ArrayList<AirportResultsVO> resultsValue = airportSendValueVO.getResults();
            for (AirportResultsVO airportResults : resultsValue){
                if(airportResults.getAirportname().equals(choosenAirport)){
                    return airportResults;
                }
            }

        }*/

        for (AirportResultsVO airportResults : results){
            if(airportResults.getName().equals(choosenAirport)){
                return airportResults;
            }
        }
        return null;
    }


    private void resetMessageEditText() {
        mEditText.setText("");
        mRecyclerView.scrollToPosition(getActivityInterface().getCNCItems().size() - 1);
        HGBUtility.hideKeyboard(getActivity().getApplicationContext(), mEditText);
    }

    private void setMessageSendingState(boolean b) {
        if (b) {
            mMicImageView.setVisibility(View.GONE);
            mSendTextView.setVisibility(View.VISIBLE);
        } else {
            mMicImageView.setVisibility(View.VISIBLE);
            mSendTextView.setVisibility(View.GONE);
        }
    }

    private void addWaitingItem() {
        removeWaitingItem();
        getActivityInterface().getCNCItems().add(new CNCItem(CNCAdapter.WAITING_ITEM));
    }

    private void removeWaitingItem() {
        Iterator<CNCItem> iter = getActivityInterface().getCNCItems().iterator();
        while (iter.hasNext()) {
            if (iter.next().getType() == CNCAdapter.WAITING_ITEM) {
                iter.remove();
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    public void requestFocusOnMessage(){
        mEditText.requestFocus();
    }

    public void  startTutorialText(){

        tutorailTexthandler.postDelayed(mRunnable,2000);
    }

    private void changeTutorialText() {

        Animation fadeOut = new AlphaAnimation(1, 0);
        final Animation fadeIn = new AlphaAnimation(0, 1);

        fadeOut.setDuration(200);
        fadeIn.setDuration(200);
        mTextTutoralBody.startAnimation(fadeOut);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(account_settings.length ==mTutorialTextNumber){
                    mTutorialTextNumber = 0;
                }
                mTextTutoralBody.setText(account_settings[mTutorialTextNumber]);
                mTextTutoralBody.startAnimation(fadeIn);

                mTutorialTextNumber++;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    public void stopTextTutorial(){
        tutorailTexthandler.removeCallbacks(mRunnable);
    }


    @Override
    public void onDestroyView() {

        try {
            Gson gsonback = new Gson();
            String json = gsonback.toJson(getActivityInterface().getCNCItems());
            // When user exit the app, next time hi will see his itirnarary
            mHGBPrefrenceManager.putStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST, json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroyView();
    }
}
