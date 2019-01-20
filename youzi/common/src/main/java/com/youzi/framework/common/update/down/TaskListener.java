package com.youzi.framework.common.update.down;

public interface TaskListener  {

    void onTaskCreate(DownTask task);

    void onTaskStart(DownTask task);

    void onTaskEnd(DownTask task);

    TaskListener EMPTY = new TaskListener() {
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
