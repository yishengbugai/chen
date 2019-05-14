package com.example.administrator.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mylist2Activity extends ListActivity implements Runnable {
    Handler handler;
    private ArrayList<HashMap<String, String>> listItems; // 存放文字、图片信息
    private SimpleAdapter listItemAdapter; // 适配器
    private MyAdapter MyAdapter;
    String  TAG="Mylist2Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();

        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    List<HashMap<String,String>> list2=(List<HashMap<String, String>>)msg.obj;
                    listItemAdapter = new SimpleAdapter(Mylist2Activity.this, list2, // listItems 数据源
                            R.layout.list_item, // ListItem 的 XML 布局实现
                            new String[]{"ItemTitle", "ItemDetail"},
                            new int[]{R.id.itemTitle, R.id.itemDetail}
                            );
                    setListAdapter(listItemAdapter);
                }
                super.handleMessage(msg);
            }


        };
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               HashMap<String,String>map= (HashMap<String,String>)getListView().getItemAtPosition(position);
               String titleStr=map.get("ItemTitle");
               String detailStr=map.get("ItemDetail");

                TextView title=(TextView)view.findViewById(R.id.itemTitle);
                TextView Detail=(TextView)view.findViewById(R.id.itemDetail);
                String title2=String.valueOf(title.getText());
                String detial2=String.valueOf(Detail.getText());
                Log.i(TAG, "onCreate: title="+title2);
                Log.i(TAG, "onCreate: rate="+detial2);
                //打开新的页面传入参数

                Intent rateCalc=new Intent(Mylist2Activity.this,RateCalcActivity.class);
                rateCalc.putExtra("title",titleStr);
                rateCalc.putExtra("rate",Float.parseFloat(detial2));
                startActivity(rateCalc);

            }
        });
    }
        private void initListView () {
            listItems = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < 10; i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("ItemTitle", "Rate： " + i); // 标题文字
                map.put("ItemDetail", "detail" + i); // 详情描述
                listItems.add(map);
            }
// 生成适配器的 Item 和动态数组对应的元素
            listItemAdapter = new SimpleAdapter(this, listItems, // listItems 数据源
                    R.layout.list_item, // ListItem 的 XML 布局实现
                    new String[]{"ItemTitle", "ItemDetail"},
                    new int[]{R.id.itemTitle, R.id.itemDetail}
            );
        }


    public void run() {
        //获取网络数据，放入list带回主线程
             List<HashMap<String,String>> retlist=new ArrayList<HashMap<String, String>>();
            Document doc=null;
            try{
                doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();

                Elements tables=doc.getElementsByTag("Table");
                Element table2=tables.get(0);
                Elements tds=table2.getElementsByTag("td");

          for(int i=0;i<tds.size();i+=6){
              Element td1=tds.get(i);
              Element td2=tds.get(i+5);

              String str1=td1.text();
              String val=td2.text();

              HashMap<String,String> map= new HashMap<String, String>();
              map.put("ItemTitle",str1);
              map.put("ItemDetail",val);
              retlist.add(map);
          }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            Message msg=handler.obtainMessage(7);
            msg.obj=retlist;
            handler.sendMessage(msg);
        }


}
