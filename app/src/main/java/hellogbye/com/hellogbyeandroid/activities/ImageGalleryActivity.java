package hellogbye.com.hellogbyeandroid.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.ViewGroup;
import java.util.ArrayList;
import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.fragments.ImageFragment;
import hellogbye.com.hellogbyeandroid.utilities.HGBConstants;

/**
 * Created by arisprung on 10/20/15.
 */
public class ImageGalleryActivity extends BaseActivity {

    private ArrayList<String> listImages ;
    private int mIndex;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery_layout);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.image_gallery_view_pager);
        final ImagePagerAdapter adapter = new ImagePagerAdapter(getSupportFragmentManager());
        listImages = getIntent().getStringArrayListExtra("images");
        mIndex = getIntent().getIntExtra("image_index",0);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(mIndex);

    }


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
            bundle.putInt(HGBConstants.EXTRA_POSITION, position);
            bundle.putString(HGBConstants.EXTRA_IMAGE_URL, listImages.get(position));
            bundle.putInt(HGBConstants.EXTRA_IMAGE_URL_SIZE, listImages.size());
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