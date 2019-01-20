package com.youzi.framework.common.util.validate;

import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * date：2018/5/14 16:42
 * author：t.simeon
 * email：tsimeon@qq.com
 * description：通用正则验证
 */
public class PatternRule extends ValidateRule {

    private String regex;
    private String matchVal;

    protected PatternRule(String matchVal, String regex) {
        super();
        this.regex = regex;
        this.matchVal = matchVal;
    }

    protected PatternRule(TextView view,boolean trim, String regex) {
        super(view,trim);
        this.regex = regex;
    }

    public static PatternRule match(String matchVal, String regex) {
        return new PatternRule(matchVal, regex);
    }

    public static PatternRule match(TextView view,boolean trim, String regex) {
        return new PatternRule(view,trim, regex);
    }

    @Override
    public Boolean apply(Boolean aBoolean) throws Exception {
        if (regex == null){
            throw new IllegalArgumentException("validate pattern rule cannot be null");
        }

        String text = getTextByView(matchVal);

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        return m.matches();
    }
}
