package com.subzerodigital.takenote;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.subzerodigital.entity.NoteItem;

public class EditNoteActivity extends Activity {

	public static final int RESULT_DELETE = -500;

	// var to track / flags
	private boolean isInEditMode = true;
	
	private Button saveButton;
	private Button cancelButton;
	private EditText titleField;
	private EditText dateField;
	private EditText textField;
	
	private void populateField(NoteItem noteToEdit){
		titleField.setText(noteToEdit.getTitle());
		textField.setText(noteToEdit.getText());
		dateField.setText(formatDate(noteToEdit.getDate()));
	}
	
	private void setToEditMode(boolean flag){
		titleField.setEnabled(flag);
		textField.setEnabled(flag);
		dateField.setEnabled(flag);
		isInEditMode = flag;
		if(flag){
			saveButton.setText("save");
		}else{
			saveButton.setText("edit");
		}
	}
	
	private String formatDate(Date date){
		DateFormat dateFormat = new SimpleDateFormat(
				"dd-MM-yyyy HH:mm:ss");
		// String dateString = dateFormat.format(new Date());
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String dateString = dateFormat.format(date);
		return dateString;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_note);
		
		// get all the buttons
		saveButton = (Button) findViewById(R.id.saveButton);
		cancelButton = (Button) findViewById(R.id.cancelButton);
		titleField = (EditText) findViewById(R.id.titleField);
		dateField = (EditText) findViewById(R.id.dateField);
		textField = (EditText) findViewById(R.id.textField);
		
		//check if there's data coming in
		Intent intent = getIntent();
		Serializable noteToEdit = intent.getSerializableExtra("noteToEdit");
		if(noteToEdit!=null){
			//Log.i("subzero","I've got some data to edit-I am from the list click");
			populateField((NoteItem)noteToEdit);
			setToEditMode(false);
		}else{
			//Log.i("subzero","no edit data - I am from add new");
		}
		
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(RESULT_CANCELED, new Intent());
				finish();
			}
		});

		saveButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(isInEditMode){
					// set the time stamp
					DateFormat dateFormat = new SimpleDateFormat(
							"dd-MM-yyyy HH:mm:ss");
					// String dateString = dateFormat.format(new Date());
					dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
					Date current = Calendar.getInstance().getTime();
					String dateString = dateFormat.format(current);
					dateField.setText(dateString);

					// finish the current activity and pass back the note item
					Intent returnIntent = new Intent();
					NoteItem newNote = new NoteItem(
							titleField.getText().toString(), current, textField
									.getText().toString());
					returnIntent.putExtra("newNote", newNote);
					setResult(RESULT_OK, returnIntent);
					finish();
				}else{
					setToEditMode(true);
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*
		Log.i("subzero",id+"");
		Log.i("subzero",R.id.deleteNote+"");
		*/
		
		if (id == R.id.deleteNote) {
			//TODO: ASK WHY, why static class could also instanciate object
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.are_you_sure);
			builder.setTitle(R.string.please_confirm);
			builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent returnIntent = new Intent();
					setResult(RESULT_DELETE,returnIntent);
					finish();
				}
			});
			builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.show();
			return true;
		}else{
			return false;
		}
	}
}
