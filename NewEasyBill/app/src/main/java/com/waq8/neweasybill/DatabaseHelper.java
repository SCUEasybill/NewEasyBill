package com.waq8.neweasybill;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "easybill.db";
    private static final int VERSION = 1;
    private static final String Anmai="anmai";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }

    //创建数据库（第一次创建时调用）
    public void onCreate(SQLiteDatabase db) {
        //bill表
        db.execSQL("CREATE TABLE IF NOT EXISTS bill" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, who_id INTEGER, cate_id INTEGER, inorout BOOLEAN, piece_price FLOAT, num INTEGER, total_price FLOAT, date VARCHAR, remark TEXT)");
        //user表
        db.execSQL("CREATE TABLE IF NOT EXISTS user" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, name VARCHAR, psw VARCHAR, latest_log_time VARCHAR, remark TEXT)");
        //log表
        db.execSQL("CREATE TABLE IF NOT EXISTS log" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, name VARCHAR, remark TEXT)");
        //cate表
        db.execSQL("CREATE TABLE IF NOT EXISTS cate" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, name VARCHAR, src_id INTEGER, is_on INTEGER, remark TEXT)");
        //pic表
        db.execSQL("CREATE TABLE IF NOT EXISTS pic" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, id INTEGER, src_id INTEGER, remark TEXT)");
    }

    //更新数据库
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //创建成功，日志输出提示
        Log.i(Anmai,getClass().getName()+"->update a Database");
    }

}