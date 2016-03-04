package com.waq8.graph;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.waq8.neweasybill.R;


/**
 * Created by guyu on 2016/2/28.
 */
public class GraphMain extends AppCompatActivity {

    public static void activityStart(Context context) {
        Intent intent = new Intent(context, GraphMain.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_main);
        //选择日期
        TextView tvCalender = (TextView) findViewById(R.id.tvCalender);
        tvCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出日期选择框
                showCalender();
            }

        });
    }
    private void showCalender() {

    }
    /*
    option menu 操作======[开始]
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toolbar_graph) {;
            Toast.makeText(this, "点击支出事件", Toast.LENGTH_SHORT).show();
            View v = findViewById(R.id.action_toolbar_graph);//v代表在哪个控件下面弹出菜单
            showPopView(v);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
    popmenu menu 操作======[开始]
     */
        private void showPopView(View v) {
            PopupMenu popup = new PopupMenu(GraphMain.this, v, Gravity.CENTER_HORIZONTAL);

            popup.getMenuInflater().inflate(R.menu.menu_graph_io, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_graph_o:
                            Toast.makeText(GraphMain.this, "支出事件", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.menu_graph_i:
                            Toast.makeText(GraphMain.this, "收入事件", Toast.LENGTH_SHORT).show();
                            return true;
                        case R.id.menu_graph_b:
                            Toast.makeText(GraphMain.this,"预算事件",Toast.LENGTH_LONG).show();
                            return true;
                    }
                    return false;
                }
            });
            popup.show();
        }


}
