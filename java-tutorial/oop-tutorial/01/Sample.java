/*
참고: https://wikidocs.net/156068
날짜: 2022-01-10
*/

class Calculator {
    static int result = 0;

    static int add(int num) {
        result += num;
        return result;
    }
}

public class Sample {
    public static void main(String[] args) {
        Calculator cal1 = new Calculator();
        Calculator cal2 = new Calculator();

        System.out.println(cal1.add(3));
        System.out.println(cal1.add(4));

        System.out.println(cal2.add(7));
        System.out.println(cal2.add(1));
    }
}

/**
 * ERROR: The public type Sample must be defined in its own file
 * -> Class name is different from file name
 * -> Class name (using 'public') must be same file name. Other class name must be different from file name.
 * 
 * ERROR: java.lang.ClassNotFoundException: Sample
 * -> 껐다 키니까 다시 됨;;;;
 * 
 * WARNING: The static method add(int) from the type Calculator should be accessed in a static way
 * -> 이건 뭘까?? 나중에 배울듯.
 */