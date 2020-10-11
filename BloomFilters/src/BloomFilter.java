public class BloomFilter {

    public static void main(String[] args){

        int k= 7; // Number of hashes
        int e = 4000;// Number of elements
        int b = 40000; // Number of bits

        Helper h = new Helper();
        int[] hashes = h.getRandomArray(k,Integer.MAX_VALUE);
        int[] elementsA = h.getRandomArray(e,Integer.MAX_VALUE);
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

        int[] elementsB = h.getRandomArray(e,Integer.MAX_VALUE);

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
