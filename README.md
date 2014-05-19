Download External Jar File for Flurry: http://teamkarbon.com/cloud/public.php?service=files&t=e132ba44f9c0cd4f81b6dc65f009bdcb

BinaryExtras
============

A random simple binary app for Android devices

Project Overview
============
**Binary Coverter** (Completed Decimal <-> Binary conversions only)

-Decimal (Done)

-Text (V2)

-Hex Decimal (V2)


**Binary Calculator** (In version 2)

-Basic Math Operations (+,-,/,x)
> Might add more complex calculations (eg. sin, cos, tan)


**Binary Quiz** (In progress)

-Random Generate Number (Decimal to Binary) & (Binary to Decimal)

>Add a function to edit range of value edited

>Add other Quizes stated at the Binary Converter

Current Area under Development (as of 17/5/2014): **Binary Quiz**
============
Level Selection (LevelSelect.java, activity_levelselect.xml)
----------------
>**DONE**: Add functionality to Up/Down buttons selecting level (1 to 9)

>**DONE**: Add functionality to Spinner selecting quantity of questions (5,10,20,30,40,50,100,500 and 1000)

>**TODO**: Create RadioBoxes so that users may choose either Dec->Bin conversions, Bin->Dec conversions, or both types

>**TODO**: Ensure all data is passed through intent extras, and test for any errors in this area.

-Quiz  (Quiz.java, activity_quiz.xml)
-----------
>**DONE**: Add required controls/views. (1 TextView, 1 EditText and 1 Derpy Button)

>**TODO**: Receive all intent extras data passed

>**TODO**: Work out the structure of how the quiz takes place.
>>Quiz has 2 states. Result mode and Question Mode, which are toggled by the press of the Derpy Button

>>> **Question Mode = true DONE**: Instructions(TextView) will be loaded with questions with the given ranges (in decimal values) and the user will type their answer in Input(EditText):

>>>> Level 1: 2^[0 to 3]

>>>> Level 2: 2^[0 to 5]

>>>> Level 3: 2^[0 to 7]

>>>> Level 4: 2^[0 to 8]

>>>> Level 5: 2^[-1 to 8]

>>>> Level 6: 2^[-1 to 9]

>>>> Level 7: 2^[-2 to 9]

>>>> Level 8: 2^[-3 to 10]

>>>> Level 9: 2^[-5 to 20]

>>> **Question Mode = false TODO**: Instructions(TextView) will show if the user had gotten the question to the previous answer right or wrong.

>**TODO**: Make sure that once noOfQns (Total quantity of questions) == currentQn (Current qn number), the following Result mode (Question Mode = false) will cause "Press the derpy button to see your results!" to be added behind the original result string in Instructions.setText(string).
