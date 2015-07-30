package com.lianzong.logistics.app.ui.activity;

import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.activity.base.BaseToolbarWithListViewActivity;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionButton;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;

/**
 * Created by wu_shenglong on 2015/7/28.
 */
public class GoodListActivity extends BaseToolbarWithListViewActivity {

    @Override
    protected void onStart() {
        super.onStart();

//        Intent fromIntent = getIntent();
//        if (null != fromIntent) {
//            mPageState = fromIntent.getIntExtra(EXTRA_VEHICLE_PAGE_STATE, TYPE_NONE);
//        }

        setHeaderViewShown(false, 300);
    }
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
    protected void setupFloatingActionButtons(FloatingActionMenu fbContainer) {
        if (null == fbContainer) return;

        fbContainer.setVisibility(View.VISIBLE);

        FloatingActionButton fbEdit = new FloatingActionButton(this);
        fbEdit.setImageResource(R.drawable.ic_edit);
        fbEdit.setButtonSize(FloatingActionButton.SIZE_MINI);

        FloatingActionButton fbDelete = new FloatingActionButton(this);
        fbDelete.setImageResource(R.drawable.ic_delete);
        fbDelete.setButtonSize(FloatingActionButton.SIZE_MINI);

        fbContainer.addMenuButton(fbEdit);
        fbContainer.addMenuButton(fbDelete);
    }

    protected void withListViewAdapter(Adapter adapter) {

    }
}
