package com.youzi.framework.common.util.utils;

import android.content.Context;

import com.youzi.framework.common.Config;

import java.io.File;

/**
 * 功能描述:缓存相关的工具
 * Created by LuoHaifeng on 2017/5/23.
 * Email:496349136@qq.com
 */

public class CacheUtil {
    /**
     * 获取缓存目录
     *
     * @return 缓存目录
     */
    public static File getCacheDir() {
        Context context = Config.getAppContext();
        File file = context.getExternalCacheDir();
        if (file != null && file.exists()) {
            File cacheDir = new File(file, "cache");
            if (!cacheDir.exists()) {
                boolean flag = cacheDir.mkdirs();
                if (!flag) {
                    return file;
                }
            } else {
                return cacheDir;
            }
        }

        file = context.getCacheDir();
        if (file != null && file.exists()) {
            File cacheDir = new File(file, "cache");
            if (!cacheDir.exists()) {
                boolean flag = cacheDir.mkdirs();
                if (!flag) {
                    return file;
                }
            } else {
                return cacheDir;
            }
        }
        return file;
    }

    /***
     * 清除缓存目录
     */
    public static void clearCache() {
        File cacheDir = getCacheDir();
        clear(cacheDir);
    }

    private static void clear(File file) {
        if (file == null) {
            return;
        }

        if(!file.exists()){
            return;
        }

        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children == null) {
                return;
            }

            for (File child : children) {
                clear(child);
            }
        } else {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }
}
