package com.ksy.android.mvplayer.music;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by ksang on 2017-03-17.
 */
public interface MusicDataSource {

    interface LoadMusicCallback {
        void onMusicLoaded(List<Music> _music);
        void onMusicLoadFailed(String msg);
        void onMusicLoadError();
        void onDataNotAvailable();
    }

    void getMusic(@NonNull LoadMusicCallback callback);

    void refreshMusic();
}
