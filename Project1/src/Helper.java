import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Helper {

    public static void main(String[] args){
//        getRandomArray(4,Integer.MAX_VALUE);
    }
    static int[] getRandomArray(int size, int range){
        Random r = new Random();
        int[] numbers = new int[size];
        Set<Integer> randNums = new HashSet<>();
        while(randNums.size()<size){
            randNums.add(r.nextInt(range)+1);
        }
        //System.out.println(randNums.size());
        int i = 0;
        Iterator<Integer> it = randNums.iterator();
        while(it.hasNext()){
//            System.out.println(it.next());
            numbers[i] = it.next();
            i++;
        }

//        for (int i = 0; i < numbers.length; i++) {
//            numbers[i] = r.nextInt(range)+1;
//        }
        return numbers;
    }
}
