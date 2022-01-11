import java.util.ArrayList;
import java.util.Arrays;

class Calculator {
    int value;

    Calculator() {
        this.value = 0;
    }

    void add(int val) {
        this.value += val;
    }

    int getValue() {
        return this.value;
    }

    int avg(int[] data) {
        int sum = 0;

        for(int num : data) {
            sum += num;
        }

        return sum/data.length;
    }

    int avg(ArrayList<Integer> data) {
        int sum = 0;

        for(int num : data) {
            sum += num;
        }

        return sum/data.size();
    }
}


public class Sample {
    public static void main(String[] args) {
        int[] data_array = {1, 3, 5, 7, 9};
        ArrayList<Integer> data_list = new ArrayList<>(Arrays.asList(1, 3, 5, 7, 9));
        Calculator cal = new Calculator();
        int result_array = cal.avg(data_array);
        int result_list = cal.avg(data_list);
        System.out.println(result_array);      // 5 출력
        System.out.println(result_list);      // 5 출력
    }
}