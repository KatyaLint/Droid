/*
package hellogbye.com.hellogbyeandroid.adapters.cncadapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.views.FontTextView;

*/
/**
 * Created by nyawka on 6/4/17.
 *//*


class ViewHolderYoutube extends RecyclerView.ViewHolder implements View.OnClickListener, YouTubePlayer.OnInitializedListener {
    // each data item is just a string in this case
    private FontTextView itemHGB;
    private ImageView cnc_image_view;
    private ImageView cnc_white_bubble_image;

    ViewHolderYoutube(FragmentManager fragmentManager, View itemLayoutView, Context context) {
        super(itemLayoutView);

        YouTubePlayerFragment youTubePlayerView = (YouTubePlayerFragment)fragmentManager.findFragmentById(R.id.youtubeplayerfragment);

     //   YouTubePlayerFragment youTubePlayerView = (YouTubePlayerFragment)itemLayoutView.findViewById(R.id.youtubeplayerfragment);
        final String API_KEY= context.getResources().getString(R.string.google_maps_key);
        youTubePlayerView.initialize(API_KEY, this);

    */
/*    itemHGB = (FontTextView) itemLayoutView.findViewById(R.id.cnc_hgb_input);
        cnc_image_view = (ImageView) itemLayoutView.findViewById(R.id.cnc_image_view);
        cnc_white_bubble_image = (ImageView) itemLayoutView.findViewById(R.id.cnc_white_bubble_image);
        itemLayoutView.setOnClickListener(this);*//*


    }

    private void setVisabilityBubbleIcon(boolean isVisible){
        if(isVisible){
            cnc_white_bubble_image.setVisibility(View.VISIBLE);
        }else{
            cnc_white_bubble_image.setVisibility(View.GONE);
        }

    }

    private void setVisabilityIcon(boolean isVisible){
        if(isVisible){
            cnc_image_view.setVisibility(View.VISIBLE);
        }else{
            cnc_image_view.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public void onClick(View v) {

    }



    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}
*/
