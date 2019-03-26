package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public  class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    TextView out,out2;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        out = (TextView)findViewById(R.id.textView2);
        out.setText("请输入摄氏温度");
        out2 = (TextView)findViewById(R.id.textView3);
        out2.setText("   ");
        edit= (EditText)findViewById(R.id.editText4);
        edit.setText(" ");
        Button btn=findViewById(R.id.button);
        btn.setOnClickListener(this);
        btn.setText("确认");

    }


    @Override
    public void onClick(View v) {

        float number = Float.valueOf(edit.getText().toString());
        int mess=(int)(number*33.8);
        out2.setText("华氏温度为"+mess);
    }
}

