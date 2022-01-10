/*
참고: https://wikidocs.net/156068
날짜: 2022-01-10
*/

class Calculator {
    int result = 0;

    int add(int num) {
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
 * -> Calculator 클래스
 * 
 * static: 메모리에 먼저 띄워버림
 * ERROR: Cannot make a static reference to the non-static field result
 * 내가 클래스 내에 변수를 그냥 선언하면, 객체를 만들 때 이 변수가 메모리에 띄워짐.
 * 근데 내가 클래스 내에 함수를 static으로 선언했다? 그리고 그 함수내에서 위에서 생성한 변수를 썼다? 이거는 말이 안됨.
 * 왜냐? 내 함수는 메모리에 올라감. 근데 함수 내에서는 메모리에 없는 변수를 참조함. 즉 없는 변수를 쓰는것.
 * 
 * 자 그러면 함수 내에 static 변수와 함수가 없다.
 * 이걸로 객체를 만든다.
 * 그러면 객체가 만들어질 때 메모리 위에 클래스의 변수 및 함수가 객체로? 띄워진다.
 */