package com.ethoslabs.radialprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by John on 7/28/2015.
 */
public class RadialProgressBar extends View {

    protected int circumference = 75;
    protected int defaultFill = 0;
    protected int currentFill = 0;
    protected boolean spinning = false;
    protected int backgroundColor = 0;
    protected int fillColor = 0;

    protected Paint backgroundPaint;
    protected Paint fillPaint;
    protected Context mContext;

    protected int diameter = 0;

    protected RectF circleBounds;
    protected Path backgroundPath;
    protected Path fillPath;

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
            backgroundColor = a.getColor(R.styleable.RadialProgressBar_backgroundColor, context.getResources().getColor(R.color.accent_material_dark));
            fillColor = a.getColor(R.styleable.RadialProgressBar_backgroundColor, context.getResources().getColor(R.color.accent_material_light));
        }finally{
            a.recycle();
        }
        mContext = context;
    }

    public RadialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RadialProgressBar,
                0, 0);

        try{
            circumference = a.getInt(R.styleable.RadialProgressBar_circumference, 75);
            defaultFill = a.getInt(R.styleable.RadialProgressBar_defaultFill, 0);
            spinning = a.getBoolean(R.styleable.RadialProgressBar_spinning, false);
            backgroundColor = a.getColor(R.styleable.RadialProgressBar_backgroundColor, context.getResources().getColor(R.color.accent_material_dark));
            fillColor = a.getColor(R.styleable.RadialProgressBar_fillColor, context.getResources().getColor(R.color.accent_material_light));
        }finally{
            a.recycle();
        }
    }

    public RadialProgressBar(Context context) {
        super(context);
        mContext = context;

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

    public void init(){

        currentFill = defaultFill;

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        fillPaint.setStyle(Paint.Style.STROKE);
        fillPaint.setStrokeCap(Paint.Cap.ROUND);

        float strokeWidth = 35;
        diameter = diameter - (int)strokeWidth;
        backgroundPaint.setStrokeWidth(strokeWidth);
        fillPaint.setStrokeWidth(strokeWidth);

        if(backgroundColor == 0)
            backgroundColor = mContext.getResources().getColor(R.color.accent_material_dark);

        if(fillColor == 0)
            fillColor = mContext.getResources().getColor(R.color.accent_material_light);

        backgroundPaint.setColor(backgroundColor);
        fillPaint.setColor(fillColor);

        float size = diameter/2;
        float centerX = getWidth()/2;
        float centerY = getHeight()/2;

        circleBounds = new RectF();
        circleBounds.set(centerX - size, centerY - size, centerX + size, centerY + size);
        backgroundPath = new Path();
        float endAngle = 360.f*((float)circumference/100.f);
        backgroundPath.addArc(circleBounds, -0, endAngle);

        Matrix mMatrix = new Matrix();
        mMatrix.postRotate(-90, centerX, centerY);
        backgroundPath.transform(mMatrix);

        fillPath = new Path();
        endAngle = endAngle*((float)currentFill/100.f);
        fillPath.addArc(circleBounds, 0, endAngle);

        fillPath.transform(mMatrix);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(w < h){
            diameter = w;
        }else
            diameter = h;

        invalidate();
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int desiredWidth = 100;
        int desiredHeight = 100;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }
        if(width < height){
            diameter = width;
        }else
            diameter = height;
        init();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(backgroundPath, backgroundPaint);
        canvas.drawPath(fillPath, fillPaint);

    }
}
