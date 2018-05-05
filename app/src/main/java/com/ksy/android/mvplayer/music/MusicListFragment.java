package com.ksy.android.mvplayer.music;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.adapter.AdapterMusiclist;
import com.ksy.android.mvplayer.util.DLogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicListFragment extends Fragment implements MusicContract.View{
    private MusicContract.Presenter mPresenter;
    @BindView(R.id.music_recycler_view)
    RecyclerView mMusicList;

    RecyclerView.Adapter mAdapter;
    ArrayList<Music> mMusicItems;
    Context mContext;

    public MusicListFragment() {
        // 프래그먼트 생성자.

    }

    public static MusicListFragment newInstance() {
        return new MusicListFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_musiclist, container, false);
        ButterKnife.bind(this, root);
        mContext = getActivity();
        if(mContext ==null){
            DLogUtils.i("널이래!!");
        }

        mPresenter.init();
        DisplayMetrics metrics = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mMusicItems = new ArrayList();
        mAdapter = new AdapterMusiclist(mMusicItems,mContext);
        mMusicList.setAdapter(mAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMusicList.setLayoutManager(llm);
        mMusicList.setItemAnimator(new DefaultItemAnimator());

        mPresenter.getMusicList();
        setHasOptionsMenu(false);
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
