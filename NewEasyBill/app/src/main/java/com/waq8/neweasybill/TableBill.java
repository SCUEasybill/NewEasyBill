package com.waq8.neweasybill;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anquan on 2015/11/11.
 */
public class TableBill {
    public static final String table_name = "bill";
    private static final int attr_num = 8;
    public int who_id;
    public int cate_id;
    public boolean inorout;
    public float piece_price;
    public int num;
    public float total_price;
    public String datetime;
    public String remark;

    public TableBill() {
    }

    public TableBill(int who_id, int cate_id, boolean inorout, float piece_price, int num, float total_price, String remark) {
        this.who_id = who_id;
        this.cate_id = cate_id;
        this.inorout = inorout; //1表示支出；2表示收入
        this.piece_price = piece_price;
        this.num = num;
        this.total_price = total_price;
        this.remark = remark;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        this.datetime = format.format(d);
    }

    public static Object[] getData(TableBill obj){
        return new Object[]{obj.who_id, obj.cate_id, obj.inorout, obj.piece_price, obj.num, obj.total_price, obj.datetime, obj.remark};
    }

}
