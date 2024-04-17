package com.org.myapplication.compenents;


import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;


import com.org.myapplication.CallActivity;
import com.org.myapplication.R;
import com.org.myapplication.SettingActivity;
import com.org.myapplication.adapter.Adatapter;
import com.org.myapplication.bean.Calling;
import com.org.myapplication.bean.CallingType;
import com.org.myapplication.bean.Device;
import com.org.myapplication.bean.MyBoolean;
import com.org.myapplication.net.OnBack;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicStampedReference;


public class FloatingVideoService extends Service {
    public static boolean isStarted = false;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;

    private MediaPlayer mediaPlayer;
    private View displayView;

    public static boolean bSetup = true;

    public static final int DMS_INPUT_IMG_W = 200;
    public static final int DMS_INPUT_IMG_H =  440;
    //
    private final String TAG = CallActivity.class.getName() ;
    private final int REQUEST_CODE_CONTACT = 1;
    private boolean isBackCamera =false;
    private ExecutorService cameraExecutor;
    private ImageView call;
    private TextView state_tv;
    private Bitmap bitmap1;
    private int stateType=1;//1等待，2，叫，3应答
    private boolean isShow=false;


    private ListView listView;
    private CircleTimerView mCircleTimerView ;
    private List<CallingType> data=new ArrayList<>();
    private List<String> permissionList = new ArrayList<>();
    private Adatapter adatapter;
    private Device myDevice=null;
    private Calling myCalling=null;


    //    private ProcessCameraProvider mCameraPRrovider = null;
    //
    //下面的可以去掉，bind connect 方式 服务
    public class MyBinder extends Binder{

        public FloatingVideoService getService(){
            return FloatingVideoService.this;
        }

    }

    //通过binder实现调用者client与Service之间的通信
    private MyBinder binder = new MyBinder();


    public void methodInService() {
        Log.i("MyService", "执行服务钟的methodInService()方法");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("MyService","jiebang ");
        return super.onUnbind(intent);

    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.i("MyService","绑定服务");
        return new MyBinder();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        isStarted = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY; //TYPE_APPLICATION_SUB_PANEL ; //TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.gravity = Gravity.LEFT | Gravity.TOP;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        layoutParams.width = DMS_INPUT_IMG_W;
        layoutParams.height = DMS_INPUT_IMG_H;
        layoutParams.x = 300;
        layoutParams.y = 300;

        mediaPlayer = new MediaPlayer();

        String deviceName= MyApplication1.getSp().get("deviceName");
        String pwd=MyApplication1.getSp().get("pwd");
        if(!deviceName.equals("")){
            startGetState();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int type=intent.getIntExtra("type",0);
        if(type==1){
            if(!isShow){
                showFloatingWindowCamera();
                isShow=true;
            }
        }else if(type==2){
            if(displayView!=null&&windowManager!=null){
                windowManager.removeView(displayView);
                isShow=false;
            }
        }
        Util.l("FLAG="+flags);
        return super.onStartCommand(intent, flags, startId);
    }
    public void showView(){
        if(!isShow){
            showFloatingWindowCamera();
            isShow=true;
        }
    }
    public void hideView(){
        if(displayView!=null&&windowManager!=null){
            windowManager.removeView(displayView);
            isShow=false;
        }
    }
    public void setCalling(){
        setCallImage(R.drawable.calling);
        if(state_tv!=null){
            state_tv.setText("呼叫中");
        }
    }
    public void setAnsered(){
        setCallImage(R.drawable.callcome);
        if(state_tv!=null){
            state_tv.setText("赶来中");
        }
    }
    public void setFinished(){
        setCallImage(R.drawable.callready);
        if(state_tv!=null){
            state_tv.setText("等待中");
        }
    }
    public CallBack myCallBack;
    public void setCallBack(CallBack callBack){
        myCallBack=callBack;
    }


    private void showFloatingWindowCamera() {
        Util.l("进来了");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                Util.l("进来了2");
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                displayView = layoutInflater.inflate(R.layout.desklayout, null);
                displayView.setOnTouchListener(new FloatingOnTouchListener());

                initListView(displayView);

                layoutParams.width = 200;
                layoutParams.height = 440;

                windowManager.addView(displayView, layoutParams);
                bSetup = true ;


                call=displayView.findViewById(R.id.call);
//                call.setOnTouchListener(new FloatingOnTouchListener());
                state_tv=displayView.findViewById(R.id.state_tv);
                setCallImage(R.drawable.callready);

//                call.setOnClickListener(o->{
//                    if(myCallBack!=null){
//                        myCallBack.onDo(null);
//                    }
////                    if(stateType==1){
////                        stateType=2;
////                        setCallImage(R.drawable.calling);
////                        state_tv.setText("呼叫中");
////                    }else if(stateType==2){
////                        stateType=3;
////                        setCallImage(R.drawable.callcome);
////                        state_tv.setText("赶来中");
////                    }else if(stateType==3){
////                        stateType=1;
////                        setCallImage(R.drawable.callready);
////                        state_tv.setText("等待中");
////                    }
//                });
//                call.setOnLongClickListener(o->{
//                    Intent intent=new Intent(this, SettingActivity.class);
//                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    return false;
//                });
            }
        }
    }

    private void setCallImage(int resImg) {
        bitmap1= BitmapFactory.decodeResource(getResources(),resImg);
        Bitmap bitmap2=this.makeBitmapSquare(bitmap1,200);
        RoundedBitmapDrawable roundImg1= RoundedBitmapDrawableFactory.create(getResources(),bitmap2);
        roundImg1.setAntiAlias(true);
        roundImg1.setCornerRadius(bitmap2.getWidth()/2);
        if(call!=null){
            call.setImageDrawable(roundImg1);
        }

    }

    public static Bitmap makeBitmapSquare(Bitmap oldbitmap, int newWidth){
        Bitmap newbitmap=null;
        if (oldbitmap.getWidth()>oldbitmap.getHeight()){
            newbitmap=Bitmap.createBitmap(oldbitmap,oldbitmap.getWidth()/2-oldbitmap.getHeight()/2,0,oldbitmap.getHeight(),oldbitmap.getHeight());
        }else{
            newbitmap=Bitmap.createBitmap(oldbitmap,0,oldbitmap.getHeight()/2-oldbitmap.getWidth()/2,oldbitmap.getWidth(),oldbitmap.getWidth());
        }
        int width=newbitmap.getWidth();
        float scaleWidth=((float)newWidth)/width;
        Matrix matrix=new Matrix();
        matrix.postScale(scaleWidth,scaleWidth);
        newbitmap= Bitmap.createBitmap(newbitmap,0,0,width,width,matrix,true);
        newbitmap=getAlplaBitmap(newbitmap,60);
        return newbitmap;
    }
 public static Bitmap getAlplaBitmap(Bitmap sourceImg, int number) {

        int[] argb = new int[sourceImg.getWidth() * sourceImg.getHeight()];

        sourceImg.getPixels(argb, 0, sourceImg.getWidth(), 0, 0, sourceImg.getWidth(), sourceImg.getHeight());

        number = number * 255 / 100;

        for (int i = 0; i < argb.length; i++) {

              argb[i] = (number << 24) | (argb[i] & 0x00FFFFFF);

        }

         sourceImg = Bitmap.createBitmap(argb, sourceImg.getWidth(), sourceImg.getHeight(), Bitmap.Config.ARGB_8888);

        return sourceImg;

 }
    @Override
    public void onDestroy() {
        if(windowManager !=null && layoutParams !=null) {
            if(bSetup){
                windowManager.removeView(displayView);
                bSetup = false ;
                isStarted = false;
            }
        }
        super.onDestroy();

    }

    long time=0;
    boolean showAll=false;
    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();

                    if(event.getActionIndex()==0){
                        time=new Date().getTime();
                        mCircleTimerView.setVisibility(View.VISIBLE);
                        mCircleTimerView.start();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                case MotionEvent.ACTION_UP:

                    Util.l("event.getActionIndex()"+event.getActionIndex());
                    if(event.getActionIndex()==0){
                       long time2=new Date().getTime();
                        Util.l("event.getActionIndex()"+event.getActionIndex());
                        Util.l("event.getActionIndex()"+(time2-time));

                        mCircleTimerView.setVisibility(View.GONE);
                        mCircleTimerView.cancelAnim();
                       if(time2-time<250){

                       }else  if(time2-time>3000){

                           Util.l("showAll"+showAll);
                           if(showAll){
                               layoutParams.width = 400;
                               layoutParams.height =  260;
                               listView.setVisibility(View.GONE);
                               windowManager.updateViewLayout(view, layoutParams);
                           }else {
                               layoutParams.width = 400;
                               layoutParams.height = 1100;
                               listView.setVisibility(View.VISIBLE);
                               windowManager.updateViewLayout(view, layoutParams);
                           }
                           showAll=!showAll;
                       }

                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }
    private void sendKeyCode(final int keyCode){
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(keyCode);
                } catch (Exception e) {
                    Log.e("err",e.getMessage()+"");
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private ImageView ll1,ll2,ll3;
    private TextView tvs;
    private boolean isSHow=true;
    public boolean isPlay=false;
    private void initListView(View root) {
        ll1=root.findViewById(R.id.ll1);
        ll2=root.findViewById(R.id.ll2);
        ll3=root.findViewById(R.id.ll3);
        tvs=root.findViewById(R.id.tvs);

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(isPlay){
                  ll1.setImageResource(R.drawable.stop);
              }else {
                  ll1.setImageResource(R.drawable.play);
              }
                isPlay=!isPlay;
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSHow){
                    ll3.setImageResource(R.drawable.fullscreen);
                    sendKeyCode(KeyEvent.KEYCODE_BACK);
                    tvs.setText("最小化");
                }else {
                    ll3.setImageResource(R.drawable.exitfullscreen);
                    Intent i=new Intent(MyApplication1.get(),CallActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication1.get().startActivity(i);
                    tvs.setText("最大化");
                }
                isSHow=!isSHow;
            }
        });



        listView=root.findViewById(R.id.list);
        mCircleTimerView = root.findViewById(R.id.circle_timer);
        mCircleTimerView.setOnCountDownFinish(new CircleTimerView.OnCountDownFinish() {
            @Override
            public void onFinish() {
                mCircleTimerView.setVisibility(View.GONE);
                layoutParams.width = 400;
                layoutParams.height = 1100;
                listView.setVisibility(View.VISIBLE);
                windowManager.updateViewLayout(displayView, layoutParams);
            }
        });
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
                            Util.showErr(getApplicationContext(),"呼叫完成");
                        }else if(myCalling.getCallTime()>0){
                            Util.l("hujiao4");
                            Util.showErr(getApplicationContext(),"正在呼叫");
                            return;
                        }

                    }else {
                        Util.showErr(getApplicationContext(),"设置失败");
                    }




                }

                @Override
                public void onFail(Object obj) {

                }
            });
        });

        Util.getAllCallTypes(new OnBack() {
            @Override
            public void onSuccess(Object obj) {
                data= (List<CallingType>) obj;
                adatapter=new Adatapter(getApplicationContext(),data);
                listView.setAdapter(adatapter);

            }

            @Override
            public void onFail(Object obj) {

            }
        });
    }
    private void startGetState() {
        new Thread(() -> {
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
                                if(myCalling!=null){
                                    if( myCalling.getFinishTime()>0){
                                        setFinished();
                                    }else   if(myCalling.getAnswerTime()>0){
                                        setAnsered();

                                    }else  if( myCalling.getCallTime()>0){
                                        setCalling();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFail(Object obj) {

                        }
                    });

                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}