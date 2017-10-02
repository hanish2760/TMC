package com.example.prana.tmc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class Flash extends AppCompatActivity {
    final int SplashScreen_Timeout=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_flash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences settings=getSharedPreferences("LOGIN",0);
                if(settings.getString("LS","NO").equals("NO")) {
                    Intent i = new Intent(Flash.this, Login.class);
/*
                ConstraintLayout cl=(ConstraintLayout) findViewById(R.id.flash);
                LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                View pop=inflater.inflate(R.layout.popup,null);
                int width=ConstraintLayout.LayoutParams.WRAP_CONTENT;
                int height=ConstraintLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable=false;
                final PopupWindow popupWindow=new PopupWindow(pop,width,height,focusable);
                popupWindow.showAtLocation(cl, Gravity.CENTER,0,0);*/
//                lst();
                    startActivity(i);
                }
                else
                {
                    startActivity(new Intent(Flash.this,userentered.class));
                }
            }
        },SplashScreen_Timeout);


        }
       /* public void lst()
        {
            fl=(ListView)findViewById(R.id.firstlist);
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
            fl.setAdapter(adapter);
            ConstraintLayout cl=(ConstraintLayout) findViewById(R.id.flash);
            LayoutInflater inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            View pop=inflater.inflate(R.layout.popup,null);
            int width=ConstraintLayout.LayoutParams.WRAP_CONTENT;
            int height=ConstraintLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable=false;
            final PopupWindow popupWindow=new PopupWindow(pop,width,height,focusable);
            popupWindow.showAtLocation(cl, Gravity.CENTER,0,0);
        }*/
}
