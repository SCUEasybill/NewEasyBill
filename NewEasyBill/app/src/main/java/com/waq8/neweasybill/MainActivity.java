package com.waq8.neweasybill;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.waq8.login.Login;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private PieChart mChart;

    private DatabaseManager  mgr;
    private ForActivityData dataManager;

    private ImageView item_1;
    private ImageView item_2;
    private ImageView item_3;
    private ImageView item_4;
    private ImageView item_5;
    private ImageView item_6;
    private ImageView item_7;
    private ImageView item_8;
    private ImageView item_9;
    private ImageView item_10;
    private ImageView item_11;
    private ImageView item_12;

    private Button btn_balance;
    private ImageView btn_sub;
    private ImageView btn_add;

    private int showType = 3;   //确定显示年或月或日；1表示年,2表示月,3表示日

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //navigation
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //nav-header-main
        ImageView imageView_header = (ImageView)findViewById(R.id.imageView);
        imageView_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        //piechart
        mChart = (PieChart) findViewById(R.id.spread_pie_chart);
        PieData mPieData = getPieData(4, 100);
        showChart(mChart, mPieData);

        //数据库
        mgr = new DatabaseManager(this);
        dataManager = new ForActivityData(this, mgr);

        //imageview
        item_1 = (ImageView) findViewById(R.id.main_item_1);
        item_2 = (ImageView) findViewById(R.id.main_item_2);
        item_3 = (ImageView) findViewById(R.id.main_item_3);
        item_4 = (ImageView) findViewById(R.id.main_item_4);
        item_5 = (ImageView) findViewById(R.id.main_item_5);
        item_6 = (ImageView) findViewById(R.id.main_item_6);
        item_7 = (ImageView) findViewById(R.id.main_item_7);
        item_8 = (ImageView) findViewById(R.id.main_item_8);
        item_9 = (ImageView) findViewById(R.id.main_item_9);
        item_10 = (ImageView) findViewById(R.id.main_item_10);
        item_11 = (ImageView) findViewById(R.id.main_item_11);
        item_12 = (ImageView) findViewById(R.id.main_item_12);
        item_1.setOnClickListener(new MyClickListener());
        item_2.setOnClickListener(new MyClickListener());
        item_3.setOnClickListener(new MyClickListener());
        item_4.setOnClickListener(new MyClickListener());
        item_5.setOnClickListener(new MyClickListener());
        item_6.setOnClickListener(new MyClickListener());
        item_7.setOnClickListener(new MyClickListener());
        item_8.setOnClickListener(new MyClickListener());
        item_9.setOnClickListener(new MyClickListener());
        item_10.setOnClickListener(new MyClickListener());
        item_11.setOnClickListener(new MyClickListener());
        item_12.setOnClickListener(new MyClickListener());

        btn_balance = (Button) findViewById(R.id.balance);
        btn_sub = (ImageView) findViewById(R.id.sub_button);
        btn_add = (ImageView) findViewById(R.id.add_button);
        btn_balance.setOnClickListener(new MyClickListener());
        btn_add.setOnClickListener(new MyClickListener());
        btn_sub.setOnClickListener(new MyClickListener());

        dataManager.insertDefaultCate();    //将首页上默认的12个选项存入数据库
    }

    protected void onDestroy(){
        super.onDestroy();
        mgr.closeDB();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id){
            case R.id.action_main_day: {
//                Toast.makeText(this,"日",Toast.LENGTH_SHORT).show();
                mChart.setCenterText(new Date().toString());
                return true;
            }
            case R.id.action_main_month: {
//                Toast.makeText(this,"月",Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.action_main_year: {
//                Toast.makeText(this,"年",Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bill) {
            BillActivity.activityStart(this);
        } else if (id == R.id.nav_category) {
            CateActivity.activityStart(this);
        } else if (id == R.id.nav_setting) {
            SettActivity.activityStart(this);
        } else if (id == R.id.nav_dream) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * author anmai
     * @param pieChart
     * @param pieData
     */
    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(55f);  //半径
        pieChart.setTransparentCircleRadius(60f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

//        pieChart.setDescription("测试饼状图");
        pieChart.setDescription(null);

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("Quarterly Revenue");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setEnabled(false);
//        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
//        mLegend.setXEntrySpace(7f);
//        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     *author anmai
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        for (int i = 0; i < count; i++) {
            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        }

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = 14;
        float quarterly2 = 14;
        float quarterly3 = 34;
        float quarterly4 = 38;

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));
        yValues.add(new Entry(quarterly4, 3));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "Quarterly Revenue 2014"/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(205, 205, 205));
        colors.add(Color.rgb(114, 188, 223));
        colors.add(Color.rgb(255, 123, 124));
        colors.add(Color.rgb(57, 135, 200));

        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        float px = 5 * (metrics.densityDpi / 160f);
        float px = 3 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }

    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_item_1:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_2:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_3:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_4:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_5:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_6:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_7:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_8:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_9:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_10:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_11:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.main_item_12:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.balance:
                    BillActivity.activityStart(MainActivity.this);
                    break;
                case R.id.add_button:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                case R.id.sub_button:
                    AddBillActivity.activityStart(MainActivity.this, v.getId());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mgr.closeDB();
    }
}
