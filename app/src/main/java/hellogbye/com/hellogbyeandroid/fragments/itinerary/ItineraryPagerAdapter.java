package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


class ItineraryPagerAdapter extends PagerAdapter {

    private Context context;

    int NumberOfPages = 5;

    int[] res = {
            android.R.drawable.ic_dialog_alert,
            android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_menu_directions,
            android.R.drawable.ic_menu_gallery};

    int[] backgroundcolor = {
            0xFF101010,
            0xFF202020,
            0xFF303030,
            0xFF404040,
            0xFF505050};


   public ItineraryPagerAdapter(final Context context) {
       this.context = context;
   }




    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        TextView textView = new TextView(context);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(30);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText(String.valueOf(position));

        ImageView imageView = new ImageView(context);
        imageView.setImageResource(res[position]);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

       /* LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
               300, 500);*/

        imageView.setLayoutParams(imageParams);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                200, 500);



/*        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);*/
        layout.setBackgroundColor(backgroundcolor[position]);
        layout.setLayoutParams(layoutParams);
        layout.addView(textView);
        layout.addView(imageView);

        final int page = position;
        layout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "Page " + page + " clicked",
                        Toast.LENGTH_LONG).show();
            }});

        container.addView(layout);
        return layout;
    }



    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }


    @Override
    public int getCount() {
        return NumberOfPages;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}