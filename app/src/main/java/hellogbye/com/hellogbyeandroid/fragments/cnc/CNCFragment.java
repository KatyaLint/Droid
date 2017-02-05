/*
package hellogbye.com.hellogbyeandroid.fragments.cnc;


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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.CNCAdapter;
import hellogbye.com.hellogbyeandroid.adapters.preferencesadapter.PreferencesSettingsPreferencesCheckAdapter;
import hellogbye.com.hellogbyeandroid.fragments.ChangeTripName;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.TitleNameChange;
import hellogbye.com.hellogbyeandroid.fragments.preferences.PreferenceSettingsFragment;
import hellogbye.com.hellogbyeandroid.fragments.preferences.preferencespopup.UserProfilePreferences;
import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportResultsVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportServerResultVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.ResponsesVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionUserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.ConversationVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.models.vo.profiles.DefaultsProfilesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.signalr.SignalRService;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBTranslate;
import hellogbye.com.hellogbyeandroid.utilities.HGBTranslateInterface;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityNetwork;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;


public class CNCFragment extends HGBAbstractFragment implements TitleNameChange {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CNCAdapter mCNCAdapter;
    private EditText mEditText;
    private ImageView mMicImageView;
    private FontTextView mSendTextView;
    private HGBPreferencesManager mHGBPrefrenceManager;
    //private FontTextView mProfileTutorialText;
    private FontTextView mSpeechTutorialText;

    private String[] locationArr;
   // private Button cnc_fragment_trip_settings;
    private static int SPLASH_TIME_OUT = 5000;
    private static int CNC_TUTORIAL_TYPING_TEXT_TIME_DURATION = 3000;
    //  private CostumeToolBar mToolbar;

    private PreferenceSettingsFragment.OnItemClickListener editClickCB;
    private ImageButton toolbar_go_to_iternerary;
    private ImageButton cncDissmissImageButton;

    private FontTextView mTextTutoralBody;
    private LinearLayout cnc_text_tutorial_ll;

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


   // private LinearLayout tool_bar_profile_name;

//    private FontTextView cnc_start_planing_text;
    private FontTextView itirnarary_title_Bar;
    private PreferencesSettingsPreferencesCheckAdapter mRadioPreferencesAdapter;
    private ArrayList<DefaultsProfilesVO> accountDefaultSettings;
    private UserProfilePreferences userProfilePreferences;

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

        account_settings = getResources().getStringArray(R.array.tutorial_arr);

        View rootView = inflater.inflate(R.layout.cnc_fragment_layout, container, false);
        args = getArguments();

        clearCNCscreen = args.getBoolean(HGBConstants.CNC_CLEAR_CHAT);

        init(rootView);
       // startTutorial();
        startTutorialText();

        if(clearCNCscreen){
            //   startTutorialText();
            clearCNCItems();

            //This is to ckeck and display tutorial this version was cancelled waiting for new one

            setTutorialTextVisibility(true);
        }else{
            setTutorialTextVisibility(false);
        }

        userProfilePreferences = new UserProfilePreferences();
        userProfilePreferences.getAccountsProfiles(getActivity(), getActivityInterface());


        initList();


        String solution_id = args.getString(HGBConstants.SOLUTION_ITINERARY_ID);
        if(solution_id != null){
            addWaitingItem();
            getCurrentItinerary(solution_id);
        }

        joinCompanionToTravel();
        final ImageButton toolbar_profile_popup = ((MainActivityBottomTabs)getActivity()).getToolbarProfilePopup();
        toolbar_profile_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfilePreferences.getUserSettings(getActivity(), getFlowInterface(), getActivityInterface());
            }
        });
       // tool_bar_profile_name = ((MainActivityBottomTabs)getActivity()).getToolBarProfileChange();



        ImageButton newIteneraryImageButton = ((MainActivityBottomTabs) getActivity()).getToolbar_new_iterneraryCnc();
        newIteneraryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCNCItems();
            }
        });

        itirnarary_title_Bar = ((MainActivityBottomTabs) getActivity()).getItirnaryTitleBar();


        UserTravelMainVO travelerOrder = getActivityInterface().getTravelOrder();
        if(travelerOrder != null && travelerOrder.getmSolutionName()!= null){
            setSolutionNameForItirnarary();

        }else{
            setTextForTrip("New Trip");
        }
        titleChangeName();

        itirnarary_title_Bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // clearCNCItems();
            }
        });



      //  showMessagesToUser();
        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFlowInterface().enableFullScreen(false);

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

        //Kate check
        args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
        getActivityInterface().setTravelOrder(new UserTravelMainVO());
        setTextForTrip("New Trip");
        mHGBPrefrenceManager.removeKey(HGBPreferencesManager.HGB_CNC_LIST);
        mHGBPrefrenceManager.removeKey(HGBPreferencesManager.HGB_LAST_TRAVEL_VO);
        initList();
    }


private void setTextForTrip(String name){
    itirnarary_title_Bar = ((MainActivityBottomTabs) getActivity()).getItirnaryTitleBar();
    itirnarary_title_Bar.setText(name);
    if(getActivityInterface().getTravelOrder() != null) {
        itirnarary_title_Bar.setTag(getActivityInterface().getTravelOrder().getmSolutionID());
    }
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
        String joinQuery = "Add ";

     //   ArrayList<AccountsVO> accounts = getActivityInterface().getAccounts();
        ArrayList<CompanionVO> companions = getActivityInterface().getCompanions();

        for(CompanionVO companionVO : companions){
            if(companionVO.getmCompanionid().equals(user_id)){
                CompanionUserProfileVO companionProfile = companionVO.getCompanionUserProfile();
                userName = companionProfile.getmFirstName() + " " +companionProfile.getmLastName();
                break;
            }
        }




        joinQuery = joinQuery + userName + " to the trip";

        AirportSendValuesVO airportSendValuesVO = new AirportSendValuesVO();
        airportSendValuesVO.setId(tripSolutionId);
        airportSendValuesVO.setQuery(joinQuery);
        airportSendValuesVO.setLatitude("0");
        airportSendValuesVO.setLongitude("0");
        airportSendValuesVO.setTravelpreferenceprofileid(getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId());


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

             //   ErrorMessage(data);
                handleHGBMessage(getResources().getString(R.string.cnc_error));
                CNCFragment.this.airportSendValuesVOs.clear();
            }
        });
    }



  */
/*  private void startTutorial() {

        if(mHGBPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.HGB_CNC_FIRST_TIME,true)){
            mHGBPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_CNC_FIRST_TIME,false);
        //    mProfileTutorialText.setVisibility(View.VISIBLE);
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

    }*//*






    public void initList() {

        loadCNCList();
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
       mLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };



        mRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<CNCItem> cncItems = getActivityInterface().getCNCItems();

        mCNCAdapter = new CNCAdapter(getActivity().getApplicationContext(), cncItems);
        mRecyclerView.setAdapter(mCNCAdapter);
        mCNCAdapter.SetOnItemClickListener(new CNCAdapter.OnItemClickListener() {
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
        UserProfileVO usersList = getActivityInterface().getCurrentUser();

        mCNCAdapter.setAvatarUserUrl(usersList.getAvatar());
    }


    private void loadCNCList() {


        String strCNCList = mHGBPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST, "");
        if((strCNCList.equals("") || strCNCList.equals("null")) &&  getActivityInterface().getCNCItems()== null){
            Resources res = getResources();
            String userName = "";
            UserProfileVO usersList = getActivityInterface().getCurrentUser();
            if( usersList != null){
                userName = usersList.getFirstname();
            }

            String text = String.format(res.getString(R.string.default_cnc_message),userName );

            getActivityInterface().addCNCItem(new CNCItem(text, CNCAdapter.HGB_ITEM));
            getActivityInterface().addCNCItem(new CNCItem(res.getString(R.string.default_cnc_message_two), CNCAdapter.HGB_ITEM_NO_ICON));
            setTutorialTextVisibility(true);

        }
        else if (getActivityInterface().getCNCItems() == null) {
            try {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<CNCItem>>() {
                }.getType();
                ArrayList<CNCItem> posts = (ArrayList<CNCItem>) gson.fromJson(strCNCList, listType);
                getActivityInterface().setCNCItems(posts);
            } catch (Exception e) {
                e.printStackTrace();
            }
            setTutorialTextVisibility(false);
        }

    }

    private void init(View view) {

        mHGBPrefrenceManager = HGBPreferencesManager.getInstance(getActivity().getApplicationContext());
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cnc_recycler_view);
        mEditText = (FontEditTextView) view.findViewById(R.id.cnc_edit_text);
        mMicImageView = (ImageView) view.findViewById(R.id.cnc_mic);
        mSendTextView = (FontTextView) view.findViewById(R.id.cnc_send);


       // mTextTutoralHeader = (FontTextView) view.findViewById(R.id.text_tutorial_header);
        mTextTutoralBody = (FontTextView)view.findViewById(R.id.text_tutorial_body);
        cnc_text_tutorial_ll = (LinearLayout)view.findViewById(R.id.cnc_text_tutorial_ll);

      //  cnc_start_planing_text = (FontTextView)view.findViewById(R.id.cnc_start_planing_text);

        mSendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMessage = mEditText.getText().toString();



                handleMyMessage(strMessage);
            }
        });




     //   mProfileTutorialText = (FontTextView)view.findViewById(R.id.profile_tutorial);
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



    private void setTutorialTextVisibility(boolean isVisible){
        if(isVisible){
         //   cnc_start_planing_text.setVisibility(View.GONE);
            cnc_text_tutorial_ll.setVisibility(View.VISIBLE);
        }else{
        //    cnc_start_planing_text.setVisibility(View.VISIBLE);
            cnc_text_tutorial_ll.setVisibility(View.GONE);
        }
    }

    public void handleMyMessage(final String strMessageReceived) {
        //stopTextTutorial();
        //setTutorialTextVisibility(false);

        getActivityInterface().addCNCItem(new CNCItem(strMessageReceived.trim(), CNCAdapter.ME_ITEM));
        addWaitingItem();
        mCNCAdapter.notifyDataSetChanged();
        resetMessageEditText();

     //   enterCNCMessage(strMessage);

        HGBTranslate.translateQueary(getActivity().getApplicationContext(),strMessageReceived, new HGBTranslateInterface() {
            @Override
            public void onSuccess(String message) {
                enterCNCMessage(message);
            }

            @Override
            public void onError(String SignalRRrror) {
                enterCNCMessage(strMessageReceived);
            }
        });



    }

    private void enterCNCMessage(final String strMessage) {
        setTutorialTextVisibility(false);
        clearCNCscreen = args.getBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
        if(!clearCNCscreen) {
            sendCNCMessageToServer(strMessage);
        } else {

            selectedCount = 0;
            sendMessageToServer(strMessage, new iAfterServer() {

                @Override
                public void serverFinished(AirportServerResultVO airportResult) {

                    ArrayList<ResponsesVO> responses = airportResult.getResponses();
                    maxAirportSize = 0;//responses.size();
                    for (ResponsesVO response : responses) {
                   //     if (response.getType().equals("City") || response.getType().equals("AirportCode") || response.getType().equals("AirportName") ) {
                            maxAirportSize = maxAirportSize + 1;
                     //   }
                    }

                    maxAirportSize = responses.size();


                    for (ResponsesVO response: responses) {

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
            String airport = "";
            if(airportSendValueVO.getType().equals("Name")){

                airport = getResources().getString(R.string.cnc_choose_companion_name);
                airport = airport + " " + airportSendValueVO.getValue() + "?";
            }
            else if(airportSendValueVO.getType().equals("City") || airportSendValueVO.getType().equals("CountryName")){
                airport = getResources().getString(R.string.cnc_choose_airport);
                airport = airport + " " + airportSendValueVO.getValue() + "?";
            }

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
                if(result.getId().equals(choosenAirport.getId())) {
                    airportSendValuesVO.setId(choosenAirport.getId());

                    String location = HGBUtilityNetwork.getLocation(getActivity(), false);


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

    @Override
    public void titleChangeName() {
        ChangeTripName.onClickListener(getActivity(),getActivityInterface().getSolutionID(), getActivityInterface().getTravelOrder());
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
        System.out.println("Kate sendCNCMessageToServer 2" );

        //   Kate


        String preferencesProfileId = getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId();


        ConnectionManager.getInstance(getActivity()).getItineraryCNCSearch(strMessage, new ConnectionManager.ServerRequestListener() {

                    @Override
                    public void onSuccess(Object data) {
                        AirportServerResultVO airportResult = (AirportServerResultVO) data;
                        iserverFinished.serverFinished(airportResult);

                    }

                    @Override
                    public void onError(Object data) {
                        handleHGBMessage(getResources().getString(R.string.cnc_error));
                        //    ErrorMessage(data);


                    }
                }
        );


    }


    private void sendCNCMessageToServer(String strMessage){
        System.out.println("Kate sendCNCMessageToServer");
        ArrayList<AirportSendValuesVO> airportSendValuesVOsTemp = new ArrayList<AirportSendValuesVO>();
        AirportSendValuesVO airportSendValuesVO = new AirportSendValuesVO();
        ArrayList<CNCItem> cncItems = getActivityInterface().getCNCItems();


        if(!cncItems.isEmpty() && cncItems.size()>1){

            UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();

            String solutionId = travelOrder.getmSolutionID();
            airportSendValuesVO.setId(solutionId);

            airportSendValuesVO.setQuery(strMessage);

            airportSendValuesVO.setTravelpreferenceprofileid(getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId());

            airportSendValuesVOsTemp.add(airportSendValuesVO);




         //   Kate


            ConnectionManager.getInstance(getActivity()).ItineraryCNCAddCompanionPost(airportSendValuesVOsTemp,  new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                    handleHGBMessagesViews(userTraveler);
                    getActivityInterface().setTravelOrder(userTraveler);

                }
                @Override
                public void onError(Object data) {
                  //  ErrorMessage(data);
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

        ConnectionManager.getInstance(getActivity()).postItineraryCNCSearch(airportSendValuesVO,  new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                handleHGBMessagesViews(userTraveler);
                getActivityInterface().setTravelOrder(userTraveler);
                setSolutionNameForItirnarary();

            }
            @Override
            public void onError(Object data) {
             //   ErrorMessage(data);
                handleHGBMessage(getResources().getString(R.string.cnc_error));
                airportSendValuesVOs.clear();
            }
        });
    }


    private void setSolutionNameForItirnarary() {
        String solutionName = getActivityInterface().getTravelOrder().getmSolutionName();
        setTextForTrip(solutionName);
    }


    private AirportResultsVO findChoosenAirport(String choosenAirport, ArrayList<AirportResultsVO> results){

       for(AirportSendValuesVO airportSendValueVO:airportSendValuesVOs){
            ArrayList<AirportResultsVO> resultsValue = airportSendValueVO.getResults();
            for (AirportResultsVO airportResults : resultsValue){
                if(airportResults.getAirportname().equals(choosenAirport)){
                    return airportResults;
                }
            }

        }


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
        mCNCAdapter.notifyDataSetChanged();
    }

    public void requestFocusOnMessage(){
        mEditText.requestFocus();
    }


    private void updateText(){

        if(account_settings.length ==mTutorialTextNumber){
            mTutorialTextNumber = 0;
        }
        mTextTutoralBody.setText(account_settings[mTutorialTextNumber]);

        mTutorialTextNumber++;
    }

    private void  startTutorialText(){
        mRunnable = new Runnable() {
            @Override
            public void run() {
                changeTutorialText();
                tutorailTexthandler.postDelayed(mRunnable, CNC_TUTORIAL_TYPING_TEXT_TIME_DURATION);
            }
        };
    }

    @Override
    public void onResume() {
        tutorailTexthandler.post(mRunnable);
        super.onResume();
    }

    @Override
    public void onStop() {
        tutorailTexthandler.removeCallbacks(mRunnable);
        super.onStop();
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
                updateText();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
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
*/
