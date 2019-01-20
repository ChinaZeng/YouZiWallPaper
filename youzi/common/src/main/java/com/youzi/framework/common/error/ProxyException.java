package com.youzi.framework.common.error;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Created by LuoHaifeng on 2018/4/12 0012.
 * Email:496349136@qq.com
 */
class ProxyException extends Exception {
    private Exception realException;

    public ProxyException(Exception realException) {
        this.realException = realException;
    }

    @Override
    public void printStackTrace() {
        if(realException == null){
            return;
        }
        realException.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintStream s) {
        if(realException == null){
            return;
        }
        realException.printStackTrace(s);
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        if(realException == null){
            return;
        }
        realException.printStackTrace(s);
    }

    @Override
    public String getMessage() {
        if(realException == null){
            return "";
        }
        return realException.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        if(realException == null){
            return "";
        }
        return realException.getLocalizedMessage();
    }

    @Override
    public synchronized Throwable getCause() {
        if(realException == null){
            return null;
        }
        return realException.getCause();
    }

    @Override
    public synchronized Throwable initCause(Throwable cause) {
        if(realException == null){
            return null;
        }
        return realException.initCause(cause);
    }

    @Override
    public String toString() {
        if(realException == null){
            return "";
        }
        return realException.toString();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        if(realException == null){
            return null;
        }
        return realException.fillInStackTrace();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        if(realException == null){
            return null;
        }
        return realException.getStackTrace();
    }

    @Override
    public void setStackTrace(StackTraceElement[] stackTrace) {
        if(realException == null){
            return;
        }
        realException.setStackTrace(stackTrace);
    }
}
