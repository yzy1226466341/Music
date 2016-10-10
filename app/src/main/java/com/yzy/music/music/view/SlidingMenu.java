package com.yzy.music.music.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.yzy.music.R;
import com.yzy.music.base.util.ScreenUtils;

/**
 * Created by yzy on 2016/10/8.
 * 自定义的SlidingMenu侧滑菜单(界面最外层)
 * TypedValue.applyDimension : 将dp sp -->px 的方法,但是不能将px --> dp sp
 */
public class SlidingMenu extends HorizontalScrollView{
    private int mScreenWidth ; //屏幕宽度
    private boolean mFirst; //第一次初始化
    private ViewGroup mMenu;//menu布局
    private ViewGroup mContent; //内容布局
    private int mMenuWidth; //Menu布局的宽度
    private int mHalfMenuWidth; //一半的Menu宽度,判断用户是否滑动到一半的位置,如果滑动到一半的位置说明要执行切换菜单状态的操作
    private boolean isOpen; //菜单是否打开

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    /**
     * 获取指定的属性以及手机屏幕的宽度
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScreenWidth = ScreenUtils.getScreenWidth(context);//获取屏幕的宽度
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu,defStyleAttr,0);//获取设置的paddingRight
        mMenuWidth = a.getDimensionPixelSize(R.styleable.SlidingMenu_menuWidth, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50f,getResources().getDisplayMetrics()));//右侧间距,默认50f
        a.recycle();
    }

    public SlidingMenu(Context context) {
        this(context,null,0);
    }

    /**
     * 测量界面的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //第一次初始化
        if(!mFirst){
            LinearLayout wrapper = (LinearLayout) getChildAt(0);//界面布局
            mMenu = (ViewGroup) wrapper.getChildAt(0);  //获取Menu布局
            mContent = (ViewGroup) wrapper.getChildAt(1);//获取内容布局

            mHalfMenuWidth = mMenuWidth/2;
            mMenu.getLayoutParams().width = mMenuWidth ;    //设置Menu的宽度
            mContent.getLayoutParams().width = mScreenWidth;//设置内容视图的宽度
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 获取视图界面显示位置
     * @param changed 视图是否有新的位置,也就是视图是否存在位置的变化
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.scrollTo(mMenuWidth,0);//将菜单隐藏,相当于向左侧滑动
            mFirst = true;//初始化完成
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle(){
        if(isOpen){
            closeMenu();
        }else{
            openMenu();
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu(){
        if(isOpen){
            this.smoothScrollTo(mMenuWidth,0); //平滑隐藏到mMenuWidth的位置
            isOpen = false;
        }
    }

    /**
     * 打开菜单
     */
    public void openMenu(){
        if(!isOpen) {
            this.smoothScrollTo(0, 0);  //平滑到0,0的位置
            isOpen = true;
        }
    }

    /**
     * 滑动改变事件,处理动画效果
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / mMenuWidth ;  //获取缩放级别，偏移量/宽度
        float leftScale = 1 - 0.3f * scale ;//获取左侧菜单栏缩放比例
        float rightScale = 0.8f + scale * 0.2f;//获取右侧内容缩放比例

        //设置左侧的缩放比例,透明度以及偏移量
        ViewHelper.setScaleX(mMenu,leftScale);
        ViewHelper.setScaleY(mMenu,leftScale);
        ViewHelper.setAlpha(mMenu,0.6f + 0.4f*(1-scale));
        ViewHelper.setTranslationX(mMenu,mMenuWidth*scale*0.7f);

        //设置内容的缩放比以及中心点
        ViewHelper.setPivotX(mContent,0);
        ViewHelper.setPivotY(mContent,mContent.getHeight()/2);
        ViewHelper.setScaleX(mContent,rightScale);
        ViewHelper.setScaleY(mContent,rightScale);
    }

    /**
     * 界面触摸事件
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //判断触摸操作
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:{//抬起时
                int scrollX = getScrollX(); //获取直接显示地方偏移的位置
                if(scrollX > mHalfMenuWidth){ //当距离原先位置大于一半时,关闭菜单(靠近中线偏关闭位置)
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                }else{//当距离原先位置小于一半时,打开菜单(靠近中线偏出现位置)
                    this.smoothScrollTo(0,0);
                    isOpen = true;
                }
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    public boolean isOpen() {
        return isOpen;
    }
}
