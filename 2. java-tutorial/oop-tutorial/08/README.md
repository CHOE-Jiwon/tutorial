#### 참고: https://wikidocs.net/269
#### 날짜: 2022-01-11

# 1. 다형성

> `IS-A` 관계  
barkAnimal 메소드의 입력자료형은 Tiger나 Lion이 아닌 Animal이다. 하지만 barkAnimal 메소드를 호출할 때는 tiger 또는 lion 객체를 전달할 수가 있다.  
이게 가능한 이유는 Tiger 클래스나 Lion 클래스가 Animal이라는 부모 클래스를 상속한 자식 클래스이기 때문이다.  
자식 클래스에 의해 만들어진 객체는 언제나 부모 클래스의 자료형으로 사용할 수 있다.  
```
Animal tiger = new Tiger();
Animal Lion = new Lion();
```

Bouncer의 barkAnimal 함수에서 동물이 들어날 수록 barkAnimal 메소드의 코드는 증가하게 될 것이다.  
이는 인터페이스를 사용하여 해결할 수 있다.
```
# Barkable.java
public interface Barkable {
    public void bark();
}

# Tiger.java
public class Tiger extends Animal implements Predator, Barkable{
    ...
    public void bark() {
        ...
    }
}
```

위에서 볼 수 있듯이, 콤마 (`,`) 를 이용하여 여러개의 인터페이스를 구현할 수 있다.

이로 써 bouncer의 코드는 다음과 같이 변경할 수 있다.
```
# 변경 전
public void barkAnimal(Animal animal) {
    if (animal instanceof Tiger) {
        System.out.println("어흥");
    } else if (animal instanceof Lion) {
        System.out.println("으르렁");
    }
}

# 변경 후
public void barkAnimal(Barkable animal) {
    animal.bark();
}
```
이처럼 다형성(polymorphism)을 사용하면 복잡한 형태의 분기문을 간단하게 처리할 수 있는 경우가 많다.

위 예제에서 사용한 tiger, lion 객체는 각각 Tiger, Lion 클래스의 객체이면서 Animal 클래스의 객체이기도 하고 Barkable, Predator 인터페이스의 객체이기도 하다.  
이러한 이유로 barkAnimal 메소드의 입력 자료형을 Animal에서 Barkable로 바꾸어 사용할 수 있는 것이다.

> 이렇게 하나의 객체가 여러개의 자료형 타입을 가질 수 있는 것을 객체지향에서는 `다형성(Polymorphism)` 이라고 한다.

Tiger 클래스의 객체는 다음과 같이 여러가지로 표현이 가능하다.
```
Tiger tiger = new Tiger();
Animal animal = new Tiger();
Predator predator = new Tiger();
Barkable barkable = new Tiger();
```
단, predator 객체와 barkable 객체는 서로 사용할 수 있는 메소드가 다르다.

만약 getFood()와 bark() 메소드를 모두 사용하고 싶다면??  
***Predator, Barkable 인터페이스를 상속받는 새로운 인터페이스를 만들면 된다.***

```
public interface BarkablePredator extends Predator, Barkable{

}
```

> 그런데 자바에서는 다중 상속이 안되는것 아닌가?  
**아니다.**  일반 클래스에 대해서는 다중 상속이 불가능하지만, `인터페이스에 대해서는 다중 상속이 지원된다`.