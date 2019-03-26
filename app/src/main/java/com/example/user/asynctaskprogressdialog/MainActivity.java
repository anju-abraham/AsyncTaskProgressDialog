package com.example.user.asynctaskprogressdialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    EditText etTime;
    Button btnRun;
    TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTime=(EditText)findViewById(R.id.etTime);
        btnRun=(Button)findViewById(R.id.btnRun);
        tvOutput=(TextView)findViewById(R.id.tvOutput);

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // create object of MyAsyncTasks class and execute it
                MyAsyncTask Task = new MyAsyncTask();
                String sleepTime=etTime.getText().toString();
                Task.execute(sleepTime);
            }
        });
    }

    public class MyAsyncTask extends AsyncTask<String,String,String> {

        ProgressDialog pD;
        String str;
        @Override
        protected String doInBackground(String... strings) {

            publishProgress("Sleeping...");
            try {
                int time = Integer.parseInt(strings[0]) * 1000;
                Thread.sleep(time);
                str = "Slept for " + strings[0] + " sec";

            } catch (InterruptedException e) {
                e.printStackTrace();
                str = e.getMessage();
            }
            catch (Exception e) {
                e.printStackTrace();
                str = e.getMessage();
            }
            return str;
        }
        //called after doInBackground method completes processing
        @Override
        protected void onPostExecute(String s) {
            pD.dismiss();
            tvOutput.setText(s);
            // super.onPostExecute(s);
        }

        //executed before the background processing starts
        @Override
        protected void onPreExecute() {
            // super.onPreExecute();
            pD= ProgressDialog.show(MainActivity.this, "ProgressDialog", "Wait for" + etTime.getText().toString() + "sec");
        }

        //receives progress updates from doInBackground method
        @Override
        protected void onProgressUpdate(String... values) {
            tvOutput.setText(values[0]);
            //super.onProgressUpdate(values);
        }
    }
}
