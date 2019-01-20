package com.youzi.framework.common.util.paging.v2;

import com.youzi.framework.common.compat.rx2.SimpleObserver;
import com.youzi.framework.common.util.transformers.rx2.ErrorHandlerTransformer;
import com.youzi.framework.common.util.transformers.rx2.SchedulersIoMainTransformer;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by LuoHaifeng on 2018/5/21 0021.
 * Email:496349136@qq.com
 */
public class PagingRequest<T> {
    private Paging<T> paging;
    private Disposable currentRequest;

    private PagingRequest(Paging<T> paging) {
        this.paging = paging;
    }

    public static <T> PagingRequest<T> newRequest(Paging<T> paging) {
        return new PagingRequest<>(paging);
    }

    public synchronized void request(final int pageIndex, final int pageSize) {
        if (currentRequest != null && !currentRequest.isDisposed()) {
            currentRequest.dispose();
        }

        paging.getDataController().provideDataSource(pageIndex, pageSize)
                .compose(SchedulersIoMainTransformer.<T>create())
                .compose(PagingTransformer.create(paging))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        currentRequest = disposable;
                    }
                })
                .doOnNext(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        if (pageIndex == paging.getConfig().getStartPageIndex()) {
                            paging.getDataStore().clear();
                        }
                        paging.getDataController().computeHaveMoreData(paging, t);
                        boolean isValidPage = paging.getDataController().isValidPage(paging, t);
                        if (isValidPage) {
                            paging.setCurrentPageIndex(pageIndex);
                            paging.getDataStore().onPagingResult(t); //缓存到数据列表
                        }

                        if (paging.getCallback() != null) {
                            paging.getCallback().accept(new PagingResult<T>().setPageIndex(pageIndex).setPageSize(pageSize).setResult(t).setValidateData(isValidPage));
                        }
                    }
                })
                .compose(ErrorHandlerTransformer.<T>create())
                .subscribe(new SimpleObserver<>());
    }
}
