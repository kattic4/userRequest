package com.example.userrequests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.List;

public class SetStatusBidActivity extends Activity implements View.OnClickListener{

    private ServerConnect serverConnect;
    private EditText etTema;
    private EditText etTextBid;
    private EditText etPO;
    private EditText etNamePO;
    private EditText etSeti;
    private EditText etWorkstation;
    private EditText etAud;
    private Spinner spStatus;
    private String spNameStatus;

    public static String idRecord = "";
    private String[] dataStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bid);
        serverConnect = new ServerConnect();

        etTema = (EditText)findViewById(R.id.etTema);
        etTextBid = (EditText)findViewById(R.id.etTextBid);
        etPO = (EditText)findViewById(R.id.etPO);
        etNamePO = (EditText)findViewById(R.id.etNamePO);
        etSeti = (EditText)findViewById(R.id.etSeti);
        etWorkstation = (EditText)findViewById(R.id.etWorkstation);
        etAud = (EditText)findViewById(R.id.etAud);
        spStatus = (Spinner)findViewById(R.id.spStatus);
        spNameStatus = "";

        dataStatus = new String[]{"Новая", "В работе", "Решена"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dataStatus);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spStatus.setAdapter(adapter);
        // заголовок
        spStatus.setPrompt("Аудитории");

        // выделяем элемент
        //spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Log.d("myDebugTest", "nameAuditory: " + data[position]);
                spNameStatus = dataStatus[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        Bid bid = serverConnect.getBidTold(MainActivity.startUrl, Integer.valueOf(idRecord));

        if(bid == null || bid.id == -1){
            Toast.makeText(getApplicationContext(), "Ошибка! Запись отсутствует.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, BidList.class);
            startActivity(intent);
        }
        else{
            //выполняем действия
            etTema.setText(bid.Tema);
            etTextBid.setText(bid.bidText);
            etPO.setText(bid.SourceSoftware);
            etNamePO.setText(bid.SoftwareName);
            etSeti.setText(bid.NetworkOrInventoryNumber);
            etWorkstation.setText(bid.Workstation);
            etAud.setText(bid.roomName);
            int index = 0;
            for(String str : dataStatus){
                if(str.equals(bid.roomName)){
                    spStatus.setSelection(index);
                    break;
                }
                index++;
            }
        }
    }

    @Override
    public void onClick(View v) {
       /* switch (v.getId()) {
            case R.id.button123s:
                Intent intent = new Intent(this, SportListOff.class);
                startActivity(intent);
                break;
            default:
                break;
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Сохранить изменения");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Сохранить изменения")){
            int response = serverConnect.setStatus(MainActivity.startUrl, MainActivity.sessionKey, Integer.valueOf(this.idRecord), spNameStatus);
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
                //Toast.makeText(getApplicationContext(), "Запись удачно изменена", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, ViewBidActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
