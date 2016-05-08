package hellogbye.com.hellogbyeandroid.fragments;


import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.activities.CNCTutorialActivity;
import hellogbye.com.hellogbyeandroid.activities.MainActivity;
import hellogbye.com.hellogbyeandroid.adapters.CNCAdapter;
import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.accounts.AccountsVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportResultsVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportServerResultVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.ResponsesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;

import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
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

    private String[] locationArr;
    private Button cnc_fragment_trip_settings;
    private static int SPLASH_TIME_OUT = 5000;


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

        View rootView = inflater.inflate(R.layout.cnc_fragment_layout, container, false);

        mHGBPrefrenceManager = HGBPreferencesManager.getInstance(getActivity().getApplicationContext());
        init(rootView);
        initList();

        getFlowInterface().getToolBar().updateToolBarView(ToolBarNavEnum.CNC.getNavNumber());

        getAccountsProfiles();

        //This is to ckeck and display tutorial this version was cancelled waiting for new one
        startTutorial();


        return rootView;
    }

    private void startTutorial() {

        if(mHGBPrefrenceManager.getBooleanSharedPreferences(HGBPreferencesManager.HGB_CNC_FIRST_TIME,true)){
            mHGBPrefrenceManager.putBooleanSharedPreferences(HGBPreferencesManager.HGB_CNC_FIRST_TIME,false);
            mProfileTutorialText.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProfileTutorialText.setVisibility(View.GONE);
                }
            }, SPLASH_TIME_OUT);
        }


    }


    private void getAccountsProfiles(){
        ConnectionManager.getInstance(getActivity()).getUserProfileAccounts(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                ArrayList<AccountsVO> accounts = ( ArrayList<AccountsVO> )data;
                getActivityInterface().setAccounts(accounts);
                ((MainActivity) getActivity()).editProfileTipeMainToolBar();
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
                errorHelper.setMessageForError( (String) data);
                errorHelper.show(getActivity().getFragmentManager(), (String) data);
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
        removeWaitingItem();
    }

    private void loadCNCList() {
        String strCNCList = mHGBPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST, "");

//        if ("".equals(strCNCList) && getActivityInterface().getCNCItems()== null ||
//                strCNCList==null && getActivityInterface().getCNCItems()== null) {
        if ( (strCNCList.equals("") || strCNCList.equals("null")) && getActivityInterface().getCNCItems()== null ) {
            Resources res = getResources();
            String userName = "";
           if( getActivityInterface().getCurrentUser() != null){

               userName = getActivityInterface().getCurrentUser().getFirstname();
           }

            String text = String.format(res.getString(R.string.default_cnc_message),userName );


            getActivityInterface().addCNCItem(new CNCItem(text, CNCAdapter.HGB_ITEM));
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

        cnc_fragment_trip_settings = (Button)view.findViewById(R.id.cnc_fragment_trip_settings);
        cnc_fragment_trip_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFlowInterface().goToFragment(ToolBarNavEnum.PREFERENCE.getNavNumber(), null);
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.cnc_recycler_view);
        mEditText = (FontEditTextView) view.findViewById(R.id.cnc_edit_text);
        mMicImageView = (ImageView) view.findViewById(R.id.cnc_mic);
        mSendTextView = (FontTextView) view.findViewById(R.id.cnc_send);
        mSendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMessage = mEditText.getText().toString();
                handleMyMessage(strMessage);
            }
        });

        mProfileTutorialText = (FontTextView)view.findViewById(R.id.profile_tutorial);

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

        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.ME_ITEM));
        addWaitingItem();
        mAdapter.notifyDataSetChanged();
        resetMessageEditText();

        sendMessageToServer(strMessage, new iAfterServer(){

            @Override
            public void serverFinished(AirportServerResultVO airportResult ) {
                ArrayList<ResponsesVO> responses = airportResult.getResponses();
                maxAirportSize = 0;//responses.size();
                for(ResponsesVO response:responses){
                    if(response.getType().equals("City")){
                        maxAirportSize = maxAirportSize + 1;
                    }
                }


                for(int i=0;i<responses.size();i++){
                    ResponsesVO response = responses.get(i);
                    if(responses.get(i).getType().equals("City")){
                        String airport = getResources().getString(R.string.cnc_choose_airport);
                        airport = airport + response.getValue();
                        final AirportSendValuesVO airportSendValuesVO = new AirportSendValuesVO();
                        airportSendValuesVO.setQuery(strMessage);
                        airportSendValuesVO.setTravelpreferenceprofileid(getActivityInterface().getCurrentUser().getUserprofileid());
                        airportSendValuesVO.setType(response.getType());
                        airportSendValuesVO.setStart(response.getPositionVO().getStart());
                        airportSendValuesVO.setEnd(response.getPositionVO().getEnd());
                        airportSendValuesVO.setValue(response.getValue());


                        final ArrayList<AirportResultsVO> results = response.getResults();

                        //Type not city result can be 0

                        String[] titleArray = new String[results.size()];
                        for (int j = 0; j < results.size(); j++) {
                            titleArray[j] = results.get(j).getAirportname();
                        }


                        HGBUtility.showPikerDialog(null, getActivity(), airport,
                                titleArray, 0, results.size() - 1, new PopUpAlertStringCB() {

                                    @Override
                                    public void itemSelected(String inputItem) {

                                        AirportResultsVO choosenAirport = findChoosenAirport(inputItem, results);
                                        airportSendValuesVO.setId(choosenAirport.getId());


                                        String location = HGBUtility.getLocation(getActivity());

                                        HGBUtility.removeGPSListener();
                                        if (location == null) { // no location found
                                            handleHGBMessage(getString(R.string.itinerary_try_again));
                                            return;
                                        }
                                        locationArr = location.split("&");
                                        airportSendValuesVO.setLatitude(locationArr[0]);
                                        airportSendValuesVO.setLongitude(locationArr[1]);
                                        airportSendValuesVOs.add(airportSendValuesVO);


                                        if (maxAirportSize == airportSendValuesVOs.size()) {
                                            sendVOForIternarary(airportSendValuesVOs);
                                        }

                                    }

                                    @Override
                                    public void itemCanceled() {

                                    }
                                }, false);

                    }
                }

                }

        });


    }


    interface iAfterServer {
        void serverFinished(AirportServerResultVO airportResult);
    }

    public void handleHGBMessage(String strMessage) {

        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.HGB_ITEM));

        removeWaitingItem();
        mAdapter.notifyDataSetChanged();
    }


    public void handleErrorHGBMessage(String strMessage) {

        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.HGB_ERROR_ITEM));

        removeWaitingItem();
      //  mAdapter.notifyDataSetChanged();
    }

    ArrayList<AirportSendValuesVO> airportSendValuesVOs = new ArrayList<AirportSendValuesVO>();
    int maxAirportSize = 0;

    private void sendMessageToServer(final String strMessage,final iAfterServer iserverFinished) {
        ConnectionManager.getInstance(getActivity()).ItineraryCNCSearchGet(strMessage, new ConnectionManager.ServerRequestListener() {

                    @Override
                    public void onSuccess(Object data) {
                        AirportServerResultVO airportResult = (AirportServerResultVO) data;
                        iserverFinished.serverFinished(airportResult);

                    }

                    @Override
                    public void onError(Object data) {
                        HGBErrorHelper errorHelper = new HGBErrorHelper();
                        errorHelper.setMessageForError((String) data);
                        errorHelper.show(getActivity().getFragmentManager(), (String) data);
                    }
                }
        );
    }


    private void sendVOForIternarary(ArrayList<AirportSendValuesVO> airportSendValuesVO){

        ConnectionManager.getInstance(getActivity()).ItineraryCNCSearchPost(airportSendValuesVO,  new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    HGBUtility.removeGPSListener();
                    UserTravelMainVO userTraveler = (UserTravelMainVO) data;
                    if(userTraveler.getItems().size() <=0){
                        handleHGBMessage(getString(R.string.itinerary_no_items));
                    }
                    else if (getActivityInterface().getSolutionID() == null) {
                        handleHGBMessage(getString(R.string.itinerary_created));
                    }else{
                        handleHGBMessage(getString(R.string.grid_has_been_updated));
                    }

                    airportSendValuesVOs.clear();

                    getActivityInterface().setTravelOrder(userTraveler);

                }
                @Override
                public void onError(Object data) {

                    HGBErrorHelper errorHelper = new HGBErrorHelper();
                    errorHelper.setMessageForError((String) data);
                    errorHelper.show(getActivity().getFragmentManager(), (String) data);
                    airportSendValuesVOs.clear();
                }
            });
    }



    private AirportResultsVO findChoosenAirport(String choosenAirport, ArrayList<AirportResultsVO> results){
        for (AirportResultsVO airportResults:results){
            if(airportResults.getAirportname().equals(choosenAirport)){
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
    }
}
