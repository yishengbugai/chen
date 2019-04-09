package com.example.administrator.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CurrencyRate extends AppCompatActivity {
    EditText rmb;
    TextView display;
    float dollarRate=0.1f;
    float euroRate=0.2f;
    float wonRate=0.3f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_rate);
        rmb= (EditText) findViewById(R.id.rmb);
        display = (TextView) findViewById(R.id.display);
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
            String.format("%.2f", curr);
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

            }

        super.onActivityResult(requestCode, resultCode, data);
    }
}


