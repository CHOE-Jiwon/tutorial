#### 참고: https://wikidocs.net/225
#### 날짜: 2021-01-10

**자바는 클래스를 떠나 존재하는 것은 있을 수 없다.** 따라서 자바의 함수는 따로 존재하지 않고 클래스 내에 존재한다.

## 1. 메소드를 사용하는 이유
- - - 
여러번 반복해서 사용되는 코드들을 하나로 묶어서 메소드를 만들어 사용.

## 2. 매개변수와 인수
- - -
매개변수(parameter): method에 입력으로 전달된 값을 받는 변수.  
인수(arguments): 메소드를 호출할 때 전달하는 입력값. 

## 3. 메소드의 입력값과 리턴값
- - -
자바의 메소드 구조
```
public return_type method_name(input_type1 param1, input_type2 param2, ...) {
    ...
    return return_value;    // if return_type == void: This code is not necessary
}
```

메소드의 분류  
1. 입력과 출력이 모두 있는 메소드 (sum)
2. 입력과 출력이 모두 없는 메소드 (단순 출력 함수)
3. 입력은 없고 출력은 있는 메소드 (getter)
4. 입력은 있고 출력은 없는 메소드 (setter)

## 4. return 의 또 다른 쓰임새
- - -
메소드를 빠져나가길 원할 때 `return` 을 단독으로 사용하여 메소드를 즉시 빠져나갈 수 있음.  
**단, 리턴 자료형이 void 인 메소드에만 해당됨**

## 5. 메소드 내에서 선언된 변수의 효력 범위
- - -

### 1. 지역 변수 (local variable)
메소드 내에서만 쓰인다.

### 2. 객체 변수 (instance variable)
메소드가 객체를 전달 받으면 메소드 내의 객체는 전달받은 객체 그 자체로 수행됨.  
따라서 입력으로 전달받은 sample 객체의 객체변수 a의 값이 증가됨.

메소드의 입력항목이 값인지 객체인지를 구별하는 기준은 입력항목의 자료형이 primitive 자료형인이 아닌지에 따라 나뉨.  
int 자료형과 같은 primitive 자료형인 경우 값이 전달되고, 그 이외의 경우 (reference 자료형)는 객체가 전달됨.

varTest() 의 인자를 굳이 객체로 주지 않고도 객체에 접근하는 법
```
public class Sample {

    int a;  // 객체변수 a

    public void varTest() {
        this.a++;
    }

    public static void main(String[] args) {
        Sample sample = new Sample();
        sample.a = 1;
        sample.varTest();
        System.out.println(sample.a);
    }
}
```

**this** 라는 키워드를 이용하면 메소드를 호출한 객체에 접근이 가능하다.