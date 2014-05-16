package com.teamkarbon.android.binaryextras;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	// Declare
	Button QuizButton, ConverterButton;
	TextView TitleView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
		Intent intent = new Intent(MainActivity.this, Converter.class);
		MainActivity.this.startActivity(intent);
	}
}
