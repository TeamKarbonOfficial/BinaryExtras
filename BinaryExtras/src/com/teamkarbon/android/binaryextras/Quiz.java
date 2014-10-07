package com.teamkarbon.android.binaryextras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.flurry.android.FlurryAdListener;
import com.flurry.android.FlurryAdSize;
import com.flurry.android.FlurryAdType;
import com.flurry.android.FlurryAds;
import com.flurry.android.FlurryAgent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import static android.util.Log.i;

public class Quiz extends Activity implements FlurryAdListener {

    public Intent currentIntent;
    public double givenValue;//In decimal, of course
    public TextView instructionView, scoreView;
    public Button enterButton;
    public EditText input;
    public int level = 0;
    public int noOfQns = 0;
    public int noOfQnsCorrect = 0;
    public int currentQn = 0;
    public int score = 0;
    /**
     * Not too sure, but declared it anyway
     */
    //int timeTaken; replaced with
    public long prevNanoSeconds;
    public String ModeString;
    public boolean binToDec, decToBin;
    public boolean ConvertFromBinaryToDecimal;//If true, the question given will require one to convert from
    public boolean QuestionMode;              //Binary to decimal.
    public boolean GameOver;
    public String CorrectAnswer;
    public Random rndGen;
    FrameLayout mBanner;
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
    private String adSpace = "MediatedBannerBottom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mBanner = (FrameLayout) findViewById(R.id.banner);
        instructionView = (TextView) findViewById(R.id.Instructions);
        scoreView = (TextView) findViewById(R.id.ScoreBox);
        enterButton = (Button) findViewById(R.id.THEbutton);
        input = (EditText) findViewById(R.id.Input);
        GameOver = false;

        currentIntent = getIntent();
        level = currentIntent.getIntExtra("level", 1);
        noOfQns = currentIntent.getIntExtra("question count", 5);

        ModeString = currentIntent.getStringExtra("mode");
        i("MODELOG", ModeString);

        if (ModeString.equalsIgnoreCase("bin to dec")) {
            ConvertFromBinaryToDecimal = true;
            binToDec = true;
            decToBin = false;
        }
        if (ModeString.equalsIgnoreCase("Dec to Bin")) {
            ConvertFromBinaryToDecimal = false;
            decToBin = true;
            binToDec = false;
        }

        instructionView.setText("Difficulty Level: " + level + " | Total Questions: " + noOfQns
                + " | Mode: " + ModeString + ". Press the derpy button to start!");


        rndGen = new Random();

        QuestionMode = true;

        //stopwatch = new Stopwatch();
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

    public void derp_click(View view) {
        boolean IsBinToDecUsed = ConvertFromBinaryToDecimal;
        if (GameOver) {
            Intent tempintent = new Intent(Quiz.this, MainActivity.class);
            Quiz.this.startActivity(tempintent);
        }
        if (QuestionMode)//Time to give a question!
        {
            double Min = 0, Max = 0;
            int SmallestPowerOfTwo = 0;

            currentQn++;
            switch (level) {
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
            if (decToBin) {
                if (SmallestPowerOfTwo == 0) {
                    givenValue = rndGen.nextInt((int) Max);
                    CorrectAnswer = String.valueOf(ToBinary(givenValue, 90));
                    instructionView.setText(currentQn + ") What is " + givenValue + " in binary? Key in your answer and " +
                            "tap the derpy button.");
                } else {
                    givenValue = 0;

                    //NOTE MIN IS NOT NEGATIVE. IT's JUST 2^SMALLESTPOWEROFTWO
                    givenValue = rndGen.nextInt((int) (Max / Min)) * Math.pow(2, SmallestPowerOfTwo);
                    CorrectAnswer = String.valueOf(givenValue);
                    instructionView.setText(currentQn + ") What is " + CorrectAnswer + " in binary? " +
                            "Key in your answer and tap the derpy button.");
                }
            } else if (binToDec) {
                if (SmallestPowerOfTwo == 0)
                    CorrectAnswer = String.valueOf(rndGen.nextInt((int) Max));
                givenValue = Double.valueOf(CorrectAnswer);
                instructionView.setText(currentQn + ") What is " + ToBinary(givenValue, 90) + " in decimal?");
            } else {
                givenValue = 0;

                //NOTE MIN IS NOT NEGATIVE. IT's JUST 2^SMALLESTPOWEROFTWO
                givenValue = rndGen.nextInt((int) (Max / Min)) * Math.pow(2, SmallestPowerOfTwo);
                CorrectAnswer = ToBinary(givenValue, 90);
                instructionView.setText(currentQn + ") What is " + CorrectAnswer + " in decimal? " +
                        "Key in your answer and tap the derpy button.");
            }

            QuestionMode = false;
        } else //Time to get an answer!
        {
            //int timeTaken = stopwatch.StopAndGet();

            if (currentQn == noOfQns)//Last question.. display stats after this
            {
                if ((IsBinToDecUsed && input.getText().toString().replaceAll("\\s", "").equals(String.valueOf(givenValue)))
                        || (!IsBinToDecUsed && input.getText().toString().replaceAll("\\s", "").equals(ToBinary(givenValue, 7)))) {

                    score += (1000 * level) / ((System.nanoTime() - prevNanoSeconds) / Math.pow(10, 9));
                    scoreView.setText("Score: " + score);

                    instructionView.setText("Correct! " + noOfQnsCorrect + "/" +
                            noOfQns + " questions right. Press the derpy button to go back.");
                    GameOver = true;
                    QuestionMode = false;

                }
            } else if ((IsBinToDecUsed && input.getText().toString().replaceAll("\\s", "").equals(CorrectAnswer))
                    || (!IsBinToDecUsed && input.getText().toString().replaceAll("\\s", "").equals(CorrectAnswer))) {
                noOfQnsCorrect++;
                instructionView.setText("Correct! Click the button to continue to question " + (currentQn + 1));

                i("INPUTLOG", input.getText().toString().replaceAll("\\s", ""));

                QuestionMode = true;

                score += (1000 * level) / (GetTimeTakenNano() / Math.pow(10, 9));
                scoreView.setText("Score: " + score);
            } else {
                instructionView.setText("Wrong! Correct answer is " + CorrectAnswer + ". Click the button to continue " +
                        "to question " + (currentQn + 1));
                QuestionMode = true;
            }

        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    public String ToBinary(double decimalvalue, int AccuracyInDP)//I shall make my own for the fun of it.
    {
        int maxpoweroftwo = -AccuracyInDP;//Gg. GG. (This var is a temporary store, btw)
        double decimalvalueleft = decimalvalue;//This is used as a temporary variable for the repeated decrements for this.
        int NumberOfDecimalPoints = 0;
        int HighestPowerOfTwo = 0;
        boolean FirstRun = true;
        ArrayList<BinaryDigit> ListOfBinaryDigits = new ArrayList<BinaryDigit>();
        String temporaryBinaryAsString = "";

        //Make sure that startingPowerOfTwo is NOT above zero!!
        if (AccuracyInDP < 0) AccuracyInDP = 0;

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
        if (decimalvalue == 0) return "0";

        //Step 3a
        while (decimalvalueleft > 0 && NumberOfDecimalPoints < 80) {
            //Step 1
            for (int poweroftwo = -AccuracyInDP; ; poweroftwo++) {
                if ((Math.pow(2, poweroftwo)) > decimalvalueleft) {
                    maxpoweroftwo = poweroftwo - 1;
                    break;
                } else if ((Math.pow(2, poweroftwo)) == decimalvalueleft) {
                    maxpoweroftwo = poweroftwo;
                    break;
                }
            }
            //Step 2
            decimalvalueleft -= Math.pow(2, maxpoweroftwo);

            //Add the digit
            ListOfBinaryDigits.add(new BinaryDigit(maxpoweroftwo, 1));
            if (FirstRun)
                HighestPowerOfTwo = maxpoweroftwo;
            FirstRun = false;

            if (NumberOfDecimalPoints + maxpoweroftwo < 0)
                NumberOfDecimalPoints = -maxpoweroftwo;
        }

        //Make sure maxpoweroftwo is not more than 0 to prevent missing digits before decimal point
        if (maxpoweroftwo > 0) maxpoweroftwo = 0;

        //Make sure HighestPowerOfTwo is not less than 0 to prevent missing digits after decimal point which are 0s
        if (HighestPowerOfTwo < 0) HighestPowerOfTwo = 0;

        //Create a temporary int array for binary digits in order
        int[] binaryIntArray = new int[HighestPowerOfTwo - maxpoweroftwo + 1];
        if (decimalvalue % 1 != 0) {   //Give space for the fullstop.
            binaryIntArray = new int[HighestPowerOfTwo - maxpoweroftwo + 2];
        }

        //Make a flag for before and after decimal point numbers during the  foreach loop
        boolean MantissaMode = false;//Mantissa is the part after the decimal point, with negative powers

        //Make an int to keep the index of the char which belongs to 2^0 group
        int indexOfZeroPower;
        //Run the for loop to fill in the 1s
        for (BinaryDigit bd : ListOfBinaryDigits) {
            if (bd.powerOfTwo > 0 || bd.powerOfTwo == 0)//Check if before or after decimal point
                MantissaMode = false;
            else
                MantissaMode = true;

            if (bd.Value == 1)//Which should be always true...
            {
                if (!MantissaMode)
                    Array.set(binaryIntArray, HighestPowerOfTwo - bd.powerOfTwo, 1);
                else //Cuz the decimal point takes up one index
                    Array.set(binaryIntArray, HighestPowerOfTwo - bd.powerOfTwo + 1, 1);
            }
        }
        //Init indexOfZeroPower
        indexOfZeroPower = HighestPowerOfTwo + 1;

        //Fill in all empty spaces with zero (except the space which is supposed to be a "."
        for (int count = 0; count < binaryIntArray.length; count++) {
            if (binaryIntArray[count] != 1 && count != indexOfZeroPower)
                binaryIntArray[count] = 0;
        }

        //Fill in decimal point and make the string
        for (int count = 0; count < binaryIntArray.length; count++) {
            if (count == indexOfZeroPower)
                temporaryBinaryAsString += ".";
            if (binaryIntArray[count] == 1)
                temporaryBinaryAsString += "1";
            else if (binaryIntArray[count] == 0)
                temporaryBinaryAsString += "0";
        }

        return temporaryBinaryAsString;
    }

    public long GetTimeTakenNano() {
        return System.nanoTime() - prevNanoSeconds;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
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

    public class BinaryDigit {
        public int powerOfTwo;//Where is the digit located?
        public int Value;//One or Zero

        public BinaryDigit(int powerOfTwo, int BinaryValue) {
            this.powerOfTwo = powerOfTwo;
            this.Value = BinaryValue;
        }
    }

}
