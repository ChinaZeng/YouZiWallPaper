package com.youzi.framework.common.update;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.youzi.framework.common.update.down.DownListener;
import com.youzi.framework.common.update.down.DownManager;
import com.youzi.framework.common.update.down.DownTask;

import java.io.File;

public class UpdateService extends Service implements IUpdate {

    private IUpdate update;


    public static final String DOWN_URL = "DOWN_URL";
    public static final String DOWN_DISK_PATH = "DOWN_DISK_PATH";

    private String url;
    private String savePath;
    private DownManager manager;

    private int nowPro;

    public void setUpdate(IUpdate update) {
        this.update = update;
    }

    public void cancel() {
        if (manager != null) {
            manager.cancel(url);
        }
    }

    @Override
    public void start() {

        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (manager == null)
            manager = new DownManager();

        manager.downFile(url, savePath, new DownListener() {

            @Override
            public void onFail(DownTask task, int code, String msg) {
                fail(code, msg);
            }

            @Override
            public void onProgress(DownTask task, long progress, long totalSize) {
                int pro = (int) (((float) progress / totalSize) * 100);
                if (nowPro != pro)
                    progressUpdate(pro);

                nowPro = pro;
            }

            @Override
            public void onSuccess(DownTask task) {
                success(task.getDownFile().getAbsolutePath());
            }
        });

        if (update != null) {
            update.start();
        }

    }

    @Override
    public void pause() {
        if (update != null) {
            update.pause();
        }
    }

    @Override
    public void progressUpdate(int progress) {
        if (update != null) {
            update.progressUpdate(progress);
        }
    }

    @Override
    public void success(String filePath) {
        if (update != null) {
            update.success(filePath);
        }
    }

    @Override
    public void fail(int code, String msg) {
        if (update != null) {
            update.fail(code, msg);
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (manager != null) {
            manager.destroy();
            manager = null;
        }
        return super.onUnbind(intent);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        url = intent.getStringExtra(DOWN_URL);
        savePath = intent.getStringExtra(DOWN_DISK_PATH);
        if (TextUtils.isEmpty(savePath)) {
            savePath = getCacheDir() + File.separator + "newVersion.apk";
        }
        return new UpdateBinder();
    }

    public class UpdateBinder extends Binder {
        public UpdateService getService() {
            return UpdateService.this;
        }
    }
}
