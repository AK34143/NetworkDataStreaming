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

        for(int i=0;i<m;i++){
            int flowId = flows[i];
            int index = flowId ^ HashFunctions[0];
            index = index%segSize;
            int k = 0;
            while(index<N){
                if(table[index] == 0) {
                    table[index] = flowId;
                    break;
                }
                k++;
                if(k==segments) break;
                index = flowId ^ HashFunctions[k];
                index = index%segSize;
                index = index+(segSize*(k));
            }
        }

        for(int i=0;i<table.length;i++){
            System.out.println(table[i]);
            if(table[i]!=0) count++;
        }
        System.out.println("count = "+count);

    }
}
