package pfc.iosnavigation;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by developer on 17/1/3.
 */

public class MyNavigationToolBar extends Toolbar {

    public TextView mTitleView;
    public ImageButton mBackButton;
    public Button mFunctionButton;

    private final Context mContext;

    public MyNavigationToolBar(Context context) {
        this(context,null);
    }

    public MyNavigationToolBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public MyNavigationToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.mytoolbar_content,this);
        mTitleView = (TextView) findViewById(R.id.mytoolbar_title);
        mBackButton = (ImageButton) findViewById(R.id.mytoolbar_back);
        mFunctionButton = (Button) findViewById(R.id.mytoolbar_function);
    }


    /**
     *
     * @param isSolid default is true
     */
    public void setFunctiionButtonStyle(boolean isSolid){
        if (isSolid){
            mFunctionButton.setBackground(mContext.getResources().getDrawable(R.drawable.button_corner_solid));
            mFunctionButton.setTextColor(Color.WHITE);
        }else {
            mFunctionButton.setBackground(mContext.getResources().getDrawable(R.drawable.button_corner));
            mFunctionButton.setTextColor(Color.parseColor("#00bfff"));
        }

    }

    public void setBackButtonClickEvent(final MyButtonClickLisetener lisetener){
        mBackButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lisetener != null)
                    lisetener.onClick();
            }
        });
    }

    public void setFunctionButtonEvent(final MyButtonClickLisetener lisetener){
        mFunctionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lisetener != null)
                    lisetener.onClick();
            }
        });
    }

    public interface MyButtonClickLisetener{
        public void onClick();
    }

}
