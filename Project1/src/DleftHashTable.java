public class DleftHashTable {
    public static void main(String []args){
        //Table entries
        int N = 1000;
        // Number of flows
        int m = 1000;
        // Number of hashes
        int segments = 4;
        Helper helper = new Helper();

        int[] HashTable = helper.getRandomArray(segments,Integer.MAX_VALUE);//Hashes
        int[] flows = helper.getRandomArray(m,Integer.MAX_VALUE);//init with 1000 random numbers
        int[] table = new int[N]; // Table entries

        int segSize = N/segments;
        int count = 0;

        for(int i=0;i<m;i++){
            int flowId = flows[i];

//            for(int j=0;j<segments;j++){
                int index = flowId ^ HashTable[0];
                index = index%segSize;
                int k = 0;
                while(index<N){
                    if(table[index] == 0) {
                        table[index] = flowId;
                        break;
                    }
//                    System.out.println("hello");
                    k++;
                    if(k==segments) break;
                    index = flowId ^ HashTable[k];
                    index = index%segSize;
                    index = index+(segSize*(k));
                }
//            }
        }

        for(int i=0;i<table.length;i++){
            System.out.println(table[i]);
            if(table[i]!=0) count++;
        }
        System.out.println("count = "+count);

    }
}
