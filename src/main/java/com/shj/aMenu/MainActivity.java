package com.shj.aMenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.shj.demohandlemsg.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final static String LogTag = "app";
    private List<Map<String, Object>> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) this.findViewById(R.id.listView);
        // 创建一个 List 集合，元素是 Map
        list = createData();

        // 将数组包装成 ArrayAdapter
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.array_item,
                new String[] {"title"},
                new int[] {R.id.title});
        listView.setAdapter(adapter);

        // 添加点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> item = list.get(position);
                try {
                    Class cls = Class.forName((String) item.get("clsName"));
                    // 意图
                    Intent intent = new Intent(MainActivity.this , cls);
                    // Intent 传递数据
                    intent.putExtra("title", (String) item.get("title"));
                    startActivity(intent);
                } catch (Exception e) {
                    Log.v(LogTag, e.toString());
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(LogTag, "onStop");
    }

    private List<Map<String, Object>> createData() {
        String[] titles = {
                "简单",
                "自定义 Thread",
                "自定义 AsyncTask",
                "回调方法"
        };
        String[] clsNames = {
                "com.shj.simple.SimpleActivity",
                "com.shj.simple2.Simple2Activity",
                "com.shj.asyncTask.AsyncTaskActivity",
                "com.shj.callback.CallbackActivity",
        };
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < titles.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("title", titles[i]);
            item.put("clsName", clsNames[i]);
            list.add(item);
        }
        return list;
    }
}
