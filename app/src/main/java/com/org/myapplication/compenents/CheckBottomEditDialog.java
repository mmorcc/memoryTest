package com.org.myapplication.compenents;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.org.myapplication.R;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.SharedPreferencesUtils;
import com.org.myapplication.utils.Util;

import java.util.Date;

public class CheckBottomEditDialog {
    private View view;
    public  static   String str="";
    SharedPreferencesUtils sp= SharedPreferencesUtils.getInstance("isLogin");

    public void BottomDialog(Context context, CallBack onClick) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(context, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog);
        //2、设置布局
        view = View.inflate(context, R.layout.edit_dialog, null);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.CENTER);
        //设置弹出动画
//        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        String isBuy=sp.get("isBuy");
        if(isBuy!=null&&isBuy.equals("true")){

        }else{
            dialog.show();
        }



        EditText et1=dialog.findViewById(R.id.et1);
        et1.setText(str);

        TextView title=dialog.findViewById(R.id.tv_take_photo);
        title.setText("请输入密码");
        TextView label=dialog.findViewById(R.id.tv_take_photo);
        label.setText("密码");
        TextView add=dialog.findViewById(R.id.add);
        add.setText("确定");
        EditText et2=dialog.findViewById(R.id.et2);
        et2.setVisibility(View.GONE);
        EditText et3=dialog.findViewById(R.id.et3);
        et3.setVisibility(View.GONE);


        Encrypt.initUtil();
        String key = Encrypt.getSecretKey();
//需要密钥才能解密字符串
        String IvParameter = Encrypt.getIvParameter();
        Log.e("Encrypt.key==",key );
        Log.e("Encrypt.IvParameter==",IvParameter );
        key="WISOT3D70cwvt1hQ";
        IvParameter="trdRc6KKZ3XBywGs";
        Encrypt.setSecretKey(key);
        Encrypt.setIvParameter(IvParameter);
        String ss = Encrypt.encrypt(new Date().getTime());
        System.out.println("Encrypt.testss11=="+ss );
        Log.e("Encrypt.testss11==",ss );



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                str=et1.getText().toString();
               String pwd= MyApplication1.getSp().get("pwd");
               if(pwd.equals(str.trim())){
                   dialog.dismiss();
               }else {
                   Toast.makeText(context,"密码不正确，请修改后重试",Toast.LENGTH_SHORT).show();
               }
                onClick.onDo(str);

            }
        });

    }
}