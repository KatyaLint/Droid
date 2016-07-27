package hellogbye.com.hellogbyeandroid.fragments.membership;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.ToolBarNavEnum;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

public class MembershipFragment extends HGBAbstractFragment {


    public MembershipFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new MembershipFragment();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.membership_layout, container, false);
        int i = getArguments().getInt(HGBConstants.ARG_NAV_NUMBER);

        String strFrag = ToolBarNavEnum.getNavNameByPosition(i);

        TextView textView = (TextView)rootView.findViewById(R.id.text);
      //  textView.setText(strFrag);

      //  getActivity().setTitle(strFrag);

        return rootView;
    }


}
