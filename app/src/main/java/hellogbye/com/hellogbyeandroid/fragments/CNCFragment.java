package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.adapters.CNCAdapter;
import hellogbye.com.hellogbyeandroid.models.CNCItem;
import hellogbye.com.hellogbyeandroid.models.PopUpAlertStringCB;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportResultsVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportSendValuesVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.AirportServerResultVO;
import hellogbye.com.hellogbyeandroid.models.vo.airports.ResponsesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;

import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBPreferencesManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/3/15.
 */
public class CNCFragment extends HGBAbtsractFragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CNCAdapter mAdapter;
    private EditText mEditText;
    private ImageView mMicImageView;
    private FontTextView mSendTextView;
    private HGBPreferencesManager mHGBPrefrenceManager;

    private String[] locationArr;


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
        View rootView = inflater.inflate(R.layout.cnc_fragment_layout, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mHGBPrefrenceManager = HGBPreferencesManager.getInstance(getActivity().getApplicationContext());
        init(rootView);
        initList();


        getActivityInterface().getToolBar().updateToolBarView(ToolBarNavEnum.CNC.getNavNumber());

        return rootView;
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
                if (getString(R.string.iteinerary_created).equals(strText)
                        || getString(R.string.grid_has_been_updated).equals(strText)) {
                    getActivityInterface().goToFragment(ToolBarNavEnum.ITINARERY.getNavNumber(),null);
                }
            }
        });
    }

    private void loadCNCList() {
        String strCNCList = mHGBPrefrenceManager.getStringSharedPreferences(HGBPreferencesManager.HGB_CNC_LIST, "");


//        if ("".equals(strCNCList) && getActivityInterface().getCNCItems()== null ||
//                strCNCList==null && getActivityInterface().getCNCItems()== null) {
        if (   (strCNCList.equals("") || strCNCList.equals("null")) && getActivityInterface().getCNCItems()== null ) {

            getActivityInterface().addCNCItem(new CNCItem(getResources().getString(R.string.default_cnc_message), CNCAdapter.HGB_ITEM));
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cnc_recycler_view);
        mEditText = (EditText) view.findViewById(R.id.cnc_edit_text);
        mMicImageView = (ImageView) view.findViewById(R.id.cnc_mic);
        mSendTextView = (FontTextView) view.findViewById(R.id.cnc_send);
        mSendTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strMessage = mEditText.getText().toString();
                handleMyMessage(strMessage);
            }
        });

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
                getActivityInterface().openVoiceToTextControl();
            }
        });

    }

    public void handleMyMessage(String strMessage) {

        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.ME_ITEM));
        addWaitingItem();
        mAdapter.notifyDataSetChanged();
        resetMessageEditText();
        sendMessageToServer(strMessage);
    }

    public void handleHGBMessage(String strMessage) {

        getActivityInterface().addCNCItem(new CNCItem(strMessage.trim(), CNCAdapter.HGB_ITEM));
        removeWaitingItem();
        mAdapter.notifyDataSetChanged();
    }


    private void sendMessageToServer(final String strMessage) {
        ConnectionManager.getInstance(getActivity()).ItineraryCNCSearchGet(strMessage, new ConnectionManager.ServerRequestListener() {

                    @Override
                    public void onSuccess(Object data) {
                        AirportServerResultVO airportResult = (AirportServerResultVO)data;
                        ResponsesVO response = airportResult.getResponses().get(0);

                        String airport = getResources().getString(R.string.cnc_choose_airport);
                        airport = airport + response.getValue();

                       final AirportSendValuesVO airportSendValuesVO= new AirportSendValuesVO();
                        airportSendValuesVO.setQuery(strMessage);
                        airportSendValuesVO.setTravelpreferenceprofileid(getActivityInterface().getCurrentUser().getUserprofileid());
                        airportSendValuesVO.setType(response.getType());
                        airportSendValuesVO.setStart(3);
                        airportSendValuesVO.setEnd(3);
                        airportSendValuesVO.setValue(response.getValue());


                        final ArrayList<AirportResultsVO> results = response.getResults();

                        String[] titleArray = new String[results.size()];
                        for(int i=0;i<results.size() ;i++){
                            titleArray[i] = results.get(i).getAirportname();
                        }


                        HGBUtility.showPikerDialog(null,getActivity(), airport,
                        titleArray, 0, results.size()-1, new PopUpAlertStringCB(){

                                    @Override
                                    public void itemSelected(String inputItem) {
                                        AirportResultsVO choosenAirport = findChoosenAirport(inputItem, results);
                                        airportSendValuesVO.setId(choosenAirport.getId());

                                        String location = HGBUtility.getLocation(getActivity());
                                        HGBUtility.removeGPSListener();
                                        if(location == null){ // no location found
                                            handleHGBMessage(getString(R.string.iteinerary_try_again));
                                            return;
                                        }
                                        locationArr = location.split("&");
                                        sendVOForIternarary(airportSendValuesVO);
                                    }

                                    @Override
                                    public void itemCanceled() {

                                    }
                                });
                    }

                    @Override
                    public void onError(Object data) {
                        HGBUtility.removeGPSListener();
                        handleHGBMessage((String) data);
                    }
                }
        );

//        if (getActivityInterface().getSolutionID() == null) {
//
//       //     ItineraryCNCSearchGet
//
//
//
//            ConnectionManager.getInstance(getActivity()).ItinerarySearch(strMessage, strPrefId, new ConnectionManager.ServerRequestListener() {
//                @Override
//                public void onSuccess(Object data) {
//                    getActivityInterface().setTravelOrder((UserTravelMainVO) data);
//                    handleHGBMessage(getString(R.string.iteinerary_created));
//                }
//
//                @Override
//                public void onError(Object data) {
//                    handleHGBMessage((String) data);
//                }
//            });
//        } else {
//
//            ConnectionManager.getInstance(getActivity()).ItineraryCNCSearch(strMessage, strPrefId, getActivityInterface().getSolutionID(), new ConnectionManager.ServerRequestListener() {
//                @Override
//                public void onSuccess(Object data) {
//                    getActivityInterface().setTravelOrder((UserTravelMainVO) data);
//                    handleHGBMessage(getString(R.string.grid_has_been_updated));
//                }
//
//                @Override
//                public void onError(Object data) {
//                    handleHGBMessage((String) data );
//                }
//            });
//        }
    }

    private void sendVOForIternarary(AirportSendValuesVO airportSendValuesVO){
        airportSendValuesVO.setLatitude(locationArr[0]);
        airportSendValuesVO.setLongitude(locationArr[1]);

        ConnectionManager.getInstance(getActivity()).ItineraryCNCSearchPost(airportSendValuesVO,  new ConnectionManager.ServerRequestListener() {
                @Override
                public void onSuccess(Object data) {
                    HGBUtility.removeGPSListener();
                    if (getActivityInterface().getSolutionID() == null) {
                        handleHGBMessage(getString(R.string.iteinerary_created));
                    }else{
                        handleHGBMessage(getString(R.string.grid_has_been_updated));
                    }
                    getActivityInterface().setTravelOrder((UserTravelMainVO) data);

                }

                @Override
                public void onError(Object data) {
                    handleHGBMessage((String) data );
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
