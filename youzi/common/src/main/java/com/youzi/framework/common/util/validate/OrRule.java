package com.youzi.framework.common.util.validate;

/**
 * Created by LuoHaifeng on 2018/5/15 0015.
 * Email:496349136@qq.com
 */
public class OrRule extends ValidateRule {
    private ValidateRule[] otherRules;

    private OrRule(ValidateRule[] otherRules) {
        this.otherRules = otherRules;
    }

    public static OrRule instance(ValidateRule... rules) {
        return new OrRule(rules);
    }

    @Override
    public Boolean apply(Boolean aBoolean) throws Exception {
        for (ValidateRule rule : otherRules) {
            boolean result = rule.apply(aBoolean);
            if (result) {
                return true;
            }
        }

        return false;
    }
}
