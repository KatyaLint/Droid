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
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.utilities.HGBErrorHelper;
import hellogbye.com.hellogbyeandroid.views.DividerItemDecoration;

/**
 * Created by arisprung on 8/17/15.
 */
public class PreferenceSettingsFragment extends HGBAbtsractFragment {


    private RecyclerView mRecyclerView;
    private PreferenceSettingsAdapter mAdapter;
    private List<AcountDefaultSettingsVO> accountDefaultSettings;
    private List<SettingsAttributeParamVO> accountSettingsAttributes;

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
        mAdapter.swapArray(accountDefaultSettings);
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




    private void getSettingsAttributes(final String clickedAttributeID) {
        ConnectionManager.getInstance(getActivity()).getUserSettingsAttributes(clickedAttributeID, new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {

                    accountSettingsAttributes = (List<SettingsAttributeParamVO>) data;//gson.fromJson((String) data, listType);
                    getActivityInterface().setAccountSettingsAttribute(accountSettingsAttributes);

                    getActivityInterface().setAccountSettingsAttribute(accountSettingsAttributes);
//                    getSettingsAttributes(clickedAttributeID, "", 3,acountSettingsAttributes);
                    getActivityInterface().goToFragment(ToolBarNavEnum.PREFERENCES_TAB_SETTINGS.getNavNumber(),null);

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

        ConnectionManager.getInstance(getActivity()).getUserSettingsDefault(new ConnectionManager.ServerRequestListener() {
            @Override
            public void onSuccess(Object data) {
                if (data != null) {
                    accountDefaultSettings =  (List<AcountDefaultSettingsVO>)data;
                    createListAdapter();
                }
            }

            @Override
            public void onError(Object data) {
                HGBErrorHelper errorHelper = new HGBErrorHelper();
            }
        });

        return rootView;
    }
}
