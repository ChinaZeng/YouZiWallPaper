package com.youzi.framework.common.util.paging.v2;

import com.youzi.framework.common.compat.rx2.ILifeCycleProvider;
import com.youzi.framework.common.ui.ILoadingHelper;
import com.youzi.framework.common.ui.refresh.IRefreshLayout;

import io.reactivex.functions.Consumer;

/**
 * Created by LuoHaifeng on 2018/5/18 0018.
 * Email:496349136@qq.com
 */
public class Paging<T> implements PagingLauncher {
    private Config config = new Config();
    private PagingView pagingView = new SimplePagingView();
    private int currentPageIndex = config.startPageIndex - 1;
    private PagingLauncher.UseLaunchMode launchMode;
    private IRefreshLayout.OnRefreshLoadMoreListener onRefreshLoadMoreListener = new IRefreshLayout.OnRefreshLoadMoreListener() {
        @Override
        public void onRefresh() {
            request(config.startPageIndex, config.pageSize,UseLaunchMode.MODE_REFRESH);
        }

        @Override
        public void onLoadMore() {
            if(dataController != null && dataController.haveMoreData(Paging.this)) {
                request(currentPageIndex + 1, config.pageSize, UseLaunchMode.MODE_REFRESH);
            }else {
                pagingView.provideRefreshLayout().loadedAll();
            }
        }
    };

    private PagingDataController<T> dataController;
    private final PagingDataStore<T> dataStore = new PagingDataStore<>(this);
    private boolean haveDefaultData = false;
    private final PagingRequest<T> pagingRequest = PagingRequest.newRequest(this);

    public static <T> Paging<T> create() {
        return new Paging<>();
    }

    private Consumer<PagingResult<T>> callback;

    /**
     * 请在发起所有请求之前配置
     */
    public Paging<T> config(Config config) {
        assert config != null;
        this.config = config;
        onUpdateConfig();
        return this;
    }

    /**
     * 请在发起所有请求之前配置
     */
    public Paging<T> startPageIndex(int startPageIndex) {
        config.startPageIndex = startPageIndex;
        onUpdateConfig();
        return this;
    }

    /**
     * 请在发起所有请求之前配置
     */
    public Paging<T> pageSize(int pageSize) {
        config.pageSize = pageSize;
        onUpdateConfig();
        return this;
    }

    public Paging<T> controller(PagingDataController<T> controller) {
        this.dataController = controller;
        return this;
    }

    public Paging<T> controllerIfNull(PagingDataController<T> controller) {
        if (this.dataController == null) {
            this.dataController = controller;
        }
        return this;
    }

    public Paging<T> callback(Consumer<PagingResult<T>> callback) {
        this.callback = callback;
        return this;
    }

    public Paging<T> bind(PagingView pagingView) {
        assert pagingView != null;
        onBindPagingView(pagingView);
        return this;
    }

    public Paging<T> haveDefaultData(boolean haveDefaultData) {
        this.haveDefaultData = haveDefaultData;
        return this;
    }

    private void onUpdateConfig() {
        this.currentPageIndex = config.startPageIndex - 1;
    }

    private void onBindPagingView(PagingView newPagingView) {
        PagingView oldPagingView = this.pagingView;
        if (oldPagingView != null) {
            IRefreshLayout oldRefreshLayout = oldPagingView.provideRefreshLayout();
            if (oldRefreshLayout != null) {
                oldRefreshLayout.setRefreshLoadMoreListener(null);
            }
            ILoadingHelper oldLoadingHelper = oldPagingView.provideLoadingHelper();
            if (oldLoadingHelper != null) {
                oldLoadingHelper.setRetryListener(null);
            }
        }

        if (newPagingView != null) {
            IRefreshLayout newRefreshLayout = newPagingView.provideRefreshLayout();
            if (newRefreshLayout != null) {
                newRefreshLayout.setRefreshLoadMoreListener(onRefreshLoadMoreListener);
            }
            ILoadingHelper newLoadingHelper = newPagingView.provideLoadingHelper();
            if (newLoadingHelper != null) {
                newLoadingHelper.setRetryListener(new ILoadingHelper.OnRetryListener() {
                    @Override
                    public void onRetry(ILoadingHelper loadingHelper) {
                        request(config.startPageIndex, config.pageSize,UseLaunchMode.MODE_LOADING);
                    }
                });
            }
        }

        this.pagingView = newPagingView;
    }

    private boolean alreadyHaveData() {
        if (dataController == null) {
            throw new IllegalArgumentException("please config controller before request");
        }

        return dataController.haveData(this);
    }

    @Override
    public void startRefresh() {
        if (dataController == null) {
            throw new IllegalArgumentException("please config controller before request");
        }
        if (pagingView != null) {
            if (pagingView.provideRefreshLayout() != null) {
                startRefreshOnly();
            } else {
                startLoadingOnly();
            }
        } else {
            startOnlyRequest();
        }
    }

    @Override
    public void startLoading() {
        if (dataController == null) {
            throw new IllegalArgumentException("please config controller before request");
        }
        if (pagingView != null) {
            if (pagingView.provideLoadingHelper() != null && !alreadyHaveData()) {
                startLoadingOnly();
            } else {
                startRefreshOnly();
            }
        } else {
            startOnlyRequest();
        }
    }

    @Override
    public void startLoadingOnly() {
        if (dataController == null) {
            throw new IllegalArgumentException("please config controller before request");
        }
        if (pagingView != null && pagingView.provideLoadingHelper() != null) {
            request(config.startPageIndex, config.pageSize,UseLaunchMode.MODE_LOADING);
        } else {
            startOnlyRequest();
        }
    }

    @Override
    public void startRefreshOnly() {
        if (dataController == null) {
            throw new IllegalArgumentException("please config controller before request");
        }
        if (pagingView != null && pagingView.provideRefreshLayout() != null) {
            pagingView.provideRefreshLayout().startRefresh();
        } else {
            startOnlyRequest();
        }
    }

    @Override
    public void startOnlyRequest() {
        if (dataController == null) {
            throw new IllegalArgumentException("please config controller before request");
        }
        request(config.startPageIndex, config.pageSize,UseLaunchMode.MODE_NONE);
    }

    private synchronized void request(int pageIndex, int pageSize,UseLaunchMode useLaunchMode) {
        this.launchMode = useLaunchMode;
        pagingRequest.request(pageIndex, pageSize);
    }

    public Config getConfig() {
        return config;
    }

    public PagingView getPagingView() {
        return pagingView;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public IRefreshLayout.OnRefreshLoadMoreListener getOnRefreshLoadMoreListener() {
        return onRefreshLoadMoreListener;
    }

    public PagingDataController<T> getDataController() {
        return dataController;
    }

    public UseLaunchMode getLaunchMode() {
        return launchMode;
    }

    public PagingDataStore<T> getDataStore() {
        return dataStore;
    }

    public boolean haveDefaultData() {
        return haveDefaultData;
    }

    public Paging<T> setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
        return this;
    }

    public Consumer<PagingResult<T>> getCallback() {
        return callback;
    }

    public static class Config {
        private int startPageIndex = 1;
        private int pageSize = 20;

        public int getStartPageIndex() {
            return startPageIndex;
        }

        public Config setStartPageIndex(int startPageIndex) {
            this.startPageIndex = startPageIndex;
            return this;
        }

        public int getPageSize() {
            return pageSize;
        }

        public Config setPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }
    }

    public interface PagingView {
        ILoadingHelper provideLoadingHelper();

        IRefreshLayout provideRefreshLayout();

        ILifeCycleProvider provideLifeCycleProvider();
    }

    public static class SimplePagingView implements PagingView {
        private ILoadingHelper loadingHelper;
        private IRefreshLayout refreshLayout;
        private ILifeCycleProvider lifeCycleProvider;

        public SimplePagingView(ILoadingHelper loadingHelper, IRefreshLayout refreshLayout, ILifeCycleProvider lifeCycleProvider) {
            this.loadingHelper = loadingHelper;
            this.refreshLayout = refreshLayout;
            this.lifeCycleProvider = lifeCycleProvider;
        }

        public SimplePagingView() {
        }

        public ILoadingHelper getLoadingHelper() {
            return loadingHelper;
        }

        public SimplePagingView setLoadingHelper(ILoadingHelper loadingHelper) {
            this.loadingHelper = loadingHelper;
            return this;
        }

        public IRefreshLayout getRefreshLayout() {
            return refreshLayout;
        }

        public SimplePagingView setRefreshLayout(IRefreshLayout refreshLayout) {
            this.refreshLayout = refreshLayout;
            return this;
        }

        public ILifeCycleProvider getLifeCycleProvider() {
            return lifeCycleProvider;
        }

        public SimplePagingView setLifeCycleProvider(ILifeCycleProvider lifeCycleProvider) {
            this.lifeCycleProvider = lifeCycleProvider;
            return this;
        }

        @Override
        public ILoadingHelper provideLoadingHelper() {
            return loadingHelper;
        }

        @Override
        public IRefreshLayout provideRefreshLayout() {
            return refreshLayout;
        }

        @Override
        public ILifeCycleProvider provideLifeCycleProvider() {
            return lifeCycleProvider;
        }
    }
}
