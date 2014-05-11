package com.teamkarbon.android.binaryextras;

import com.teamkarbon.android.binaryextras.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class Quiz extends Activity {
	
	public int givenValue;
	public String instructions;
	
	public TextView instructionView;
	public Button enterButton;
	public EditText input;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_quiz);
		instructionView = (TextView) findViewById(R.id.Instructions);
		enterButton = (Button) findViewById(R.id.THEbutton);
		input = (EditText) findViewById(R.id.Input);
		
		//On startup,
		instructionView.setText("Press Button to start!");
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			
			return false;
		}
	};
}
