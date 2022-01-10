#### 참고: https://wikidocs.net/217
#### 날짜: 2022-01-10

## 1. 인터페이스는 왜 필요한가?
- - -
동물원 관리자 주키퍼는 사자에게는 바나나를, 호랑이에게는 사과를 준다.  
메소드 오버로딩을 통하여 사자용 함수, 호랑이용 함수를 만들었다.  

*근데 만약 다른 동물들도 각기 다른 먹이가 필요할 때는? 함수를 계속 만들것인가?*

**이럴 때 사용할 수 있는 것이 인터페이스이다.**

## 2. 인터페이스 작성하기
- - -
1. 인터페이스는 class가 아닌 `interface` 라는 키워드를 사용한다.
2. 인터페이스 구현은 extends가 아닌 `implements` 라는 키워드를 사용한다.

### 인터페이스 사용전
```
    public void feed(Tiger tiger) {
        System.out.println("feed apple");
    }

    public void feed(Lion lion) {
        System.out.println("feed banana");
    }
```
각 메소드마다 입력받는 클래스가 다름.  
-> 다른 입력값이 생길 때마다 새로운 메소드를 생성해야함.

### 인터페이스 사용후
```
    public void feed(Predetor predetor) {
        System.out.println("feed apple");
    }
```
각 메소드마다 입력받는 클래스는 다르지만, 해당 클래스들은 모두 Predetor라는 인터페이스를 구현함.  
-> 다른 클래스 입력값이 생기더라도 새로운 메소드 생성이 필요 없음.

(** 물론 주는 음식은 바뀌어야겠지만~ 대충 이런 개념임)

tiger와 lion은 각각 Tiger, Lion의 객체이기도 하지만 Predetor 인터페이스의 객체이기도 함.
> 인터페이스를 구현한 클래스는 인터페이스와 `IS-A` 관계가 성립됨.  

"Tiger is a Predetor, Lion is a Predetor"

* Tiger is a Animal + Tiger is a Predetor
* Lion is a Animal + Lion is a Predetor

> 위와 같이 객체가 한 개 이상의 자료형을 가지는 특성을 `다형성(polymophism)` 이라고 한다.

이제 어떤 육식동물들이 추가되더라도 Predetor를 구현하기만 하면 주키퍼 입장에서는 메소드를 추가할 필요가 없다.

> 중요 클래스(ZooKeeper)를 작성하는 입장에서는 구현체(Lion, Tiger, ...)와 상관없이 `인터페이스를 기준`으로 `중요 클래스`를 작성해야 한다.


## 3. 인터페이스의 메소드
- - -
이제 각 육식 동물마다 다른 먹이를 주는 것을 구현해야 한다.

```
public interface Predetor {
    public String getFood();
}
```

인터페이스의 메소드에는 body가 없다. 이는 인터페이스의 규칙이다.  
getFood() 라는 함수의 body는`인터페이스를 implements(구현)한 클래스에서 작성`해야 한다.

## 4. 인터페이스의 핵심과 개념
- - - 
인터페이스를 이용하여 구현하였더니 ZooKeeper에서 사용하는 메소드의 개수가 줄었다.  
-> 이건 핵심이 아니다. 다른 클래스에서는 메소드가 늘었다.

여기서의 핵심은 ZooKeeper 클래스가 다른 동물 클래스에 `의존하는 클래스에서 독립적인 클래스로` 변했다는 것이다.

현실세계와 비교해보면 다음과 같다.

|현실|코드|
|-----|-----|
|컴퓨터|ZooKeeper|
|USB 포트|Predetor|
|하드디스크, 아이폰 젠더, ...|Lion, Tiger, ...|

### ***굳이 interface를 쓰지 않고 부모 클래스를 상속해서도 똑같은 효과를 볼 수 있지 않나???***
> 가장 큰 차이점은 `강제성`이다.  
상속의 경우 자식 클래스가 메소드를 오버라이딩 하지 않고 부모 클래스의 함수를 그대로 사용할 수 있다.  
그러나 인터페이스의 경우 구현 클래스가 메소드를 **반드시** 오버라이딩하여야 한다.

