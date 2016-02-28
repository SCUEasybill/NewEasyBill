package com.waq8.neweasybill;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AddBillActivity extends AppCompatActivity {
    public static void activityStart(Context context) {
        Intent intent = new Intent(context, AddBillActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void activityStart(Context context, int rId) {
        Intent intent = new Intent(context, AddBillActivity.class);
        intent.putExtra("rId", rId);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private DatabaseManager  mgr;
    private ForActivityData dataManager;

    private int Rid;
    private TableBill tableBill = new TableBill();

    private EditText edit_date;
    private Button btn_pic_date;
    private Spinner spinner_cate;
    private ToggleButton toggleButton;
    private EditText ediPiecePrice;
    private EditText ediNum;
    private EditText ediTatolPrice;
    private EditText ediRemark;
    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        mgr = new DatabaseManager(this);
        dataManager = new ForActivityData(AddBillActivity.this, mgr);

        Intent intent = getIntent();
        Rid = intent.getIntExtra("rId",0);

        //日期确定
        edit_date = (EditText)findViewById(R.id.edit_pick_date);
        btn_pic_date = (Button)findViewById(R.id.btn_add_date);
        btn_pic_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(AddBillActivity.this,
                        new OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker dp, int year, int mounth, int day) {
                                edit_date.setText(year + "年" + (mounth + 1) + "月" + day + "日");
                                tableBill.datetime = year + "年" + (mounth + 1) + "月" + day + "日";
                            }
                        },
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //spinner
        spinner_cate = (Spinner) findViewById(R.id.spinner_add_cate);
        String[] strs_cates = dataManager.getCates();
   //     String[] strs_cates = {"nihai","sdf"};
        if(strs_cates == null){
            Toast.makeText(AddBillActivity.this, "strs_cates为空", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, strs_cates);
        spinner_cate.setAdapter(adapter);
        spinner_cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            @Override
            public void onItemSelected(AdapterView parent, View view,
                                       int position, long id) {
                String str = parent.getItemAtPosition(position).toString();
                Toast.makeText(AddBillActivity.this, "你点击的是:" + str, Toast.LENGTH_SHORT).show();
                tableBill.cate_id = dataManager.getCateIdByName(str);
            }
        });

        if(Rid != 0){
            if(Rid != R.id.sub_button && Rid != R.id.add_button){

            }
        }

        //ToggleButton
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton_add_bill);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tableBill.inorout = isChecked;
            }
        });

        //EditText
        ediPiecePrice = (EditText) findViewById(R.id.add_piece_price);
        ediNum = (EditText) findViewById(R.id.add_num);
        ediTatolPrice = (EditText) findViewById(R.id.add_total_price);
        ediRemark = (EditText) findViewById(R.id.add_remark);
        ediPiecePrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && !TextUtils.isEmpty(ediNum.getText().toString()) && !TextUtils.isEmpty(ediPiecePrice.getText().toString())) {
                    float temp = Float.valueOf(ediPiecePrice.getText().toString()) * Integer.parseInt(ediNum.getText().toString());
                    Log.e("anmai", String.valueOf(temp));
                    ediTatolPrice.setText(String.valueOf(temp));
                }

            }
        });
        ediNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && !TextUtils.isEmpty(ediNum.getText().toString()) && !TextUtils.isEmpty(ediPiecePrice.getText().toString())) {
                    float temp = Float.valueOf(ediPiecePrice.getText().toString()) * Integer.parseInt(ediNum.getText().toString());
                    Log.e("anmai", String.valueOf(temp));
                    ediTatolPrice.setText(String.valueOf(temp));
                }
            }
        });

        btn_submit = (Button) findViewById(R.id.btn_submit_bill);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(ediNum.getText().toString()) && !TextUtils.isEmpty(ediPiecePrice.getText().toString())) {
                    tableBill.who_id = 1;
                    tableBill.piece_price = Float.parseFloat(ediPiecePrice.getText().toString());
                    tableBill.num = Integer.parseInt(ediNum.getText().toString());
                    tableBill.total_price = Float.parseFloat(ediTatolPrice.getText().toString());
                    tableBill.remark = ediRemark.getText().toString();
                    for(int i=0; i<8;i++){
                        Log.e("anmai", String.valueOf(TableBill.getData(tableBill)[i]));
                    }
                    dataManager.putBilltoDb(TableBill.table_name, tableBill);
                    BillActivity.activityStart(AddBillActivity.this);
                }
                else{
                    new AlertDialog.Builder(AddBillActivity.this).setTitle("警告").setMessage("请填写空白处！！")
                            .setPositiveButton("确定", null)
                            .show();
                }
            }
        });
    }

}
