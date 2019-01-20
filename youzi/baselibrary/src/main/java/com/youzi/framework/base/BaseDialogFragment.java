package com.youzi.framework.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.youzi.framework.common.util.autoparam.AutoParamCompat;

/**
 * Created by LuoHaifeng on 2018/5/8 0008.
 * Email:496349136@qq.com
 */
public class BaseDialogFragment extends DialogFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AutoParamCompat.injectValue(this, savedInstanceState, getArguments());
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        AutoParamCompat.saveValues(this, outState);
    }
}
