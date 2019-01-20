package com.youzi.framework.common.util.validate;

import android.widget.TextView;

import java.util.regex.Pattern;

public class PhoneRule extends ValidateRule {
    private static final String REG_MOBILE_PHONE_EXT_VIRTUAL = "^(13[0-9]|14[579]|15[012356789]|16[6]|17[35678]|18[0-9]|19[89])[0-9]{8}$";//不包含虚拟号段
    private static final String REG_MOBILE_PHONE = "^(13[0-9]|14[1456789]|15[012356789]|16[6]|17[01345678]|18[0-9]|19[89])[0-9]{8}$";
    private static final String REG_FIX_PHONE = "^0\\d{2,3}-?[1-9]\\d{4,7}$";

    private Mode mode;
    private String phone;

    private PhoneRule(String phone, Mode mode) {
        this.phone = phone;
        this.mode = mode;
    }

    private PhoneRule(TextView textView,boolean trim,Mode mode){
        super(textView,trim);
        this.mode = mode;
    }

    public static PhoneRule isValid(String phone, Mode mode) {
        return new PhoneRule(phone, mode);
    }

    public static PhoneRule isValid(TextView textView, boolean trim, Mode mode) {
        return new PhoneRule(textView,trim,mode);
    }

    @Override
    public Boolean apply(Boolean aBoolean) throws Exception {
        String text = getTextByView(phone);

        switch (mode) {
            case MODE_MOBILE:
                return Pattern.matches(REG_MOBILE_PHONE_EXT_VIRTUAL, text);
            case MODE_MOBILE_INCLUDE_VIRTUAL:
                return Pattern.matches(REG_MOBILE_PHONE, text);
            case MODE_TELEPHONE:
                return Pattern.matches(REG_FIX_PHONE, text);
            case MODE_MOBILE_TELEPHONE:
                return Pattern.matches(REG_MOBILE_PHONE_EXT_VIRTUAL, text) || Pattern.matches(REG_FIX_PHONE, text);
            case MODE_MOBILE_INCLUDE_VIRTUAL_TELEPHONE:
                return Pattern.matches(REG_MOBILE_PHONE, text) || Pattern.matches(REG_FIX_PHONE, text);
            default:
                return false;
        }
    }

    public enum Mode{
        MODE_MOBILE,
        MODE_MOBILE_INCLUDE_VIRTUAL,
        MODE_TELEPHONE,
        MODE_MOBILE_TELEPHONE,
        MODE_MOBILE_INCLUDE_VIRTUAL_TELEPHONE
    }
}
