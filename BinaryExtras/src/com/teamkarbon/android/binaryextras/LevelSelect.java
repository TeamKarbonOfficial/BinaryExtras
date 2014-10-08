package com.teamkarbon.android.binaryextras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAdListener;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAdType;
import com.flurry.android.FlurryAds;
import com.flurry.android.FlurryAgent;

public class LevelSelect extends Activity implements FlurryAdListener {

	// Declare
	Spinner spinner;
	TextView LevelView;
	Button AddLevelButton, SubLevelButton;
	RadioGroup radioGroup1;
	RadioButton radioBD, radioDB;
	
	FrameLayout mBanner;
    private String adSpace="MediatedBannerBottom";

	ArrayAdapter<CharSequence> adapter;

	int LevelValue = 1;
	int NoOfQns = 0;
    String Mode = "";

    long prevNanoSeconds = 1337;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBanner = (FrameLayout)findViewById(R.id.banner);
		
		setContentView(R.layout.activity_level_select);

		// Initialize
		AddLevelButton = (Button) findViewById(R.id.AddLevelButton);
		SubLevelButton = (Button) findViewById(R.id.SubLevelButton);
		LevelView = (TextView) findViewById(R.id.LevelView);
		radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
		radioBD = (RadioButton) findViewById(R.id.radioBD);
		radioDB = (RadioButton) findViewById(R.id.radioDB);
		spinner = (Spinner) findViewById(R.id.spinner1);

        adapter = ArrayAdapter.createFromResource(this, R.array.noOfQnsSpinnerList, R.layout.custom_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

        Mode = "Bin to Dec";

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				switch (position) {
				case 0:
					//Nothing, for instructions
					NoOfQns = 0;
					break;
				case 1:
					NoOfQns = 5;
					break;
				case 2:
					NoOfQns = 10;
					break;
				case 3:
					NoOfQns = 20;
					break;
				case 4:
					NoOfQns = 30;
					break;
				case 5:
					NoOfQns = 40;
					break;
				case 6:
					NoOfQns = 50;
					break;
				case 7:
					NoOfQns = 100;
					break;
				case 8:
					NoOfQns = 500;
					break;
				case 9:
					NoOfQns = 1000;
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg1) {
				// Do nothing.
			}
		});

		addListenerOnradioGroup1();
		addListenerOnButtonAddLevelButton();
		addListenerOnButtonSubLevelButton();
	}

	// RadioGroup Listener
	private void addListenerOnradioGroup1() {
		radioGroup1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {

				// Checking RadioButton Selected
				if (radioBD.isChecked()) {
					Mode = "Bin to Dec";
				} else if (radioDB.isChecked()) {
                    Mode = "Dec to Bin";
				}

			}
		});
	}

	// AddLevelButton Listener
	private void addListenerOnButtonAddLevelButton() {
		AddLevelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (LevelValue < 9) {
					LevelValue++;
				} else {
                    if(System.nanoTime() - prevNanoSeconds > 1 * Math.pow(10,9)) { //This line prevents the queueing up of too many notifs.
                        Toast.makeText(getApplicationContext(), "[ERROR] Values Between 1 - 9 Only!", Toast.LENGTH_SHORT).show();
                        prevNanoSeconds = System.nanoTime();
                    }
				}
				LevelView.setText(String.valueOf(LevelValue));
			}
		});
	}
	
	// SubLevelButton Listener
	private void addListenerOnButtonSubLevelButton() {
		SubLevelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (LevelValue > 1) {
					LevelValue--;
				} else {
                    if(System.nanoTime() - prevNanoSeconds > 1 * Math.pow(10,9)) {
                        Toast.makeText(getApplicationContext(), "[ERROR] Values Between 1 - 9 Only!", Toast.LENGTH_SHORT).show();
                        prevNanoSeconds = System.nanoTime();
                    }
				}
				LevelView.setText(String.valueOf(LevelValue));
			}
		});
	}
	
	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, "VPS2QCRRB3MQ8RPTD3SB");
		FlurryAds.setAdListener(this);
        FlurryAds.fetchAd(this, adSpace, mBanner, FlurryAdSize.BANNER_BOTTOM);
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAds.removeAd(this, adSpace, mBanner);
        FlurryAgent.onEndSession(this);
	}
	
	@Override 
    public void spaceDidReceiveAd(String adSpace) {
        FlurryAds.displayAd(this, adSpace, mBanner);
	}

	@Override
	public boolean shouldDisplayAd(String adSpace, FlurryAdType arg1) {
		return true;
	}

	// On Start! button click
	public void start_click(View view) {
		if (NoOfQns != 0) {
			Intent intent = new Intent(LevelSelect.this, Quiz.class);
			intent.putExtra("question count", NoOfQns);
			intent.putExtra("level", LevelValue);
			intent.putExtra("mode", Mode);
			android.util.Log.i("MODELOG", "MODE: " + Mode);
			startActivity(intent);
		} else {
			Toast.makeText(getApplicationContext(), "[ERROR] Must select number of questions first!", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.level_select, menu);
		return true;
	}

	@Override
	public void onAdClicked(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdClosed(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdOpened(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onApplicationExit(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRenderFailed(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRendered(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onVideoCompleted(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void spaceDidFailToReceiveAd(String arg0) {
		// TODO Auto-generated method stub
		
	}

}
