package com.example.userrequests;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class BidList extends ListActivity {
    String Title = "Title";
    String Description = "Description";
    String TextId = "BidId";
    static public String U_id = "";
    ServerConnect serverConnect;
    ArrayList<HashMap<String,String>> strings;
    SimpleAdapter adapter;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        U_id = ((TextView)v.findViewById(R.id.textId)).getText().toString();
        ViewBidActivity.idRecord = U_id;
        //Log.d("myDebugTest", "position id: " + U_id);
        Intent intent = new Intent(this, ViewBidActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getListView().setBackgroundResource(R.drawable.ic_launcher);
        serverConnect = new ServerConnect();
        strings = new ArrayList<HashMap<String,String>>();

        List<Bid> bids = serverConnect.getAllBid(MainActivity.startUrl, MainActivity.sessionKey);
        for(Bid bid : bids){
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put(Title, bid.Tema);

            Locale locale = new Locale("ru", "RU");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HH:mm", locale);
            String d =  dateFormat.format(bid.datePost.getTime());

            hm.put(Description, "Дата: " + d + "\tСтатус: " + bid.status);
            hm.put(TextId, String.valueOf(bid.id));
            strings.add(hm);
        }

        adapter = new SimpleAdapter(this, strings, R.layout.list_item,
                new String[] {Title, Description, TextId}, new int[] {R.id.text1, R.id.text2, R.id.textId});

        setListAdapter(adapter);
        //LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(this, R.anim.list_layout_controller);
        //getListView().setLayoutAnimation(controller);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Добавить заявку");
        //menu.add("List Exercises");
        //menu.add("Add Exercis");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("Добавить заявку")){
            Intent intent = new Intent(this, AddBidActivity.class);
            startActivity(intent);
        }
        if(item.getTitle().equals("Add Exercis")){
            //Intent intent = new Intent(this, SportEditActivity.class);
            //startActivity(intent);
        }
        if(item.getTitle().equals("menu")){
            //Intent intent = new Intent(this, MenuLvl.class);
            //startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
