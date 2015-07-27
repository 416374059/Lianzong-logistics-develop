package com.lianzong.logistics.app.ui.view.observableviews.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.fragment.BaseFragment;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;
import com.lianzong.logistics.app.ui.view.observableviews.FlexibleSpaceContainerLayout;
import com.lianzong.logistics.app.ui.view.observableviews.ObservableScrollView;
import com.lianzong.logistics.app.ui.view.observableviews.ObservableScrollViewCallbacks;
import com.lianzong.logistics.app.ui.view.observableviews.ScrollState;
import com.lianzong.logistics.app.ui.view.observableviews.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;


public class FlexibleSpaceWithImageFragment extends BaseFragment implements ObservableScrollViewCallbacks{

    protected static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private ObservableScrollView mScrollView;
    private FloatingActionMenu mFab;
    private FlexibleSpaceContainerLayout mFlexibleSpaceContainerView;
    private View mOverlayView;

    private boolean mFabIsShown;

    public FlexibleSpaceWithImageFragment() {
        // Required empty public constructor
    }

    public static FlexibleSpaceWithImageFragment newInstance(String title) {
        FlexibleSpaceWithImageFragment f = new FlexibleSpaceWithImageFragment();

        Bundle args = new Bundle();

        args.putString(KEY_TITLE, title);
        f.setArguments(args);


        Log.i("wsl", "FlexibleSpaceWithImageFragment created.");
        return (f);
    }

    public void setScrollViewScrollBarShown(boolean shown) {
        if (null != mScrollView) {
            mScrollView.setOverScrollMode(shown ? View.OVER_SCROLL_IF_CONTENT_SCROLLS : View.OVER_SCROLL_NEVER);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flexible_space_with_image, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
            @Override
            public void run() {
//                mScrollView.scrollTo(0, mFlexibleSpaceHeight - mActionBarSize);

                // If you'd like to start from scrollY == 0, don't write like this:
                //mScrollView.scrollTo(0, 0);
                // The initial scrollY is 0, so it won't invoke onScrollChanged().
                // To do this, use the following:
                //onScrollChanged(0, false, false);

                // You can also achieve it with the following codes.
                // This causes scroll change from 1 to 0.
//                mScrollView.scrollTo(0, 1);
//                mScrollView.scrollTo(0, 0);
            }
        });

    }

    private void initViews(View view) {
        mScrollView = (ObservableScrollView) view.findViewById(R.id.scroll);
        setScrollViewScrollBarShown(false);
        mScrollView.setScrollViewCallbacks(this);

        mFab = (FloatingActionMenu) view.findViewById(R.id.fb_menus_down);
        mFab.hideMenuButton(false);
        mFab.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFab.showMenuButton(true);
            }
        }, 400);

        mFlexibleSpaceContainerView = (FlexibleSpaceContainerLayout) view.findViewById(R.id.flexible_space_container);
        mOverlayView = view.findViewById(R.id.overlay);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (getView() == null) {
            return;
        }

        updateFlexibleSpace(scrollY);
    }

    @Override
    public final void onDownMotionEvent() {
        // We don't use this callback in this pattern.
    }

    @Override
    public final void onUpOrCancelMotionEvent(ScrollState scrollState) {
        // We don't use this callback in this pattern.
    }

    private void updateFlexibleSpace(int scrollY) {
        if (null == mScrollView) {
            return;
        }

        mScrollView.scrollVerticallyTo(scrollY);
        doScrollChanged(scrollY);
    }

    private void doScrollChanged(int scrollY) {
        if (null == mScrollView) {
            return;
        }

        translateTab(Math.min(scrollY, mFlexibleSpaceHeight - mTabHeight));
    }

    private void translateTab(int scrollY) {
        // Translate overlay
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / mFlexibleRange, 0, 1));

        // flexible space container
        ViewHelper.setTranslationY(mFlexibleSpaceContainerView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

//        // Scale title text
//        float scale = 1 + ScrollUtils.getFloat((mFlexibleRange - scrollY) / mFlexibleRange, 0, MAX_TEXT_SCALE_DELTA);
//        ViewHelper.setPivotX(mTitleView, 0);
//        ViewHelper.setPivotY(mTitleView, 0);
//        ViewHelper.setScaleX(mTitleView, scale);
//        ViewHelper.setScaleY(mTitleView, scale);

//        // Translate title text
//        int maxTitleTranslationY = (int) (mFlexibleSpaceHeight - mTitleView.getHeight() * scale);
//        int titleTranslationY = maxTitleTranslationY - scrollY;
//        ViewHelper.setTranslationY(mTitleView, titleTranslationY);

        // Translate FAB
        final int maxFabTranslationY = mFlexibleSpaceShowFabOffset;
        Log.i("wsl", "maxFabTranslationY = " + maxFabTranslationY );
//        float fabTranslationY = ScrollUtils.getFloat(
//                -scrollY + mFlexibleSpaceHeight - mFab.getHeight() / 2,
//                mActionBarSize - mFab.getHeight() / 2,
//                maxFabTranslationY);
        float fabTranslationY = -scrollY;
        Log.i("wsl", "fabTranslationY = " + fabTranslationY );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
            lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mFab.getWidth();
            lp.topMargin = (int) fabTranslationY;
            mFab.requestLayout();
        } else {
//            ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
            ViewHelper.setTranslationY(mFab, fabTranslationY);
        }

        // Show/hide FAB
        if (fabTranslationY < - mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
    }

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
//    private void setPivotXToTitle(View view) {
//        Configuration config = getResources().getConfiguration();
//        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
//                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
//            ViewHelper.setPivotX(mTitleView, view.findViewById(android.R.id.content).getWidth());
//        } else {
//            ViewHelper.setPivotX(mTitleView, 0);
//        }
//    }

    private void showFab() {
        if (!mFabIsShown) {
//            ViewPropertyAnimator.animate(mFab).cancel();
//            ViewPropertyAnimator.animate(mFab).scaleY(1).setDuration(200).start();
            mFab.showMenuButton(true);
            mFabIsShown = true;
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
//            ViewPropertyAnimator.animate(mFab).cancel();
//            ViewPropertyAnimator.animate(mFab).scaleY(0).setDuration(200).start();
            mFab.hideMenuButton(true);
            mFabIsShown = false;
        }
    }
}
