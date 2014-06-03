package com.example.userrequests;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.List;

public class ViewBidActivity extends Activity implements View.OnClickListener{

    private ServerConnect serverConnect;
    private EditText etTema;
    private EditText etTextBid;
    private EditText etPO;
    private EditText etNamePO;
    private EditText etSeti;
    private EditText etWorkstation;
    private EditText etAud;

    public static String idRecord = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bid);
        serverConnect = new ServerConnect();

        etTema = (EditText)findViewById(R.id.etTema);
        etTextBid = (EditText)findViewById(R.id.etTextBid);
        etPO = (EditText)findViewById(R.id.etPO);
        etNamePO = (EditText)findViewById(R.id.etNamePO);
        etSeti = (EditText)findViewById(R.id.etSeti);
        etWorkstation = (EditText)findViewById(R.id.etWorkstation);
        etAud = (EditText)findViewById(R.id.etAud);

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
        menu.add("Редактировать запись");
        menu.add("Удалить запись");
        if(MainActivity.idAdmin){
            menu.add("Сменить статус");
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, BidList.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Редактировать запись")){
            EditBidActivity.idRecord = this.idRecord;
            Intent intent = new Intent(this, EditBidActivity.class);
            startActivity(intent);
        }
        if(item.getTitle().equals("Удалить запись")){
            Boolean isBidRemove = serverConnect.removeBid(MainActivity.startUrl, MainActivity.sessionKey, Integer.valueOf(this.idRecord));

            if(isBidRemove){
                Toast.makeText(getApplicationContext(), "Запись удалена!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "Ошибка удаления записи!", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(this, BidList.class);
            startActivity(intent);
        }
        if(item.getTitle().equals("Сменить статус")){
            SetStatusBidActivity.idRecord = this.idRecord;
            Intent intent = new Intent(this, SetStatusBidActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
