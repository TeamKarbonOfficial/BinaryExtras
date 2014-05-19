package com.teamkarbon.android.binaryextras;

import com.flurry.android.FlurryAdListener;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAdType;
import com.flurry.android.FlurryAds;
import com.flurry.android.FlurryAgent;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements FlurryAdListener {

	//Ads Declare
	FrameLayout mBanner;
    private String adSpace="MediatedBannerBottom";
	
	// Declare
	Button QuizButton, ConverterButton;
	TextView TitleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBanner = (FrameLayout)findViewById(R.id.banner);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void Quiz_Click(View view) {
		Intent intent = new Intent(MainActivity.this, Quiz.class);
		MainActivity.this.startActivity(intent);
	}

	public void Converter_Click(View view) {
		Intent intent = new Intent(MainActivity.this, LevelSelect.class);
		MainActivity.this.startActivity(intent);
	}
	
    @Override 
    public void spaceDidReceiveAd(String adSpace) {
        FlurryAds.displayAd(this, adSpace, mBanner);
    }
	
	@Override
    public boolean shouldDisplayAd(String adSpace, FlurryAdType arg1) {
       return true;
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
