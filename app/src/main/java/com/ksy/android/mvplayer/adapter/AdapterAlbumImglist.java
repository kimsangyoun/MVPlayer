package com.ksy.android.mvplayer.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.music.Music;

import java.util.ArrayList;

public class AdapterAlbumImglist extends PagerAdapter {

    private ArrayList<Music> mMusics;
    private LayoutInflater inflater;
    private Context context;

    public AdapterAlbumImglist(Context context,ArrayList<Music> _list) {
        this.context = context;
        this.mMusics=_list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mMusics.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.musicimg_slide_item, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        Uri uri = Uri.parse(mMusics.get(position).suri);
        myImage.setImageURI(uri);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
