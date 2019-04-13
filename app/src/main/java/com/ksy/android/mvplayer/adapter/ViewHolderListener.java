package com.ksy.android.mvplayer.adapter;

import android.view.View;
import android.widget.ImageView;

public interface ViewHolderListener {
    void onLoadCompleted(ImageView view, int adapterPosition);
    void onItemClicked(View view, int adapterPosition);
}
