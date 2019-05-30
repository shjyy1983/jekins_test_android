package com.shj.callback;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.shj.demohandlemsg.R;

import org.w3c.dom.Text;

import java.util.Map;

/**
 * Java回调的原理与实现
 * https://www.jianshu.com/p/67190bdce647
 */
public class CallbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callback);
        demo();
    }

    void demo() {
        final TextView textView = findViewById(R.id.textView);
        final Context context = this;

        textView.setText("正在加载...");

        // 回调
        ProfileManagerCallback callback = new ProfileManagerCallback() {
            @Override
            public void completeGetData(Map<String, Object> map) {
                Log.v("app", map.toString());
                final Map<String, Object> _map = map;

                // 切换到主线程更新 UI
                ((CallbackActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(_map.toString());
                    }
                });
            }
        };

        ProfileManager manager = ProfileManager.getInstance();
        manager.getProfileInfo("userId_123", "token_123456", callback);
    }
}
