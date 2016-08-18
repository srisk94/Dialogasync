package com.example.srima.diasync2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText edt;
    private TextView finalResult;
    Context context = this;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn_run);
        //loading.setVisibility(View.INVISIBLE);
        finalResult = (TextView) findViewById(R.id.tv_result);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog();
            }
        });
    }
    private void showDialog() {
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout, null);
        alertbuilder.setView(dialogView);
         edt = (EditText) dialogView.findViewById(R.id.ed);
        final Integer time = edt.getInputType();
        alertbuilder.setTitle("NUMBER");
        alertbuilder.setMessage("Enter your  number below");
        alertbuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = edt.getText().toString();
                runner.execute(sleepTime);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });

        AlertDialog b = alertbuilder.create();
        b.show();
    }
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
       ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..." ); // Calls onProgressUpdate()
            try {
                int time = Integer.parseInt(params[0]) * 1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {

            // execution of result of Long time consuming operation
            progressDialog.dismiss();
           // loading.setVisibility(View.INVISIBLE);
            //finalResult.setText(result);
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
            alertdialog.setTitle("title");

            alertdialog.setMessage("are you sure??");
            alertdialog.setCancelable(false);
            alertdialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                }
            });
            alertdialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog1 = alertdialog.create();
            alertDialog1.show();
        }


        @Override
        protected void onPreExecute() {
           // loading.setVisibility(View.VISIBLE);
            //finalResult.setText(time.getText().toString());
            //progressBar.setProgress(vals);
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Wait for " + edt.getText().toString() + " seconds");
        }


        @Override
        protected void onProgressUpdate(String... text) {
            finalResult.setText(text[0]);

        }
    }
}