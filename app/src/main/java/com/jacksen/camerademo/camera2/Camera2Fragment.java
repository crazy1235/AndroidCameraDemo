package com.jacksen.camerademo.camera2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacksen.camerademo.R;

/**
 * A simple {@link Fragment} subclass.
 *
 * http://doc.okbase.net/yydcdut/archive/122270.html
 * https://github.com/pinguo-yuyidong/Camera2
 *
 */
public class Camera2Fragment extends Fragment {


    public Camera2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera2, container, false);
    }

}
