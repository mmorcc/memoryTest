package com.org.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.org.myapplication.R;
import com.org.myapplication.bean.Good;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.DpUtil;
import com.org.myapplication.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SelectWordActivity extends Activity {
    LinearLayout ll;
    private LayoutInflater flater;
    private List<String> data=new ArrayList<>();
    private TextView tv;
    private String info="请您正确找出刚才出现的5个词语" +
            "点击对应的选项，全部选择完毕后" +
            "点击“->”按钮继续。";
    private String info2="您已经完成了一半的任务!" +
            "下面请您正确找出刚才出现的5个词" +
            "语，点击对应的选项，全部选择完" +
            "毕后点击“->”" +
            "继续。";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);


        findViewById(R.id.back_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.back_btn).setVisibility(View.GONE);
        TextView title=findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));

        ll=findViewById(R.id.ll);

        flater = LayoutInflater.from(this);

        tv=findViewById(R.id.tv);

        findViewById(R.id.bt).setOnClickListener(view -> {
        Util.l(" isRetry5Word="+Util.isRetry5Word);
            if(Util.uSelect.size()<5){
                Util.showErr(SelectWordActivity.this,"数量不够5个，请继续选择");
                return;
            }
            if(Util.isRight()){

//                Util.showInfo(FirstWordActivity.this,"test");
//                Util.showErr(SelectWordActivity.this,"选择正确");
                Util.endTime=new Date().getTime();
                String isRetry=Util.isRetry5Word?" 是回忆 ":" 不是回忆 ";
                Util.writeFileToLocalStorage(Util.userName
                        +isRetry
                        +" 选择5个单词成功正确率100%，用时"
                        +Util.getDuring(Util.startTime,Util.endTime)+"");
                Util.types.remove(Util.type);
                Util.uSelect.clear();
                finish();
                Intent intent=new Intent(SelectWordActivity.this, SuccessActivity.class);
                startActivity(intent);

            }else {
                if(!Util.isRetry5Word) {
                    Util.showInfo(SelectWordActivity.this, "回答错误，请您重新记忆之后再次" +
                            "选择。" +
                            "准备好之后点击下方“->”继续","->" ,new CallBack() {
                        @Override
                        public void onDo(Object o) {
                            Util.uSelect.clear();
                            finish();

                        }
                    });
                }else {
                    String percent=Util.getPercent();
                    String uselectRe=Util.getSelect();
                    String isRetry=Util.isRetry5Word?" 是回忆 ":" 不是回忆 ";
                    Util.writeFileToLocalStorage(Util.userName
                            +" 选择5个单词正确率"+percent+"  "
                            +isRetry
                            +" 用户选择="+uselectRe
                            +"，用时"
                            +Util.getDuring(Util.startTime,Util.endTime)+"");
                    Util.types.remove(Util.type);
                    Util.uSelect.clear();
                    finish();
                    Intent intent=new Intent(SelectWordActivity.this, SuccessActivity.class);
                    startActivity(intent);
                }

            }
        });
        if(Util.isRetry5Word){
            tv.setText(info2);
            data=Util.getRandomWord2();
        }else {
            tv.setText(info);
            data=Util.getRandomWord();
        }


        int i = 0;
        for (; i < data.size(); i=i+3) {
            addItem(i,1);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addItem(int i, int num) {
        LinearLayout lls= (LinearLayout) flater.inflate(R.layout.img_ll,null);

        addWord(lls,data.get(i),i);
        if(i+1<data.size()){
            addWord(lls,data.get(i+1),i+1);
        }
        if(i+2<data.size()){
            addWord(lls,data.get(i+2),i+2);
        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        int W=DpUtil.getScreenWidth(this)-DpUtil.dip2px(this,40);
        params.width= W/num;
        params.height=DpUtil.dip2px(this,68);
        params.topMargin=DpUtil.dip2px(this,10);
        ll.addView(lls,params);
    }

    private void addWord(LinearLayout lls, String good,int i) {
        LinearLayout img_item= (LinearLayout) flater.inflate(R.layout.img_item,null);
        TextView name=img_item.findViewById(R.id.name);
        ImageView img=img_item.findViewById(R.id.img);
        name.setText(good);
        img.setVisibility(View.GONE);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        params2.width=0;
        params2.height=DpUtil.dip2px(this,60);
        name.getLayoutParams().height=DpUtil.dip2px(this,60);
        params2.weight=1;
        int divider = DpUtil.dip2px(this, 5);
        params2.setMargins(divider,divider,divider,divider);
        lls.addView(img_item,params2);
        if(Util.uSelect.contains(good)){
            img_item.setBackgroundResource(R.drawable.background_corner);
        }else {
            img_item.setBackgroundResource(R.color.white);
        }
        img_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.uSelect.contains(good)){
                    Util.uSelect.remove(good);
                    view.setBackgroundResource(R.color.white);
                }else {
                    if(Util.uSelect.size()<5){
                        Util.uSelect.add(good);
                        view.setBackgroundResource(R.drawable.background_corner);
                    }else {
                        Util.showErr(SelectWordActivity.this,"已满5个");
                    }
                }
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
