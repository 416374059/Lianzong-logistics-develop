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

package com.lianzong.logistics.app.ui.activity;

import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lianzong.logistics.app.R;

import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {
    public static final String KEY_TITLE = "title";
    public static final String KEY_TYPE= "type";

    public enum LogisticsFragmentType{
        TYPE_ALL,
        TYPE_MINE
    }

    private static final int NUM_OF_ITEMS = 100;
    private static final int NUM_OF_ITEMS_FEW = 3;

    protected int mActionBarSize;
    protected int mFlexibleSpaceHeight;
    protected int mTabHeight;
    protected float mFlexibleRange;
    protected int mFabMargin;
    protected int mFlexibleSpaceShowFabOffset;
    protected int mFloatingActionMenuTopMargin;
    protected int mActivityPaddingLeft, mActivityPaddingTop, mActivityPaddingRight, mActivityPaddingBottom;

    protected void getDimensionPixelSize() {
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
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    protected int getScreenHeight() {
        return findViewById(android.R.id.content).getHeight();
    }

    public static ArrayList<String> getDummyData() {
        return getDummyData(NUM_OF_ITEMS);
    }

    public static ArrayList<String> getDummyData(int num) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            items.add("Item " + i);
        }
        return items;
    }

    protected void setDummyData(ListView listView) {
        setDummyData(listView, NUM_OF_ITEMS);
    }

    protected void setDummyDataFew(ListView listView) {
        setDummyData(listView, NUM_OF_ITEMS_FEW);
    }

    protected void setDummyData(ListView listView, int num) {
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getDummyData(num)));
    }

    protected void setDummyDataWithHeader(ListView listView, int headerHeight) {
        setDummyDataWithHeader(listView, headerHeight, NUM_OF_ITEMS);
    }

    protected void setDummyDataWithHeader(ListView listView, int headerHeight, int num) {
        View headerView = new View(this);
        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, headerHeight));
        headerView.setMinimumHeight(headerHeight);
        // This is required to disable header's list selector effect
        headerView.setClickable(true);
        setDummyDataWithHeader(listView, headerView, num);
    }

    protected void setDummyDataWithHeader(ListView listView, View headerView, int num) {
        listView.addHeaderView(headerView);
        setDummyData(listView, num);
    }
}
