package hellogbye.com.hellogbyeandroid.fragments.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

public class NotificationFragment extends HGBAbstractFragment {


    public NotificationFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getFlowInterface().selectBottomBar(R.id.bb_menu_notiifcations);
        getFlowInterface().bottomBarVisible(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notification_layout, container, false);
        int i = getArguments().getInt(HGBConstants.ARG_NAV_NUMBER);

        String strFrag = ToolBarNavEnum.getNavNameByPosition(i);

        TextView textView = (TextView)rootView.findViewById(R.id.text);
       // textView.setText(strFrag);

        getActivity().setTitle(strFrag);

        return rootView;
    }


}
