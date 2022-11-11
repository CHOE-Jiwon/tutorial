package src;

public class Sample {
    int a; // 객체 변수

    public void varTest(Sample sample) {
        sample.a++;
    }

    public static void main(String[] args) {
        Sample sample = new Sample();
        sample.a = 1;
        sample.varTest(sample);
        System.out.println(sample.a);
    }
}

/**
 * 지금 폴더 구조에서 똑같은 이름을 가진 파일들을 만들면 계속 오류가 나는데,
 * src 라는 폴더를 만든 후 package src; 를 사용하면 괜찮음.
 */