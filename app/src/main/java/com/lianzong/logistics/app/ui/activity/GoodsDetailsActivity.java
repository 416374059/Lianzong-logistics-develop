package com.lianzong.logistics.app.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionButton;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;

/**
 * Created by wu_shenglong on 2015/7/28.
 */
public class GoodsDetailsActivity extends ToolbarWithScrollViewBaseActivity{
    @Override
    protected void setupHeaderLayout(LinearLayout headView) {
        if (null == headView) return;

        ImageView helpHeader = new ImageView(this);
        helpHeader.setImageResource(R.drawable.example);
        helpHeader.setScaleType(ImageView.ScaleType.FIT_XY);

        headView.addView(helpHeader);
    }

    @Override
    protected void setupScrollViewContainer(LinearLayout scrollViewContainer) {
        if (null == scrollViewContainer) return;

        TextView helpContainer = new TextView(this);
        helpContainer.setBackgroundResource(android.R.color.white);
        helpContainer.setPadding(mActivityPaddingLeft, mActivityPaddingTop, mActivityPaddingRight, mActivityPaddingBottom);
        helpContainer.setText(R.string.help);

        scrollViewContainer.addView(helpContainer);
    }

    @Override
    protected void setupFloatingActionButtons(FloatingActionMenu fabMenu) {
        if (null == fabMenu) return;

        fabMenu.setVisibility(View.VISIBLE);

        FloatingActionButton fbEdit = new FloatingActionButton(this);
        fbEdit.setImageResource(R.drawable.ic_edit);
        fbEdit.setButtonSize(FloatingActionButton.SIZE_MINI);

        FloatingActionButton fbDelelte = new FloatingActionButton(this);
        fbDelelte.setImageResource(R.drawable.ic_delete);
        fbDelelte.setButtonSize(FloatingActionButton.SIZE_MINI);

        fabMenu.addMenuButton(fbEdit);
        fabMenu.addMenuButton(fbDelelte);
    }
}
