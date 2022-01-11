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
}

class MaxLimitCalculator extends Calculator {
    void add(int val) {
        int tmpValue = this.value + val;

        if (tmpValue > 100) {
            this.value = 100;
        } else {
            this.value = tmpValue;
        }
    }
}

public class Sample {
    public static void main(String[] args) {
        MaxLimitCalculator cal = new MaxLimitCalculator();
        cal.add(50);
        cal.add(51);
        System.out.println(cal.getValue());  // 100 출력
    }
}