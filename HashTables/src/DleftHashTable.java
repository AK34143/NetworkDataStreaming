import java.io.*;

public class DleftHashTable {
    public static void main(String []args){
        //Table entries
        int N = 1000;
        if(args.length!=0)
            N = Integer.parseInt(args[0]);
        // Number of flows
        int m = 1000;
        if(args.length!=0)
            m = Integer.parseInt(args[1]);
        // Number of hashes
        int segments = 4;
        if(args.length!=0)
            segments = Integer.parseInt(args[2]);
        Helper helper = new Helper();

        int[] HashFunctions = helper.getRandomArray(segments,Integer.MAX_VALUE);//Hashes
        int[] flows = helper.getRandomArray(m,Integer.MAX_VALUE);//init with 1000 random numbers
        int[] table = new int[N]; // Table entries

        int segSize = N/segments;
        int count = 0;

        for(int i=0;i<m;i++){ /** for each flow */
            int flowId = flows[i];
            int index = flowId ^ HashFunctions[0]; /** apply XOR to flowId and first segment hasfunction to get an index */
            index = index%segSize;
            int k = 0;
            while(index<N){
                /** if there is no entry present at the index in the table, place the flowId in that position */
                if(table[index] == 0) {
                    table[index] = flowId;
                    break;
                }
                k++;
                if(k==segments) break;
                int ind = flowId ^ HashFunctions[k]; /** apply XOR to flowId and next segment hasfunction to get new index int the next segment*/
                ind = ind%segSize;
                index = ind+(segSize*(k)); /** update index to get the actual index of the array */
            }
        }

        /** Count number of non-zero flowIds */
        for(int i=0;i<table.length;i++){
            if(table[i]!=0) count++;
        }

        /** Write count and the table entries into a file */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out/dleftHash_output.txt"));
            writer.write(count+"\n");

            for(int i=0;i<table.length;i++) {
                writer.append(table[i]+"\n");

            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
