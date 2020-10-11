import java.util.HashSet;
import java.util.Set;

public class CountingBloomFilter {

    public static void main(String[] args){
        int e = 1000; // Number of elements to be encoded initially
        int r = 500; // Number of elements to be removed
        int a = 500; // No. of elements to be added
        int c = 10000;//No. of counters in the filter
        int h = 7; // No. of hashes

        Helper helper = new Helper();
        int[] elements_A = helper.getRandomArray(e,Integer.MAX_VALUE);
        int[] hashes = helper.getRandomArray(h,Integer.MAX_VALUE);
        int[] filters = new int[c];
        for(int i=0;i<e;i++){/** For each element */
            int element = elements_A[i];
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
            int element = elements_A[i];
            for(int j=0;j<h;j++){
                int index = element ^ hashes[j];
                index = index % c;
                filters[index]--; /** decrease counter by 1 */
            }
        }

        int[] new_elements = helper.getRandomArray(a,Integer.MAX_VALUE);

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
//        for(int i=0;i<c;i++){
//            if(filters[i]>0)
//                count++;
//        }
//        Set<Integer> indexes = new HashSet<>();
        int count = 0;
        for(int i=0;i<e;i++){/** For each element */
            int element = elements_A[i];
            int j=0;
            while(j<h){
                int index = element ^ hashes[j];
                index = index % c;
                if(filters[index]==0) break;
                j++
//                if(filters[index]>0) indexes.add(index);
            }
        }
        System.out.println("Lookup count = "+count);
    }
}
