package com.youzi.framework.common.util.req;

import android.content.Intent;

/**
 * Created by LuoHaifeng on 2018/5/17 0017.
 * Email:496349136@qq.com
 */
public class ActivityResult {
    private int resultCode;
    private Intent data;

    public int getResultCode() {
        return resultCode;
    }

    public ActivityResult setResultCode(int resultCode) {
        this.resultCode = resultCode;
        return this;
    }

    public Intent getData() {
        return data;
    }

    public ActivityResult setData(Intent data) {
        this.data = data;
        return this;
    }
}
