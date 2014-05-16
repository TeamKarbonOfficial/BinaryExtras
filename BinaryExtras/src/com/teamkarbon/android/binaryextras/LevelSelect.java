package com.teamkarbon.android.binaryextras;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.view.*;
import android.view.View.OnClickListener;

public class LevelSelect extends Activity{

	Spinner spinner;
	
	ArrayAdapter<CharSequence> adapter;
	
	public int LevelValue = 0;
	public int NoOfQns = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    // TODO Auto-generated method stub
	    spinner = (Spinner) findViewById(R.id.spinner1);
	    
	    //FIXME i need help...
	    adapter = ArrayAdapter.createFromResource(this, R.array.noOfQnsSpinnerList, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);//I hope this works...
	    
	    spinner.setOnItemSelectedListener(new OnItemSelectedListener()
        {           
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) 
            {
            	switch(position)
            	{
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
            public void onNothingSelected(AdapterView<?> arg1)
            {
            	
            }
        });
	}
	public void meep(View view)//On Start! button click
	{
		
	}
	
}
