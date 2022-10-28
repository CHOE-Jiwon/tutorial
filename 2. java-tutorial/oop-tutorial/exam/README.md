#### 참고: https://wikidocs.net/157998
#### 날짜: 2022-01-11
#### 풀이: https://wikidocs.net/157712#05

## Q5
- - -
```
import java.util.ArrayList;
import java.util.Arrays;

public class Sample {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1, 2, 3));
        ArrayList<Integer> b = a;
        a.add(4);
        System.out.println(b.size());
    }
}
```

왜 출력값이 4일까?

객체 복사에는 두 가지 개념이 있다.
1. shallow copy
2. deep copy

shallow copy 의 경우 b에 저장되는건 a의 값이 아니라 a의 참조값이다.  
즉 a가 변하면 b도 변하는 것이다.

deep copy의 경우 a 인스턴스의 실제 값을 복사하고 그 값의 참조값을 b에 저장한다.

출처: https://jackjeong.tistory.com/100

## Q6
- - -
### Integer와 int의 차이
int:Primitive 자료형 (단순 값)
Integer: Wrapper 클래스 (객체)

`Integer` 자료형은 값을 대입하지 않을 경우 null 이기 때문에 add 메서드에서 null에 값을 더하려고 할 때 오류가 발생한다.  
따라서 클래스의 생성자를 만들어 초기 값을 설정해주면 오류가 나지 않는다.

## Q8
- - -
1. 정상
2. IS-A 관계 (상속)
3. IS-A 관계 (상속)
4. 상속 뒤집어짐. 오류
5. IS-A 관계 (구현)

## Q9
- - -
1. 정상
2. 오류 -> a의 자료형은 Animal. 따라서 Lion 클래스의 bark() 메소드 사용 불가.
3. 정상
4. 정상
5. 오류 -> c의 자료형은 Predator. 따라서 Animal 클래스의 hello() 메소드 사용 불가.
6. 정상