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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CurrencyRate2 extends AppCompatActivity implements Runnable{
    EditText rmb;
    TextView display;
    final String TAG="Rate";
    float dollarRate=0.0f;
    float euroRate=0.0f;
    float wonRate=0.0f;
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

        Log.i(TAG,"onCreate: sp dollarRate" + dollarRate);
        Log.i(TAG,"onCreate: sp euroRate" + euroRate);
        Log.i(TAG,"onCreate: sp wonRate" + wonRate);

        //开启子线程
        Thread t = new Thread(this);
        t.start();
//????hanlder为什么可以重写
         handler = new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==5){
                    String str=(String)msg.obj;
                    Log.i(TAG,"handlemessage:getmessage msg="+str);
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

    @Override
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
        //获取Msg对象，用于返回主线程
        Message msg=handler.obtainMessage(5);
        //msg.what =5;
        msg.obj="hello for run()";
        handler.sendMessage(msg);

        //获得网络数据
        URL url= null;
        try {
            url = new URL("http://www.haigouzu.com/Price/HK_ExRate.aspx");
            HttpURLConnection http= (HttpURLConnection)url.openConnection();
            InputStream in= http.getInputStream();

            String hrml=inputStream2String(in);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
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


