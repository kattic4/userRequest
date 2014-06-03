package com.example.userrequests;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.*;
import android.view.View;

import java.util.List;

public class MainActivity extends Activity {

    public ServerConnect serverConnect;

    public static String startUrl = "http://servicetech.apphb.com";
    //public static String startUrl = "http://servicetech.somee.com";
    public static String sessionKey = "oawAmqtqq0HnAAh6IYZlVyPVGqAUnuzf";
    private Response response = new Response();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serverConnect = new ServerConnect();

        final EditText nameUs = (EditText) this.findViewById(R.id.nameUs);
        final EditText pass = (EditText) this.findViewById(R.id.pass);
        final EditText fio = (EditText) this.findViewById(R.id.fio);
        nameUs.setText("saintgluk");
        pass.setText("51392491");

        // начинаем авторизацию на сервере
        Button synchButton = (Button) this.findViewById(R.id.btnSynch);
        synchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("myDebugTest", "нажата кнопка run в синхронизации");
                //response = serverConnect.login(startUrl, nameUs.getText().toString(), pass.getText().toString());
                //sessionKey = response.sessionKey;
                response.response = 0;
                Log.d("myDebugTest", "result=" + sessionKey);

                if(response.response == -404){
                    Toast.makeText(getApplicationContext(), "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
                }
                else if (response.response == 0){
                    Toast.makeText(getApplicationContext(), sessionKey, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, BidList.class);
                    startActivity(intent);
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
                response = serverConnect.signup(startUrl, nameUs.getText().toString(), pass.getText().toString(), fio.getText().toString(), false);
                sessionKey = response.sessionKey;

                Log.d("myDebugTest", "result=" + sessionKey + " = " + response.response);

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
                else if (response.response == -2){
                    Toast.makeText(getApplicationContext(), "Имя пользователя уже существует. Введите другое имя пользователя", Toast.LENGTH_SHORT).show();
                }
            }

        });

        Button testButton = (Button) this.findViewById(R.id.test);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("myDebugTest", "нажата кнопка Test");

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.autorization, menu);
        return true;
    }



    //кнопка добавления_________________________________________________________________________________________________
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

    ///кнопка получения 1-ой записи  ___________________________________________________________________________________
    /*Bid bid = serverConnect.getBidTold(startUrl, 58);

    if(bid == null || bid.id == -1){
        Toast.makeText(getApplicationContext(), "Ошибка! Запись отсутствует.", Toast.LENGTH_SHORT).show();
    }
    else{
        Log.d("myDebugTest", "Можем продолжить работу");
        //выполняем действия
    }*/

    //получить все записи_______________________________________________________________________________________________
    /*
    List<Bid> bids= serverConnect.getAllBid(startUrl, sessionKey);
        for(Bid bid : bids){
            Log.d("myDebugTest", "id=" + bid.id);
            Log.d("myDebugTest", "adminId=" + bid.adminId);
            Log.d("myDebugTest", "bidText=" + bid.bidText);
            Log.d("myDebugTest", "datePost=" + bid.datePost);
            Log.d("myDebugTest", "FIOShort=" + bid.FIOShort);
            Log.d("myDebugTest", "NetworkOrInventoryNumber=" + bid.NetworkOrInventoryNumber);
            Log.d("myDebugTest", "roomName=" + bid.roomName);
            Log.d("myDebugTest", "SoftwareName=" + bid.SoftwareName);
            Log.d("myDebugTest", "SourceSoftware=" + bid.SourceSoftware);
            Log.d("myDebugTest", "status=" + bid.status);
            Log.d("myDebugTest", "Tema=" + bid.Tema);
            Log.d("myDebugTest", "userId=" + bid.userId);
            Log.d("myDebugTest", "Workstation=" + bid.Workstation);
        }
    */


    //удаление заявки___________________________________________________________________________________________________
    /*
    Boolean isBidRemove = serverConnect.removeBid(startUrl, sessionKey, 58);

                if(isBidRemove){
                    Toast.makeText(getApplicationContext(), "Запись удалена!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Ошибка удаления записи!", Toast.LENGTH_SHORT).show();
                }
     */

    //редактирование завки______________________________________________________________________________________________
    /*
    int response = serverConnect.editBid(startUrl, sessionKey, 57, "666", "Новый Текст с андроид клиента", "Тема анжроида", "sourceSoftware a", "softwareName a", "networkOrInventoryNumber a", "workstation a");
    if(response == -404){
        Toast.makeText(getApplicationContext(), "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
    }
    else if (response == -100){
        Toast.makeText(getApplicationContext(), "Произошла неизвестная ошибка", Toast.LENGTH_SHORT).show();
    }
    else if (response == -3){
        Toast.makeText(getApplicationContext(), "действие ключа истекло или передан неверный ключ", Toast.LENGTH_SHORT).show();
    }
    else if (response == -5){
        Toast.makeText(getApplicationContext(), "Запись по данному id отсутствует", Toast.LENGTH_SHORT).show();
    }
    else if (response == -7){
        Toast.makeText(getApplicationContext(), "у вас нет доступа на редактирование данной записи, запись может редактировать только создатель", Toast.LENGTH_SHORT).show();
    }
    else if (response == 0){
        Toast.makeText(getApplicationContext(), "Запись удачно изменена", Toast.LENGTH_SHORT).show();
    }
    */

    //смена статуса_____________________________________________________________________________________________________
    /*
    int response = serverConnect.setStatus(startUrl, sessionKey, 57, "В работе");
    if(response == -404){
        Toast.makeText(getApplicationContext(), "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
    }
    else if (response == -100){
        Toast.makeText(getApplicationContext(), "Произошла неизвестная ошибка", Toast.LENGTH_SHORT).show();
    }
    else if (response == -3){
        Toast.makeText(getApplicationContext(), "действие ключа истекло или передан неверный ключ", Toast.LENGTH_SHORT).show();
    }
    else if (response == -5){
        Toast.makeText(getApplicationContext(), "Запись по данному id отсутствует", Toast.LENGTH_SHORT).show();
    }
    else if (response == -6){
        Toast.makeText(getApplicationContext(), "только администраторам доступно менять статус", Toast.LENGTH_SHORT).show();
    }
    else if (response == 0){
        Toast.makeText(getApplicationContext(), "Запись удачно изменена", Toast.LENGTH_SHORT).show();
    }
    */

    //проверка на тип учетной записи____________________________________________________________________________________
    /*
    Boolean isUserAdmin = serverConnect.getIsAdmin(startUrl, sessionKey);
        if(isUserAdmin){
            Toast.makeText(getApplicationContext(), "Вы администратор!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Вы смертный!", Toast.LENGTH_SHORT).show();
        }
    */

    //получение справочников____________________________________________________________________________________________
    /*
    List<Spravochnik> spravochniks = serverConnect.getSpravochniki(startUrl);
    for(Spravochnik sp : spravochniks){
        Log.d("myDebugTest", "id=" + sp.id);
        Log.d("myDebugTest", "spravochnicValue=" + sp.spravochnicValue);
        Log.d("myDebugTest", "spravochnicType=" + sp.spravochnicType);
        Log.d("myDebugTest", "____________________________________________________________________________");
    }
    */
}

