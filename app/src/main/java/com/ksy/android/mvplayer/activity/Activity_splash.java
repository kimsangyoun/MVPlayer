package com.ksy.android.mvplayer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ksy.android.mvplayer.R;

import com.ksy.android.mvplayer.music.MusicActivity;

public class Activity_splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Thread SplashThread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(1500);
                    Intent startMain = new Intent(getApplicationContext(), MusicActivity.class);
                    startActivity(startMain);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        SplashThread.start();
    }

    @Override
    public void onBackPressed(){ //로딩페이지 종료 막기
    }
}
