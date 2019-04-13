package com.ksy.android.mvplayer.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.ksy.android.mvplayer.R;
import com.ksy.android.mvplayer.music.Music;
import com.ksy.android.mvplayer.music.MusicActivity;
import com.ksy.android.mvplayer.music.MusicListFragment;
import com.ksy.android.mvplayer.music.MusicPlayFragment;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class AdapterMusiclist extends RecyclerView.Adapter<MusicListViewHolder> {

    private final ArrayList<Music> mMusicList;
    Context mContext;
    Fragment mFragment;

    private final RequestManager requestManager;
    private final ViewHolderListener viewHolderListener;


    public AdapterMusiclist(ArrayList<Music> _list, Context context, Fragment fragment) {
        this.mMusicList = _list;
        this.mContext = context;
        this.mFragment = fragment;
        this.requestManager = Glide.with(fragment);
        this.viewHolderListener = new ViewHolderListenerImpl(fragment);
    }

    @Override
    public MusicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_list_item, parent, false);

        return new MusicListViewHolder(listItemView,requestManager,viewHolderListener,mContext);
    }

    @Override
    public void onBindViewHolder(MusicListViewHolder musicListViewHolder, int pos) {
        // Extract info from cursor
        musicListViewHolder.bind(mMusicList.get(pos),pos);
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    private static class ViewHolderListenerImpl implements ViewHolderListener {

        private Fragment fragment;
        private AtomicBoolean enterTransitionStarted;

        ViewHolderListenerImpl(Fragment fragment) {
            this.fragment = fragment;
            this.enterTransitionStarted = new AtomicBoolean();
        }

        @Override
        public void onLoadCompleted(ImageView view, int position) {
            // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
            Toast.makeText(fragment.getContext(),"온로드 컴플리티드",Toast.LENGTH_LONG).show();
            if (MusicActivity.currentPosition != position) {
                return;
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return;
            }
            fragment.startPostponedEnterTransition();
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
                    .commit();
        }

    }
}