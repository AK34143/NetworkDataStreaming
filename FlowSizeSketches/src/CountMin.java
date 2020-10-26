import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class CountMin {

    public static void main(String[] args){
        int k = 3;
        int w = 3000;
        int[] HashFunctions = new int[k];
        Helper helper = new Helper();
        HashFunctions = helper.getRandomArray(k,Integer.MAX_VALUE);

//        Set<Integer> randSets = helper.getRandomSet(k, Integer.MAX_VALUE);
//        int[][] HashFunctions = new int[k][w];
//        Iterator<Integer> it = randSets.iterator();
//        int hashIndex = 0;
//        while(it.hasNext()){
//            /** store random numbers into the array */
//            for(int i=0;i<w && hashIndex<k;i++){
//                HashFunctions[hashIndex][i] = it.next();
//            }
//            hashIndex++;
//
//        }

        int[][] C = new int[k][w];


        try {
            File myObj = new File("project3input.txt");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
//            System.out.println("N = "+data);
            int N = Integer.valueOf(data);
            String[] flows = new String[N];
           int[] RandomFlow = helper.getRandomArray(N,Integer.MAX_VALUE);
           int f=0;
           int truesize = 0;
           int[] packetsizes = new int[N];
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
//                System.out.println(data);
                String[] flow = data.split("\\s+");
                String flowId = flow[0];
                flows[f] = flowId;

                int packtes = Integer.valueOf(flow[1]);
                packetsizes[f] = packtes;

//                truesize += packtes;
//                System.out.print("flow id = "+flowId);
//                System.out.print(" packets = "+packtes);
//                System.out.println();

//                For all i belongs to K, C[i][H(f)]++
//                int nfcap = Math.min(C[i][Hi(f)]);


//                int nfcap = Integer.MAX_VALUE;




                f++;
            }
//            System.out.println(truesize);

            int total = 0;

//            for(int i=0;i<k;i++){
//                for(int j=0;j<w;j++){
//                    System.out.print(C[i][j]+" ");
//                }
//                System.out.println();
//            }

            for(int n=0;n<N;n++) {
                for (int i = 0; i < k; i++) {
//                    for(int j=0;j<w;j++){
                    int index = (flows[n].hashCode() & 0xfffffff) ^ HashFunctions[i];//RandomFlow[f]

                    index = index % w;
//                        System.out.print(index+" ");
//                        System.out.print(index+" "+packtes+" ; ");
                    C[i][index] += packetsizes[n];
//                    }
//                    System.out.println("-----");
                }
            }
            for(int n=0;n<N;n++){
                int nfcap = Integer.MAX_VALUE;
                for(int i=0;i<k;i++){
//                    for(int j=0;j<w;j++){
                        int index = (flows[n].hashCode() & 0xfffffff) ^ HashFunctions[i];//RandomFlow[n]
                        index = index % w;
                        nfcap = Math.min(nfcap,C[i][index]);
//                    }
                }
//                System.out.println(packetsizes[n]+" "+nfcap);
                total += Math.abs(packetsizes[n]-nfcap);
            }
            System.out.println(total/N);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }



//        int k = 3;
//
//        for(int i=0;i<k;i++){
//            //hash each flow if to all the k arrays
//
//        }
    }
}
