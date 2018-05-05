package com.ksy.android.mvplayer.music;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
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
    private SharePreferenceUtils mSharePreferenceUtils;
    private MusicPresenter mMusicPresenter;
    private MusicLocalDataSource mMusicLocalDataSource;
    private MusicRepository mMusicRepository;
    private MusicListFragment mMusicListFragment;
    private MusicPlayFragment mMusicPlayFragment;
    private BroadcastReceiver receiver;
    private ArrayList<MusicContract.View> mFragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharePreferenceUtils = new SharePreferenceUtils(this);

        //6.0 이상 권한 요청.
        mMusicListFragment =(MusicListFragment)
                getSupportFragmentManager().findFragmentById(R.id.musicListFrame);

        makeFragment();

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
        IntentFilter filter = new IntentFilter();

        filter.addAction("SELECT_MUSIC");
       // filter.addAction(Intent.ACTION_POWER_DISCONNECTED);


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String message = (intent.getAction());
                int curposition = intent.getIntExtra("curposition",0);
                DLogUtils.i(message);
                if(message.equals("SELECT_MUSIC")){
                    if (mMusicPlayFragment == null) {
                        mMusicPlayFragment = MusicPlayFragment.newInstance();
                    }
                    mMusicPlayFragment.currentPage = curposition;

                    ActivityUtils.changeFragementToActivity(
                            getSupportFragmentManager(), mMusicPlayFragment, R.id.musicListFrame);
                }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        };

        registerReceiver(receiver, filter);

    }
    public void makeFragment(){
        mMusicPlayFragment = MusicPlayFragment.newInstance();
        mMusicListFragment = MusicListFragment.newInstance();

        mFragmentList.add(mMusicListFragment);
        mFragmentList.add(mMusicPlayFragment);
        mMusicLocalDataSource = new MusicLocalDataSource(mContext);
        mMusicRepository = new MusicRepository(mMusicLocalDataSource);
        mMusicPresenter = new MusicPresenter(mMusicRepository,mFragmentList,mContext);
    }
    @Override
    public void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver = null;
        }
        super.onDestroy();

    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(MusicActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            if (mMusicListFragment == null) {
                mMusicListFragment = MusicListFragment.newInstance();
            }
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mMusicListFragment, R.id.musicListFrame);

        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MusicActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }


    };

}
