package com.example.homework7.bean;

import com.example.homework7.R;

import java.util.ArrayList;

public class GoodsInfo {
    public long rowid; // 行号
    public int sn; // 序号
    public String name; // 名称
    public String desc; // 描述
    public float price; // 价格
    public String thumb_path; // 小图的保存路径
    public String pic_path; // 大图的保存路径
    public int thumb; // 小图的资源编号
    public int pic; // 大图的资源编号

    public GoodsInfo() {
        rowid = 0L;
        sn = 0;
        name = "";
        desc = "";
        price = 0;
        thumb_path = "";
        pic_path = "";
        thumb = 0;
        pic = 0;
    }

    // 声明一个手机商品的名称数组
    private static String[] mNameArray = {
            "iPhone12", "华为 mate 30", "红米 k30S", "OPPO Reno4 SE", "vivo iQOO", "魅蓝17","三星A51","一加8T"
    };
    // 声明一个手机商品的描述数组
    private static String[] mDescArray = {
            "Apple iPhone 手机国行正品官方旗舰店 iphone 12 苹果手机5G 6期免息",
            "Huawei mate 30 手机全网通新款直降p30",
            "Xiaomi Redmi k30S 至尊纪念版",
            "OPPO Reno4 SE opporeno4se手机5G 限量版",
            "vivo iQOO旗舰游戏手机旗舰官方店",
            "Meizu/魅族 魅族17旗舰骁龙865UFS3.1闪存4500毫安电池续航官方旗舰店",
            "三星Galaxy A51 5G版8GB+128G",
            "OnePlus/一加8T 5G手机赛博朋克2077官方正品"
    };
    // 声明一个手机商品的价格数组
    private static float[] mPriceArray = {6789, 3421, 3456, 4567, 2413, 2678,2299,3299};
    // 声明一个手机商品的小图数组
    private static int[] mThumbArray = {
            R.drawable.iphone_s, R.drawable.huawei_s, R.drawable.xiaomi_s,
            R.drawable.oppo_s, R.drawable.vivo_s, R.drawable.meizu_s,R.drawable.sanxing_s,R.drawable.yijia_s
    };
    // 声明一个手机商品的大图数组
    private static int[] mPicArray = {
            R.drawable.iphone, R.drawable.huawei, R.drawable.xiaomi,
            R.drawable.oppo, R.drawable.vivo, R.drawable.meizu,R.drawable.sanxing,R.drawable.yijia
    };

    // 获取默认的手机信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.desc = mDescArray[i];
            info.price = mPriceArray[i];
            info.thumb = mThumbArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}
