package com.org.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.org.myapplication.R;
import com.org.myapplication.bean.Good;
import com.org.myapplication.utils.Const;
import com.org.myapplication.utils.DpUtil;
import com.org.myapplication.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Tab2Activity extends Activity {
    LinearLayout ll;
    private LayoutInflater flater;
    private List<Good> data=new ArrayList<>();
    public static Set<Integer> select=new HashSet<>();
    TextView tv;
    private String info="请您记住接下来出现的5个词语，并在" +
            "随后出现的多个选项中正确找出这5个" +
            "词语。" +
            "准备好之后点击下方“->”按钮继续";
    private String subType="";
    private String title="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.first);
        findViewById(R.id.back_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.back_btn).setOnClickListener(view ->
        {
            finish();
        });

        findViewById(R.id.right_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.right_btn).setOnClickListener(view -> {


            Intent intent=new Intent(Tab2Activity.this,ShopCarActivity.class);
            startActivityForResult(intent,100);
        });

        TextView titleTv=findViewById(R.id.title);
        titleTv.setText(!TextUtils.isEmpty(getIntent().getStringExtra("title")) ?
                getIntent().getStringExtra("title"):"");

        ll=findViewById(R.id.ll);
        tv=findViewById(R.id.tv);
        tv.setText("");
        flater = LayoutInflater.from(this);

        findViewById(R.id.bt).setVisibility(View.GONE);
        findViewById(R.id.bt).setOnClickListener(view -> {
//            if(type==0){
//                Util.startTime=new Date().getTime();
//                Util.uSelect.clear();
//                Intent intent=new Intent(Tab2Activity.this, SelectActivity.class);
//                intent.putExtra("title","请选择");
//                intent.putExtra("subType",0);
//                startActivity(intent);
//            }else if(type==1){
//                Util.startTime=new Date().getTime();
//                Util.uSelect.clear();
//                Intent intent=new Intent(Tab2Activity.this, Tab2Activity.class);
//                intent.putExtra("title","请选择");
//                intent.putExtra("subType",0);
//            }

        });
         subType=getIntent().getStringExtra("subType");
         title=getIntent().getStringExtra("title");
        if(subType.equals("0")){
            addItem(Const.shipin,Const.baihuo,1);
        }else if(subType.equals("2")){
            if(Util.type.getType()==2){
//                addItem(Const.shipin,Const.baihuo,1);
            }else if(Util.type.getType()==3){
                if(title.equals(Const.shipin)){
                    addItem(Const.shengxian,Const.liangyou,1);
                }else    if(title.equals(Const.baihuo)){
                    addItem(Const.riyongbangong,Const.chufangweiyu,1);
                }
            }

        } if(subType.equals("3")){
            if(title.equals(Const.shengxian)){
                addItem(Const.shuiguo,Const.shucai,1);
            }else if(title.equals(Const.liangyou)){
                addItem(Const.wuguzaliang,Const.tiaoliaojiangzhi,1);
            } else if(title.equals(Const.riyongbangong)){
                addItem(Const.jiajuriyong,Const.bangongwenju,1);
            } else if(title.equals(Const.chufangweiyu)){
                addItem(Const.chufangcanju,Const.xihuyongping,1);
            }
        }
        findViewById(R.id.sc).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Util.type.setcTimes(Util.type.cTimes+1);
                }
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Util.endTime==-1){
            finish();
        }
    }

    private void addItem(String item1, String item2, int num) {
        LinearLayout lls= (LinearLayout) flater.inflate(R.layout.img_ll,null);
        addItem(item1,lls,1);
        addItem(item2,lls,1);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        int W=DpUtil.getScreenWidth(this)-DpUtil.dip2px(this,40);
        params.width= W/num;
//        params.height=DpUtil.dip2px(this,30);
        params.topMargin=DpUtil.dip2px(this,10);
        ll.addView(lls,params);
        lls.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Util.type.setcTimes(Util.type.cTimes+1);
                }
                return false;
            }
        });
    }

    private void addItem(String nameStr,LinearLayout lls,int num) {
        TextView name= (TextView) flater.inflate(R.layout.text_item,null);
        name.setText(nameStr);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.weight=1;
        int W=DpUtil.getScreenWidth(this)-DpUtil.dip2px(this,40);
        params.width= W/2;
        params.height=DpUtil.dip2px(this,60);
        params.topMargin=DpUtil.dip2px(this,10);
        lls.addView(name,params);
        name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Util.type.setcTimes(Util.type.cTimes+1);
                }
                return false;
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Tab2Activity.this, SelectActivity.class);

                if(subType.equals("0")){
                    if(Util.type.getType()==2){
                        intent=new Intent(Tab2Activity.this, Tab4Activity.class);
                        intent.putExtra("subType","2");
                    }else if(Util.type.getType()==3){
                        intent=new Intent(Tab2Activity.this, Tab2Activity.class);
                        intent.putExtra("subType","2");
                    }
                }else if(subType.equals("2")){
                    if(Util.type.getType()==3){
                        if(title.equals(Const.shipin)){
                            intent=new Intent(Tab2Activity.this, Tab2Activity.class);
                            intent.putExtra("subType","3");
                        }else    if(title.equals(Const.baihuo)){
                            intent=new Intent(Tab2Activity.this, Tab2Activity.class);
                            intent.putExtra("subType","3");
                        }
                    }

                }else if(subType.equals("3")){
                    intent=new Intent(Tab2Activity.this, SelectActivity.class);
                    intent.putExtra("subType",nameStr);
                }

                intent.putExtra("title",nameStr);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            Util.l("我点到了");
            Util.type.setcTimes(Util.type.cTimes+1);
        }
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Util.type.setcTimes(Util.type.cTimes+1);
        }
        return super.onKeyDown(keyCode, event);
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
