package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.ImageFragment;

/**
 * Created by arisprung on 10/20/15.
 */
public class ImageGalleryActivity extends FragmentActivity {

    private ArrayList<String> listImages ;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery_layout);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.image_gallery_view_pager);
        final ImagePagerAdapter adapter = new ImagePagerAdapter(getSupportFragmentManager());
        listImages = getIntent().getStringArrayListExtra("images");
        viewPager.setAdapter(adapter);

    }
//    private class ImagePagerAdapter extends PagerAdapter {
//
//        SparseArray<DrawingFragment> registeredFragments = new SparseArray<DrawingFragment>();
//        @Override
//        public void destroyItem(final ViewGroup container, final int position, final Object object) {
//            ((ViewPager) container).removeView((ImageView) object);
//        }
//
//        @Override
//        public int getCount() {
//            return listImages.size();
//        }
//
//        @Override
//        public Object instantiateItem(final ViewGroup container, final int position) {
//            final Context context = ImageGalleryActivity.this;
//            final ImageView imageView = new ImageView(context);
////            final int padding = context.getResources().getDimensionPixelSize(
////                    R.dimen.padding_medium);
//           // imageView.setPadding(padding, padding, padding, padding);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//           // HGBUtility.loadHotelImage(getApplicationContext(), listImages.get(position), imageView);
//
//            DisplayImageOptions options = new DisplayImageOptions.Builder()
//                    .considerExifParams(true)
////                .showImageForEmptyUri(R.drawable.new_action_profile_edit_large)
////                .cacheInMemory(true)
////                .showImageOnLoading(R.drawable.icon_placeholder)
////                .showImageOnFail(R.drawable.icon_placeholder)
////                .showImageForEmptyUri(R.drawable.icon_placeholder)
//                    .cacheOnDisk(true)
//                    .postProcessor(new BitmapProcessor() {
//                        @Override
//                        public Bitmap process(Bitmap bmp) {
//                            try {
//                                return Bitmap.createScaledBitmap(bmp, 800, 800, false);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            return null;
//                        }
//                    })
//                    .build();
//            ImageLoader.getInstance().displayImage(listImages.get(position), imageView, options, new ImageLoadingListener() {
//                @Override
//                public void onLoadingStarted(String s, View view) {
//                }
//
//                @Override
//                public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//                    view.setVisibility(View.GONE);
//                }
//
//                @Override
//                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//
//                }
//
//                @Override
//                public void onLoadingCancelled(String s, View view) {
//                }
//            });
//            ((ViewPager) container).addView(imageView, 0);
//            return imageView;
//        }
//
//        @Override
//        public boolean isViewFromObject(final View view, final Object object) {
//            return view == ((ImageView) object);
//        }
//    }

    private  final class ImagePagerAdapter extends FragmentStatePagerAdapter {

        SparseArray<ImageFragment> registeredFragments = new SparseArray<ImageFragment>();

        public ImagePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageFragment fragment = (ImageFragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        @Override
        public Fragment getItem(int position) {
            final Bundle bundle = new Bundle();
            bundle.putInt(ImageFragment.EXTRA_POSITION, position);
            bundle.putString(ImageFragment.EXTRA_IMAGE_URL, listImages.get(position));
            bundle.putInt(ImageFragment.EXTRA_IMAGE_URL_SIZE, listImages.size());
            final ImageFragment fragment = new ImageFragment();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return listImages.size();
        }

        public ImageFragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

    }
}