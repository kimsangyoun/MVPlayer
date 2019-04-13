package com.ksy.android.mvplayer.music;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ksy.android.mvplayer.util.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class MusicPresenter implements MusicContract.Presenter {
    private final MusicRepository mMusicRepository;
    private final ArrayList<MusicContract.View>  mMusicView;
    private SharePreferenceUtils mSharePreferenceUtils;

    public MusicPresenter(@NonNull MusicRepository musicRepository, @NonNull ArrayList<MusicContract.View>  musicView, Context _context) {
        Log.i("ㅇㅇ","여기온다");
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
        Log.i("s","오는데!!!!?");
        mMusicRepository.getMusic(new MusicDataSource.LoadMusicCallback() {
            @Override
            public void onMusicLoaded(List<Music> _music) {
                Log.i("s","오는데!!!?"+_music.size());
                for(MusicContract.View view : mMusicView){
                  //  if(view.getFragmentTag().equals("LIST")){
                        view.setMusic(_music);
                   // }
                }
            }

            @Override
            public void onMusicLoadFailed(String msg) {
                Log.i("s","오는데1?"+msg);
            }

            @Override
            public void onMusicLoadError() {
                Log.i("s","오는데2?");
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public MusicContract.View getViewByTag(String tag) {
        return mMusicView.get(0);
    }

    @Override
    public void start() {

    }
}
