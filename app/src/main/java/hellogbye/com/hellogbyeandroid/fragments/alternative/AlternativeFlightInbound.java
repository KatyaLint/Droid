package hellogbye.com.hellogbyeandroid.fragments.alternative;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.UserTravelMainVO;
import hellogbye.com.hellogbyeandroid.network.ConnectionManager;

/**
 * Created by nyawka on 8/3/16.
 */
public class AlternativeFlightInbound extends AlternativeFlightFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flightType(true);


    }

}
