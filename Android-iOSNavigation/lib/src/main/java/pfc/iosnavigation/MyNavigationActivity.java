package pfc.iosnavigation;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by developer on 17/2/6.
 */

public class MyNavigationActivity extends AppCompatActivity {
    protected MyNavigationManager navigation;
    protected MyNavigationToolBar.MyButtonClickLisetener backKeyDownListener;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        navigation = new MyNavigationManager(this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        navigation.setLayoutParams(params);
        navigation.setVisibility(View.INVISIBLE);
        navigation.setBackground(null);
        this.addContentView(navigation,params);

    }

    /**
     * default animation ANIMATION_TYPE_RIGHT_TO_LEFT
     *
     * @param view
     */
    protected void pushView(MyNavigationView view) {
        navigation.pushView(view);
    }


    /**
     * default animation ANIMATION_TYPE_RIGHT_TO_LEFT
     *
     * @param view
     * @param isDismissViewStable default true
     */
    protected void pushView(MyNavigationView view, boolean isDismissViewStable) {
        navigation.pushView(view, isDismissViewStable);
    }

    protected void pushView(MyNavigationView view, MyNavigationManager.AnimationType animationType, boolean isDismissViewStable) {
        navigation.pushView(view,animationType,isDismissViewStable);
    }

    /**
     * choose a animation type
     *
     * @param view
     * @param animationType
     * @param isDismissViewStable
     */
    protected void pushView(MyNavigationView view, MyNavigationManager.AnimationType animationType, boolean isDismissViewStable, MyNavigationManager.OnAnimationEndListener listener) {
        navigation.pushView(view,animationType,isDismissViewStable,listener);
    }

    protected void popView(boolean animated){
        navigation.popView(animated);

    }

    protected void popToIndex(int index,boolean animated){
        navigation.popToIndex(index,animated);
    }

    protected void popToRootView(boolean animated){
        navigation.popToRootView(animated);
    }

    protected int getNavigationChildCount(){
        return navigation.getChildViews().size();
    }

    public boolean myBackButtonClick(){
        if (getNavigationChildCount()>0){
            MyNavigationView view = navigation.getChildViews().get(getNavigationChildCount() - 1).childView;
            if (view.isBackListenerValid()){
                view.naviBack();
            }
            return true;
        }else {
            if (backKeyDownListener != null){
                backKeyDownListener.onClick();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if (!myBackButtonClick()){
                return super.onKeyDown(keyCode,event);
            }
        }
        return false;
    }

}
