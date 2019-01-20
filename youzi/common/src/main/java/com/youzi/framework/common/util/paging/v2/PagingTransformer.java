package com.youzi.framework.common.util.paging.v2;

import com.youzi.framework.common.error.ErrorHandlerManager;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by LuoHaifeng on 2018/5/21 0021.
 * Email:496349136@qq.com
 * 控制分页时UI方面的显示
 */
public class PagingTransformer<T> implements ObservableTransformer<T, T> {
    private Paging<T> paging;

    private PagingTransformer(Paging<T> paging) {
        this.paging = paging;
    }

    public static <T> PagingTransformer<T> create(Paging<T> paging) {
        return new PagingTransformer<>(paging);
    }

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        final boolean[] isProccessed = new boolean[]{false};
        return upstream
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        switch (paging.getLaunchMode()) {
                            case MODE_LOADING:
                                paging.getPagingView().provideLoadingHelper().showLoading();
                                break;
                            case MODE_REFRESH:
//                                paging.getPagingView().provideRefreshLayout().startRefresh();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        isProccessed[0] = true;
                        switch (paging.getLaunchMode()) {
                            case MODE_LOADING:
                                ErrorHandlerManager.getInstance().switchErrorLayout(throwable, paging.getPagingView().provideLoadingHelper());
                                break;
                            case MODE_REFRESH: {
                                paging.getPagingView().provideRefreshLayout().refreshFailed();
                                if (!paging.getDataController().haveData(paging) && paging.getPagingView().provideLoadingHelper() != null) {
                                    ErrorHandlerManager.getInstance().switchErrorLayout(throwable, paging.getPagingView().provideLoadingHelper());
                                }
                                break;
                            }
                            default:
                                break;
                        }
                    }
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (paging.getPagingView() == null) {
                            return;
                        }

                        boolean proccessed = isProccessed[0];
                        if (!proccessed) {
                            switch (paging.getLaunchMode()) {
                                case MODE_LOADING:
                                    if (paging.getDataController().haveData(paging)) {
                                        paging.getPagingView().provideLoadingHelper().restore();
                                    } else {
                                        paging.getPagingView().provideLoadingHelper().showEmpty();
                                    }
                                    break;
                                case MODE_REFRESH:
                                    if (!paging.getDataController().haveData(paging) && paging.getPagingView().provideLoadingHelper() != null) {
                                        paging.getPagingView().provideLoadingHelper().showEmpty();
                                    }

                                    paging.getPagingView().provideRefreshLayout().refreshCompleted();
                                    paging.getPagingView().provideRefreshLayout().loadMoreCompleted();
                                    break;
                                default:
                                    break;
                            }
                        }

                        if (paging.getPagingView().provideRefreshLayout() != null && !paging.getDataController().haveMoreData(paging)) {
                            paging.getPagingView().provideRefreshLayout().loadedAll();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}
