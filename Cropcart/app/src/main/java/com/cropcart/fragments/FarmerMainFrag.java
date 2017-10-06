package com.cropcart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cropcart.R;
import com.cropcart.ui.MainActivity;

/**
 * Created by BHUSRI on 10/6/2017.
 */

public class FarmerMainFrag extends Fragment implements View.OnClickListener {
    private CardView myfarm, experts, labours, equipments, callus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.farmermain, container, false);
        bindviews(v);
        myfarm.setOnClickListener(this);
        experts.setOnClickListener(this);
        labours.setOnClickListener(this);
        equipments.setOnClickListener(this);
        callus.setOnClickListener(this);
        return v;
    }

    private void bindviews(View v) {
        myfarm = v.findViewById(R.id.myfarm);
        experts = v.findViewById(R.id.experts);
        labours = v.findViewById(R.id.labourers);
        equipments = v.findViewById(R.id.eqipments);
        callus = v.findViewById(R.id.approval);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.myfarm:
                ((MainActivity) getActivity()).myfarm();
                break;

        }
    }
}
