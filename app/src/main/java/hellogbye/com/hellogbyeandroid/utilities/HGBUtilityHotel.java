package hellogbye.com.hellogbyeandroid.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

/**
 * Created by nyawka on 11/14/16.
 */

public class HGBUtilityHotel {


    public static Bitmap getMarkerBitmap(boolean isFirst, int index, float star, double price, Activity activity) {


        View customMarkerView = ((LayoutInflater)  activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.select_hotel_marker_layout, null);
        FontTextView numTxt = (FontTextView) customMarkerView.findViewById(R.id.select_hotel_marker_price);
        FontTextView indexText = (FontTextView) customMarkerView.findViewById(R.id.index);
        numTxt.setText("$" + price);
        indexText.setText(String.valueOf(index));
        if(isFirst){
            customMarkerView.setBackgroundResource(R.drawable.bubbles_red_background);
            indexText.setTextColor(ContextCompat.getColor(activity, R.color.COLOR_WHITE));
            numTxt.setTextColor(ContextCompat.getColor(activity, R.color.COLOR_WHITE));
        }else{
            customMarkerView.setBackgroundResource(R.drawable.bubbles_white_background);
            indexText.setTextColor(ContextCompat.getColor(activity, R.color.COLOR_565656));
            numTxt.setTextColor(ContextCompat.getColor(activity, R.color.COLOR_565656));
        }



        setStarRating(customMarkerView, star,isFirst);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }


    public static Bitmap getMyHotelMarkerBitmap(float star, double price,Activity activity) {


        View customMarkerView = ((LayoutInflater)  activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.hotel_current_marker_layout, null);

        FontTextView numTxt = (FontTextView) customMarkerView.findViewById(R.id.select_hotel_marker_price);

        numTxt.setText("$" + price);
        setStarRating(customMarkerView, star,true);
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }



    private static void setStarRating(View view, float star,boolean isSelected) {

        if ("0.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_half,R.drawable.red_star_out,
                        R.drawable.red_star_out, R.drawable.red_star_out,R.drawable.red_star_out);
            }else{
                starHolder(view,R.drawable.gray_star_half,R.drawable.gray_star_out,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }


        } else if ("1.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_full,R.drawable.red_star_out,
                        R.drawable.red_star_out, R.drawable.red_star_out,R.drawable.red_star_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_out,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }


        } else if ("1.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_full,R.drawable.red_star_half,
                        R.drawable.red_star_out, R.drawable.red_star_out,R.drawable.red_star_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_half,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }


        } else if ("2.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_full,R.drawable.red_star_full,
                        R.drawable.red_star_out, R.drawable.red_star_out,R.drawable.red_star_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_out, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }

        } else if ("2.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_full,R.drawable.red_star_full,
                        R.drawable.red_star_half, R.drawable.red_star_out,R.drawable.red_star_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_half, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }

        } else if ("3.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_full,R.drawable.red_star_full,
                        R.drawable.red_star_full, R.drawable.red_star_out,R.drawable.red_star_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_out,R.drawable.gray_star_out);
            }

        } else if ("3.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_full,R.drawable.red_star_full,
                        R.drawable.red_star_full, R.drawable.red_star_half,R.drawable.red_star_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_half,R.drawable.gray_star_out);
            }


        } else if ("4.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_full,R.drawable.red_star_full,
                        R.drawable.red_star_full, R.drawable.red_star_full,R.drawable.red_star_out);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_out);
            }


        } else if ("4.5".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_full,R.drawable.red_star_full,
                        R.drawable.red_star_full, R.drawable.red_star_full,R.drawable.red_star_half);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_half);
            }


        } else if ("5.0".equals(String.valueOf(star))) {
            if(isSelected){
                starHolder(view,R.drawable.red_star_full,R.drawable.red_star_full,
                        R.drawable.red_star_full, R.drawable.red_star_full,R.drawable.red_star_full);
            }else{
                starHolder(view,R.drawable.gray_star_full,R.drawable.gray_star_full,
                        R.drawable.gray_star_full, R.drawable.gray_star_full,R.drawable.gray_star_full);
            }
        }
    }

    private static void starHolder(View view, int firstStar, int secondStar, int thirdStar, int fourStar, int fiveStar) {
        view.findViewById(R.id.star1).setBackgroundResource(firstStar);
        view.findViewById(R.id.star2).setBackgroundResource(secondStar);
        view.findViewById(R.id.star3).setBackgroundResource(thirdStar);
        view.findViewById(R.id.star4).setBackgroundResource(fourStar);
        view.findViewById(R.id.star5).setBackgroundResource(fiveStar);

    }
}
