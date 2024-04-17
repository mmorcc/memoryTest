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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.org.myapplication.R;
import com.org.myapplication.bean.Good;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.Const;
import com.org.myapplication.utils.DpUtil;
import com.org.myapplication.utils.ShowBottomEditDialog;
import com.org.myapplication.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SelectActivity extends Activity {
    LinearLayout ll;
    private LayoutInflater flater;
    private List<Good> data=new ArrayList<>();
    private Good uSelectGood=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);


        findViewById(R.id.back_btn).setVisibility(View.VISIBLE);
//        findViewById(R.id.tv).setVisibility(View.GONE);
        findViewById(R.id.bt).setVisibility(View.GONE);
        findViewById(R.id.back_btn).setOnClickListener(view ->
        {

            Util.type.setcTimes(Util.type.cTimes+1);
            finish();
        });
        findViewById(R.id.right_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.right_btn).setOnClickListener(view -> {

            Util.type.setcTimes(Util.type.cTimes+1);
            Intent intent=new Intent(SelectActivity.this,ShopCarActivity.class);
            startActivityForResult(intent,100);
        });
        TextView title=findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));

        ll=findViewById(R.id.ll);

        flater = LayoutInflater.from(this);

//        findViewById(R.id.bt).setOnClickListener(view -> {
//        });

        String subType=getIntent().getStringExtra("subType");
        if(!TextUtils.isEmpty(subType)){
            if(subType.equals("0")){
                Util.l(" ss  0");
                data=Util.getRandomGood();
            }else if(!TextUtils.isEmpty(subType)){
                Util.l(" ss  "+subType);
                data=Util.getRandomGood8(subType);
                Util.l(" ss  "+data.size());
            }
        }
       addView();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==101&&requestCode==100){
            finish();
            Intent intent=new Intent(SelectActivity.this, FirstActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        addView();
    }

    private void addView() {
        ll.removeAllViews();
        int i = 0;
        for (; i < data.size(); i=i+2) {
            addItem(i,1);
        }
    }

    private void addItem(int i,int num) {
        LinearLayout lls= (LinearLayout) flater.inflate(R.layout.img_ll,null);

        addGood(lls,data.get(i),i);
        if(i+1<data.size()){
            addGood(lls,data.get(i+1),i+1);
        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        int W=DpUtil.getScreenWidth(this)-DpUtil.dip2px(this,40);
        params.width= W/num;
        params.height=DpUtil.dip2px(this,160);
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

    private void addGood(LinearLayout lls, Good good,int i) {
        LinearLayout img_item= (LinearLayout) flater.inflate(R.layout.img_item,null);
        TextView name=img_item.findViewById(R.id.name);
        ImageView img=img_item.findViewById(R.id.img);
        name.setText(good.getName());
        img.setImageResource(good.getId());
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        params2.width=0;
        params2.weight=1;
        ViewGroup.LayoutParams pp = img.getLayoutParams();
        pp.height=DpUtil.dip2px(this,   120);
        pp.width=DpUtil.dip2px(this,   120);
        lls.addView(img_item,params2);
        if(Util.isSelectedGood(good)){
            img_item.setBackgroundResource(R.drawable.background_corner);
        }else {
            img_item.setBackgroundResource(R.color.transparent);
        }
        img_item.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    Util.type.setcTimes(Util.type.cTimes+1);
                }
                return false;
            }
        });
        img_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.isSelectedGood(good)){
                    Util.delUSelGood(good);
                }else {
                    if(Util.uSelGoods.size()==3){
                        Util.showInfo(SelectActivity.this, "最多选择3个商品", new CallBack() {
                            @Override
                            public void onDo(Object o) {

                            }
                        });
                    }else {
                        Util.uSelGoods.add(good);
                    }

                }
                addView();

            }
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
