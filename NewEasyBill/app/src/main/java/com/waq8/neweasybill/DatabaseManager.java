package com.waq8.neweasybill;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DatabaseManager {
    private DatabaseHelper helper;
    public SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context context) {
        helper = new DatabaseHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
        this.context = context;
    }

    public void add(List<Object> objs, String table_name) {
        db.beginTransaction();	//开始事务
        try {
            for (Object obj : objs) {
                if(table_name.equals("bill"))
                    db.execSQL("INSERT INTO "+ table_name+" VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?)",TableBill.getData((TableBill)obj));
                else if(table_name.equals("cate"))
                    db.execSQL("INSERT INTO "+ table_name+" VALUES(null, ?, ?, ?, ?, ?)",TableCate.getData((TableCate)obj));
            }
            Toast.makeText(context,table_name+"->添加成功", Toast.LENGTH_SHORT).show();
            db.setTransactionSuccessful();	//设置事务成功完成
        } finally {
            db.endTransaction();	//结束事务
        }
    }

    public List<Object> query(String table_name) {
        ArrayList<Object> objs = new ArrayList<Object>();
        db.beginTransaction();
        try{
            Cursor c = db.rawQuery("select * from "+table_name, null);
            while (c.moveToNext())
            {
                if(table_name.equals("bill")) {
                    TableBill bill = new TableBill();
                    bill.who_id = c.getInt(c.getColumnIndex("who_id"));
                    bill.cate_id = c.getInt(c.getColumnIndex("cate_id"));
                    objs.add(bill);
                }
            }
            Toast.makeText(context, "查询成功", Toast.LENGTH_SHORT).show();
            c.close();
        }finally {
            db.endTransaction();
        }
        return objs;
    }

    public void todo(String sql){
        db.execSQL(sql);
    }

    public Cursor todoQuery(String sql) {
        Cursor c = db.rawQuery(sql, null);
        Toast.makeText(context,"数据表添加成功", Toast.LENGTH_SHORT).show();
        return c;
    }
    public void closeDB() {
        db.close();
    }

}