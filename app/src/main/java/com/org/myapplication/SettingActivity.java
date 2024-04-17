package com.org.myapplication;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.org.myapplication.activity.LoginActivity;
import com.org.myapplication.bean.Device;
import com.org.myapplication.bean.MyBoolean;
import com.org.myapplication.compenents.CheckBottomEditDialog;
import com.org.myapplication.compenents.FloatingVideoService;
import com.org.myapplication.compenents.MyApplication1;
import com.org.myapplication.compenents.Service1;
import com.org.myapplication.net.Api;
import com.org.myapplication.net.Data;
import com.org.myapplication.net.InfoLogin;
import com.org.myapplication.net.OnBack;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.SharedPreferencesUtils;
import com.org.myapplication.utils.Util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SettingActivity extends Activity {

    private final String TAG = this.getClass().getName();
    EditText pwd;
    EditText pwd2,ip_tv,time_tv,device_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initTitle();
        initCom();
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Util.l("进来了00");
                saveData();
            }
        });
        String pwd=MyApplication1.getSp().get("pwd");
        if(pwd==null||pwd.trim().equals("")){

        }else{
            checkDialog();
        }
    }

    private void saveData() {
        String deviceName=device_name.getText().toString();
        if(deviceName.trim().equals("")){
            Toast.makeText(this,"设备名称不能为空",Toast.LENGTH_SHORT).show();
        }
        MyApplication1.getSp().put("deviceName",deviceName);
        MyApplication1.getSp().put("ip",ip_tv.getText().toString());
        MyApplication1.getSp().put("time",time_tv.getText().toString());

        MyApplication1.reSetNet();

        String pwdStr=pwd.getText().toString();
        String pwdStr2=pwd2.getText().toString();
        if(!pwdStr.trim().equals("")&&!pwdStr2.trim().equals("")){
            if(pwdStr.equals(pwdStr2)){
                MyApplication1.getSp().put("pwd",pwdStr);
            }else {
                Toast.makeText(this,"两次密码不一致，请修改",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
        }
        register(deviceName,pwdStr);
    }

    private void initCom() {

        device_name=findViewById(R.id.device_name);
         pwd=findViewById(R.id.pwd);
         pwd2=findViewById(R.id.pwd2);
        ip_tv=findViewById(R.id.ip_tv);
        time_tv=findViewById(R.id.time_tv);
        String deviceName=MyApplication1.getSp().get("deviceName");
        String ip=MyApplication1.getSp().get("ip");
        String time=MyApplication1.getSp().get("time");
        ip_tv.setText(ip);
        time_tv.setText(time);
        device_name.setText(deviceName);
    }

    private void initTitle() {
        TextView title=findViewById(R.id.title);
        title.setText("设置");
        ImageView bacBtn=findViewById(R.id.back_btn);
        bacBtn.setVisibility(View.VISIBLE);
        bacBtn.setOnClickListener(v->{
            finish();
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        Intent intent=new Intent(this, FloatingVideoService.class);
        intent.putExtra("type",1);//显示
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=new Intent(this, FloatingVideoService.class);
        intent.putExtra("type",2);//隐藏
        startService(intent);
    }
    private void checkDialog() {
        CheckBottomEditDialog checkBottomEditDialog=new CheckBottomEditDialog();
        checkBottomEditDialog.BottomDialog(this, new CallBack() {
            @Override
            public void onDo(Object o) {

            }
        });
    }
    /**
     * 示例，get加载Json数据
     */
    private void register(String username,String pwd) {
        String deviceId=Util.getUUID();
        Device d = new Device();
        d.setDeviceId(deviceId);
        d.setDeviceName(username);
        d.setPwd(pwd);
        d.setLastTime(new Date().getTime());
        Util.updateDevice(d,new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                MyBoolean myBoolean= (MyBoolean) obj;
                if(myBoolean.getSuccess()){
                    Util.showErr(SettingActivity.this,"保存成功");
                }else {
                    Util.showErr(SettingActivity.this,"保存失败");
                }
            }

            @Override
            public void onFail(Object obj) {
                Util.showErr(SettingActivity.this,"保存失败异常");
            }
        });

    }
}