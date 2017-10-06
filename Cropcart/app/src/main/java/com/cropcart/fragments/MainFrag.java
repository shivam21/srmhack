package com.cropcart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cropcart.R;
import com.cropcart.ui.MainActivity;

/**
 * Created by BHUSRI on 10/6/2017.
 */

public class MainFrag extends Fragment {
    private FloatingActionButton post;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);
        bindviews(v);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).postCommodity();
            }
        });
        return v;
    }

    private void bindviews(View v) {
        post = v.findViewById(R.id.postitem);
    }
}
