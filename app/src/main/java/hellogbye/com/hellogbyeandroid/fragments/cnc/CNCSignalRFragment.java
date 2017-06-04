package hellogbye.com.hellogbyeandroid.fragments.cnc;

/**
 * Created by nyawka on 2/1/17.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.MainActivityBottomTabs;
import hellogbye.com.hellogbyeandroid.adapters.cncadapters.CNCAdapter;
import hellogbye.com.hellogbyeandroid.adapters.cncadapters.CNCMenuAdapter;
import hellogbye.com.hellogbyeandroid.fragments.ChangeTripName;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.fragments.TitleNameChange;
import hellogbye.com.hellogbyeandroid.fragments.preferences.preferencespopup.UserProfilePreferences;
import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
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

    private LinearLayout cnc_add_dialog_favorites;

    private LinearLayout cnc_fragment_profile_line;
    private FontTextView cnc_fragment_profile_name;
    private UserProfilePreferences userProfilePreferences;
    private CNCTutorialsVO cncTutorials;
    private String tutorialMessage = "/examples";
    // private SignalRService service;

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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


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
                freeUserPopUp();


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


    private void freeUserPopUp(){

        LayoutInflater li = LayoutInflater.from(getActivity());
        final  View popup_cnc_add_dialog = li.inflate(R.layout.popup_cnc_add_dialog, null);
      /*  FontTextView popup_flight_baggage_text = (FontTextView) popupView.findViewById(R.id.popup_flight_baggage_text);
        String currency = getActivityInterface().getCurrentUser().getCurrency();
        String text = String.format(getActivity().getResources().getString(R.string.popup_flight_baggage_info_text),currency );
        popup_flight_baggage_text.setText(text);*/



        LinearLayout cnc_add_dialog_add_trip = (LinearLayout)popup_cnc_add_dialog.findViewById(R.id.cnc_add_dialog_add_trip);
        cnc_add_dialog_add_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCNCItems();
                HGBUtility.dialog.cancel();

            }
        });




        LinearLayout cnc_add_dialog_add_companion = (LinearLayout)popup_cnc_add_dialog.findViewById(R.id.cnc_add_dialog_add_companion);
        cnc_add_dialog_add_companion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFlowInterface().goToFragment(ToolBarNavEnum.COMPANIONS.getNavNumber(), null);
                HGBUtility.dialog.cancel();


            }
        });

        cnc_add_dialog_favorites = (LinearLayout)popup_cnc_add_dialog.findViewById(R.id.cnc_add_dialog_favorites);
        cnc_add_dialog_favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorityItinerary();
                HGBUtility.dialog.cancel();

            }
        });


        FontTextView cnc_add_dialog_favorites_text  =  (FontTextView)cnc_add_dialog_favorites.findViewById(R.id.cnc_add_dialog_favorites_text);
        ImageView add_to_favorites_img = (ImageView)cnc_add_dialog_favorites.findViewById(R.id.add_to_favorites_img);
        if (getActivityInterface().getTravelOrder() != null && !getActivityInterface().getTravelOrder().ismIsFavorite()) {
            cnc_add_dialog_favorites_text.setText(R.string.cnc_add_dialog_add_favorites);
            add_to_favorites_img.setBackgroundResource(R.drawable.add_to_favorites);
        } else {
            cnc_add_dialog_favorites_text.setText(R.string.cnc_add_dialog_remove_favorites);
            add_to_favorites_img.setBackgroundResource(R.drawable.remove_from_favorites);
        }


        if( getActivityInterface().getCNCItems().size() > 2){
            cnc_add_dialog_add_companion.setVisibility(View.VISIBLE);
            cnc_add_dialog_favorites.setVisibility(View.VISIBLE);
        }else{
            cnc_add_dialog_add_companion.setVisibility(View.GONE);
            cnc_add_dialog_favorites.setVisibility(View.GONE);
        }


        HGBUtility.showAlertPopUp(getActivity(), null, popup_cnc_add_dialog,
                null, null, new PopUpAlertStringCB() {
                    @Override
                    public void itemSelected(String inputItem) {

                    }

                    @Override
                    public void itemCanceled() {

                    }
                });


    }

    private void getFavorityCurrentItinerary(String solutionId) {

        ConnectionManager.getInstance(getActivity()).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;
                getActivityInterface().setTravelOrder(userTravelMainVO);
            }

            @Override
            public void onError(Object data) {
                ErrorMessage(data);
            }
        });
    }

    private void setFavorityItinerary() {
        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
        final String solutionID = travelOrder.getmSolutionID();
        boolean isFavorite = travelOrder.ismIsFavorite();

        ConnectionManager.getInstance(getActivity()).putFavorityItenarary(!isFavorite, solutionID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                FontTextView cnc_add_dialog_favorites_text  =  (FontTextView)cnc_add_dialog_favorites.findViewById(R.id.cnc_add_dialog_favorites_text);
                if (getActivityInterface().getTravelOrder().ismIsFavorite()) {
                    cnc_add_dialog_favorites_text.setText(R.string.cnc_add_dialog_add_favorites);
                } else {
                    cnc_add_dialog_favorites_text.setText(R.string.cnc_add_dialog_remove_favorites);
                }

                getFavorityCurrentItinerary(solutionID);
            }

            @Override
            public void onError(Object data) {

                ErrorMessage(data);
            }
        });
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

    private void getCurrentItinerary(String solutionId){

        ConnectionManager.getInstance(getActivity()).getItinerary(solutionId, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {

                getActivityInterface().setCNCItems(null);

                initList();
               // getActivityInterface().setCNCItems(null);
                args.putString(HGBConstants.SOLUTION_ITINERARY_ID,null);
                args.putBoolean(HGBConstants.CNC_CLEAR_CHAT,false);
                UserTravelMainVO userTravelMainVO = (UserTravelMainVO) data;
                getActivityInterface().setTravelOrder(userTravelMainVO);

                addUserConversation();
                getFlowInterface().goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(), null);
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

    private void handleMyMessage(final String strMessageReceived) {
        //stopTextTutorial();
        //setTutorialTextVisibility(false);

        String userMessage = strMessageReceived.trim();
        if(userMessage.equals(tutorialMessage)){
            examplesLogics();
            return;
        }
        getActivityInterface().addCNCItem(new CNCItem(userMessage, CNCAdapter.ME_ITEM));
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
            public void onError(String error) {
                enterCNCMessage(strMessageReceived);
            }
        });



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


    private void enterCNCMessage(final String strMessage) {
        setTutorialTextVisibility(false);
        clearCNCscreen = args.getBoolean(HGBConstants.CNC_CLEAR_CHAT, true);

        if(!clearCNCscreen) { // not a new message
            sendCNCMessageToServer(strMessage);
        } else { //  a new message

            selectedCount = 0;
            sendMessageToServer(strMessage);
        }
    }


    public void serverFinished(AirportServerResultCNCVO airportServerResultVO) {

        ArrayList<ResponsesVO> responses = airportServerResultVO.getHighlightdataVO().getResponses();


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
            airportSendValuesVO.setQuery(airportServerResultVO.getmOriginalQuery());

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
            positive_button.setTextColor(getContext().getResources().getColor(R.color.COLOR_EE3A3C));
        }

        user_profile_popup_list_view.setAdapter(sortPopupAdapter);


        alertDialog.show();





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



    public void handleHGBMessage(String strMessage,int type ) {
        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(),  type ));//CNCAdapter.HGB_ITEM));
        removeWaitingItem();
    }
    public void handleHGBMessageSelected(String strMessage,int type ) {
        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.HGB_ITEM));
        removeWaitingItem();
    }

    public void handleHGBMessageMe(String strMessage) {
        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.ME_ITEM));
        addWaitingItem();

    }


    public interface IHiglightReceivedFromServer{
        void HiglightReceived(AirportServerResultCNCVO airportServerResultVO);
        void AnswearFromServerToUserChooses(SignalRServerResponseForHighlightVO signalRServerResponseForHighlightVO);
        void SignalRRrror(String error);
    }

    private IHiglightReceivedFromServer higlightReceivedFromServer = new IHiglightReceivedFromServer(){

        @Override
        public void HiglightReceived(final AirportServerResultCNCVO airportServerResultVO) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    serverFinished(airportServerResultVO);
                }
            });

        }

        @Override
        public void AnswearFromServerToUserChooses(final SignalRServerResponseForHighlightVO signalRServerResponseForHighlightVO) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String solutionID = signalRServerResponseForHighlightVO.getmSolutionid();
                    getCurrentItinerary(solutionID);
                    airportSendValuesVOs.clear();
                    selectedCount = 0;
                }
            });


        }

        @Override
        public void SignalRRrror(String error) {
            handleHGBMessage(getResources().getString(R.string.cnc_error), CNCAdapter.HGB_ITEM);
            airportSendValuesVOs.clear();
          //  ErrorMessage(error);
        }
    };

    private void sendMessageToServer(final String strMessage){//,final iAfterServer iserverFinished) {


        String preferencesProfileId = getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId();

        SignalRService service = ((MainActivityBottomTabs) getActivity()).getSignalRService();
        service.cncSubmitQueryR(strMessage, null, preferencesProfileId);

  /*      ConnectionManager.getInstance(getActivity()).getItineraryCNCSearch(strMessage, new ConnectionManager.ServerRequestListener() {

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
        );*/


    }


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


            SignalRService service = ((MainActivityBottomTabs) getActivity()).getSignalRService();
            service.cncSubmitQueryCmR(strMessage, preferencesID, getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId(), solutionId);


       /*     ConnectionManager.getInstance(getActivity()).ItineraryCNCAddCompanionPost(airportSendValuesVOsTemp,  new ConnectionManager.ServerRequestListener() {
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
            });*/
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

        SignalRService service = ((MainActivityBottomTabs) getActivity()).getSignalRService();
        String preferencesProfileId = getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId();


        ArrayList<CNCItem> cncItems = getActivityInterface().getCNCItems();

        UserTravelMainVO travelOrder = getActivityInterface().getTravelOrder();
        String solutionId = null;
        if(travelOrder != null) {
             solutionId = travelOrder.getmSolutionID();
        }

        if(solutionId != null && !cncItems.isEmpty() && cncItems.size()>1){ // new query still no solutionid

            String userProfileId = airportSendValuesVO.get(0).getTravelpreferenceprofileid();
            service.cncSubmitHighlightExist(airportSendValuesVO.get(0).getQuery(), userProfileId, preferencesProfileId, solutionId);//cncSubmitQueryR(strMessage, airportSendValuesVOsTemp, getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId());
        }else{

            service.cncSubmitHighlightNew(airportSendValuesVO, preferencesProfileId);//cncSubmitQueryR(strMessage, airportSendValuesVOsTemp, getActivityInterface().getPersonalUserInformation().getmTravelPreferencesProfileId());
        }

       /* ConnectionManager.getInstance(getActivity()).postItineraryCNCSearch(airportSendValuesVO,  new ConnectionManager.ServerRequestListener() {
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

            }
        });*/
    }


    private void setSolutionNameForItirnarary() {
        String solutionName = getActivityInterface().getTravelOrder().getmSolutionName();
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
       //     getActivityInterface().setCNCItems(null);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroyView();
    }
}
