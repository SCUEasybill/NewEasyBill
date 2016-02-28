package com.waq8.neweasybill;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class CateActivity extends AppCompatActivity {
    /**
     * author anmai
     * @param context
     */
    public static void activityStart(Context context) {
        Intent intent = new Intent(context, CateActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_cate);

//        actionBar.setDisplayShowTitleEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cate, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toolbar_cate) {
            Toast.makeText(this,"点击菜单事件",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
