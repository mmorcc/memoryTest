package com.org.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import  com.org.myapplication.R;



public class RegistActivity extends Activity {
    EditText name;
    EditText pwd;
    //声明数据库帮助器的对象
//    private UserDBHelper userDBHelper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_main);
        name=findViewById(R.id.ed_login_user);
        pwd=findViewById(R.id.ed_login_password);
        findViewById(R.id.back_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.back_btn).setOnClickListener(view -> finish());
        TextView title=findViewById(R.id.title);
        title.setText("注册");
        findViewById(R.id.bt_register).setOnClickListener(view -> {
            if(TextUtils.isEmpty(name.getText().toString())||
                    TextUtils.isEmpty(pwd.getText().toString())) {
                Toast.makeText(RegistActivity.this, "用户，密码不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
//            Util.regist(RegistActivity.this, name.getText().toString(),pwd.getText().toString());
    });
    }
    @Override
    protected void onStop() {
        super.onStop();
//        userDBHelper.closeLink();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
