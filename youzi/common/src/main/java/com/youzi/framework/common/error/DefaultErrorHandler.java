package com.youzi.framework.common.error;

import com.youzi.framework.common.ui.ILoadingHelper;
import com.youzi.framework.common.util.utils.NetworkUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * Created by LuoHaifeng on 2018/2/5 0005.
 * Email:496349136@qq.com
 */

public class DefaultErrorHandler implements IErrorHandler {
    @Override
    public String getErrorMessage(Throwable ex) {
        if (ex != null) {
            ex.printStackTrace();
        }

        if (!NetworkUtils.isConnected()) {
            return "当前设备未连接到网络,请检查网络连接";
        } else if (ex instanceof SocketTimeoutException) {
            return "服务器连接超时,请重试";
        } else if (ex instanceof UnknownHostException) {
            return "无法连接至服务器,请稍后再试";
        } else if (ex instanceof ProcessResultException) {
            return "逻辑处理异常";
        } else if (ex instanceof ConnectException) {
            return "网络连接失败,请检查网络";
        } else if (ex instanceof CodeException || ex instanceof MessageException || ex instanceof ValidateException || ex instanceof PermissionException) {
            return ex.getMessage();
        } else if (ex instanceof HttpException) {
            HttpException exception = (HttpException) ex;
            int code = exception.code();
            return "服务器连接出现异常:" + code;
        }

        return "未知异常";
    }

    @Override
    public void switchErrorLayout(Throwable ex, ILoadingHelper loadingHelper) {
        if (ex != null) {
            ex.printStackTrace();
        }
        if (loadingHelper == null) {
            return;
        }
        if (!NetworkUtils.isConnected()) {
            loadingHelper.showNoNetwork();
        } else if (ex instanceof EmptyDataException) {
            loadingHelper.showEmpty();
        } else {
            loadingHelper.showError();
        }
    }
}
