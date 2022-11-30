"""
routine: 함수를 호출하는 쪽
subroutin: 호출되는 함수

routine이 subroutine을 호출하는 경우, subroutine이 리턴될 때 까지 routine은 대기하게 된다.
subroutine이 종료되면 실행의 흐름이 원래의 routine으로 돌아오며, 이 때 subroutine이 return한 값을 routine에서 사용하게 된다.
"""

def hap(a,b):
    return a + b

ret = hap(3,4)
print(ret)

"""
Coroutine: 'Co'는 'cooperative'로 with 또는 together를 의미한다.
즉, coroutine을 번역하면 '협동 루틴' 정도로 말할 수 있다.

파이썬에서 coroutine은 함수인데, 기존의 함수와 달리 routine에 종속적이지는 않고 '협동'이 가능한 대등 관계이다.

coroutine은 함수가 종료되지 않은 상태에서 routine의 코드를 실행한 뒤 다시 돌아와서 coroutine 코드를 실행한다.
이에 따라, coroutine이 종료되지 않았으므로 coroutine의 내용도 계속 유지된다.

subroutine을 호출하면 코드를 한 번만 실행할 수 있지만, coroutine은 코드를 여러번 실행할 수 있다.
참고로 함수의 코드를 실행하는 지점을 진입점(entry point)라 하는데, coroutine은 진입점이 여러 개인 함수이다.

coroutine은 generator의 특별한 형태이다.
generator은 yield로 값을 발생시켰지만, coroutine은 yield로 값을 받아올 수 있다.

아래와 같이 coroutine에 값을 보내면서 코드를 실행할 때는 send 메소드를 사용하고,
send 메소드가 보낸 값을 받아오려면 (yield) 형식으로 변수에 저장할 수 있다.
* coroutine_obj.send(value)
* var = (yield)
"""

def number_coroutine():
    while True:         # coroutine을 계속 유지하기 위한 무한 루프
        x = (yield)     # coroutine 바깥에서 값을 받아옴. (yield) 사용
        print(x)

co = number_coroutine() # coroutine 객체 생성

next(co)                # coroutine 안의 yield 까지 코드 실행(최초 실행)
                        # x에 할당은 하지 않은 상태 (coroutine은 yield에서 대기하고 routine으로 나옴)


co.send(1)              # 1. coroutine이 대기에서 풀리고
                        # 2. x = 부분이 실행된 후(= 값 할당이 된 후) print 실행
                        # 3. while문을 돌아 다시 (yield)를 만나면 대기

co.send(2)              # 위와 동일
co.send(3)              # 위와 동일

"""
위에서는 coroutine 안에 값을 보내는 것을 해보았다면, 이번에는 바깥으로 보내는 것을 해본다.
(yield 변수) 와 같은 형식으로 yield에 변수를 지정한 뒤 괄호룰 묶어주면 값을 받아오면서 바깥으로 전달한다.
그리고 yield를 사용하여 바깥으로 전달한 값은 next 함수와 send 메서드의 반환값으로 나온다.

* 변수 = (yield 변수)
* 변수 = next(coroutine_obj)
* 변수 = coroutine_obj.send(value)

coroutine에서 값을 누적할 변수 total은 coroutine이 살아있는 한 유지된다.
routine에서 send로 보내는 값은 coroutine의 x 에 저장되고,
coroutine의 total은 routine으로 전달된다.

coroutine 예외 전달 후 종료하는 방법은 나중에 보시길..!
https://dojang.io/mod/page/view.php?id=2420
"""

def sum_coroutine():
    total = 0
    while True:
        x = (yield total)   # coroutine 바깥에서 값을 받아오면서 바깥으로 값을 전달
        total += x

co = sum_coroutine()
print(next(co))             # 0: coroutine 안의 yield까지 코드 실행 후 coroutine에서 나온 값 출력
                            # next 메소드는 coroutine에 값을 보내지 않을 때 사용
                            # send 메소드는 coroutine에 값을 보낼 때 사용

print(co.send(1))           # 1: coroutine에 1을 보내고 coroutine에서 나온 값 출력
print(co.send(2))           # 3: coroutine에 2을 보내고 coroutine에서 나온 값 출력
print(co.send(3))           # 6: coroutine에 3을 보내고 coroutine에서 나온 값 출력

co.close()                  # coroutine 종료

"""
generator와 coroutine의 차이
* generator: next method를 반복 호출하여 값을 얻어내는 방식
* coroutine: next method를 한 번만 호출한 뒤 send로 값을 주고 받는 방식
"""