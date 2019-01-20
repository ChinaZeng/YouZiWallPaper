package com.youzi.framework.common.update.down;

import android.text.TextUtils;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载任务
 */
public class DownTask implements Runnable {

    private String downUrl;
    private File downFile;
    private AbsProxyTaskListener taskListener;
    private AbsProxyDownListener downListener;
    private volatile Boolean cancel = false;

    private static final int DATA_BUFFER = 1024 * 1024;//1M

    public DownTask(String downUrl, File downFile, AbsProxyDownListener downListener, AbsProxyTaskListener taskListener) {
        this.downUrl = downUrl;
        this.downFile = downFile;
        this.taskListener = taskListener;
        this.downListener = downListener;

        //不要造成没必要的空指针
        if (downListener == null) {
            this.downListener = new AbsProxyDownListener(null) {
                @Override
                public void onFail(DownTask task, int code, String msg) {

                }

                @Override
                public void onProgress(DownTask task, long progress, long totalSize) {

                }

                @Override
                public void onSuccess(DownTask task) {

                }
            };
        }

        if (taskListener == null) {
            taskListener = new AbsProxyTaskListener(null) {
                @Override
                public void onTaskCreate(DownTask task) {

                }

                @Override
                public void onTaskStart(DownTask task) {

                }

                @Override
                public void onTaskEnd(DownTask task) {

                }
            };
        }

        taskListener.onTaskCreate(this);
    }


    public void cancel() {
        this.cancel = true;
    }

    @Override
    public void run() {
        taskListener.onTaskStart(this);

        if (TextUtils.isEmpty(downUrl)) {
            downListener.onFail(this, Code.URL_IS_EMPTY, "下载链接为空!");
            taskListener.onTaskEnd(this);
            return;
        }

        if (downFile == null) {
            downListener.onFail(this, Code.SAVE_FILE_IS_EMPTY, "保存路径为空!");
            taskListener.onTaskEnd(this);
            return;
        }

        if (cancel) {
            downListener.onFail(this, Code.DOWN_CANCEL, "下载取消!");
            taskListener.onTaskEnd(this);
            return;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            URL url = new URL(downUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");
            connection.connect();
            long fileLength = -1;
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                fileLength = connection.getContentLength();
            }
            if (fileLength <= 0) {
                if (!cancel) {
                    downListener.onFail(this, Code.URL_ERROR, "下载链接无效!");
                }
                return;
            }
            inputStream = connection.getInputStream();
            fos = new FileOutputStream(downFile);

            long downCount = 0;
            byte buf[] = new byte[DATA_BUFFER];

            while (!cancel) {
                int numRead = inputStream.read(buf);
                if (numRead <= 0 && !cancel) {
                    downListener.onSuccess(this);
                    break;
                }
                fos.write(buf, 0, numRead);
                downCount += numRead;
                if (!cancel) {
                    downListener.onProgress(this, downCount, fileLength);
                }
            }

//            if (cancel) {
//                if (taskListener != null) {
//                    taskListener.onFail(Code.DOWN_CANCEL, "下载取消!");
//                }
//                return;
//            }

        } catch (Exception e) {
            e.printStackTrace();
            if (!cancel) {
                downListener.onFail(this, Code.DOWN_ERROR, "下载失败!");
            }
        } finally {
            close(inputStream);
            close(fos);
            if (connection != null) {
                connection.disconnect();
            }
            taskListener.onTaskEnd(this);
        }
    }


    private void close(Closeable closeable) {

        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int hashCode() {
        return downUrl.hashCode() + downFile.hashCode();
    }

    public String getDownUrl() {
        return downUrl;
    }

    public File getDownFile() {
        return downFile;
    }


    public AbsProxyTaskListener getTaskListener() {
        return taskListener;
    }

    public AbsProxyDownListener getDownListener() {
        return downListener;
    }
}
