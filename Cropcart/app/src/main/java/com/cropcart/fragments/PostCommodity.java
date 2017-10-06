package com.cropcart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cropcart.R;

/**
 * Created by BHUSRI on 10/6/2017.
 */

public class PostCommodity extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.postcommodity, container, false);
        bindViews(v);
        return v;
    }

    private void bindViews(View v) {

    }
}
