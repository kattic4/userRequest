package com.example.userrequests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.List;

public class EditBidActivity extends Activity implements View.OnClickListener{

    private ServerConnect serverConnect;
    private EditText etTema;
    private EditText etTextBid;
    private EditText etPO;
    private EditText etNamePO;
    private EditText etSeti;
    private EditText etWorkstation;
    private Spinner spNumAud;
    public String[] data;
    private String spNameAud;

    public static String idRecord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bid);
        serverConnect = new ServerConnect();

        etTema = (EditText)findViewById(R.id.etTema);
        etTextBid = (EditText)findViewById(R.id.etTextBid);
        etPO = (EditText)findViewById(R.id.etPO);
        etNamePO = (EditText)findViewById(R.id.etNamePO);
        etSeti = (EditText)findViewById(R.id.etSeti);
        etWorkstation = (EditText)findViewById(R.id.etWorkstation);
        spNumAud = (Spinner)findViewById(R.id.spNumAud);
        spNameAud = "";

        List<Spravochnik> spAud = serverConnect.getSpravochniki(MainActivity.startUrl);

        data = new String[spAud.size()];
        int index = 0;
        for (Spravochnik spA : spAud) {
            data[index] = spA.spravochnicValue;
            index++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spNumAud.setAdapter(adapter);
        // заголовок
        spNumAud.setPrompt("Аудитории");

        // выделяем элемент
        //spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spNumAud.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                //Log.d("myDebugTest", "nameAuditory: " + data[position]);
                spNameAud = data[position];
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
            index = 0;
            for(String str : data){
                if(str.equals(bid.roomName)){
                    spNumAud.setSelection(index);
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
            int response = serverConnect.editBid(MainActivity.startUrl, MainActivity.sessionKey,Integer.valueOf(this.idRecord), spNameAud, etTextBid.getText().toString(),
                    etTema.getText().toString(), etPO.getText().toString(), etNamePO.getText().toString(), etSeti.getText().toString(), etWorkstation.getText().toString());
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
                //Toast.makeText(getApplicationContext(), "Запись удачно изменена", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, ViewBidActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
