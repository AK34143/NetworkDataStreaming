import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class BloomFilter {

    public static void main(String[] args){

        int k= 7; // Number of hashes
        int e = 1000;// Number of elements
        int b = 10000; // Number of bits
        if(args.length==3){
            k = Integer.parseInt(args[0]);
            e = Integer.parseInt(args[1]);
            b = Integer.parseInt(args[2]);
        }

        Helper h = new Helper();
        Set<Integer> randHashes = h.getRandomSet(k,Integer.MAX_VALUE);
        int h_i = 0;
        int[] hashes = new int[k];
        Iterator<Integer> it = randHashes.iterator();
        while(it.hasNext()){
            // store random numbers into the array
            hashes[h_i] = it.next();
            h_i++;
        }

        Set<Integer> randElementsA = h.getRandomSet(e,Integer.MAX_VALUE);
        int e_i = 0;
        int[] elementsA = new int[e];
        it = randElementsA.iterator();
        while(it.hasNext()){
            // store random numbers into the array
            elementsA[e_i] = it.next();
            e_i++;
        }

        int[] filters = new int[b];

        for(int i=0;i<e;i++){ /** For each element */
            int element = elementsA[i];
            for(int j=0;j<k;j++){
                int encode = element ^ hashes[j];
                encode = encode%b;
                /** If any one of them is 0, set all of them to 1
                 *  If all are 1's then they have already been encoded
                 */
                filters[encode] = 1; /** Set to 1*/
            }
        }

        int countA = 0;
        /** Lookup */
        for(int i=0;i<e;i++){ /** For each element */
            int element = elementsA[i];
            int j=0;
            while(j<k){
                int encode = element ^ hashes[j];
                encode = encode%b;
                if(filters[encode] == 0){ /** If any filter is not encoded */
                    break;
                }
                j++;
            }
            if(j==k) countA++;
        }
        System.out.println("Lookup count in A = "+countA);

//        int[] elementsB = h.getRandomArray(e,Integer.MAX_VALUE);

        Set<Integer> randElementsB = h.getRandomSet(e,Integer.MAX_VALUE);
        e_i = 0;
        int[] elementsB = new int[e];
        it = randElementsB.iterator();
        while(it.hasNext()){
            // store random numbers into the array
            elementsB[e_i] = it.next();
            e_i++;
        }

        int countB=0;
        /** Lookup */
        for(int i=0;i<e;i++){ /** For each element */
            int element = elementsB[i];
            int j=0;
            while(j<k){
                int encode = element ^ hashes[j];
                encode = encode%b;
                if(filters[encode] == 0){ /** If any element is not encoded */
                    break;
                }
                j++;
            }
            if(j==k) countB++;
        }
        System.out.println("Lookup count in B = "+countB);
        /** Write count and the table entries into a file */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out/bloomFilter_output.txt"));
            writer.write("Lookup count in A = "+countA+"\n");
            writer.write("Lookup count in B = "+countB+"\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
