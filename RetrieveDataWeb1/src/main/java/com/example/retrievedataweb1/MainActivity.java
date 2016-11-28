package com.example.retrievedataweb1;


import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


import android.os.Bundle;
import android.app.Activity;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.widget.TextView;

public class MainActivity extends Activity {
TextView tv2, tv1;
Button btnRetrieve;
final String textSource = "http://api.flickr.com/services/feeds/photos_public.gne?id=26648248@N04&lang=en-us&format=atom";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		
		btnRetrieve = (Button) findViewById(R.id.btn_retrieve);

		btnRetrieve.setOnClickListener(new View.OnClickListener() {
			public void onClick (View v) {
				try
				{
				//TO DO
					 URL textUrl;
					  try {
					   textUrl = new URL(textSource);
					   BufferedReader bufferReader = new BufferedReader(new InputStreamReader(textUrl.openStream()));
					   String StringBuffer;
					         String stringText = "";
					   while ((StringBuffer = bufferReader.readLine()) != null) {
					    stringText += StringBuffer;
					   }
					         bufferReader.close();
					         tv2.setText(stringText);
					  } catch (MalformedURLException e) {
					   // TODO Auto-generated catch block
					   e.printStackTrace();
					   tv2.setText(e.toString());
					  } catch (IOException e) {
					   // TODO Auto-generated catch block
					   e.printStackTrace();
					   tv2.setText(e.toString());
					  }

					  tv1.setText("Finished!");



				Toast.makeText(getBaseContext(), "Data Retrieved Successufully", Toast.LENGTH_LONG).show();
				}
				catch (Exception e){
					Toast.makeText(getBaseContext(), "Error Occurred " +e.toString(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
