package com.ksy.android.mvplayer.music;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.adapter.AdapterAlbumImglist;
import com.ksy.android.mvplayer.adapter.AdapterMusiclist;
import com.ksy.android.mvplayer.util.DLogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicPlayFragment extends Fragment implements MusicContract.View{
    private MusicContract.Presenter mPresenter;
    Context mContext;
    private ViewPager mPager;
    ArrayList<Music> mMusicItems = new ArrayList();
    AdapterAlbumImglist mAdapter;
   // @BindView(R.id.ctrAlbumImg)
   // ImageView songImageView;
    public int currentPage = 0;

    public MusicPlayFragment() {
        // 프래그먼트 생성자.
    }


    public static MusicPlayFragment newInstance() {
        return new MusicPlayFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_musicplay, container, false);
        ButterKnife.bind(this, root);
        mContext = getActivity();
       // songImageView.setVisibility(View.INVISIBLE);
        if(mPresenter !=null){
            mPresenter.init();
        }

        DisplayMetrics metrics = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mPager = root.findViewById(R.id.pager);
        mAdapter = new AdapterAlbumImglist(this.mContext,this.getChildFragmentManager(),mMusicItems);
        mPager.setAdapter(mAdapter);
        mPager.setCurrentItem(MusicActivity.currentPosition);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                MusicActivity.currentPosition = position;
            }
        });

        prepareSharedElementTransition();

        // Avoid a postponeEnterTransition on orientation change, and postpone only of first creation.
        if (savedInstanceState == null) {
            postponeEnterTransition();
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
//        mPresenter.start();
    }

    @Override
    public void setPresenter(MusicContract.Presenter presenter) {
        if(presenter !=null){
            mPresenter =presenter;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }


    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void setMusic(List<Music> _music) {
        this.mMusicItems.addAll(_music);
      //  mAdapter.notifyDataSetChanged();
    }

    @Override
    public String getFragmentTag(){
        return this.getTag();
    }

    private void prepareSharedElementTransition() {
        Transition transition =
                TransitionInflater.from(getContext())
                        .inflateTransition(R.transition.image_shared_element_transition);
        setSharedElementEnterTransition(transition);

        // A similar mapping is set at the GridFragment with a setExitSharedElementCallback.
        setEnterSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        // Locate the image view at the primary fragment (the ImageFragment that is currently
                        // visible). To locate the fragment, call instantiateItem with the selection position.
                        // At this stage, the method will simply return the fragment at the position and will
                        // not create a new one.
                        Fragment currentFragment = (Fragment) mPager.getAdapter()
                                .instantiateItem(mPager,MusicActivity.currentPosition);
                        View view = currentFragment.getView();
                        if (view == null) {
                            return;
                        }
                        Log.i("ksy","이거 맵 쉐어"+view.getTransitionName());
                        // Map the first shared element name to the child ImageView.
                        sharedElements.put(names.get(0), view.findViewById(R.id.image));
                    }
                });
    }
}
