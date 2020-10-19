import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CountingBloomFilter {

    public static void main(String[] args){
        int e = 1000; // Number of elements to be encoded initially
        int r = 500; // Number of elements to be removed
        int a = 500; // No. of elements to be added
        int c = 10000;//No. of counters in the filter
        int h = 7; // No. of hashes

        if(args.length==5){
            e = Integer.parseInt(args[0]);
            r = Integer.parseInt(args[1]);
            a = Integer.parseInt(args[2]);
            c = Integer.parseInt(args[3]);
            h = Integer.parseInt(args[4]);
        }

        Helper helper = new Helper();
//        int[] elements_A = helper.getRandomArray(e,Integer.MAX_VALUE);
//        int[] hashes = helper.getRandomArray(h,Integer.MAX_VALUE);

        Set<Integer> randHashes = helper.getRandomSet(h,Integer.MAX_VALUE);
        int h_i = 0;
        int[] hashes = new int[h];
        Iterator<Integer> it = randHashes.iterator();
        while(it.hasNext()){
            // store random numbers into the array
            hashes[h_i] = it.next();
            h_i++;
        }

        Set<Integer> randElementsA = helper.getRandomSet(e,Integer.MAX_VALUE);
        int e_i = 0;
        int[] elementsA = new int[e];
        it = randElementsA.iterator();
        while(it.hasNext()){
            // store random numbers into the array
            elementsA[e_i] = it.next();
            e_i++;
        }


        int[] filters = new int[c];
        for(int i=0;i<e;i++){/** For each element */
            int element = elementsA[i];
            for(int j=0;j<h;j++){
                int index = element ^ hashes[j];
                index = index % c;
                filters[index]++; /** increase counter by 1 */
            }
        }
//        int count = 0;
//        for(int i=0;i<c;i++){
//            if(filters[i]>0)
//                count++;
//        }
//
//        System.out.println(count);
        /** Remove */
        for(int i=0;i<r;i++){
            int element = elementsA[i];
            for(int j=0;j<h;j++){
                int index = element ^ hashes[j];
                index = index % c;
                filters[index]--; /** decrease counter by 1 */
            }
        }

//        int[] new_elements = helper.getRandomArray(a,Integer.MAX_VALUE);

        Set<Integer> randElementsNew = helper.getRandomSet(a,Integer.MAX_VALUE);
        e_i = 0;
        int[] new_elements = new int[a];
        it = randElementsNew.iterator();
        while(it.hasNext()){
            // store random numbers into the array
            new_elements[e_i] = it.next();
            e_i++;
        }

        /** Add new randomly generated elements in filter */
        for(int i=0;i<a;i++){
            int element = new_elements[i];
            for(int j=0;j<h;j++){
                int index = element ^ hashes[j];
                index = index % c;
                filters[index]++; /** increase counter by 1 */
            }
        }


        /** Lookup */
        int count = 0;
        for(int i=0;i<e;i++){/** For each element */
            int element = elementsA[i];
            int j=0;
            while(j<h){
                int index = element ^ hashes[j];
                index = index % c;
                if(filters[index]==0) break;/** If any element is not encoded */
                j++;
            }
            if(j==h) count++;
        }

        /** Write count and the table entries into a file */
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out/countingBloomFilter_output.txt"));
            writer.write("count = "+count+"\n");
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        System.out.println("Lookup count = "+count);
    }
}
