package hellogbye.com.hellogbyeandroid.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

import hellogbye.com.hellogbyeandroid.R;
import hellogbye.com.hellogbyeandroid.models.vo.alternativeflights.AlternativeFlightsVO;
import hellogbye.com.hellogbyeandroid.models.vo.flights.LegsVO;

/**
 * Created by nyawka on 10/8/15.
 */
public class GraphicsViewLayout extends View {

    private float xStart ;
    private float yStart;
    private float xEnd ;
    private int r = 6;
    private Paint drawPaint;
    private ArrayList<LegsVO> legs;
    private Context context;
    private float stopOverOffset;
    private float duration;
    private float durationOffset;
    private int yTextOffset;

    public GraphicsViewLayout(Context context) {
        super(context);
        this.context = context;
    }
    public GraphicsViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public GraphicsViewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }


    public void drawAlternativeFlights( AlternativeFlightsVO alternativeFlightsVO ){
        setupPaint();
        legs = alternativeFlightsVO.getLegs();
        initialize();
    }


    private void initialize(){
        xStart = 24;
        yStart = 60;
        xEnd = 540;
        xStartEnd = 0;
        duration = 0;
        durationOffset = 500;
        stopOverOffset = 30;
        yTextOffset = 30;
    }


    float xStartEnd;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("Kate draw");
        initialize();
        durationOffset = (xEnd / legs.size()) * 2;
        int legSize = legs.size();
        for (int i = 0; i < legSize; i++) {
            LegsVO leg = legs.get(i);
            if (leg.getmType().equals("Leg")) {   //TODO change "leg" to enum can be stopOver
                canvas.drawCircle(xStart, yStart, r, drawPaint); // draw first circle

                float xDuration = (float) leg.getmNormalizedDuration();
                duration = xDuration * durationOffset;

                xStartEnd = xStart + duration; // end position for first circle or line
                if (xStartEnd > xEnd || legSize - 1 == i) { // if line more then maxmimumEndPostion, or if last destionatio
                    canvas.drawLine(xStart + r, yStart, xEnd, yStart, drawPaint);
                    canvas.drawCircle(xEnd + r, yStart, r, drawPaint);
                    initDrawText(canvas,  leg.getmDestination(),xEnd - 20,yStart - yTextOffset);
                } else {

                    canvas.drawLine(xStart + r, yStart, xStartEnd, yStart, drawPaint);
                    canvas.drawCircle(xStartEnd + r, yStart, r, drawPaint);
                    initDrawText(canvas,  leg.getmOrigin(),xStart - 10,yStart - yTextOffset);
                    initDrawText(canvas,  leg.getmDestination(),xStartEnd - 6,yStart - yTextOffset);
                }
                xStart = xStartEnd + stopOverOffset;
            }

        }
    }


    private void initDrawText(Canvas canvas, String airoport,float xPostion,float yPosition){
        canvas.drawText(airoport, xPostion, yPosition,drawPaint);
    }


    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(context.getResources().getColor(R.color.marine));
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(3);
        drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        float textSize = getContext().getResources().getDimension(R.dimen.SP14);
        drawPaint.setTextSize(textSize);
        Typeface textFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + "dinnextltpro_medium.otf");
        drawPaint.setTypeface(textFont);
    }

}
