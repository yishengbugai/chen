package com.example.administrator.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class config extends AppCompatActivity {
    public final String TAG="config";
    EditText dollar,pound,won;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent= getIntent();
        float dollar_rate=intent.getFloatExtra("dollar_rate_key",0.0f);
        float euro_rate=intent.getFloatExtra("euro_rate_key",0.0f);
        float won_rate=intent.getFloatExtra("won_rate_key",0.0f);

        Log.i(TAG,"onCreat:dollar_rate"+dollar_rate);
        Log.i(TAG,"onCreat:euro_rate"+euro_rate);
        Log.i(TAG,"onCreat:won_rate"+won_rate);

        dollar=(EditText)findViewById(R.id.buck);
        pound=(EditText)findViewById(R.id.euro);
        won=(EditText)findViewById(R.id.won);

        dollar.setText(""+dollar_rate);
        pound.setText(""+euro_rate);
        won.setText(""+won_rate);
    }
    public void save(View btn){
        Log.i(TAG,"save:");
        //获取新值保存。
        float f1=Float.parseFloat(dollar.getText().toString());
        float f2=Float.parseFloat(pound.getText().toString());
        float f3=Float.parseFloat(won.getText().toString());
        Log.i(TAG,"save:获取的新值");
        Log.i(TAG,"onCreat:f1"+f1);
        Log.i(TAG,"onCreat:f2"+f2);
        Log.i(TAG,"onCreat:f3"+f3);

        Intent intent=getIntent();
        Bundle bun=new Bundle();
        bun.putFloat("newdollar",f1);
        bun.putFloat("neweuro",f2);
        bun.putFloat("newwon",f3);
        intent.putExtras(bun);
        setResult(2,intent);
        finish();//返回调用页面

    }
}
