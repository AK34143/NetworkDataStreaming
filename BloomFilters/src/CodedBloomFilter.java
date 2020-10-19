import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class CodedBloomFilter {

    public static void main(String[] args){
        int g = 7; // No. of sets
        int e = 1000; // No. of elements in each set
        int f = 3; // No. of filters
        int b = 30000; // No. of bits in each filter
        int h = 7; // No. of hashes

        if(args.length==5){
            g = Integer.parseInt(args[0]);
            e = Integer.parseInt(args[1]);
            f = Integer.parseInt(args[2]);
            b = Integer.parseInt(args[3]);
            h = Integer.parseInt(args[4]);
        }

        int[][] bloomFilters = new int[f][b];
        char[][] codes = new char[g][f];
        setsCode(codes,g,f);

        Helper helper = new Helper();

        /** Get random numbers for sets */
        Set<Integer> randSets = helper.getRandomSet(g*e, Integer.MAX_VALUE);
        int[][] sets = new int[g][e];
        Iterator<Integer> it = randSets.iterator();
        int setIndex = 0;
        while(it.hasNext()){
            /** store random numbers into the array */
            for(int i=0;i<e && setIndex<g;i++){
                sets[setIndex][i] = it.next();
            }
            setIndex++;

        }
        /** Hashes */
        int[] hashes = helper.getRandomArray(h,Integer.MAX_VALUE);

        /**For each set */
        for(int i=0;i<g;i++){
            /** For each bit */
            for(int j=0;j<f;j++){
                if(codes[i][j]=='1'){
                    int b_j = j;
                    /** For each element*/
                    for(int k=0;k<e;k++){
                        int element = sets[i][k];
                        /** For each hash */
                        for(int l=0;l<h;l++){
                            int encode = element ^ hashes[l];
                            encode = encode%b;
                            bloomFilters[b_j][encode] = 1;
                        }
                    }
                }
            }
        }

        /** Lookup **/

        int count = 0;
        /** For each set */
        for(int i=0;i<sets.length;i++) {
            /** For each element */
            for (int k=0; k<sets[0].length;k++) {
                int element = sets[i][k];
                StringBuilder sb = new StringBuilder();
                /** For each bloom filter */
                for (int j=0;j<bloomFilters.length;j++) {
                    int l=0;
                    while(l<h){
                        int encode = element ^ hashes[l];
                        encode = encode%b;
                        if(bloomFilters[j][encode] == 0){ /** If any element is not encoded */
                            break;
                        }
                        l++;
                    }
                    /** Reconstruct the code */
                    if(l==h) sb.append('1');
                    else sb.append('0');
                }
                /** Validate the new code with the binary code of the set i */
                if(String.valueOf(codes[i]).equals(sb.toString())){
                    count++;
                }

            }
        }

        /** Write count and the table entries into a file */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out/codedBloomFilter_output.txt"));
            writer.write("count = "+count+"\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    static void setsCode(char[][] codes, int g, int f){
        for(int i=0;i<codes.length;i++){
            String str = Integer.toBinaryString(i+1);
            while(str.length()!=f)
                str = '0'+str;
            codes[i] = str.toCharArray();
        }
    }
}
