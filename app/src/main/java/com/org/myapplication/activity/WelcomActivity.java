package com.org.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.org.myapplication.R;
import com.org.myapplication.utils.Util;


public class WelcomActivity extends Activity {
    EditText name;
    EditText pwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
//        startActivity(new Intent(WelcomActivity.this, FinishActivity.class));
//        Util.writeFileToLocalStorage("ceshi1");
//        Util.writeFileToLocalStorage("ceshi2");
//        Util.writeFileToLocalStorage("ceshi3");
//        Util.writeFileToLocalStorage("ceshi4");
        findViewById(R.id.bt).setOnClickListener(view -> {
            Util.select.clear();
            Util.uSelect.clear();
            startActivity(new Intent(WelcomActivity.this, FirstWordActivity.class));
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
