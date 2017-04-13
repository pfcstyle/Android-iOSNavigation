package pfc.iosnavigation;

/**
 * Created by developer on 17/1/2.
 */

public class MyNavigationItem extends Object {
    public MyNavigationView childView;
    public boolean isStable;
    public MyNavigationManager.AnimationType animationType;
    public MyNavigationItem(MyNavigationView v, boolean stable, MyNavigationManager.AnimationType type){
        this.childView = v;
        this.animationType = type;
        this.isStable = stable;
    }
}
