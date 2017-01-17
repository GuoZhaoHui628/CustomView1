package guozhaohui.com.customview1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by ${GuoZhaoHui} on 2017/1/16.
 * Abstract:
 */

public class CustomTitleView extends View {

    private static final String TAG = "CustomTitleView";
    private String mTitleText;
    private int mTitleTextColor;
    private int mTitleTextSize;

    private Rect mBound;
    private Paint mPaint;

    private static Random mRandom = new Random();

    public CustomTitleView(Context context) {
       this(context,null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for(int i=0;i<n;i++){

            int attr = a.getIndex(i);
            switch (attr){

                case R.styleable.CustomTitleView_textTitle:

                    mTitleText =   a.getString(attr);

                    break;

                case R.styleable.CustomTitleView_textTitleColor:

                    mTitleTextColor =  a.getColor(attr,getResources().getColor(R.color.colorPrimaryDark));

                    break;

                case R.styleable.CustomTitleView_textTitleSize:

                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));

                    break;

            }

        }
        a.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);

        mBound = new Rect();
        //Return in bounds (allocated by the caller) the smallest rectangle that encloses all of the characters, with an implied origin at (0,0).
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mTitleText = randomText();
                postInvalidate();

            }
        });

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int widthResult = 0;
        int heightResulr = 0;


        if(widthMode==MeasureSpec.EXACTLY){

            widthResult = widthSize;

        }else{

            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mBound);

            float textWidth = mBound.width();
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            widthResult = desired;

        }


        if(heightMode == MeasureSpec.EXACTLY){
            heightResulr = heightSize;
        }else{

            mPaint.setTextSize(mTitleTextSize);
            mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);


            float textHeight = mBound.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            heightResulr = desired;

        }



        setMeasuredDimension(widthResult,heightResulr);

    }


    @Override
    protected void onDraw(Canvas canvas) {

        mPaint.setColor(getResources().getColor(R.color.yellow));
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(mTitleTextColor);
        canvas.drawText(mTitleText,getWidth()/2-mBound.width()/2,getHeight()/2+mBound.height()/2,mPaint);
        Log.i(TAG, "getwidth()-->" + getWidth() + "   mBound.width()-->" + mBound.width());


        //干扰由两部分组成，圆点和线

        for(int i=0;i<500;i++){
            mPaint.setColor(getResources().getColor(R.color.black));
            PointF pointf = new PointF(mRandom.nextInt(getWidth())+1, mRandom.nextInt(getHeight())+1);
            canvas.drawPoint(pointf.x,pointf.y,mPaint);
        }


        //绘制干扰线
        for(int i=0;i<10;i++){

            //给画笔设置随机颜色
            mPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20,
                    mRandom.nextInt(200) + 20);

            canvas.drawLine(mRandom.nextInt(getWidth()),mRandom.nextInt(getHeight()),mRandom.nextInt(getWidth()),mRandom.nextInt(getHeight()),mPaint);
        }


    }

    public String randomText(){

        Random random = new Random();
        Set<Integer> set = new HashSet<>();

        while(set.size()<4){

            int randomInt = random.nextInt(10);
            set.add(randomInt);

        }

        StringBuilder sb = new StringBuilder();
        for(Integer i:set){

            sb.append("" + i);

        }
        return sb.toString();
    }


    //判断验证码是否一致
    public boolean isJudgeAgree(String content){
        return content.equals(mTitleText);
    }

}
