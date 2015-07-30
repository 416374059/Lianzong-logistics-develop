package com.lianzong.logistics.app.ui.activity.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.activity.BaseActivity;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionButton;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;
import com.lianzong.logistics.app.ui.view.observableviews.ObservableScrollView;
import com.lianzong.logistics.app.ui.view.observableviews.ObservableScrollViewCallbacks;
import com.lianzong.logistics.app.ui.view.observableviews.ScrollState;
import com.lianzong.logistics.app.ui.view.observableviews.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

public abstract class BaseToolbarWithScrollViewActivity extends BaseActivity implements ObservableScrollViewCallbacks {

    private LinearLayout mLlHeaderView;
    private View mOverlayView;
    private LinearLayout mLlScrollViewContainer;
    private FloatingActionMenu mFab;

    private ObservableScrollView mScrollView;
    private FloatingActionButton mFabUpToTop;

    private View mFabMarginTopView;
    private View mScrollViewMarginTopView;

    private boolean mFabIsShown;

    private View.OnClickListener mOnFabUpToTopClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mScrollView.smoothScrollTo(0, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_bar_with_scroll_view);

        getDimensionPixelSize();
        initToolBar();
        initViews();
    }

    private void initToolBar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getIntent().getStringExtra(KEY_TITLE));
        }
    }

    private void initViews() {
        // header view
        mLlHeaderView = (LinearLayout) findViewById(R.id.flexible_space_container);
        setupHeaderLayout(mLlHeaderView);

        // overlay view
        mOverlayView = findViewById(R.id.overlay);

        // scroll view
        mScrollView = (ObservableScrollView) findViewById(R.id.list);
        mScrollView.setScrollViewCallbacks(this);

        // scroll view margin top viw
        mScrollViewMarginTopView = findViewById(R.id.scroll_view_margin_top_view);

        // scroll container view
        mLlScrollViewContainer = (LinearLayout) findViewById(R.id.ll_scroll_view_container);
        setupScrollViewContainer(mLlScrollViewContainer);

        // floating action menu
        mFab = (FloatingActionMenu) findViewById(R.id.fb_menus_center);
        setupFloatingActionButtons(mFab);
        mFab.hideMenuButton(false);
        mFab.postDelayed(new Runnable() {
            @Override
            public void run() {
                mFab.showMenuButton(true);
            }
        }, 400);

        // floating action menu margin top view
        mFabMarginTopView = findViewById(R.id.fab_margin_top_view);
        ViewGroup.LayoutParams params = mFabMarginTopView.getLayoutParams();
        params.height = mFloatingActionMenuTopMargin;
        mFabMarginTopView.setLayoutParams(params);

        // floating action button up to top
        mFabUpToTop = (FloatingActionButton) findViewById(R.id.fb_btn_up_to_top);
        mFabUpToTop.setOnClickListener(mOnFabUpToTopClickListener);
    }

    protected void setHeaderViewShown(boolean visible, int height) {
        if (visible) {
            mFlexibleSpaceHeight = height;

            LinearLayout.LayoutParams paramsHeaderView = (LinearLayout.LayoutParams) mLlHeaderView.getLayoutParams();
            paramsHeaderView.height = height;
            mLlHeaderView.setLayoutParams(paramsHeaderView);

            LinearLayout.LayoutParams paramsOverlayView =(LinearLayout.LayoutParams) mOverlayView.getLayoutParams();
            paramsOverlayView.height = height;
            mOverlayView.setLayoutParams(paramsOverlayView);

            LinearLayout.LayoutParams paramsScrollViewMarginTopView =(LinearLayout.LayoutParams) mScrollViewMarginTopView.getLayoutParams();
            paramsScrollViewMarginTopView.height = height;
            mScrollViewMarginTopView.setLayoutParams(paramsScrollViewMarginTopView);
        }

        mLlHeaderView.setVisibility(visible ? View.VISIBLE : View.GONE);
        mOverlayView.setVisibility(visible ? View.VISIBLE : View.GONE);
        mScrollViewMarginTopView.setVisibility(visible ? View.VISIBLE : View.GONE);
        mFab.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        updateFlexibleSpace(scrollY);
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
    }

    private void updateFlexibleSpace(final int scrollY) {
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

        translateViews(Math.min(scrollY, mFlexibleSpaceHeight));
    }


    private void translateViews(int scrollY) {
        // Translate overlay
        int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
        ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        // Change alpha of overlay
        ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / mFlexibleRange, 0, 1));

        // flexible space container
        ViewHelper.setTranslationY(mLlHeaderView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));


        // Translate FAB
        float fabTranslationY = -scrollY;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            // On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
            // which causes FAB 's OnClickListener not working.
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
            lp.topMargin = (int) fabTranslationY;
            mFab.requestLayout();
        } else {
            ViewHelper.setTranslationY(mFab, fabTranslationY);
        }

        // Show/hide FAB
        if (fabTranslationY < - mFlexibleSpaceShowFabOffset) {
            hideFab();
        } else {
            showFab();
        }
    }

    private void showFab() {
        if (!mFabIsShown) {
            mFab.showMenuButton(true);
            mFabIsShown = true;
            hideFabUpToTop();
        }
    }

    private void hideFab() {
        if (mFabIsShown) {
            mFab.hideMenuButton(true);
            mFabIsShown = false;
            showFabUpToTop();
        }
    }

    private void hideFabUpToTop() {
        if (null != mFabUpToTop) {
            mFabUpToTop.setVisibility(View.GONE);
        }
    }

    private void showFabUpToTop() {
        if (null != mFabUpToTop) {
            mFabUpToTop.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 配置header view
     */
    protected abstract void setupHeaderLayout(LinearLayout headView);
    /**
     * 配置container view
     */
    protected abstract void setupScrollViewContainer(LinearLayout scrollViewContainer);
    /**
     * 配置fab button
     */
    protected abstract void setupFloatingActionButtons(FloatingActionMenu fabMenu);
}