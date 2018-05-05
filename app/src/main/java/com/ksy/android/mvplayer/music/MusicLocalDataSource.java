package com.ksy.android.mvplayer.music;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import java.util.ArrayList;

/**
 * Created by AppCreater01 on 2017-04-05.
 */
public class MusicLocalDataSource implements MusicDataSource {
        private Context mContext;
        private Cursor mCursor;

    public MusicLocalDataSource(Context _context){
      //  mLoginAPIService = loginAPIService;
        mContext = _context;
        mCursor=_context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
    }


    @Override
    public void getMusic(@NonNull LoadMusicCallback callback) {
        //callback
        try{
            ArrayList<Music> mMusicList = new ArrayList<>();

            if (mCursor==null){
                mCursor=mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                        null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            }

            while (mCursor.moveToNext()){
                Music music=new Music();
                music.sid=mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media._ID));
                music.stitle=mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                music.sartist=mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
               // music.suri=mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                music.salbumid=mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                music.suri= "content://media/external/audio/albumart/" + music.salbumid;
                //music.size=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                if(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION))!=null){
                    music.songlength=mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    music.slength=ConvertToDate(mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                }

                mMusicList.add(music);
            }
            mCursor.close();
            callback.onMusicLoaded(mMusicList);
        }catch (Exception e){
            callback.onMusicLoadFailed(e.getMessage());
        }
    }

    @Override
    public void refreshMusic() {

    }
    public String ConvertToDate(int str) {
        int sum=str;
        sum/=1000;
        int minute=sum/60;
        int second=sum%60;
        if (second<10){
            return minute+":0"+second;
        }
        return minute+":"+second;
    }

    public String ConvertToDate(String str) {
        int sum=Integer.parseInt(str);
        sum/=1000;
        int minute=sum/60;
        int second=sum%60;
        if (second<10){
            return minute+":0"+second;
        }
        return minute+":"+second;
    }

}
