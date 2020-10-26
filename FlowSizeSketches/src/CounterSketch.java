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
        String[] HashBits = new String[k];
//        hashBits(HashFunctions,HashBits,32);

        int[][] C = new int[k][w];

        try {
            File myObj = new File("project3input.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();

            int N = Integer.valueOf(data);
            String[] flows = new String[N];
            int[] RandomFlow = helper.getRandomArray(N,Integer.MAX_VALUE);
            int f=0;
            int truesize = 0;
            int[] packetsizes = new int[N];
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                String[] flow = data.split("\\s+");
                String flowId = flow[0];
                flows[f] = flowId;

                int packtes = Integer.valueOf(flow[1]);
                packetsizes[f] = packtes;

                truesize += packtes;

                for(int i=0;i<k;i++){
                    int index = RandomFlow[f] ^ HashFunctions[i];//(flowId.hashCode() & 0xfffffff)
//                    char bit = msb(index,32);//Integer.toBinaryString(index).charAt(0);
                    int bit = (index & 0xff) >> 7;
                    index = index % w;
//                    if(HashBits[i].charAt(HashBits[i].length()-1)=='1')
                    if(bit == 1)
                        C[i][index]+=packtes;
                    else
                        C[i][index]-=packtes;
                }
                f++;
            }

            int total = 0;

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
//                    char msb = Integer.toBinaryString(index).charAt(0);
                    int bit = (index & 0xff) >> 7;
//                    char bit = msb(index,32);
                    index = index % w;

//                    nfcap = Math.min(nfcap,C[i][index]);
//                    nfcap += C[i][index];
//                    nfcaps[i] = (HashBits[i].charAt(HashBits[i].length()-1)=='1') ? C[i][index] : -1*C[i][index];
                    nfcaps[i] = (bit==1) ? C[i][index] : -1*C[i][index];
                }
                Arrays.sort(nfcaps);
                int median;
                if (N % 2 == 0)
                    median = (nfcaps[k/2] + nfcaps[k/2 - 1])/2;
                else
                    median = nfcaps[k/2];
                total += median;
//                total += ((nfcap)/3);//Math.abs(packetsizes[n]-nfcap);
            }
            System.out.println(total/N);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

//    static void hashBits(int[] HashFunction, String[] HashToBits, int bitSize){
//        for(int i=0;i<HashFunction.length;i++){
//            String str = Integer.toBinaryString(HashFunction[i]);
//            while(str.length()!=bitSize)
//                str = '0'+str;
//            System.out.println(str);
//            HashToBits[i] = str;
//        }
//    }
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
//    public static int middleOfThree(int a, int b,
//                                    int c)
//    {
//        // x is positive if a is greater than b.
//        // x is negative if b is greater than a.
//        int x = a - b;
//
//        int y = b - c; // Similar to x
//        int z = a - c; // Similar to x and y.
//
//        // Checking if b is middle (x and y
//        // both are positive)
//        if (x * y > 0)
//            return b;
//
//            // Checking if c is middle (x and z
//            // both are positive)
//        else if (x * z > 0)
//            return c;
//        else
//            return a;
//    }
}
