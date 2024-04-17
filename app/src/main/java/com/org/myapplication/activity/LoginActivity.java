package com.org.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.gson.Gson;
import com.org.myapplication.MainActivity;
import com.org.myapplication.MyApplication;
import com.org.myapplication.net.Api;
import com.org.myapplication.net.Data;
import com.org.myapplication.net.Info;
import com.org.myapplication.net.InfoLogin;
import com.org.myapplication.utils.Internet;
import com.org.myapplication.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import  com.org.myapplication.R;
import com.org.myapplication.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  {
    private EditText username,pwd,age,sex;
    private Button login_bt;
    private TextView register_tv,info;

    private static final String TAG = "LoginActivity";

    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    private List<String> permissionList = new ArrayList<>();
    private SharedPreferencesUtils sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        info=findViewById(R.id.info);
        if (Build.VERSION.SDK_INT >= 23) {
            System.out.println("版本正确");
            getPermission();
        }else {
            System.out.println("版本过低");
        }
        bindView();
        sp= SharedPreferencesUtils.getInstance("isLogin");

//        String userId=sp.get("isLogin");
//        if(!userId.isEmpty()){
//            Util.userId=Long.parseLong(userId);
//            Util.sign=sp.get(Util.userId+"sign");
//            Util.userName=sp.get(Util.userId+"name");
//            startActivity(new Intent(this, MainActivity.class));
//            LoginActivity.this.finish();
//        }
    }

    private void bindView() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        login_bt = findViewById(R.id.login_bt);
        register_tv = findViewById(R.id.register_tv);
        username= findViewById(R.id.username);
        age= findViewById(R.id.age);
        sex= findViewById(R.id.sex);
//        pwd= findViewById(R.id.pwd);
        login_bt.setOnClickListener(v -> {
            String ages = age.getText().toString().trim();
            String sexs = sex.getText().toString().trim();
            String userNameStr = username.getText().toString().trim();
            if (userNameStr.isEmpty()) {
                Toast.makeText(this, "用户名不能为空!", Toast.LENGTH_SHORT).show();
//                return;
            }
//            if (psw.isEmpty()) {
//                Toast.makeText(this, "密码不能为空!", Toast.LENGTH_SHORT).show();
////                return;
//            }
//            startActivity(new Intent(this, MainActivity.class));

//            MyApplication.getNet()


            LogIn(userNameStr+" 年龄："+ages+"  性别:"+sexs,ages);
        });
//        startActivity(new Intent(LoginActivity.this, LogActivity.class));
        register_tv.setOnClickListener(v->{
            Intent setPsw_intent = new Intent(LoginActivity.this, RegistActivity.class);
            startActivity(setPsw_intent);
        });
    }
    /**
     * 示例，get加载Json数据
     */
    private void LogIn(String username,String pwd) {
        Util.userName=username;
        startActivity(new Intent(LoginActivity.this, WelcomActivity.class));

        Util.writeFileToLocalStorage("\n\n用户登录:"+username+"");
        Util.initData();
        finish();
    }

    private void getPermission() {
        if (permissionList != null) {
            permissionList.clear();
        }
        //版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionList.add(permission);
                }
            }

            if(!permissionList.isEmpty()){
                ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1000);
            }else{
                startActivity(new Intent(this, MainActivity.class));
                LoginActivity.this.finish();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1000) {
            //权限请求失败
            if (grantResults.length > 0) {
                //存放没授权的权限
                List<String> deniedPermissions = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add(permission);
                    }
                }
                if (deniedPermissions.isEmpty()) {
                    //说明都授权了
//                    getPic();
                } else {
                    //默认false 第二次提醒会有是否不在询问按钮，选择则为true  不在提醒是否申请权限
//                    if (!shouldShowRequestPermissionRationale(deniedPermissions.get(0))) {
////                        permissionDialog();
//                    }
                }
            }
        }
    }

}
