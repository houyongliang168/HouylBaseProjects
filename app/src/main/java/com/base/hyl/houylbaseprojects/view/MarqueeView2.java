package com.base.hyl.houylbaseprojects.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.base.hyl.houylbaseprojects.R;

public class MarqueeView2 extends View {
    //设置默认值
    private int TEXT_COLOR=Color.BLACK;//默认颜色为 黑色
    private int TEXT_SIZE=12;//默认字体大小
    private float TEXT_SPEED=1;//默认字体速度
    private int TEXT_DISTANCE=20;//默认间距
    private float TEXT_START_DISTANCE=20;//默认间距
    private boolean IS_RESET_TEXT=true;//默认重新设置内容
    private boolean IS_CLICKALBE_STOP=false;//默认重新设置内容

    private int REPET_TYPE = REPET_INTERVAL;//滚动模式
    public static final int REPET_ONCETIME = 0;//一次结束
    public static final int REPET_INTERVAL = 1;//一次结束以后，再继续第二次
    public static final int REPET_CONTINUOUS = 2;//紧接着 滚动第二次
    //设置实际值
    private String contentText; //获取内容
    private int textColor;
    private float textSize;
    private float speed;
    private int repet_type;
    private int text_distance;
    private float text_start_distance;
    private boolean is_reset_text;
    private boolean is_clickalbe_stop;
    /*设置默认初始值*/
    private int WIDTH=100;
    private int  HEIGHT=100;


    public MarqueeView2(Context context) {
        this(context, null);
    }

    public MarqueeView2(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet,0);
    }

    public MarqueeView2(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initParameter(attributeSet);//初始化参数
        initPaint();//初始化画笔
    }

    /**
     * 初始化参数 获取 xml 的具体值
     * @param attrs
     */
    private void initParameter(AttributeSet attrs) {//初始化参数
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MyMarqueeView);
        //获取文字内容
        contentText = ta.getString(R.styleable.MyMarqueeView_text);
        //获取文字颜色，默认颜色是黑色
        textColor = ta.getColor(R.styleable.MyMarqueeView_text_color, TEXT_COLOR);
        //获取字体大小，默认为14sp
        textSize = ta.getDimension(R.styleable.MyMarqueeView_text_size, TEXT_SIZE);
        //获取字体大小，默认为1
        speed = ta.getFloat(R.styleable.MyMarqueeView_text_size, TEXT_SPEED);
        //获取默认的模式  为 普通模式
        repet_type = ta.getInt(R.styleable.MyMarqueeView_repet_type, REPET_TYPE);
        //获取默认间距 20
        text_distance = ta.getInt(R.styleable.MyMarqueeView_text_distance, TEXT_DISTANCE);
        //获取初始位置 间距为 百分比
        text_start_distance = ta.getFloat(R.styleable.MyMarqueeView_text_start_distance, TEXT_START_DISTANCE);
        //获取初始位置 间距为 百分比
        is_reset_text = ta.getBoolean(R.styleable.MyMarqueeView_is_reset_text, IS_RESET_TEXT);
        //是否点击暂停 默认false
        is_clickalbe_stop = ta.getBoolean(R.styleable.MyMarqueeView_is_clickalbe_stop, IS_CLICKALBE_STOP);
        //view 自带背景颜色等参数
        ta.recycle();
    }

    /**
     * 初始化画笔
     *
     */
    private Rect rect;
    private TextPaint paint;
    private void initPaint(){
        rect = new Rect();
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);//初始化文本画笔
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(textColor);//文字颜色值,可以不设定
        paint.setTextSize(dp2px(textSize));//文字大小

    }
    /*获取px 值*/
    private int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /*线程或动画停止 时机*/
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
    /*线程或动画开始的 时机*/
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
    /*画布 绘制内容  在画布上绘制 需要 画布的对象*/
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int paddingLeft= getPaddingLeft();
        final int paddingRight= getPaddingRight();
        final int paddingTop= getPaddingTop();
        final int paddingBottom= getPaddingBottom();
        int width=getWidth()-paddingLeft-paddingRight;//获取 绘制区域 宽度
        int height=getHeight()-paddingTop-paddingBottom;//获取 绘制区域 高度

    }

    @Override
    protected void onMeasure(int i, int i1) {
        super.onMeasure(i, i1);
        int widthSpecMode= MeasureSpec.getMode(i);
        int widthSpecSize= MeasureSpec.getSize(i);
        int heightSpecMode= MeasureSpec.getMode(i1);
        int heightSpecSize= MeasureSpec.getSize(i1);
        if(widthSpecMode==MeasureSpec.AT_MOST&&heightSpecMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(WIDTH,HEIGHT);
        }else if(widthSpecMode==MeasureSpec.AT_MOST){//宽度
            setMeasuredDimension(WIDTH,heightSpecSize);
        }else if(heightSpecMode==MeasureSpec.AT_MOST){//高度
            setMeasuredDimension(widthSpecSize,HEIGHT);
        }
    }

}
