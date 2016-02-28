package com.waq8.neweasybill;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillActivity extends AppCompatActivity {

    /**
     * author anmai
     * @param context
     */
    public static void activityStart(Context context) {
        Intent intent = new Intent(context, BillActivity.class);
        context.startActivity(intent);
    }

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setScrollbarFadingEnabled(true);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_toolbar_bill) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return new PlaceholderFragment().newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        List<String> group;           //组列表
        List<List<String>> child;     //子列表
        List<List<Integer>> child_img;     //子列表图片
        static BillsInfoAdapter adapter;  //数据适配器
        private DatabaseManager  mgr;
        private ForActivityData dataManager;

        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_bill, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            mgr = new DatabaseManager(getContext());
            dataManager = new ForActivityData(getContext(), mgr);

            initializeData();
            adapter = new BillsInfoAdapter((Activity) getContext());

            final ExpandableListView eListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView_bill);
            eListView.setAdapter(adapter);
            eListView.setGroupIndicator(null);
            eListView.setCacheColorHint(0);

            final EditText et = (EditText) rootView.findViewById(R.id.edittext_date);
            Button btn = (Button) rootView.findViewById(R.id.button_datePicker);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker dp, int year, int mounth, int day) {
                                    et.setText(year + "年" + (mounth + 1) + "月" + day + "日");
                                    String date = year + "年" + (mounth + 1) + "月" + day + "日";
//                                    Log.e("anmai", "date = "+date);
                                    String[][] dayData = dataManager.getBillsByData(date);
                                    initializeData();
                                    for (int i = 0; i<dayData.length; i++){
                                        if(!TextUtils.isEmpty(dayData[i][0])){
//                                            Log.e("anmai", "in adapter");
                                            addInfo(dayData[i][0], new String[]{dayData[i][1], dayData[i][2], dayData[i][3]}, new Integer[]{android.R.drawable.sym_action_chat,android.R.drawable.sym_action_chat,android.R.drawable.sym_action_chat});
//                                            Log.e("anmai", dayData[i][0]);
//                                            Log.e("anmai", dayData[i][1]);
//                                            Log.e("anmai", dayData[i][2]);
//                                            Log.e("anmai", dayData[i][3]);
//                                            Log.e("anmai", "i = " + String.valueOf(i));
                                        }
                                        else{
//                                            Log.e("anmai", "in break");
                                            break;
                                        }
                                    }
                                    adapter = new BillsInfoAdapter((Activity) getContext());
                                    eListView.setAdapter(adapter);
                                }
                            },
                            c.get(Calendar.YEAR),
                            c.get(Calendar.MONTH),
                            c.get(Calendar.DAY_OF_MONTH)).show();

                }
            });

            return rootView;
        }

        /**
         * author anmai
         */
        private  void initializeData() {
            group = new ArrayList<String>();
            child = new ArrayList<List<String>>();
            child_img = new ArrayList<List<Integer>>();

//        addInfo("数据同步", new String[]{"立即同步", "关闭同步"}, new Integer[]{android.R.drawable.sym_action_chat,android.R.drawable.sym_action_chat});
//        addInfo("个人信息", new String[]{"昵称", "男", "女"});
//        addInfo("你", new String[]{"电风扇", "合法化", "嘻嘻"});
//        addInfo("我", new String[]{"电风扇", "牛逼", "谔谔"});
//        addInfo("她", new String[]{"哈哈", "擦擦", "真是"});
        }

        /**
         * author anmai
         * @param g-group
         * @param c-child
         */
        private void addInfo(String g, String[] c, Integer[] c_img) {
            group.add(g);
            List<String> childitem = new ArrayList<String>();
            List<Integer> childitemimg = new ArrayList<Integer>();
            for (int i = 0; i < c.length; i++) {
                childitem.add(c[i]);
                childitemimg.add(c_img[i]);
            }
            child.add(childitem);
            child_img.add(childitemimg);
        }



        /**
         * author anmai
         */
        public class BillsInfoAdapter extends BaseExpandableListAdapter {
            Activity activity;

            public BillsInfoAdapter(Activity a) {
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
//                String string = child.get(groupPosition).get(childPosition);
//                return getGenericView(string);
                ChildHolder childHolder = null;
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(activity).inflate(R.layout.expandlist_child, null);
                    childHolder = new ChildHolder();
                    childHolder.txt = (TextView)convertView.findViewById(R.id.expandlist_child_txt);
                    childHolder.img = (ImageView)convertView.findViewById(R.id.expandlist_child_img);
                    convertView.setTag(childHolder);
                }
                else
                {
                    childHolder = (ChildHolder)convertView.getTag();
                }
                childHolder.txt.setText(child.get(groupPosition).get(childPosition));
                childHolder.img.setBackgroundResource(child_img.get(groupPosition).get(childPosition));
                return convertView;
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
//                String string = group.get(groupPosition);
//                return getGenericView(string);
                GroupHolder groupHolder = null;
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(activity).inflate(R.layout.expendlist_group, null);
                    groupHolder = new GroupHolder();
                    groupHolder.txt = (TextView)convertView.findViewById(R.id.expandlist_group_txt);
                    groupHolder.img = (ImageView)convertView.findViewById(R.id.expandlist_group_img);
                    convertView.setTag(groupHolder);
                }
                else
                {
                    groupHolder = (GroupHolder)convertView.getTag();
                }

                if (!isExpanded)
                {
                    groupHolder.img.setBackgroundResource(android.R.drawable.sym_action_chat);
                }
                else
                {
                    groupHolder.img.setBackgroundResource(android.R.drawable.sym_action_email);
                }

                groupHolder.txt.setText(group.get(groupPosition));
                return convertView;
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

            class GroupHolder
            {
                public TextView txt;

                public ImageView img;
            }

            class ChildHolder
            {
                public ImageView img;

                public TextView txt;
            }
        }
    }
}