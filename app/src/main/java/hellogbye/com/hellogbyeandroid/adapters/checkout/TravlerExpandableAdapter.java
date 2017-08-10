package hellogbye.com.hellogbyeandroid.adapters.checkout;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.UserProfileVO;
import hellogbye.com.hellogbyeandroid.models.vo.creditcard.PaymnentGroup;
import hellogbye.com.hellogbyeandroid.models.vo.flights.PassengersVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 9/14/16.
 */
public class TravlerExpandableAdapter extends BaseExpandableListAdapter {
    private final LayoutInflater inf;
    private final ArrayList<PassengersVO> groupsList;
    private List<ArrayList<UserProfileVO>> childrenList = new ArrayList<>();
    private Context context;
    private IExpandableViewSelected groupViewClickedInterface;

    public TravlerExpandableAdapter(Context context, ArrayList<PassengersVO> groups, List<ArrayList<UserProfileVO>> children) {
        this.groupsList = groups;
        this.childrenList = children;
        this.inf = LayoutInflater.from(context);
        this.context = context;

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
        return childrenList.get(groupPosition).get(childPosition);
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = inf.inflate(R.layout.payment_child_travler_item, parent, false);
            holder = new ChildViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        final UserProfileVO child = (UserProfileVO) getChild(groupPosition, childPosition);

        String middleName = child.getMiddlename();
        String userName = child.getFirstname() + " " + child.getLastname();
        if(middleName != null){
            userName = child.getFirstname() + " " + child.getLastname() + " "+child.getMiddlename();
        }

        holder.childNametext.setText(userName);
        String dateOfBirth = child.getDob();
        holder.childDOB.setText(HGBUtilityDate.parseDateToddMMyyyyForPayment(child.getDob()));
        holder.childPhone.setText(child.getPhone());
        holder.childAddress.setText(child.getAddress() + "\n" + child.getCity() + "," + child.getState() + "\n" + child.getPostalcode());
        holder.childEmail.setText(child.getEmailaddress());

        if(!child.ispremiumuser()){
            holder.travler_loyalty_program_entry.setText("None");
        }else{
            holder.travler_loyalty_program_entry.setText("Premium");
        }



        if(child.getFirstname() == null){
            holder.childNametext.setText("");
        }
        if(child.getAddress()  == null){
            holder.childAddress.setText("");
        }

        findMissingElemnts(holder, child);
        return convertView;
    }

    private void findMissingElemnts(ChildViewHolder holder, UserProfileVO child) {

        if (child.getFirstname() == null || child.getFirstname().equals("")) {
            holder.childNametextLabel.setTextColor(ContextCompat.getColor(context, R.color.COLOR_EE3A3C));
        }
        if (child.getPostalcode() == null || child.getPostalcode().equals("")) {
            holder.childAddressLabel.setTextColor(ContextCompat.getColor(context, R.color.COLOR_EE3A3C));
        }
        if (child.getEmailaddress() == null || child.getEmailaddress().equals("")) {
            holder.childEmailLabel.setTextColor(ContextCompat.getColor(context, R.color.COLOR_EE3A3C));
        }
        if (child.getPhone() == null || child.getPhone().equals("")) {
            holder.childPhoneLabel.setTextColor(ContextCompat.getColor(context, R.color.COLOR_EE3A3C));
        }
        if (child.getDob() == null || child.getDob().equals("")) {
            holder.childDOBLabel.setTextColor(ContextCompat.getColor(context, R.color.COLOR_EE3A3C));
        }
        if (!child.ispremiumuser()){// || child.getDob().equals("")) {
            holder.travler_loyalty_program.setTextColor(ContextCompat.getColor(context, R.color.COLOR_EE3A3C));
        }


    }


    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder holder;

        if (convertView == null) {
            convertView = inf.inflate(R.layout.payment_group_travlers_item, parent, false);

            holder = new GroupViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GroupViewHolder) convertView.getTag();

        }

        final PassengersVO group = (PassengersVO) getGroup(groupPosition);
        holder.groupNametext.setText(group.getmName());

        holder.groupImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(groupViewClickedInterface.groupClicked(groupPosition)){
                    group.setSelected(true);
                    v.setBackgroundResource(R.drawable.expand_copy_3);
                }
                else{
                    group.setSelected(false);
                    v.setBackgroundResource(R.drawable.minimize);
                }

            /*    if (mRecyclerView.isGroupExpanded(groupPosition)) {
                    mRecyclerView.collapseGroup(groupPosition);
                    v.setBackgroundResource(R.drawable.expand_copy_3);
                    group.setSelected(true);
                } else {
                    mRecyclerView.expandGroup(groupPosition);
                    group.setSelected(false);
                    v.setBackgroundResource(R.drawable.minimize);
                }*/
            }
        });
        holder.groupEdittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupViewClickedInterface.groupEditClicked(groupPosition);

            }
        });

        if(group.ismChildDataMissing()){
            holder.groupMissing.setVisibility(View.VISIBLE);
        }else{
            holder.groupMissing.setVisibility(View.GONE);
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setGroupViewClickedInterface(IExpandableViewSelected groupViewClickedInterface) {
        this.groupViewClickedInterface = groupViewClickedInterface;
    }

    private class GroupViewHolder  extends RecyclerView.ViewHolder{
        private FontTextView groupNametext;
        private FontTextView groupPricetext;
        private  FontTextView groupEdittext;
        private  FontTextView groupMissing;
        //CheckBox groupCheckBox;
        private ImageView groupImageView;


        public GroupViewHolder(View itemView) {
            super(itemView);
            initializeView(itemView);
        }

        private void initializeView(View convertView){
            groupNametext = (FontTextView) convertView.findViewById(R.id.payment_group_name);
            groupEdittext = (FontTextView) convertView.findViewById(R.id.payment_group_edit);
            groupMissing = (FontTextView) convertView.findViewById(R.id.payment_group_missing);

            groupPricetext = (FontTextView) convertView.findViewById(R.id.payment_group_price);
            // holder.groupSelectCC = (FontTextView) convertView.findViewById(R.id.passenger_select_cc);
            // holder.groupCheckBox = (CheckBox) convertView.findViewById(R.id.payment_group_checkbox);
            groupImageView = (ImageView) convertView.findViewById(R.id.payment_group_image);
        }
    }

    private class ChildViewHolder  extends RecyclerView.ViewHolder{


        private FontTextView childNametextLabel;
        private FontTextView childDOBLabel;
        private FontTextView childEmailLabel;
        private FontTextView childPhoneLabel;
        private FontTextView childAddressLabel;
        private FontTextView travler_loyalty_program;

        private FontTextView childNametext;
        private FontTextView childDOB;
        private FontTextView childEmail;
        private FontTextView childPhone;
        private FontTextView childAddress;
        private FontTextView travler_loyalty_program_entry;

        public ChildViewHolder(View itemView) {
            super(itemView);
            initializeHolder(itemView);
        }


        private void initializeHolder(View convertView){
            childNametext = (FontTextView) convertView.findViewById(R.id.travler_name_entry);
            childDOB = (FontTextView) convertView.findViewById(R.id.travler_dob_entry);
            childPhone = (FontTextView) convertView.findViewById(R.id.travler_phone_entry);
            childAddress = (FontTextView) convertView.findViewById(R.id.travler_address_entry);
            childEmail = (FontTextView) convertView.findViewById(R.id.travler_email_entry);
            travler_loyalty_program = (FontTextView)convertView.findViewById(R.id.travler_loyalty_program_txt);

            childNametextLabel = (FontTextView) convertView.findViewById(R.id.travler_name);
            childDOBLabel = (FontTextView) convertView.findViewById(R.id.travler_dob);
            childPhoneLabel = (FontTextView) convertView.findViewById(R.id.travler_phone);
            childAddressLabel = (FontTextView) convertView.findViewById(R.id.travler_address);
            childEmailLabel = (FontTextView) convertView.findViewById(R.id.travler_email);
            travler_loyalty_program_entry = (FontTextView)convertView.findViewById(R.id.travler_loyalty_program_entry);
        }


    }

}
