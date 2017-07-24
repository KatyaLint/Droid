package hellogbye.com.hellogbyeandroid.adapters.cncadapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.cnc.CNCItem;
import hellogbye.com.hellogbyeandroid.views.FontTextView;
import hellogbye.com.hellogbyeandroid.views.RoundedImageView;
import pl.tajchert.sample.DotsTextView;

/**
 * Created by arisprung on 10/27/15.
 */
public class CNCAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final FragmentManager fragmentManager;
    private ArrayList<CNCItem> mArrayList = new ArrayList<>();
    private OnItemClickListener mItemClickListner;
    private Context mContext;

    public static final int ME_ITEM = 0;
    public static final int HGB_ERROR_ITEM = 1;
    public static final int HGB_ITEM = 2;
    public static final int WAITING_ITEM = 3;
    public static final int HGB_ITEM_NO_ICON = 4;
    public static final int HGB_ITEM_SELECTED = 5;
    public static final int HGB_ITEM_VIDEO_TUTORIAL = 6;
    public static final int HGB_ITEM_SIGNALR = 7;





    private int padding_integer;
    private String avatarUrl;
    // Provide a suitable constructor (depends on the kind of dataset)
    public CNCAdapter(Activity activity, Context applicationContext, ArrayList<CNCItem> myDataset) {

        mArrayList = myDataset;
        mContext = applicationContext;
        padding_integer = (int) applicationContext.getResources().getDimension(R.dimen.DP10);
        fragmentManager = activity.getFragmentManager();


    }

 /*   public void setItems(ArrayList<CNCItem> myDataset){
        mArrayList.clear();
        mArrayList.addAll(myDataset);
        notifyDataSetChanged();
    }*/

    public void setAvatarUserUrl(String avatarUrl){
        this.avatarUrl = avatarUrl;
    }

    class ViewHolderMe extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private FontTextView itemME;
        private RoundedImageView cnc_image_view_user;
        private String userAvatarUrl;

        public ViewHolderMe(View itemLayoutView) {
            super(itemLayoutView);
            itemME = (FontTextView) itemLayoutView.findViewById(R.id.cnc_input);
            cnc_image_view_user = (RoundedImageView) itemLayoutView.findViewById(R.id.cnc_image_view_user);

            itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListner != null) {
                mItemClickListner.onItemClick(v, getPosition());
            }
        }

    }


   /* String[] VideoID = {"P3mAtvs5Elc", "nCgQDjiotG0", "P3mAtvs5Elc"};

    class VideoInfoHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;

        public VideoInfoHolder(View itemView) {
            super(itemView);
            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
        }

        @Override
        public void onClick(View v) {
            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) mContext, "AIzaSyANMrd66GHfMu43HBJMAxvxSyB4W5rTk-E", VideoID[getLayoutPosition()]);
            mContext.startActivity(intent);
        }
    }
*/



    class ViewHolderHGB extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private FontTextView itemHGB;
        private ImageView cnc_image_view;
        private ImageView cnc_white_bubble_image;

        ViewHolderHGB(View itemLayoutView) {
            super(itemLayoutView);
            itemHGB = (FontTextView) itemLayoutView.findViewById(R.id.cnc_hgb_input);
            cnc_image_view = (ImageView) itemLayoutView.findViewById(R.id.cnc_image_view);
            cnc_white_bubble_image = (ImageView) itemLayoutView.findViewById(R.id.cnc_white_bubble_image);
            itemLayoutView.setOnClickListener(this);

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
            if (mItemClickListner != null) {
                mItemClickListner.onItemClick(v, getPosition());
            }
        }

    }

    class ViewHolderWaiting extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        private DotsTextView itemWaiting;


         ViewHolderWaiting(View itemLayoutView) {
            super(itemLayoutView);
            itemWaiting = (DotsTextView) itemLayoutView.findViewById(R.id.dots);
            //itemLayoutView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mItemClickListner != null) {
                mItemClickListner.onItemClick(v, getPosition());
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ME_ITEM:
                // create a new view
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cnc_item, parent, false);
                ViewHolderMe vh = new ViewHolderMe(view);
                return vh;

            case HGB_ITEM:
            case HGB_ITEM_SIGNALR:
                View hgbview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cnc_hgb_item, parent, false);
                ViewHolderHGB vh2 = new ViewHolderHGB(hgbview);
                vh2.setVisabilityBubbleIcon(false);
                return vh2;

            case HGB_ITEM_SELECTED:
                View hgbviewselected = LayoutInflater.from(parent.getContext()).inflate(R.layout.cnc_hgb_item, parent, false);
                ViewHolderHGB vhselected = new ViewHolderHGB(hgbviewselected);
                vhselected.setVisabilityBubbleIcon(true);


             //   vh2.setVisabilityIcon(true);
                return vhselected;
            case HGB_ITEM_NO_ICON:
                View hgbview2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.cnc_hgb_item, parent, false);
                ViewHolderHGB vh3 = new ViewHolderHGB(hgbview2);

            //    vh3.setVisabilityIcon(false);
                return vh3;

            case WAITING_ITEM:
                View waitview = LayoutInflater.from(parent.getContext()).inflate(R.layout.cnc_waiting_item, parent, false);
                ViewHolderWaiting vhwait = new ViewHolderWaiting(waitview);
                return vhwait;

    /*        case HGB_ITEM_VIDEO_TUTORIAL:
                View videoTutorial = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                VideoInfoHolder videoTutorialHolder = new VideoInfoHolder(videoTutorial);
                return videoTutorialHolder;*/


        }
        return null;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        String strMessage = mArrayList.get(position).getText();
        switch (holder.getItemViewType()) {

            case ME_ITEM:
                ViewHolderMe meholder = (ViewHolderMe) holder;
                meholder.itemME.setText(strMessage);
          //      HGBUtility.getAndSaveUserImage(avatarUrl,  meholder.cnc_image_view_user, null);
                meholder.itemME.setTextIsSelectable(true);
              //  meholder.cnc_image_view_user.setImageBitmap(HGBUtility.getBitmapFromCache(mContext));
                break;
            case HGB_ERROR_ITEM:
                ViewHolderHGB hgbholderError = (ViewHolderHGB) holder;
                hgbholderError.itemHGB.setBackgroundResource(R.drawable.hgb_red_cnc_backround);
                hgbholderError.itemHGB.setTextColor(mContext.getResources().getColor(R.color.COLOR_BLACK));
                hgbholderError.itemHGB.setPadding(padding_integer, padding_integer, padding_integer, padding_integer);
                hgbholderError.itemHGB.setTextIsSelectable(true);
                break;
          /*  case HGB_ITEM:
            case HGB_ITEM_NO_ICON:
                ViewHolderHGB hgbholder = (ViewHolderHGB) holder;
                if (mContext.getResources().getString(R.string.itinerary_created).equals(strMessage)
                        || mContext.getResources().getString(R.string.grid_has_been_updated).equals(strMessage)) {
                    hgbholder.itemHGB.setBackgroundResource(R.drawable.hgb_cnc_backround);
                    hgbholder.itemHGB.setTextColor(mContext.getResources().getColor(R.color.COLOR_00516f));
                    hgbholder.itemHGB.setPadding(padding_integer, padding_integer, padding_integer, padding_integer);
                    hgbholder.itemHGB.setPaintFlags(hgbholder.itemHGB.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    hgbholder.itemHGB.setSelected(false);
                    hgbholder.itemHGB.setTextIsSelectable(false);
                } else {
                    hgbholder.itemHGB.setBackgroundResource(R.drawable.hgb_cnc_backround);
                    hgbholder.itemHGB.setTextColor(mContext.getResources().getColor(R.color.COLOR_00516f));
                    hgbholder.itemHGB.setPadding(padding_integer, padding_integer, padding_integer, padding_integer);
                    hgbholder.itemHGB.setSelected(true);
                    hgbholder.itemHGB.setTextIsSelectable(true);
                }

                hgbholder.itemHGB.setText(strMessage);
                break;*/

            case HGB_ITEM:
            case HGB_ITEM_NO_ICON:

                ViewHolderHGB hgbholder = (ViewHolderHGB) holder;

                    hgbholder.itemHGB.setBackgroundResource(R.drawable.hgb_cnc_backround);
                    hgbholder.itemHGB.setTextColor(mContext.getResources().getColor(R.color.COLOR_00516f));
                    hgbholder.itemHGB.setPadding(padding_integer, padding_integer, padding_integer, padding_integer);

                   // hgbholder.itemHGB.setPaintFlags(hgbholder.itemHGB.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    //hgbholder.itemHGB.setSelected(false);
                  //  hgbholder.itemHGB.setTextIsSelectable(false);
                    hgbholder.itemHGB.setSelected(true);
                    hgbholder.itemHGB.setTextIsSelectable(true);
                    hgbholder.itemHGB.setText(strMessage);
                 break;
            case HGB_ITEM_SIGNALR:
                ViewHolderHGB hgbholderSignalR = (ViewHolderHGB) holder;
                hgbholderSignalR.itemHGB.setBackgroundResource(R.drawable.hgb_cnc_backround_signalr);
                hgbholderSignalR.itemHGB.setTextColor(ContextCompat.getColor(mContext, R.color.COLOR_00503e));//getApmContext.getResources().getColor(R.color.COLOR_00503e));
                hgbholderSignalR.itemHGB.setPadding(padding_integer, padding_integer, padding_integer, padding_integer);

                // hgbholder.itemHGB.setPaintFlags(hgbholder.itemHGB.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                //hgbholder.itemHGB.setSelected(false);
                //  hgbholder.itemHGB.setTextIsSelectable(false);
                hgbholderSignalR.itemHGB.setSelected(true);
                hgbholderSignalR.itemHGB.setTextIsSelectable(true);
                hgbholderSignalR.itemHGB.setText(strMessage);
                break;
            case HGB_ITEM_SELECTED:

                        ViewHolderHGB hgbholderselected = (ViewHolderHGB) holder;
                //hgbholderselected.itemHGB.findViewById(R.id.cnc_white_bubble_image).setVisibility(View.VISIBLE);
                        hgbholderselected.itemHGB.setBackgroundResource(R.drawable.hgb_cnc_backround);
                        hgbholderselected.itemHGB.setTextColor(mContext.getResources().getColor(R.color.COLOR_00516f));
                    //    hgbholderselected.itemHGB.setPaintFlags(hgbholderselected.itemHGB.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        hgbholderselected.itemHGB.setPadding(padding_integer, padding_integer, padding_integer, padding_integer);
                        hgbholderselected.itemHGB.setSelected(false);
                        hgbholderselected.itemHGB.setTextIsSelectable(false);
                        hgbholderselected.itemHGB.setText(strMessage);
                break;

            case WAITING_ITEM:
                ViewHolderWaiting hgbwaiting = (ViewHolderWaiting) holder;
                //TODO magic
                break;
         /*   case HGB_ITEM_VIDEO_TUTORIAL:

                final VideoInfoHolder videoInfoHolder = (VideoInfoHolder) holder;
                final YouTubeThumbnailLoader.OnThumbnailLoadedListener  onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener(){
                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }

                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailView.setVisibility(View.VISIBLE);
                        videoInfoHolder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                    }
                };

                videoInfoHolder.youTubeThumbnailView.initialize("AIzaSyANMrd66GHfMu43HBJMAxvxSyB4W5rTk-E", new YouTubeThumbnailView.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

                        youTubeThumbnailLoader.setVideo(VideoID[position]);
                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                    }

                    @Override
                    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                        //write something for failure
                    }
                });
                break;*/
        }
    }


    @Override
    public int getItemViewType(int position) {

        return mArrayList.get(position).getType();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        mItemClickListner = mItemClickListener;
    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (null != mArrayList ? mArrayList.size() : 0);
    }


}