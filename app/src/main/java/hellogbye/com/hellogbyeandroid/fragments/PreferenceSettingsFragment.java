package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hellogbye.com.hellogbyeandroid.R;

import hellogbye.com.hellogbyeandroid.adapters.PreferenceSettingsAdapter;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.AcountDefaultSettingsVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributeParamVO;
import hellogbye.com.hellogbyeandroid.models.vo.acountsettings.SettingsAttributesVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;

/**
 * Created by arisprung on 8/17/15.
 */
public class PreferenceSettingsFragment extends HGBAbtsractFragment {



  //  private ProgressBar pbHeaderProgress;
    private RecyclerView mRecyclerView;
    private PreferenceSettingsAdapter mAdapter;

    public PreferenceSettingsFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new PreferenceSettingsFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    List<AcountDefaultSettingsVO> acountDefaultSettings;
    List<SettingsAttributeParamVO> acountSettingsAttributes;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    private void createListAdapter(){


        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        mAdapter = new PreferenceSettingsAdapter();
        mAdapter.swapArray(acountDefaultSettings);
        mAdapter.setViewClickListener(new IViewCallBackClick() {
            @Override
            public void viewCallBackClick(String viewId) {
                getSettingsAttributes(viewId);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    public interface IViewCallBackClick{
        void viewCallBackClick(String viewId);
    }


//    private void getSettingsAttributes(String clickedAttributeID, String type, int attributeID, List<SettingsAttributeParamVO> data) {
//        ConnectionManager.getInstance(getActivity()).getUserSettingAttributesForAttributeID(clickedAttributeID,  type, new ConnectionManager.ServerRequestListener() {
//            @Override
//            public void onSuccess(Object data) {
//                if (data != null) {
//                    List<SettingsAttributesVO> acountSettingsAttributes = (List<SettingsAttributesVO>) data; //gson.fromJson((String) data, listType);
//                    getActivityInterface().setAccountSettingsAttributeSpecific(acountSettingsAttributes);
//                    getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_TAB_SETTINGS.getNavNumber());
//                }
//            }
//
//            @Override
//            public void onError(Object data) {
//                HGBErrorHelper errorHelper = new HGBErrorHelper();
//            }
//        });
//    }


    private void getSettingsAttributes(final String clickedAttributeID) {
        ConnectionManager.getInstance(getActivity()).getUserSettingsAttributes(clickedAttributeID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    acountSettingsAttributes = (List<SettingsAttributeParamVO>) data;//gson.fromJson((String) data, listType);
                    getActivityInterface().setAccountSettingsAttribute(acountSettingsAttributes);
//                    getSettingsAttributes(clickedAttributeID, "", 3,acountSettingsAttributes);
                    getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_TAB_SETTINGS.getNavNumber());
                }
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_list_layout, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.settingsRecyclerView);

//        pbHeaderProgress = (ProgressBar) rootView.findViewById(R.id.pbHeaderProgress);
     //   pbHeaderProgress.setVisibility(View.VISIBLE);

        ConnectionManager.getInstance(getActivity()).getUserSettingsDefault(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {

                    acountDefaultSettings =  (List<AcountDefaultSettingsVO>)data;
                    createListAdapter();
                   // pbHeaderProgress.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Object data) {
            //    pbHeaderProgress.setVisibility(View.GONE);
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });





        return rootView;
    }
}
