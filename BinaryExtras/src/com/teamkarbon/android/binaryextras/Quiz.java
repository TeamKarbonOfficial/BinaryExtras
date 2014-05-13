package com.teamkarbon.android.binaryextras;

import java.util.ArrayList;

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
	
	public int givenValue;//In decimal, of course
	public String instructions;
	
	public TextView instructionView;
	public Button enterButton;
	public EditText input;
	
	public boolean HazTehGaemStahrtad;
	public boolean ConvertFromBinaryToDecimal;//If true, the question given will require one to convert from
											  //Binary to decimal.
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_quiz);
		instructionView = (TextView) findViewById(R.id.Instructions);
		enterButton = (Button) findViewById(R.id.THEbutton);
		input = (EditText) findViewById(R.id.Input);
		HazTehGaemStahrtad = false;
		
		//On startup,
		instructionView.setText("Press Button to start!");
		
		//Set event
		enterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(HazTehGaemStahrtad)
                {
                	
                }
                else
                {
                	HazTehGaemStahrtad = true;
                }
            }
        });
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
	public String ToBinary(float decimalvalue, int startingPowerOfTwo)//I shall make my own for the fun of it.
	{
		int maxpoweroftwo = startingPowerOfTwo;//Gg. GG. (This var is a temporary store, btw)
		float decimalvalueleft = decimalvalue;//This is used as a temporary variable for the repeated decrements for this.
		int NumberOfDecimalPoints = 0;
		ArrayList <BinaryDigit> ListOfBinaryDigits = new ArrayList<BinaryDigit>();
		String temporaryBinaryAsString = "";
		
		//startingPowerOfTwo is the minimum power of two you want to check. (Prevents lag if you know you only need
		//to start checking from 1. This value is usually zero or less... (2^0 = 1)
		
		/*
		 * How to convert:
		 * Step 0: Make sure that decimalvalue != 0. Seriously.
		 * Step 1: Find the largest possible power of two without exceeding the decimalvalue
		 * Step 2: Subtract the decimalvalue by the largest possible power of two
		 * Step 3a: Repeat steps 1 and 2 until decimalvalue is 0.
		 * Step 3b: Check for recurring dinals (binary decimals) by
		 * Step 3bi: Loading one binary digit into an array as a set for the previous 9 sets
		 * Step 3bii: Check if all 9 digits in the sets are the same
		 * Step 3biii: If not, repeat 3bi and 3bii where
		 * 						binary digits in the set = x; number of sets = y;
		 *                      2 <= x <= 9 and 7 < xy < 13
		 * Step 3c: If there are recurring decimals, pointlessly repeat them to at least 80dp.
		 */

		//And now.... the code.
		
		//Step 0
		if(decimalvalue == 0) return "0";
		
		//Step 3a
		while(decimalvalueleft > 0 && NumberOfDecimalPoints < 90)
		{
			//Step 1
			for(int poweroftwo = startingPowerOfTwo; ; poweroftwo++)
			{
				if( (2 ^ poweroftwo) > decimalvalueleft)
				{
					maxpoweroftwo = poweroftwo - 1;
					break;
				}
			}
			//Step 2
			decimalvalueleft -= 2 ^ maxpoweroftwo;
			
			//Add the digit
			ListOfBinaryDigits.add(new BinaryDigit(maxpoweroftwo, 1));
			if(maxpoweroftwo < 0)
				NumberOfDecimalPoints++;
			
			//Make it into a string as zero's are not inside the arraylist
			for(BinaryDigit bd : ListOfBinaryDigits)
			{
				if(bd.powerOfTwo >= 0)
				{
					//Add it to the string respectively.
					//TODO BUT I DON'T KNOW HOW XP
				}
			}
			//TODO: Put Step 3b here...
			for(int NoOfDigitsInSet = 1; NoOfDigitsInSet <= 10; NoOfDigitsInSet ++)
			{
				
			}
		}
		
		return null;
	}
	public class BinaryDigit
	{
		public int powerOfTwo;//Where is the digit located?
		public int Value;//One or Zero
		
		public BinaryDigit(int powerOfTwo, int BinaryValue)
		{
			this.powerOfTwo = powerOfTwo;
			this.Value = BinaryValue;
		}
	}
}
