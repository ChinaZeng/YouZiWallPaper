package com.youzi.service.api.req;


import com.youzi.service.utils.GsonUtil;

import java.io.Serializable;

public class BaseReq implements Serializable {
    @Override
    public String toString() {
        return GsonUtil.getInstance().toJson(this);
    }
}
