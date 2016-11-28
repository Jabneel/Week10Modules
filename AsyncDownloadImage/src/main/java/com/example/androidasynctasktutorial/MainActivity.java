package com.example.androidasynctasktutorial;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {
TextView tv;
URL url1; 
URL url2;
URL url3;
int k = 0;
DownloadFilesTask dw;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.tvUpdate);
		
		try {
			url1  = new URL("http://fblog.futurebrand.com/wp-content/uploads/2013/10/android-vs-apple-wallpaper-wallpaper-ranpict.jpg");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			url2 = new URL("http://images.wikia.com/fantendo/images/6/6e/Small-mario.png");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			url3 = new URL("http://www.freegreatdesign.com/files/images/8/3533-crystal-rainbow-apple-icon-png-1.jpg");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dw = new DownloadFilesTask();
	}

private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

		@Override
		protected Long doInBackground(URL... urls) {
			int count = urls.length;
			
	         long totalSize = 0;
	         for (int i = 0; i < count; i++) {
	             totalSize += DownloadFile(urls[i]);
	             publishProgress((int) ((i / (float) count) * 100));
	             // Escape early if cancel() is called
	             if (isCancelled()) break;
	         }
	         return totalSize;
		}

		private long DownloadFile(URL url) {
			// TODO Auto-generated method stub
			int total = 0;
			try {


				URLConnection conn = url.openConnection();
				if (!(conn instanceof HttpURLConnection))
					throw new IOException("Not an HTTP connection");


				HttpURLConnection httpConn  = (HttpURLConnection) conn;

				//httpConn.setAllowUserInteraction(false);
				//httpConn.setInstanceFollowRedirects(true);
				httpConn.setRequestMethod("GET");
				httpConn.connect();

				int response = httpConn.getResponseCode();
					if (response == HttpURLConnection.HTTP_OK) {
						InputStream input = httpConn.getInputStream();

						// download the file
						String[] path = url.getPath().split("/");
						String imageName = path[path.length - 1];
						String PATH = Environment.getExternalStorageDirectory()+ "/DownLoad/" ;
						File folder = new File(PATH);
						folder.mkdirs();
						String fileName = imageName;
						OutputStream output = new FileOutputStream(folder+"/"+fileName);

						byte data[] = new byte[1024];

						int count;
						while ((count = input.read(data)) != -1) {
							total += count;
							output.write(data, 0, count);
						}
						output.flush();
						output.close();
						input.close();

					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				return total;
		}

		
		
		protected void onPostExecute(Long result) {
			Toast.makeText(MainActivity.this, "Downloaded " + result + " bytes", Toast.LENGTH_LONG).show();
		}
		
		
		protected void onProgressUpdate(Integer... values) {
	        Log.d("MyAsyncTask","onProgressUpdate - " + values[0]);
	        int i = values.length;
	       
	        for (int ii = 0; ii< i;ii++){
	        	k= k+ values[ii];
	        }
	        
	        tv.setText(String.valueOf(k));
	    }

		
		protected void onCancelled() {
			// stop the progress
			tv.setText("Download stopped!!");
			Toast.makeText(MainActivity.this, "on Cancelled called", Toast.LENGTH_LONG).show();
		
		}
	}

	
	public void CancelDownload(View view){
		Toast.makeText(MainActivity.this, "CancelDownload", Toast.LENGTH_LONG).show();
		dw.cancel(true);
		
	}

	public void downloadImage(View view) {
				
		//	new DownloadFilesTask().execute(url1, url2, url3);
	  dw.execute(url1, url2, url3);

	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
