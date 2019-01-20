package com.youzi.framework.common.util.validate;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by LuoHaifeng on 2018/5/15 0015.
 * Email:496349136@qq.com
 */
public class EmptyOrRule extends ValidateRule {
    private String object;
    private ValidateRule validateRule;

    private EmptyOrRule(String object, ValidateRule validateRule) {
        this.object = object;
        this.validateRule = validateRule;
    }

    private EmptyOrRule(TextView textView,boolean trim,ValidateRule validateRule){
        super(textView,trim);
        this.validateRule = validateRule;
    }

    public static EmptyOrRule instance(TextView textView, boolean trim, ValidateRule rule) {
        return new EmptyOrRule(textView,trim, rule);
    }

    public static EmptyOrRule instance(String data, ValidateRule rule) {
        return new EmptyOrRule(data, rule);
    }

    @Override
    public Boolean apply(Boolean aBoolean) throws Exception {
        String text = getTextByView(object);

        if (TextUtils.isEmpty(text)) {
            return true;
        } else {
            return validateRule.apply(aBoolean);
        }
    }
}
