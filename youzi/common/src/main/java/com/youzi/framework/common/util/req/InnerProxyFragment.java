package com.youzi.framework.common.util.req;

import android.app.Fragment;
import android.content.Intent;
import android.util.SparseArray;

/**
 * Created by LuoHaifeng on 2018/1/24 0024.
 * Email:496349136@qq.com
 */

public class InnerProxyFragment extends Fragment {
    private int requestCode = 0;
    private SparseArray<OnStartActivityResultCallback> callbackSparseArray = new SparseArray<>();

    public synchronized void startActivityForResult(Intent intent, OnStartActivityResultCallback callback) {
        int realRequestCode = requestCode++;
        callbackSparseArray.put(realRequestCode, callback);
        super.startActivityForResult(intent, realRequestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        OnStartActivityResultCallback callback = callbackSparseArray.get(requestCode);
        callbackSparseArray.remove(requestCode);
        if (callback != null) {
            callback.onActivityResult(resultCode, data);
        }
    }
}
