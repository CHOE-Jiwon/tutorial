#### 참고: https://wikidocs.net/232
#### 날짜: 22-01-24

## 1. 접근제어자(Access Modifier)
- - -
 변수와 메소드의 사용 권한은 다음과 같은 접근 제어자를 사용하여 설정할 수 있다.

 1. private
 2. default
 3. protected
 4. public

 `private -> default -> protected -> public` 순으로 보다 많은 접근을 허용한다.

### 1. private

접근 제어자가 private으로 설정되었다면 private 이 붙은 변수, 메소드는 해당 클래스에서만 접근이 가능하다.

### 2.default

접근 제어자를 별도로 설정하지 않는다면 접근 제어자가 없는 변수, 메소드는 default 접근 제어자가 되어 해당 패키지 내에서만 접근이 가능하다.

HouseKim과 HousePark의 패키지는 `house` 로 동일하다. 따라서 HousePark 클래스에서 HouseKim의 lastname 변수에 접근이 가능하다.  
    - ***HouseKim의 lastname을 private으로 선언하니까 접근이 안됨!!! 애시당초 컴파일이 안됨!!***

### 3. protected
접근제어자가 protected로 설정되었다면 protected가 붙은 변수, 메소드는 동일 패키지의 클래스 또는 해당 클래스를 상속받은 다른 패키지의 클래스에서만 접근이 가능하다.

***default는 동일한 패키지 내에서만 접근이 가능 <-> protected는 동일 패키지 또는 상속받은 다른 패키지의 클래스.***
> house 패키지와 house.person 패키지는 서로 다른 것이다.

### 4. public
접근제어자가 public으로 설정되었다면 public 접근제어자가 붙은 변수, 메소드는 어떤 클래스에서라도 접근이 가능하다.


## 2. Class, Method의 접근 제어자
- - -
위 4가지 접근 제어자는 class와 method에도 적용이 된다. 