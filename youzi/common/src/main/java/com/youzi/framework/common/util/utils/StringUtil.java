package com.youzi.framework.common.util.utils;

import android.text.TextUtils;

import java.util.Random;

/**
 * 功能描述:字符串工具
 * Created by LuoHaifeng on 2017/5/23.
 * Email:496349136@qq.com
 */

public class StringUtil {
    /**
     * 获取指定长度的随机字符串
     *
     * @param length 指定长度
     * @return 随机字符串
     */
    public static String getRandomString(int length) {
        StringBuilder buffer = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }


    /**
     * 姓名隐藏
     *
     * @param name       姓名
     * @param starNum    多少颗星星
     * @param surplusNum 剩余多少个字
     * @return
     */
    public static String parseName(String name, int starNum, int surplusNum) {
        if (TextUtils.isEmpty(name.trim()) || name.length() < 2 || name.length() < surplusNum) {
            return name;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < starNum; i++) {
                stringBuilder1.append("*");
            }
            return stringBuilder.append(stringBuilder1.toString()).append(name.substring(name.length() - surplusNum)).toString();
        }

    }

    /**
     * 隐藏指定位置字符串
     *
     * @param str    需要隐藏的字符串
     * @param start  前面多少个不隐藏
     * @param end    后面多少个不隐藏
     * @return
     */
    public static String parseStr2StarStr(String str, int start, int end) {

        if (TextUtils.isEmpty(str.trim()) || str.length() <= start + end) {
            return str;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder stringBuilder1 = new StringBuilder();
            for (int i = 0; i < str.length() - end - start; i++) {
                stringBuilder1.append("*");
            }
            return stringBuilder.append(str.substring(0, start)).append(stringBuilder1.toString()).append(str.substring(str.length() - end)).toString();
        }
    }

}
