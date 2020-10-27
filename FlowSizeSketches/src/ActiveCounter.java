import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class ActiveCounter {

    public static void main(String[] args){
        int N = 1000000;
        int Cn = 0;
        int Ce = 0;
        int bitSize = 16;
        Random rand = new Random();

        /** Increase the active counter by one for N times */
        int i=0;
        while(i<N){
            String str = Integer.toBinaryString(Cn);
            if(str.length()>bitSize){
                /** If Cn overflows, right-shift it by one bit and increase Ce by 1 */
                Ce++;
                Cn = Cn>>>1;
            } else {
                /** Increment Cn by one with probability 1/2^Ce */
                boolean val = rand.nextInt((int)Math.pow(2,Ce))==0;
                if(val)
                    Cn++;
            }
            i++;
        }
        /** Decimal representation of Active Counter */
        int ans = Cn * (int)Math.pow(2,Ce);
//        System.out.println(ans);

        /** Write output into a file */
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
