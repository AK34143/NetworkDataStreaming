import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Helper {

    public static void main(String[] args){

    }
    static int[] getRandomArray(int size, int range){
        Random r = new Random();
        int[] numbers = new int[size];
        Set<Integer> randNums = new HashSet<>();
        while(randNums.size()<size){
            randNums.add(r.nextInt(range)+1);
        }

        int i = 0;
        Iterator<Integer> it = randNums.iterator();
        while(it.hasNext()){
            numbers[i] = it.next();
            i++;
        }
        return numbers;
    }
}
