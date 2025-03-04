import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Helper {
    public static void main(String[] args){

    }

    static Set<Integer> getRandomSet(int size, int range){
        Random r = new Random();
        Set<Integer> randNums = new HashSet<>();
        while(randNums.size()<size){
            // generate random numbers till the given size is reached
            randNums.add(r.nextInt(range)+1);
        }

        return randNums;
    }

    static int[] getRandomArray(int size, int range){
        Random r = new Random();
        int[] numbers = new int[size];
        Set<Integer> randNums = new HashSet<>();
        while(randNums.size()<size){
            // generate random numbers till the given size is reached
            randNums.add(r.nextInt(range)+1);
        }

        int i = 0;
        Iterator<Integer> it = randNums.iterator();
        while(it.hasNext()){
            // store random numbers into the array
            numbers[i] = it.next();
            i++;
        }
        return numbers;
    }

}
