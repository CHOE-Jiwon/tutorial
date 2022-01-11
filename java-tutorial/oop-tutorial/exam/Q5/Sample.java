import java.util.ArrayList;
import java.util.Arrays;

public class Sample {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> b = a;
        a.add(4);
        System.out.println(b.size());
    }
}

// 출력 결과는?
// 3이 아닐까? 왜냐하면 a에다가 원소 하나 넣기 전에 b를 생성한건데 혹시 메모리 공유하니?