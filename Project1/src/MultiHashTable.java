import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MultiHashTable {

    public static void main(String []args){
        //Table entries
        int N = 1000;
        // Number of flows
        int m = 1000;
        // Number of hashes
        int k = 3;

        Helper helper = new Helper();
        int[] HashTable = helper.getRandomArray(k,Integer.MAX_VALUE);//Hashes
        int[] flows = helper.getRandomArray(m,Integer.MAX_VALUE);//init with 1000 random numbers
        int[] table = new int[N]; // Table entries
        Set<Integer> set = new HashSet<>();
        for(int i=0;i<m;i++){
            int flowId = flows[i];
            for(int j=0;j<k;j++){
                int index = flowId ^ HashTable[j];
                index = index%N;
                if(index<N && table[index] == 0){
                    table[index] = flowId;
                }

            }
        }
//        for(int i=0;i<table.length;i++){
//            System.out.println(table[i]);
//        }

    }


}
