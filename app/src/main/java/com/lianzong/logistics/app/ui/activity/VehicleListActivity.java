package com.lianzong.logistics.app.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.activity.base.BaseToolbarWithListViewActivity;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionButton;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;

/**
 * Created by wu_shenglong on 2015/7/28.
 */
public class VehicleListActivity extends BaseToolbarWithListViewActivity {

    @Override
    protected void onStart() {
        super.onStart();

//        Intent fromIntent = getIntent();
//        if (null != fromIntent) {
//            mPageState = fromIntent.getIntExtra(EXTRA_VEHICLE_PAGE_STATE, TYPE_NONE);
//        }

        setHeaderViewShown(true, 300);
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

        // 发布车辆
        FloatingActionButton fbAddVehicle = new FloatingActionButton(this);
        fbAddVehicle.setImageResource(R.drawable.ic_add);
        fbAddVehicle.setButtonSize(FloatingActionButton.SIZE_MINI);
        fbAddVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VehicleListActivity.this, "发布车辆", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VehicleListActivity.this, AddEditVehicleActivity.class);
                intent.putExtra(AddEditVehicleActivity.EXTRA_VEHICLE_PAGE_STATE, AddEditVehicleActivity.TYPE_NEW);
                intent.putExtra(BaseActivity.KEY_TITLE, getString(R.string.activity_title_new_a_vehicle));
                VehicleListActivity.this.startActivity(intent);
            }
        });

        // 编辑我的车辆
        FloatingActionButton fbUpdateMyVehicle = new FloatingActionButton (this);
        fbUpdateMyVehicle.setImageResource(R.drawable.ic_edit);
        fbUpdateMyVehicle.setButtonSize(FloatingActionButton.SIZE_MINI);
        fbUpdateMyVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VehicleListActivity.this, "编辑我的车辆", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(VehicleListActivity.this, AddEditVehicleActivity.class);
                intent.putExtra(AddEditVehicleActivity.EXTRA_VEHICLE_PAGE_STATE, AddEditVehicleActivity.TYPE_EDIT);
                intent.putExtra(BaseActivity.KEY_TITLE, getString(R.string.activity_title_update_my_vehicles));
                VehicleListActivity.this.startActivity(intent);
            }
        });

        // 批量删除我的车辆
        FloatingActionButton fbPatchDelete = new FloatingActionButton(this);
        fbPatchDelete.setImageResource(R.drawable.ic_delete);
        fbPatchDelete.setButtonSize(FloatingActionButton.SIZE_MINI);
        fbUpdateMyVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VehicleListActivity.this, "批量删除我的车辆", Toast.LENGTH_SHORT).show();
            }
        });

        fbContainer.addMenuButton(fbUpdateMyVehicle);
        fbContainer.addMenuButton(fbAddVehicle);
        fbContainer.addMenuButton(fbPatchDelete);
    }

    private void setListViewAdapter(Adapter adapter) {

    }
}
