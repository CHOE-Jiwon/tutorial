import java.util.Arrays;

public class Sample {
    public static void main(String[] args) {
        int[] numbers = {1, -2, 3, -5, 8, -3};

        int[] result = Arrays.stream(numbers)
                // .boxed()
                .filter((a) -> a >= 0)
                .toArray()
                ;

        for (int r:result) {

            System.out.println(r);
        }
    }
}