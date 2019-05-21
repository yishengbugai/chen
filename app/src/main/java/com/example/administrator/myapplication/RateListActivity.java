package com.example.administrator.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable {
    String data[] = {"one", "three", "two"};
    Handler handler;
    final String TAG="Rate";
    private String logDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);

        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        logDate = sp.getString(DATE_SP_KEY, "");
        Log.i("List","lastRateDateStr=" + logDate);

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
        String curDateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Log.i("run","curDateStr:" + curDateStr + " logDate:" + logDate);

        if(curDateStr.equals(logDate)) {
//如果相等，则不从网络中获取数据
            Log.i("run", "日期相等，从数据库中获取数据");
            RateManager manager=new RateManager(this);
            for(RateItem rateItem : manager.listAll()){
                retList.add(rateItem.getCurName() + "=>" + rateItem.getCurRate());
            }
        }else{
            Log.i("run", "日期不等，从网络中获取数据");
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
                List<RateItem>  rateList=new ArrayList<RateItem>();

                for(int i=0;i<tds.size();i+=6){
                    Element td1=tds.get(i);
                    Element td2=tds.get(i+5);
                    // String str1 =td1.text()
                    //String val=td2.text()

                    Log.i(TAG, "run:"+td1.text()+"==>"+td2.text());
                    retList.add(td1.text()+"==>"+td2.text());
                    rateList.add(new RateItem(td1.text(),td2.text()));

                }
                //把数据写入数据库
                RateManager manager= new RateManager(this);
                manager.deleteAll();
                manager.addAll(rateList);

                //更新记录日期
                SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString(DATE_SP_KEY, curDateStr);
                edit.commit();
                Log.i("run","更新日期结束：" + curDateStr);


           /* for(Element td:tds){
                Log.i(TAG, "run: tds"+td);

            }*/
            }
            catch(IOException e){
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }



        Message msg=handler.obtainMessage(7);
        //msg.what=5;
        // msg1.obj="hello for run";
        msg.obj=retList;
        handler.sendMessage(msg);
    }
}