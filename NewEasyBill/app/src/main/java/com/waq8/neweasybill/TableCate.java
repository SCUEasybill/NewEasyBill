package com.waq8.neweasybill;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by anquan on 2015/11/11.
 */
public class TableCate {
    public static final String table_name = "cate";
    private static final int attr_num = 5;
    public int cate_id;
    public String name;
    public int src_id;
    public int is_on = 0;
    public String remark;

    public TableCate() {
    }

    public TableCate(int cate_id, String name, int src_id, int is_on, String remark) {
        this.cate_id = cate_id;
        this.name = name;
        this.src_id = src_id;
        this.is_on = is_on;
        this.remark = remark;
    }

    public static Object[] getData(TableCate obj){
        return new Object[]{obj.cate_id, obj.name, obj.src_id, obj.is_on, obj.remark};
    }

}
