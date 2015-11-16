package hellogbye.com.hellogbyeandroid.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.PaymentChild;
import hellogbye.com.hellogbyeandroid.models.PaymnentGroup;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by arisprung on 11/15/15.
 */
public class PaymentDetailsFragemnt extends HGBAbtsractFragment {

    View rootView;
    ExpandableListView lv;
    private ArrayList<PaymnentGroup> groups;
    private List<ArrayList<PaymentChild>> children;


    public static Fragment newInstance(int position) {
        Fragment fragment = new PaymentDetailsFragemnt();
        Bundle args = new Bundle();
        args.putInt(HGBConstants.ARG_NAV_NUMBER, position);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        groups = new ArrayList<>();
        groups.add(new PaymnentGroup("Flight", "$124", true));
        groups.add(new PaymnentGroup("Hotel", "$133", true));

        children = new ArrayList<>();

        // children.add(new PaymentChild("YYZ-NW","$111",true));

        ArrayList<PaymentChild> childArray = new ArrayList<>();
        childArray.add(new PaymentChild("YYZ-NW", "$111", true));
        childArray.add(new PaymentChild("TLV-LA", "$2211", true));
        children.add(childArray);
        children.add(childArray);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.payment_details_layout, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lv = (ExpandableListView) view.findViewById(R.id.flight_ex_list);
        lv.setAdapter(new ExpandableListAdapter(groups, children));
        lv.setGroupIndicator(null);

    }

    public class ExpandableListAdapter extends BaseExpandableListAdapter {

        private final LayoutInflater inf;
        private ArrayList<PaymnentGroup> groupsList;
        private List<ArrayList<PaymentChild>> childrenList;

        public ExpandableListAdapter(ArrayList<PaymnentGroup> groups, List<ArrayList<PaymentChild>> children) {
            this.groupsList = groups;
            this.childrenList = children;
            inf = LayoutInflater.from(getActivity());
        }

        @Override
        public int getGroupCount() {
            return groupsList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childrenList.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupsList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return children.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            ChildViewHolder holder;
            if (convertView == null) {
                convertView = inf.inflate(R.layout.payment_child_item, parent, false);
                holder = new ChildViewHolder();

                holder.childNametext = (FontTextView) convertView.findViewById(R.id.payment_child_name);
                holder.childPricetext = (FontTextView) convertView.findViewById(R.id.payment_child_price);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("", "");
                    }
                });
                convertView.setTag(holder);
            } else {
                holder = (ChildViewHolder) convertView.getTag();
            }
            PaymentChild child = (PaymentChild) getChild(groupPosition, childPosition);
            holder.childNametext.setText(child.getNameText());
            holder.childPricetext.setText(child.getTotalText());
            return convertView;
        }

        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            GroupViewHolder holder;

            if (convertView == null) {
                convertView = inf.inflate(R.layout.payment_group_item, parent, false);

                holder = new GroupViewHolder();
                holder.groupNametext = (FontTextView) convertView.findViewById(R.id.payment_group_name);
                holder.groupPricetext = (FontTextView) convertView.findViewById(R.id.payment_group_price);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO need to fix this up not good
                        if (lv.isGroupExpanded(groupPosition)) {
                            lv.collapseGroup(groupPosition);
                        } else {
                            lv.expandGroup(groupPosition);
                        }
                    }
                });
                convertView.setTag(holder);
            } else {
                holder = (GroupViewHolder) convertView.getTag();
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO need to fix this up not good
                        if (lv.isGroupExpanded(groupPosition)) {
                            lv.collapseGroup(groupPosition);
                        } else {
                            lv.expandGroup(groupPosition);
                        }
                    }
                });
            }

            PaymnentGroup group = (PaymnentGroup) getGroup(groupPosition);
            holder.groupNametext.setText(group.getNameText());
            holder.groupPricetext.setText(group.getTotalText());

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private class GroupViewHolder {
            FontTextView groupNametext;
            FontTextView groupPricetext;

        }

        private class ChildViewHolder {

            FontTextView childNametext;
            FontTextView childPricetext;
        }
    }
}
