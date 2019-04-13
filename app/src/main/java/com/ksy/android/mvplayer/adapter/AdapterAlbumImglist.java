package com.ksy.android.mvplayer.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.music.ImageFragment;
import com.ksy.android.mvplayer.music.Music;

import java.util.ArrayList;

public class AdapterAlbumImglist extends FragmentStatePagerAdapter {

    private ArrayList<Music> mMusics;


    public AdapterAlbumImglist(Context context, FragmentManager fragmentManager, ArrayList<Music> objects)
    {
        super(fragmentManager);
        //this.context = context;
        this.mMusics = objects;
    }
    @Override
    public Fragment getItem(int position) {
        Log.i("ksy",position +"입니다.");
        return ImageFragment.newInstance(mMusics.get(position).suri);
    }


    @Override
    public int getCount() {
        return mMusics.size();
    }
/*

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.musicimg_slide_item, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);
        Uri uri = Uri.parse(mMusics.get(position).suri);
        Toast.makeText(context,"뭐야"+ mMusics.get(position).stitle, Toast.LENGTH_LONG).show();
        myImage.setImageURI(uri);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }
*/

   /* @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }*/
}
