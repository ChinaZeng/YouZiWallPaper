package com.youzi.framework.common.util.validate;

import android.widget.TextView;

/**
 * date：2018/5/14 16:42
 * author：t.simeon
 * email：tsimeon@qq.com
 * description：
 */
public class EmailRule extends PatternRule {
    //邮箱正则表达式
    private static String EMAILREGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    private EmailRule(String matchVal) {
        super(matchVal, EMAILREGEX);
    }

    private EmailRule(TextView textView,boolean trim){
        super(textView,trim,EMAILREGEX);
    }

    public static EmailRule match(String matchVal) {
        return new EmailRule(matchVal);
    }

    public static EmailRule match(TextView textView, boolean trim) {
        return new EmailRule(textView,trim);
    }

}
