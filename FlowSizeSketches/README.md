Name : Aarthi Kashikar <br/>
UFID : 03668968


This project contains 3 java classes:
1) CountMin.java that implements Count Min
2) CounterSketch.java that implements Counter Sketch
3) ActiveCounter.java that implements Active Counter. 

I have added a Helper class that generates array with random numbers for a given size of the array.
I have added another class called Flow that stores flowId, true size and estimated size.

The input for CountMin.java and CounterSketch.java is read from project3input.txt
The folder "out" contains all the output files
1) countMin_output.txt
2) counterSketch_output.txt
3) activeCounter_output.txt


<u>The steps to run the program are as follows</u>:<br/>
In the project directory type the following commands:- <br/>
javac -d . src/*.java <br/>
java Helper <br/>
java CountMin or java CountMin \<k> \<w> <br/>
java CounterSketch or java CounterSketch \<k> \<w> <br/>
java ActiveCounter or java ActiveCounter \<bitSize>