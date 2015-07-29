package com.lianzong.logistics.app.ui.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;
import com.lianzong.logistics.app.ui.view.observableviews.fragment.BaseContentDetailsFragment;


/**
 * 设置
 */
public class SettingFragment extends BaseContentDetailsFragment {

    private final static SettingFragment fragment = new SettingFragment();

    public SettingFragment() {
    }

    public static SettingFragment newInstance() {
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
        helpContainer.setText("设置一");

        TextView helpContainer2 = new TextView(mContext);
        helpContainer2.setBackgroundResource(android.R.color.white);
        helpContainer2.setPadding(mActivityPaddingLeft, mActivityPaddingTop, mActivityPaddingRight, mActivityPaddingBottom);
        helpContainer2.setText("设置二");

        scrollViewContainer.addView(helpContainer);
        scrollViewContainer.addView(helpContainer2);
    }

    @Override
    protected void setupFloatingActionButtons(FloatingActionMenu fabMenu) {
        if (null == fabMenu) return;

        fabMenu.setVisibility(View.GONE);
    }
}
