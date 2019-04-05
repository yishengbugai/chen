package com.example.administrator.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public  class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
    TextView out4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        out4 = (TextView)findViewById(R.id.textView2);
        Button btn4=findViewById(R.id.button4);
        Button btn1=findViewById(R.id.button1);
        Button btn2=findViewById(R.id.button2);
        Button btn3=findViewById(R.id.button3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }
        @Override
       public void onClick(View v) {
            String a = (String)out4.getText();

            if (v.getId()==R.id.button1){
                   int newscore=Integer.parseInt(a)+3;
                out4.setText(""+newscore);
                }
            else if (v.getId()==R.id.button2){
                int newscore=Integer.parseInt(a)+2;
                out4.setText(""+newscore);
                 }
            else if (v.getId()==R.id.button3){
                int newscore=Integer.parseInt(a)+1;
                out4.setText(""+newscore);
             }
            else if (v.getId()==R.id.button4)
                {int newscore=0;
                 out4.setText(""+newscore); }

    }
}
