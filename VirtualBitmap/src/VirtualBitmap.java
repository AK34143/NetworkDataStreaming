import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Flow{
    String FlowId;
    int distinctElements;

    public Flow(String f, int elements)
    {
        FlowId = f;
        distinctElements = elements;
    }

    //Getter
    public int getDistinctElements() {
        return distinctElements;
    }

    // Setter
    public void setDistinctElements(int elements) {
        this.distinctElements = elements;
    }

}

public class VirtualBitmap {

    public static void main(String[] args) throws FileNotFoundException {
        int m = 500000;
        int l = 500;

        /** Read input file */
        File myObj = new File("project4input.txt");
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        int n = Integer.valueOf(data);

        Helper helper = new Helper();

        int[] RandomFlows = helper.getRandomArray(n,Integer.MAX_VALUE);
        int f=0;
        Flow[] flows = new Flow[n];

        int totalElementSize = 0;
        int max = 0;
        /** Read each, create a flow node and add to flows array */
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            String[] flow = data.split("\\s+");
            String flowId = flow[0];
            int elements = Integer.valueOf(flow[1]);
            flows[f] = new Flow(flowId,elements);
            f++;
            totalElementSize += elements;
            max = Math.max(max, elements);
        }

//        System.out.println(max);
//        int[] elementArr = helper.getRandomArray(totalElementSize, Integer.MAX_VALUE);
//
//        Random r = new Random();
//        Set<Integer> randNums = new HashSet<>();
//        while(randNums.size()<size){
//            // generate random numbers till the given size is reached
//            randNums.add(r.nextInt(range)+1);
//        }
        int[][] elementArr = new int[n][l];
        for(int i=0;i<n;i++){
            Set<Integer> set = helper.getRandomSet(flows[i].getDistinctElements(),Integer.MAX_VALUE);
            Iterator<Integer> it = set.iterator();
            int j=0;
            while(it.hasNext()){
                // store random numbers into the array
                elementArr[i][j] = it.next();
                j++;
            }
        }
//        Set<Integer> set = helper.getRandomSet(totalElementSize,Integer.MAX_VALUE);
//        int[][] elementArr = new int[n][l];
//        Iterator<Integer> it = set.iterator();
//        f=0;
//
//        while(it.hasNext()){
//            int j=0;
//            while(j<flows[f].getDistinctElements()){
//                // store random numbers into the array
//                elementArr[f][j] = it.next();
//                j++;
//            }
//            f++;
//        }
        int[][] HashElements = new int[n][l];
        Random r = new Random();
        int randNum = r.nextInt(Integer.MAX_VALUE);
        for(int i=0;i<n;i++){
            int size = elementArr[i].length;
            int j=0;
            while(j<size){
//                String str = Integer.toString(elementArr[i][j]);
//                int hash = str.hashCode();
//                int index = hash % flows[i].getDistinctElements();
//                HashElements[i][j] = elementArr[i][j]*2654435761 % Math.pow(2,32);
                HashElements[i][j] = elementArr[i][j] ^ randNum;//str.hashCode();
                j++;
            }
        }

        int[] B = new int[m];
        /** Generate unique random numbers */
        int[] Random = helper.getRandomArray(l,Integer.MAX_VALUE);

        // B[H(f xor R[H(e) mod l])mod m]=1
        for(int i=0;i<n;i++){
            for(int j=0;j<flows[i].getDistinctElements();j++){
                int index = HashElements[i][j] %(l-1);
                int Ri = Random[index];
                int fxorR = RandomFlows[i] ^ Ri;
                int h_fxorR = fxorR ^ randNum;
                int bitIndex = h_fxorR % m;
                B[bitIndex] = 1;
            }
        }



        int b_zero = 0;
        for(int i=0;i<m;i++){
            if(B[i]==0) b_zero++;
        }

        double[] nf_arr = new double[n];
        double Vb = (double) b_zero/(double) m;
        System.out.println(b_zero+" "+Vb);
        for(int i=0;i<n;i++){
            int f_zero = 0;
            for(int j=0;j<l;j++){
                int Ri = Random[j];
                int fxorR = RandomFlows[i] ^ Ri;
                int h_i_f = fxorR ^ randNum;
                int index = h_i_f % m;
                if(B[index]==0) f_zero++;
            }
            double Vf = (double)f_zero/(double) l;//flows[i].getDistinctElements();

            nf_arr[i] = l * Math.log(Vb) - l * Math.log(Vf);
//            System.out.println(f_zero+" "+Vf+" "+nf_arr[i]);
//            System.out.println(nf_arr[i]);
        }
        for(int i=0;i<n;i++){
            System.out.println(flows[i].getDistinctElements());//+" "+nf_arr[i]);
        }

    }
}
