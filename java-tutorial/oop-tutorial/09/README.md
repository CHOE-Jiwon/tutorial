#### 참고: https://wikidocs.net/219
#### 날짜: 2022-01-11

# 1. 추상 클래스 (Abstract Class)

추상클래스는 인터페이스의 역할을 하면서 실제 메소드도 가지고 있는 자바의 돌연변이 같은 클래스이다.

> 1. 추상클래스를 만들기 위해서는 class 앞에 `abstract`라고 표기해야 한다.  
> 2. 또한 인터페이스의 메소드와 같은 역할을 하는 메소드에도 역시 `abstract` 를 표기해야 한다.  
> 3. **abstract 메소드**는 인터페이스의 메소드와 마찬가지로 **바디가 없다**.

*Predator.java*
```
public abstract class Predator extends Animal {
    public abstract String getFood();
}
```

*Tiger.java*
```
public class Tiger extends Predator implements Barkable {
    public String getFood() {
        return "apple";
    }

    public void bark() {
        System.out.println("어흥");
    }
}
```

*Lion.java*
```
public class Lion extends Predator implements Barkable {
    public String getFood() {
        return "banana";
    }

    public void bark() {
        System.out.println("으르렁");
    }
}
```

아니 이거 왜 쓰는거지?? 인터페이스가 있는데???

> 추상 클래스에는 abstract 메소드 외에 실제 메소드도 추가할 수 있다.  
추상 클래스에 실제 메소드를 추가하면, 해당 추상 클래스를 상속받는 객체에서 그 메소드들을 사용할 수 있게 된다.

