package com.teamkarbon.android.binaryextras;

import com.flurry.android.FlurryAgent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.*;

public class LevelSelect extends Activity{

	//Declare
	Spinner spinner;
	TextView levelView;
	Button plus, minus;
	RadioGroup radioGroup1;
	RadioButton radioBD;
	RadioButton radioDB;
	
	ArrayAdapter<CharSequence> adapter;
	
	public int LevelValue = 1;
	public int NoOfQns = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    //Initialize
	    levelView = (TextView) findViewById(R.id.LevelView);
	    radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		radioBD = (RadioButton) findViewById(R.id.radioBD);
		radioDB = (RadioButton) findViewById(R.id.radioDB);
	    spinner = (Spinner) findViewById(R.id.spinner1);
	    
	    //FIXME i need help...
	    adapter = ArrayAdapter.createFromResource(this, R.array.noOfQnsSpinnerList, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);//I hope this works...
	    
	    spinner.setOnItemSelectedListener(new OnItemSelectedListener() {           
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
            	switch(position) {
            	case 0:
            		NoOfQns = 5;
            		break;
            	case 1:
            		NoOfQns = 10;
            		break;
            	case 2:
            		NoOfQns = 20;
            		break;
            	case 3:
            		NoOfQns = 30;
            		break;
            	case 4:
            		NoOfQns = 40;
            		break;
            	case 5:
            		NoOfQns = 50;
            		break;
            	case 6:
            		NoOfQns = 100;
            		break;
            	case 7:
            		NoOfQns = 500;
            		break;
            	case 8:
            		NoOfQns = 1000;
            		break;
            	}
            }       

            @Override
            public void onNothingSelected(AdapterView<?> arg1) {
            	//Do nothing.
            }
        });
	    
		addListenerOnradioGroup1();
	}
	
	//RadioGroup Listener
	private void addListenerOnradioGroup1() {	
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				
				//Checking RadioButton Selected
				if(radioBD.isChecked()) {
					//Do something
				} else if(radioDB.isChecked()) {
					//Do something
				} else {
					//If this happens, there is some serious error :P
				}

			}
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, "VPS2QCRRB3MQ8RPTD3SB");
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}

	// On Start! button click
	public void start_click(View view) {
		Intent intent = new Intent(LevelSelect.this, Quiz.class);
		intent.putExtra("question count", NoOfQns);
		intent.putExtra("level", LevelValue);

		LevelSelect.this.startActivity(intent);
	}

	// On + button click
	public void add_click(View view) {
		if (LevelValue < 9)
			LevelValue++;
		levelView.setText(LevelValue);
	}

	// On - button click
	public void sub_click(View view) {
		if (LevelValue > 1)
			LevelValue--;
		levelView.setText(LevelValue);
	}

}
