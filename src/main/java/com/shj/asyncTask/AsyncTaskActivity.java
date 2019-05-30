package com.shj.asyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.shj.demohandlemsg.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class AsyncTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);

        Button btn = findViewById(R.id.button);
        final Context context = this;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DownloadTask task = new DownloadTask(context);
                    task.execute(new URL("https://blog.csdn.net/hack8/article/details/28038541"));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    class DownloadTask extends AsyncTask<URL, Integer, String> {
        Context context;
        ProgressDialog dialog;
        int hasRead = 0;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(URL... urls) {
            StringBuffer sb = new StringBuffer();
            try {
                URLConnection connection = urls[0].openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line + "\n");
                    hasRead ++;
                    publishProgress(hasRead);
                }
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(context);
            dialog.setTitle("提示");
            dialog.setMessage("正在下载...");
            dialog.setMax(1000); // 这里是以行数作为模拟的测试，会不准确
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                System.out.println(s);
            }

            dialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setProgress(values[0]); // 这里是以行数作为模拟完成数，由于不知道总的行数，所以这里只是模拟
        }
    }
}
