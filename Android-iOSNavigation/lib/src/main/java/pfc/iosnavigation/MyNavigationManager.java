package pfc.iosnavigation;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by developer on 17/1/2.
 * push use a animation
 * pop use a reserve animation autoly
 */

public class MyNavigationManager extends RelativeLayout {

    public static enum AnimationType{
        ANIMATION_TYPE_NONE,
        ANIMATION_TYPE_DOWN_TO_UP,
        ANIMATION_TYPE_RIGHT_TO_LEFT,
        ANIMATION_TYPE_FADE_IN_OUT,
        ANIMATION_TYPE_SCALE
    }

    private static int ANIMATION_DURATION = 150;
    private Context mContext;
    private OnFirstPushListener firstPushListener;
    public MyClickLisetener backBtnClickListener = null;

    public void setLastPopListener(OnLastPopListener lastPopListener) {
        this.lastPopListener = lastPopListener;
    }

    public void setFirstPushListener(OnFirstPushListener firstPushListener) {
        this.firstPushListener = firstPushListener;
    }

    private OnLastPopListener lastPopListener;
    private boolean isAnimating = false;



    //record child views entity
    private ArrayList<MyNavigationItem> childViews = new ArrayList<MyNavigationItem>();
    public MyNavigationManager(Context context) {
        this(context,null);
    }

    public MyNavigationManager(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyNavigationManager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        this.setBackgroundColor(Color.argb(0,0,0,0));
    }

    /**
     * default animation ANIMATION_TYPE_RIGHT_TO_LEFT
     * @param view
     */
    public void pushView(MyNavigationView view){
        pushView(view, AnimationType.ANIMATION_TYPE_RIGHT_TO_LEFT,false);
    }

    /**
     * default animation ANIMATION_TYPE_RIGHT_TO_LEFT
     * @param view
     * @param isDismissViewStable default true
     */
    public void pushView(MyNavigationView view, boolean isDismissViewStable){
        pushView(view, AnimationType.ANIMATION_TYPE_RIGHT_TO_LEFT,isDismissViewStable);
    }

    public void pushView(MyNavigationView view, AnimationType animationType, boolean isDismissViewStable){
        pushView(view,animationType,isDismissViewStable,null);
    }

    /**
     * choose a animation type
     * @param view
     * @param animationType
     * @param isDismissViewStable
     */
    public void pushView(MyNavigationView view, AnimationType animationType, boolean isDismissViewStable, OnAnimationEndListener listener){
        if (isAnimating)return;
        if (this.childViews.size()==0){
            this.setVisibility(View.VISIBLE);
        }
        view.setManager(this);
        this.addView(view);
        MyNavigationItem item = new MyNavigationItem(view,isDismissViewStable,animationType);
        this.childViews.add(item);
        int childSize = childViews.size();
        MyNavigationView willDismissView = childSize>1?childViews.get(childSize-2).childView:null;
        if (willDismissView != null){
            willDismissView.viewWillDisappear();
        }
        view.viewWillAppear();
        if (childSize==1){
            if (firstPushListener != null){
                firstPushListener.beforeFirstPush();
            }
        }
        if (animationType == AnimationType.ANIMATION_TYPE_NONE) {
            afterPush(view, willDismissView);
        }
        else {
            startAnimation(view, willDismissView, animationType, true, isDismissViewStable, listener);
        }
    }

    /**
     * release a child view use a reserve animation for push if animated is true
     * @param animated
     */
    public void popView(boolean animated){
        if(isAnimating)return;
        if (childViews.size()<1)return;
        int childSize = childViews.size();
        if (childSize==1){
            if (lastPopListener != null){
                lastPopListener.beforeLastPop();
            }
        }
        MyNavigationItem item = childViews.get(childSize - 1);
        MyNavigationView willDismissView = item.childView;
        AnimationType animationType = item.animationType;
        boolean isStable = item.isStable;

        MyNavigationView willShowView = childSize>1?childViews.get(childSize-2).childView:null;
        willDismissView.viewWillDisappear();
        if (willShowView != null)
            willShowView.viewWillAppear();
        if (animated){
            startAnimation(willShowView,willDismissView,animationType,false,isStable,null);
        }else {
            afterPop(willShowView,willDismissView);
        }
    }



    /**
     * pop to the first view
     */
    public void popToRootView(boolean animated){
        int size = childViews.size();
        if (size < 2) {
            return;
        }
        popToIndex(0,animated);
    }

    /**
     * pop to the specified view
     */
    public void popToIndex(int index, boolean animated){
        if (isAnimating)return;
        if (index<0 || index >= childViews.size() - 1){
            return;
        }else {
            for (int i=index+1;i< childViews.size() - 1;i++){
                MyNavigationItem item = childViews.get(i);
                this.removeView(item.childView);
                childViews.remove(i);
                i--;
            }
        }
        popView(animated);
    }

    public ArrayList<MyNavigationItem> getChildViews() {
        return childViews;
    }

    private void clearAll(){
        this.removeAllViews();
        this.childViews.clear();
    }

    /**
     * to animation
     * @param willShowView
     * @param willDismissView
     * @param animationType
     * @param isPush
     */
    private void startAnimation(final MyNavigationView willShowView, final MyNavigationView willDismissView, AnimationType animationType, boolean isPush, boolean isDismissViewStable, final OnAnimationEndListener listener){
        isAnimating = true;
        Animation showAnimation = null;
        Animation dismissAnimation = null;
        if (willDismissView != null){
            willDismissView.setVisibility(View.VISIBLE);
        }
        if (willShowView != null){
            willShowView.setVisibility(View.VISIBLE);
        }
        if (isPush){
            switch (animationType){
                case ANIMATION_TYPE_RIGHT_TO_LEFT:
                    showAnimation = getAnimation(willShowView, toAnimation.TO_ANIMATION_LEFT,true);
                    if (!isDismissViewStable)
                        dismissAnimation = getAnimation(willDismissView, toAnimation.TO_ANIMATION_LEFT,false);
                    break;
                case ANIMATION_TYPE_DOWN_TO_UP:
                    showAnimation = getAnimation(willShowView, toAnimation.TO_ANIMATION_UP,true);
                    if (!isDismissViewStable)
                        dismissAnimation = getAnimation(willDismissView, toAnimation.TO_ANIMATION_UP,false);
                    break;
                case ANIMATION_TYPE_FADE_IN_OUT:
                    showAnimation = getAnimation(willShowView, toAnimation.TO_ANIMATION_FADE,true);
                    if (!isDismissViewStable)
                        dismissAnimation = getAnimation(willDismissView, toAnimation.TO_ANIMATION_FADE,false);
                    break;
                case ANIMATION_TYPE_SCALE:
                    showAnimation = getAnimation(willShowView, toAnimation.TO_ANIMATION_SCALE,true);
                    if (!isDismissViewStable)
                        dismissAnimation = getAnimation(willDismissView, toAnimation.TO_ANIMATION_SCALE,false);
                    break;
                case ANIMATION_TYPE_NONE:
                    isAnimating = false;
                    afterPush(willShowView,willDismissView);
                    return;


            }
            showAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (listener != null){
                        listener.run();
                    }
                    afterPush(willShowView,willDismissView);
                    isAnimating = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }else {
            switch (animationType){
                case ANIMATION_TYPE_RIGHT_TO_LEFT:
                    if (!isDismissViewStable)
                        showAnimation = getAnimation(willShowView, toAnimation.TO_ANIMATION_RIGHT,true);
                    dismissAnimation = getAnimation(willDismissView, toAnimation.TO_ANIMATION_RIGHT,false);
                    break;
                case ANIMATION_TYPE_DOWN_TO_UP:
                    if (!isDismissViewStable)
                        showAnimation = getAnimation(willShowView, toAnimation.TO_ANIMATION_DOWN,true);
                    dismissAnimation = getAnimation(willDismissView, toAnimation.TO_ANIMATION_DOWN,false);
                    break;
                case ANIMATION_TYPE_FADE_IN_OUT:

                    if (!isDismissViewStable)
                        showAnimation = getAnimation(willShowView, toAnimation.TO_ANIMATION_FADE,true);
                    dismissAnimation = getAnimation(willDismissView, toAnimation.TO_ANIMATION_FADE,false);
                    break;
                case ANIMATION_TYPE_SCALE:
                    if (!isDismissViewStable)
                        showAnimation = getAnimation(willShowView, toAnimation.TO_ANIMATION_SCALE,true);
                    dismissAnimation = getAnimation(willDismissView, toAnimation.TO_ANIMATION_SCALE,false);
                    break;
                case ANIMATION_TYPE_NONE:
                    isAnimating = false;
                    afterPop(willShowView,willDismissView);
                    return;

            }
            dismissAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    afterPop(willShowView,willDismissView);
                    if (listener != null){
                        listener.run();
                    }
                    isAnimating = false;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        if (showAnimation!=null){
            willShowView.startAnimation(showAnimation);
        }
        if (dismissAnimation!=null){
            willDismissView.startAnimation(dismissAnimation);
        }
    }

    private void afterPop(MyNavigationView willShowView, MyNavigationView willDismissView){
        willDismissView.viewDidDisappear();
        willDismissView.viewWillDealloc();
        if (willShowView != null) {
            willShowView.setVisibility(VISIBLE);
            willShowView.viewDidAppear();
        }
        this.removeView(willDismissView);
        this.childViews.remove(this.childViews.size() - 1);
        if (this.childViews.size()==0){
            this.setVisibility(View.GONE);
            if (lastPopListener != null){
                lastPopListener.afterLastPop();
            }
        }
    }

    private void afterPush(MyNavigationView willShowView, MyNavigationView willDismissView){
        if (willShowView != null){
            willShowView.setVisibility(VISIBLE);
            willShowView.viewDidAppear();
        }
        if (willDismissView != null) {
            willDismissView.setVisibility(View.GONE);
            willDismissView.viewDidDisappear();
        }
        if (childViews.size()==1){
            if (firstPushListener != null){
                firstPushListener.afterFirstPush();
            }
        }
    }

    private enum toAnimation {
        TO_ANIMATION_UP,
        TO_ANIMATION_DOWN,
        TO_ANIMATION_LEFT,
        TO_ANIMATION_RIGHT,
        TO_ANIMATION_FADE,
        TO_ANIMATION_SCALE
    }

    private Animation getAnimation(MyNavigationView view, toAnimation toAnimationDirection, boolean isShow){
        return getAnimation(view,toAnimationDirection,isShow,null);
    }

    private Animation getAnimation(MyNavigationView view, toAnimation toAnimationDirection, boolean isShow, final OnAnimationEndListener listener){
        Animation animation = null;
        if (view == null)return null;
        switch (toAnimationDirection){

            case TO_ANIMATION_UP:
                if (isShow)
                    animation = new TranslateAnimation(0, 0, this.getHeight() , 0);
                else
                    animation = new TranslateAnimation(0, 0, 0 ,-this.getHeight());
                break;
            case TO_ANIMATION_DOWN:
                if (!isShow)
                    animation = new TranslateAnimation(0, 0, 0, this.getHeight());
                else
                    animation = new TranslateAnimation(0, 0, -this.getHeight() , 0);
                break;
            case TO_ANIMATION_LEFT:
                if (isShow)
                    animation = new TranslateAnimation(this.getWidth(), 0, 0, 0);
                else
                    animation = new TranslateAnimation(0,-this.getWidth(), 0, 0);
                break;
            case TO_ANIMATION_RIGHT:
                if (!isShow)
                    animation = new TranslateAnimation(0,this.getWidth(), 0, 0);
                else
                    animation = new TranslateAnimation(-this.getWidth(), 0, 0, 0);
                break;
            case TO_ANIMATION_FADE:
                if (isShow)
                    animation = new AlphaAnimation(0,1);
                else
                    animation = new AlphaAnimation(1,0);
                break;
            case TO_ANIMATION_SCALE:
                //to stretch view height from 0 to view's height
                if (isShow)
                    animation = new ScaleAnimation(1,1,0,1);
                else//to compress view height from view's height to 0
                    animation = new ScaleAnimation(1,1,1,0);
                break;
        }
        animation.setDuration(ANIMATION_DURATION);
        if (listener != null) {
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (listener != null) {
                        listener.run();
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        return animation;
    }

    public interface OnAnimationEndListener{
        public void run();
    }

    public interface OnFirstPushListener{
        public void afterFirstPush();
        public void beforeFirstPush();
    }

    public interface OnLastPopListener{
        public void afterLastPop();
        public void beforeLastPop();
    }

    public interface MyClickLisetener{
        public void onClick();
    }
}
