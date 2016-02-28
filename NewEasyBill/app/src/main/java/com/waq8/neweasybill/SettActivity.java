package com.waq8.neweasybill;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettActivity extends AppCompatActivity {
    /**
     * author anmai
     * @param context
     */
    public static void activityStart(Context context) {
        Intent intent = new Intent(context, SettActivity.class);
        context.startActivity(intent);
    }

    List<String> group;           //组列表
    List<List<String>> child;     //子列表
    SettInfoAdapter adapter;  //数据适配器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett);

        initializeData();
        adapter = new SettInfoAdapter(SettActivity.this);
        ExpandableListView eListView = (ExpandableListView)findViewById(R.id.expandableListView_sett);
        eListView.setAdapter(adapter);
        eListView.setGroupIndicator(null);
    }

    /**
     * author anmai
     */
    private  void initializeData() {
        group = new ArrayList<String>();
        child = new ArrayList<List<String>>();

        addInfo("第一行", new String[]{"子1", "子2"});
        addInfo("第二行", new String[]{"子1", "子2", "子3"});
    }

    /**
     * author anmai
     *
     * @param g-group
     * @param c-child
     */
    private void addInfo(String g, String[] c) {
        group.add(g);
        List<String> childitem = new ArrayList<String>();
        for (int i = 0; i < c.length; i++) {
            childitem.add(c[i]);
        }
        child.add(childitem);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sett, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toolbar_sett) {
            Toast.makeText(this, "点击菜单事件", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * author anmai
     */
    public class SettInfoAdapter extends BaseExpandableListAdapter {
        Activity activity;

        public SettInfoAdapter(Activity a) {
            activity = a;
        }

        public Object getChild(int groupPosition, int childPosition) {
            return child.get(groupPosition).get(childPosition);
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return child.get(groupPosition).size();
        }

        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            String string = child.get(groupPosition).get(childPosition);
            return getGenericView(string);
        }

        // group method stub
        public Object getGroup(int groupPosition) {
            return group.get(groupPosition);
        }

        public int getGroupCount() {
            return group.size();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String string = group.get(groupPosition);
            return getGenericView(string);
        }

        // View stub to create Group/Children 's View
        public TextView getGenericView(String string) {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView text = new TextView(activity);
            text.setLayoutParams(layoutParams);
            // Center the text vertically
            text.setGravity(Gravity.CENTER_VERTICAL);
            // Set the text starting position
            text.setPadding(36, 0, 0, 0);
            text.setText(string);
            return text;
        }

        public boolean hasStableIds() {
            return false;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
