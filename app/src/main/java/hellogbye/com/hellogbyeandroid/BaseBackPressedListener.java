package hellogbye.com.hellogbyeandroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;

/**
 * Created by arisprung on 10/27/15.
 */
public class BaseBackPressedListener implements OnBackPressedListener {
    private final Activity activity;

    public BaseBackPressedListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void doBack() {
        activity.getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}
