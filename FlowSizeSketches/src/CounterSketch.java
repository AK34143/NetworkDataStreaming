import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class CounterSketch {

    public static void main(String[] args){
        int k = 3;
        int w = 3000;
        int[] HashFunctions = new int[k];
        Helper helper = new Helper();
        HashFunctions = helper.getRandomArray(k,Integer.MAX_VALUE);

        int[][] C = new int[k][w];

        try {
            File myObj = new File("project3input.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();

            int N = Integer.valueOf(data);
            String[] flows = new String[N];
            int[] RandomFlow = helper.getRandomArray(N,Integer.MAX_VALUE);
            int f=0;
            int[] packetsizes = new int[N];
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                String[] flow = data.split("\\s+");
                String flowId = flow[0];
                flows[f] = flowId;

                int packtes = Integer.valueOf(flow[1]);
                packetsizes[f] = packtes;
                f++;
            }

            int total = 0;

            for(int n=0;n<N;n++) {
                for (int i = 0; i < k; i++) {
                    int index = RandomFlow[n] ^ HashFunctions[i];//(flowId.hashCode() & 0xfffffff)
                    int bit = (index & 0xff) >> 7;
                    index = index % w;
                    if (bit == 1)
                        C[i][index] += packetsizes[n];
                    else
                        C[i][index] -= packetsizes[n];
                }
            }
            for(int i=0;i<k;i++){
                for(int j=0;j<w;j++){
                    System.out.print(C[i][j]+" ");
                }
                System.out.println();
            }


            for(int n=0;n<N;n++){
                int[] nfcaps = new int[k];
                for(int i=0;i<k;i++){
                    int index = RandomFlow[n] ^ HashFunctions[i];//(flows[n].hashCode() & 0xfffffff)
                    int bit = (index & 0xff) >> 7;
//                    char bit = msb(index,32);
                    index = index % w;
                    nfcaps[i] = (bit==1) ? C[i][index] : -1*C[i][index];
                }
                Arrays.sort(nfcaps);
                int median;
                if (N % 2 == 0)
                    median = (nfcaps[k/2] + nfcaps[k/2 - 1])/2;
                else
                    median = nfcaps[k/2];
                total += median;
            }
            System.out.println(total/N);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    static char msb(int i, int bitSize){
//        for(int i=0;i<HashFunction.length;i++){
//            String str = Integer.toBinaryString(HashFunction[i]);
//            while(str.length()!=bitSize)
//                str = '0'+str;
//            System.out.println(str);
//            HashToBits[i] = str;
//        }
        String str = Integer.toBinaryString(i);
        while(str.length()!=bitSize-1)
            str = '0'+str;
//        System.out.println(str);
        return str.charAt(0);
    }
}
