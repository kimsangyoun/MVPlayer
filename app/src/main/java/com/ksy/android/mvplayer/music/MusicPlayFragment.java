package com.ksy.android.mvplayer.music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.adapter.AdapterAlbumImglist;
import com.ksy.android.mvplayer.adapter.AdapterMusiclist;
import com.ksy.android.mvplayer.util.DLogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicPlayFragment extends Fragment implements MusicContract.View{
    private MusicContract.Presenter mPresenter;
    Context mContext;
    private ViewPager mPager;
    ArrayList<Music> mMusicItems = new ArrayList();
    AdapterAlbumImglist mAdapter;

    public int currentPage = 0;

    public MusicPlayFragment() {
        // 프래그먼트 생성자.
    }

    public static MusicPlayFragment newInstance() {
        return new MusicPlayFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_musicplay, container, false);
        ButterKnife.bind(this, root);
        mContext = getActivity();

        mPresenter.init();
        DisplayMetrics metrics = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mPager = (ViewPager) root.findViewById(R.id.pager);
        mAdapter = new AdapterAlbumImglist(mContext,mMusicItems);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(currentPage);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(MusicContract.Presenter presenter) {
        if(presenter !=null){
            mPresenter =presenter;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }


    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void setMusic(List<Music> _music) {
        DLogUtils.i("뮤직옴 " + _music.size());
        this.mMusicItems.addAll(_music);
        mAdapter.notifyDataSetChanged();
    }
}
