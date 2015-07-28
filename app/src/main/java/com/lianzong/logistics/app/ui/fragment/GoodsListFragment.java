package com.lianzong.logistics.app.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonRectangle;
import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionButton;
import com.lianzong.logistics.app.ui.view.fab.FloatingActionMenu;
import com.lianzong.logistics.app.ui.view.pulltorefresh.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class GoodsListFragment extends Fragment implements XListView.IXListViewListener, View.OnClickListener {
    private static final String KEY_TITLE = "title";

    private RelativeLayout mRlSearchConditions;
    private ButtonRectangle mRectButton;
    private XListView mListView;
    private FloatingActionMenu mMenusGoods;
    private FloatingActionButton mFbAddVehicle, mFbSearchGoods;

    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private int mIndex = 0;
    private int mRefreshIndex = 0;
    private int mPreviousVisibleItem = 0;

    private Handler mHandler;

    private final static GoodsListFragment fragment = new GoodsListFragment();

    public GoodsListFragment() {
    }

    public static GoodsListFragment newInstance() {
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHandler = new Handler();
        geneItems();

        initFloatingMenus();
        initView();
    }

    private void initFloatingMenus() {
        mMenusGoods = (FloatingActionMenu) getView().findViewById(R.id.fb_menus);
        mMenusGoods.setClosedOnTouchOutside(true);
        mMenusGoods.hideMenuButton(false);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMenusGoods.showMenuButton(true);
            }
        }, 400);

//        mFbAddVehicle = (FloatingActionButton) getView().findViewById(R.id.fb_add_a_vehicle);
//        mFbAddVehicle.setOnClickListener(this);
//
//        mFbSearchGoods = (FloatingActionButton) getView().findViewById(R.id.fb_search_goods);
//        mFbSearchGoods.setOnClickListener(this);
    }

    private void initView() {
        mRlSearchConditions = (RelativeLayout) getView().findViewById(R.id.rl_search_conditions);
        // set animations to search conditions layout


        mRectButton = (ButtonRectangle) getView().findViewById(R.id.btn_search);
        mRectButton.setOnClickListener(this);

        mListView = (XListView) getView().findViewById(R.id.lv_goods);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());

        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.vw_list_item, items);
        mListView.setAdapter(mAdapter);

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 滑动停止
                        mMenusGoods.showMenuButton(true);
                        break;
                    default:
                        mMenusGoods.hideMenuButton(true);
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIndex = ++mRefreshIndex;
                items.clear();
                geneItems();
                mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.vw_list_item,
                        items);
                mListView.setAdapter(mAdapter);
                onLoad();
            }
        }, 2500);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                geneItems();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2500);
    }

    private void geneItems() {
        for (int i = 0; i != 20; ++i) {
            items.add("Test XListView item " + (++mIndex));
        }
    }

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                onGoodsSearching();
                break;
//            case R.id.fb_add_a_vehicle:
//                actionAddVehicle();
//                break;
//            case R.id.fb_search_goods:
//                actionSearchGoods();
//                break;
            default:
                break;
        }
    }

    private void actionAddVehicle() {
        Toast.makeText(getActivity(), "Add a Vehicle", Toast.LENGTH_LONG).show();
    }

    private void actionSearchGoods() {
        Toast.makeText(getActivity(), "Search Goods", Toast.LENGTH_LONG).show();
    }

    /**
     * search goods by some conditions
     */
    private void onGoodsSearching() {
        // wait for show the list view
        if (null != mRlSearchConditions) {
            mRlSearchConditions.setVisibility(View.GONE);
        }
    }
}
