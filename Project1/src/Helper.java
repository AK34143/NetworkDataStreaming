import java.util.Random;

public class Helper {

    public static void main(String[] args){
        
    }
    static int[] getRandomArray(int size, int range){
        Random r = new Random();
        int[] numbers = new int[size];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = r.nextInt(range)+1;
        }
        return numbers;
    }
}
