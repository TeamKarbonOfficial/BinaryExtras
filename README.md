Download External Jar File for Flurry: http://teamkarbon.com/cloud/public.php?service=files&t=e132ba44f9c0cd4f81b6dc65f009bdcb

*BinaryExtras Official release v1*
============

As of 8/10/2014; Binary Extras v1.0.0.91

A random simple binary app for Android devices

*Project Overview*
============
**Binary Coverter** (Completed Decimal <-> Binary conversions only)

-Decimal (Done)

-Text (V2)

-Hex Decimal (V2)


**Binary Calculator** (In version 2)

-Basic Math Operations (+,-,/,x)
> Might add more complex calculations (eg. sin, cos, tan)


**Binary Quiz** (Functions completed, Bugs to be fixed)

-Random Generate Number (Decimal to Binary) & (Binary to Decimal) (Done)

>Add a function to edit range of value edited (Done)

>Add other Quizes stated at the Binary Converter


**Level Selection**
----------------
>Add functionality to Up/Down buttons selecting level (1 to 9)

>Add functionality to Spinner selecting quantity of questions (5,10,20,30,40,50,100,500 and 1000)

>Create RadioBoxes so that users may choose either Dec->Bin conversions, Bin->Dec conversions, or both types

>Ensure all data is passed through intent extras, and test for any errors in this area.


Current Area under Development (as of 7/10/2014): **Binary Quiz**
============
-Quiz  (Quiz.java, activity_quiz.xml)
-----------
>**DONE**: Add required controls/views. (1 TextView, 1 EditText and 1 Derpy Button)

>**DONE**: Receive all intent extras data passed

>**DONE**: Work out the structure of how the quiz takes place.
>>Quiz has 2 states. Result mode and Question Mode, which are toggled by the press of the Derpy Button

>>> **Question Mode = true DONE**: Instructions(TextView) will be loaded with questions with the given ranges (in decimal values) and the user will type their answer in Input(EditText):

>>>> **Level 1-9 settings DONE**

>>> **Question Mode = false DONE**: Instructions(TextView) will show if the user had gotten the question to the previous answer right or wrong.

>**DONE**: Make sure that once noOfQns (Total quantity of questions) == currentQn (Current qn number), the following Result mode (Question Mode = false) will cause "Press the derpy button to see your results!" to be added behind the original result string in Instructions.setText(string). *BUG FIXED*.
