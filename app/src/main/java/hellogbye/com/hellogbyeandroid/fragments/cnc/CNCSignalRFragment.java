package hellogbye.com.hellogbyeandroid.fragments.cnc;

/**
 * Created by nyawka on 2/1/17.
 */

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.activities.RefreshComplete;
import hellogbye.com.hellogbyeandroid.adapters.cncadapters.CNCAdapter;
import hellogbye.com.hellogbyeandroid.adapters.cncadapters.CNCMenuAdapter;
import hellogbye.com.hellogbyeandroid.fragments.ChangeTripName;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.TitleNameChange;
import hellogbye.com.hellogbyeandroid.fragments.preferences.preferencespopup.UserProfilePreferences;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.vo.cnc.CNCItem;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportResultsVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.ResponsesVO;
import hellogbye.com.hellogbyeandroid.models.vo.cnc.CNCExamplesVO;
import hellogbye.com.hellogbyeandroid.models.vo.cnc.CNCTutorialsVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionUserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.companion.CompanionVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.ConversationVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.signalr.AirportServerResultCNCVO;
import hellogbye.com.hellogbyeandroid.signalr.SignalRServerResponseForHighlightVO;
import hellogbye.com.hellogbyeandroid.signalr.SignalRService;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBTranslate;
import hellogbye.com.hellogbyeandroid.utilities.HGBTranslateInterface;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityNetwork;
import hellogbye.com.hellogbyeandroid.views.FontEditTextView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by arisprung on 11/3/15.
 */


public class CNCSignalRFragment extends HGBAbstractFragment implements TitleNameChange {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CNCAdapter mCNCAdapter;
    private EditText mEditText;
    private ImageView mMicImageView;
    private FontTextView mSendTextView;
    private HGBPreferencesManager mHGBPrefrenceManager;

    private String[] locationArr;
    private static int CNC_TUTORIAL_TYPING_TEXT_TIME_DURATION = 3000;


    private FontTextView mTextTutoralBody;
    private LinearLayout cnc_text_tutorial_ll;

    private Handler tutorailTexthandler = new Handler();
    private Runnable mRunnable;
    private String[] account_settings;
    private int mTutorialTextNumber = 0;

    private ArrayList<AirportSendValuesVO> airportSendValuesVOs = new ArrayList<>();
    private int maxAirportSize = 0;
    private boolean clearCNCscreen;
    private Bundle args;
    private int selectedCount = 0;

    private FontTextView itirnarary_title_Bar;

    private LinearLayout cnc_fragment_profile_line;
    private FontTextView cnc_fragment_profile_name;
    private UserProfilePreferences userProfilePreferences;
    private CNCTutorialsVO cncTutorials;
    private String tutorialMessage = "/examples";
    private String tutorialVideoMessage = "/tutorials";

    public static Fragment newInstance(int position) {
        Fragment fragment = new CNCSignalRFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public interface IProfileUpdated{
        void profileUpdated(String profilename);
    }

    public interface IClearCNC{
        void clearCNCScreen();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        final Information_Popup_View information_popup_view = new Information_Popup_View(getActivity(), getFlowInterface(), getActivityInterface(),new IClearCNC() {
            @Override
            public void clearCNCScreen() {
                clearCNCItems();
            }
        });

        cncTutorials = getActivityInterface().getCNCTutorialsVOs();

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

        userProfilePreferences = ((MainActivityBottomTabs)getActivity()).getUserProfilePreferences();
        userProfilePreferences.getAccountsProfiles(getActivity(), getActivityInterface());

        initList();



        String solution_id = args.getString(HGBConstants.SOLUTION_ITINERARY_ID);
        if(solution_id != null){
            addWaitingItem();
            getCurrentItinerary(solution_id);
        }

        joinCompanionToTravel();

     /*   final ImageButton toolbar_profile_popup = ((MainActivityBottomTabs)getActivity()).getToolbarProfilePopup();
        toolbar_profile_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfilePreferences.getUserSettings(getActivity(), getFlowInterface(), getActivityInterface());
            }
        });*/


        // tool_bar_profile_name = ((MainActivityBottomTabs)getActivity()).getToolBarProfileChange();



        ImageButton newIteneraryImageButton = ((MainActivityBottomTabs) getActivity()).getToolbar_new_iterneraryCnc();
        newIteneraryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information_popup_view.freeUserPopUp();


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


        SignalRService service = ((MainActivityBottomTabs) getActivity()).getSignalRService();
        service.setCNCHiglightResponceCB(higlightReceivedFromServer);
     //   service.cncSignalRMessagesInvokation();

        cnc_fragment_profile_line =  (LinearLayout)rootView.findViewById(R.id.cnc_fragment_profile_line); //((MainActivityBottomTabs)getActivity()).getCnc_fragment_profile_line();

        cnc_fragment_profile_name = (FontTextView)rootView.findViewById(R.id.cnc_fragment_profile_name);//((MainActivityBottomTabs)getActivity()).getCnc_fragment_profile_name();

/*
        cnc_fragment_profile_line = (LinearLayout)rootView.findViewById(R.id.cnc_fragment_profile_line);
        cnc_fragment_profile_name = (FontTextView)rootView.findViewById(R.id.cnc_fragment_profile_name);
*/

        String profilePreferencesActiveName = userProfilePreferences.getActiveAccount(getActivityInterface());

       cnc_fragment_profile_name.setText(profilePreferencesActiveName);

        String profilePreferencesActiveId =   getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId();

        FontTextView my_trip_profile = ((MainActivityBottomTabs) getActivity()).getMyTripProfile();
        my_trip_profile.setTag(profilePreferencesActiveId);

        cnc_fragment_profile_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userProfilePreferences.getUserSettings(getActivity(), getFlowInterface(), getActivityInterface(), new IProfileUpdated() {

                    @Override
                    public void profileUpdated(String profilename) {
                        cnc_fragment_profile_name.setText(profilename);
                    }
                }, true);
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

        ArrayList<ConversationVO> conversations = getActivityInterface().getTravelOrder().getConversation(); //getActivityInterface().getCNCItems();
        ArrayList<CNCItem> cncItems = getActivityInterface().getCNCItems();

        for(ConversationVO conversation : conversations){
            handleHGBMessageMe(conversation.getmMessage());
            handleHGBMessage(getString(R.string.itinerary_created), CNCAdapter.HGB_ITEM_SELECTED);
        }
    }



    private void getCurrentItinerary(final String solutionItineraryId){

        ((MainActivityBottomTabs)getActivity()).callRefreshItineraryWithCallback(ToolBarNavEnum.CNC.getNavNumber(), new RefreshComplete() {
            @Override
            public void onRefreshSuccess(Object data) {
                getActivityInterface().setCNCItems(null);
                initList();
                args.putString(HGBConstants.SOLUTION_ITINERARY_ID,null);
                args.putBoolean(HGBConstants.CNC_CLEAR_CHAT,false);


                String signalrConnectionID = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_ID, "");
                String userId = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_PROFILE_ID, "");



                ((MainActivityBottomTabs)getActivity()).postToSignalR(signalrConnectionID, userId, solutionItineraryId);

                addUserConversation();
                //Kate
                getPostBookingItinerary();
            }

            @Override
            public void onRefreshError(Object data) {
                ErrorMessage(data);
                removeWaitingItem();
            }
        }, solutionItineraryId);



      /*  ConnectionManager.getInstance(getActivity()).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;

                getActivityInterface().setTravelOrder(userTravelMainVO);

                getActivityInterface().setCNCItems(null);

                initList();
               // getActivityInterface().setCNCItems(null);
                args.putString(HGBConstants.SOLUTION_ITINERARY_ID,null);
                args.putBoolean(HGBConstants.CNC_CLEAR_CHAT,false);


                String signalrConnectionID = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_ID, "");
                String userId = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_PROFILE_ID, "");

                postToSignalR(signalrConnectionID, userId, solutionId);

                addUserConversation();
                //Kate
                getPostBookingItinerary();

                //getFlowInterface().goToFragment(ToolBarNavEnum.ITINERARY.getNavNumber(), null);
             //   handleHGBMessage(getString(R.string.itinerary_created));
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
                removeWaitingItem();
            }
        });*/
    }


    private void getPostBookingItinerary(){

        UserTravelMainVO userOrder = getActivityInterface().getTravelOrder();
        boolean isBookedVersion = userOrder.getmHasbookedversion();
        if(!isBookedVersion){
            return;
        }

        String solutionID = userOrder.getmSolutionID();
        String signalrConnectionID = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_ID, "");
        ConnectionManager.getInstance(getActivity()).getBookedItinerary(solutionID,signalrConnectionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;

                getActivityInterface().setBookedTravelOrder(userTravelMainVO);
                getFlowInterface().goToFragment(ToolBarNavEnum.ITINERARY.getNavNumber(), null);
                //   handleHGBMessage(getString(R.string.itinerary_created));
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);

            }
        });

    }


    private void clearCNCItems() {

        getActivityInterface().setCNCItems(null);

        args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, true);
        getActivityInterface().setTravelOrder(new UserTravelMainVO());
        setTextForTrip("New Trip");
        mHGBPrefrenceManager.removeKey(HGBConstants.HGB_CNC_LIST);
        mHGBPrefrenceManager.removeKey(HGBConstants.HGB_LAST_TRAVEL_VO);
        initList();
    }


    private void setTextForTrip(String name){
        itirnarary_title_Bar = ((MainActivityBottomTabs) getActivity()).getItirnaryTitleBar();
        if(name != null && !name.isEmpty()) {
            itirnarary_title_Bar.setText(name);
        }else{
            itirnarary_title_Bar.setText("New Trip");
        }
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
        airportSendValuesVO.setTravelpreferenceprofileid(getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId());


        ArrayList<AirportSendValuesVO> airportSendValuesVOsCompanion = new ArrayList<>();
        airportSendValuesVOsCompanion.add(airportSendValuesVO);
        addCompanionToQuery(airportSendValuesVOsCompanion);

        handleHGBMessageMe(joinQuery);
        args.putString(HGBConstants.BUNDLE_ADD_COMPANION_ID, null);
    }

    private void addCompanionToQuery(ArrayList<AirportSendValuesVO> airportSendValuesVOsCompanion){

 /*       ((MainActivityBottomTabs)getActivity()).callRefreshItineraryWithCallback(ToolBarNavEnum.CNC.getNavNumber(), new RefreshComplete() {
            @Override
            public void onRefreshSuccess(Object data) {
                UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                handleHGBMessagesViews(userTraveler);
                getPostBookingItinerary();
                //   clearCNCscreen = false;
                args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, false);

            }

            @Override
            public void onRefreshError(Object data) {
                handleHGBMessage(getResources().getString(R.string.cnc_error), CNCAdapter.HGB_ITEM);
                CNCSignalRFragment.this.airportSendValuesVOs.clear();
            }
        });*/



        ConnectionManager.getInstance(getActivity()).ItineraryCNCAddCompanionPost(airportSendValuesVOsCompanion,  new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                handleHGBMessagesViews(userTraveler);
                getActivityInterface().setTravelOrder(userTraveler);
                getPostBookingItinerary();
                //   clearCNCscreen = false;
                args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, false);

            }
            @Override
            public void onError(Object data) {

                //   ErrorMessage(data);
                handleHGBMessage(getResources().getString(R.string.cnc_error), CNCAdapter.HGB_ITEM);
                CNCSignalRFragment.this.airportSendValuesVOs.clear();
            }
        });
    }



/*    private void startTutorial() {

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

    }*/




    public void initList() {

        loadCNCList();
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
 /*       mLayoutManager = new LinearLayoutManager(getContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
*/

        mRecyclerView.setLayoutManager(mLayoutManager);



        //all cnc items
        ArrayList<CNCItem> cncItems = getActivityInterface().getCNCItems();


        mCNCAdapter = new CNCAdapter(getActivity(),getActivity().getApplicationContext(), cncItems);

       /* cncItemsAdapterList.addAll(cncItems);
        mCNCAdapter.setItems(cncItemsAdapterList);*/


        mRecyclerView.setAdapter(mCNCAdapter);
        mCNCAdapter.SetOnItemClickListener(new CNCAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                String strText = getActivityInterface().getCNCItems().get(position).getText();
                //TODO this logic needs to change once we get final api
                if (getString(R.string.itinerary_created).equals(strText)
                        || getString(R.string.grid_has_been_updated).equals(strText)) {
                    getFlowInterface().goToFragment(ToolBarNavEnum.ITINERARY.getNavNumber(),null);
                }
            }
        });

        UserProfileVO usersList = getActivityInterface().getCurrentUser();

        mCNCAdapter.setAvatarUserUrl(usersList.getAvatar());
        mCNCAdapter.notifyDataSetChanged();
    }


    private void loadCNCList() {


        String strCNCList = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_CNC_LIST, "");

        if((strCNCList.equals("") || strCNCList.equals("null")) &&  getActivityInterface().getCNCItems()== null){

            Resources res = getResources();
            String userName = "";
            UserProfileVO usersList = getActivityInterface().getCurrentUser();
            if( usersList != null){
                userName = usersList.getFirstname();
            }

          //  String text = String.format(res.getString(R.string.default_cnc_message),userName );

           String default_cnc_message_examples_text = res.getString(R.string.default_cnc_message_examples);

            getActivityInterface().addCNCItem(new CNCItem(default_cnc_message_examples_text, CNCAdapter.HGB_ITEM));
         //   getActivityInterface().addCNCItem(new CNCItem(res.getString(R.string.default_cnc_message_two), CNCAdapter.HGB_ITEM_NO_ICON));
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

               // enterCNCMessage(strMessage);
            }
        });




        //   mProfileTutorialText = (FontTextView)view.findViewById(R.id.profile_tutorial);
     //   mSpeechTutorialText = (FontTextView)view.findViewById(R.id.speak_tutorial);


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
                promptSpeechInput();
               // getVoiceInterface().openVoiceToTextControl();
            }
        });
    }

    private final int REQ_CODE_SPEECH_INPUT = 100;
    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "speach promt");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getActivity().getApplicationContext(),
                   "speach not supported",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    handleMyMessage(result.get(0));

                }
                break;
            }

        }
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



    private void handleMyMessage(final String strMessageReceived) {
        //stopTextTutorial();
        //setTutorialTextVisibility(false);

        String userMessage = strMessageReceived.trim();
        if(userMessage.equals(tutorialMessage)){
            examplesLogics();
            return;
        }else if(userMessage.equals(tutorialVideoMessage)){
            examplesVideoLogics();
            return;
        }
        getActivityInterface().addCNCItem(new CNCItem(userMessage, CNCAdapter.ME_ITEM));
        addWaitingItem();

        mCNCAdapter.notifyDataSetChanged();
        resetMessageEditText();

        enterCNCMessage(strMessageReceived);

//        HGBTranslate.translateQueary(getActivity().getApplicationContext(),strMessageReceived, new HGBTranslateInterface() {
//            @Override
//            public void onSuccess(String message) {
//
//                enterCNCMessage(message);
//            }
//
//            @Override
//            public void onError(String error) {
//
//                if(error == null){
//                    return;
//                }
//                ErrorMessage(error);
//            }
//        });

    }

    private void examplesLogics() {
        getActivityInterface().getCNCItems().clear();

        ArrayList<CNCExamplesVO> examples = cncTutorials.getExamples();

        for(CNCExamplesVO example : examples){
            handleHGBMessage(example.getName(),CNCAdapter.HGB_ITEM_SELECTED);
        }

        mCNCAdapter.notifyDataSetChanged();
        resetMessageEditText();
        setTutorialTextVisibility(false);
    }

    private void examplesVideoLogics(){
        getActivityInterface().getCNCItems().clear();

        ArrayList<CNCExamplesVO> examples = cncTutorials.getExamples();

        for(CNCExamplesVO example : examples){

            handleHGBMessage(example.getName(),CNCAdapter.HGB_ITEM_VIDEO_TUTORIAL);
        }

        mCNCAdapter.notifyDataSetChanged();

        resetMessageEditText();
        setTutorialTextVisibility(false);
    }

    interface iAfterServer {
        void serverFinished(AirportServerResultCNCVO airportResult);
    }

    private void enterCNCMessage(final String strMessage) {
        setTutorialTextVisibility(false);
        clearCNCscreen = args.getBoolean(HGBConstants.CNC_CLEAR_CHAT, true);

        if(!clearCNCscreen) { // not a new message
            sendCNCMessageToServer(strMessage);
        } else { //  a new message
            selectedCount = 0;
          //  sendMessageToServer(strMessage);


            selectedCount = 0;
            sendMessageToServer(strMessage, new iAfterServer() {

                @Override
                public void serverFinished(AirportServerResultCNCVO airportResult) {
                    serverFinishedResult(airportResult);


              /*      ArrayList<ResponsesVO> responses = airportResult.getResponses();
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
                }*/
                }

            });

        }
    }


    private void serverFinishedResult(AirportServerResultCNCVO airportServerResultVO) {





       // ArrayList<ResponsesVO> responses = airportServerResultVO.getHighlightdataVO().getResponses();

        ArrayList<ResponsesVO> responses = airportServerResultVO.getResponses();

        String signalrConnectionID = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_ID, "");
        String userId = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_PROFILE_ID, "");
        String solutionItineraryId = airportServerResultVO.getItineraryid();

        ((MainActivityBottomTabs)getActivity()).postToSignalR(signalrConnectionID, userId, solutionItineraryId);
//        if(responses.isEmpty()){
//
//          //  handleHGBMessage("Please specify your request",CNCAdapter.HGB_ITEM);
//        }
       // ArrayList<ResponsesVO> responses = airportResult.getResponses();
        maxAirportSize = 0;//responses.size();
                   /* for (ResponsesVO response : responses) {
                   //     if (response.getType().equals("City") || response.getType().equals("AirportCode") || response.getType().equals("AirportName") ) {
                            maxAirportSize = maxAirportSize + 1;
                     //   }
                    }*/


        maxAirportSize = responses.size();


        for (ResponsesVO response: responses) {

            AirportSendValuesVO  airportSendValuesVO = new AirportSendValuesVO();
            airportSendValuesVO.setQuery(airportServerResultVO.getQuery());
            airportSendValuesVO.setItineraryid(airportServerResultVO.getItineraryid());
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

                popupCNCMenu(airport, airportSendValueVO);


          /*      HGBUtility.showPikerDialog(airportSendValueVO.getCenteredItem(), null, getActivity(), airport,
                        airportSendValueVO.getTitleArray(), 0, airportSendValueVO.getResults().size() - 1, new PopUpAlertStringCB() {

                            @Override
                            public void itemSelected(String inputItem) {

                                AirportResultsVO choosenAirport = findChoosenAirport(inputItem, airportSendValueVO.getResults());
                                sendUserAnswearToServer(choosenAirport);
                            }

                            @Override
                            public void itemCanceled() {

                            }
                        }, false);*/
            }
        }


    }


    private void popupCNCMenu(String airportTitle, final AirportSendValuesVO airportSendValueVO){


        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.popup_cnc_screen_menu, null);

        FontTextView popup_cnc_screen_title_text = (FontTextView)promptsView.findViewById(R.id.popup_cnc_screen_title_text) ;
        popup_cnc_screen_title_text.setText(airportTitle);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setCustomTitle(promptsView);


        final List<String> data  = Arrays.asList(airportSendValueVO.getTitleArray());
        final CNCMenuAdapter sortPopupAdapter = new CNCMenuAdapter(data);


        View promptsViewTeest = li.inflate(R.layout.popup_alternative_layout_sort, null);
       final ListView user_profile_popup_list_view = (ListView) promptsViewTeest.findViewById(R.id.alternative_popup_sort);

        user_profile_popup_list_view.smoothScrollToPosition(airportSendValueVO.getCenteredItem());
        sortPopupAdapter.setCheckedItemPosition(airportSendValueVO.getCenteredItem());

        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int checked = sortPopupAdapter.getCheckedItemPosition();

                String airportName = airportSendValueVO.getResults().get(checked).getAirportname();
                sendUserAnswearToServer(airportSendValueVO.getResults().get(checked));
              //  removeWaitingItem();

            } });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                removeWaitingItem();

            } });

        //Create alert dialog object via builder
       final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setView(promptsViewTeest);
        alertDialog.setCancelable(false);
        alertDialog.show();


        user_profile_popup_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String airportName = ((CheckedTextView) view.findViewById(R.id.cnc_menu_item)).getText().toString();
                sortPopupAdapter.setCheckedItemPosition(position);
                user_profile_popup_list_view.invalidate();

                AirportResultsVO choosenAirport = findChoosenAirport(airportName, airportSendValueVO.getResults());
                sendUserAnswearToServer(choosenAirport);

                alertDialog.dismiss();

            }
        });

        Button positive_button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);

        if (positive_button != null) {
            positive_button.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_EE3A3C));
        }
        Button negative_button = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        if (negative_button != null) {
            negative_button.setTextColor(ContextCompat.getColor(getContext(), R.color.COLOR_999999));
        }

        user_profile_popup_list_view.setAdapter(sortPopupAdapter);


    }



    private void sendUserAnswearToServer(AirportResultsVO choosenAirport){
        selectedCount = selectedCount+1;
        args.putBoolean(HGBConstants.CNC_CLEAR_CHAT, false);


        for(AirportSendValuesVO airportSendValuesVO :airportSendValuesVOs){

            ArrayList<AirportResultsVO> results = airportSendValuesVO.getResults();


            for (AirportResultsVO result : results){
                if(result.getId().equals(choosenAirport.getId())) {
                    airportSendValuesVO.setId(choosenAirport.getId());
                    airportSendValuesVO.setValue(choosenAirport.getAirportname());

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



    private synchronized void handleHGBMessage(String strMessage,int type ) {
        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(),  type ));//CNCAdapter.HGB_ITEM));
        removeWaitingItem();
    }
    public void handleHGBMessageSelected(String strMessage,int type ) {
        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.HGB_ITEM));
        removeWaitingItem();
    }

    private void handleHGBMessageMe(String strMessage) {
        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.ME_ITEM));
        addWaitingItem();

    }


    public interface IHiglightReceivedFromServer{
        void HiglightReceived(AirportServerResultCNCVO airportServerResultVO);
        void AnswearFromServerToUserChooses(SignalRServerResponseForHighlightVO signalRServerResponseForHighlightVO);
        void SignalRRrror(String error);
        void SignalRCNCConversation(String cncConversation);
    }

    private IHiglightReceivedFromServer higlightReceivedFromServer = new IHiglightReceivedFromServer(){

        @Override
        public void HiglightReceived(final AirportServerResultCNCVO airportServerResultVO) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                   // serverFinishedResult(airportServerResultVO);
                }
            });

        }

        @Override
        public void AnswearFromServerToUserChooses(final SignalRServerResponseForHighlightVO signalRServerResponseForHighlightVO) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                 /*   String solutionID = signalRServerResponseForHighlightVO.getmSolutionid();
                    getCurrentItinerary(solutionID);
                    airportSendValuesVOs.clear();
                    selectedCount = 0;*/
                }
            });


        }

        @Override
        public void SignalRRrror(String error) {
            handleHGBMessage(getResources().getString(R.string.cnc_error), CNCAdapter.HGB_ITEM);
            airportSendValuesVOs.clear();
          //  ErrorMessage(error);
        }

        @Override
        public void SignalRCNCConversation(final String cncConversation) {
            //all cnc items

            getActivityInterface().addCNCItem(new CNCItem(cncConversation, CNCAdapter.HGB_ITEM_SIGNALR));

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

//stuff that updates ui
                    removeWaitingItem();

                    mCNCAdapter.notifyDataSetChanged();

                 /*   cncItemsAdapterList.clear();
                    cncItemsAdapterList.addAll(getActivityInterface().getCNCItems());
                    mCNCAdapter.setItems(cncItemsAdapterList);*/
                }
            });
        }
    };


    private void sendMessageToServer(final String strMessage, final iAfterServer iserverFinished) {

        String preferencesProfileId = getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId();

//        SignalRService service = ((MainActivityBottomTabs) getActivity()).getSignalRService();
//        service.cncSubmitQueryR(strMessage, null, preferencesProfileId);

       String connectionId =  mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_ID, "");
        ConnectionManager.getInstance(getActivity()).getItineraryCNCSearch(strMessage, connectionId,new ConnectionManager.ServerRequestListener() {

                    @Override
                    public void onSuccess(Object data) {

                        AirportServerResultCNCVO airportResult = (AirportServerResultCNCVO) data;
                        iserverFinished.serverFinished(airportResult);

                    }

                    @Override
                    public void onError(Object data) {

                     //   handleHGBMessage(getResources().getString(R.string.cnc_error));
                            ErrorMessage(data);


                    }
                }
        );


    }


   /* private void sendMessageToServer(final String strMessage){//,final iAfterServer iserverFinished) {


        String preferencesProfileId = getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId();

        SignalRService service = ((MainActivityBottomTabs) getActivity()).getSignalRService();
        service.cncSubmitQueryR(strMessage, null, preferencesProfileId);

  *//*      ConnectionManager.getInstance(getActivity()).getItineraryCNCSearch(strMessage, new ConnectionManager.ServerRequestListener() {

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
        );*//*


    }*/


    private void sendCNCMessageToServer(String strMessage){


     //   second message


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


         //   SignalRService service = ((MainActivityBottomTabs) getActivity()).getSignalRService();

            String preferencesID = null;

                if(airportSendValuesVOsTemp!= null && airportSendValuesVOsTemp.get(0) != null){
                    preferencesID = airportSendValuesVOsTemp.get(0).getTravelpreferenceprofileid();
                }


     /*       SignalRService service = ((MainActivityBottomTabs) getActivity()).getSignalRService();
            service.cncSubmitQueryCmR(strMessage, preferencesID, getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId(), solutionId);

*/
            ConnectionManager.getInstance(getActivity()).ItineraryCNCAddCompanionPost(airportSendValuesVOsTemp,  new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                    handleHGBMessagesViews(userTraveler);
                    getActivityInterface().setTravelOrder(userTraveler);
                    getPostBookingItinerary();

                }
                @Override
                public void onError(Object data) {
                    ErrorMessage(data);
                   // handleHGBMessage(getResources().getString(R.string.cnc_error));
                    CNCSignalRFragment.this.airportSendValuesVOs.clear();
                }
            });
        }
    }


    private void handleHGBMessagesViews(UserTravelMainVO userTraveler){
        if(userTraveler.getItems().size() <=0){
            handleHGBMessage(getString(R.string.itinerary_no_items),CNCAdapter.HGB_ITEM);
        }
        else if (getActivityInterface().getSolutionID() == null) {
            handleHGBMessage(getString(R.string.itinerary_created), CNCAdapter.HGB_ITEM_SELECTED);
        }else{
            handleHGBMessage(getString(R.string.grid_has_been_updated), CNCAdapter.HGB_ITEM_SELECTED);
        }

        airportSendValuesVOs.clear();
    }

    private void sendVOForIternarary(final ArrayList<AirportSendValuesVO> airportSendValuesVO){

       // SignalRService service = ((MainActivityBottomTabs) getActivity()).getSignalRService();
        String preferencesProfileId = getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId();


        ArrayList<CNCItem> cncItems = getActivityInterface().getCNCItems();

        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
        String solutionId = null;
        if(travelOrder != null) {
             solutionId = travelOrder.getmSolutionID();
        }

        if(solutionId != null && !cncItems.isEmpty() && cncItems.size()>1){ // new query still no solutionid

            String userProfileId = airportSendValuesVO.get(0).getTravelpreferenceprofileid();
         //   service.cncSubmitHighlightExist(airportSendValuesVO.get(0).getQuery(), userProfileId, preferencesProfileId, solutionId);//cncSubmitQueryR(strMessage, airportSendValuesVOsTemp, getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId());
        }else{

          //  service.cncSubmitHighlightNew(airportSendValuesVO, preferencesProfileId);//cncSubmitQueryR(strMessage, airportSendValuesVOsTemp, getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId());
        }


        //Kate
        String connectionID = mHGBPrefrenceManager.getStringSharedPreferences(HGBConstants.HGB_USER_SIGNALR_CONNECTION_ID,"");

        ConnectionManager.getInstance(getActivity()).postItineraryCNCSearch(airportSendValuesVO, connectionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                handleHGBMessagesViews(userTraveler);
                getActivityInterface().setTravelOrder(userTraveler);
                getPostBookingItinerary();
                setSolutionNameForItirnarary();

            }
            @Override
            public void onError(Object data) {

                //   ErrorMessage(data);

            }
        });
    }


    private void setSolutionNameForItirnarary() {
        String solutionName = getActivityInterface().getTravelOrder().getmSolutionName();
        System.out.println("Kate solutionName =" + solutionName);
        setTextForTrip(solutionName);
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

        mCNCAdapter.notifyDataSetChanged();

/*        cncItemsAdapterList.clear();
        cncItemsAdapterList.addAll(getActivityInterface().getCNCItems());
        mCNCAdapter.setItems(cncItemsAdapterList);*/
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
            mHGBPrefrenceManager.putStringSharedPreferences(HGBConstants.HGB_CNC_LIST, json);
       //     getActivityInterface().setCNCItems(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroyView();
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

}
