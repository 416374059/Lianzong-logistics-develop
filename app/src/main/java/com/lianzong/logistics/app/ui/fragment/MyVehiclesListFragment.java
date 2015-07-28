package com.lianzong.logistics.app.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lianzong.logistics.app.R;
import com.lianzong.logistics.app.ui.view.pulltorefresh.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MyVehiclesListFragment extends Fragment implements XListView.IXListViewListener, View.OnClickListener {

    private XListView mListView;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<String> items = new ArrayList<String>();
    private int mIndex = 0;
    private int mRefreshIndex = 0;

    private Handler mHandler;

    private final static MyVehiclesListFragment fragment = new MyVehiclesListFragment();

    public MyVehiclesListFragment() {
    }

    public static MyVehiclesListFragment newInstance() {
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        return inflater.inflate(R.layout.fragment_my_vehicles, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        geneItems();
        initView();
    }

    private void initView() {
        mHandler = new Handler();

        mListView = (XListView) getView().findViewById(R.id.lv_my_vehicles);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());

        mAdapter = new ArrayAdapter<String>(getActivity(), R.layout.vw_list_item, items);
        mListView.setAdapter(mAdapter);
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
            default:
                break;
        }
    }
}
