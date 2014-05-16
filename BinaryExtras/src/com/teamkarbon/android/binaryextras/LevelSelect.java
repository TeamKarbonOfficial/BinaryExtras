package com.teamkarbon.android.binaryextras;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class LevelSelect extends Activity {

	Spinner spinner;
	
	ArrayAdapter<CharSequence> adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    spinner = (Spinner) findViewById(R.id.spinner1);
	    
	    //FIXME i need help...
	    //adapter = ArrayAdapter.createFromResource(this, R.string.noOfQnsSelect, android.R.layout.simple_spinner_item);
	}

}
