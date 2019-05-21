package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MylistActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<String> data=new ArrayList<String>();
    private String TAG="MylistActivity:";
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);
        ArrayAdapter adapter;
        ListView listView=(ListView)findViewById(R.id.mylist);
    //初始化数据
           for (int i=0;i<10;i++       ){
               data.add("item"+i);
           }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);  //构造adapter  ListAdapter 和ArrayAdapter 的关系与自定义列表的区别
        listView.setAdapter(adapter);  //讲adapter与布局控件对应起来
        listView.setEmptyView(findViewById(R.id.nodata));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "onItemClick: position"+position);
        adapter.remove(parent.getItemAtPosition(position));
        adapter.notifyDataSetChanged(); //通知界面已改变。刷新界面，否则不会刷新。
    }
}
