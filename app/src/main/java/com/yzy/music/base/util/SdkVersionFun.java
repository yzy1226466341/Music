package com.yzy.music.base.util;

import android.content.Context;
import android.os.Build;

/**
 * 根据Android Sdk调用适宜的API
 * Created by yzy on 2016/9/21.
 */
public class SdkVersionFun {
    /**
     * 获取系统定义的颜色
     * @param context
     * @param id
     * @return
     */
    public static int getColor(Context context,int id){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            return context.getApplicationContext().getColor(id);
        }else{
            return context.getApplicationContext().getResources().getColor(id);
        }
    }
}
