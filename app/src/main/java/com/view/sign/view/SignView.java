package com.view.sign.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author : zhoukai
 * time   : 2018/05/08
 * desc   : 自定义签名控件
 */
public class SignView extends View {

    private Bitmap mBitmap; //绘制的图片
    private Canvas mCanvas;     //画布
    private Path mPath; //绘制路径
    private Paint mBitmapPaint; //绘制图片的画笔
    private Paint mPathPaint;   //绘制路径的画笔
    private float mX, mY;    //坐标
    private final float TOUCH_TOLERANCE = 4;    //公差

    public SignView(Context context) {
        super(context);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPath = new Path();// 绘图路径
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);// 绘制图片的画笔
        mPathPaint = new Paint();// 绘制路径的画笔
        mPathPaint.setAntiAlias(true);
        mPathPaint.setDither(true);
        mPathPaint.setColor(0xFF000000);// 颜色
        mPathPaint.setStyle(Paint.Style.STROKE);// 样式 线条
        mPathPaint.setStrokeJoin(Paint.Join.ROUND);
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        mPathPaint.setStrokeWidth(7);// 笔画宽度
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.createBitmap();
    }

    // 生成签名图片

    protected void createBitmap() {
        if (mBitmap != null) {
            return;
        }
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        //生成图片的背景颜色
        mBitmap.eraseColor(Color.WHITE);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.drawPath(mPath, mPathPaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();// 横坐标
        float y = event.getY();// 纵坐标
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// 手指接触到屏幕
                mPath.reset();
                mPath.moveTo(x, y);
                mX = x;
                mY = y;
                break;
            case MotionEvent.ACTION_MOVE:// 手指移动
                float dx = Math.abs(x - mX);// 横坐标移动量
                float dy = Math.abs(y - mY);// 纵坐标移动量
                if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                    mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);// 移动画笔
                    mX = x;
                    mY = y;
                }
                break;
            case MotionEvent.ACTION_UP:// 手指释放
                mPath.lineTo(mX, mY);// 路径到达终点坐标
                mCanvas.drawPath(mPath, mPathPaint);// 通过路径，在画布上画图
                mPath.reset();// 重新设置绘图路径到原始状态
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 清除之前画的图像
     */
    public void clearSignature() {
        mBitmap.eraseColor(Color.TRANSPARENT);
        invalidate();
        mBitmap.eraseColor(Color.WHITE);
    }

    /**
     * 获取画好的图像
     * @return
     */
    public Bitmap getSignatureBitmap() {
        return mBitmap;
    }

}