package com.org.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.org.myapplication.R;
import com.org.myapplication.bean.Good;
import com.org.myapplication.utils.DpUtil;
import com.org.myapplication.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FirstWordActivity extends Activity {
    LinearLayout ll;
    private LayoutInflater flater;
    private List<Good> data=new ArrayList<>();
    TextView tv;
    private String info="请您记住接下来出现的5个词语，并在" +
            "随后出现的多个选项中正确找出这5个" +
            "词语。" +
            "准备好之后点击下方“->”按钮继续";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.first);
        findViewById(R.id.back_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.back_btn).setOnClickListener(view -> finish());
        TextView title=findViewById(R.id.title);
        title.setText(!TextUtils.isEmpty(getIntent().getStringExtra("title")) ?
                getIntent().getStringExtra("title"):"");

        ll=findViewById(R.id.ll);
        tv=findViewById(R.id.tv);

        flater = LayoutInflater.from(this);

//        if(Util.types.size()==0){
//            Intent intent=new Intent(FirstWordActivity.this, FinishActivity.class);
//            intent.putExtra("title","请选择");
//            intent.putExtra("subType","0");
//            startActivity(intent);
//            finish();
//            return;
//        }
//        if(Util.types.size()<4){
//            Util.showInfo(FirstWordActivity.this,"完成度("+(4-Util.types.size()+"/4)"));
//
//        }
//         if(Util.types.size()==0){
//            Util.showErr(FirstWordActivity.this,"4种类型已经测试完成");
//            return;
//        }
//        int type=Util.getType();
        findViewById(R.id.bt).setOnClickListener(view -> {
            Util.startTime=new Date().getTime();
//                Util.uSelect.clear();
            Intent intent=new Intent(FirstWordActivity.this, SelectWordActivity.class);
            intent.putExtra("title","请选择");
            intent.putExtra("subType","0");
            startActivity(intent);
        });
        Util.select.clear();
//        List<String> words = Util.getRandomWord();
        Util.select.add("面孔");
        addItem("面孔",1);
        Util.select.add("天鹅绒");
        addItem("天鹅绒",1);
        Util.select.add("教堂");
        addItem("教堂",1);
        Util.select.add("菊花");
        addItem("菊花",1);
        Util.select.add("红色");
        addItem("红色",1);

    }

    private void addItem(String nameStr,int num) {
        TextView name= (TextView) flater.inflate(R.layout.text_item,null);
        name.setText(nameStr);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.weight=1;
        int W=DpUtil.getScreenWidth(this)-DpUtil.dip2px(this,40);
        params.width= W/num;
        params.height=DpUtil.dip2px(this,60);
        params.topMargin=DpUtil.dip2px(this,10);
        ll.addView(name,params);
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
