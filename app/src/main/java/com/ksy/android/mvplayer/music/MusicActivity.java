package com.ksy.android.mvplayer.music;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.base.BaseActivity;
import com.ksy.android.mvplayer.util.ActivityUtils;
import com.ksy.android.mvplayer.util.DLogUtils;
import com.ksy.android.mvplayer.util.SharePreferenceUtils;

import java.util.ArrayList;

public class MusicActivity extends BaseActivity {
    public static int currentPosition;
    private static final String KEY_CURRENT_POSITION = "com.google.samples.gridtopager.key.currentPosition";

    private SharePreferenceUtils mSharePreferenceUtils;
    private MusicPresenter mMusicPresenter;
    private MusicLocalDataSource mMusicLocalDataSource;
    private MusicRepository mMusicRepository;
    private MusicListFragment mMusicListFragment;
    private MusicPlayFragment mMusicPlayFragment;
    private BroadcastReceiver mReceiver;
    private ArrayList<MusicContract.View> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(KEY_CURRENT_POSITION, 0);
            // Return here to prevent adding additional GridFragments when changing orientation.
            return;
        }


        mSharePreferenceUtils = new SharePreferenceUtils(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            //단말기 OS버전이 젤라빈 버전 보다 작을때.....처리 코드
            makeFragment();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mMusicListFragment, R.id.musicListFrame,"LIST");

        }
        else{
            //젤라빈 버전 이상일때.....처리 코드
            TedPermission.with(this)
                    .setPermissionListener(permissionlistener)
                    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                    .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .check();

        }

        IntentFilter filter = new IntentFilter();
        filter.addAction("SELECT_MUSIC");
       // filter.addAction(Intent.ACTION_POWER_DISCONNECTED);


        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = (intent.getAction());
                int curr_pos = intent.getIntExtra("curposition",0);
                DLogUtils.i(message+curr_pos);
                if(message.equals("SELECT_MUSIC")){
                   // if (mMusicPlayFragment == null) {
                    //    mMusicPlayFragment = MusicPlayFragment.newInstance();
                    //}
                    //mMusicPlayFragment.currentPage = curr_pos;

                    //ActivityUtils.changeFragementToActivity( //엑티비티 내 프레그먼트 교체.
                            //getSupportFragmentManager(), mMusicPlayFragment, R.id.musicListFrame,MusicPlayFragment.class.getSimpleName());
                }
               // Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        };

        registerReceiver(mReceiver, filter);

    }
    public void makeFragment(){
        //mMusicPlayFragment = MusicPlayFragment.newInstance();
        mMusicListFragment = MusicListFragment.newInstance();

        mFragmentList.add(mMusicListFragment);
        //mFragmentList.add(mMusicPlayFragment);
        mMusicLocalDataSource = new MusicLocalDataSource(mContext);
        mMusicRepository = new MusicRepository(mMusicLocalDataSource);
        mMusicPresenter = new MusicPresenter(mMusicRepository,mFragmentList,mContext);
    }

    @Override
    public void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onDestroy();

    }

    //권한 체크
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(MusicActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            //if (mMusicListFragment == null) {
              //  mMusicListFragment = MusicListFragment.newInstance();
            //}
            makeFragment();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mMusicListFragment, R.id.musicListFrame,"LIST");

        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MusicActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_POSITION, currentPosition);
    }

    public MusicPresenter getMusicPresenter() {
        return mMusicPresenter;
    }
}
