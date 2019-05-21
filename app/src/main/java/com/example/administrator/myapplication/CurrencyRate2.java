package com.example.administrator.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CurrencyRate2 extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView display;
    final String TAG="Rate";
    float dollarRate=0.0f;
    float euroRate=0.0f;
    float wonRate=0.0f;
    String updateDate ="";
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_rate);
        rmb= (EditText) findViewById(R.id.rmb);
        display = (TextView) findViewById(R.id.display);

        //获取sp里面保存的数据
        SharedPreferences sharedPreferences= getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        //或者 SharedPreferences sharedPreferences1 =PreferenceManager.getDefaultSharedPreferences(this);
        dollarRate = sharedPreferences.getFloat("dollar_RATE",0.0f);
        euroRate = sharedPreferences.getFloat("euro_RATE",0.0f);
        wonRate = sharedPreferences.getFloat("won_RATE",0.0f);
        updateDate=sharedPreferences.getString("update_date","");

        //获取当前系统时间
        Date today= Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr =sdf.format(today);


        Log.i(TAG,"onCreate: sp dollarRate" + dollarRate);
        Log.i(TAG,"onCreate: sp euroRate" + euroRate);
        Log.i(TAG,"onCreate: sp wonRate" + wonRate);
        Log.i(TAG,"onCreate: sp updatedate=" + updateDate);
        Log.i(TAG,"onCreate: sp updatedate=" + todayStr);

        //判断时间
        if(!todayStr.equals(updateDate)){
            Log.i(TAG,"onCreate:  需要更新=" );
            //开启子线程
            Thread t = new Thread(this);
            t.start();
        }else {
            Log.i(TAG, "onCreate:  不需要更新=");
        }


//????hanlder为什么可以重写
         handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){
                    Bundle bdl=(Bundle)msg.obj;
                    dollarRate=bdl.getFloat("dollar-rate");
                    wonRate=bdl.getFloat("won-rate");
                    euroRate=bdl.getFloat("euro-rate");

                    Log.i(TAG, "handleMessage: dollarRate="+dollarRate);
                    Log.i(TAG, "handleMessage: wonRate="+wonRate);
                    Log.i(TAG, "handleMessage: euroRate="+euroRate);

                    //保存更新的日期
                    SharedPreferences sharedPreferences =getSharedPreferences("myrate",Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("update_date",todayStr);
                    editor.putFloat("dollar_RATE",dollarRate);
                    editor.putFloat("euro_RATE",euroRate);
                    editor.putFloat("won_RATE",wonRate);
                    editor.apply();


                    Toast.makeText(CurrencyRate2.this, "汇率已更新", Toast.LENGTH_SHORT).show();
                }

                super.handleMessage(msg);
            }
        };
        }

    public void onClick(View btn){

        String str=rmb.getText().toString();
        float r=0;
        String curr;
        float meudiem;
        if (str.length()>0){
            r= Float.parseFloat(str);
        }
        else {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }
        if(btn.getId()==R.id.button_buck){
            meudiem=r*dollarRate;
            curr=String.format("%.2f",meudiem);
            display.setText(curr);

        }
        if(btn.getId()==R.id.button_pound){
            meudiem=r*euroRate;
            curr=String.format("%.2f",meudiem);
            display.setText(curr);

        }
        if(btn.getId()==R.id.button_korea){
            meudiem=r*wonRate;
            curr=String.format("%.2f", meudiem);
            display.setText(curr);

        }
    }
    public  void openOne(View btn){

        Intent hello= new Intent(this,config.class);
        hello.putExtra("dollar_rate_key",dollarRate);
        hello.putExtra("euro_rate_key",euroRate);
        hello.putExtra("won_rate_key",wonRate);
        Log.i("s","openOne:dollar_rate_key "+dollarRate);
        Log.i("sa","openOne:euro_rate_key"+euroRate);
        Log.i("san","openOne:won_rate_key "+wonRate);
        Intent web=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.swufe.edu.cn"));
        startActivityForResult(hello,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_set){
            Intent hello= new Intent(this,config.class);
            hello.putExtra("dollar_rate_key",dollarRate);
            hello.putExtra("euro_rate_key",euroRate);
            hello.putExtra("won_rate_key",wonRate);
            Log.i("s","openOne:dollar_rate_key "+dollarRate);
            Log.i("sa","openOne:euro_rate_key"+euroRate);
            Log.i("san","openOne:won_rate_key "+wonRate);
            Intent web=new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.swufe.edu.cn"));
            startActivityForResult(hello,1);
        }else if(item.getItemId()==R.id.menu_set_label){
            //打开列表窗口
            Intent list= new Intent(this,RateListActivity.class);
            startActivity(list);
            //Intent list= new Intent(this,Mylist2Activity.class);
            //startActivity(list);
            //测试数据库
//            RateItem item1=new RateItem("aaa","1");
//            RateManager manager=new RateManager(this);
//            manager.add(item1);
//            manager.add(new RateItem("222","22"));
//            Log.i(TAG, "onOptionsItemSelected: 写入数据完毕");
//
//            //插叙所有数据
//            List<RateItem> testlist=manager.listAll();
//            for(RateItem i:testlist){
//                Log.i(TAG, "onOptionsItemSelected: 取出数据name="+i.getCurName());
//            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==2){

            Bundle bun=data.getExtras();
            dollarRate=bun.getFloat("newdollar",0.1f);
            euroRate=bun.getFloat("neweuro",0.2f);
            wonRate=bun.getFloat("newwon",0.3f);


            //将新设置的汇率保存在sp里面
            SharedPreferences sharedPreferences =getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putFloat("dollar_RATE",dollarRate);
            editor.putFloat("euro_RATE",euroRate);
            editor.putFloat("won_RATE",wonRate);
            editor.commit();  //保存数据  或者editor.apply();
            Log.i(TAG,"onActivityResult:数据已保存到sharePreferences");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void run() {
        Log.i(TAG,"run:run....");
        for(int i=1;i<6;i++){
            Log.i(TAG, "run:i="+i);
            try{
                    Thread.sleep(200);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        //用于保存获取的汇率
        Bundle bun=new Bundle();
        //获取Msg对象，用于返回主线程


        //获得网络数据
       /* URL url= null;
        try {
            url = new URL("http://www.usd-cny.com/bankofchina.htm");
            HttpURLConnection http= (HttpURLConnection)url.openConnection();
            InputStream in= http.getInputStream();

            String html=inputStream2String(in);
            Document doc = Jsoup.parse(html);
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }  */
        getFromURL(bun);

        //bundle 中保存所获取的汇率
            //获取msg对象，用于返回主线程
        Message msg=handler.obtainMessage(5);
       //msg.what=5;
       // msg1.obj="hello for run";
        msg.obj=bun;
        handler.sendMessage(msg);

    }

    private void getFromURL(Bundle bun) {
        Document doc=null;
        try{
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
             if("美元".equals(td1.text())){
                 bun.putFloat("dollar-rate",100f/Float.parseFloat(td2.text()));
             }
                else if("韩元".equals(td1.text())){
                    bun.putFloat("won-rate",100f/Float.parseFloat(td2.text()));
                }
                else if("欧元".equals(td1.text())){
                    bun.putFloat("euro-rate",100f/Float.parseFloat(td2.text()));
                }
            }

           /* for(Element td:tds){
                Log.i(TAG, "run: tds"+td);

            }*/
         }
         catch(IOException e){
            e.printStackTrace();
         }
    }


    //inputStream转化成String
    private String inputStream2String (InputStream inputStream)throws IOException {
            final int buffersize=1024;
            final char[]buffer=new char[buffersize];
            final StringBuilder out =new StringBuilder();
            Reader in = new InputStreamReader(inputStream,"gb2312");
            for(;;){
                int rsz=in.read(buffer,0,buffer.length);
                if(rsz<0)
                    break;
                out.append(buffer,0,rsz);
            }
            return out.toString();
    }
}


