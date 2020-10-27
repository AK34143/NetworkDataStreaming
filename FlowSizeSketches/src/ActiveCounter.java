import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ActiveCounter {

    public static void main(String[] args){
        String CnStr=null;
        String CeStr=null;

        int N = 1000000;
        int Cn = 0;
        int Ce = 0;
        int bitSize = 16;
        Random rand = new Random();

        int i=0;
        while(i<N){
            String str = Integer.toBinaryString(Cn);
            if(str.length()>bitSize){
                Ce++;
//                CeStr = Integer.toBinaryString(Ce);
                Cn = Cn>>>1;
//                CnStr = Integer.toBinaryString(Cn);
//                System.out.println(i+" "+CnStr+" "+CeStr);
            } else {
//                CnStr = Integer.toBinaryString(Cn);
//                System.out.println(i+" "+CnStr+" "+CeStr);
                boolean val = rand.nextInt((int)Math.pow(2,Ce))==0;
                if(val)
                    Cn++;
            }
            i++;
        }
        int ans = Cn * (int)Math.pow(2,Ce);
//        System.out.println(ans);
        /** Write count and the table entries into a file */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out/activeCounter_output.txt"));
            writer.write(ans+"\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
