package com.subzerodigital.takenote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.subzerodigital.entity.NoteItem;

public class HomeListActivity extends Activity {

	private static int HOME_ACTION_BAR_LAUNCH_CODE = 100;
	private static int HOME_LIST_LAUNCH_CODE = 101;
	private int currentEditing = -1;
	
	private List<NoteItem> homeList = new ArrayList<NoteItem>();
	private ListView homeListView = null;
	//event handler declare
	private OnItemClickListener homeListClickListener = new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intentLunchEditExisting = new Intent(view.getContext(), EditNoteActivity.class);
			intentLunchEditExisting.putExtra("noteToEdit", homeList.get(position));
			currentEditing = position;
			startActivityForResult(intentLunchEditExisting, HOME_LIST_LAUNCH_CODE);
		}
		
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_list);
		//get the listView
		homeListView = (ListView) findViewById(R.id.notesListView);
		//event handler 
		homeListView.setOnItemClickListener(homeListClickListener);
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
			//add dummy one
			/*
			homeList.add(new NoteItem("new one", new Date(), "body text"));
			reRenderList(homeList);
			*/
			//create new intent and navigate to it
			Intent intentLunchEdit = new Intent(this, EditNoteActivity.class);
			//startActivity(intentLunchEdit);
			startActivityForResult(intentLunchEdit, HOME_ACTION_BAR_LAUNCH_CODE);
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
		homeListView.setAdapter(arrayAdpt);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		Log.i("subzero",resultCode+"");
		Log.i("subzero", requestCode+"");
		
		if(resultCode==RESULT_CANCELED){
			Log.i("subzero","do not bother, canceled");
			return;
		}
		
		try {
			Serializable rawNote = data.getSerializableExtra("newNote");
			if(rawNote!=null){
				
				if(requestCode==HOME_LIST_LAUNCH_CODE){
					homeList.set(currentEditing, (NoteItem)rawNote);
					//reset after done editing
					currentEditing = -1;
				}else if(requestCode == HOME_ACTION_BAR_LAUNCH_CODE){
					homeList.add((NoteItem)rawNote);
				}
				reRenderList(homeList);
			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
