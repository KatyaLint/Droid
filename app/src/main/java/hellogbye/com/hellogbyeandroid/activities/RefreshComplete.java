package hellogbye.com.hellogbyeandroid.activities;

/**
 * Created by arisprung on 9/20/16.
 */

public interface RefreshComplete {
    void onRefreshSuccess(Object data);
    void onRefreshError(Object data);
}
