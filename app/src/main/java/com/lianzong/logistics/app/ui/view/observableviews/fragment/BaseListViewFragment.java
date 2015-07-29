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

package com.lianzong.logistics.app.ui.view.observableviews.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.fragment.BaseFragment;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionButton;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;
import com.lianzong.logistics.app.ui.view.observableviews.ObservableListView;
import com.lianzong.logistics.app.ui.view.observableviews.ObservableScrollViewCallbacks;


/**
 * Fragment for ViewPagerTabFragmentActivity.
 * ScrollView callbacks are handled by its parent fragment, not its parent activity.
 */
public class BaseListViewFragment extends BaseFragment {

    public static final int FRAGMENT_TYPE_GOODS = 0;
    public static final int FRAGMENT_TYPE_VEHICLES = 1;


    private final int[] FLOATIONG_MENU_TITLES_ID = {R.string.fb_add_a_vehicle,
            R.string.fb_goods_search_conditions};
    private final int[] FLOATIONG_MENU_IMAGE_ID = {R.drawable.ic_add,
            R.drawable.ic_launcher};


    // floating action menus
    private FloatingActionMenu mMenusGoods;
    private FloatingActionButton mFbAddVehicle, mFbSearchGoods;
    private Handler mHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHandler = new Handler();

        initFloatingMenus(view);
        initView();
    }

    private void initFloatingMenus(View view) {
        mMenusGoods = (FloatingActionMenu) view.findViewById(R.id.fb_menus);
        mMenusGoods.setClosedOnTouchOutside(true);
        mMenusGoods.hideMenuButton(false);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMenusGoods.showMenuButton(true);
            }
        }, 400);

        mFbAddVehicle = new FloatingActionButton(getActivity());
        mFbAddVehicle.setImageResource(R.drawable.ic_add);
        mFbAddVehicle.setButtonSize(FloatingActionButton.SIZE_MINI);
        mFbAddVehicle.setLabelText(getString(R.string.fb_add_a_vehicle));

        mFbSearchGoods = new FloatingActionButton(getActivity());
        mFbSearchGoods.setImageResource(R.drawable.ic_launcher);
        mFbSearchGoods.setButtonSize(FloatingActionButton.SIZE_MINI);
        mFbSearchGoods.setLabelText(getString(R.string.fb_goods_search_conditions));
    }

    private void initView() {
        final ObservableListView listView = (ObservableListView) getView().findViewById(R.id.scroll);
        setDummyData(listView);

        Fragment parentFragment = getParentFragment();
        ViewGroup viewGroup = (ViewGroup) parentFragment.getView();
        if (viewGroup != null) {
            listView.setTouchInterceptionViewGroup((ViewGroup) viewGroup.findViewById(R.id.container));
            if (parentFragment instanceof ObservableScrollViewCallbacks) {
                listView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentFragment);
            }
        }
    }

    private void actionAddVehicle() {
        Toast.makeText(getActivity(), "Add a Vehicle", Toast.LENGTH_LONG).show();
    }

    private void actionSearchGoods() {
        Toast.makeText(getActivity(), "Search Goods", Toast.LENGTH_LONG).show();
    }
}
