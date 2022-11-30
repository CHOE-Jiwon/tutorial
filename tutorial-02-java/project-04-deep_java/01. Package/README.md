#### 참고: https://wikidocs.net/231
#### 날짜: 2022-01-12

## 1. 패키지 (Package)
- - -

***패키지는 비슷한 성격의 자바 클래스들을 모아 놓은 자바의 디렉토리이다.***

`package` 키워드는 해당 파일이 어떤 패키지의 파일인지를 알려주는 역할을 한다.

## 2. 클래스패스(Classpath)
- - -
참고
1. https://vvshinevv.tistory.com/70
2. https://whatisthenext.tistory.com/47

> classpath란 JVM이 프로그램을 실행할 때 클래스를 찾기 위한 `기준이 되는 경로`이다.  
만약 따로 설정하지 않는다면, 현재 경로를 default로 가져간다.

인텔리제이에서는 프로젝트를 생성할 때 클래스패스를 기본적으로 잘 잡아주지만  
터미널에서 작업을 할 때에는 신경을 써주어야 한다.

house/Sample.java
```
package house.test;

public class Sample {
    
}
```

1. 패키지를 신경쓰지 않고 컴파일 하는 경우 디렉토리 구조
```
$ javac house/Sample.java
.
|---house
    |---Sample.class

$ java house.Sample
오류: 기본 클래스 house.Sample을(를) 찾거나 로드할 수 없습니다.
원인: java.lang.NoClassDefFoundError: house/test/Sample (wrong name: house/Sample)
```
2. 패키지를 신경쓰고 컴파일하는 경우 디렉토리 구조
```
$ javac -d . house/Sample.java
.
|---house
    |---test
        |---Sample.class

$ java house.test.Sample
정상 동작
```

## 3. 서브패키지(Subpackage)
- - -
패키지 아래 다시 패키지를 만들 때, 하위 패키지를 상위 패키지의 `서브패키지` 라고 한다.

```
package house.person;

public class EungYongPark {

}
```

위에서 볼 수 있듯이, 패키지는 도트(`.`) 을 사용하여 하위 패키지를 표시한다.  
> 이 때 house.person 은 house 패키지의 서브패키지이다.

## 4. 패키지 사용하기
- - -
생성된 패키지를 사용하기 위해서는 `import`를 사용한다.

예를 들어, 다른 클래스에서 HouseKim 클래스를 사용하려면 다음과 같이 import 한다.
```
# 특정 패키지에서 특정 클래스만 사용할 경우
import house.HouseKim;

# 특정 패키지내의 모든 클래스를 사용할 경우
import house.*
```

## 5. 패키지를 사용하는 이유
- - -
1. 비슷한 성격의 클래스들끼리 묶을 수 있어 클래스의 분류가 용이함.
2. 클래스 명이 동일한 경우, 패키지를 통해 오류를 피할 수 있음.
