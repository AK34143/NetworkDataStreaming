import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
        File myObj = new File("project3input.txt");
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        int n = Integer.valueOf(data);

        Helper helper = new Helper();
        /** Generate unique random flow ids that associate with each flow */
        int[] RandomFlow = helper.getRandomArray(n,Integer.MAX_VALUE);

        int f=0;
        Flow[] flows = new Flow[n];

        /** Read each, create a flow node and add to flows array */
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            String[] flow = data.split("\\s+");
            String flowId = flow[0];
            int elements = Integer.valueOf(flow[1]);
            flows[f] = new Flow(flowId,elements);
            f++;
        }
    }
}
