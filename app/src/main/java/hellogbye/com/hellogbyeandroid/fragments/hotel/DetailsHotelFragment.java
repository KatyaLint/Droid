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
public class DetailsHotelFragment extends HGBAbstractFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hotel_details_layout, container, false);
        TextView descTextView = (TextView) v.findViewById(R.id.hotel_detail_description);
        NodesVO nodesVO = getLegWithGuid(getActivityInterface().getTravelOrder());
        descTextView.setText(Html.fromHtml(nodesVO.getmShortDescription()));
        return v;
    }
}
