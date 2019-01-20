package com.youzi.framework.common.update;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class UpdateManager {
    private ServiceConnection serviceConnection;
    private UpdateService updateService;
    private IUpdate update;
    private Context context;

    public UpdateManager(Context context) {
        this.context = context;
    }

    public void cancel() {
        if (updateService != null) {
            updateService.cancel();
        }
    }

    public void start() {
        if (updateService != null) {
            updateService.start();
        }
    }

    public void setUpdate(IUpdate update) {
        this.update = update;
        if (updateService != null) {
            updateService.setUpdate(update);
        }
    }

    public void destory() {
        if (serviceConnection != null)
            context.unbindService(serviceConnection);

    }

    public void update(String url, String filePath) {
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(UpdateService.DOWN_URL, url);
        intent.putExtra(UpdateService.DOWN_DISK_PATH, filePath);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                updateService = ((UpdateService.UpdateBinder) service).getService();
                setUpdate(update);
                start();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        context.bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }


}
