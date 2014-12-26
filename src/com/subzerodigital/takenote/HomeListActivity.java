package com.subzerodigital.takenote;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.subzerodigital.entity.NoteItem;

public class HomeListActivity extends Activity {
	
	private List<NoteItem> homeList = new ArrayList<NoteItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_list);
		//dummy data
		homeList.add(new NoteItem("This is 1st", new Date(), "body text1"));
		homeList.add(new NoteItem("2nd followd", new Date(), "body text2"));
		homeList.add(new NoteItem("last one is here", new Date(), "body text3"));
		reRenderList(homeList);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_list, menu);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.AddNewNote) {
			//mean we  
			homeList.add(new NoteItem("new one", new Date(), "body text"));
			reRenderList(homeList);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void reRenderList(List<NoteItem> homeList) {
		List<String> titles = new ArrayList<String>();
		for (NoteItem noteItem : homeList) {
			titles.add(noteItem.getTitle());
		}
		ArrayAdapter<String> arrayAdpt = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1,titles);
		ListView listView = (ListView) findViewById(R.id.notesListView);
		listView.setAdapter(arrayAdpt);
	}
}
