package com.shj.simple2;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shj.demohandlemsg.R;

public class Simple2Activity extends AppCompatActivity {
    public Handler handler;
    final int[] imageIds = {
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,
            R.drawable.s5,
            R.drawable.s6
    };
    int count = 0;

    ChangeThread changeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple2);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demo1();
                Log.i("app", "click");
            }
        });


        final ImageView imageView = findViewById(R.id.imageView);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    imageView.setImageResource(imageIds[count++ % imageIds.length]);
                }
            }
        };
    }

    void demo1() {
        changeThread = new ChangeThread();
        changeThread.start();
    }

    class ChangeThread extends Thread {
        @Override
        public void run() {
            handler.sendEmptyMessage(0x123);
        }
    }
}

