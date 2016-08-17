package hellogbye.com.hellogbyeandroid.activities;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import hellogbye.com.hellogbyeandroid.R;

/**
 * Created by arisprung on 4/21/16.
 */
public class CNCTutorialActivity extends BaseActivity{
    private RelativeLayout mTutorialRelativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.tutorial_layout);
        mTutorialRelativeLayout = (RelativeLayout)findViewById(R.id.tutrial_background);
        mTutorialRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}
