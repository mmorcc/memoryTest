package com.org.myapplication.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.org.myapplication.R;
import com.org.myapplication.bean.Calling;
import com.org.myapplication.bean.CallingType;
import com.org.myapplication.bean.MyCall;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.SharedPreferencesUtils;
import com.org.myapplication.utils.ShowBottomEditDialog;
import com.org.myapplication.utils.Util;

import java.util.List;

public class Adatapter extends BaseAdapter {
    private List<CallingType> data;
    private Context c;
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局 /*构造函数*/
    private Calling myCalling=null;
    SharedPreferencesUtils sp= SharedPreferencesUtils.getInstance("isLogin");
    public Adatapter(Context context,List<CallingType> d) {
        this.mInflater = LayoutInflater.from(context);
        c=context;
        data=d;
    }
    public void setCalling(Calling myCalling){
        this.myCalling= myCalling;
    }

    @Override
    public int getCount() {

        return data.size();//返回数组的长度
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /*书中详细解释该方法*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //观察convertView随ListView滚动情况

        Log.v("MyListViewBase", "getView " + position + " " + convertView);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_call_item,null);
            holder = new ViewHolder();
            /*得到各个控件的对象*/
            holder.dName =  convertView.findViewById(R.id.dName);
            holder.dLove =  convertView.findViewById(R.id.dLove);
            holder.img =  convertView.findViewById(R.id.img2);
            holder.ll =  convertView.findViewById(R.id.ll);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        holder.dLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if(position ==2
        ){
            holder.img.setImageResource(R.drawable.c2);
        }else if(position ==3||position ==6||position ==14){
            holder.img.setImageResource(R.drawable.c3);
        }else if(  position ==4||
                position ==15){
            holder.img.setImageResource(R.drawable.c4);
        }else  if(  position ==0){
            holder.img.setImageBitmap(null);
        }else {
            holder.img.setImageResource(R.drawable.c1);
        }

        /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.dName.setText(Util.getStr(data.get(position).getName()));
        holder.dLove.setText(Util.getStr(data.get(position).getValue()));
        holder.dName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowBottomEditDialog showBottomEditDialog=new ShowBottomEditDialog();
                ShowBottomEditDialog.str=  holder.dName.getText().toString();
                showBottomEditDialog.BottomDialog(c, new CallBack() {
                    @Override
                    public void onDo(Object o) {
                        String str= (String) o;
                        if(str!=null){
                            data.get(position).setName(str);
                            notifyDataSetChanged();
                            sp.put("lname"+position,str);
                        }

                    }
                });
            }
        });;
        holder.dLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowBottomEditDialog showBottomEditDialog=new ShowBottomEditDialog();
                ShowBottomEditDialog.str=  holder.dLove.getText().toString();
                showBottomEditDialog.BottomDialog(c, new CallBack() {
                    @Override
                    public void onDo(Object o) {
                        String str= (String) o;
                        if(str!=null){
                            data.get(position).setValue(str);
                            notifyDataSetChanged();
                            sp.put("lvalue"+position,str);
                        }

                    }
                });
            }
        });
        return convertView;
    }
    /*存放控件*/
    public final class ViewHolder{
        public TextView dName;
        public  TextView dLove;
        public ImageView img;
        public LinearLayout ll;
    }
}
