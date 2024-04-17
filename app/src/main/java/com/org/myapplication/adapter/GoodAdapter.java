package com.org.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.org.myapplication.R;
import com.org.myapplication.bean.Calling;
import com.org.myapplication.bean.Good;
import com.org.myapplication.utils.CallBack;
import com.org.myapplication.utils.SharedPreferencesUtils;
import com.org.myapplication.utils.ShowBottomEditDialog;
import com.org.myapplication.utils.Util;

import java.util.List;

public class GoodAdapter extends BaseAdapter {
    private List<Good> data;
    private Context c;
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局 /*构造函数*/
    SharedPreferencesUtils sp= SharedPreferencesUtils.getInstance("isLogin");
    public GoodAdapter(Context context, List<Good> d) {
        this.mInflater = LayoutInflater.from(context);
        c=context;
        data=d;
    }

    @Override
    public int getCount() {

        return data.size();//返回数组的长度
    }

    @Override
    public Good getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.list_good_item,null);
            holder = new ViewHolder();
            /*得到各个控件的对象*/
            holder.name =  convertView.findViewById(R.id.name);
            holder.img =  convertView.findViewById(R.id.img);
            holder.delete =  convertView.findViewById(R.id.delete);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.delUSelGood(data.get(position));
                data.remove(position);
                notifyDataSetChanged();
                Util.type.setcTimes(Util.type.cTimes+1);
            }
        });
        holder.img.setImageResource(data.get(position).getId());
        /*设置TextView显示的内容，即我们存放在动态数组中的数据*/
        holder.name.setText(Util.getStr(data.get(position).getName()));

        return convertView;
    }
    /*存放控件*/
    public final class ViewHolder{
        public TextView name;
        public ImageView img;
        public Button delete;
    }
}
