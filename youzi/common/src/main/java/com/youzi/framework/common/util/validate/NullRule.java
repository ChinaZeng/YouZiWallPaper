package com.youzi.framework.common.util.validate;

/**
 * Created by LuoHaifeng on 2018/4/24 0024.
 * Email:496349136@qq.com
 */
public class NullRule extends ValidateRule{
    private Object object;
    private boolean isAssertNotNull;

    public NullRule(Object object, boolean isAssertNotNull) {
        this.object = object;
        this.isAssertNotNull = isAssertNotNull;
    }

    public static NullRule notNull(Object object) {
        return new NullRule(object, true);
    }

    public static NullRule isNull(Object object) {
        return new NullRule(object, false);
    }

    @Override
    public Boolean apply(Boolean aBoolean) throws Exception {
        return isAssertNotNull ? object != null : object == null;
    }
}
