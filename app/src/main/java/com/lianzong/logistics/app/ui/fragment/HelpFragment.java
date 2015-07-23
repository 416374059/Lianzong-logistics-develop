package com.lianzong.logistics.app.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lianzong.logistics.app.R;


public class HelpFragment extends Fragment {
    private static final String KEY_TITLE = "title";

    public HelpFragment() {
        // Required empty public constructor
    }

    public static HelpFragment newInstance(String title) {
        HelpFragment f = new HelpFragment();

        Bundle args = new Bundle();

        args.putString(KEY_TITLE, title);
        f.setArguments(args);

        return (f);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        return inflater.inflate(R.layout.fragment_sample, container, false);
    }
}
