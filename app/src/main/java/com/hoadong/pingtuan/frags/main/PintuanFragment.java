package com.hoadong.pingtuan.frags.main;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoadong.diy.deleagates.BaseDelegate;
import com.hoadong.pingtuan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PintuanFragment extends BaseDelegate{


    @Override
    public Object setLayout() {
        return R.layout.fragment_pintuan;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pintuan, container, false);
    }

}
