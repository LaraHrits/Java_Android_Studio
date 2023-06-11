package com.example.lw_6;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


public class MainActivity extends Activity {
    MyTask mt;
    TextView tv;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("my_app", "create MainActivity: " + this.hashCode());

        tv = findViewById(R.id.tv);

        mt = (MyTask) getLastNonConfigurationInstance();
        if (mt == null) {
            mt = new MyTask();
            mt.execute();
        }

        mt.link(this);
        Log.d("my_app", "create MyTask: " + mt.hashCode());
    }

    public Object onRetainNonConfigurationInstance() {
        mt.unLink();
        return mt;
    }

    static class MyTask extends AsyncTask<String, Integer, Void> {
        MainActivity activity;
        void link(MainActivity act) {
            activity = act;
        }
        void unLink() {
            activity = null;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                for (int i = 1; i <= 100; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    publishProgress(i);
                    Log.d("my_app", "i = " + i
                            + ", MyTask: " + this.hashCode()
                            + ", MainActivity: " + activity.hashCode());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            activity.tv.setText("i = " + values[0]);
        }
    }
}