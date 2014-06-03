package com.example.userrequests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.List;

public class AddBidActivity extends Activity implements View.OnClickListener{

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
        menu.add("Добавить");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Добавить")){



            Intent intent = new Intent(this, BidList.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
