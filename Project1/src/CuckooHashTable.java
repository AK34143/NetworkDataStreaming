public class CuckooHashTable {

    public static void main(String []args){
        //Table entries
        int N = 1000;
        // Number of flows
        int m = 1000;
        // Number of hashes
        int k = 3;
        int s = 2;

        Helper helper = new Helper();
        int[] HashTable = helper.getRandomArray(k,Integer.MAX_VALUE);//Hashes
        int[] flows = helper.getRandomArray(m,Integer.MAX_VALUE);//init with 1000 random numbers
        int[] table = new int[N]; // Table entries

        int count = 0;
        for(int i=0;i<m;i++){
            int flowId = flows[i];
            boolean flag = false;
            for(int j=0;j<k;j++){
                int index = flowId ^ HashTable[j];
                index = index%N;
                if(index<N && table[index] == 0){
                    table[index] = flowId;
                    flag = true;
                    break;
                }
            }
            if(!flag){
                for(int j=0;j<k;j++){
                    int index = flowId ^ HashTable[j];
                    index = index%N;
                    if(shift(index, HashTable, table, k,s,N)){
                        table[index] = flowId;
                        break;
                    }
                }
            }
        }

        for(int i=0;i<table.length;i++){
            System.out.println(table[i]);
            if(table[i]!=0) count++;
        }
        System.out.println("count = "+count);

    }

    static boolean shift(int index, int[] HashTable, int[] table, int k, int s, int N){
        if(s<=0) return false;
        int tableEntry = table[index];
        for(int i=0;i<k;i++){
            int newIndex = tableEntry ^ HashTable[i];
            newIndex = newIndex%N;
            if(newIndex!=index && newIndex<N && table[newIndex]==0){
                table[newIndex] = tableEntry;
                return true;
            }
        }

        for(int i=0;i<k;i++){
            int newIndex = tableEntry ^ HashTable[i];
            newIndex = newIndex%N;
            if(newIndex!=index && shift(newIndex, HashTable, table, k,s-1,N)){
                table[newIndex] = tableEntry;
                return true;
            }
        }
        return false;
    }

}
