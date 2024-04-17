package com.org.myapplication.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.org.myapplication.R;
import com.org.myapplication.adapter.Adatapter;
import com.org.myapplication.bean.CallingType;
import com.org.myapplication.net.OnBack;
import com.org.myapplication.net.getBike;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.org.myapplication.utils.SharedPreferencesUtils;
import com.org.myapplication.utils.Util;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;


public class FirstFragment extends Fragment {

    SharedPreferencesUtils sp= SharedPreferencesUtils.getInstance("isLogin");
    ListView list;
    Adatapter adapter;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View root = inflater.inflate(R.layout.fragment_first, container, false);

        initView(root);
        return root;

    }

    private void initView(View root) {
        list=root.findViewById(R.id.list);
        List<CallingType> data=new ArrayList<>();

        addItem(data,"变量","赋值",0);
        addItem(data,"Set AUTOMATIC Timeframe","true",1);
        addItem(data,"Timeframe","1 Minute",2);
        addItem(data,"--------","--------",3);
        addItem(data,"Initial Lot (Fixed Lot)","0.05",4);
        addItem(data,"INewsFilter","true",5);
        addItem(data,"--------","--------",6);
        addItem(data,"TradeMonday","true",7);
        addItem(data,"TradeTuesday","true",8);
        addItem(data,"TradeWednesday","true",9);
        addItem(data,"TradeThursday","true",10);
        addItem(data,"TradeFriday","true",11);
        addItem(data,"TradeSaturday","false",12);
        addItem(data,"TradeSunday","false",13);
        addItem(data,"--------","--------",14);
        addItem(data,"Take Profit in the account currency","5.0",15);
        addItem(data,"Two Ways orders","true",16);
        addItem(data,"HTF Price Action Filter","false",17);
        adapter=new Adatapter(getActivity(),data);
        list.setAdapter(adapter);
    }

    private void addItem(List<CallingType> data, String s, String s1,int pos) {
        String lname = sp.get("lname17");
        Log.e("kkk",lname);
        if(TextUtils.isEmpty(lname)){
            sp.put("lname"+pos,s);
            sp.put("lvalue"+pos,s1);
            CallingType callingType=new CallingType(s,s1);
            data.add(callingType);
        }else {
            String name=sp.get("lname"+pos);
            String value=sp.get("lvalue"+pos);
            CallingType callingType=new CallingType(name,value);
            data.add(callingType);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}