package com.example.gao.mooc_qq50sliddingmenu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.gao.mooc_qq50sliddingmenu.R;
import com.nineoldandroids.view.ViewHelper;


/**
 * Created by gao on 2016/5/23.
 */
public class SliddingMenu extends HorizontalScrollView{
    private LinearLayout mWapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;


    private int mScreenWidth;

    private int mMenuWidth;

    //dp   �˵�����Ļ�Ҳ�ľ���
    private int mMenuRightPadding=50;


    private boolean once=false;


    private boolean isOpen;

    public SliddingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);//ע����this��  ��������
//        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics outMetrics=new DisplayMetrics();
//
//        wm.getDefaultDisplay().getMetrics(outMetrics);
//        mScreenWidth=outMetrics.widthPixels;
//        //��dpת��Ϊpx
//        mMenuRightPadding= (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics());



    }
    public SliddingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //��ȡ���Ƕ��������
        TypedArray array=context.getTheme().obtainStyledAttributes(attrs, R.styleable.SliddingMenu,defStyleAttr,0);

        int n=array.getIndexCount();
        for(int i=0;i<n;i++){
            int attr=array.getIndex(i);
            switch(attr){
                case R.styleable.SliddingMenu_rightPadding:
                    mMenuRightPadding=array.getDimensionPixelSize(attr,(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics()));
                    break;
            }
        }
        //һ��Ҫ�ǵ��ͷ�
        array.recycle();

        WindowManager wm=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();

        wm.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth=outMetrics.widthPixels;
        //��dpת��Ϊpx
      // mMenuRightPadding= (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,context.getResources().getDisplayMetrics())
    }
    /**
     * ���ڴ�����new��ʱ�� ����һ��������ʱ����
     * @param context
     */
    public SliddingMenu(Context context) {
        this(context, null);
    }



    /**
     * ������View�Ŀ�͸�
     * �����Լ��Ŀ�͸�
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(!once) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);

            mMenuWidth=mMenu.getLayoutParams().width = mScreenWidth-mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            //�Լ��ĸ��Ѿ���menu��content������ ������������

            once=true;
        }

    }

    /**
     * ͨ������ƫ��������Menu����
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    /**
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX=getScrollX();
                if(scrollX>=mMenuWidth/2){
                    this.smoothScrollTo(mMenuWidth,0);
                    isOpen=false;
                }
                else{
                    this.smoothScrollTo(0,0);
                    isOpen=true;
                }
                return true;
        }



        return super.onTouchEvent(ev);
    }


    public void openMenu(){
        if(isOpen)return;
        this.smoothScrollTo(0,0);
        isOpen=true;
    }
    public void closeMenu(){
        if(!isOpen)return;
        this.smoothScrollTo(mMenuWidth,0);
        isOpen=false;
    }

    public void toggle(){
        if(isOpen){
            closeMenu();
        }
        else{
            openMenu();
        }
    }

    /**
     * ��������ʱ������������
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    protected void onScrollChanged(int l,int t,int oldl,int oldt){
        super.onScrollChanged(l, t, oldl, oldt);
        //ע�� lһ��ʼΪmMenuWidth
        float scale=l*1.0f/mMenuWidth;//1~0  һ���ݶ�
        // �������Զ���������TranslationX



        //ת����QQ5.0
        /**
         * ����1����������1.0~0.7��һ���������������Ч��
         scale: 1.0~0.0
         0.7+0.3*scale   ����1.0~0.7


         ����2���˵���ƫ������Ҫ�޸�
         ����3���˵�����ʾʱ�����ţ��Լ�͸���ȵı仯
         ���ţ�0.7~1.0
         1-scale*0.3
         ͸����  0.6~1.0
         0.6+0.4*(1-scale)
         */


        float rightScale=0.7f+0.3f*scale;
        float leftScale=1.0f-scale*0.5f;
        float leftAlpha=0.6f+0.4f*(1-scale);
        ViewHelper.setTranslationX(mMenu,mMenuWidth*scale*0.8f);

        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);

        ViewHelper.setAlpha(mMenu,leftAlpha);


        //����content�������ĵ㣬�����ĵ�����Ϊ����е�
        ViewHelper.setPivotX(mContent,0);
        ViewHelper.setPivotY(mContent,mContent.getHeight()/2);
        ViewHelper.setScaleX(mContent,rightScale);
        ViewHelper.setScaleY(mContent,rightScale);
    }



}
