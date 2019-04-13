package com.ksy.android.mvplayer.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.music.Music;
import com.ksy.android.mvplayer.music.MusicActivity;
import com.ksy.android.mvplayer.music.MusicPlayFragment;
import com.ksy.android.mvplayer.util.DLogUtils;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Contains a Contact List Item
 */
public class MusicListViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
    public ImageView photo;
    public TextView title;
    public TextView singer;
    public Context mContext;
    private final RequestManager requestManager;
    private final ViewHolderListener viewHolderListener;
    public int mCurPosition;

    public MusicListViewHolder(final View itemView, RequestManager requestManager, ViewHolderListener viewHolderListener, Context context) {
        super(itemView);
        this.requestManager = requestManager;
        this.viewHolderListener = viewHolderListener;
        title = (TextView) itemView.findViewById(R.id.mlist_title);
        singer = (TextView) itemView.findViewById(R.id.mlist_singer);
        photo = (ImageView) itemView.findViewById(R.id.mlist_img01);
        mContext = context;
        mCurPosition = 0;
        itemView.findViewById(R.id.mlist_item).setOnClickListener(this);
    }

    public void bind(Music data, int position) {
        title.setText(data.stitle);
        singer.setText(data.sartist);

        setImage(data.suri, position);
        photo.setTransitionName(data.suri);
        // Picasso.with(mContext).load(uri).placeholder(R.drawable.music_default).error(R.drawable.music_default).resize(175, 175).into(photo);
        //mLabel.setText(data);
        mCurPosition = position;
    }

    @Override
    public void onClick(View view) {
       // Intent sendIntent = new Intent("SELECT_MUSIC");
       // sendIntent.putExtra("curposition", mCurPosition);
       // sendIntent.putExtra("sendInteger", 123);
        //sendIntent.putExtra("sendString", "Intent String");
        //mContext.sendBroadcast(sendIntent);
        viewHolderListener.onItemClicked(view, getAdapterPosition());
    }

    void setImage(String imagepath, final int position) {
        // Load the image with Glide to prevent OOM error when the image drawables are very large.
        Uri uri = Uri.parse(imagepath);
        requestManager
                .load(uri)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<Drawable> target, boolean isFirstResource) {
                        viewHolderListener.onLoadCompleted(photo, position);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                            target, DataSource dataSource, boolean isFirstResource) {
                        viewHolderListener.onLoadCompleted(photo, position);
                        return false;
                    }
                })
                .into(photo);
    }


}