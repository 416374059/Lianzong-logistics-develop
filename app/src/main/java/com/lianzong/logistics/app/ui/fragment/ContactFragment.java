package com.lianzong.logistics.app.ui.fragment;


import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;
import com.lianzong.logistics.app.ui.view.observableviews.fragment.BaseContentDetailsFragment;


public class ContactFragment extends BaseContentDetailsFragment {

    private final static ContactFragment fragment = new ContactFragment();

    public ContactFragment() {
    }

    public static ContactFragment newInstance() {
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
        TextView contactContainer = new TextView(mContext);
        contactContainer.setBackgroundResource(android.R.color.white);
        contactContainer.setPadding(mActivityPaddingLeft, mActivityPaddingTop, mActivityPaddingRight, mActivityPaddingBottom);
        contactContainer.setText(R.string.contact);

        scrollViewContainer.addView(contactContainer);
    }

    @Override
    protected void setupFloatingActionButtons(FloatingActionMenu fabMenu) {
        if (null == fabMenu) return;

        fabMenu.setVisibility(View.GONE);
    }
}
