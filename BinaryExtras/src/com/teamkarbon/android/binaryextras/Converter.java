package com.teamkarbon.android.binaryextras;

import java.lang.reflect.Array;
import java.util.ArrayList;

import com.teamkarbon.android.binaryextras.Quiz.BinaryDigit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class Converter extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.activity_converter);
	    // TODO Auto-generated method stub
	    
	    
	}

	public void ToBinary_Click(View view)
	{
		
	}
	public void ToDecimal_Click(View view)
	{
		
	}
	
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
