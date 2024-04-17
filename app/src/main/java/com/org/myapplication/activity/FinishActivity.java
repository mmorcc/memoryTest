package com.org.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.org.myapplication.R;


public class FinishActivity extends Activity {
    EditText name;
    EditText pwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish);

        findViewById(R.id.bt).setOnClickListener(view -> {
            startActivity(new Intent(FinishActivity.this, LogActivity.class));
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
