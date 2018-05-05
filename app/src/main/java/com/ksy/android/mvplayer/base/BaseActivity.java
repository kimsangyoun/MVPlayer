package com.ksy.android.mvplayer.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.util.DLogUtils;
import com.ksy.android.mvplayer.util.SharePreferenceUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ksang on 2017-05-20.
 */
public abstract class BaseActivity extends AppCompatActivity {

   // @BindView(R.id.drawer_layout)  DrawerLayout mDrawerLayout;
   // @BindView(R.id.toolbar)  Toolbar toolbar;
   // @BindView(R.id.nav_view) NavigationView navigationView;

    private int mLayoutRes;
    protected Context mContext;
    private SharePreferenceUtils mSharePreferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
        mSharePreferenceUtils = new SharePreferenceUtils(getApplicationContext());
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        mLayoutRes = layoutResID;
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
       // super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onResume() {
        super.onResume();
        DLogUtils.i("Resume");
    }
    @Override
    public void onDestroy(){
        DLogUtils.i("Destroy");
        super.onDestroy();
    }



}
