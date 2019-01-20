package com.youzi.framework.common.error;

import com.youzi.framework.common.ui.ILoadingHelper;

/**
 * Created by LuoHaifeng on 2017/12/26 0026.
 * Email:496349136@qq.com
 */

public interface IErrorHandler {
    String getErrorMessage(Throwable ex);

    void switchErrorLayout(Throwable ex,ILoadingHelper loadingHelper);
}
