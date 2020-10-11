import java.io.*;

public class MultiHashTable {

    public static void main(String []args) {
        //Table entries
        int N = 1000;
        if(args.length!=0)
            N = Integer.parseInt(args[0]);
        // Number of flows
        int m = 1000;
        if(args.length!=0)
            m = Integer.parseInt(args[1]);
        // Number of hashes
        int k = 3;
        if(args.length!=0)
            k = Integer.parseInt(args[2]);

        Helper helper = new Helper();
        int[] HashFunctions = helper.getRandomArray(k,Integer.MAX_VALUE);//Hashes
        int[] flows = helper.getRandomArray(m,Integer.MAX_VALUE);//init with 1000 random numbers
        int[] table = new int[N]; // Table entries
        int count = 0;
        for(int i=0;i<m;i++){ // for each flow
            int flowId = flows[i];
            for(int j=0;j<k;j++){ //for each hashfunction
                int index = flowId ^ HashFunctions[j]; /** apply XOR to flowId and hasfunction to get an index */
                index = index%N;
                if(table[index] == 0){
                    /** if there is no entry present at the index in the table, place the flowId in that position 
                        and move to the next flowId. If there is already an entry, hash with next hashfunction
                    */
                    table[index] = flowId;
                    break;
                }
            }
        }
        /** Count number of non-zero flowIds */
        for(int i=0;i<table.length;i++){
            if(table[i]!=0) count++;
        }

        /** Write count and the table entries into a file */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out/multiHash_output.txt"));
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
