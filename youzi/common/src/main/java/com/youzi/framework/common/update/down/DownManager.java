package com.youzi.framework.common.update.down;


import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 下载管理类  可下载多个任务
 */
public class DownManager {

    private static final int UPDATE_WHAT = 0x1;
    private static final int SUCCESS_WHAT = 0x2;
    private static final int FAIL_WHAT = 0x3;
    private static final int TASK_CREATE = 0x4;
    private static final int TASK_START = 0x5;
    private static final int TASK_END = 0x6;

    private HashMap<String, DownTask> downTasks = new HashMap<>();

    private DownHandler handler = new DownHandler(Looper.getMainLooper());
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(5, Integer.MAX_VALUE,
            10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(5), new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r);
        }
    });

    public void downFile(final String url, final String filePath, final DownListener listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        final File downFile = getFile2StringPath(filePath);
        if (downTasks.containsKey(url)) {
            cancel(url);
        }
        executor.execute(new DownTask(url, downFile, new HandlerDownListener(listener), new HandlerTaskListener(null)));
    }


    public void destroy() {
        destroy(false);
    }

    public void destroy(boolean cancelable) {
        if (cancelable) {
            Set<String> set = downTasks.keySet();
            for (String url : set) {
                DownTask task = downTasks.get(url);
                if (task != null) {
                    task.cancel();
                }
            }
        }
        downTasks.clear();
        executor.shutdownNow();

        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    public void cancel(String url) {
        if (TextUtils.isEmpty(url)) return;
        DownTask task = downTasks.remove(url);

        if (task != null) {
            task.cancel();
            //如果在线程池等待队列中，那么将之移除
            executor.remove(task);
        }

    }

    private class DownHandler extends android.os.Handler {

        public DownHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void dispatchMessage(Message msg) {
            DownBean bean;
            DownTask task;

            switch (msg.what) {
                case UPDATE_WHAT:
                    bean = (DownBean) msg.obj;
                    task = bean.task;
                    if (task != null && task.getDownListener().listener != null) {
                        task.getDownListener().listener.onProgress(bean.task, bean.progress, bean.total);
                    }
                    break;

                case SUCCESS_WHAT:
                    bean = (DownBean) msg.obj;
                    task = bean.task;
                    if (task.getDownListener().listener != null) {
                        task.getDownListener().listener.onSuccess(bean.task);
                    }
                    break;

                case FAIL_WHAT:
                    bean = (DownBean) msg.obj;
                    task = bean.task;
                    if (task.getDownListener().listener != null) {
                        task.getDownListener().listener.onFail(bean.task, bean.code, bean.msg);
                    }
                    break;
                case TASK_CREATE:
                    task = (DownTask) msg.obj;
                    if (task.getTaskListener().listener != null) {
                        task.getTaskListener().listener.onTaskCreate(task);
                    }
                    downTasks.put(task.getDownUrl(), task);

                    break;

                case TASK_START:
                    task = (DownTask) msg.obj;
                    if (task.getTaskListener().listener != null) {
                        task.getTaskListener().listener.onTaskStart(task);
                    }
                    break;

                case TASK_END:
                    task = (DownTask) msg.obj;
                    if (task.getTaskListener().listener != null) {
                        task.getTaskListener().listener.onTaskEnd(task);
                    }

                    downTasks.remove(task.getDownUrl());
                    break;
            }
        }
    }

    private class HandlerDownListener extends AbsProxyDownListener {

        public HandlerDownListener(DownListener listener) {
            super(listener);
        }

        @Override
        public void onFail(DownTask task, int code, String msg) {
            DownBean bean = new DownBean();
            bean.code = code;
            bean.msg = msg;
            bean.task = task;

            Message message = handler.obtainMessage();
            message.what = FAIL_WHAT;
            message.obj = bean;
            handler.sendMessage(message);
        }

        @Override
        public void onProgress(DownTask task, long progress, long totalSize) {
            DownBean bean = new DownBean();
            bean.task = task;
            bean.total = totalSize;
            bean.progress = progress;
            Message message = handler.obtainMessage();
            message.what = UPDATE_WHAT;
            message.obj = bean;
            handler.sendMessage(message);
        }

        @Override
        public void onSuccess(DownTask task) {
            DownBean bean = new DownBean();
            bean.task = task;
            Message message = handler.obtainMessage();
            message.what = SUCCESS_WHAT;
            message.obj = bean;
            handler.sendMessage(message);
        }
    }

    private class HandlerTaskListener extends AbsProxyTaskListener {

        public HandlerTaskListener(TaskListener taskListener) {
            super(taskListener);
        }

        @Override
        public void onTaskCreate(DownTask task) {
            Message message = handler.obtainMessage();
            message.what = TASK_CREATE;
            message.obj = task;
            handler.sendMessage(message);
        }

        @Override
        public void onTaskStart(DownTask task) {
            Message message = handler.obtainMessage();
            message.what = TASK_START;
            message.obj = task;
            handler.sendMessage(message);
        }

        @Override
        public void onTaskEnd(DownTask task) {
            Message message = handler.obtainMessage();
            message.what = TASK_END;
            message.obj = task;
            handler.sendMessage(message);
        }
    }


    private File getFile2StringPath(String filePath) {
        File pathFile = new File(filePath);
        if (pathFile.exists()) {
            pathFile.delete();
        }
        try {
            pathFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return pathFile;
    }
}
