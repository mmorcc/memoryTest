package com.org.myapplication.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.org.myapplication.R;

public class SecondFragment extends Fragment {


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        View root = inflater.inflate(R.layout.fragment_second, container, false);

        return root;

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}