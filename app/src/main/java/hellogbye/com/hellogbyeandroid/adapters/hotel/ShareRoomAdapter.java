package hellogbye.com.hellogbyeandroid.adapters.hotel;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.ShareClass;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;

/**
 * Created by arisprung on 2/14/17.
 */

public class ShareRoomAdapter extends ArrayAdapter<ShareClass> {

    private final Activity context;
    private final ArrayList<ShareClass> itemname;

    public ShareRoomAdapter(Activity context, ArrayList<ShareClass> itemname) {
        super(context, R.layout.share_room_layout, itemname);
        this.context = context;
        this.itemname = itemname;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.share_room_layout, null, true);

        RoundedImageView imageView = (RoundedImageView) rowView.findViewById(R.id.share_image);
        FontTextView name = (FontTextView) rowView.findViewById(R.id.share_name);

        name.setText(itemname.get(position).getName());
        HGBUtility.loadRoundedImage(itemname.get(position).getUrl(),imageView,R.drawable.avatar_companions);

        return rowView;

    }
}

