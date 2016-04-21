package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by arisprung on 4/20/16.
 */
public class TutorialFragment extends HGBAbstractFragment {


    public static Fragment newInstance(int position) {
        Fragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tutorial_layout, container, false);

        RelativeLayout layout = (RelativeLayout)rootView.findViewById(R.id.tutrial_background);
        layout.setAlpha(0.85f);

        return rootView;
    }

}
