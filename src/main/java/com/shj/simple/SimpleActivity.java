package com.shj.simple;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.shj.demohandlemsg.R;

import java.util.Timer;
import java.util.TimerTask;

public class SimpleActivity extends AppCompatActivity {
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        demo1();
    }

    void demo1() {
        final int[] imageIds = {
                R.drawable.s1,
                R.drawable.s2,
                R.drawable.s3,
                R.drawable.s4,
                R.drawable.s5,
                R.drawable.s6
        };

        final ImageView imageView = findViewById(R.id.imageView);
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    imageView.setImageResource(imageIds[count++ % imageIds.length]);
                }
            }
        };

        // 定时器，每隔1s发送一个消息
        // TimerTask 本质就是启动一个新的线程
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 1000);
    }
}
