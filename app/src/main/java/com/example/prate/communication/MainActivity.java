package com.example.prate.communication;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.prate.communication.LoginActivity.LoginActivity;

public class MainActivity extends AppCompatActivity {
ProgressBar progressBar;
Handler handler;
Intent intent;
    public static String EncodeString(String string) {
        return string.replace(".", ",");  /// because firebase is not accepting special characters eg .,[] type bt accpets coma.
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.starting_progressbar);
        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            progressBar.setVisibility(View.GONE);
             intent =new Intent(MainActivity.this, LoginActivity.class);
             startActivity(intent);
             finish();
            }
        },2900);




    }



}
