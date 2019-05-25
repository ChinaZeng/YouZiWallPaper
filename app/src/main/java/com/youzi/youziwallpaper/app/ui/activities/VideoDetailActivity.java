package com.youzi.youziwallpaper.app.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youzi.framework.base.BaseMvpActivity;
import com.youzi.framework.common.util.login.LoginManager;
import com.youzi.framework.common.util.systembar.BarCompat;
import com.youzi.player.YouZiPlayerView;
import com.youzi.player.listener.VideoListener;
import com.youzi.service.api.resp.ThemeBean;
import com.youzi.youziwallpaper.R;
import com.youzi.youziwallpaper.app.mvp.contracts.VideoDetailActivityContract;
import com.youzi.youziwallpaper.di.DaggerAppComponent;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class VideoDetailActivity extends BaseMvpActivity<VideoDetailActivityContract.Presenter>
        implements VideoDetailActivityContract.View,
        VideoListener {

    private static final String BEAN_KEY = "bean_key";
    @BindView(R.id.player_view)
    YouZiPlayerView videoPlayer;
    @BindView(R.id.iv_header)
    CircleImageView ivHeader;
    @BindView(R.id.iv_follow)
    ImageView ivFollow;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect_num)
    TextView tvCollectNum;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.ll_share)
    LinearLayout llShare;
    @BindView(R.id.rl_right)
    RelativeLayout rlRight;
    @BindView(R.id.iv_music)
    ImageView ivMusic;
    @BindView(R.id.ll_music)
    LinearLayout llMusic;
    @BindView(R.id.tv_video_des)
    TextView tvVideoDes;
    @BindView(R.id.tv_huati)
    TextView tvHuati;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.tv_down_num)
    TextView tvDownNum;
    @BindView(R.id.ll_down_count)
    LinearLayout llDownCount;


    public static void open(Context context, ThemeBean bean) {
        Intent intent = new Intent(context, VideoDetailActivity.class);
        intent.putExtra(BEAN_KEY, bean);
        context.startActivity(intent);
    }

    private ThemeBean mThemeBean;

    @Override
    protected void daggerInject() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setTitle(" ");
        //设置背景颜色
        getToolbar().getView().setBackgroundColor(Color.TRANSPARENT);
        //设置toolbar上方的padding
        setToolbarFitSystemWindowPadding(true);
        //设置内容区域的对照关系，让内容区域不要让显示在toolbar下方
        setToolbarOverFlowStyle(true);
        mThemeBean = (ThemeBean) getIntent().getSerializableExtra(BEAN_KEY);
        initData();
    }


    private void initData() {

        ThemeBean.DetailBean detailBean = mThemeBean.getDetail();
        if (detailBean == null) return;

        if (!TextUtils.isEmpty(detailBean.getVideoUrl())) {

            videoPlayer.setVideoListener(this);
//            videoPlayer.setPath("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4");
            videoPlayer.setPath(detailBean.getVideoUrl());
            try {
                videoPlayer.load();
            } catch (IOException e) {
                provideToast().showError("播放失败");
                e.printStackTrace();
            }
        }

        tvVideoDes.setText(detailBean.getContent());
        tvDownNum.setText(detailBean.getThemeDownloadNumber());
        tvCollectNum.setText(detailBean.getCollectNum());
        tvHuati.setText("@" + mThemeBean.getSpecies());
        ///TODO: 2019/5/12  头像  是否关注

    }

    @Override
    protected BarCompat.Builder onUpdateSystemBar(BarCompat.Builder builder) {
        //设置沉浸式
        builder.setStatusBarPadding(false);
        return builder;
    }

    @Override
    protected View provideLayoutView() {
        return inflate(R.layout.activity_video_detail);
    }


    @OnClick({R.id.iv_follow, R.id.ll_collect, R.id.ll_down_count, R.id.ll_share, R.id.tv_huati})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_follow:
                if (!LoginManager.getInstance().isLogin()) {
                    LoginActivity.open(this, getClass().getName());
                    return;
                }

                mPresenter.follow(mThemeBean.getHome_theme_id());

                break;
            case R.id.ll_collect:
                if (!LoginManager.getInstance().isLogin()) {
                    LoginActivity.open(this, getClass().getName());
                    return;
                }
                mPresenter.collection(mThemeBean.getHome_theme_id());
                break;
            case R.id.ll_down_count:
                break;
            case R.id.ll_share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                // 比如发送文本形式的数据内容
                // 指定发送的内容
                sendIntent.putExtra(Intent.EXTRA_TEXT, mThemeBean.getShare());
                // 指定发送内容的类型
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent,"分享"));
                break;
            case R.id.tv_huati:
                break;
        }
    }

    @Override
    public void followSuccess() {
        // TODO: 2019/5/12 ui变化
        provideToast().showSuccess("关注成功");
    }

    @Override
    public void collectionSuccess() {
        // TODO: 2019/5/12 ui变化
        provideToast().showSuccess("收藏成功");
    }


    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        videoPlayer.start();
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {

    }
}