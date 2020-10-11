import java.io.*;

public class CuckooHashTable {

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
        int k = 3;
        if(args.length!=0)
            k = Integer.parseInt(args[2]);
        int s = 2;
        if(args.length!=0)
            s = Integer.parseInt(args[3])   ;

        Helper helper = new Helper();
        int[] HashFunctions = helper.getRandomArray(k,Integer.MAX_VALUE);//Hashes
        int[] flows = helper.getRandomArray(m,Integer.MAX_VALUE);//init with 1000 random numbers
        int[] table = new int[N]; // Table entries

        int count = 0;
        for(int i=0;i<m;i++){
            int flowId = flows[i];
            boolean flag = false;
            for(int j=0;j<k;j++){
                int index = flowId ^ HashFunctions[j]; /** apply XOR to flowId and hasfunction to get an index */
                index = index%N;
                if(index<N && table[index] == 0){/** If there is no table entry present at index, place the flowId at index */
                    table[index] = flowId;
                    flag = true; /** Flag to check if the entry is placed */
                    break;
                }
            }
            if(!flag){
                /** Cuckoo steps are needed */
                for(int j=0;j<k;j++){
                    int index = flowId ^ HashFunctions[j]; /** apply XOR to flowId and hasfunction to get an index */
                    index = index%N;
                    if(shift(index, HashFunctions, table, k,s,N)){ 
                        /** If cuckoo steps are satisfied place flowId in the index and move to the next flow */
                        table[index] = flowId;
                        break;
                    }
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
            writer = new BufferedWriter(new FileWriter("out/cuckooHash_output.txt"));
            writer.write(count+"\n");

            for(int i=0;i<table.length;i++) {
                writer.append(table[i]+"\n");

            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static boolean shift(int index, int[] HashFunctions, int[] table, int k, int s, int N){
        if(s<=0) return false; /** Base case */
        int tableEntry = table[index];
        for(int i=0;i<k;i++){
            int newIndex = tableEntry ^ HashFunctions[i]; /** apply XOR to flowId and hasfunction to get new index */
            newIndex = newIndex%N;
            if(newIndex!=index && table[newIndex]==0){ 
                /** If the new index is not equal to the previous index and the table entry in the new entry is empty */
                table[newIndex] = tableEntry;
                return true;
            }
        }

        for(int i=0;i<k;i++){
            int newIndex = tableEntry ^ HashFunctions[i]; /** apply XOR to flowId and hasfunction to get new index */
            newIndex = newIndex%N;
            if(newIndex!=index && shift(newIndex, HashFunctions, table, k,s-1,N)){
                /** If the new index is not equal to the previous index and previous cuckoo steps is true */
                table[newIndex] = tableEntry;
                return true;
            }
        }
        return false;
    }

}
