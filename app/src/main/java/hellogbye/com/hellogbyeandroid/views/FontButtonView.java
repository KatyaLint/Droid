package hellogbye.com.hellogbyeandroid.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import hellogbye.com.hellogbyeandroid.R;

/**
 * Created by nyawka on 6/21/16.
 */
public class FontButtonView  extends Button{
    public FontButtonView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public FontButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public FontButtonView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontTextView);
            String fontName = a.getString(R.styleable.FontTextView_fontName);
            if (fontName!=null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}


