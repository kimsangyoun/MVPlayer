package com.ksy.android.mvplayer.music;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.transition.TransitionSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.adapter.AdapterMusiclist;
import com.ksy.android.mvplayer.adapter.ViewHolderListener;
import com.ksy.android.mvplayer.util.DLogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicListFragment extends Fragment implements MusicContract.View{
    private MusicContract.Presenter mPresenter;
    @BindView(R.id.music_recycler_view)
    RecyclerView mMusicList;
    RecyclerView.Adapter mAdapter;
    ArrayList<Music> mMusicItems;
    Context mContext;
    @BindView(R.id.ctrAlbumImg)
    ImageView songImageView;

    public class ViewHolderListenerImpl implements ViewHolderListener {

        private Fragment fragment;
        private AtomicBoolean enterTransitionStarted;

        ViewHolderListenerImpl(Fragment fragment) {
            this.fragment = fragment;
            this.enterTransitionStarted = new AtomicBoolean();
        }

        @Override
        public void onLoadCompleted(ImageView view, int position) {
            // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
            // Toast.makeText(fragment.getContext(),"온로드 컴플리티드",Toast.LENGTH_LONG).show();
         /*   if (MusicActivity.currentPosition != position) {
                return;
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return;
            }*/
           // if(fragment !=null)
            // fragment.startPostponedEnterTransition();
        }

        /**
         * Handles a view click by setting the current position to the given {@code position} and
         * starting a {@link  MusicPlayFragment} which displays the image at the position.
         *
         * @param view the clicked {@link ImageView} (the shared element view will be re-mapped at the
         * GridFragment's SharedElementCallback)
         * @param position the selected view position
         */
        @Override
        public void onItemClicked(View view, int position) {
            // Update the position.
            MusicActivity.currentPosition = position;
            Uri uri = Uri.parse(mMusicItems.get(position).suri);
            songImageView.setImageURI(uri);
            songImageView.setTransitionName(mMusicItems.get(position).suri);

         /*   Uri uri = Uri.parse(mMusicItems.get(1).suri);
            songImageView.setImageURI(uri);
            songImageView.setTransitionName(mMusicItems.get(1).suri);*/
          /*  MusicActivity.currentPosition = position;

            // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
            // instead of fading out with the rest to prevent an overlapping animation of fade and move).
            ((TransitionSet) fragment.getExitTransition()).excludeTarget(view, true);

            ImageView transitioningView = view.findViewById(R.id.mlist_img01);

            // songImageView.findViewById(R.id.ctrAlbumImg)
            MusicPlayFragment simpleFragmentB = MusicPlayFragment.newInstance();
            simpleFragmentB.setPresenter(((MusicActivity)fragment.getActivity()).getMusicPresenter());
            simpleFragmentB.setMusic(((MusicListFragment)fragment).getmMusic());
           // simpleFragmentB.setMusic(mMusicList);
            fragment.getFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true) // Optimize for shared element transition
                    .addSharedElement(transitioningView, transitioningView.getTransitionName())
                    .replace(R.id.musicListFrame,simpleFragmentB, MusicPlayFragment.class
                            .getSimpleName())
                    .addToBackStack(null)
                    .commit();*/
        }

    }

    public MusicListFragment() {
        // 프래그먼트 생성자.

    }

    public static MusicListFragment newInstance() {
        return new MusicListFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollToPosition();
    }
    private void scrollToPosition() {
        mMusicList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left,
                                       int top,
                                       int right,
                                       int bottom,
                                       int oldLeft,
                                       int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                mMusicList.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = mMusicList.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(MusicActivity.currentPosition);
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            layoutManager.scrollToPosition(MusicActivity.currentPosition);
                        }
                    }, 200);

                }
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_musiclist, container, false);
        ButterKnife.bind(this, root);
        mContext = getActivity();
        mPresenter.init();

        DisplayMetrics metrics = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        mMusicItems = new ArrayList();

       if(mAdapter ==null) {
            mAdapter = new AdapterMusiclist(mMusicItems, mContext,this,new ViewHolderListenerImpl(this));
        }
        mMusicList.setAdapter(mAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(mContext);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMusicList.setLayoutManager(llm);
        mMusicList.setItemAnimator(new DefaultItemAnimator());

        mPresenter.getMusicList();

        Uri uri = Uri.parse(mMusicItems.get(MusicActivity.currentPosition ).suri);

        songImageView.setImageURI(uri);
        songImageView.setTransitionName(mMusicItems.get(MusicActivity.currentPosition ).suri);
        setHasOptionsMenu(false);

        prepareTransitions();
       // postponeEnterTransition();
        postponeEnterTransition();
        startPostponedEnterTransition();

        songImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MusicListFragment simpleFragmentA = (MusicListFragment)mPresenter.getViewByTag("LIST");

                ((TransitionSet) simpleFragmentA.getExitTransition()).excludeTarget(v, true);
               // MusicActivity.currentPosition = 1;
                ImageView transitioningView = v.findViewById(R.id.ctrAlbumImg);

                // songImageView.findViewById(R.id.ctrAlbumImg)
                MusicPlayFragment simpleFragmentB = MusicPlayFragment.newInstance();
                simpleFragmentB.setPresenter(((MusicActivity)simpleFragmentA.getActivity()).getMusicPresenter());
                simpleFragmentB.setMusic(simpleFragmentA.getmMusic());
                // simpleFragmentB.setMusic(mMusicList);
                simpleFragmentA.getFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true) // Optimize for shared element transition
                        .addSharedElement(transitioningView, transitioningView.getTransitionName())
                        .replace(R.id.musicListFrame,simpleFragmentB, MusicPlayFragment.class
                                .getSimpleName())
                        .addToBackStack(null)
                        .commit();

            }
        });

        return root;
    }

    public void choiseMusic(int position){

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
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
        DLogUtils.i("뮤직옴 " + _music.size() + this.getTag());
        this.mMusicItems.addAll(_music);
        mAdapter.notifyDataSetChanged();
    }
    public ArrayList<Music> getmMusic(){
        return this.mMusicItems;
    }
    @Override
    public String getFragmentTag(){
        return this.getTag();
    }

    private void prepareTransitions() {
        setExitTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.grid_exit_transition));

        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.
        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        // Locate the ViewHolder for the clicked position.

                       /* RecyclerView.ViewHolder selectedViewHolder = mMusicList
                                .findViewHolderForAdapterPosition(MusicActivity.currentPosition);

                        if (selectedViewHolder == null || selectedViewHolder.itemView == null) {
                            return;
                        }
*/
                      sharedElements
                                .put(names.get(0), songImageView.findViewById(R.id.ctrAlbumImg));
                    }
                });
    }
}
