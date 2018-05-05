package com.ksy.android.mvplayer.music;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ksy.android.mvplayer.util.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class MusicPresenter implements MusicContract.Presenter {
    private final MusicRepository mMusicRepository;
    private final ArrayList<MusicContract.View>  mMusicView;
    private SharePreferenceUtils mSharePreferenceUtils;

    public MusicPresenter(@NonNull MusicRepository musicRepository, @NonNull ArrayList<MusicContract.View>  musicView, Context _context) {
        mMusicRepository = musicRepository;
        mMusicView = musicView;
        mSharePreferenceUtils = new SharePreferenceUtils(_context);
        for(MusicContract.View view : mMusicView){
            view.setPresenter(this);
        }
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void init() {

    }

    @Override
    public void getMusicList() {
        mMusicRepository.getMusic(new MusicDataSource.LoadMusicCallback() {

            @Override
            public void onMusicLoaded(List<Music> _music) {
                for(MusicContract.View view : mMusicView){
                    view.setMusic(_music);
                }
            }

            @Override
            public void onMusicLoadFailed(String msg) {

            }

            @Override
            public void onMusicLoadError() {

            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void start() {

    }
}
