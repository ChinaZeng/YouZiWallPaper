package com.youzi.framework.common.error;

import com.youzi.framework.common.ui.ILoadingHelper;

/**
 * Created by LuoHaifeng on 2017/12/26 0026.
 * Email:496349136@qq.com
 */

public class ErrorHandlerManager implements IErrorHandler {
    private IErrorHandler errorHandler;
    private static ErrorHandlerManager errorHandlerManager = new ErrorHandlerManager(new DefaultErrorHandler());

    private ErrorHandlerManager(IErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public static ErrorHandlerManager getInstance() {
        return errorHandlerManager;
    }

    @Override
    public String getErrorMessage(Throwable ex) {
        return errorHandler == null ? ex.getMessage() : errorHandler.getErrorMessage(ex);
    }

    @Override
    public void switchErrorLayout(Throwable ex, ILoadingHelper loadingHelper) {
        if (errorHandler != null) {
            errorHandler.switchErrorLayout(ex, loadingHelper);
        }
    }

    public IErrorHandler getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(IErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
}
