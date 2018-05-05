package com.ksy.android.mvplayer.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.music.Music;

import java.util.ArrayList;

public class AdapterMusiclist extends RecyclerView.Adapter<MusicListViewHolder> {

    ArrayList<Music> mMusicList = new ArrayList<Music>();
    Context mContext;

    public AdapterMusiclist(ArrayList<Music> _list, Context context) {
        this.mMusicList = _list;
        this.mContext = context;
    }

    @Override
    public MusicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_list_item, parent, false);

        return new MusicListViewHolder(listItemView,mContext);
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