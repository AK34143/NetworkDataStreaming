import java.util.Iterator;
import java.util.Set;

public class BloomFilter {

    public static void main(String[] args){

        int k= 7; // Number of hashes
        int e = 1000;// Number of elements
        int b = 10000; // Number of bits

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
                if(filters[encode] == 0){
                    /** If any one of them is 0, set all of them to 1*/
                    setAllToOne(element,hashes,filters);
                    break;
                }
                /** If all are 1's then they have already been encoded */
            }
        }

        int count = 0;
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
            if(j==k) count++;
        }
        System.out.println("Lookup count in A = "+count);

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

        count=0;
        /** Lookup */
        for(int i=0;i<e;i++){ /** For each element */
            int element = elementsB[i];
            int j=0;
            while(j<k){
                int encode = element ^ hashes[j];
                encode = encode%b;
                if(filters[encode] == 0){ /** If any filter is not encoded */
                    break;
                }
                j++;
            }
            if(j==k) count++;
        }
        System.out.println("Lookup count in B = "+count);
    }

    static void setAllToOne(int element, int[] hashes, int[] filters){
        for(int i=0;i<hashes.length;i++){
            int index = element ^ hashes[i];
            index = index%filters.length;
            filters[index] = 1;
        }
    }
}
