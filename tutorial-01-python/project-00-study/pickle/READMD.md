# pickle

##1. What is "pickle"?

---
> pickle 라이브러리는 python object를 직렬화 및 역직렬화 하는데 사용되는 라이브러리이다.  

* python object를 file로 저장할 수 있고, 그 반대로 file에서 python object를 가져오는 것도 가능하다.
* Booleans, integers, list, dictionary 와 같은 non-string python type 들 또한 저장이 가능하다.   
* 다양한 목적에 의해 object를 파일로 직렬화한다.
    * 상태값 저장
    * 대용량 파일 저장


**주의: 신뢰할 수 없거나 인증되지 않은 소스에서 받은 pickle 데이터를 절대로 unpickle 하지 말자.**

## 2. When use "pickle"?

---
1. 기계 학습 모델을 교육한 후 상태를 저장하면 나중에 모델을 로드하고 예측에 사용 가능.
2. 다른 프로그램에 대용량 및 복잡한 object를 넘겨줄 때 사용 가능
3. 머신러닝에서 사용되는 데이터 셋과 같은 데용랑 데이터를 저장할 때 사용 가능.
4. 데이터 캐싱 및 실행중인 애플리케이션의 데이터를 저장할 때 사용 가능.
