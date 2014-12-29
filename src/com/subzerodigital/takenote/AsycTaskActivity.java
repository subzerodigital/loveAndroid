package com.subzerodigital.takenote;

import java.util.ArrayList;
import java.util.List;

import com.subzerodigital.http.HttpManager;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AsycTaskActivity extends Activity {
	
	private ProgressBar pb;
	private List<MyAsycTask> trackingParralleTasks = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asyc_task);
		//get reference
		pb = (ProgressBar) findViewById(R.id.progressBar1);
		pb.setVisibility(ProgressBar.INVISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.asyc_task, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			if(isOnline()){
				requestData("http://services.hanselandpetal.com/feeds/flowers.xml");
			}else{
				//warn the user-->using toast message?
				Toast.makeText(this, "Internet connection not available", Toast.LENGTH_LONG).show();
			}
		}
		return true;
	}

	private void requestData(String url) {
		MyAsycTask task = new MyAsycTask();
		//task.execute("1","2","3");
		task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
	}
	
	//check if online --> can go to a util function
	protected boolean isOnline(){
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo!=null && netInfo.isConnected()) {
			return true;
		} else {
			return false;	
		}
	}
	
	private class MyAsycTask extends AsyncTask<String, String, String>{
		
		@Override
		protected String doInBackground(String... params) {
			//this would return to onPostExcute
			/*
			String msg = "";
			for (String param : params) {
				msg += param;
				msg += " ";
				//mocking 	
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//this would call onProgressUpdate
				publishProgress(param);
			}*/
			String data = HttpManager.getData(params[0]);
			//return msg;
			return data;
		}
		
		@Override
		protected void onPreExecute() {
			Log.i("subzero","start");
			//tracking parralle tasks
			if(trackingParralleTasks.size()==0){
				pb.setVisibility(View.VISIBLE);
			}
			trackingParralleTasks.add(this);
		}
		
		@Override
		protected void onPostExecute(String result) {
			//Log.i("subzero",result);
			Log.i("subzero","end");
			Log.i("subzero",result);
			
			//tracking parralle --> remove the spinner on the right time
			trackingParralleTasks.remove(this);
			if(trackingParralleTasks.size()==0){
				pb.setVisibility(View.INVISIBLE);
			}
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			Log.i("subzero","working with "+values[0]);
		}
		
	}
}
