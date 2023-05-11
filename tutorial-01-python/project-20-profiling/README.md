# Profiling
---
> 병목 지점을 찾기 위해 하는 행위
> 측정 가능한 자원은 모두 프로파일할 수 있음.

### Profiling의 목표
1. 시스템의 어느 부분이 느린지, 어디서 RAM을 많이 쓰는지, 디스크 I/O나 네트워크 I/O를 과도하게 발생시키는 부분이 어디인지를 확인


### 시간을 측정하는 간단한 방법
1. print time.time()
2. Decorator
3. timeit

### Unix time 명령어를 이용한 간단한 시간 측정
```bash
$ /usr/bin/time -p {execute command}
$ /usr/bin/time --verbose {execute command}
```

`--verbose` 옵션은 mac에서 안먹힘

> `Major (requirint I/O) page faults` : 운영체제가 RAM에서 필요한 데이터를 찾을 수 없기 때문에 디스크에서 페이지를 불러왔는지 여부.
> 속도를 느리게 하는 원인

* real: 경과 시간을 기록
* user: CPU가 커널 함수 외 작업을 처리하느라 소비한 시간을 기록
* sys: 커널 함수를 수행하는 데 소비한 시간을 기록


### cProfile
```bash
$ python -m cProfile -s cumulative {file_name}
```
* 어느 코드가 오래걸렸는지 확인 가능
* 전체 실행 시간은 늘어남
* `-s cumulative` 옵션은 결과를 각 함수에서 소비한 누적 시간순으로 정렬하므로, 어떤 함수가 더 느린지 쉽게 확인 가능
* cProfile의 결과는 부모 함수에 따라 정렬되지 않음
* 실행된 코드 블록 안의 모든 함수가 소비한 시간을 요약하여 보여줌
* 함수 안의 각 줄의 정보가 아닌 함수 호출 자체의 정보만 얻을 수 있음

```bash
$ python -m cProfile -o profiles.stats {file_name}
```
* 통계 파일을 생성한 뒤 파이썬으로 분석하여 좀 더 세밀하게 살펴보는 것이 가능하다.

`p.print_callees()` 함수를 통해 특정 함수에서 호출하는 함수들의 실행 시간 측정이 가능하다.

> cProfile은 보기는 불편하지만 파이썬 기본 내장 도구이며 편리하고 빠르게 병목 지점을 찾게 해주는 장점이 있다.


## 한 줄씩 프로파일링 (line_profiler)
