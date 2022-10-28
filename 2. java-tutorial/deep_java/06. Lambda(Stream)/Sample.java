import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

// @FunctionalInterface
// interface Calculator {
//     int sum(int a, int b);
// }

// class MyCalculator implements Calculator {
//     public int sum(int a, int b)  {
//         return a+b;
//     }
// }

public class Sample {
    public static void main(String[] args) {
        // MyCalculator mc = new MyCalculator();
        // int result = mc.sum(3, 4);
        // System.out.println(result);

        // Calculator mc2 = (int a, int b) -> a + b;
        // int result2 = mc2.sum(3,4);
        // System.out.println(result2);

        // Calculator mc3 = Integer::sum;
        // int result3 = mc3.sum(3,4);
        // System.out.println(result3);

        // BiFunction<Integer, Integer, Integer> mc = (a, b) -> a + b;
        // int result = mc.apply(3,4);
        // System.out.println(result);

        BinaryOperator<Integer> mc = (a,b) -> a+b;
        int result = mc.apply(3,4);
        System.out.println(result);

    }
}