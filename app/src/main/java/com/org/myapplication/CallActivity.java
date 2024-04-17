package com.org.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.org.myapplication.adapter.Adatapter;
import com.org.myapplication.bean.Calling;
import com.org.myapplication.bean.CallingType;
import com.org.myapplication.bean.Device;
import com.org.myapplication.bean.MyBoolean;
import com.org.myapplication.compenents.CircleTimerView;
import com.org.myapplication.compenents.FloatingVideoService;
import com.org.myapplication.bean.MyCall;
import com.org.myapplication.compenents.MyApplication1;
import com.org.myapplication.compenents.Service1;
import com.org.myapplication.fragment.FirstFragment;
import com.org.myapplication.fragment.FourFragment;
import com.org.myapplication.fragment.SecondFragment;
import com.org.myapplication.fragment.ThirdFragment;
import com.org.myapplication.net.OnBack;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.SharedPreferencesUtils;
import com.org.myapplication.utils.ShowBottomEditDialog;
import com.org.myapplication.utils.Util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallActivity extends AppCompatActivity {
    private Button setting;
//    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//    Manifest.permission.READ_PHONE_STATE,
//    Manifest.permission.CAMERA,
//    Manifest.permission.ACCESS_COARSE_LOCATION,
//    Manifest.permission.SYSTEM_ALERT_WINDOW,
//    Manifest.permission.ACCESS_FINE_LOCATION,
//    Manifest.permission.BLUETOOTH_CONNECT,
//    Manifest.permission.READ_EXTERNAL_STORAGE};
    private String[] permissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    private ListView listView;
    private List<CallingType> data=new ArrayList<>();
    private List<String> permissionList = new ArrayList<>();
    private Adatapter adatapter;
    private Device myDevice=null;
    private Calling myCalling=null;
    private FloatingVideoService.MyBinder downloadBinder; //创建一个ServiceConnection的内部类，并重写里面的方法。
    private CircleTimerView mCircleTimerView ;


    private BottomNavigationView bottomNavigationView;
    private List<Fragment> fragmentList;
    private static final int INDEX_HOME_FRAGMENT = 0;
    private FragmentTransaction transaction;

    SharedPreferencesUtils sp= SharedPreferencesUtils.getInstance("isLogin");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);     // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



        Util.l("进来了0110");
        getPermission();



        checkOverLay(this);

//        String deviceName= MyApplication1.getSp().get("deviceName");
//        String pwd=MyApplication1.getSp().get("pwd");
//        if(!deviceName.equals("")){
//            startGetState();
//        }

        Intent intent = new Intent();
        intent.setClass(CallActivity.this,FloatingVideoService.class);
        bindService(intent,serviceConnection, BIND_AUTO_CREATE);//绑定服务

        if(downloadBinder!=null) {
            downloadBinder.getService().showView();
        }
//        initView();}
        initToolbar();
        initFragment();
        initNavigationBottom();

        TextView title22=findViewById(R.id.title22);
        String titles = sp.get("title");
        Util.l(titles);
        if(!TextUtils.isEmpty(titles)){
            title22.setText(titles);
        }
        title22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.l(titles+"ssssssssss");
                ShowBottomEditDialog showBottomEditDialog=new ShowBottomEditDialog();
                ShowBottomEditDialog.str=title22.getText().toString();
                showBottomEditDialog.BottomDialog(CallActivity.this, new CallBack() {
                    @Override
                    public void onDo(Object o) {
                        String str= (String) o;
                        if(str!=null){
                            title22.setText(str);
                            sp.put("title",str);
                        }

                    }
                });
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void initNavigationBottom() {
        bottomNavigationView = findViewById(R.id.navigation_bottom);
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
    }
    private void initFragment() {
//        FirstFragment diariesFragment = getFragment();
//        if (diariesFragment == null) {
//            diariesFragment = new FirstFragment();
//            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), diariesFragment, R.id.content);
//        }

        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
            fragmentList.add(new FourFragment());
            fragmentList.add(new SecondFragment());
            fragmentList.add(new FirstFragment());
            fragmentList.add(new ThirdFragment());

            if (fragmentList.size() >1) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                transaction = fragmentManager.beginTransaction();
                for (int i = 0; i < fragmentList.size(); i++) {
                    Fragment fragment = fragmentList.get(i);
                    transaction.add(R.id.content, fragment);
                    transaction.hide(fragment);
                }
                transaction.show(fragmentList.get(INDEX_HOME_FRAGMENT));
                transaction.commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
    private FirstFragment getFragment() {
        return (FirstFragment) getSupportFragmentManager().findFragmentById(R.id.content);
    }

    private void initToolbar() {
        //设置顶部状态栏为透明
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.menu_diary:
                changeFragment(2);
////                MeController.setToolbarVisibility(this);
//                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
//                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new FirstFragment(), R.id.content);
                break;
            case R.id.menu_new:
                changeFragment(0);
//                bottomNavigationView.setVisibility(View.GONE);
//                MeController.setToolbarVisibility(this);
//                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
//                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new SecondFragment (), R.id.content);
                break;
            case R.id.menu_state:
                changeFragment(2);
//                findViewById(R.id.toolbar).setVisibility(View.GONE);
//                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
//                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new ThirdFragment(), R.id.content);
                break;
            case R.id.menu_test:
                changeFragment(0);
//                findViewById(R.id.toolbar).setVisibility(View.GONE);
//                ActivityUtils.removeFragmentTOActivity(getSupportFragmentManager(), getSupportFragmentManager().findFragmentById(R.id.content));
//                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), new FourFragment(), R.id.content);
                break;
        }
        return true;
    };

    private void changeFragment(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (index == i) {
                transaction.show(fragmentList.get(i));
            } else {
                transaction.hide(fragmentList.get(i));
            }
        }
        transaction.commit();
    }














    private void initView() {
        mCircleTimerView = findViewById(R.id.circle_timer);
        mCircleTimerView.setVisibility(View.GONE);
        mCircleTimerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCircleTimerView.cancelAnim();

            }
        });
        mCircleTimerView.setOnCountDownFinish(new CircleTimerView.OnCountDownFinish() {
            @Override
            public void onFinish() {

            }
        });
//        mCircleTimerView.start();
    }
    private void startGetState() {
        new Thread(new Runnable() {
            @Override
            public void run() {
              while (true){
                  try {
                      String DEVICE_ID=Util.getUUID();
                      Util.getDeviceById(DEVICE_ID,new OnBack() {
                          @Override
                          public void onSuccess(Object obj) {
                              if(obj!=null){
                                  myDevice= (Device) obj;
                                  myCalling=myDevice.getCalling();
                                  Util.l("hujia11s  "+myDevice.toString());
                                  if(adatapter!=null){
                                      adatapter.setCalling(myCalling);
                                      adatapter.notifyDataSetChanged();
                                  }
                              }
//                              Util.showErr(CallActivity.this,"查询设备状态 成功");
                          }

                          @Override
                          public void onFail(Object obj) {

//                              Util.showErr(CallActivity.this,"查询设备状态 失败");
                          }
                      });

                      Thread.sleep(1000);
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }
              }
            }
        }).start();
    }

    public static final int REQUEST_CODE = 1012;
    private void checkOverLay(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                // 用户已经回收了悬浮窗权限
                Util.showErr(this,"请开启悬浮窗权限");
                goHuaWeiMainager();
            } else {
                // 悬浮窗权限可用，执行相关操作
            }
        }
    }
    private void goHuaWeiMainager() {
        try {
            Intent intent = new Intent(this.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            this.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "跳转失败", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            goIntentSetting();
        }
    }
    private void goIntentSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        try {
            this.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListView() {
        listView=findViewById(R.id.list);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            CallingType item= data.get(position);
            Calling calling=new Calling();
            calling.setTypeId(item.getId());
            calling.setTypeName(item.getCallName());
            calling.setCallTime(new Date().getTime());
            calling.setDeviceId(Util.getUUID());
            calling.setDeviceName(MyApplication1.getSp().get("deviceName"));
            if(myCalling==null){
                myCalling=calling;
                Util.l("hujiao0"+myCalling.toString());
            }else {
                Util.l("hujiao"+myCalling.toString());
                if(myCalling.getFinishTime()>0){
                    myCalling=calling;
                }else if(myCalling.getAnswerTime()>0){
                    Util.l("hujiao3");
                    myCalling.setFinishTime(new Date().getTime());
                }else if(myCalling.getCallTime()>0){
                    Util.l("hujiao4");
                    Util.showErr(this,"呼叫等待中。。");
                   return;
                }
            }

            Util.updateCalling(myCalling,new OnBack() {
                @Override
                public void onSuccess(Object obj) {
                    MyBoolean myBoolean= (MyBoolean) obj;
                    if(myBoolean.getSuccess()){
                        if(myCalling.getAnswerTime()>0){
                            Util.l("hujiao3");
                            Util.showErr(CallActivity.this,"呼叫完成");
                        }else if(myCalling.getCallTime()>0){
                            Util.l("hujiao4");
                            Util.showErr(CallActivity.this,"正在呼叫");
                            return;
                        }

                    }else {
                        Util.showErr(CallActivity.this,"保存失败");
                    }




                }

                @Override
                public void onFail(Object obj) {
                    Util.showErr(CallActivity.this,"更新状态失败");
                }
            });
        });

        Util.getAllCallTypes(new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                data= (List<CallingType>) obj;
                adatapter=new Adatapter(CallActivity.this,data);
                listView.setAdapter(adatapter);

            }

            @Override
            public void onFail(Object obj) {

                Util.showErr(CallActivity.this,"getAllCallTypes 失败");
            }
        });
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) { //该方法会在Activity和Service绑定成功的时候调用。

            downloadBinder = (FloatingVideoService.MyBinder) service; //通过向下转型得到了DownloadBinder的实列

        }

        @Override
        public void onServiceDisconnected(ComponentName name) { //该方法只有在Service的创建崩溃或者被杀掉的时候才会调用，并不常用。

        }
    };
    @Override
    protected void onPause() {
        super.onPause();
//        if(downloadBinder!=null){
//            downloadBinder.getService().showView();
//            if(myCalling!=null){
//                if( myCalling.getFinishTime()>0){
//                    if(downloadBinder!=null){
//                        downloadBinder.getService().setFinished();
//                    }
//                }else   if(myCalling.getAnswerTime()>0){
//                    if(downloadBinder!=null){
//                        downloadBinder.getService().setAnsered();
//                    }
//
//                }else  if( myCalling.getCallTime()>0){
//                    if(downloadBinder!=null){
//                        downloadBinder.getService().setCalling();
//                    }
//                }
//            }
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if(downloadBinder!=null){
//            downloadBinder.getService().hideView();
//        }

        if(downloadBinder!=null){
            downloadBinder.getService().showView();
        }
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
//                startActivity(new Intent(this, MainActivity.class));
//                LoginActivity.this.finish();
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
        } else if (requestCode == 1000) {
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
                    Util.showErr(this,"授权成功");
                } else {
                    Util.showErr(this,"授权失败");
                    Util.l("没有授权");
                }
            }
        }
    }


}