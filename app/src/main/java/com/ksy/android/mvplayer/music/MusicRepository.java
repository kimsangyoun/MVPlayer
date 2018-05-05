package com.ksy.android.mvplayer.music;

import android.support.annotation.NonNull;

/**
 * Created by ksang on 2017-03-17.
 */
public class MusicRepository implements MusicDataSource {
    private static MusicRepository INSTANCE = null;
    public final MusicDataSource mMusicLocalDataSource;

    public MusicRepository(@NonNull MusicDataSource musicLocalDataSource) {
        mMusicLocalDataSource = musicLocalDataSource;
    }

    public static MusicRepository getInstance(MusicDataSource musicLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MusicRepository(musicLocalDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getMusic(@NonNull LoadMusicCallback callback) {
        //callback.
        mMusicLocalDataSource.getMusic(callback);
    }

    @Override
    public void refreshMusic() {

    }
}
