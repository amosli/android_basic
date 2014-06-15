package com.amos.android_db;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.amos.android_db.dao.Person;
import com.amos.android_db.dao.PersonDao;

import java.util.List;

public class MyActivity extends Activity {

    private ListView personListView;
    private List<Person> persons;
    LayoutInflater inflater;//打气筒
    String tag="MyActivity.class";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflater是一个系统服务,初始化系统服务,利用inflater将一个布局文件转化为一个对象
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        setContentView(R.layout.main);
        PersonDao personDao = new PersonDao(this);
        persons = personDao.findAll();


        //第一步,得到组件的id的引用
        personListView = (ListView) this.findViewById(R.id.listview_show_data);
        //第二步,设置组件要显示的内容,listview要显示的内容比较复杂,需要数据的适配器
        personListView.setAdapter(new MyListAdapter());

        //注册点击事件
        personListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             *
             * @param parent 当前ListView
             * @param view 代表当前被点击的条目
             * @param position 当前条目的位置
             * @param id 当前被点击的条目的id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(tag,"点击了!");
                /*
                方法 1:
                 */
                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                Toast.makeText(MyActivity.this,"姓名是1:"+tv_name.getText(),Toast.LENGTH_SHORT).show();
                /*
                方法 2:
                 */
                String name = persons.get(position).getName();
                Toast.makeText(MyActivity.this,"姓名是2:"+name,Toast.LENGTH_SHORT).show();
                /*
                方法 3:
                 */
                Person p=(Person)parent.getItemAtPosition(position);
                Toast.makeText(MyActivity.this,"姓名是3:"+p.getName(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    public class MyListAdapter extends BaseAdapter {

        /**
         * 返回当前有多少个条目
         * @return
         */

        @Override
        public int getCount() {
            return persons.size();
        }

        /**
         * 返回当前position位置对应的条目的object对象
         * @param position
         * @return
         */
        @Override
        public Object getItem(int position) {
            return persons.get(position);
        }

        /**
         * 返回当前position位置对应条目的id
         * @param position
         * @return
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 返回一个条目显示的具体内容
         * 计算当前界面 会有多少个条目出现
         * 1.得到每一个textview的高度
         * 2.得到listview的高度
         * 3.listview高度/textview高度=得到了一个屏幕显示textview的的个数
         * listview的每一个条目的显示都需要调用一次getView的方法
         * 屏幕上有多个item显示就会调用多少getview的方法
         *
         * @param position
         * @param convertView
         * @param parent
         * @return
         */

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
//         TextView textView = new TextView(MyActivity.this);
//         textView.setText(persons.get(position).getName()+":"+persons.get(position).getAge());
//         return textView;

            View view = inflater.inflate(R.layout.item, null);
            Person person = persons.get(position);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_age = (TextView) view.findViewById(R.id.tv_age);
            tv_name.setText("姓名:" + person.getName());
            tv_age.setText("年龄:" + person.getAge());
            Log.d("item:",""+position);
            return view;
        }
    }

}
