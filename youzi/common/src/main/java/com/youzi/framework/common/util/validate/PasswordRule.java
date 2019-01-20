package com.youzi.framework.common.util.validate;

import android.widget.TextView;

public class PasswordRule extends PatternRule {

    private static String REGEX = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";

    private PasswordRule(String matchVal) {
        super(matchVal, REGEX);
    }
    private PasswordRule(TextView textView,boolean trim) {
        super(textView,trim, REGEX);
    }

    public static PasswordRule match(String matchVal) {
        return new PasswordRule(matchVal);
    }

    public static PasswordRule match(TextView textView, boolean trim) {
        return new PasswordRule(textView,trim);
    }

}
