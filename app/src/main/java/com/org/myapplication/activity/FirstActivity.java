package com.org.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.org.myapplication.R;
import com.org.myapplication.bean.Good;
import com.org.myapplication.bean.GoodType;
import com.org.myapplication.utils.Const;
import com.org.myapplication.utils.DpUtil;
import com.org.myapplication.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class FirstActivity extends Activity {
    LinearLayout ll;
    private LayoutInflater flater;
    private List<Good> data=new ArrayList<>();
    TextView tv;
    private String info="请您记住接下来出现的5个词语，并在" +
            "随后出现的多个选项中正确找出这5个" +
            "词语。" +
            "准备好之后点击下方“->”按钮继续";

   public static boolean needReFresh=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.first);
        findViewById(R.id.back_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.back_btn).setVisibility(View.GONE);
        TextView title=findViewById(R.id.title);
        title.setText(!TextUtils.isEmpty(getIntent().getStringExtra("title")) ?
                getIntent().getStringExtra("title"):"");

        ll=findViewById(R.id.ll);
        tv=findViewById(R.id.tv);



        flater = LayoutInflater.from(this);

        refresh();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Util.l("needre="+needReFresh);
        if(needReFresh){
            needReFresh=false;
            ll.removeAllViews();
            refresh();
        }
    }

    private void refresh() {
        Util.l("Util.types.size()="+Util.types.size()+"  Util.isRetry5Word"+Util.isRetry5Word+" isFirst="+Util.isFirst);
        if(Util.isFirst){
            if(Util.types.size()==0)
            {
                Util.isFirst=false;
                Util.isRetry5Word=true;
                Util.types.clear();
                Util.types.add(new GoodType(0,3));
                Util. types.add(new GoodType(1,3));
                Util. types.add(new GoodType(2,3));
                Intent intent=new Intent(FirstActivity.this, SelectWordActivity.class);
                intent.putExtra("title","请选择");
                intent.putExtra("subType","0");
                startActivity(intent);
                finish();
                return;
            }
        }else {
//            if(Util.types.size()==2){
//                Util.l("ss  22");
//
////            Util.types.add(new GoodType(0,1));
////            Util. types.add(new GoodType(1,1));
////            Util. types.add(new GoodType(2,1));
////            Intent intent=new Intent(FirstActivity.this, SuccessActivity.class);
////            intent.putExtra("title","请选择");
////            intent.putExtra("subType","0");
////            startActivity(intent);
////            finish();
////            return;
//            }else
            if(Util.types.size()==0){
                    Util.l("ss  11");
                    Intent intent=new Intent(FirstActivity.this, FinishActivity.class);
                    intent.putExtra("title","请选择");
                    intent.putExtra("subType","0");
                    startActivity(intent);
                    finish();
                    return;
            }
        }
//        if(Util.types.size()<4){
//
////            Util.showInfo(FirstActivity.this,"完成度("+(4-Util.types.size()+"/4)"),null);
//
//        }
//        if(Util.types.size()==0){
//            Util.showErr(FirstActivity.this,"4种类型已经测试完成");
//            return;
//        }
        GoodType type=Util.type;
        if(type==null||type.getTimes()==0){
            type=Util.getType();
            Util.endTime=-1;
        }
        GoodType finalType = type;
        findViewById(R.id.bt).setOnClickListener(view -> {
            Util.l(finalType.getType()+" ss");
            if(finalType.getType()==0){//64
                Util.uSelect.clear();
                Intent intent=new Intent(FirstActivity.this, SelectActivity.class);
                intent.putExtra("title","请选择");
                intent.putExtra("subType","0");
                startActivity(intent);
            }else if(finalType.getType()==1){//88
                Util.uSelect.clear();
                Intent intent=new Intent(FirstActivity.this, Tab8Activity.class);
                intent.putExtra("title","请选择");
                intent.putExtra("subType","0");
                startActivity(intent);
            }else if(finalType.getType()==2){//2
                Util.uSelect.clear();
                Intent intent=new Intent(FirstActivity.this, Tab2Activity.class);
                intent.putExtra("title","请选择");
                intent.putExtra("subType","0");
                startActivity(intent);
            }else if(finalType.getType()==3){
                Util.uSelect.clear();
                Intent intent=new Intent(FirstActivity.this, Tab2Activity.class);
                intent.putExtra("title","请选择");
                intent.putExtra("subType","0");
                startActivity(intent);
            }
            if(Util.endTime==-1){
                Util.endTime=0;
                Util.startTime=new Date().getTime();
                Util.type.setcTimes(0);
            }

        });
        data=Util.getRandomGood();
        Util.needGoods=data.subList(0,3);
        addItem(0,1);
        Util.l("uSelGoods s="+Util.needGoods.size());

        tv.setText("请您在商场中找到以下三种商品，点击对应的商品图，在购物车中点击完成即可。");
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void addItem(int i,int num) {
        LinearLayout lls= (LinearLayout) flater.inflate(R.layout.img_ll,null);

        addGood(lls,data.get(i),i);
        if(i+2<data.size()){
            addGood(lls,data.get(i+1),i+1);
            addGood(lls,data.get(i+2),i+2);
        }


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        int W=DpUtil.getScreenWidth(this)-DpUtil.dip2px(this,40);
        params.width= W/num;
        params.height=DpUtil.dip2px(this,160);
        ll.addView(lls,params);
    }
    private void addGood(LinearLayout lls, Good good, int i) {
        LinearLayout img_item= (LinearLayout) flater.inflate(R.layout.img_item,null);
        TextView name=img_item.findViewById(R.id.name);
        ImageView img=img_item.findViewById(R.id.img);
        name.setText(good.getName());
        img.setImageResource(good.getId());
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        ViewGroup.LayoutParams pp = img.getLayoutParams();

        int W=DpUtil.getScreenWidth(this)-DpUtil.dip2px(this,40);

        pp.height=DpUtil.dip2px(this,   120);
        pp.width=W/4;
        ViewGroup.MarginLayoutParams mp= (ViewGroup.MarginLayoutParams) pp;
        int dp5=DpUtil.dip2px(this,   5);
        mp.setMargins(dp5,dp5,dp5,dp5);


        lls.addView(img_item,params2);
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
