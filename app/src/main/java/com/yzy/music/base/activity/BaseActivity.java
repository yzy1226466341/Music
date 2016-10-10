package com.yzy.music.base.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yzy.music.R;
import com.yzy.music.base.core.AllActivity;
import com.yzy.music.base.util.SdkVersionFun;

/**
 * Activity基类,实现>4.4沉淀式以及添加移除Activity,实现Activity的统一的管理
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AllActivity.addActivity(this);
        setSystemTintManager();
    }

    /**
     * 设置界面沉淀式
     */
    private void setSystemTintManager() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {  //获取Android版本,当版本>19(4.4,巧克力)时,实现沉淀式状态栏,Build获取Android系统信息
            setTranslucentStatus(true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        //tintManager.setTintAlpha(0);   //设置全透明
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        // 自定义状态栏颜色
        tintManager.setTintColor(SdkVersionFun.getColor(this, R.color.theme_color));
    }

    /**
     * 设置透明状态栏
     * @param on
     */
    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AllActivity.removeActiviy(this);
    }
}
