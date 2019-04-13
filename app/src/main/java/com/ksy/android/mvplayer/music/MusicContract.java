package com.ksy.android.mvplayer.music;

import android.support.annotation.NonNull;

import com.ksy.android.mvplayer.base.BasePresenter;
import com.ksy.android.mvplayer.base.BaseView;

import java.util.ArrayList;
import java.util.List;

public interface  MusicContract {
    interface View extends BaseView<Presenter> {
        //뷰에서 진행할 메소드 정의.
        void setLoadingIndicator(boolean active);
        void setMusic(List<Music> _music);
        String getFragmentTag();
        //void initView(Account account);
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);
        //void RecordPwd(String pwd);
        //void setAutoSignIn(boolean flag);
        void init();
        void getMusicList();
        View getViewByTag(String tag);
    }
}
