#### 참고: https://wikidocs.net/280
#### 날짜: 2021-01-10


# 상속

> **상속이란 부모 클래스의 성질을 자식 클래스가 물려받는 것이다.**

## 1. 상속 (inheritance)
- - -
클래스 상속을 위해서는 `extends` 를 사용해야 한다.
Dog 클래스는 Animal 클래스를 상속받음으로써 name이라는 속성과 setname이라는 메서드를 사용할 수 있다.

```
뜬금없지만!
Error: Cannot use `this` in a static context
-> this 라는 키워드는 클래스의 인스턴스를 나타내는건데, static context 에는 instance가 없다.
-> this 라는 키워드는 현재 객체를 말한다.
참고
1. https://stackoverflow.com/questions/3728062/what-is-the-meaning-of-this-in-java  
2. https://stackoverflow.com/questions/16315488/this-cannot-use-this-in-static-context
```

## 2. 부모 클래스의 기능을 확장
- - -
부모 클래스에게 상속을 받은 자식 클래스는 부모 클래스보다 더 많은 기능을 가질 수 있다.
(보통 상속을 사용하여 더 많은 기능을 가지도록 작성한다.)

## 3. IS-A 관계
- - -
Dog 클래스는 Animal 클래스를 상속받았다. 이는 Dog는 Animal의 하위 개념으로 볼 수 있단 뜻이다.  
이런 경우 *Dog는 Animal이다.* 라고 할 수 있다.

자바에서는 이러한 관계를 `IS-A` 라고 한다.  
즉 "Dog `is a` Animal" 와 같이 is-a 로 표현 가능한 관계를 `IS-A` 관계라고 한다.

더 나아가서 다음의 경우를 보자.
```
1. 자식 클래스의 객체는 부모 클래스의 자료형인 것처럼 사용할 수 있다.
Animal dog = new Dog(); // 이 때 dog 객체는 Dog 클래스의 sleep 메소드는 사용할 수 없다.

2. 부모 클래스의 객체는 자식 클래스의 자료형인 것처럼 사용할 수 없다.
Dog dog = new Animal(); // 컴파일 오류
```

첫 번째 경우를 다시 보자.  
*개 클래스로 만든 개 객체는 동물이다* -> 이는 틀린말이 아니다. 개는 동물이 맞다.

그러나 두 번째 경우를 보자.  
*동물 클래스로 만든 동물 객체는 개다* -> 동물은 개다? 말이 안된다.

상속에서는 이와 같이 자식 클래스는 부모 클래스를 자료형으로 쓸 수는 있지만 그 반대는 되지 않는다는 것을 유의하자.

> 사실 자바에서 모든 클래스는 Object라는 클래스를 상속받는다.  
> Object라는 클래스를 명시적으로 상속하지 않는다 하더라도, 자동으로 상속받게끔 되어있다.

## 4. 메소드 오버라이딩(Method Overriding)
- - -
부모 클래스의 메소드를 자식 클래스에서 재구현하는 것을 **메소드 오버라이딩** 이라고 한다.

```
Error: This instance method cannot override the static method from Dog

-> 부모 클래스의 main method는 static. 자식 클래스의 main method는 non-static. 이때 오류가 난다?

-> we cannot override static methods because method overriding is based on dynamic binding at runtime and the static methods are bonded using static binding at compile time. So, we cannot override static methods.  

-> dynamic binding(runtime) & static binding(compile time)

참고: https://www.javatpoint.com/can-we-override-static-method-in-java
```

## 5. 메소드 오버로딩 (Method Overloading)
- - -
> 입력 항목이 다른 경우(parameters들이 다른 경우) 동일한 이름을 가진 함수를 생성할 수 있는데 이를 메소드 오버로딩이라고 한다.

## 6. 다중 상속 (Multiple Inheritance)
> 다중 상속이란, 하나의 자식 클래스가 여러 부모 클래스에게서 상속을 받는다는 것인데,  
> 자바에서는 다중 상속을 지원하지 않는다.

실제로 다중 상속을 지원하는 언어들은 있으나, 이들 모두 우선순위를 잘 설정하여야 한다.