package com.youzi.service.utils;

import android.content.Context;
import android.os.Environment;

/**
 * description：
 * date：2018/8/13 10:06
 * author：ZouChao
 * email：475934874@qq.com
 */
public class FileUtils {
    /**
     * 获取SD path
     *
     * @return
     */
    public static String getAppSdcardPath(Context context) {
        String path = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        if (sdCardExist) {
            path = context.getExternalCacheDir().getPath();
        }
        return path;
    }
}
