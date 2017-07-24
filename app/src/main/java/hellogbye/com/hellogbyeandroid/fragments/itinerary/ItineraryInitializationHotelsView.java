package hellogbye.com.hellogbyeandroid.fragments.itinerary;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.flights.NodesVO;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtility;
import hellogbye.com.hellogbyeandroid.utilities.HGBUtilityDate;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 7/13/17.
 */

public class ItineraryInitializationHotelsView {

    private ImageView mStart5ImageView;
    private ImageView mStart4ImageView;
    private ImageView mStart3ImageView;
    private ImageView mStart2ImageView;
    private ImageView mStart1ImageView;


    /**
     * Create hotel layout, with node details
     * @param node
     * @return hotel layout
     */

    public View hotelLayout(NodesVO node, Activity activity){
        int cellHieght = (int) activity.getResources().getDimension(R.dimen.DP220);
        float iScreenSize = activity.getResources().getDimension(R.dimen.DP335);

        final View child = activity.getLayoutInflater().inflate(R.layout.new_grid_view_inner_hotel_item, null);



        ImageView image_view = (ImageView)child.findViewById(R.id.image_view);
        ImageLoader.getInstance().displayImage(node.getmImage(), image_view);

        FontTextView grid_hotel_name = (FontTextView)child.findViewById(R.id.grid_hotel_name);
        grid_hotel_name.setText(node.getmHotelName());
        grid_hotel_name.setTag(node.getAccountID());

        FontTextView grid_hotel_place = (FontTextView)child.findViewById(R.id.grid_hotel_place);
        grid_hotel_place.setText(node.getmAddress1());
        grid_hotel_place.setTag(node.getmGuid());




        View starView = (View)child.findViewById(R.id.grid_star_layout);

        mStart1ImageView = (ImageView) starView.findViewById(R.id.star1);
        mStart2ImageView = (ImageView) starView.findViewById(R.id.star2);
        mStart3ImageView = (ImageView) starView.findViewById(R.id.star3);
        mStart4ImageView = (ImageView) starView.findViewById(R.id.star4);
        mStart5ImageView = (ImageView) starView.findViewById(R.id.star5);

        setStarRating(starView, node.getmStarRating());


        FontTextView grid_hotel_room_explanation = (FontTextView) child.findViewById(R.id.grid_hotel_room_explanation);
        grid_hotel_room_explanation.setText(node.getRoomsVOs().get(0).getmRoomType());

        LinearLayout.LayoutParams outerLayoutParams = new LinearLayout.LayoutParams((int)iScreenSize, cellHieght);
        RelativeLayout innerWhiteLayout = (RelativeLayout)child.findViewById(R.id.innerWhiteHotelLayout);
        innerWhiteLayout.setLayoutParams(outerLayoutParams);

        ImageView new_grid_promo_available = (ImageView) child.findViewById(R.id.new_grid_promo_available);

        LinearLayout hotel_grid_regular_price = (LinearLayout) child.findViewById(R.id.hotel_grid_regular_price);

        if(node.getAlternativePromoHotel() != null && !node.getAlternativePromoHotel().isEmpty()){

            hotel_grid_regular_price.setVisibility(View.GONE);
            new_grid_promo_available.setVisibility(View.VISIBLE);

            RelativeLayout hotel_grid_price_line = (RelativeLayout) child.findViewById(R.id.hotel_grid_price_line);
            hotel_grid_price_line.setVisibility(View.VISIBLE);
            //regular price
            FontTextView promo_price_line_regular_price = (FontTextView) child.findViewById(R.id.promo_price_line_regular_price);
            long diff = HGBUtilityDate.dayDifference(node.getmCheckIn(), node.getmCheckOut());//HGBUtility.getDateDiff(node.getmCheckIn(), node.getmCheckOut());
            double iCharge = node.getmMinimumAmount()/(diff+1);
            String result = String.format("%.2f", iCharge);
            promo_price_line_regular_price.setText("$" + HGBUtility.roundNumber(iCharge));

            FontTextView price_line_regular_currency = (FontTextView) child.findViewById(R.id.price_line_regular_currency);
            price_line_regular_currency.setText(node.getmCurrency() +"\n"+"NIGHT");


            FontTextView promo_price_line_promo_price = (FontTextView) child.findViewById(R.id.promo_price_line_promo_price);
            promo_price_line_promo_price.setText(""+HGBUtility.roundNumber(node.getAlternativePromoHotel().getRoomsVOs().get(0).getmCost()));

            FontTextView promo_price_line_promo_currency = (FontTextView) child.findViewById(R.id.promo_price_line_promo_currency);
            promo_price_line_promo_currency.setText(node.getmCurrency() + "\n" + "NIGHT");


        }else{
            hotel_grid_regular_price.setVisibility(View.VISIBLE);
            new_grid_promo_available.setVisibility(View.GONE);

            FontTextView grid_hotel_night = (FontTextView) child.findViewById(R.id.grid_hotel_night);
            grid_hotel_night.setText(node.getmCurrency() + "/NIGHT");

            FontTextView grid_hotel_price = (FontTextView)child.findViewById(R.id.grid_hotel_price);
            long diff = HGBUtilityDate.dayDifference(node.getmCheckIn(), node.getmCheckOut());//HGBUtility.getDateDiff(node.getmCheckIn(), node.getmCheckOut());
            double iCharge = node.getmMinimumAmount()/(diff+1);
            String result = String.format("%.2f", iCharge);
            grid_hotel_price.setText("$" + HGBUtility.roundNumber(iCharge));
            //type
            grid_hotel_price.setTag(node.getmType());
        }



        //  RelativeLayout outer = new RelativeLayout(activity);

        // outer.setLayoutParams(outerLayoutParams);
        //  outer.setOrientation(LinearLayout.VERTICAL);

        //  outer.addView(child);

        return innerWhiteLayout;

    }

    private void setStarRating(View holder, float star) {

        if ("0.5".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.big_star_dark_blue_half, R.drawable.empty_star,
                    R.drawable.empty_star, R.drawable.empty_star, R.drawable.empty_star);

        } else if ("1.0".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.star_1, R.drawable.empty_star,
                    R.drawable.empty_star, R.drawable.empty_star, R.drawable.empty_star);

        } else if ("1.5".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.star_1, R.drawable.big_star_dark_blue_half,
                    R.drawable.empty_star, R.drawable.empty_star, R.drawable.empty_star);

        } else if ("2.0".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.star_1, R.drawable.star_1,
                    R.drawable.empty_star, R.drawable.empty_star, R.drawable.empty_star);
        } else if ("2.5".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.star_1, R.drawable.star_1,
                    R.drawable.big_star_dark_blue_half, R.drawable.empty_star, R.drawable.empty_star);
        } else if ("3.0".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.star_1, R.drawable.star_1,
                    R.drawable.star_1, R.drawable.empty_star, R.drawable.empty_star);
        } else if ("3.5".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.star_1, R.drawable.star_1,
                    R.drawable.star_1, R.drawable.big_star_dark_blue_half, R.drawable.empty_star);

        } else if ("4.0".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.star_1, R.drawable.star_1,
                    R.drawable.star_1, R.drawable.star_1, R.drawable.empty_star);

        } else if ("4.5".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.star_1, R.drawable.star_1,
                    R.drawable.star_1, R.drawable.star_1, R.drawable.big_star_dark_blue_half);

        } else if ("5.0".equals(String.valueOf(star))) {
            starHolder(holder, R.drawable.star_1, R.drawable.star_1,
                    R.drawable.star_1, R.drawable.star_1, R.drawable.star_1);

        }
    }

    private void starHolder(View view, int firstStar, int secondStar, int thirdStar, int fourStar, int fiveStar){
        mStart1ImageView.setBackgroundResource(firstStar);
        mStart2ImageView.setBackgroundResource(secondStar);
        mStart3ImageView.setBackgroundResource(thirdStar);
        mStart4ImageView.setBackgroundResource(fourStar);
        mStart5ImageView.setBackgroundResource(fiveStar);
    }

}
