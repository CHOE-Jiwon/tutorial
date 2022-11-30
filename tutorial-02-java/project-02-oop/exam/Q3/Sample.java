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

    boolean isOdd(int val) {
        if(val%2==0){
            return true;
        } else {
            return false;
        }
    }
}


public class Sample {
    public static void main(String[] args) {
        Calculator cal = new Calculator();
        System.out.println(cal.isOdd(13));      // false 출력
        System.out.println(cal.isOdd(20));      // true 출력
    }
}