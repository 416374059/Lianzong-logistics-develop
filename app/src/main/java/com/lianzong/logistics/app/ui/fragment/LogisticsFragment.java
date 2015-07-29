package com.lianzong.logistics.app.ui.fragment;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.activity.BaseActivity;
import com.lianzong.logistics.app.ui.activity.GoodListActivity;
import com.lianzong.logistics.app.ui.activity.VehicleListActivity;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;
import com.lianzong.logistics.app.ui.view.observableviews.fragment.BaseContentDetailsFragment;


public class LogisticsFragment extends BaseContentDetailsFragment {

    private final static LogisticsFragment fragment = new LogisticsFragment();

    public LogisticsFragment() {
    }

    public static LogisticsFragment newInstance() {
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
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View homePage = inflater.inflate(R.layout.layout_logistics_home_page, null);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()) {
                    case R.id.lr_find_goods:
                        Intent intentFindGoods = new Intent();
                        intentFindGoods.setClass(mContext, GoodListActivity.class);
                        intentFindGoods.putExtra(BaseActivity.KEY_TITLE, getResources().getText(R.string.activity_title_find_goods));
                        intentFindGoods.putExtra(BaseActivity.KEY_TYPE, BaseActivity.LogisticsFragmentType.TYPE_ALL);
                        mContext.startActivity(intentFindGoods);
                        break;
                    case R.id.lr_find_vehicles:
                        Intent intentFindVehicles = new Intent();
                        intentFindVehicles.setClass(mContext, VehicleListActivity.class);
                        intentFindVehicles.putExtra(BaseActivity.KEY_TITLE, getResources().getText(R.string.activity_title_find_vehicles));
                        intentFindVehicles.putExtra(BaseActivity.KEY_TYPE, BaseActivity.LogisticsFragmentType.TYPE_ALL);
                        mContext.startActivity(intentFindVehicles);
                        break;
                    case R.id.lr_my_goods:
                        Intent intentMyGoods = new Intent();
                        intentMyGoods.setClass(mContext, GoodListActivity.class);
                        intentMyGoods.putExtra(BaseActivity.KEY_TITLE, getResources().getText(R.string.activity_title_find_goods));
                        intentMyGoods.putExtra(BaseActivity.KEY_TYPE, BaseActivity.LogisticsFragmentType.TYPE_MINE);
                        mContext.startActivity(intentMyGoods);
                        break;
                    case R.id.lr_my_vehicles:
                        Intent intentMyVehicles = new Intent();
                        intentMyVehicles.setClass(mContext, VehicleListActivity.class);
                        intentMyVehicles.putExtra(BaseActivity.KEY_TITLE, getResources().getText(R.string.activity_title_find_vehicles));
                        intentMyVehicles.putExtra(BaseActivity.KEY_TYPE, BaseActivity.LogisticsFragmentType.TYPE_MINE);
                        mContext.startActivity(intentMyVehicles);
                        break;
                    default:
                        break;
                }
            }
        };

        homePage.findViewById(R.id.lr_find_goods).setOnClickListener(listener);
        homePage.findViewById(R.id.lr_find_vehicles).setOnClickListener(listener);
        homePage.findViewById(R.id.lr_my_goods).setOnClickListener(listener);
        homePage.findViewById(R.id.lr_my_vehicles).setOnClickListener(listener);

        scrollViewContainer.addView(homePage);
    }

    @Override
    protected void setupFloatingActionButtons(FloatingActionMenu fabMenu) {
        if (null == fabMenu) return;

        fabMenu.setVisibility(View.GONE);
    }
}
