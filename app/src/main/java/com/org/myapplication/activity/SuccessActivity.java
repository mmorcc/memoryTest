package com.org.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.org.myapplication.R;
import com.org.myapplication.bean.GoodType;
import com.org.myapplication.utils.Util;


public class SuccessActivity extends Activity {
    EditText name;
    EditText pwd;
    String infotest="恭喜您回答正确！\n" +
            "请记住这5个词语，稍后需要您再重新回忆一遍！\n" +
            "\n" +
            "接下来您需要在百货商场中找到指定的商品，共有3种不同的分类布局，每一种分类布局是一种商场中的分类方式，您需要进入商品所在的类别中找到目标商品，完成任务。\n" +
            "现在是练习阶段，正确找到所有商品后即可进入正式测试。\n" +
            "\n" +
            "准备好之后请点击下方“开始”按钮开始测试。";
    String info="接下来您需要在百货商场中找到指" +
            "定的商品，共有3种不同的分类布" +
            "局，每种分类布局下需要找到3个商" +
            "品。\n\n" +
            "准备好之后请点击下方“开始”按" +
            "钮开始测试。";
    String info2="接下来您需要在百货商场中找到指" +
            "定的商品，共有3种不同的分类布" +
            "局，每种分类布局下需要找到3个商" +
            "品。\n\n" +
            "准备好之后请点击下方“开始”按" +
            "钮开始测试。";
    TextView tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);
        tv=findViewById(R.id.tv);
        if(Util.isRetry5Word){
            tv.setText(info2);
        }else {
            tv.setText(info);
        }
        if(Util.isFirst){
            tv.setText(infotest);
        }
        findViewById(R.id.bt).setOnClickListener(view -> {
            Util.l("on to FIrst");
            FirstActivity.needReFresh=true;
            startActivity(new Intent(SuccessActivity.this, FirstActivity.class));
//            if(Util.isFirst){
//                Util.isFirst=false;
//                Intent intent=new Intent(SuccessActivity.this, SelectWordActivity.class);
//                intent.putExtra("title","请选择");
//                intent.putExtra("subType","0");
//                startActivity(intent);
//                finish();
//            }else {
//                Util. types.add(new GoodType(1,3));
//                Util. types.add(new GoodType(2,3));
//                Util. types.add(new GoodType(3,3));
////                Util. types.add(new GoodType(1,5));
////                Util. types.add(new GoodType(2,5));
////                Util. types.add(new GoodType(3,5));
//                FirstActivity.needReFresh=true;
//                startActivity(new Intent(SuccessActivity.this, FirstActivity.class));
//            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
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
