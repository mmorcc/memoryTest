package com.org.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.org.myapplication.R;
import com.org.myapplication.utils.Util;


public class LogActivity extends Activity {
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        tv=findViewById(R.id.tv);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String log= Util.readFileToLocalStorage();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(log+"");
                    }
                });
            }
        }).start();
    }
}
