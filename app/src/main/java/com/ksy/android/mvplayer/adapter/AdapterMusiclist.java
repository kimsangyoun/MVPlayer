package com.ksy.android.mvplayer.adapter;


import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.music.Music;
import com.ksy.android.mvplayer.music.MusicActivity;
import com.ksy.android.mvplayer.music.MusicListFragment;
import com.ksy.android.mvplayer.music.MusicPlayFragment;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdapterMusiclist extends RecyclerView.Adapter<MusicListViewHolder> {

    private final ArrayList<Music> mMusicList;
    Context mContext;
    Fragment mFragment;

    private final RequestManager requestManager;
    private final ViewHolderListener viewHolderListener;


    public AdapterMusiclist(ArrayList<Music> _list, Context context, Fragment fragment, MusicListFragment.ViewHolderListenerImpl listener) {
        this.mMusicList = _list;
        this.mContext = context;
        this.mFragment = fragment;
        this.requestManager = Glide.with(fragment);
        this.viewHolderListener = listener;
    }

    @Override
    public MusicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_list_item, parent, false);

        return new MusicListViewHolder(listItemView,requestManager,viewHolderListener,mContext);
    }

    @Override
    public void onBindViewHolder(MusicListViewHolder musicListViewHolder, int pos) {
        // Extract info from cursor
        musicListViewHolder.bind(mMusicList.get(pos),pos);
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }


}