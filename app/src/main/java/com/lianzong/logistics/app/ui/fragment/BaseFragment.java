/*
 * Copyright 2014 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lianzong.logistics.app.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.activity.BaseActivity;

import java.util.ArrayList;

public abstract class BaseFragment extends Fragment {
    protected static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    protected int mActionBarSize;
    protected int mFlexibleSpaceHeight;
    protected int mTabHeight;
    protected float mFlexibleRange;
    protected int mFabMargin;
    protected int mFlexibleSpaceShowFabOffset;
    protected int mFloatingActionMenuTopMargin;

    protected int mActivityPaddingLeft, mActivityPaddingTop, mActivityPaddingRight, mActivityPaddingBottom;

    protected Context mContext;

    public static ArrayList<String> getDummyData() {
        return BaseActivity.getDummyData();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionBarSize = getActionBarSize();
        mFlexibleSpaceHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        mTabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        mFlexibleRange = mFlexibleSpaceHeight - mActionBarSize;
        mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
        mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
        mFloatingActionMenuTopMargin = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_fab_margin_top);

        mActivityPaddingLeft = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        mActivityPaddingTop = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
        mActivityPaddingRight = getResources().getDimensionPixelSize(R.dimen.activity_horizontal_margin);
        mActivityPaddingBottom = getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
    }

    protected int getActionBarSize() {
        Activity activity = getActivity();
        if (activity == null) {
            return 0;
        }
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = activity.obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    protected int getScreenHeight() {
        Activity activity = getActivity();
        if (activity == null) {
            return 0;
        }
        return activity.findViewById(android.R.id.content).getHeight();
    }

    protected void setDummyData(ListView listView) {
        listView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, getDummyData()));
    }

    protected void setDummyDataWithHeader(ListView listView, View headerView) {
        listView.addHeaderView(headerView);
        setDummyData(listView);
    }
}