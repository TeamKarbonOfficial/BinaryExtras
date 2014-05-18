package com.teamkarbon.android.binaryextras;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import com.flurry.android.FlurryAgent;
import com.teamkarbon.android.binaryextras.util.SystemUiHider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
	public Intent currentIntent;
	
	public int givenValue;//In decimal, of course
	public String instructions;
	
	public TextView instructionView;
	public Button enterButton;
	public EditText input;
	
	public int level = 0;
	public int noOfQns = 0;
	public boolean binToDec, decToBin;
	
	public int currentQn = 0;
	
	public boolean HazTehGaemStahrtad;
	public boolean ConvertFromBinaryToDecimal;//If true, the question given will require one to convert from
											  //Binary to decimal.
	public boolean QuestionMode;
	
	public Random rndGen;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_quiz);
		instructionView = (TextView) findViewById(R.id.Instructions);
		enterButton = (Button) findViewById(R.id.THEbutton);
		input = (EditText) findViewById(R.id.Input);
		HazTehGaemStahrtad = false;
		
		currentIntent = getIntent();
		level = currentIntent.getIntExtra("level", 1);
		noOfQns = currentIntent.getIntExtra("question count", 5);
		
		instructionView.setText("Difficulty Level: " + level + " | Total Questions: " + noOfQns
				+ ". Press the derpy button to start!");
		
		rndGen = new Random();
		
		QuestionMode = true;
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
	
	public void derp_click(View view)
	{
		double properAnswer;
		boolean IsBinToDecUsed;
		if(QuestionMode)//Time to give a question!
		{
			double Min = 0, Max = 0; int SmallestPowerOfTwo = 0;
			boolean WithDecimal;
			
			double ValueToConvert;
			//TODO: A lot of work here!
			switch(level)
			{
			case 1:
				Min = Math.pow(2, 0);
				Max = Math.pow(2, 3);
				SmallestPowerOfTwo = 0;
				break;
			case 2:
				Min = Math.pow(2, 0);
				Max = Math.pow(2, 5);
				SmallestPowerOfTwo = 0;
				break;
			case 3:
				Min = Math.pow(2, 0);
				Max = Math.pow(2, 7);
				SmallestPowerOfTwo = 0;
				break;
			case 4:
				Min = Math.pow(2, 0);
				Max = Math.pow(2, 8);
				SmallestPowerOfTwo = 0;
				break;
			case 5:
				Min = Math.pow(2, -1);
				Max = Math.pow(2, 8);
				SmallestPowerOfTwo = -1;
				break;
			case 6:
				Min = Math.pow(2, -1);
				Max = Math.pow(2, 9);
				SmallestPowerOfTwo = -1;
				break;
			case 7:
				Min = Math.pow(2, -2);
				Max = Math.pow(2, 9);
				SmallestPowerOfTwo = -2;
				break;
			case 8:
				Min = Math.pow(2, -3);
				Max = Math.pow(2, 10);
				SmallestPowerOfTwo = -3;
				break;
			case 9:
				Min = Math.pow(2, -5);
				Max = Math.pow(2, 20);
				SmallestPowerOfTwo = -5;
				break;
			}
			if(binToDec && decToBin)
			{
				if(rndGen.nextInt(1) == 0)//binToDec type
				{
					IsBinToDecUsed = true;
					if(SmallestPowerOfTwo == 1)
						instructionView.setText("What is " + ToBinary(rndGen.nextInt((int) Max), 90) + " in decimal?");
					else
					{
						double tempDouble = 0;
						double SmallestIntervalUsed = 0;
						
						SmallestIntervalUsed = Math.pow(2, -1 * rndGen.nextInt(0 - SmallestPowerOfTwo));
						
						//FIXME CREATE ALGORITHM TO MAKE A CORRECTLY RND GEN NUMBER.
						//NOTE MIN IS NOT NEGATIVE. IT's JUST 2^SMALLESTPOWEROFTWO
						tempDouble = rndGen.nextInt((int) (Max / Min)) * Math.pow(2, SmallestPowerOfTwo);
						instructionView.setText("What is " + ToBinary(tempDouble, 90) + " in decimal?");
					}
				}
				else//decToBin type
				{
					IsBinToDecUsed = false;
				}
			}
			else if(decToBin)
			{
				IsBinToDecUsed = false;
			}
			else if(binToDec)
			{
				IsBinToDecUsed = true;
			}
			QuestionMode = false;
		}
		else//Time to get an answer!
		{
			QuestionMode = true;
		}
		if(currentQn == noOfQns)//Last question.. display stats after this
		{
			
		}
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
	
	
	public String ToBinary(double decimalvalue, int AccuracyInDP)//I shall make my own for the fun of it.
	{
		int maxpoweroftwo = -AccuracyInDP;//Gg. GG. (This var is a temporary store, btw)
		double decimalvalueleft = decimalvalue;//This is used as a temporary variable for the repeated decrements for this.
		int NumberOfDecimalPoints = 0;
		int HighestPowerOfTwo = 0;
		boolean FirstRun = true;
		ArrayList <BinaryDigit> ListOfBinaryDigits = new ArrayList<BinaryDigit>();
		String temporaryBinaryAsString = "";
		
		//Make sure that startingPowerOfTwo is NOT above zero!!
		if(AccuracyInDP < 0) AccuracyInDP = 0;
		
		//startingPowerOfTwo is the minimum power of two you want to check. (Prevents lag if you know you only need
		//to start checking from 1. This value is usually zero or less... (2^0 = 1)
		
		/*
		 * How to convert:
		 * Step 0: Make sure that decimalvalue != 0. Seriously.
		 * Step 1: Find the largest possible power of two without exceeding the decimalvalue
		 * Step 2: Subtract the decimalvalue by the largest possible power of two
		 * Step 3a: Repeat steps 1 and 2 until decimalvalue is 0.
		 * (I don't think I'll be doing this.... Step 3b: Check for recurring dinals (binary decimals) by
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
		while(decimalvalueleft > 0 && NumberOfDecimalPoints < 80)
		{
			//Step 1
			for(int poweroftwo = -AccuracyInDP; ; poweroftwo++)
			{
				if( (Math.pow(2, poweroftwo)) > decimalvalueleft)
				{
					maxpoweroftwo = poweroftwo - 1;
					break;
				}
                else if ((Math.pow(2, poweroftwo)) == decimalvalueleft)
                {
                    maxpoweroftwo = poweroftwo;
                    break;
                }
			}
			//Step 2
			decimalvalueleft -= Math.pow(2, maxpoweroftwo);
			
			//Add the digit
			ListOfBinaryDigits.add(new BinaryDigit(maxpoweroftwo, 1));
			if(FirstRun)
				HighestPowerOfTwo = maxpoweroftwo;
			FirstRun = false;
			
			if(NumberOfDecimalPoints + maxpoweroftwo < 0)
				NumberOfDecimalPoints = - maxpoweroftwo;
		}
		
		//Make sure maxpoweroftwo is not more than 0 to prevent missing digits before decimal point
		if (maxpoweroftwo > 0) maxpoweroftwo = 0;
		
		//Make sure HighestPowerOfTwo is not less than 0 to prevent missing digits after decimal point which are 0s
		if (HighestPowerOfTwo < 0) HighestPowerOfTwo = 0;
		
		//Create a temporary int array for binary digits in order
		int [] binaryIntArray = new int[HighestPowerOfTwo - maxpoweroftwo + 1];
		if(decimalvalue % 1 != 0)
		{   //Give space for the fullstop.
			binaryIntArray = new int[HighestPowerOfTwo - maxpoweroftwo + 2];
		}
		
		//Make a flag for before and after decimal point numbers during the  foreach loop
		boolean MantissaMode = false;//Mantissa is the part after the decimal point, with negative powers
		
		//Make an int to keep the index of the char which belongs to 2^0 group
		int indexOfZeroPower;
		//Run the for loop to fill in the 1s
		for (BinaryDigit bd : ListOfBinaryDigits)
		{
			if(bd.powerOfTwo > 0)//Check if before or after decimal point
				MantissaMode = false;
			else if (bd.powerOfTwo == 0)
				MantissaMode = false;
			else
				MantissaMode = true;
			
			if(bd.Value == 1)//Which should be always true...
			{
				if(!MantissaMode)
					Array.set(binaryIntArray, HighestPowerOfTwo - bd.powerOfTwo, 1);
				else //Cuz the decimal point takes up one index
					Array.set(binaryIntArray, HighestPowerOfTwo - bd.powerOfTwo + 1, 1);
			}
		}
		//Init indexOfZeroPower
		indexOfZeroPower = HighestPowerOfTwo + 1;
		
		//Fill in all empty spaces with zero (except the space which is supposed to be a "."
		for(int count = 0; count < binaryIntArray.length; count ++)
		{
			if(binaryIntArray[count] != 1 && count != indexOfZeroPower)
				binaryIntArray[count] = 0;
		}
		
		//Fill in decimal point and make the string
		for(int count = 0; count < binaryIntArray.length; count ++)
		{
			if(count == indexOfZeroPower)
				temporaryBinaryAsString += ".";
			if(binaryIntArray[count] == 1)
				temporaryBinaryAsString += "1";
			else if (binaryIntArray[count] == 0)
				temporaryBinaryAsString += "0";
		}
		
		return temporaryBinaryAsString;
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
