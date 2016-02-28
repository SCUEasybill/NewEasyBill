package com.waq8.neweasybill;

import android.content.Context;
import android.database.Cursor;
import android.media.session.PlaybackState;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import javax.security.auth.PrivateCredentialPermission;

/**
 * Created by anquan on 2015/11/13.
 */
public class ForActivityData {

    private Context context;
    private DatabaseManager mgr;

    public ForActivityData(Context context, DatabaseManager mgr){
        this.context = context;
        this.mgr = mgr;
    }

    public void insertDefaultCate(){
        Cursor c = mgr.todoQuery("select * from " + TableCate.table_name);
        if(c.getCount() != 0){
            Toast.makeText(context, "已经存好了的", Toast.LENGTH_SHORT).show();
        }else {
            ArrayList<Object> cates = new ArrayList<Object>();
            TableCate cate1 = new TableCate(1, "衣服", R.id.main_item_1, 1, null);
            TableCate cate2 = new TableCate(2, "学习", R.id.main_item_2, 2, null);
            TableCate cate3 = new TableCate(3, "食物", R.id.main_item_3, 3, null);
            TableCate cate4 = new TableCate(4, "住房", R.id.main_item_4, 4, null);
            TableCate cate5 = new TableCate(5, "生活", R.id.main_item_5, 5, null);
            TableCate cate6 = new TableCate(6, "通信", R.id.main_item_6, 6, null);
            TableCate cate7 = new TableCate(7, "公交", R.id.main_item_7, 7, null);
            TableCate cate8 = new TableCate(8, "运动", R.id.main_item_8, 8, null);
            TableCate cate9 = new TableCate(9, "娱乐", R.id.main_item_9, 9, null);
            TableCate cate10 = new TableCate(10, "工具", R.id.main_item_10, 10, null);
            TableCate cate11 = new TableCate(11, "硬件", R.id.main_item_11, 11, null);
            TableCate cate12 = new TableCate(12, "鞋子", R.id.main_item_12, 12, null);
            cates.add(cate1);
            cates.add(cate2);
            cates.add(cate3);
            cates.add(cate4);
            cates.add(cate5);
            cates.add(cate6);
            cates.add(cate7);
            cates.add(cate8);
            cates.add(cate9);
            cates.add(cate10);
            cates.add(cate11);
            cates.add(cate12);
            mgr.add(cates, TableCate.table_name);
        }
    }

    public String[] getCates(){
        Cursor c = mgr.todoQuery("select name from cate");
        if(c.getCount() == 0) {
            c.close();
            return null;
        }
        else {
            int lenth = c.getCount();
            Log.e("anmai", String.valueOf(c.getCount()));
            Log.e("anmai", String.valueOf(c.getColumnCount()));
            String[] strs = new String[lenth];
            for(int i = 0; i < lenth; i++){
                c.moveToNext();
                strs[i] = c.getString(0);
                Log.e("anmai", c.getString(0));
            }
            c.close();
            return strs;
        }
    }
    public int getCateIdByName(String name){
        Cursor c = mgr.todoQuery("select id from cate where name like \"" + name + "\"");
        c.moveToNext();
        return c.getInt(0);
    }

    public void putBilltoDb(String table_name, TableBill bill){
        Log.e("anmai", "put start");
        mgr.db.execSQL("insert into " + table_name + " values(null, ?, ?, ?, ?, ?, ?, ?, ?)", TableBill.getData(bill));
        Log.e("anmai", "put end");
    }

    public String[][] getBillsByData(String date) {
//        Log.e("anmai", "getBillsByData put start");
        String[][] Str = new String[20][4];
//        Log.e("anmai", "1 put start");
        Cursor c = mgr.todoQuery("select * from bill where date like \"" + date +"\"");
//        Log.e("anmai", "1 put end");
        for(int i=0; i<c.getCount(); i++){
            c.moveToNext();
//            Log.e("anmai", "2 put start");
//            Log.e("anmai", String.valueOf(c.getInt(2)));
            Cursor cc = mgr.todoQuery("select name from cate where id = \"" + c.getInt(2) +"\"");
            cc.moveToNext();
//            Log.e("anmai", String.valueOf(cc.getString(0)));
//            Log.e("anmai", "2 put end");
            Str[i][0] = String.valueOf(cc.getString(0));
            Str[i][1] = String.valueOf(c.getString(6));
            Str[i][2] = String.valueOf(c.getString(7));
            Str[i][3] = String.valueOf(c.getString(8));
        }
//        Log.e("anmai", "getBillsByData put end");
        return Str;
    }
}
