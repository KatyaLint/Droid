package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.HGBProgressRelativeLayout;

/**
 * Created by arisprung on 8/17/15.
 */
public class HomeFragment extends Fragment {

    private ImageView mSpinnerImageView;


    public HomeFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        int i = getArguments().getInt(HGBConstants.ARG_NAV_NUMBER);
        String strFrag = getResources().getStringArray(R.array.nav_draw_array)[i];

        TextView textView = (TextView)rootView.findViewById(R.id.text);
//        HGBProgressRelativeLayout relativeLayout = (HGBProgressRelativeLayout)rootView.findViewById(R.id.loader_spinner);
//        relativeLayout.setVisibility(View.VISIBLE);

        textView.setText(strFrag);


        getActivity().setTitle(strFrag);

        return rootView;
    }
}
