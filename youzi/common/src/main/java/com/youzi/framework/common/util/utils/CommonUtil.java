package com.youzi.framework.common.util.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

/**
 * description：通用工具类
 * date：2018/4/20 16:44
 * author：ZouChao
 * email：475934874@qq.com
 */
public class CommonUtil {

    /**
     * 跳转到拨打电话界面的方法
     *
     * @param num 电话号码
     */
    public static void skipPhone(Context context, String num) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 获取当前版本信息
     */
    public static PackageInfo getVersion(Context context) {
        PackageInfo info = null;
        PackageManager manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 验证是否安装某个app
     */
    public static boolean isAppInstalled(Context context, String packName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }
}
