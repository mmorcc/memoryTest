package com.org.myapplication.utils;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.org.myapplication.MyApplication;
import com.org.myapplication.R;
import com.org.myapplication.activity.SelectActivity;
import com.org.myapplication.bean.Calling;
import com.org.myapplication.bean.CallingType;
import com.org.myapplication.bean.CallingTypeBean;
import com.org.myapplication.bean.Device;
import com.org.myapplication.bean.Good;
import com.org.myapplication.bean.GoodType;
import com.org.myapplication.bean.MyBoolean;
import com.org.myapplication.bean.MyDevice;
import com.org.myapplication.compenents.MyApplication1;
import com.org.myapplication.net.Api;
import com.org.myapplication.net.Data;
import com.org.myapplication.net.OnBack;
import com.org.myapplication.net.getBike;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Util {
    public static String TOKEN="";
    public static int USER_ID=0;

    private static final String TAG = "UTIL";
    public static long userId=0;
    public static String sign="";
    public static String userName="";



    public static Set<String> select=new HashSet<>();
    public static Set<String> uSelect=new HashSet<>();


    public static  List<GoodType> types=new ArrayList<>();

    public static String name="";
    public static long startTime=0;
    public static long endTime=0;
    public static GoodType type;
    public static boolean isFirst=true;//是否练习阶段
    public static boolean isRetry5Word=false;

    public static boolean isBefore64=true;

    public static boolean isRight(){

        Iterator<String> iter2 = select.iterator();
        while (iter2.hasNext()) {
            String i=iter2.next();
            Util.l("isRight  ss="+i+" name="+i);
        }
        Util.l("isRight  iter2 s="+select.size());


        Iterator<String> iter = uSelect.iterator();
        while (iter.hasNext()){
            String i=iter.next();
            Util.l("isRight uselect="+i+" name="+i);
            if (!select.contains(i)){
                return false;
            }
        }
        return true;
    }

    /**
     * 示例，get加载Json数据
     */
    public static void getBike(OnBack back) {

    }

    /**
     * 示例，get加载Json数据
     */
    public static void sendLoc(Context c, double x, double y) {

    }
    /**
     * 示例，get加载Json数据
     */
    public static String getStr(String s) {
        return  s==null?"":s;
    }
    public static void setText(TextView text, String key ,SharedPreferencesUtils sp){
        if(!TextUtils.isEmpty(sp.get(key))){
            text.setText(sp.get(key));
        }
        Util.l("names="+sp.get(key));

    }
    public static final String FILE_NAME=File.separator+"Download/goodLog2.2.txt";

    public static void writeFileToLocalStorage(String content) {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILE_NAME);

            FileWriter writer = new FileWriter(file, true);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(!TextUtils.isEmpty(content)){
                content=sdf.format(new Date())+":"+content+"\n";
            }else {
                content+="\n";
            }

            writer.append(content);
            writer.flush();
            writer.close();
            // 文件写入成功
        } catch (IOException e) {
            e.printStackTrace();
            // 文件写入发生错误
        }
    }
    public  static Vibrator myVibrator = (Vibrator)MyApplication1.get(). getSystemService(Service.VIBRATOR_SERVICE);
    /**
     * 示例，get加载Json数据
     */
    public static void zhendong() {
        myVibrator.cancel();
        myVibrator.vibrate(100);
    }
    public static void l(Object msg){
        Log.e("日志","消息="+String.valueOf(msg));
    }
    public static void showErr(Context context,Object msg){
        Toast.makeText(context,msg==null?"":String.valueOf(msg),Toast.LENGTH_LONG).show();
    }


    /**
     * 示例，get加载Json数据
     */
    public static void getAllCallTypes(OnBack back) {
    }


    /**
     * 示例，get加载Json数据
     */
    public static void getDeviceById(String deviceId,OnBack back) {
    }

    /**
     * 示例，get加载Json数据
     */
    public static void updateCalling(Calling calling, OnBack back) {
    }
    /**
     * 示例，get加载Json数据
     */
    public static void updateDevice(Device device,OnBack back) {
    }

    public static String getUUID() {
        String deviceId=MyApplication1.getSp().get("deviceId");
        if(deviceId.equals("")){
            String DEVICE_ID=UUID.randomUUID().toString();
            Util.l("devicid="+DEVICE_ID);
            MyApplication1.getSp().put("deviceId",DEVICE_ID);
        }
        return deviceId;
    }



    public static List<Good> data=new ArrayList<>();
    public static List<String> words=new ArrayList<>();
    public static List<String> words2=new ArrayList<>();
    public static Good selectGood=null;
    public static List<Good> needGoods=new ArrayList<>();
    public static List<Good> uSelGoods=new ArrayList<>();

    public static void initData() {
        data.clear();
        select.clear();
        uSelect.clear();
        words.clear();
        words2.clear();
        isFirst=true;
        isRetry5Word=false;
        isBefore64=getIndex(10)>5;

        types.clear();
//2.1 测试阶段练1次 包用4  2.2测试阶段练2次 包名5  log名不一样  包名不一样 app名也不一样
        types.add(new GoodType(0,2));
        types.add(new GoodType(1,2));
        types.add(new GoodType(2,2));

        words.add("鼻子");
        words.add("面孔");
        words.add("手掌");
        words.add("棉布");
        words.add("的确良");
        words.add("天鹅绒");
        words.add("教堂");
        words.add("学校");
        words.add("医院");
        words.add("红色");
        words.add("绿色");
        words.add("蓝色");
        words.add("玫瑰");
        words.add("菊花");
        words.add("牡丹");

        words2.add("水杯");
        words2.add("天鹅绒");
        words2.add("三角形");
        words2.add("天空");
        words2.add("手表");
        words2.add("教堂");
        words2.add("小狗");
        words2.add("菊花");
        words2.add("尺子");
        words2.add("面孔");
        words2.add("火车");
        words2.add("土豆");
        words2.add("电池");
        words2.add("音乐");
        words2.add("红色");
        addGood("苹果", R.drawable.pingguo, Const.shipin,Const.shengxian,Const.shuiguo);
        addGood("西瓜",R.drawable.xigua, Const.shipin,Const.shengxian,Const.shuiguo);
        addGood("香蕉",R.drawable.xiangjiao, Const.shipin,Const.shengxian,Const.shuiguo);
        addGood("橙子",R.drawable.chengzi, Const.shipin,Const.shengxian,Const.shuiguo);
        addGood("草莓",R.drawable.caomei, Const.shipin,Const.shengxian,Const.shuiguo);//
        addGood("葡萄",R.drawable.putao, Const.shipin,Const.shengxian,Const.shuiguo);
        addGood("芒果",R.drawable.mangguo, Const.shipin,Const.shengxian,Const.shuiguo);
        addGood("桃子",R.drawable.taozi, Const.shipin,Const.shengxian,Const.shuiguo);


        addGood("菠菜",R.drawable.bocai, Const.shipin,Const.shengxian,Const.shucai);
        addGood("青椒",R.drawable.qingjiao, Const.shipin,Const.shengxian,Const.shucai);
        addGood("娃娃菜",R.drawable.wawacai, Const.shipin,Const.shengxian,Const.shucai);
        addGood("豆芽",R.drawable.douya, Const.shipin,Const.shengxian,Const.shucai);
        addGood("茄子",R.drawable.qiezi, Const.shipin,Const.shengxian,Const.shucai);
        addGood("油菜",R.drawable.youcai, Const.shipin,Const.shengxian,Const.shucai);
        addGood("胡萝卜",R.drawable.huluobo, Const.shipin,Const.shengxian,Const.shucai);
        addGood("豆角",R.drawable.doujiao, Const.shipin,Const.shengxian,Const.shucai);


        addGood("玉米",R.drawable.yumi, Const.shipin,Const.liangyou,Const.wuguzaliang);
        addGood("红豆",R.drawable.hongdou, Const.shipin,Const.liangyou,Const.wuguzaliang);
        addGood("小米",R.drawable.xiaomi, Const.shipin,Const.liangyou,Const.wuguzaliang);
        addGood("花生米",R.drawable.huashengmi, Const.shipin,Const.liangyou,Const.wuguzaliang);
        addGood("黄豆",R.drawable.huangdou, Const.shipin,Const.liangyou,Const.wuguzaliang);
        addGood("黑芝麻",R.drawable.heizhima, Const.shipin,Const.liangyou,Const.wuguzaliang);
        addGood("大米",R.drawable.dami, Const.shipin,Const.liangyou,Const.wuguzaliang);
        addGood("绿豆",R.drawable.lvdou, Const.shipin,Const.liangyou,Const.wuguzaliang);

        addGood("蚝油",R.drawable.haoyou, Const.shipin,Const.liangyou,Const.tiaoliaojiangzhi);
        addGood("食盐",R.drawable.lajiao, Const.shipin,Const.liangyou,Const.tiaoliaojiangzhi);
        addGood("八角",R.drawable.bajiao, Const.shipin,Const.liangyou,Const.tiaoliaojiangzhi);
        addGood("花生油",R.drawable.huashengyou, Const.shipin,Const.liangyou,Const.tiaoliaojiangzhi);
        addGood("花椒",R.drawable.huajiao, Const.shipin,Const.liangyou,Const.tiaoliaojiangzhi);
        addGood("豆瓣酱",R.drawable.doubanjiang, Const.shipin,Const.liangyou,Const.tiaoliaojiangzhi);
        addGood("鸡精",R.drawable.jijing, Const.shipin,Const.liangyou,Const.tiaoliaojiangzhi);
        addGood("陈醋",R.drawable.chencu, Const.shipin,Const.liangyou,Const.tiaoliaojiangzhi);

//2 0
        addGood("拖鞋",R.drawable.tuoxie, Const.baihuo,Const.riyongbangong,Const.jiajuriyong);
        addGood("雨伞",R.drawable.yusang, Const.baihuo,Const.riyongbangong,Const.jiajuriyong);
        addGood("口罩",R.drawable.kouzhao, Const.baihuo,Const.riyongbangong,Const.jiajuriyong);
        addGood("插排",R.drawable.chapai, Const.baihuo,Const.riyongbangong,Const.jiajuriyong);
        addGood("指甲刀",R.drawable.zhijiadao, Const.baihuo,Const.riyongbangong,Const.jiajuriyong);
        addGood("打火机",R.drawable.dahuoji, Const.baihuo,Const.riyongbangong,Const.jiajuriyong);
        addGood("花瓶",R.drawable.huaping, Const.baihuo,Const.riyongbangong,Const.jiajuriyong);
        addGood("衣架",R.drawable.yijia, Const.baihuo,Const.riyongbangong,Const.jiajuriyong);

        addGood("笔记本",R.drawable.bijiben, Const.baihuo,Const.riyongbangong,Const.bangongwenju);
        addGood("文件夹",R.drawable.wenjianjia, Const.baihuo,Const.riyongbangong,Const.bangongwenju);
        addGood("订书机",R.drawable.dingshuji, Const.baihuo,Const.riyongbangong,Const.bangongwenju);
        addGood("钢笔",R.drawable.gangbi, Const.baihuo,Const.riyongbangong,Const.bangongwenju);
        addGood("铅笔",R.drawable.qianbi, Const.baihuo,Const.riyongbangong,Const.bangongwenju);
        addGood("笔筒",R.drawable.bitong, Const.baihuo,Const.riyongbangong,Const.bangongwenju);
        addGood("文具盒",R.drawable.wenjuhe, Const.baihuo,Const.riyongbangong,Const.bangongwenju);
        addGood("墨水",R.drawable.moshou, Const.baihuo,Const.riyongbangong,Const.bangongwenju);


        addGood("平底锅",R.drawable.pingdiguo, Const.baihuo,Const.chufangweiyu,Const.chufangcanju);
        addGood("砂锅",R.drawable.shaguo, Const.baihuo,Const.chufangweiyu,Const.chufangcanju);
        addGood("菜刀",R.drawable.caidao, Const.baihuo,Const.chufangweiyu,Const.chufangcanju);//
        addGood("筷子",R.drawable.kuaizi, Const.baihuo,Const.chufangweiyu,Const.chufangcanju);
        addGood("勺子",R.drawable.shaozi, Const.baihuo,Const.chufangweiyu,Const.chufangcanju);
        addGood("漏勺",R.drawable.loushao, Const.baihuo,Const.chufangweiyu,Const.chufangcanju);
        addGood("碟子",R.drawable.diezi, Const.baihuo,Const.chufangweiyu,Const.chufangcanju);
        addGood("调料瓶",R.drawable.tiaoliao, Const.baihuo,Const.chufangweiyu,Const.chufangcanju);


        addGood("洗发水",R.drawable.xifashui, Const.baihuo,Const.chufangweiyu,Const.xihuyongping);
        addGood("牙膏",R.drawable.yagao, Const.baihuo,Const.chufangweiyu,Const.xihuyongping);
        addGood("牙刷",R.drawable.yashua, Const.baihuo,Const.chufangweiyu,Const.xihuyongping);
        addGood("肥皂",R.drawable.feizao, Const.baihuo,Const.chufangweiyu,Const.xihuyongping);
        addGood("洗手液",R.drawable.xishouye, Const.baihuo,Const.chufangweiyu,Const.xihuyongping);
        addGood("护手霜",R.drawable.hushoushuang, Const.baihuo,Const.chufangweiyu,Const.xihuyongping);//
        addGood("沐浴露",R.drawable.muyulu, Const.baihuo,Const.chufangweiyu,Const.xihuyongping);
        addGood("洗面奶",R.drawable.ximiannai, Const.baihuo,Const.chufangweiyu,Const.xihuyongping);
    }

    public static void addGood(String name, int pingdiguo, String shipin, String shengxian, String shuiguo) {
        Good good=new Good(name,pingdiguo, shipin,shengxian,shuiguo);
        good.setIndex(data.size());
        data.add(good);
    }
    public static Random random=new Random();
    public static int getIndex(int max){
        int i=random.nextInt(max);
        Util.l("random="+i+" max="+max+ "  select.s="+select.size());
       return i;
    }
    public static List<Good> getRandomGood(){
        List<Good> randomData=new ArrayList<>();
        List<Good> all=new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            all.add(data.get(i));
        }
        for (int i = 0; i < all.size(); i=0) {
            int index=getIndex(all.size());
            randomData.add(all.get(index));
            all.remove(index);
        }
        return randomData;

    }

    public static List<String> getRandomWord(){
        List<String> randomData=new ArrayList<>();
        List<String> all=new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            all.add(words.get(i));
        }
        int j= 0;
        for (int i = 0; i < all.size(); i=0) {
            j++;
            int index=getIndex(all.size());
            randomData.add(all.get(index));
            all.remove(index);
        }
        Util.l("random si="+randomData.size()+" i="+j+" all.s="+words.size());
        return randomData;

    }
    public static List<String> getRandomWord2(){
        List<String> randomData=new ArrayList<>();
        List<String> all=new ArrayList<>();
        for (int i = 0; i < words2.size(); i++) {
            all.add(words2.get(i));
        }
        int j= 0;
        for (int i = 0; i < all.size(); i=0) {
            j++;
            int index=getIndex(all.size());
            randomData.add(all.get(index));
            all.remove(index);
        }
        Util.l("random si="+randomData.size()+" i="+j+" all.s="+words.size());
        return randomData;

    }

    public static List<Good> getRandomGood8(String type){
        List<Good> randomData=new ArrayList<>();
        List<Good> all=new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getType3().equals(type)||
                    data.get(i).getType2().equals(type)||
                    data.get(i).getType().equals(type)
            ){
                all.add(data.get(i));
            }
        }
        for (int i = 0; i < all.size(); i=0) {
            int index=getIndex(all.size());
            randomData.add(all.get(index));
            all.remove(index);
        }
        return randomData;
    }
    public static List<Good> getRandomGood2(String type){
        List<Good> randomData=new ArrayList<>();
        List<Good> all=new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getType2().equals(type)){
                all.add(data.get(i));
            }
        }
        for (int i = 0; i < all.size(); i=0) {
            int index=getIndex(all.size());
            randomData.add(all.get(index));
            all.remove(index);
        }
        return randomData;
    }
    public static String getDuring(long startTime, long endTime) {
        long dur=0;
        String re="";
        if(startTime==0||endTime==0){
            return "";
        }else {
             dur=endTime-startTime;
             Util.l("dur="+dur);
            long s = (dur / 1000 );
            re=+ s+"秒 ";
        }
        return re;
    }

    public static ShowBottomEditDialog showBottomEditDialog=new ShowBottomEditDialog();
    public static void showInfo(Context context, String content,CallBack cb) {
        ShowBottomEditDialog.str=content;
        showBottomEditDialog.BottomDialog(context,cb);
    }
    public static void showInfo(Context context, String content,String btText,CallBack cb) {
        ShowBottomEditDialog.str=content;
        ShowBottomEditDialog.btText=btText;
        showBottomEditDialog.BottomDialog(context,cb);
    }

    public static GoodType getType() {
        int index=getIndex(types.size());
         type= types.get(index);
        return type;
    }
    public static String readFileToLocalStorage() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILE_NAME);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb=new StringBuilder();
            String lineTxt = null;
            while ((lineTxt = br.readLine()) != null) {  //
                if (!"".equals(lineTxt)) {
                    sb.append(lineTxt+"\n");
                }
            }
            isr.close();
            br.close();
            return sb.toString();
            // 文件写入成功
        } catch (IOException e) {
            e.printStackTrace();
            // 文件写入发生错误
        }
        return "";
    }

    public static String getPercent() {
        String re="";
        int g=0;
        Iterator<String> iter2 = select.iterator();
        while (iter2.hasNext()) {
            String i=iter2.next();
            if( uSelect.contains(i)){
                g++;
            }
            Util.l("isRight  ss="+i+" name="+i+"  iscontain="+ uSelect.contains(i));
        }
        Util.l("isRight  iter2 s="+select.size()+ " g="+g);

        double percentage = (double)g /5 *100;
        Util.l("isRight  iter2 s="+select.size()+ " g="+g+"  per="+percentage);
        return percentage+"%";
    }

    public static String getSelect() {
        String re="";
        Iterator<String> iter = uSelect.iterator();
        while (iter.hasNext()){
            String i=iter.next();
           re+=i;
        }
        return re;
    }

    public static boolean isSelectGoodOk() {
        if(uSelGoods.size()<3){
            return false;
        }
        print(needGoods);
        l("uSelGoods:");
        print(uSelGoods);

        for (int i = 0; i < needGoods.size(); i++) {
            int j = 0;
            for (; j < uSelGoods.size(); j++) {
                if(uSelGoods.get(j).getIndex()==needGoods.get(i).getIndex()){
                    break;
                }
            }
            if(j==3){
                return false;
            }
        }
        return true;
    }

    private static void print(List<Good> needGoods) {
        for (int i = 0; i < needGoods.size(); i++) {
            l(needGoods.get(i).getName());
        }

    }

    public static boolean isSelectedGood(Good good) {
        for ( int j = 0; j < uSelGoods.size(); j++) {
            if(uSelGoods.get(j).getIndex()==good.getIndex()){
                return true;
            }
        }
        return false;
    }

    public static void delUSelGood(Good good) {
        int j = 0;
        for (; j < uSelGoods.size(); j++) {
            if(uSelGoods.get(j).getIndex()==good.getIndex()){
                uSelGoods.remove(j);
                break;
            }
        }
    }

    /**
     * 取需要选择的商品
     * @return
     */
    public static String getNeedSelectGood() {
        String str="";
        int j = 0;
        for (; j < needGoods.size(); j++) {
            str+=needGoods.get(j).getName()+"、";
        }
        if(str.length()>0){
            str=str.substring(0,str.length()-1);
        }

        return str;
    }
    /**
     * 取用户选择的商品
     * @return
     */
    public static String getUSelect() {
        String str="";
        int j = 0;
        for (; j < uSelGoods.size(); j++) {
           str+=uSelGoods.get(j).getName()+"、";
        }
        if(str.length()>0){
            str=str.substring(0,str.length()-1);
        }
        l("用户选择="+str);
            return str;
    }

    public static String getErrSelect() {
        String str="";
        for (int i = 0; i < uSelGoods.size(); i++) {
            int j = 0;
            for (; j < needGoods.size(); j++) {
                if(uSelGoods.get(i).getIndex()==needGoods.get(j).getIndex()){
                    break;
                }
            }
            if(j==3){
                str+=uSelGoods.get(i).getName()+"、";
            }
        }
        if(str.length()>0){
            str=str.substring(0,str.length()-1);
        }
        return str;
    }
}
