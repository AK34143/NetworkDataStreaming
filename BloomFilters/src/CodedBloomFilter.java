import java.util.Iterator;
import java.util.Set;

public class CodedBloomFilter {

    public static void main(String[] args){
        int g = 7; // No. of sets
        int e = 1000; // No. of elements in each set
        int f = 3; // No. of filters
        int b = 30000; // No. of bits in each filter
        int h = 7; // No. of hashes


        int[][] bloomFilters = new int[f][b];
        char[][] codes = new char[g][f];
        fillSets(codes,g,f);

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
        /** Get random numbers for hashes */
        Set<Integer> randHashes = helper.getRandomSet(h,Integer.MAX_VALUE);
        int h_i = 0;
        int[] hashes = new int[h];
        it = randHashes.iterator();
        while(it.hasNext()){
            /** store random numbers into the array */
            hashes[h_i] = it.next();
            h_i++;
        }

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
                int flag = 1;
                /** For each bloom filter */
                for (int j=0;j<bloomFilters.length;j++) {
//                    for (int l = 0; l < hashes.length; l++) {
//                        int encode = element ^ hashes[l];
//                        encode = encode % b;
//                        if(i==0){
//                            System.out.println();
//                        }
//                        if (bloomFilters[j][encode] == 0) {
//                            flag = 0;
//                            break;
//                        }
//                    }
                    int l=0;
                    while(l<h){
                        int encode = element ^ hashes[l];
                        encode = encode%b;
                        if(bloomFilters[j][encode] == 0){ /** If any filter is not encoded */
                            break;
                        }
                        l++;
                    }
                    if(l==h) sb.append('1');
                    else sb.append('0');

                }
//                System.out.println(String.valueOf(codes[i])+" "+sb.toString());
                if(String.valueOf(codes[i]).equals(sb.toString())){
                    count++;
                }

            }
        }
        System.out.println(count);
    }

    static void fillSets(char[][] codes, int g, int f){
        for(int i=0;i<codes.length;i++){
            String str = Integer.toBinaryString(i+1);
            while(str.length()!=f)
                str = '0'+str;
            codes[i] = str.toCharArray();
//            System.out.println(s_i[i]);
        }
    }
}
