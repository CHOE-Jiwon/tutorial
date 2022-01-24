#### 참고: https://wikidocs.net/230
#### 날짜: 21-01-24

## 1. Thread
- - -
1. thread 를 사용하기 위해 `Thread` 를 상속한다.
2. thread에서 실행할 코드를 담고 있는 `run()` 메소드를 선언한다.
3. `run()`을 실행하기 위해서는 `start()` 메소드를 실행한다.

## 2. Join
- - -
> 모든 쓰레드가 종료된 후에 무언가 동작을 하기 위해서는 `join()` 메소드를 실행한다.

## 3. Runnable
- - -
Thread 객체를 만들 때 상속을 사용하기도 하지만 보통은 Runnable 인터페이스를 구현하도록 하는 방법을 사용한다.  
왜냐하면 Thread 클래스를 상속하면 다른 클래스를 상속할 수 없기 때문이다. (`다중 상속 불가`)

> Runnable 인터페이스는 run 메소드를 구현하도록 강제한다.