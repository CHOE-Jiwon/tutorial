#### 참고: https://wikidocs.net/231
#### 날짜: 2021-01-10

```
뜬금없지만

## How to run java?
# create class file of java file
$ javac {java_file_name}

# execute class file
$ java {class_file_name}

## java 파일 내에 2개의 클래스?
하나의 java 파일 내에 2개의 클래스가 등장하는건 좀 특이하지만 가능한 코드이다.
하나의 java 파일 내에는 여러개의 클래스를 선언할 수 있다.
단, 파일명이 Counter.java 라면 Counter.java 내의 Counter라는 클래스는 public으로 선언하라는 관례가 있다.
```


## Call by value
**메소드로 객체(reference type)를 전달할 경우 메소드에서 객체의 객체변수(속성) 값을 변경할 수 있게 된다.**

메소드의 입력으로 값(primitive type)을 전달받으면 객체의 속성에 접근할 수 없다. (변수명이 같다고 해서 접근 가능한게 아님)  
메소드의 입력으로 객체(reference type)을 전달받으면 객체의 속성에 접근할 수 있다. (이 때는 변수명이 같아야 함. 변수명이 다르면 없는 속성에 접근하는것이나 마찬가지)