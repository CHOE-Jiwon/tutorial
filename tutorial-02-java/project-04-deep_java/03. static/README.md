#### 참고: https://wikidocs.net/228
#### 날짜: 21-01-24

## 1. static 변수
- - -
동일한 클래스를 가지고 인스턴스를 만들 때, 값이 변하지 않는 속성들이 있을 것이다.  
이러한 속성들은 선언할 때 `static`으로 선언하면 메모리의 이점을 얻을 수 있다.  
***(static으로 선언된 값이 변경되지 않기를 바란다면, static 키워드 앞에 final 키워드를 추가하면 된다.)***

> static을 사용하는 또 한가지 이유로는 `공유`  개념을 들 수 있다. static으로 설정하면 같은 곳의 메모리 주소만을 바라보기 때문에 static 변수의 값을 공유하게 되는 것이다.  

> java에서 double quotes 와 single quote는 서로 다르다. 
> > single quote: for literal `char`  
> > double quotes: for literal `String`

## 2. static 메소드
- - - 

method 앞에 static 키워드를 붙이면, ```Counter.getCount()```와 같이 객체 생성 없이 클래스를 통해 메서드를 직접 호출할 수 있다.  

> static 메소드 안에서는 객체변수 접근이 불가능하다. 객체 변수는 객체가 생성될 때 메모리에 올라가기 때문.  
> 객체 변수가 static으로 선언되었다면 접근이 가능하겠쥬

보통 static 메소드는 유틸리티성 메소드를 작성할 때 많이 사용된다.  
예를 들어, "오늘의 날짜 구하기", "숫자에 콤마 추가하기" 등의 메소드를 작성할 때에는 클래스 메소드를 사용하는 것이 유리하다.

## 3. Singleton Pattern
- - -
***싱글톤은 단 하나의 객체만을 생성하게 강제하는 패턴이다.***

즉 클래스를 통해 생성할 수 있는 객체가 무조건 한 개만 되도록 만드는 것이다.

생성자를 `private` 로 선언하게 되면 다른 클래스에서 Singleton 클래스를 `new` 를 이용하여 생성할 수 없게 된다.

Singleton 클래스의 객체를 생성하기 위해서는 `static getInstance` 메소드를 이용하여 객체를 생성할 수 있다.  
여기서 끝낼 것이 아니라, Singleton 클래스에 one 이라는 static 변수를 두고, getInstance 메소드에서 one 값이 null 인 경우에만 객체를 생성하도록 하여 one 객체가 딱 한번만 만들어지도록 헀다.

