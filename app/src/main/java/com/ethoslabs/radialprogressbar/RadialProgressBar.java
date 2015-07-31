package com.ethoslabs.radialprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by John on 7/28/2015.
 */
public class RadialProgressBar extends View {

    protected int circumference = 75;
    protected int defaultFill = 0;
    protected boolean spinning = false;

    public RadialProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RadialProgressBar,
                0, 0);

        try{
            circumference = a.getInt(R.styleable.RadialProgressBar_circumference, 75);
            defaultFill = a.getInt(R.styleable.RadialProgressBar_defaultFill, 0);
            spinning = a.getBoolean(R.styleable.RadialProgressBar_spinning, false);
        }finally{
            a.recycle();
        }
    }

    public RadialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RadialProgressBar,
                0, 0);

        try{
            circumference = a.getInt(R.styleable.RadialProgressBar_circumference, 75);
            defaultFill = a.getInt(R.styleable.RadialProgressBar_defaultFill, 0);
            spinning = a.getBoolean(R.styleable.RadialProgressBar_spinning, false);
        }finally{
            a.recycle();
        }
    }

    public RadialProgressBar(Context context) {
        super(context);
    }

    public int getCircumference() {
        return circumference;
    }

    public void setCircumference(int circumference) {
        this.circumference = circumference;
        invalidate();
        requestLayout();
    }

    public int getDefaultFill() {
        return defaultFill;
    }

    public void setDefaultFill(int defaultFill) {
        this.defaultFill = defaultFill;
        invalidate();
        requestLayout();
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
