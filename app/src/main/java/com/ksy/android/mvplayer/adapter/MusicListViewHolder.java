package com.ksy.android.mvplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.music.Music;
import com.ksy.android.mvplayer.util.DLogUtils;
import com.squareup.picasso.Picasso;


/**
 * Contains a Contact List Item
 */
public class MusicListViewHolder extends RecyclerView.ViewHolder {
    public ImageView photo;
    public TextView title;
    public TextView singer;
    public Context mContext;
    public int mCurPosition;

    public MusicListViewHolder(final View itemView,Context context) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.mlist_title);
        singer = (TextView) itemView.findViewById(R.id.mlist_singer);
        photo = (ImageView) itemView.findViewById(R.id.mlist_img01);
        mContext = context;
        mCurPosition = 0;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DLogUtils.i("클릭!");
                Intent sendIntent = new Intent("SELECT_MUSIC");
                sendIntent.putExtra("curposition", mCurPosition);
                sendIntent.putExtra("sendInteger", 123);
                sendIntent.putExtra("sendString", "Intent String");
                mContext.sendBroadcast(sendIntent);
            }
        });
    }

    public void bind(Music data,int position) {
        title.setText(data.stitle);
        singer.setText(data.sartist);
        Uri uri = Uri.parse(data.suri);

        Picasso.with(mContext).load(uri).placeholder(R.drawable.music_default).error(R.drawable.music_default).resize(175, 175).into(photo);
        //mLabel.setText(data);
        mCurPosition = position;
    }
}
