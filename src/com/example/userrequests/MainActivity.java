package com.example.userrequests;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    public ServerConnect serverConnect;

    public String startUrl = "http://servicetech.apphb.com";
    //public String startUrl = "http://servicetech.somee.com";
    public String sessionKey = "oawAmqtqq0HnAAh6IYZlVyPVGqAUnuzf";
    private Response response = new Response();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serverConnect = new ServerConnect();

        final EditText nameUs = (EditText) this.findViewById(R.id.nameUs);
        final EditText pass = (EditText) this.findViewById(R.id.pass);
        nameUs.setText("saintgluk");
        pass.setText("51392491");

        // начинаем авторизацию на сервере
        Button synchButton = (Button) this.findViewById(R.id.btnSynch);
        synchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("myDebugTest", "нажата кнопка run в синхронизации");
                response = serverConnect.login(startUrl, nameUs.getText().toString(), pass.getText().toString());
                sessionKey = response.sessionKey;

                Log.d("myDebugTest", "result=" + sessionKey);

                if(response.response == -404){
                    Toast.makeText(getApplicationContext(), "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
                }
                else if (response.response == 0){
                    Toast.makeText(getApplicationContext(), sessionKey, Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(AutorizationActivity.this, MainActivity.class);
                    //startActivity(intent);
                }
                else if (response.response == -1){
                    Toast.makeText(getApplicationContext(), "Неверный логин или пароль. Отказано в авторизации", Toast.LENGTH_SHORT).show();
                }
            }

        });


        Button regButton = (Button) this.findViewById(R.id.btnReg2);
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("myDebugTest", "нажата кнопка run в синхронизации");
                response = serverConnect.signup(startUrl, nameUs.getText().toString(), "", pass.getText().toString(), false);
                sessionKey = response.sessionKey;

                Log.d("myDebugTest", "result=" + sessionKey);

                if(response.response == -404){
                    Toast.makeText(getApplicationContext(), "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
                }
                else if (response.response == 0){
                    Toast.makeText(getApplicationContext(), sessionKey, Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent(AutorizationActivity.this, MainActivity.class);
                    //startActivity(intent);
                }
                else if (response.response == -1){
                    Toast.makeText(getApplicationContext(), "Неверный логин или пароль. Отказано в авторизации", Toast.LENGTH_SHORT).show();
                }
            }

        });

        Button testButton = (Button) this.findViewById(R.id.test);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myDebugTest", "нажата кнопка Test");
                int response = serverConnect.addRequest(startUrl, sessionKey, "А555", "Текст с андроид клиента", "Тема анжроида", "sourceSoftware a", "softwareName a", "networkOrInventoryNumber a", "workstation a");
                Log.d("myDebugTest", "TestResult=" + response);

                if(response == -404){
                    Toast.makeText(getApplicationContext(), "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
                }
                else if (response == -100){
                    Toast.makeText(getApplicationContext(), "Произошла неизвестная ошибка", Toast.LENGTH_SHORT).show();
                }
                else if (response == -3){
                    Toast.makeText(getApplicationContext(), "действие ключа истекло или передан неверный ключ", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.autorization, menu);
        return true;
    }



    //кнопка добавления
    /*int response = serverConnect.addRequest(startUrl, sessionKey, "А555", "Текст с андроид клиента", "Тема анжроида", "sourceSoftware a", "softwareName a", "networkOrInventoryNumber a", "workstation a");
    if(response == -404){
        Toast.makeText(getApplicationContext(), "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
    }
    else if (response == -100){
        Toast.makeText(getApplicationContext(), "Произошла неизвестная ошибка", Toast.LENGTH_SHORT).show();
    }
    else if (response == -3){
        Toast.makeText(getApplicationContext(), "действие ключа истекло или передан неверный ключ", Toast.LENGTH_SHORT).show();
    } */

}

