package com.youzi.framework.common.util.validate;

/**
 * Created by LuoHaifeng on 2018/5/15 0015.
 * Email:496349136@qq.com
 */
public class AndRule extends ValidateRule {
    private ValidateRule[] otherRules;

    private AndRule(ValidateRule[] otherRules) {
        this.otherRules = otherRules;
    }

    public static AndRule instance(ValidateRule... rules) {
        return new AndRule(rules);
    }

    @Override
    public Boolean apply(Boolean aBoolean) throws Exception {
        for (ValidateRule rule : otherRules) {
            boolean result = rule.apply(aBoolean);
            if (!result) {
                return false;
            }
        }

        return true;
    }
}
