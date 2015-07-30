package com.lianzong.logistics.app.ui.view.observableviews.fragment;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.fragment.BaseFragment;
import com.lianzong.logistics.app.ui.view.observableviews.ObservableScrollView;
import com.lianzong.logistics.app.ui.view.observableviews.ObservableScrollViewCallbacks;
import com.lianzong.logistics.app.ui.view.observableviews.ScrollState;
import com.lianzong.logistics.app.ui.view.observableviews.ScrollUtils;
import com.lianzong.logistics.app.ui.view.observableviews.Scrollable;
import com.nineoldandroids.view.ViewHelper;

// 需要修改， need fix up
public class FlexibleSpaceWithImageWithToolBarFragment extends BaseFragment implements ObservableScrollViewCallbacks{

    public static final String FRAGMENT_TAG = FlexibleSpaceWithImageWithToolBarFragment.class.getSimpleName();

    private final static FlexibleSpaceWithImageWithToolBarFragment fragment = new FlexibleSpaceWithImageWithToolBarFragment();

    public FlexibleSpaceWithImageWithToolBarFragment() {
    }

    public static FlexibleSpaceWithImageWithToolBarFragment newInstance() {
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flexible_space_with_image_with_tool_bar, container, false);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (getView() == null) {
            return;
        }

        updateFlexibleSpace(scrollY, getView());
    }

    @Override
    public final void onDownMotionEvent() {
        // We don't use this callback in this pattern.
    }

    @Override
    public final void onUpOrCancelMotionEvent(ScrollState scrollState) {
        // We don't use this callback in this pattern.
    }

    private void updateFlexibleSpace(int scrollY, View view) {

        Scrollable s = getView() == null ? null : (Scrollable)view.findViewById(R.id.list);
        s.scrollVerticallyTo(scrollY);

        ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.list);
        onScrollChanged(scrollY, scrollView);
    }

    private void onScrollChanged(int scrollY, Scrollable s) {
        Scrollable scrollable = (Scrollable) getView().findViewById(R.id.list);
        if (scrollable == null) {
            return;
        }
        if (scrollable == s) {
            translateTab(Math.min(scrollY, mFlexibleSpaceHeight - mTabHeight));
        }
    }

    private void translateTab(int scrollY) {
        View imageView = getView().findViewById(R.id.image);
        View overlayView = getView().findViewById(R.id.overlay);
        TextView titleView = (TextView) getView().findViewById(R.id.title);

        // Translate overlay and image
        float flexibleRange = mFlexibleSpaceHeight - getActionBarSize();
        int minOverlayTransitionY = mTabHeight - overlayView.getHeight();
        ViewHelper.setTranslationY(overlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(imageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(overlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY - mTabHeight) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        setPivotXToTitle(titleView);
        ViewHelper.setPivotY(titleView, 0);
        ViewHelper.setScaleX(titleView, scale);
        ViewHelper.setScaleY(titleView, scale);

        // Translate title text
        int maxTitleTranslationY = mFlexibleSpaceHeight - mTabHeight - getActionBarSize();
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(titleView, titleTranslationY);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle(View view) {
        final TextView mTitleView = (TextView) view.findViewById(R.id.title);
        Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(mTitleView, view.findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(mTitleView, 0);
        }
    }
}
