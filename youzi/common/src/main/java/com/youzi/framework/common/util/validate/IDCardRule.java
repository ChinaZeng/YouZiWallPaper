package com.youzi.framework.common.util.validate;

import android.widget.TextView;

import com.youzi.framework.common.util.utils.IDVerification;


/**
 * date：2018/5/10 16:29
 * author：t.simeon
 * email：tsimeon@qq.com
 * description：身份证验证规则
 */
public class IDCardRule extends ValidateRule {


    private String number;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;

    private IDCardRule(String number) {
        this.number = number;
    }

    private IDCardRule(TextView textView,boolean trim){
        super(textView,trim);
    }

    public static IDCardRule getInstance(String number) {
        return new IDCardRule(number);
    }

    public static IDCardRule validate(String number){
        return new IDCardRule(number);
    }

    public static IDCardRule validate(TextView textView,boolean trim){
        return new IDCardRule(textView,trim);
    }


    @Override
    public Boolean apply(Boolean aBoolean) throws Exception {
        String text = getTextByView(number);
        message = IDVerification.validate(text);

        return text != null && text.equalsIgnoreCase(message);
    }
}
