package pfc.iosnavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by developer on 17/2/6.
 */

public class MyNavigationView extends RelativeLayout {

    public void setManager(MyNavigationManager manager) {
        this.manager = manager;
    }

    private MyNavigationManager manager;

    protected MyNavigationActivity mNavigationContext;
    protected MyNavigationToolBar myNavigationToolBar;
    private RelativeLayout contentView;
    private boolean isBackBtnEnable = true;
    private boolean isFuncBtnEnable = true;

    private MyNavigationToolBar.MyButtonClickLisetener mBackBtnListener;
    private MyNavigationToolBar.MyButtonClickLisetener mFuncBtnListener;

    public MyNavigationView(Context context, int rid){
        super(context);
        mNavigationContext = (MyNavigationActivity) context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_navigation_view,this);
        View view = inflater.inflate(rid,null);
        myNavigationToolBar = (MyNavigationToolBar) findViewById(R.id.navi_toolbar);
        contentView = (RelativeLayout) findViewById(R.id.content_view);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        contentView.addView(view);
        setBackBtnListener(new MyNavigationToolBar.MyButtonClickLisetener() {
            @Override
            public void onClick() {
                popView(true);
            }
        });
        setToolBarFuncBtnHide(true);
        setTitle("");
        viewDidLoad();
    }

    public void viewWillAppear(){

    }

    public void viewDidAppear(){

    }

    public void viewWillDisappear(){

    }

    public void viewDidDisappear(){

    }

    public void viewDidLoad(){

    }

    public void viewWillDealloc(){
        manager = null;
    }

    /**
     * default animation ANIMATION_TYPE_RIGHT_TO_LEFT
     *
     * @param view
     */
    public void pushView(MyNavigationView view) {
        manager.pushView(view);
    }


    /**
     * default animation ANIMATION_TYPE_RIGHT_TO_LEFT
     *
     * @param view
     * @param isDismissViewStable default true
     */
    public void pushView(MyNavigationView view, boolean isDismissViewStable) {
        manager.pushView(view, isDismissViewStable);
    }

    public void pushView(MyNavigationView view, MyNavigationManager.AnimationType animationType, boolean isDismissViewStable) {
        manager.pushView(view,animationType,isDismissViewStable);
    }

    /**
     * choose a animation type
     *
     * @param view
     * @param animationType
     * @param isDismissViewStable
     */
    public void pushView(MyNavigationView view, MyNavigationManager.AnimationType animationType, boolean isDismissViewStable, MyNavigationManager.OnAnimationEndListener listener) {
        manager.pushView(view,animationType,isDismissViewStable,listener);
    }

    public void popView(boolean animated){
        manager.popView(animated);

    }

    public void popToIndex(int index,boolean animated){
        manager.popToIndex(index,animated);
    }

    public void popToRootView(boolean animated){
        manager.popToRootView(animated);
    }

    public void setToolBarHide(boolean isHide){
        if (isHide)
            myNavigationToolBar.setVisibility(GONE);
        else
            myNavigationToolBar.setVisibility(VISIBLE);
    }

    public void setToolBarBackBtnHide(boolean isHide){
        if (isHide){
            myNavigationToolBar.mBackButton.setVisibility(GONE);
            isBackBtnEnable = false;
        }else {
            myNavigationToolBar.mBackButton.setVisibility(VISIBLE);
            isBackBtnEnable = true;
        }
    }

    public void setToolBarFuncBtnHide(boolean isHide){
        if (isHide){
            myNavigationToolBar.mFunctionButton.setVisibility(GONE);
            isFuncBtnEnable = false;
        }else {
            myNavigationToolBar.mFunctionButton.setVisibility(VISIBLE);
            isFuncBtnEnable = true;
        }
    }

    public void setTitle(String title){
        myNavigationToolBar.mTitleView.setText(title);
    }

    public void setFunctionBtnText(String text){
        myNavigationToolBar.mFunctionButton.setText(text);
    }

    public void setBackBtnListener(MyNavigationToolBar.MyButtonClickLisetener listener){
        mBackBtnListener = listener;
        myNavigationToolBar.setBackButtonClickEvent(listener);
    }

    public void setFuncBtnListener(MyNavigationToolBar.MyButtonClickLisetener listener){
        mFuncBtnListener = listener;
        myNavigationToolBar.setFunctionButtonEvent(listener);
    }

    public MyNavigationToolBar.MyButtonClickLisetener getBackBtnListener() {
        return mBackBtnListener;
    }

    public MyNavigationToolBar.MyButtonClickLisetener getFuncBtnListener() {
        return mFuncBtnListener;
    }

    public boolean isBackListenerValid(){
        return mBackBtnListener != null && isBackBtnEnable && myNavigationToolBar.mBackButton.isEnabled();
    }

    public boolean isFuncListenerValid(){
        return mFuncBtnListener != null && isFuncBtnEnable && myNavigationToolBar.mFunctionButton.isEnabled();
    }

    public void naviBack(){
        if (mBackBtnListener != null){
            mBackBtnListener.onClick();
        }
    }

}
