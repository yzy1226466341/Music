package com.yzy.music.music.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.yzy.music.R;
import com.yzy.music.base.activity.BaseActivity;
import com.yzy.music.base.core.AllActivity;
import com.yzy.music.databinding.ActivityMainBinding;
/**
 * Create by yzy on 2016/10/8
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    ActivityMainBinding bing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bing = DataBindingUtil.setContentView(this, R.layout.activity_main);
        bing.setMyClick(this);
        Log.i("MainActivity" , "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnMenu:{
                bing.slidemenu.toggle();
                break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK &&event.getAction() == KeyEvent.ACTION_DOWN ){   //按返回键时
            if(bing.slidemenu.isOpen()){
                bing.slidemenu.closeMenu();
                return true;
            }else{
                this.finish();
                AllActivity.exitApp();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
