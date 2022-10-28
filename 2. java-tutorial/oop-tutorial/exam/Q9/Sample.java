interface Predator {
    String bark();
}

abstract class Animal {
    public String hello() {
        return "hello";
    }
}

class Dog extends Animal {
}

class Lion extends Animal implements Predator {
    public String bark() {
        return "Bark bark!!";
    }
}

public class Sample {
    public static void main(String[] args) {
        Animal a = new Lion();
        Lion b = new Lion();
        Predator c = new Lion();

        System.out.println(a.hello());  // 1번
        System.out.println(a.bark());   // 2번 -> a 의 자료형은 Animal. 따라서 Lion 클래스의 bark 메소드 사용 불가
        System.out.println(b.hello());  // 3번
        System.out.println(b.bark());   // 4번
        System.out.println(c.hello());  // 5번 -> c 의 자료형은 Predator. Predator 인터페이스는 hello 라는 메소드 사용 불가. 만약 이 인터페이스가 Animal을 상속한다면 사용 가능하겠지.
        System.out.println(c.bark());   // 6번
    }
}