package com.yzy.music.base.util;

import android.app.Service;
import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * 获取手机设备的相关信息
 * Created by yzy on 2016/8/8.
 */
public class DeviceUtil {
    /**
     * 获取手机设备号
     * @return
     */
    public static String getImei(Context context){
        TelephonyManager tManager  = (TelephonyManager)context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tManager.getDeviceId();
    }

    /**
     * 判断SIM卡是否可用
     * @param context
     * @return
     */
    public static boolean isSIMReady(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getApplicationContext().getSystemService(Service
                .TELEPHONY_SERVICE);

        return TelephonyManager.SIM_STATE_READY == telephonyManager.getSimState();
    }
}
