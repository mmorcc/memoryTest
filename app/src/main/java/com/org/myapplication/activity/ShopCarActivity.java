package com.org.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.org.myapplication.R;
import com.org.myapplication.adapter.Adatapter;
import com.org.myapplication.adapter.GoodAdapter;
import com.org.myapplication.bean.CallingType;
import com.org.myapplication.bean.Good;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.Util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ShopCarActivity extends Activity {
    private ListView listView;
    private List<Good> data=new ArrayList<>();
    private GoodAdapter adatapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        findViewById(R.id.back_btn).setVisibility(View.VISIBLE);

        TextView title=findViewById(R.id.title);
        title.setText("购物车");
        findViewById(R.id.back_btn).setOnClickListener(view ->
        {

            Util.type.setcTimes(Util.type.cTimes+1);
            finish();
        });

        if(Util.uSelGoods!=null&&Util.uSelGoods.size()>0){
            for (int i = 0; i < Util.uSelGoods.size(); i++) {
                data.add(Util.uSelGoods.get(i));
            }
        }
        listView=findViewById(R.id.listv);
        adatapter=new GoodAdapter(this,data);
        listView.setAdapter(adatapter);

        findViewById(R.id.bt).setOnClickListener(view -> {
            Check();
//            Util.select.clear();
//            Util.uSelect.clear();
//            startActivity(new Intent(ShopCarActivity.this, FirstWordActivity.class));
        });
    }

    private void Check() {
        Util.type.setcTimes(Util.type.cTimes+1);
        if(Util.isSelectGoodOk()){
            Util.endTime=-1;
                String needSelStr=Util.getNeedSelectGood();
                String uSelStr=Util.getUSelect();
                String selectStr=" 需要选择"+needSelStr+" 用户选择"+uSelStr;
                Util.writeFileToLocalStorage(" 选择类型"+Util.type.getName()+" "
                        +" 点击次数:"+Util.type.cTimes+"次 "
                        +selectStr
                        +" 选择商品成功，用时"
                        +Util.getDuring(Util.startTime,new Date().getTime())+" " )
                ;
            FirstActivity.needReFresh=true;
            Util.showInfo(ShopCarActivity.this, "回答正确，请点击 -> 继续", new CallBack() {
                @Override
                public void onDo(Object o) {
                    Util.type.setcTimes(Util.type.cTimes+1);
                    if(Util.type.getTimes()>1){
                        Util.type.setTimes(Util.type.getTimes()-1);
                    }else {
                        Util.type.setTimes(Util.type.getTimes()-1);
                        if(!Util.isFirst){
                            Util.writeFileToLocalStorage(
                                    " 选择类型"+Util.type.getName()+" "

                                            +" 完成 ");
                        }
                        Util.types.remove(Util.type);

                    }

                    setResult(101);
                    finish();

                }
            });
            Util.uSelGoods.clear();
        }else {
            String needSelStr=Util.getNeedSelectGood();
            String uSelStr=Util.getUSelect();
            String selectStr=" 需要选择"+needSelStr+" 用户选择"+uSelStr;
            Util.writeFileToLocalStorage(" 选择类型"+Util.type.getName()+" "
                    +" 点击次数:"+Util.type.cTimes+"次 "
                    +selectStr
                    +" 选择商品错误，用时"
                    +Util.getDuring(Util.startTime,new Date().getTime())+" " )
            ;
            String errSelStr=Util.getErrSelect();
            if(Util.showBottomEditDialog!=null){
                Util.showBottomEditDialog.redStr=errSelStr;
            }
            Util.showInfo(ShopCarActivity.this, "选择错误，"+"请您重新选择", new CallBack() {
                @Override
                public void onDo(Object o) {
                    Util.type.setcTimes(Util.type.cTimes+1);
                }
            });
        }
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
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
