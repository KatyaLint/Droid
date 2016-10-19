package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;

/**
 * Created by arisprung on 9/15/16.
 */
public class PolicyHotelFragment extends HGBAbstractFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hotel_policy_layout, container, false);
        TextView policyTextView = (TextView) v.findViewById(R.id.hotel_policy);
        NodesVO nodesVO = getLegWithGuid(getActivityInterface().getTravelOrder());
        policyTextView .setText(Html.fromHtml(nodesVO.getRoomsVOs().get(0).getmCancellationPolicy()));
        return v;
    }
}
