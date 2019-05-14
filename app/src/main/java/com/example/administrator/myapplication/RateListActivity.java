package com.example.administrator.myapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable {
    String data[] = {"one", "three", "two"};
    Handler handler;
    final String TAG="Rate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);
        List<String> list1 = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            list1.add("item" + 1);
        }
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list1);
        setListAdapter(adapter);

        Thread thread= new Thread(this);
        thread.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                  if (msg.what==7){
                    List<String>  list2=(List<String>) msg.obj ;
                      ListAdapter adapter = new ArrayAdapter<String>(RateListActivity.this, android.R.layout.simple_list_item_1, list2);
                      setListAdapter(adapter);
                }
                super.handleMessage(msg);
            }
        };


    }

    public void run() {
        //获取网络数据，放入list带回到主线程中
        List<String> retList = new ArrayList<String>();
        Document doc=null;
        try{
            Thread.sleep(3000);
            doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i(TAG, "run: "+doc.title());
            Elements tables=doc.getElementsByTag("Table");
            /* for (Element table: tables){
                 Log.i(TAG, "run: table="+table);

             }*/
            Element table6= tables.get(0);
            Log.i(TAG, "run: table6="+table6);
            //获取TD中的数据
            Elements tds=table6.getElementsByTag("td");
            for(int i=0;i<tds.size();i+=6){
                Element td1=tds.get(i);
                Element td2=tds.get(i+5);
                Log.i(TAG, "run:"+td1.text()+"==>"+td2.text());
               retList.add(td1.text()+"==>"+td2.text());

            }

           /* for(Element td:tds){
                Log.i(TAG, "run: tds"+td);

            }*/
        }
        catch(IOException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Message msg=handler.obtainMessage(7);
        //msg.what=5;
        // msg1.obj="hello for run";
        msg.obj=retList;
        handler.sendMessage(msg);
    }
}