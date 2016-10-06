package hellogbye.com.hellogbyeandroid.adapters;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.fragments.hotel.AlternativeHotelFragment;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;


/**
 * Created by arisprung on 9/21/16.
 */

public class CustomAlternativeHotelAdapter extends FragmentStatePagerAdapter {

    private ArrayList<NodesVO> mArrayList;
    private NodesVO mCurrentNode;
    private NodesVO mPastNode;
    private NodesVO mMyNode;
    private Context mContext;
    private OnSelectItemClickListener onSelectItemClick;


    public CustomAlternativeHotelAdapter(android.support.v4.app.FragmentManager fm, ArrayList<NodesVO> myDataset, NodesVO currentNode, Context context) {
        super(fm);
        mArrayList = myDataset;
        mCurrentNode = currentNode;
        mMyNode = mCurrentNode;
        mContext = context;
    }


    @Override
    public int getItemPosition(Object object) {

        AlternativeHotelFragment f =(AlternativeHotelFragment)object;
        String strCode= f.getmHotelCode();

        if(this.mMyNode.getmHotelCode().equals(strCode) ||this.mPastNode.getmHotelCode().equals(strCode)  ){
            return POSITION_NONE;
        }else{
            return super.getItemPosition(object);
        }

    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }



    @Override public float getPageWidth(int position) { return(0.45f); }

    @Override
    public Fragment getItem(int position)
    {
        AlternativeHotelFragment f = new AlternativeHotelFragment();
        Bundle args = new Bundle();
        args.putString("hotel_price","$"+String.valueOf((int) mArrayList.get(position).getmMinimumAmount()));
        args.putString("hotel_name",position+1+"."+mArrayList.get(position).getmHotelName());
        args.putFloat("hotel_star",mArrayList.get(position).getmStarRating());
        args.putBoolean("my_hotel",mCurrentNode.getmHotelCode().equals(mArrayList.get(position).getmHotelCode()));
        args.putBoolean("hotel_selected", mMyNode.getmHotelCode().equals(mArrayList.get(position).getmHotelCode()));
        args.putInt("position",position);
        args.putString("hotel_code",mMyNode.getmHotelCode());
        f.setArguments(args);
        f.SetOnItemClickListener(new AlternativeHotelFragment.OnLinearLayoutClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onSelectItemClick.onSelectItemClick(view,position);
            }
        });

        return f;

    }



    public interface OnSelectItemClickListener {
        void onSelectItemClick(View view , int position);
    }



    public void SetOnSelectClickListener(final OnSelectItemClickListener mItemClickListener) {
        onSelectItemClick = mItemClickListener;
    }



    public void setmMyNode(NodesVO node) {
            mPastNode = mMyNode ;
        this.mMyNode = node;
    }

    public void setmCurrentNode(NodesVO mCurrentNode) {
        this.mCurrentNode = mCurrentNode;
    }
}
