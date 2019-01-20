package com.youzi.framework.common.ui.dialog.i;

/**
 * Created by LuoHaifeng on 2018/2/1 0001.
 * Email:496349136@qq.com
 */

public interface IProgressDialog extends IAlertDialog{
    void setProgress(int progress);
    void setMax(int max);
}
