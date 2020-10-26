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

//            for(int i=0;i<k;i++){
//                for(int j=0;j<w;j++){
//                    System.out.print(C[i][j]+" ");
//                }
//                System.out.println();
//            }

            for(int n=0;n<N;n++) {
                for (int i = 0; i < k; i++) {
                    int index = RandomFlow[n] ^ HashFunctions[i];//(flows[n].hashCode() & 0xfffffff)
                    index = index % w;
                    C[i][index] += packetsizes[n];
                }
            }
            for(int n=0;n<N;n++){
                int nfcap = Integer.MAX_VALUE;
                for(int i=0;i<k;i++){
                        int index = RandomFlow[n] ^ HashFunctions[i];//(flows[n].hashCode() & 0xfffffff)
                        index = index % w;
                        nfcap = Math.min(nfcap,C[i][index]);
                }
                total += Math.abs(packetsizes[n]-nfcap);
            }
            System.out.println(total/N);

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
