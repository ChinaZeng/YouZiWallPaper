package com.youzi.player.listener;



import tv.danmaku.ijk.media.player.IMediaPlayer;

/**
 * Created by zzw on 2019/5/25.
 * 描述:
 */
public interface VideoListener  extends IMediaPlayer.OnBufferingUpdateListener
        , IMediaPlayer.OnCompletionListener
        , IMediaPlayer.OnPreparedListener
        , IMediaPlayer.OnInfoListener
        , IMediaPlayer.OnVideoSizeChangedListener
        , IMediaPlayer.OnErrorListener
        , IMediaPlayer.OnSeekCompleteListener  {

}
