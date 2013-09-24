/* 
 * @(#)MediaPlayerUtils.java    Created on 2012-12-13
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package com.winupon.andframe.bigapple.media;

import java.io.File;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.text.TextUtils;
import android.util.Log;

import com.winupon.andframe.bigapple.media.helper.MediaConfig;

/**
 * 播放器工具类
 * 
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2012-12-13 下午12:20:12 $
 */
public class MediaPlayerModel {
    public static final String POINT = ".";

    private static final String TAG = "bigapple.MediaPlayerModel";

    private final MediaConfig mediaConfig;// 参数配置

    // 保持单例
    private MediaPlayer mediaPlayer;

    public MediaPlayerModel(MediaConfig mediaConfig) {
        this.mediaConfig = mediaConfig;
    }

    /**
     * 播放音频
     * 
     * @param fileName
     */
    public void playVoice(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return;
        }

        prepareMediaPlayer();

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
        }

        try {
            mediaPlayer.setDataSource(mediaConfig.getVoicePath() + File.separator + fileName + POINT
                    + mediaConfig.getVoiceExt());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                }
            });
        }
        catch (Exception e) {
            Log.e(TAG, "", e);
        }
    }

    /**
     * 获取MediaPlayer对象
     * 
     * @return
     */
    public MediaPlayer getMediaPlayer() {
        prepareMediaPlayer();
        return mediaPlayer;
    }

    /**
     * 设置MediaPlayer对象
     * 
     * @param mediaPlayer
     */
    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    /**
     * 释放MediaPlayer对象
     */
    public void release() {
        getMediaPlayer().release();
    }

    // 懒加载模式，第一次使用时才实例化对象
    private void prepareMediaPlayer() {
        if (null == mediaPlayer) {
            mediaPlayer = new MediaPlayer();
        }
    }

}
