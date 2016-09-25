package hellogbye.com.hellogbyeandroid.fragments.hotel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.HGBAbstractFragment;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.views.ExpandableHeightGridView;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

import static hellogbye.com.hellogbyeandroid.R.id.hotel_detail_description_label;

/**
 * Created by arisprung on 9/15/16.
 */
public class DetailsHotelFragment extends HGBAbstractFragment {


    private ExpandableHeightGridView mGridView;
    private  NodesVO mNodesVO;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.hotel_details_layout, container, false);
        FontTextView descTextView = (FontTextView) v.findViewById(R.id.hotel_detail_description);
        FontTextView descLabelTextView = (FontTextView) v.findViewById(R.id.hotel_detail_description_label);
        FontTextView contactLabelTextView = (FontTextView) v.findViewById(R.id.hotel_detail_contact_label);
        FontTextView contact = (FontTextView) v.findViewById(R.id.hotel_detail_contact);
        descLabelTextView.setVisibility(View.VISIBLE);
        contactLabelTextView.setVisibility(View.VISIBLE);
        mNodesVO = getLegWithGuid(getActivityInterface().getTravelOrder());
        mGridView = (ExpandableHeightGridView) v.findViewById(R.id.grid);
        mGridView.setExpanded(true);
        descTextView.setText(Html.fromHtml(mNodesVO.getmShortDescription()));
        contact.setText(mNodesVO.getmAddress1());
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CustomDetailAmenitiesGridAdapter adapter = new CustomDetailAmenitiesGridAdapter(getActivity(),mNodesVO.getmAmenities() );

        mGridView.setAdapter(adapter);



    }
}
