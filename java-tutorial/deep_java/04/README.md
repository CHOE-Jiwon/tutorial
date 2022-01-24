#### 참고: https://wikidocs.net/229
#### 날짜: 21-01-24

## 1. 예외 처리하기
- - - 
``` java
try {
    ...
} catch (exception1) {
    ...
} catch (exception2) {
    ...
}
```

try 문안의 수행할 문장들에서 예외가 발생하지 않는다면, catch 문 다음의 문장들이 수행되지 않는다.  
하지만 try 문안의 문장을 수행하는 도중 예외가 발생하면, 예외에 해당되는 catch 문이 수행된다.


## 2. finally
- - - 
프로그램 수행 도중 예외가 발생하면 프로그램이 중지되거나 예외 처리에 의해 catch 구문이 실행된다.  
하지만 어떤 예외가 발생하더라도 반드시 실행되어야 하는 부분이 있다면???

``` java
public class Sample {
    public void shouldBeRun() {
        System.out.println("ok thanks.");
    }

    public static void main(String[] args) {
        Sample sample = new Sample();
        int c;
        try {
            c = 4 / 0;
        } catch (ArithmeticException e) {
            c = -1;
        } finally {
            sample.shouldBeRun();  // 예외에 상관없이 무조건 수행된다.
        }
    }
}
```

`finally` 구문은 try 문장 수행 중 예외발생 여부에 상관없이 무조건 실행된다.  
따라서 위 코드를 실행하면 `sample.shoudBeRun()` 메소드가 수행되어 "ok, thanks" 문장이 출력될 것이다.

## 3. RuntimeException과 Exception
- - -
1. RuntimeException (Unchecked Exception)
    - 프로그램 실행시 발생하는 예외
2. Exception (Checked Exception)
    - 컴파일시 발생하는 예외

## 4. 예외 던지기 (throws)
> 발생된 exception을 메서드 내에서 해결하지 않고, 메서드를 호출한 곳에서 처리하도록 하는 것.

## 5. 트랜잭션 (Transaction)
> 트랜잭션은 하나의 작업 단위를 뜻한다.

트랜잭션 안의 각각의 작업에서 exception 발생이 가능하고 이에 대해 하나라도 실패시 롤백이 필요할 때,  
이를 메서드에서 처리하는 것이 아닌 하나의 트랜잭션 단위에서 예외 처리를 하면 정합성이 유지될 수 있다.

