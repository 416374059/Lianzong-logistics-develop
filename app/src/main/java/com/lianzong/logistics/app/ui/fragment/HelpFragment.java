package com.lianzong.logistics.app.ui.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;
import com.lianzong.logistics.app.ui.view.observableviews.fragment.BaseContentDetailsFragment;

/**
 * 帮助
 */
public class HelpFragment extends BaseContentDetailsFragment {

    @Override
    public void onStart() {
        super.onStart();

        setHeaderViewShown(false, 300);
    }

    public static final String FRAGMENT_TAG = HelpFragment.class.getSimpleName();

    private final static HelpFragment fragment = new HelpFragment();

    public HelpFragment() {
    }

    public static HelpFragment newInstance() {
        return fragment;
    }

    @Override
    protected void setupHeaderLayout(LinearLayout headView) {
        if (null == headView) return;

        ImageView helpHeader = new ImageView(mContext);
        helpHeader.setImageResource(R.drawable.example);
        helpHeader.setScaleType(ImageView.ScaleType.FIT_XY);

        headView.addView(helpHeader);
    }

    @Override
    protected void setupScrollViewContainer(LinearLayout scrollViewContainer) {
        if (null == scrollViewContainer) return;

        TextView helpContainer = new TextView(mContext);
        helpContainer.setBackgroundResource(android.R.color.white);
        helpContainer.setPadding(mActivityPaddingLeft, mActivityPaddingTop, mActivityPaddingRight, mActivityPaddingBottom);
        helpContainer.setText(R.string.help);

        scrollViewContainer.addView(helpContainer);
    }

    @Override
    protected void setupFloatingActionButtons(FloatingActionMenu fabMenu) {
        if (null == fabMenu) return;

        fabMenu.setVisibility(View.GONE);
    }
}
