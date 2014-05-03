package com.example.userrequests;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RequestsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_requests);

        ServerConnect sc = new ServerConnect();
        sc.getAllRequestsClass("http://servicetech.apphb.com", "ssb9CTZD2e3SZyaP7PhUn4Yl7js4EG5z");


        Button addButton = (Button) this.findViewById(R.id.addButton);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequestsActivity.this,
                        AddRequestActivity.class); // описывает
                startActivity(intent);
            }
        });
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.requests, menu);
		return true;
	}

}
