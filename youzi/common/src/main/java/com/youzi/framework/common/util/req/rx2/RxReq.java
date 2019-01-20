package com.youzi.framework.common.util.req.rx2;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.youzi.framework.common.util.req.ActivityResult;
import com.youzi.framework.common.util.req.OnStartActivityResultCallback;
import com.youzi.framework.common.util.req.ReqCompat;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by LuoHaifeng on 2018/5/17 0017.
 * Email:496349136@qq.com
 */
public class RxReq {
    public static Observable<ActivityResult> startActivityForResult(final Activity activity, final Intent intent){
        return Observable.create(new ObservableOnSubscribe<ActivityResult>() {
            @Override
            public void subscribe(final ObservableEmitter<ActivityResult> emitter) throws Exception {
                ReqCompat.startActivityForResult(activity, intent, new OnStartActivityResultCallback() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        emitter.onNext(new ActivityResult().setResultCode(resultCode).setData(data));
                        emitter.onComplete();
                    }
                });
            }
        });
    }

    public static Observable<ActivityResult> startActivityForResult(final Fragment fragment, final Intent intent){
        return Observable.create(new ObservableOnSubscribe<ActivityResult>() {
            @Override
            public void subscribe(final ObservableEmitter<ActivityResult> emitter) throws Exception {
                ReqCompat.startActivityForResult(fragment, intent, new OnStartActivityResultCallback() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        emitter.onNext(new ActivityResult().setResultCode(resultCode).setData(data));
                        emitter.onComplete();
                    }
                });
            }
        });
    }
}
