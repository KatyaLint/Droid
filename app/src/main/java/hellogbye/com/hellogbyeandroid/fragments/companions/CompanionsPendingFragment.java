package hellogbye.com.hellogbyeandroid.fragments.companions;




import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import hellogbye.com.hellogbyeandroid.R;

/**
 * Created by nyawka on 4/20/16.
 */
public class CompanionsPendingFragment extends CompanionsTabsViewClass {
    public CompanionsPendingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = createViewForTab(R.layout.companions_pending, getContext(), true);

        RecyclerView searchRecyclerView = (RecyclerView) rootView.findViewById(R.id.companion_search_recycle_list_pending);
        LinearLayout companion_empty_view = (LinearLayout) rootView.findViewById(R.id.companion_empty_view);
        setSearchView(searchRecyclerView, companion_empty_view);

        return rootView;
    }
}
