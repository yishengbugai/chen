package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SettingFragment extends Fragment {

    //将Fragment与某一布局文件匹配
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        return inflater.inflate(R.layout.frame_setting,container);
    }

    //Activity 调用该Fragment时，会使用该方法
    @Override
    public void onActivityCreated(Bundle saveInstanceState){
        super.onActivityCreated(saveInstanceState);
         TextView tv=(TextView)getView().findViewById(R.id.settingTextView1);
         tv.setText("这是设置页面");
     }
}
