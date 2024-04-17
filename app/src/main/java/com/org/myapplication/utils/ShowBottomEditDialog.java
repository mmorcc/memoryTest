package com.org.myapplication.utils;


import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.org.myapplication.R;


public class ShowBottomEditDialog {
    private View view;
    public  static   String str="";
    public  static  String  btText="确定";
    public static String redStr="";
    public void BottomDialog(Context context, CallBack onClick) {
        if(context==null){
            Util.l("context is null");
            return;
        }
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog);
        //2、设置布局
        view = View.inflate(context, R.layout.dialog, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
//        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        TextView tv=dialog.findViewById(R.id.info);
        Util.l("red"+redStr);
        if(!TextUtils.isEmpty(redStr)){
            Util.l("red2"+redStr);
            tv.setText(Html.fromHtml("<font color='#FF0000'>"+redStr+"</font>"+str ));
            redStr="";
        }else {
            tv.setText(str);
        }

        TextView add=dialog.findViewById(R.id.add);
        if(!TextUtils.isEmpty(btText)){
            add.setText(btText);
        }

        dialog.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                str=et1.getText().toString();
                onClick.onDo(str);
                dialog.dismiss();
            }
        });

    }
}