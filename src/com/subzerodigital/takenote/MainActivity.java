package com.subzerodigital.takenote;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
	
	//var to track / flags
	private boolean isInEditMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //get all the buttons
        final Button saveButton = (Button)findViewById(R.id.saveButton);        
        final EditText titleField = (EditText)findViewById(R.id.titleField);
        final EditText dateField = (EditText)findViewById(R.id.dateField);
        final EditText textField = (EditText)findViewById(R.id.textField);
        
        saveButton.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				// saveButton.setText("Clicked");
				if(isInEditMode){
					isInEditMode = false;
					titleField.setEnabled(false);
					dateField.setEnabled(false);
					textField.setEnabled(false);
					saveButton.setText("Edit");
					//set the time stamp
					DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					//String dateString = dateFormat.format(new Date());
					dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
					String dateString = dateFormat.format(Calendar.getInstance().getTime());
					dateField.setText(dateString);
					
					
				}else{
					isInEditMode = true;
					titleField.setEnabled(true);
					dateField.setEnabled(true);
					textField.setEnabled(true);
					saveButton.setText("Save");
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
