package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public  class MainActivity4 extends AppCompatActivity implements View.OnClickListener {
    TextView out1,out2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        out2 = (TextView)findViewById(R.id.textView22);
        out1 = (TextView)findViewById(R.id.textView12);
        Button btn6=findViewById(R.id.button6);
        Button btn11=findViewById(R.id.button11);
        Button btn12=findViewById(R.id.button12);
        Button btn13=findViewById(R.id.button13);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn13.setOnClickListener(this);

        Button btn21=findViewById(R.id.button21);
        Button btn22=findViewById(R.id.button22);
        Button btn23=findViewById(R.id.button23);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn23.setOnClickListener(this);
        btn6.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        String a = (String)out1.getText();
        String b = (String)out2.getText();
        if (v.getId()==R.id.button11){
            int newscore=Integer.parseInt(a)+3;
            out1.setText(""+newscore);
        }
        else if (v.getId()==R.id.button12){
            int newscore=Integer.parseInt(a)+2;
            out1.setText(""+newscore);
        }
        else if (v.getId()==R.id.button13){
            int newscore=Integer.parseInt(a)+1;
            out1.setText(""+newscore);
        }
        if (v.getId()==R.id.button21){
            int newscore=Integer.parseInt(a)+3;
            out2.setText(""+newscore);
        }
        else if (v.getId()==R.id.button22){
            int newscore=Integer.parseInt(a)+2;
            out2.setText(""+newscore);
        }
        else if (v.getId()==R.id.button23){
            int newscore=Integer.parseInt(a)+1;
            out2.setText(""+newscore);
        }
        else if (v.getId()==R.id.button6)
        {int newscore=0;
            out1.setText(""+newscore);
            out2.setText(""+newscore); }
    }
}