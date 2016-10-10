package com.yzy.music.base.util;

import android.content.Context;
import android.widget.Toast;

/**
 * 自定义Toast工具类
 * Created by yzy on 2016/10/9.
 */
public class ToastUtil {
    private static Toast toast;

    /**
     * 在这里Toast只会初始化一次,不会每次创建,当用户多次单击时,不会等待上一次提示完,而是直接取代
     * @param context
     * @param charSequence
     */
    public static void showOneToast(Context context,CharSequence charSequence){
        if(toast == null){
            toast = Toast.makeText(context,charSequence,Toast.LENGTH_SHORT);
        }else{
            toast.setText(charSequence);
        }
        toast.show();
    }

    /**
     * 在这里Toast每次都会重新创建,每次不是覆盖而是等待完上一次提示再提示下一次
     * @param context
     * @param charSequence
     */
    public static void showToast(Context context,CharSequence charSequence){
        Toast.makeText(context,charSequence,Toast.LENGTH_SHORT).show();
    }
}
