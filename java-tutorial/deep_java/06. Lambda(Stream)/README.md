#### 참고: https://wikidocs.net/157858
#### 날짜: 21-01-24

## 1. 람다 (Lambda)
- - -
> 람다는 익명 함수(Anonymous functions)를 의미한다.

### 람다를 적용한 코드
```java
(int a, int b) -> a + b
```
처음에는 Calculator 인터페이스의 input value  
`->`  뒤의 `a+b` 는 return value.

### 인터페이스 사용시 주의사항
> 주의사항: interface의 method가 2개 이상이면 람다함수를 사용할 수 없다.  
> 그래서 람다 함수로 사용할 인터페이스는 `@FunctionalInterface` 어노테이션을 사용하는 것이 좋다.  
> 어노테이션을 사용하면 2개 이상의 메서드를 가진 인터페이스를 작성하는 것이 불가능해진다.

### 람다 축약
```java
(int a, int b) -> a + b
-------------------------
(a, b) -> a + b
-------------------------
Integer.sum(int a, int b)
-------------------------
Integer::sum
```

### 람다 함수 인터페이스
`BiFunction`

> BiFunction의 <Integer, Integer, Integer> 제네릭스는 순서대로 입력항목 2개, 출력항목 1개를 의미한다.  
그리고 BiFunction 인터페이스의 apply 메서드를 호출하면 람다함수 (a, b) -> a + b가 실행된다.  

+ BiFunction은 입출력 항목의 타입을 다양하게 사용할 수 있다.
+ 함수 실행시에는 apply 메소드를 사용한다.

`BinaryOperator`
> BinaryOperator를 사용하면 입출력 항목이 동일한 경우에 람다 함수 인터페이스로 사용 가능하다.


## 2. 스트림 (Stream)
- - -
> 스트림은 `데이터의 흐름` 으로, 배열 또는 컬렉션 인스턴스에 함수 여러 개를 조합해서 원하는 결과를 필터링하고 가공된 결과를 얻을 수 있다.  
또한 람다를 이용하여 코드의 양을 줄이고 간결하게 표현할 수 있다.  
`즉, 배열과 컬렉션을 함수형으로 처리할 수 있다.`


예시: 주어진 배열에서 짝수만 골라내서 중복 제거하고 역순으로 보여주어라.
```java
int[] data = {5, 6, 4, 2, 3, 1, 1, 2, 2, 4, 8};
```

```java
import java.util.*;

public class Sample{
    public static void main(String[] args) {
        int[] data = {5, 6, 4, 2, 3, 1, 1, 2, 2, 4, 8};

        // 짝수만 포함하는 ArrayList 생성
        ArrayList<Integer> dataList = new ArrayList<>();
        for (int = 0; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                dataList.add(data[i]);
            }
        }

        //Set을 사용하여 중복 제거
        HashSet<Integer> dataSet = new HashSet<>(dataList);

        // Set -> List
        ArrayList<Integer> distinctList = new ArrayList<>(dataSet);

        // order reversive
        distinctList.sort(Comparator.reverseOrder());

        // return int arrays
        int[] result = new int[distinctList.size()];
        for (int = 0; i < distinctList.zise(); i++) {
            result[i] = distinctList.get(i);
        }
    }
}
```

이걸 스트림을 사용하면 다음과 같이 바꿀 수 있다.

```java
import java.util.Arrays;
import java.util.Comparator;

public class Sample{
    public static void main(String[] args) {
        int[] data = {5, 6, 4, 2, 3, 1, 1, 2, 2, 4, 8};
        int[] result = Arrays.stream(data) // create IntStream
                .boxed() // IntStream -> Stream<Integer>
                .filter((a) -> a % 2 == 0) // filter odd
                .distinct() // remain distinct value
                .sorted(Comparator.reverseOrder()) // order reversive
                .mapToInt(Integer::intValue) // Stream<Integer> -> IntStream
                .toArray() // IntStream -> int[] arrays.
    }
}
```