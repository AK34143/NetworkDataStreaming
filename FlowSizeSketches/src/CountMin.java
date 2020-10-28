import java.io.*;
import java.util.*;

class Flow{
    String FlowId;
    int trueSize;
    int estimatedSize;
    public Flow(String f, int size)
    {
        FlowId = f;
        trueSize = size;
    }

    //Getter
    public int getEstimatedSize() {
        return estimatedSize;
    }

    // Setter
    public void setEstimatedSize(int size) {
        this.estimatedSize = size;
    }
    //Getter
    public int getTrueSize() {
        return trueSize;
    }

    // Setter
    public void setTrueSize(int size) {
        this.trueSize = size;
    }
}

public class CountMin {

    public static void main(String[] args) throws FileNotFoundException {
        int k = 3;
        int w = 3000;
        if(args.length==2){
            k = Integer.parseInt(args[0]);
            w = Integer.parseInt(args[1]);
        }
        Helper helper = new Helper();
        int[] HashFunctions = helper.getRandomArray(k,Integer.MAX_VALUE);

        int[][] C = new int[k][w];
        Queue<Flow> heap = new PriorityQueue<>(
                Comparator.comparingInt(Flow::getEstimatedSize));


        /** Read input file */
        File myObj = new File("project3input.txt");
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        int N = Integer.valueOf(data);

        /** Generate unique random flow ids that associate with each flow */
        int[] RandomFlow = helper.getRandomArray(N,Integer.MAX_VALUE);

        int f=0;
        Flow[] flows = new Flow[N];

        /** Read each, create a flow node and add to flows array */
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            String[] flow = data.split("\\s+");
            String flowId = flow[0];
            int packtes = Integer.valueOf(flow[1]);
            flows[f] = new Flow(flowId,packtes);
            f++;
        }

        /** Generate Counter array */
        int total = 0;
        for(int n=0;n<N;n++) {
            for (int i = 0; i < k; i++) {
                int index = RandomFlow[n] ^ HashFunctions[i];//(flows[n].hashCode() & 0xfffffff)
                index = index % w;
                C[i][index] += flows[n].getTrueSize();//packetsizes[n];
            }
        }

        /**  */
        for(int n=0;n<N;n++){
            int nfcap = Integer.MAX_VALUE;
            for(int i=0;i<k;i++){
                    int index = RandomFlow[n] ^ HashFunctions[i];//(flows[n].hashCode() & 0xfffffff)
                    index = index % w;
                    nfcap = Math.min(nfcap,C[i][index]);
            }
            flows[n].setEstimatedSize(nfcap);
            heap.add(flows[n]);
            if (heap.size() > 100) heap.poll();
            total += Math.abs(flows[n].getTrueSize()-nfcap);
        }
//            System.out.println(total/N);
//
//            System.out.println("Top 100 elements");
//
//            while(!heap.isEmpty()){
//                Flow fl = heap.poll();
//                System.out.println(fl.FlowId+" "+fl.trueSize+" "+fl.estimatedSize);
//            }

        /** Write output into a file */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out/countMin_output.txt"));
            writer.write(total/N+"\n");
            writer.write("Top 100 elements\n");
            writer.write("Flow Id   True Size   Estimated Size\n");
            while(!heap.isEmpty()){
                Flow fl = heap.poll();
                writer.write(fl.FlowId+"\t\t"+fl.trueSize+"\t"+fl.estimatedSize+"\n");
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        myReader.close();

    }
}
