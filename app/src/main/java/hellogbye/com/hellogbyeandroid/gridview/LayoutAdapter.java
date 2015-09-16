package hellogbye.com.hellogbyeandroid.gridview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.StaggeredGridLayoutManager;
import org.lucasr.twowayview.widget.TwoWayView;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.PassengersVO;
import hellogbye.com.hellogbyeandroid.models.vo.UserTravelVO;

/**
 * Created by nyawka on 9/6/15.
 */
public class LayoutAdapter extends RecyclerView.Adapter<LayoutAdapter.SimpleViewHolder> {
    private static final int COUNT = 100;

    private final Context mContext;
    private final TwoWayView mRecyclerView;

    private final int mLayoutId;
    private final ArrayList<PassengersVO> mItems;
    private int mCurrentItemId = 0;

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
      //  public final TextView title;
        public final TextView travelMonth;
        public final TextView travelDate;
        public final TextView flightTo;
        public final TextView flightNumber;
        public final TextView flightTime;

        public SimpleViewHolder(View view) {
            super(view);
//            title = (TextView) view.findViewById(R.id.title);
            travelMonth = (TextView)view.findViewById(R.id.travelMonth);
            travelDate = (TextView)view.findViewById(R.id.travelDate);
            flightTo = (TextView)view.findViewById(R.id.flyghtTo);
            flightNumber = (TextView)view.findViewById(R.id.flightNumber);
            flightTime = (TextView)view.findViewById(R.id.flightTime);

        }
    }

    public LayoutAdapter(Context context, TwoWayView recyclerView, int layoutId, UserTravelVO airplaneDataVO) {
        mContext = context;
        this.mItems = airplaneDataVO.getPassengerses();//airplaneDataVO;
//        mItems = new ArrayList<FlightsVO>(COUNT);
//        for (int i = 0; i < COUNT; i++) {
//            addItem(i);
//        }

        mRecyclerView = recyclerView;
        mLayoutId = layoutId;
    }



//    public void addItem(int position) {
//        final int id = mCurrentItemId++;
//        mItems.add(position, id);
//        notifyItemInserted(position);
//    }

    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.item, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {

//        if(mItems.get(position).ismIsAlternative()){
//            return;
//        }
      //  boolean isVertical = (mRecyclerView.getOrientation() == TwoWayLayoutManager.Orientation.VERTICAL);
        final View itemView = holder.itemView;

       // final FlightsVO itemId = mItems.get(position);

        if (mLayoutId == R.layout.layout_staggered_grid) {
            final int span;
            final int dimenId;

            if (position % 3 == 0) {
                dimenId = R.dimen.staggered_child_medium;
            }
            else {
                dimenId = R.dimen.staggered_child_small;
            }



            if (position % 3 != 0) {
                span = 2;

            } else {

                span = 1;
            }


            flightSettings( holder,  position);
            dateSettings( holder,  position);

            final int size = mContext.getResources().getDimensionPixelSize(dimenId);

            final StaggeredGridLayoutManager.LayoutParams lp =
                    (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();


            lp.span = span;
            lp.height = size;
            itemView.setLayoutParams(lp);
        }

    }

    private void flightSettings(SimpleViewHolder holder, int position) {
        holder.travelMonth.setVisibility(View.GONE);
        holder.travelDate.setVisibility(View.GONE);
        holder.flightTo.setVisibility(View.VISIBLE);
        holder.flightNumber.setVisibility(View.VISIBLE);
        holder.flightTime.setVisibility(View.VISIBLE);



    }

    private void clearView(SimpleViewHolder holder){
        holder.travelMonth.setText("");
        holder.travelDate.setText("");
        holder.flightTo.setText("");
        holder.flightNumber.setText("");
        holder.flightTime.setText("");
    }

    private void dateSettings(SimpleViewHolder holder, int position){

        holder.travelMonth.setVisibility(View.VISIBLE);
        holder.travelDate.setVisibility(View.VISIBLE);
        holder.flightTo.setVisibility(View.GONE);
        holder.flightNumber.setVisibility(View.GONE);
        holder.flightTime.setVisibility(View.GONE);

       // holder.travelMonth.setText(mItems.get(position).getmFlightTime());
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
