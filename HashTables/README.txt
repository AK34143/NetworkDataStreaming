This project contains 3 java classes called MultiHashTable.java that implements MultiHash, CuckooHashTable.java that implements CuckooHash and DleftHashTable.java that implements dleft Hash. I have added a Helper class that generates array with random numbers for a given size of the array.

The folder out contains all the output files namely multiHash_output.txt, cuckooHash_output.txt, dleftHash_output.txt


The steps to run the program are as follows:
javac -d out src/*.java
cd out
java Helper
java MultiHashTable
java CuckooHashTable
java DleftHashTable