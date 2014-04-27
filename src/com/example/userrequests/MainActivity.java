package com.example.userrequests;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);String[] itemList = { "«а¤вки", "јвторизаци¤" };
		
		ListView mainList = (ListView) this.findViewById(R.id.main);
		// создаем адаптер
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, itemList);
		mainList.setAdapter(adapter);
		
		
		mainList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				switch (position) {
				case 0: {
					Intent intent = new Intent(MainActivity.this,
							RequestsActivity.class); // описывает активити, которое
													// будем запускать
					startActivity(intent);
				}
					break;

				case 1: {
					Intent intent = new Intent(MainActivity.this,
							AutorizationActivity.class); // описывает активити,
													// которое
													// будем запускать
					startActivity(intent);
				}
					break;
				
				

				}
				;
			}
		});
	}

	//@Override
	//public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main, menu);
		//return true;
	//}

	}

