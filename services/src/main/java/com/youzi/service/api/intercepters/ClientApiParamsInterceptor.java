package com.youzi.service.api.intercepters;

import com.youzi.framework.common.net.AbsCommonParamsInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * 公告参数拦截器
 * Created by zzw on 2019\1\11
 */

public class ClientApiParamsInterceptor extends AbsCommonParamsInterceptor {
    @Override
    public Map<String, String> getHeaderMap() {
        return null;
    }

    @Override
    public Map<String, String> getQueryParamMap() {
        Map<String, String> params = new HashMap<>();
        params.put("publicParams", new ClientPublicParam().toString());
        return params;
    }

    @Override
    public Map<String, String> getFormBodyParamMap() {
        Map<String, String> params = new HashMap<>();
        params.put("publicParams", new ClientPublicParam().toString());
        return params;
    }
}
