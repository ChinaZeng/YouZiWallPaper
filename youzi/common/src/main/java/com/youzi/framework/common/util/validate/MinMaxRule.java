package com.youzi.framework.common.util.validate;

import android.widget.TextView;

/**
 * Created by LuoHaifeng on 2018/4/24 0024.
 * Email:496349136@qq.com
 */
public class MinMaxRule extends ValidateRule {
    public static final int FLAG_EXCLUSIVE_EXCLUSIVE = 0;
    public static final int FLAG_EXCLUSIVE_INCLUSIVE = 1;
    public static final int FLAG_INCLUSIVE_EXCLUSIVE = 2;
    public static final int FLAG_INCLUSIVE_INCLUSIVE = 3;

    private double value;
    private double max;
    private double min;
    private int flag;


    public MinMaxRule(double value, double min, double max, int flag) {
        this.value = value;
        this.max = max;
        this.min = min;
        this.flag = flag;
        if (flag != FLAG_INCLUSIVE_INCLUSIVE && flag != FLAG_EXCLUSIVE_EXCLUSIVE && flag != FLAG_EXCLUSIVE_INCLUSIVE && flag != FLAG_INCLUSIVE_EXCLUSIVE) {
            throw new IllegalArgumentException("not support flag:" + flag);
        }
    }

    public MinMaxRule(TextView textView, double min, double max, int flag) {
        super(textView,true);

        this.max = max;
        this.min = min;
        this.flag = flag;
        if (flag != FLAG_INCLUSIVE_INCLUSIVE && flag != FLAG_EXCLUSIVE_EXCLUSIVE && flag != FLAG_EXCLUSIVE_INCLUSIVE && flag != FLAG_INCLUSIVE_EXCLUSIVE) {
            throw new IllegalArgumentException("not support flag:" + flag);
        }
    }

    public static MinMaxRule lt(double value, double maxValue) {
        return new MinMaxRule(value, Double.NEGATIVE_INFINITY, maxValue, MinMaxRule.FLAG_INCLUSIVE_INCLUSIVE);
    }

    public static MinMaxRule lt(TextView textView, double maxValue) {
        return new MinMaxRule(textView, Double.NEGATIVE_INFINITY, maxValue, MinMaxRule.FLAG_INCLUSIVE_INCLUSIVE);
    }

    public static MinMaxRule lt(double value, double maxValue, int flag) {
        return new MinMaxRule(value, Double.NEGATIVE_INFINITY, maxValue, flag);
    }

    public static MinMaxRule lt(TextView textView, double maxValue, int flag) {
        return new MinMaxRule(textView, Double.NEGATIVE_INFINITY, maxValue, flag);
    }

    public static MinMaxRule gt(double value, double minValue) {
        return new MinMaxRule(value, minValue, Double.POSITIVE_INFINITY, MinMaxRule.FLAG_INCLUSIVE_INCLUSIVE);
    }

    public static MinMaxRule gt(TextView textView, double minValue) {
        return new MinMaxRule(textView, minValue, Double.POSITIVE_INFINITY, MinMaxRule.FLAG_INCLUSIVE_INCLUSIVE);
    }

    public static MinMaxRule gt(double value, double minValue, int flag) {
        return new MinMaxRule(value, minValue, Double.POSITIVE_INFINITY, flag);
    }

    public static MinMaxRule gt(TextView textView, double minValue, int flag) {
        return new MinMaxRule(textView, minValue, Double.POSITIVE_INFINITY, flag);
    }

    public static MinMaxRule range(double value, double minValue, double maxValue) {
        return new MinMaxRule(value, minValue, maxValue, MinMaxRule.FLAG_INCLUSIVE_INCLUSIVE);
    }

    public static MinMaxRule range(TextView textView, double minValue, double maxValue) {
        return new MinMaxRule(textView, minValue, maxValue, MinMaxRule.FLAG_INCLUSIVE_INCLUSIVE);
    }

    public static MinMaxRule range(double value, double minValue, double maxValue, int flag) {
        return new MinMaxRule(value, minValue, maxValue, flag);
    }

    public static MinMaxRule range(TextView textView, double minValue, double maxValue, int flag) {
        return new MinMaxRule(textView, minValue, maxValue, flag);
    }

    @Override
    public Boolean apply(Boolean aBoolean) throws Exception {
        try{
            double finalValue = getDoubleValueByView(value);

            switch (flag) {
                case FLAG_INCLUSIVE_INCLUSIVE:
                    return finalValue >= min && finalValue <= max;
                case FLAG_EXCLUSIVE_EXCLUSIVE:
                    return finalValue > min && finalValue < max;
                case FLAG_INCLUSIVE_EXCLUSIVE:
                    return finalValue >= min && finalValue < max;
                case FLAG_EXCLUSIVE_INCLUSIVE:
                    return finalValue > min && finalValue <= max;
            }
            return false;
        }catch (Exception e){
            if(e instanceof NumberFormatException){ //转换为double失败
                return false;
            }
            throw e;
        }
    }
}
