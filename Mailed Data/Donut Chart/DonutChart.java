package com.spintum.preexam;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;
import android.graphics.Color;

/**
 * Created by Nirmal on 2/9/2016.
 */

public class DonutChart extends View {
    private float radius;
    Paint paint;
    Paint shadowPaint;
    int a,b,c;
    Path myPath;
    Path shadowPath;
    RectF outterCircle;
    RectF innerCircle;
    RectF shadowRectF;
    public DonutChart(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.DonutChart,0, 0);

        try {
            radius = a.getDimension(R.styleable.DonutChart_radius, 20.0f);
        } finally {
            a.recycle();
        }
        paint = new Paint();
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(radius / 14.0f);
        shadowPaint = new Paint();
        shadowPaint.setColor(0xf0000000);
        shadowPaint.setStyle(Paint.Style.STROKE);
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStrokeWidth(6.0f);
        shadowPaint.setMaskFilter(new BlurMaskFilter(4, BlurMaskFilter.Blur.SOLID));
        myPath = new Path();
        shadowPath = new Path();
        outterCircle = new RectF();
        innerCircle = new RectF();
        shadowRectF = new RectF();
        float adjust = (.019f*radius);
        shadowRectF.set(adjust, adjust, radius*2-adjust, radius*2-adjust);
        adjust = .038f * radius;
        outterCircle.set(adjust, adjust, radius*2-adjust, radius*2-adjust);
        adjust = .276f * radius;
        innerCircle.set(adjust, adjust, radius * 2 - adjust, radius * 2 - adjust);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // draw shadow
        paint.setShader(null);
        float adjust = (.0095f*radius);
        paint.setShadowLayer(8, adjust, -adjust, 0xaa000000);
        drawDonut(canvas, paint, 0, 359.9f);
        //Maroon
        //setGradient(0xff84BC3D,0xff5B8829);
        setGradient(0xffffb300,0xffffb300);
        drawDonut(canvas,paint, a,b);
        //Violet
        //setGradient(0xffe04a2f,0xffB7161B);
        setGradient(0xffd32f2f,0xfff44336);
        drawDonut(canvas, paint, 0,a);
        // White
       // setGradient(Color.TRANSPARENT,Color.TRANSPARENT);
        //drawDonut(canvas, paint, a+b)0, 180);
        // Green
        setGradient(0xff388e3c,0xff66bb6a);
        drawDonut(canvas, paint,(a+b),c);
    }
    public void drawDonut(Canvas canvas, Paint paint, float start,float sweep){

        myPath.reset();
        myPath.arcTo(outterCircle, start, sweep, false);
        myPath.arcTo(innerCircle, start+sweep, -sweep, false);
        myPath.close();
        canvas.drawPath(myPath, paint);
    }

    public void setGradient(int sColor, int eColor){
        paint.setShader(new RadialGradient(radius, radius, radius - 5,
                new int[]{sColor, eColor},
                new float[]{.6f, .95f}, TileMode.CLAMP));
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = (int) radius*2;
        int desiredHeight = (int) radius*2;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        //70dp exact
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }else if (widthMode == MeasureSpec.AT_MOST) {
            //wrap content
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }
        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }
        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }
    public void getData(int x,int y){
        invalidate();
        a=((x*360)/10);
        b=(y*360)/10;
        c=180-(a+b);
    }
}

