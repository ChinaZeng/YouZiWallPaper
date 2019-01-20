package com.youzi.framework.common.util.validate;

import android.text.TextUtils;
import android.widget.TextView;
/**
 * Created by LuoHaifeng on 2018/4/24 0024.
 * Email:496349136@qq.com
 */
public class EmptyRule extends ValidateRule {
    private String object;
    private boolean isAssertNotEmpty;

    public EmptyRule(String object, boolean isAssertNotEmpty) {
        this.object = object;
        this.isAssertNotEmpty = isAssertNotEmpty;
    }

    public EmptyRule(TextView textView,boolean trim, boolean isAssertNotEmpty) {
        super(textView,trim);
        this.isAssertNotEmpty = isAssertNotEmpty;
    }

    public static EmptyRule isEmpty(String data) {
        return new EmptyRule(data, false);
    }

    public static EmptyRule notEmpty(String object) {
        return new EmptyRule(object, true);
    }

    public static EmptyRule emptyable(TextView textView, boolean trim, boolean isAssertNotEmpty) {
        return new EmptyRule(textView,trim, isAssertNotEmpty);
    }

    public static EmptyRule isEmpty(TextView textView, boolean trim) {
        return emptyable(textView, trim, false);
    }

    public static EmptyRule notEmpty(TextView textView, boolean trim) {
        return emptyable(textView, trim, true);
    }

    @Override
    public Boolean apply(Boolean aBoolean) throws Exception {
        String text = getTextByView(object);
        return isAssertNotEmpty != TextUtils.isEmpty(text);
    }
}
