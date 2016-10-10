package com.yzy.music.base.core;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Acivity管理器
 * Created by yzy on 2016/10/9.
 */
public class AllActivity {
    private static List<Activity> allActivity = new ArrayList<Activity>();

    /**
     * 添加Activity
     * @param activity
     */
    public static void addActivity(Activity activity) {
        allActivity.add(activity);
    }

    /**
     * 退出软件
     */
    public static void exitApp() {
        if (allActivity.size() > 0) {
            for (Activity activity : allActivity) {
                activity.finish();
            }
        }
        System.exit(0);
    }

    /**
     * 移除Activity
     * @param activity
     */
    public static void removeActiviy(Activity activity){
        allActivity.remove(activity);
    }
}
