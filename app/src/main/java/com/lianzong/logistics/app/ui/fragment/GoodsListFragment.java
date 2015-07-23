package com.lianzong.logistics.app.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lianzong.logistics.app.R;


public class GoodsListFragment extends Fragment {
    private static final String KEY_TITLE = "title";

    public GoodsListFragment() {
        // Required empty public constructor
    }

    public static GoodsListFragment newInstance(String title) {
        GoodsListFragment f = new GoodsListFragment();

        Bundle args = new Bundle();

        args.putString(KEY_TITLE, title);
        f.setArguments(args);

        return (f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        return inflater.inflate(R.layout.fragment_goods, container, false);
    }
}
