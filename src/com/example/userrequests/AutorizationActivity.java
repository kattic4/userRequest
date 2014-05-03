package com.example.userrequests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.util.Pair;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AutorizationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autorization);
        final EditText nameUs = (EditText) this.findViewById(R.id.nameUs);
        final String nameUser = nameUs.getText().toString();

        final EditText pass = (EditText) this.findViewById(R.id.pass);
        final String passUser = pass.getText().toString();
        Log.d("myDebugTest",
                "Это, отладочная печать, используй ее для отладки, Катя!!!");
        final TextView synchText = (TextView) this.findViewById(R.id.result);
        final String sText = synchText.getText().toString();

        // начинаем авторизацию на сервере
        Button synchButton = (Button) this.findViewById(R.id.btnSynch);
        synchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                AutorizationActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String textSynch;
                        Log.d("myDebugTest",
                                "нажата кнопка run в синхронизации");

                        class DownloadFilesTask extends
                                AsyncTask<Void, Void, String> {

                            protected String doInBackground(Void... urls) {
                                Log.d("myDebugTest", "до");
                                String iUrl2 = "http://servicetech.apphb.com/api/Auth/login?login="
                                        + nameUs.getText().toString()
                                        + "&pass=" + pass.getText().toString();
                                Log.d("myDebugTest", iUrl2);

                                HttpURLConnection conn = null;

                                int iDD;

                                try {
                                    HttpURLConnection conn2 = null;

                                    Log.v("connection",
                                            "Starting loading image by URL: "
                                                    + iUrl2);
                                    conn2 = (HttpURLConnection) new URL(iUrl2)
                                            .openConnection();
                                    conn2.setDoInput(true);
                                    String content2;
                                    content2 = "paaaaaaaaaaain";
                                    conn2.connect();
                                    int responseCode2 = conn2.getResponseCode();

                                    if (responseCode2 == HttpURLConnection.HTTP_OK) {
                                        InputStream in2 = conn2
                                                .getInputStream();
                                        BufferedReader r2 = new BufferedReader(
                                                new InputStreamReader(in2));
                                        content2 = r2.readLine();
                                        Log.d("myDebugTest", "content2="
                                                + content2);
                                        JSONObject subcontent2;
                                        subcontent2 = new JSONObject(content2);
                                        String sk = subcontent2
                                                .getString("SessionKey");
                                        int id = Integer.parseInt(subcontent2
                                                .getString("response"));
                                        iDD = id;
                                        if (sk.length() > 30) {
                                            return "Тут просто пока что проверка. To be continued...";
                                        }

                                    }

                                } catch (MalformedURLException ex) {
                                    Log.e("connection", ex.getMessage());
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (OutOfMemoryError e) {
                                    Log.w("connection", "Out of memory!!!");

                                    return "Все очень плохо...";

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                return "Эхехехей. Вы прошли авторизацию";
                            }
                        }

                        String result = "";
                        DownloadFilesTask downloadFilesTask = new DownloadFilesTask();
                        downloadFilesTask.execute();
                        try {
                            result = downloadFilesTask.get();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Log.d("myDebugTest", "result=" + result);
                    }

                });
            }

        });
        // тут закончилась авторизация

        // тут начинается регистрация пользователя
        Button regButton = (Button) this.findViewById(R.id.btnReg2);
        regButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                AutorizationActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        String textSynch;
                        Log.d("myDebugTest2", "нажата кнопка run в регистрации");

                        class DownloadFilesTask extends
                                AsyncTask<Void, Void, String> {

                            protected String doInBackground(Void... urls) {
                                Log.d("myDebugTest2", "до");
                                String iUrl2 = "http://servicetech.apphb.com/api/Auth/signup?login="
                                        + nameUs.getText().toString()
                                        + "&pass="
                                        + pass.getText().toString()
                                        + "&isAdmin=false";
                                Log.d("myDebugTest", iUrl2);

                                HttpURLConnection conn = null;

                                int iDD;

                                try {
                                    HttpURLConnection conn2 = null;

                                    Log.v("connection",
                                            "Starting loading image by URL: "
                                                    + iUrl2);
                                    conn2 = (HttpURLConnection) new URL(iUrl2)
                                            .openConnection();
                                    conn2.setDoInput(true);
                                    String content2;
                                    content2 = "paaaaaaaaaaain";
                                    conn2.connect();
                                    int responseCode2 = conn2.getResponseCode();

                                    if (responseCode2 == HttpURLConnection.HTTP_OK) {
                                        InputStream in2 = conn2
                                                .getInputStream();
                                        BufferedReader r2 = new BufferedReader(
                                                new InputStreamReader(in2));
                                        content2 = r2.readLine();
                                        Log.d("myDebugTest2", "content2="
                                                + content2);
                                        JSONObject subcontent2;
                                        subcontent2 = new JSONObject(content2);
                                        String sk = subcontent2
                                                .getString("SessionKey");
                                        int id = Integer.parseInt(subcontent2
                                                .getString("response"));
                                        iDD = id;
                                        if (sk.length() > 30) {
                                            return "Тут просто пока что проверка. To be continued...";
                                        }

                                    }

                                } catch (MalformedURLException ex) {
                                    Log.e("connection", ex.getMessage());
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } catch (OutOfMemoryError e) {
                                    Log.w("connection", "Out of memory!!!");

                                    return "Все очень плохо...";

                                } catch (JSONException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                return "Эхехехей. Вы прошли регистрацию";
                            }
                        }

                        String result = "";
                        DownloadFilesTask downloadFilesTask = new DownloadFilesTask();
                        downloadFilesTask.execute();
                        try {
                            result = downloadFilesTask.get();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        Log.d("myDebugTest2", "result=" + result);
                    }
                });
            }
        });
        // тут закончилась регистрация

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.autorization, menu);
        return true;
    }

}
